package com.hijackster99.engine.core;

public class Vector2f {

	private float x;
	private float y;
	
	public Vector2f(float x, float y) {
		this.setX(x);
		this.setY(y);
	}
	
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		
		return dest.sub(this).mult(lerpFactor).add(this);
		
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public float dot(Vector2f v) {
		return this.x * v.x + this.y * v.y;
	}
	
	public Vector2f normalized() {
		float length = length();
		
		return new Vector2f(x / length, y / length);
	}
	
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2f((float)(x * cos - y * sin), (float)(x * sin + y * cos));
	}
	
	public float cross(Vector2f v) {
		
		return x * v.getY() - y * v.getX();
		
	}
	
	public Vector2f add(Vector2f v) {
		return new Vector2f(x + v.x, y + v.y);
	}
	
	public Vector2f add(float v) {
		return new Vector2f(x + v, y + v);
	}
	
	public Vector2f sub(Vector2f v) {
		return new Vector2f(x - v.x, y - v.y);
	}
	
	public Vector2f sub(float v) {
		return new Vector2f(x - v, y - v);
	}
	
	public Vector2f mult(Vector2f v) {
		return new Vector2f(x * v.x, y * v.y);
	}
	
	public Vector2f mult(float v) {
		return new Vector2f(x * v, y * v);
	}
	
	public Vector2f div(Vector2f v) {
		if(v.x != 0 & v.y != 0) {
			return new Vector2f(x / v.x, y / v.y);
		}
		return null;
	}
	
	public Vector2f div(float v) {
		if(v != 0) {
			return new Vector2f(x / v, y / v);
		}
		return null;
	}

	public Vector2f set(float x, float y) {
		
		this.x = x;
		this.y = y;
		
		return this;
	}
	
	public Vector2f set(Vector2f v) {
		this.x = v.getX();
		this.y = v.getY();
		
		return this;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}
	
	@Override
	public String toString() {
		return "X: " + x + "\nY: " + y;
	}

	@Override
	public boolean equals(Object v) {
		if(v instanceof Vector2f) {
			return x == ((Vector2f) v).getX() & y == ((Vector2f) v).getY();
		}
		return false;
	}

	/**
	 * @return
	 */
	public float getMax() {
		return x >= y ? x : y;
	}
	
}
