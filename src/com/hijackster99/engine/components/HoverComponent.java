/**
 * 
 */
package com.hijackster99.engine.components;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hijackster99.engine.core.Input;
import com.hijackster99.engine.core.Vector2f;
import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.Material;
import com.hijackster99.engine.rendering.Mesh2d;
import com.hijackster99.engine.rendering.RenderingEngine;
import com.hijackster99.engine.rendering.Shader;
import com.hijackster99.engine.rendering.Texture;
import com.hijackster99.engine.rendering.Vertex;

/**
 * @author jonat
 *
 */
public class HoverComponent extends GameComponent{

	private Vector2f pos;
	private Vector2f dim;
	private Method method;
	private Shader shader;
	private RenderingEngine engine;
	
	public HoverComponent(Vector2f pos, Vector2f dim, Method method, Shader shader, RenderingEngine engine) {
		this.pos = pos;
		this.dim = dim;
		this.method = method;
		this.shader = shader;
		this.engine = engine;
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#input(float)
	 */
	@Override
	public void input(float delta) {
		Vector2f mousePos = Input.getMousePosition();
		if(mousePos.getX() >= pos.getX() && mousePos.getY() >= pos.getY() && mousePos.getX() <= pos.getX() + dim.getX() && mousePos.getY() <= pos.getY() + dim.getY()) {
			try {
				method.invoke(this, shader, engine);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void highLight(Shader shader, RenderingEngine engine) {
		Vertex[] vertices = new Vertex[] {new Vertex(new Vector3f(pos.getX(), pos.getY(), 0), new Vector2f(0, 0)), new Vertex(new Vector3f(pos.getX() + dim.getX(), pos.getY(), 0), new Vector2f(50, 0)), new Vertex(new Vector3f(pos.getX(), pos.getY() + dim.getY(), 0), new Vector2f(0, 100)), new Vertex(new Vector3f(pos.getX() + dim.getX(), pos.getY() + dim.getY(), 0), new Vector2f(50, 100))};
		int[] indices = new int[] {0, 1, 2, 1, 3, 2};
		Mesh2d mesh = new Mesh2d(vertices, indices, true);
		Material mat = new Material();
		mat.addTexture("highlight.png", new Texture("highlight.png"));
		mat.addFloat("specularPower", 0);
		mat.addFloat("specularIntensity", 0);
		MeshRenderer2d rend = new MeshRenderer2d(mesh, mat);
		rend.render(shader, engine);
	}

	/**
	 * @return the pos
	 */
	public Vector2f getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(Vector2f pos) {
		this.pos = pos;
	}

	/**
	 * @return the dim
	 */
	public Vector2f getDim() {
		return dim;
	}

	/**
	 * @param dim the dim to set
	 */
	public void setDim(Vector2f dim) {
		this.dim = dim;
	}

	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(Method method) {
		this.method = method;
	}
	
}
