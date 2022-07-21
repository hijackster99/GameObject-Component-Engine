package com.hijackster99.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

public class TextureResource {

	private int id;
	private int refCount;
	
	public TextureResource() {
		this.id = glGenTextures();
		this.refCount = 1;
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
		glDeleteBuffers(id);
	}

	public int getId() {
		return id;
	}
	
}
