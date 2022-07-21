package com.hijackster99.engine.core;

public class Vector3f {

	private float x;
	private float y;
	private float z;
	
	public static final Vector3f ZERO = new Vector3f(0, 0, 0);
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public float getMax() {
		
		return Math.max(x, Math.max(y, z));
		
	}
	
	public Vector3f max(Vector3f other) {
		return new Vector3f(x > other.getX() ? x : other.getX(), y > other.getY() ? y : other.getY(), z > other.getZ() ? z : other.getZ());
	}
	
	public Vector3f negate() {
		return new Vector3f(-x, -y, -z);
	}
	
	public float dot(Vector3f v) {
		return this.x * v.x + this.y * v.y  + this.z * v.z;
	}
	
	public Vector3f cross(Vector3f v) {
		
		float x_ = y * v.z - z * v.y;
		float y_ = z * v.x - x * v.z;
		float z_ = x * v.y - y * v.x;
		
		return new Vector3f(x_, y_, z_);
	}
	
	public Vector3f normalized() {
		float length = length();
		
		if(length > 0)
			return new Vector3f(x / length, y / length, z / length);
		return this;
	}
	
	public Vector3f rotate(Vector3f axis, float angle) {
		
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);
		
		return this.cross(axis.mult(sinAngle)).add(
			   (this.mult(cosAngle)).add(
			   axis.mult(this.dot(axis.mult(1 - cosAngle)))));
	}
	
	public Vector3f rotate(Quaternion rot) {
		
		Quaternion con = rot.conjugate();
		
		Quaternion res = rot.mult(this).mult(con);
		
		return new Vector3f(res.getX(), res.getY(), res.getZ());
	}
	
	public Vector3f remove(Vector3f dir) {
		Quaternion q = Quaternion.getRotationBetween(new Vector3f(1, 0, 0), dir);
		Vector3f v = this.rotate(q);
		Vector3f scaledDir = dir.mult(v.getX()).rotate(q.conjugate());
		return this.sub(scaledDir);
	}
	
	public static Vector3f orthogonal(Vector3f v)
	{
	    float x = Math.abs(v.x);
	    float y = Math.abs(v.y);
	    float z = Math.abs(v.z);

	    Vector3f other = x < y ? (x < z ? new Vector3f(1, 0, 0) : new Vector3f(0, 0, 1)) : (y < z ? new Vector3f(0, 1, 0) : new Vector3f(0, 0, 1));
	    return v.cross(other);
	}
	
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		
		return dest.sub(this).mult(lerpFactor).add(this);
		
	}
	
	public Vector3f add(Vector3f v) {
		return new Vector3f(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector3f add(float v) {
		return new Vector3f(x + v, y + v, z + v);
	}
	
	public Vector3f add(float x, float y, float z) {
		return new Vector3f(this.x + x, this.y + y, this.z + z);
	}
	
	public Vector3f sub(Vector3f v) {
		return new Vector3f(x - v.x, y - v.y, z - v.z);
	}
	
	public Vector3f sub(float v) {
		return new Vector3f(x - v, y - v, z - v);
	}
	
	public Vector3f mult(Vector3f v) {
		return new Vector3f(x * v.x, y * v.y, z * v.z);
	}
	
	public Vector3f mult(float v) {
		return new Vector3f(x * v, y * v, z * v);
	}
	
	public Vector3f div(Vector3f v) {
		if(v.x != 0 & v.y != 0 & v.z != 0) {
			return new Vector3f(x / v.x, y / v.y, z / v.z);
		}
		return null;
	}
	
	public Vector3f div(float v) {
		if(v != 0) {
			return new Vector3f(x / v, y / v, z / v);
		}
		return null;
	}
	
	public static float getAngle(Vector3f v1, Vector3f v2) {
		float dot = v1.dot(v2);
		float cos = dot / (v1.length() * v2.length());
		return (float) Math.acos(cos);
	}
	
	public Vector3f set(float x, float y, float z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	
	public Vector3f set(Vector3f v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
		
		return this;
	}
	
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}
	
	public Vector2f getXZ() {
		return new Vector2f(x, z); 
	}
	
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	public Vector2f getYX() {
		return new Vector2f(y, x);
	}
	
	public Vector2f getZX() {
		return new Vector2f(z, x); 
	}
	
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y + " Z: " + z;
	}
	@Override
	public boolean equals(Object v) {
		if(v instanceof Vector3f) {
			return x == ((Vector3f) v).getX() & y == ((Vector3f) v).getY() & z == ((Vector3f) v).getZ();
		}
		return false;
	}
}

