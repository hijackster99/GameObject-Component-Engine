package com.hijackster99.engine.rendering;

import com.hijackster99.engine.core.Vector2f;
import com.hijackster99.engine.core.Vector3f;

public class Vertex {

	private Vector3f pos;
	private Vector2f texCoord;
	private Vector3f normal;
	
	public static final int SIZE = 8;
	
	public Vertex(Vector3f pos, Vector2f texCoord, Vector3f normal) {
		this.pos = pos;
		this.texCoord = texCoord;
		this.normal = normal;
	}
	
	public Vertex(Vector3f pos, Vector2f texCoord) {
		this(pos, texCoord, new Vector3f(0, 0, 0));
	}
	
	public Vertex(Vector3f pos) {
		this(pos, new Vector2f(0, 0), new Vector3f(0, 0, 0));
	}
	
	public Vertex(float x, float y, float z) {
		this(new Vector3f(x, y, z), new Vector2f(0, 0), new Vector3f(0, 0, 0));
	}
	public Vertex(float x, float y, float z, float x2, float y2) {
		this(new Vector3f(x, y, z), new Vector2f(x2, y2), new Vector3f(0, 0, 0));
	}
	public Vertex(float x, float y, float z, float x2, float y2, float x3, float y3, float z3) {
		this(new Vector3f(x, y, z), new Vector2f(x2, y2), new Vector3f(x3, y3, z3));
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public Vector2f getTexCoord() {
		return texCoord;
	}

	public void setTexCoord(Vector2f texCoord) {
		this.texCoord = texCoord;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}
	
}
