/**
 * 
 */
package com.hijackster99.engine.components;

import java.util.ArrayList;

import com.hijackster99.engine.core.CoreEngine;
import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.physics.Rotation;

/**
 * @author jonat
 *
 */
public class PhysicsComponent extends GameComponent{
	
	private float mass;
	private float terminal;
	private ArrayList<Vector3f> acceleration;
	private Vector3f velocity;
	private Vector3f netVel;
	private Rotation rot;
	
	public PhysicsComponent(float mass) {
		this.mass = mass;
		acceleration = new ArrayList<Vector3f>();
		velocity = Vector3f.ZERO;
		rot = new Rotation(new Vector3f(1, 0, 0), 0);
	}
	
	public PhysicsComponent(float mass, float terminal) {
		this.mass = mass;
		this.terminal = terminal;
		acceleration = new ArrayList<Vector3f>();
		velocity = Vector3f.ZERO;
		rot = new Rotation(new Vector3f(1, 0, 0), 0);
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#addToEngine(com.hijackster99.engine.core.CoreEngine)
	 */
	@Override
	public void addToEngine(CoreEngine engine) {
		engine.getPhysicsEngine().addPhysicsComponent(this);
	}
	
	public void addForce(Vector3f vec3) {
		acceleration.add(vec3.div(mass));
	}
	public void setVelocity(Vector3f vec3) {
		acceleration.clear();
		velocity = new Vector3f(vec3.getX(), vec3.getY(), vec3.getZ());
	}
	
	public void setRot(Rotation rot) {
		this.rot = rot;
	}
	
	public void setRot(Vector3f axis, float angularVelocity) {
		this.rot = new Rotation(axis, angularVelocity);
	}
	
	public void setTerminalVelocity(float terminal) {
		this.terminal = terminal;
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#update(float)
	 */
	@Override
	public void update(float delta) {
		netVel = Vector3f.ZERO;
		for(Vector3f v : acceleration) {
			netVel = netVel.add(v.mult(delta));
		}
		netVel = netVel.add(velocity);
		if(netVel.length() > terminal)
			netVel = netVel.normalized().mult(terminal);
		
	}

	/**
	 * @return the mass
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(float mass) {
		this.mass = mass;
	}
	
	public Vector3f getNetVel() {
		return netVel;
	}
	
	public Rotation getNetRot() {
		return rot;
	}

}
