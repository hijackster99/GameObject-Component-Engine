package com.hijackster99.engine.rendering;

import java.util.HashMap;

import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.resourceManagement.MappedValues;

public class Material extends MappedValues{

	private HashMap<String, Texture> textureMap;
	
	public Material() {
		super();
		textureMap = new HashMap<String, Texture>();
	}
	
	public void addTexture(String name, Texture texture) {
		textureMap.put(name, texture);
	}
	
	public Texture getTexture(String name) {
		Texture result = textureMap.get(name);
		if(result != null) 
			return result;
		return new Texture("map.png");
	}
	
	@Override
	public void addFloat(String name, float f) {
		super.addFloat(name, f);
	}
	
	@Override
	public void addVector3f(String name, Vector3f v) {
		super.addVector3f(name, v);
	}
}
