package com.hijackster99.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;

import java.util.ArrayList;
import java.util.HashMap;

public class ShaderResource {

	private int program;
	private HashMap<String, Integer> uniforms;
	private ArrayList<String> uniformNames;
	private ArrayList<String> uniformTypes;
	private int refCount;
	
	public ShaderResource() {
		this.program = glCreateProgram();
		this.refCount = 1;
		
		if(program == 0) {
			System.err.println("Shader creation failed: invalid memory location");
			System.exit(1);
		}
		
		uniforms = new HashMap<String, Integer>();
		uniformNames = new ArrayList<String>();
		uniformTypes = new ArrayList<String>();
	}
	
	public void addRef() {
		refCount++;
	}
	
	public boolean removeRef() {
		refCount--;
		return refCount == 0;
	}
	
	@Override
	protected void finalize() throws Throwable {
		glDeleteBuffers(program);
	}

	public int getProgram() {
		return program;
	}

	public HashMap<String, Integer> getUniforms() {
		return uniforms;
	}

	public ArrayList<String> getUniformNames() {
		return uniformNames;
	}

	public ArrayList<String> getUniformTypes() {
		return uniformTypes;
	}
	
}
