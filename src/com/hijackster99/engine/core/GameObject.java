package com.hijackster99.engine.core;

import java.util.ArrayList;

import com.hijackster99.engine.components.GameComponent;
import com.hijackster99.engine.rendering.RenderingEngine;
import com.hijackster99.engine.rendering.Shader;

public class GameObject {

	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transform transform;
	private CoreEngine engine;
	private boolean enabled = true;
	private String identifier;
	
	public GameObject(String identifier) {
		this.identifier = identifier;
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
	}
	
	public void addChild(GameObject child) {
		children.add(child);
		child.setEngine(engine);
		child.getTransform().setParent(transform);
	}

	public GameObject addComponent(GameComponent component) {
		components.add(component);
		component.setParent(this);
		component.init();
		
		return this;
	}
	
	public void inputAll(float delta) {
		input(delta);
		
		for(GameObject child : children)
			if(child.enabled())
				child.inputAll(delta);
	}
	
	public void updateAll(float delta) {
		update(delta);
		
		for(GameObject child : children)
			if(child.enabled())
				child.updateAll(delta);
	}
	
	public void renderAll(Shader shader, RenderingEngine renderingEngine) {
		render(shader, renderingEngine);
		
		for(GameObject child : children)
			if(child.enabled())
				child.renderAll(shader, renderingEngine);
	}
	
	public void input(float delta) {
		for(GameComponent component : components) {
			if(component.enabled()) {
				component.input(delta);
			}
		}
	}

	public void update(float delta) {
		transform.update(delta);
		
		for(GameComponent component : components) 
			if(component.enabled())
				component.update(delta);
	}

	public void render(Shader shader, RenderingEngine renderingEngine) {
		for(GameComponent component : components)
			if(component.enabled())
				component.render(shader, renderingEngine);
	}
	
	public ArrayList<GameObject> getAllAttached() {
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		for(GameObject child : children)
			result.addAll(child.getAllAttached());
		
		result.add(this);
		return result;
	}
	
	public Transform getTransform() {
		
		return transform;
		
	}
	
	public void setEngine(CoreEngine engine) {
		this.engine = engine;
		
		for(GameComponent component : components) {
			component.addToEngine(engine);
		}
	}

	/**
	 * @return the engine
	 */
	public CoreEngine getEngine() {
		return engine;
	}
	
	public boolean enabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public GameObject enable() {
		enabled = true;
		
		return this;
	}
	
	public GameObject disable() {
		enabled = false;
		
		return this;
	}

	/**
	 * @return the components
	 */
	public ArrayList<GameComponent> getComponents() {
		return components;
	}
	
	public <G> G getComponent(Class<G> type){
		for(GameComponent c : components) {
			
			if(c.getClass().isInstance(type)) {
				return type.cast(c);
			}
		}
		return null;
	}

	public <G> ArrayList<G> getComponents(Class<G> type){
		ArrayList<G> list = new ArrayList<G>();
		for(GameComponent c : components) {
			
			if(c.getClass().isInstance(type)) {
				list.add(type.cast(c));
			}
		}
		if(!list.isEmpty())
			return list;
		return null;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
