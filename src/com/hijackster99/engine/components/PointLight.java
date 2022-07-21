package com.hijackster99.engine.components;

import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.Attenuation;
import com.hijackster99.engine.rendering.Shader;

public class PointLight extends BaseLight{

	private static final int COLOR_DEPTH = 256;
	
	private Attenuation atten;
	private float range;
	
	public PointLight(Vector3f color, float intensity, Attenuation atten) {
		super(color, intensity);
		this.atten = atten;
		
		float a = atten.getExponent();
		float b = atten.getLinear();
		float c = atten.getExponent() - COLOR_DEPTH * getIntensity() * getColor().getMax();
		
		this.range = (float)((-b + Math.sqrt(b * b - 4 * a * c))/2 * a);
		
		setShader(new Shader("forward-point"));
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}
	public float getConstant() {
		return atten.getX();
	}
	public float getLinear() {
		return atten.getY();
	}
	public float getExponent() {
		return atten.getZ();
	}
	public Attenuation getAtten() {
		return atten;
	}
	
}
