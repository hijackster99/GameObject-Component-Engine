/**
 * 
 */
package com.hijackster99.engine.rendering;

import com.hijackster99.engine.core.Vector3f;

public class Attenuation extends Vector3f{

	/**
	 * 
	 * 
	 * 
	 * @param constant
	 * @param linear
	 * @param exponent
	 */
	
	public Attenuation(float constant, float linear, float exponent) {
		super(constant, linear, exponent);
	}

	public float getConstant() {
		return getX();
	}
	
	public float getLinear() {
		return getY();
	}
	
	public float getExponent() {
		return getZ();
	}
}
