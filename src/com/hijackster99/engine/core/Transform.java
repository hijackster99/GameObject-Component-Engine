package com.hijackster99.engine.core;

public class Transform {
	
	private Transform parent;
	private Matrix4f parentMatrix;
	
	private Vector3f pos;
	private Quaternion rot;
	private Vector3f scale;
	
	private Vector3f oldPos;
	private Quaternion oldRot;
	private Vector3f oldScale;
	
	public Transform() {
		pos = new Vector3f(0, 0, 0);
		rot = new Quaternion(0, 0, 0, 1);
		scale = new Vector3f(1, 1, 1);
		parentMatrix = new Matrix4f().initIdentity();
	}
	
	public Transform(Transform t) {
		pos = new Vector3f(t.pos.getX(), t.pos.getY(), t.pos.getZ());
		rot = new Quaternion(t.rot.x, t.rot.y, t.rot.z, t.rot.w);
		scale = new Vector3f(t.scale.getX(), t.scale.getY(), t.scale.getZ());
		parentMatrix = t.getParentMatrix();
	}
	
	public void update(float delta) {
		if(oldPos != null) {
			oldPos.set(pos);
			oldRot.set(rot);
			oldScale.set(scale);
		}else {
			oldPos = new Vector3f(0, 0, 0).set(pos).add(1.0f);
			oldRot = new Quaternion(0, 0, 0, 0).set(rot).mult(0.5f);
			oldScale = new Vector3f(0, 0, 0).set(scale).add(1.0f);
		}
	}
	
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mult(rot).normalized();
	}
	
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtDirection(point, up);
	}
	
	public Quaternion getLookAtDirection(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}
	
	public boolean hasChanged() {
		if(parent != null && parent.hasChanged()) {
			return true;
		}

		if(!pos.equals(oldPos)) return true;
		if(!rot.equals(oldRot)) return true;
		if(!scale.equals(oldScale)) return true;
		
		return false;
	}
	
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());
		
		return getParentMatrix().mult(translationMatrix.mult(rotationMatrix.mult(scaleMatrix)));
	}
	
	private Matrix4f getParentMatrix() {
		if(parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();
		
		return parentMatrix;
	}
	
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}
	
	public Vector3f transform(Vector3f newPos) {
		return getParentMatrix().transform(newPos);
	}
	
	public Quaternion getTransformedRot() {
		Quaternion parentRot = new Quaternion(0, 0, 0, 1);
		
		if(parent != null) {
			parentRot = parent.getTransformedRot();
		}
		
		return parentRot.mult(rot);
	}

	public Vector3f getPos() {
		return pos;
	}

	public void setPos(Vector3f translation) {
		this.pos = translation;
	}

	public void setPos(float x, float y, float z) {
		this.pos = new Vector3f(x, y, z);
	}

	public Quaternion getRot() {
		return rot;
	}

	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public Transform getParent() {
		return parent;
	}

	public void setParent(Transform parent) {
		this.parent = parent;
	}
	
	public void setTransform(Transform t) {
		pos = t.getPos();
		rot = t.getRot();
		scale = t.getScale();
		
	}
	
	@Override
	public String toString() {
		return "Pos: " + pos.toString() + "\n" + 
			   "Rot: " + rot.toString() + "\n" + 
			   "Scale: " + pos.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Transform t;
		if(obj instanceof Transform) {
			t = (Transform) obj;
			if(t.pos == pos && t.rot == rot && t.scale == scale)
				return true;
		}
		return false;
	}
}
