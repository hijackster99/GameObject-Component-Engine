package com.hijackster99.engine.components;

import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.Shader;

public class DirectionalLight extends BaseLight{
	
	public DirectionalLight(Vector3f color, float intensity) {
		super(color, intensity);
		
		setShader(new Shader("forward-directional"));
	}

	public Vector3f getDirection() {
		return getTransform().getTransformedRot().getForward();
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.BaseLight#getId()
	 */
	@Override
	public int getId() {
		return 3;
	}
	
	@Override
	public String toString() {
		return "BaseLight[" + super.toString() + "], Direction[" + getTransform().getTransformedRot().getForward().toString() + "]";
	}
	
}
