package com.hijackster99.engine.rendering.resourceManagement;

import java.util.HashMap;

import com.hijackster99.engine.core.Vector3f;

public abstract class MappedValues {

	private HashMap<String, Vector3f> vector3Map;
	private HashMap<String, Float> floatMap;
	
	public MappedValues() {
		vector3Map = new HashMap<String, Vector3f>();
		floatMap = new HashMap<String, Float>();
	}
	
	protected void addVector3f(String name, Vector3f v) {
		vector3Map.put(name, v);
	}
	
	public Vector3f getVector3f(String name) {
		Vector3f result = vector3Map.get(name);
		if(result != null)
			return result;
		return new Vector3f(0, 0, 0);
	}

	protected void addFloat(String name, float f) {
		floatMap.put(name, f);
	}
	
	public float getFloat(String name) {
		Float result = floatMap.get(name);
		if(result != null) 
			return result;
		return 0.0f;
	}
	
}
