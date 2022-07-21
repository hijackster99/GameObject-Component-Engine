package com.hijackster99.engine.core;

public class Quaternion {
	
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Quaternion() {
		
		this(0, 0, 0, 1);
		
	}
	
	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quaternion(Vector3f axis, float angle) {
		float sinHalfAngle = (float)Math.sin(angle/2);
		float cosHalfAngle = (float)Math.cos(angle/2);
		
		this.x = axis.getX() * sinHalfAngle;
		this.y = axis.getY() * sinHalfAngle;
		this.z = axis.getZ() * sinHalfAngle;
		this.w = cosHalfAngle;
	}
	
	public Quaternion(Matrix4f rot) {
		float trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);
		
		if(trace > 0) {
			float s = 0.5f/(float) Math.sqrt(trace + 1f);
			w = 0.25f/s;
			x = (rot.get(1, 2) - rot.get(2, 1)) * s;
			y = (rot.get(2, 0) - rot.get(0, 2)) * s;
			z = (rot.get(0, 1) - rot.get(1, 0)) * s;
		}
		else {
			if(rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2)) {
				float s = 2f * (float)Math.sqrt(1f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
				w = (rot.get(1, 2) - rot.get(2, 1))/s;
				x = 0.25f * s;
				y = (rot.get(1, 0) + rot.get(0, 1))/s;
				z = (rot.get(2, 0) + rot.get(0, 2))/s;
			}
			else if(rot.get(1, 1) > rot.get(2, 2)) {
				float s = 2f * (float)Math.sqrt(1f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
				w = (rot.get(2, 0) - rot.get(0, 2))/s;
				x = (rot.get(1, 0) + rot.get(0, 1))/s;
				y = 0.25f * s;
				z = (rot.get(2, 1) + rot.get(1, 2))/s;
			}
			else {
				float s = 2f * (float)Math.sqrt(1f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
				w = (rot.get(0, 1) - rot.get(1, 0))/s;
				x = (rot.get(2, 0) + rot.get(0, 2))/s;
				y = (rot.get(1, 2) + rot.get(2, 1))/s;
				z = 0.25f * s;
			}
		}
		
		float length = (float)Math.sqrt(x * x + y * y + z * z + w * w);
		
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}
	public Quaternion normalized() {
		float length = length();
		
		return new Quaternion(x / length, y / length, z / length, w / length);
	}
	
	public Quaternion conjugate() {
		return new Quaternion(-x, -y, -z, w);
	}
	
	public static Quaternion getRotationBetween(Vector3f u, Vector3f v)
	{
	  float k_cos_theta = u.dot(v);
	  float k = (float) Math.sqrt(Math.pow(u.length(), 2) * Math.pow(v.length(), 2));

	  if (k_cos_theta / k == -1)
	  {
	    // 180 degree rotation around any orthogonal vector
		Vector3f qxyz = Vector3f.orthogonal(u).normalized();
	    return new Quaternion(qxyz.getX(), qxyz.getY(), qxyz.getZ(), 0);
	  }

	  Vector3f cross = u.cross(v);
	  return new Quaternion(cross.getX(), cross.getY(), cross.getZ(), k_cos_theta + k).normalized();
	}
	
	public Quaternion mult(Quaternion q) {
		float x_ = (x * q.getW()) + 
				   (w * q.getX()) + 
				   (y * q.getZ()) - 
				   (z * q.getY());
		float y_ = (y * q.getW()) + 
				   (w * q.getY()) + 
				   (z * q.getX()) - 
				   (x * q.getZ());
		float z_ = (z * q.getW()) + 
				   (w * q.getZ()) + 
				   (x * q.getY()) - 
				   (y * q.getX());
		float w_ = (w * q.getW()) - 
				   (x * q.getX()) - 
				   (y * q.getY()) - 
				   (z * q.getZ());
		
		return new Quaternion(x_, y_, z_, w_);
	}
	public Quaternion mult(Vector3f v) {
		float x_ = w * v.getX() + y * v.getZ() - z * v.getY();
		float y_ = w * v.getY() + z * v.getX() - x * v.getZ();
		float z_ = w * v.getZ() + x * v.getY() - y * v.getX();
		float w_ = -x * v.getX() - y * v.getY() - z * v.getZ();
		
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion mult(float f) {
		return new Quaternion(x * f, y * f, z * f, w * f);
	}
	
	public Quaternion add(Quaternion q) {
		return new Quaternion (x + q.getX(), y +  q.getY(), z + q.getZ(), w + q.getW());
	}
	
	public Quaternion sub(Quaternion q) {
		return new Quaternion (x - q.getX(), y -  q.getY(), z - q.getZ(), w - q.getW());
	}
	
	public Matrix4f toRotationMatrix() {
		
		Vector3f forward = new Vector3f(2 * (x * z - w * y), 2 * (y * z + w * x), 1 - 2 * (x * x + y * y));
		Vector3f up = new Vector3f(2 * (x * y + w * z), 1 - 2 * (x * x + z * z), 2 * (y * z - w * x));
		Vector3f right = new Vector3f(1 - 2 * (y * y + z * z), 2 * (x * y - w * z), 2 * (x * z + w * y));
		
		return new Matrix4f().initRotation(forward, up, right);
		
	}
	
	public float dot(Quaternion q) {
		return x * q.getX() + y * q.getY() + z * q.getZ() + w * q.getW();
	}
	
	public Quaternion nlerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;
		
		if(shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getX(), -dest.getY(), -dest.getZ(), -dest.getW());
		
		return correctedDest.sub(this).mult(lerpFactor).add(this).normalized();
	}
	
	public Quaternion slerp(Quaternion dest, float lerpFactor, boolean shortest) {
		final float EPSILON = 1e3f;
		
		float cos = this.dot(dest);
		Quaternion correctedDest = dest;
		
		if(shortest && cos < 0) {
			cos = -cos;
			correctedDest = new Quaternion(-dest.getX(), -dest.getY(), -dest.getZ(), -dest.getW());
		}
		
		if(Math.abs(cos) >= 1 - EPSILON)
			return nlerp(correctedDest, lerpFactor, false);
		
		float sin = (float) Math.sqrt(1 - cos * cos);
		float angle = (float) Math.atan2(sin, cos);
		float invSin = 1f/sin;
		
		float srcFactor = (float)Math.sin((1f - lerpFactor) * angle) * invSin;
		float destFactor = (float)Math.sin((lerpFactor) * angle) * invSin;
		
		return this.mult(srcFactor).add(correctedDest.mult(destFactor));
	}
	
	public Vector3f getForward() {
		
		return new Vector3f(0, 0, 1).rotate(this);
		
	}
	
	public Vector3f getBack() {
		
		return new Vector3f(0, 0, -1).rotate(this);
		
	}


	public Vector3f getUp() {
	
		return new Vector3f(0, 1, 0).rotate(this);
	
	}

	public Vector3f getDown() {
	
		return new Vector3f(0, -1, 0).rotate(this);
	
	}
	
	public Vector3f getRight() {
		
		return new Vector3f(1, 0, 0).rotate(this);
		
	}
	
	public Vector3f getLeft() {
		
		return new Vector3f(-1, 0, 0).rotate(this);
		
	}

	 public Quaternion set(float x, float y, float z, float w) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		
		return this;
	}
	
	public Quaternion set(Quaternion q) {
		this.x = q.getX();
		this.y = q.getY();
		this.z = q.getZ();
		this.w = q.getW();
		
		return this;
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

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y + " Z: " + z + " W: " + w;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Quaternion) {
			return x == ((Quaternion) obj).getX() & y == ((Quaternion) obj).getY() & z == ((Quaternion) obj).getZ() & w == ((Quaternion) obj).getW();
		}
		return false;
	}

}
