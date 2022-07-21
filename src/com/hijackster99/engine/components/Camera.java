package com.hijackster99.engine.components;

import org.lwjgl.input.Mouse;

import com.hijackster99.engine.core.CoreEngine;
import com.hijackster99.engine.core.Matrix4f;
import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.RenderingEngine;
import com.hijackster99.engine.rendering.Shader;

public class Camera extends GameComponent{
	
	private Matrix4f projection;
	
	/**
	 * 
	 * Creates a camera.
	 * 
	 * Should be added via GameObject hierarchy.
	 * 
	 * @param fov The field of view of the camera.
	 * @param aspect The aspect ratio. Specifically: Window.getWidth()/Window.getHeight().
	 * @param zNear The near clipping plane.
	 * @param zFar The far clipping plane.
	 */
	
	public Camera(float fov, float aspect, float zNear, float zFar) {
		this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}

	@Override
	public void addToEngine(CoreEngine engine) {
		engine.getRenderingEngine().addCamera(this);
		
	}
	
	/**
	 * 
	 * @return
	 */
	
	public Matrix4f getViewProjection() {
		
		Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
		Vector3f cameraPos = getTransform().getTransformedPos().mult(-1);
		
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
		
		return projection.mult(cameraRotation.mult(cameraTranslation));
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#getId()
	 */
	@Override
	public int getId() {
		return 2;
	}
}
