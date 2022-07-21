package com.hijackster99.engine.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BiMap<h, k> extends HashMap<h, k> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4509071057173948596L;
	
	private HashMap<k, h> flip = new HashMap<k, h>();
	
	@Override
	public k put(h key, k value) {
		flip.put(value, key);
		return super.put(key, value);
	}
	
	public h getKey(k obj) {
		return flip.get(obj);
	}

	public k removeKey(k obj) {
		return remove(flip.remove(obj));
	}
	
	@Override
	public void clear() {
		flip.clear();
		super.clear();
	}
	
	@Override
	public void putAll(Map<? extends h, ? extends k> m) {
		Iterator<h> iter = keySet().iterator();
		while(iter.hasNext()) {
			h key = iter.next();
			flip.put(get(key), key);
		}
		super.putAll(m);
	}
	
	@Override
	public Object clone() {
		BiMap<h, k> result = new BiMap<h, k>();
		result.putAll(this);
		return result;
	}
	
	@Override
	public boolean replace(h key, k oldValue, k newValue) {
		if(flip.get(oldValue).equals(key)) {
			flip.remove(oldValue);
			flip.put(newValue, key);
		}
		return super.replace(key, oldValue, newValue);
	}
	@Override
	public k replace(h key, k value) {
		flip.remove(get(key));
		flip.put(value, key);
		return super.replace(key, value);
	}
	
	

}
