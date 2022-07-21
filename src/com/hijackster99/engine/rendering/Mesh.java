package com.hijackster99.engine.rendering;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.util.ArrayList;

import com.hijackster99.engine.core.BiMap;
import com.hijackster99.engine.core.Util;
import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.meshLoading.IndexedModel;
import com.hijackster99.engine.rendering.meshLoading.OBJModel;
import com.hijackster99.engine.rendering.resourceManagement.MeshResource;

public class Mesh {

	private static BiMap<String, MeshResource> loadedModels = new BiMap<String, MeshResource>();
	private MeshResource resource;
	
	public Mesh(String fileName) {
		MeshResource oldResource = loadedModels.get(fileName);
		
		if(oldResource != null) {
			resource = oldResource;
			resource.addRef();
		}
		else {
			loadMesh(fileName);
			loadedModels.put(fileName, resource);
		}
	}
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
		addVertices(vertices, indices, calcNormals);
	}
	
	private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
		
		if(calcNormals) {
			calcNormals(vertices, indices);
		}
		
		resource = new MeshResource(indices.length);
		
		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}
	
	public void draw(int mode) {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		
		glDrawElements(mode, resource.getSize(), GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}
	
	private void calcNormals(Vertex[] vertices, int[] indices) {
		for(int i = 0; i < indices.length; i += 3) {
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];
			
			Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
			Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());
			
			Vector3f normal = v1.cross(v2).normalized();
			
			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
		}
		for(int i = 0; i < vertices.length; i++) {
			vertices[i].setNormal(vertices[i].getNormal().normalized());
		}
	}

	private void loadMesh(String fileName) {
		String[] split = fileName.split("\\.");
		String ext = split[split.length - 1];
		
		if(!ext.equals("obj")) {
			System.err.println("Error: file format not found : " + ext);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		OBJModel test = new OBJModel("./resources/models/" + fileName);
		IndexedModel model = test.toIndexedModel();
		model.calcNormals();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		
		for(int i = 0; i < model.getPositions().size(); i++) {
			vertices.add(new Vertex(model.getPositions().get(i), model.getTexCoords().get(i), model.getNormals().get(i)));
		}
		
		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);
		
		int[] indexData = new int[model.getIndices().size()];
		for(int i = 0; i < indexData.length; i++) {
			indexData[i] = model.getIndices().get(i);
		}
		
		addVertices(vertexData, indexData, false);
	}
	
	@Override
	public String toString() {
		return "vbo: " + resource.getVbo() + " ibo: " + resource.getIbo() + " size: " + resource.getSize();
	}
	@Override
	protected void finalize() throws Throwable {
		if(resource.removeRef()) {
			loadedModels.removeKey(resource);
		}
	}
	
}
