package com.hijackster99.engine.rendering;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgram;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.hijackster99.engine.components.BaseLight;
import com.hijackster99.engine.components.DirectionalLight;
import com.hijackster99.engine.components.PointLight;
import com.hijackster99.engine.components.SpotLight;
import com.hijackster99.engine.core.BiMap;
import com.hijackster99.engine.core.Matrix4f;
import com.hijackster99.engine.core.Transform;
import com.hijackster99.engine.core.Util;
import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.resourceManagement.ShaderResource;

public class Shader {

	private static BiMap<String, ShaderResource> loadedShaders = new BiMap<String, ShaderResource>();
	
	private ShaderResource resource;
	
	public Shader(String fileName) {
		
		ShaderResource oldResource = loadedShaders.get(fileName);
		
		if(oldResource != null) {
			resource = oldResource;
			resource.addRef();
		}
		else {
			resource = new ShaderResource();
			
			String vertexText = loadShader(fileName + ".vs");
			String fragmentText = loadShader(fileName + ".fs");
			
			addVertexShader(vertexText);
			addFragmentShader(fragmentText);
			
			addAllAttribs(vertexText);
			
			compileShader();
			
			addAllUniforms(vertexText);
			addAllUniforms(fragmentText);
		}
	}
	
	public void bind() {
		glUseProgram(resource.getProgram());
	}

	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mult(worldMatrix);
		
		for(int i = 0; i < resource.getUniformNames().size(); i++) {
			String uniformName = resource.getUniformNames().get(i);
			String uniformType = resource.getUniformTypes().get(i);
			String unprefixedUniformName = uniformName.substring(2);
			
			if(uniformType.equals("sampler2D")) {
				int samplerSlot = renderingEngine.getSamplerSlot(uniformName);
				material.getTexture(uniformName).bind(samplerSlot);
				setUniformI(uniformName, samplerSlot);
			}
			else if(uniformName.startsWith("t_")) {
				if(uniformName.equals("t_MVP")) setUniform(uniformName, projectedMatrix);
				else if(uniformName.equals("t_model")) setUniform(uniformName, worldMatrix);
				else throw new IllegalArgumentException(uniformName + " is not a valid component of Transform");
			}
			else if(uniformName.startsWith("r_")) {
				if(uniformType.equals("vec3")) setUniform(uniformName, renderingEngine.getVector3f(unprefixedUniformName));
				else if(uniformType.equals("float")) setUniformF(uniformName, renderingEngine.getFloat(unprefixedUniformName));
				else if(uniformType.equals("DirectionalLight")) setUniformDL(uniformName, (DirectionalLight)renderingEngine.getActiveLight());
				else if(uniformType.equals("PointLight")) setUniformPL(uniformName, (PointLight)renderingEngine.getActiveLight());
				else if(uniformType.equals("SpotLight")) setUniformSL(uniformName, (SpotLight)renderingEngine.getActiveLight());
				else renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);
			}
			else if(uniformName.startsWith("c_")) {
				if(uniformName.equals("c_eyePos")) setUniform(uniformName, renderingEngine.getMainCamera().getTransform().getTransformedPos());
				else throw new IllegalArgumentException(uniformName + " is not a valid component of Camera");
			}
			else {
				if(uniformType.equals("vec3")) setUniform(uniformName, material.getVector3f(unprefixedUniformName));
				else if(uniformType.equals("float")) setUniformF(uniformName, material.getFloat(uniformName));
				else throw new IllegalArgumentException(uniformType + " is not a valid uniform type for Material (prefix: no prefix)");
			}
		}
	}
	
	private void addAllAttribs(String shaderText) {
		final String ATTRIBUTE = "attribute";
		int attributeStartLocation = shaderText.indexOf(ATTRIBUTE);
		int attribNum = 0;
		while(attributeStartLocation != -1) {
			
			if(!(attributeStartLocation != 0 
					   && (Character.isWhitespace(shaderText.charAt(attributeStartLocation - 1)) || shaderText.charAt(attributeStartLocation - 1) == ';')
					   && Character.isWhitespace(shaderText.charAt(attributeStartLocation + ATTRIBUTE.length()))))
						continue;
			
			int begin = attributeStartLocation + ATTRIBUTE.length() + 1;
			int end = shaderText.indexOf(";", begin);
			
			String attributeLine = shaderText.substring(begin, end).trim();
			
			String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length()).trim();
			
			setAttribLocation(attributeName, attribNum);
			
			attributeStartLocation = shaderText.indexOf(ATTRIBUTE, attributeStartLocation + ATTRIBUTE.length());
			attribNum++;
		}
	}
	
	private class GLSLStruct{
		public String name;
		public String type;
		
		public GLSLStruct(String name, String type) {
			this.name = name;
			this.type = type;
		}
	}
	
	private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderText) {
		HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();
		final String STRUCT = "struct";
		int structStartLocation = shaderText.indexOf(STRUCT);
		while(structStartLocation != -1) {
			
			if(!(structStartLocation != 0 
			   && (Character.isWhitespace(shaderText.charAt(structStartLocation - 1)) || shaderText.charAt(structStartLocation - 1) == ';')
			   && Character.isWhitespace(shaderText.charAt(structStartLocation + STRUCT.length()))))
				continue;
			
			int nameBegin = structStartLocation + STRUCT.length() + 1;
			int braceBegin = shaderText.indexOf('{', nameBegin); 
			int braceEnd = shaderText.indexOf("}", braceBegin);
			
			String structName = shaderText.substring(nameBegin, braceBegin).trim();
			ArrayList<GLSLStruct> structComponents = new ArrayList<GLSLStruct>();
			
			int componentSemicolonPos = shaderText.indexOf(';', braceBegin);
			while(componentSemicolonPos != -1 & componentSemicolonPos < braceEnd) {
				int componentNameEnd = componentSemicolonPos + 1;
				
				while(Character.isWhitespace(shaderText.charAt(componentNameEnd - 1)) || shaderText.charAt(componentNameEnd - 1) == ';') 
					componentNameEnd--;
				
				int componentNameStart = componentSemicolonPos;
				
				while(!Character.isWhitespace(shaderText.charAt(componentNameStart - 1))) 
					componentNameStart--;
				
				int componentTypeEnd = componentNameStart;
				
				while(Character.isWhitespace(shaderText.charAt(componentTypeEnd - 1)))
					componentTypeEnd--;
					
				int componentTypeStart = componentTypeEnd;
				
				while(!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1)))
					componentTypeStart--;
				
				String componentName = shaderText.substring(componentNameStart, componentNameEnd);
				String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);
				structComponents.add(new GLSLStruct(componentName, componentType));
				
				componentSemicolonPos = shaderText.indexOf(';', componentSemicolonPos + 1);
			}
			result.put(structName, structComponents);
			
			structStartLocation = shaderText.indexOf(STRUCT, structStartLocation + STRUCT.length());
		}
		return result;
	}
	
	private void addAllUniforms(String shaderText) {
		HashMap<String, ArrayList<GLSLStruct>> structs = findUniformStructs(shaderText);
		
		final String UNIFORM = "uniform";
		int uniformStartLocation = shaderText.indexOf(UNIFORM);
		while(uniformStartLocation != -1) {
			
			if(!(uniformStartLocation != 0 
					   && (Character.isWhitespace(shaderText.charAt(uniformStartLocation - 1)) || shaderText.charAt(uniformStartLocation - 1) == ';')
					   && Character.isWhitespace(shaderText.charAt(uniformStartLocation + UNIFORM.length()))))
						continue;
			
			int begin = uniformStartLocation + UNIFORM.length() + 1;
			int end = shaderText.indexOf(";", begin);
			
			String uniformLine = shaderText.substring(begin, end).trim();
			
			int whiteSpacePos = uniformLine.indexOf(' ');
			String uniformName = uniformLine.substring(uniformLine.indexOf(' ') + 1, uniformLine.length()).trim();
			String uniformType = uniformLine.substring(0, whiteSpacePos).trim();
			
			resource.getUniformNames().add(uniformName);
			resource.getUniformTypes().add(uniformType);
			addUniform(uniformName, uniformType, structs);
			
			uniformStartLocation = shaderText.indexOf(UNIFORM, uniformStartLocation + UNIFORM.length());
		}
	}
	
	private void addUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs) {
		boolean addThis = true;
		ArrayList<GLSLStruct> structComponents = structs.get(uniformType);
		
		if(structComponents != null) {
			addThis = false;
			
			for(GLSLStruct struct : structComponents) {
				addUniform(uniformName + "." + struct.name, struct.type, structs);
			}
			
		}
		
		if(!addThis) return;
		
		int uniformLocation = glGetUniformLocation(resource.getProgram(), uniformName);
		
		if(uniformLocation == 0xFFFFFFFF) {
			System.err.println("Error: could not find uniform: " + uniformName);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		resource.getUniforms().put(uniformName, uniformLocation);
	}
	private void addVertexShader(String text) {
		addProgram(text, GL_VERTEX_SHADER);
	}
	
	private void addFragmentShader(String text) {
		addProgram(text, GL_FRAGMENT_SHADER);
	}
	
	@SuppressWarnings("unused")
	private void addGeometryShader(String text) {
		addProgram(text, GL_GEOMETRY_SHADER);
	}
	
	public void setAttribLocation(String attribName, int location) {
		glBindAttribLocation(resource.getProgram(), location, attribName);
	}
	
	@SuppressWarnings("deprecation")
	private void compileShader() {
		glLinkProgram(resource.getProgram());

		if(glGetProgram(resource.getProgram(), GL_LINK_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
		
		glValidateProgram(resource.getProgram());
		
		if(glGetProgram(resource.getProgram(), GL_VALIDATE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
	}

	@SuppressWarnings("deprecation")
	private void addProgram(String text, int type) {
		int shader = glCreateShader(type);
		
		if(shader == 0) {
			System.err.println("Shader creation failed: invalid memory location");
			System.exit(1);
		}
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShader(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(resource.getProgram(), shader);
	}
	
	private static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader;
		final String INCLUDE_DIRECTIVE = "#include";
		
		try {
			shaderReader = new BufferedReader(new FileReader("./resources/shaders/" + fileName));
			
			String line;
			
			while((line = shaderReader.readLine()) != null) {
				if(line.startsWith(INCLUDE_DIRECTIVE)) {
					shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
				}else
					shaderSource.append(line).append("\n");
			}
			
			shaderReader.close();
		}catch(Exception e) {
			e.printStackTrace();
			try {
				shaderReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/resources/shaders/" + fileName));
				
				String line;
				
				while((line = shaderReader.readLine()) != null) {
					if(line.startsWith(INCLUDE_DIRECTIVE)) {
						shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
					}else
						shaderSource.append(line).append("\n");
				}

				shaderReader.close();
			}catch(Exception e2){
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		return shaderSource.toString();
	}
	
	public void setUniformI(String uniformName, int value) {
		glUniform1i(resource.getUniforms().get(uniformName), value);
	}
	public void setUniformF(String uniformName, float value) {
		glUniform1f(resource.getUniforms().get(uniformName), value);
	}
	public void setUniform(String uniformName, Vector3f value) {
		glUniform3f(resource.getUniforms().get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	public void setUniform(String uniformName, Matrix4f value) {
		glUniformMatrix4(resource.getUniforms().get(uniformName), true, Util.createFlippedBuffer(value));
	}
	public void setUniformBL(String uniformName, BaseLight base) {
		setUniform(uniformName + ".color", base.getColor());
		setUniformF(uniformName + ".intensity", base.getIntensity());
	}
	
	public void setUniformDL(String uniformName, DirectionalLight directionalLight) {
		setUniformBL(uniformName + ".base", directionalLight);
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}
	
	public void setUniformPL(String uniformName, PointLight pointLight) {
		setUniformBL(uniformName + ".base", pointLight);
		setUniformF(uniformName + ".atten.constant", pointLight.getConstant());
		setUniformF(uniformName + ".atten.linear", pointLight.getLinear());
		setUniformF(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		setUniformF(uniformName + ".range", pointLight.getRange());
	}
	

	public void setUniformSL(String uniformName, SpotLight spotLight) {
		setUniformPL(uniformName + ".pointLight", spotLight);
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniformF(uniformName + ".cutoff", spotLight.getCutoff());
	}
	
}
