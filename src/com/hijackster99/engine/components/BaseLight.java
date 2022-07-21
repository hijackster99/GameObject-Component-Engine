package com.hijackster99.engine.components;

import com.hijackster99.engine.core.CoreEngine;
import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.Shader;

public class BaseLight extends GameComponent {

	private Vector3f color;
	private float intensity;
	private Shader shader;

	/**
	 * 
	 * Creates a basic light.
	 * 
	 * All lights should extend this.
	 * 
	 * Should be added via GameObject hierarchy.
	 * 
	 * @param color The color of the light.
	 * @param intensity The intensity of the light.
	 */

	public BaseLight(Vector3f color, float intensity) {
		this.color = color;
		this.intensity = intensity;
	}

	@Override
	public void addToEngine(CoreEngine engine) {
		engine.getRenderingEngine().addLight(this);
	}

	/**
	 * 
	 * Sets the shader.
	 * 
	 * @param shader The shader to be set.
	 */

	public void setShader(Shader shader) {
		this.shader = shader;
	}

	/**
	 * 
	 * Gets the shader.
	 * 
	 * @return The shader.
	 */

	public Shader getShader() {
		return shader;
	}

	/**
	 * 
	 * Gets the color.
	 * 
	 * @return The color.
	 */

	public Vector3f getColor() {
		return color;
	}

	/**
	 * 
	 * Sets the color.
	 * 
	 * @param color The color to be set.
	 */

	public void setColor(Vector3f color) {
		this.color = color;
	}

	/**
	 * 
	 * Gets the Intensity.
	 * 
	 * @return The intensity.
	 */
	
	public float getIntensity() {
		return intensity;
	}

	/**
	 * 
	 * Sets the intensity.
	 * 
	 * @param intensity The intensity to be set.
	 */
	
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#getId()
	 */
	@Override
	public int getId() {
		return 1;
	}
	
	@Override
	public String toString() {
		return "Color[" + color.toString() + "], Intensity[" + intensity + "]";
	}

}
