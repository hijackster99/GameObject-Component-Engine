package com.hijackster99.engine.components;

import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.Attenuation;
import com.hijackster99.engine.rendering.Shader;

public class SpotLight extends PointLight{

	private float cutoff;
	
	public SpotLight(int id, Vector3f color, float intensity, Attenuation atten, float cutoff) {
		super(color, intensity, atten);
		this.cutoff = cutoff;
		
		setShader(new Shader("forward-spot"));
	}
	
	public Vector3f getDirection() {
		return getTransform().getTransformedRot().getForward();
	}
	public float getCutoff() {
		return cutoff;
	}
	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}
	
}
