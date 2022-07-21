/**
 * 
 */
package com.hijackster99.engine.components;

import com.hijackster99.engine.core.Transform;

/**
 * @author jonat
 *
 */
public interface CollisionHandler {

	public void handleCollision(PhysicsComponent obj1, Transform trans1, PhysicsComponent obj2, Transform trans2);
	
}
