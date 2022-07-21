package com.hijackster99.engine.components;

import com.hijackster99.engine.core.CoreEngine;
import com.hijackster99.engine.core.GameObject;
import com.hijackster99.engine.core.Transform;
import com.hijackster99.engine.rendering.RenderingEngine;
import com.hijackster99.engine.rendering.Shader;

public abstract class GameComponent {

	private GameObject parent;
	private boolean enabled = true;
	
	public void init() {}
	
	public void input(float delta) {}
	
	public void update(float delta) {}
	
	public void render(Shader shader, RenderingEngine renderingEngine) {}
	
	public void setParent(GameObject parent) {
		
		this.parent = parent;
		
	}
	
	public GameObject getParent() {
		return parent;
	}
	
	public Transform getTransform() {
		
		return parent.getTransform();
		
	}
	
	/**
	 * 
	 * Adds this object to the rendering engine so the rendering engine can process
	 * it.
	 * 
	 * @param CoreEngine The core engine used to access the rendering engine and the physics engine.
	 */
	
	public void addToEngine(CoreEngine engine) {}
	
	public boolean enabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public GameComponent enable() {
		enabled = true;
		
		return this;
	}
	
	public GameComponent disable() {
		enabled = false;
		
		return this;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return 0;
	}

	
}
