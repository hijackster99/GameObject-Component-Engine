/**
 * 
 */
package com.hijackster99.engine.components;

import com.hijackster99.engine.core.Vector3f;

/**
 * @author jonat
 *
 */
public class BoundingCylinder extends Collider {

	private float radius;
	private float height;
	
	/**
	 * 
	 */
	public BoundingCylinder(Vector3f pos, float radius, float height) {
		super();
		this.getTransform().setPos(pos);
		this.radius = radius;
		this.height = height;
	}

	/**
	 * @return the pos
	 */
	public Vector3f getPos() {
		return getTransform().getPos();
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(Vector3f pos) {
		this.getTransform().setPos(pos);
	}

	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/* (non-Javadoc)
	 * @see com.hijackster99.engine.physics.Collider#getNormal(com.hijackster99.engine.core.Vector3f)
	 */
	@Override
	public Vector3f getNormal(Vector3f pos) {
		return null;
	}
	
}
