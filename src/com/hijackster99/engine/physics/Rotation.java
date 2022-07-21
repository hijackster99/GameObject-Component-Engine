/**
 * 
 */
package com.hijackster99.engine.physics;

import com.hijackster99.engine.core.Vector3f;

/**
 * @author jonat
 *
 */
public class Rotation {

	private Vector3f axis;
	private float angularVelocity;
	
	public Rotation(Vector3f axis, float angularVelocity) {
		this.axis = axis;
		this.angularVelocity = angularVelocity;
	}
	
	/**
	 * @return the axis
	 */
	public Vector3f getAxis() {
		return axis;
	}
	/**
	 * @param axis the axis to set
	 */
	public void setAxis(Vector3f axis) {
		this.axis = axis;
	}
	/**
	 * @return the angularVelocity
	 */
	public float getAngularVelocity() {
		return angularVelocity;
	}
	/**
	 * @param angularVelocity the angularVelocity to set
	 */
	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}
	
}
