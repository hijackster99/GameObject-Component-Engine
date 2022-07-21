package com.hijackster99.engine.core;

import com.hijackster99.engine.rendering.RenderingEngine;

public abstract class Game {
	
	private CoreEngine engine;
	private GameObject rootObj = new GameObject("root");
	
	public Game(CoreEngine engine) {
		this.engine = engine;
	}
	
	public void init() {
		
	}
	
	public void input(float delta) {
		rootObj.inputAll(delta);
	}
	public void update(float delta) {
		rootObj.updateAll(delta);
	}
	
	public void render(RenderingEngine renderingEngine) {
		renderingEngine.render(rootObj);
	}

	/**
	 * @return the engine
	 */
	public CoreEngine getEngine() {
		return engine;
	}

	/**
	 * @param engine the engine to set
	 */
	public void setEngine(CoreEngine engine) {
		this.engine = engine;
	}

	/**
	 * @return the rootObj
	 */
	public GameObject getRootObj() {
		return rootObj;
	}
}
