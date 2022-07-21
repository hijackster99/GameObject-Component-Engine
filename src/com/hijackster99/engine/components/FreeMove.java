package com.hijackster99.engine.components;

import org.lwjgl.input.Keyboard;

import com.hijackster99.engine.core.Input;
import com.hijackster99.engine.core.Vector3f;

public class FreeMove extends GameComponent{

	private float speed;
	private int forwardKey;
	private int backKey;
	private int leftKey;
	private int rightKey;
	private int upKey;
	private int downKey;
	private boolean upDown;
	
	private PhysicsComponent physicsComponent;
	
	public FreeMove(float speed, boolean upDown, PhysicsComponent physicsComponent) {
		this(speed, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_D, upDown, physicsComponent);
	}
	
	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey, boolean upDown, PhysicsComponent physicsComponent) {
		this(speed, forwardKey, backKey, leftKey, rightKey, Keyboard.KEY_SPACE, Keyboard.KEY_LSHIFT, upDown, physicsComponent);
	}
	
	private FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey, int upKey, int downKey, boolean upDown, PhysicsComponent physicsComponent) {
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.upKey = upKey;
		this.downKey = downKey;
		this.upDown = upDown;
		this.physicsComponent = physicsComponent;
	}
	
	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey, int upKey, int downKey, PhysicsComponent physicsComponent) {
		this(speed, forwardKey, backKey, leftKey, rightKey, upKey, downKey, true, physicsComponent);
	}
	
	public void input(float delta) {
		Vector3f velocity = Vector3f.ZERO;
		if(Input.getKey(forwardKey)) {
			velocity = velocity.add(new Vector3f(getTransform().getRot().getForward().getX(), 0, getTransform().getRot().getForward().getZ()).normalized());
		}if(Input.getKey(backKey)) {
			velocity = velocity.add(new Vector3f(getTransform().getRot().getBack().getX(), 0, getTransform().getRot().getBack().getZ()).normalized());
		}if(Input.getKey(leftKey)) {
			velocity = velocity.add(new Vector3f(getTransform().getRot().getLeft().getX(), 0, getTransform().getRot().getLeft().getZ()).normalized());
		}if(Input.getKey(rightKey)) {
			velocity = velocity.add(new Vector3f(getTransform().getRot().getRight().getX(), 0, getTransform().getRot().getRight().getZ()).normalized());
		}
		if(upDown) {
			if(Input.getKey(upKey)) {
				velocity = velocity.add(new Vector3f(0, getTransform().getRot().getUp().getY(), 0).normalized());
			}if(Input.getKey(downKey)) {
				velocity = velocity.add(new Vector3f(0, getTransform().getRot().getDown().getY(), 0).normalized());
			}
		}
		physicsComponent.setVelocity(velocity.normalized().mult(speed));
	}

	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @return the forwardKey
	 */
	public int getForwardKey() {
		return forwardKey;
	}

	/**
	 * @param forwardKey the forwardKey to set
	 */
	public void setForwardKey(int forwardKey) {
		this.forwardKey = forwardKey;
	}

	/**
	 * @return the backKey
	 */
	public int getBackKey() {
		return backKey;
	}

	/**
	 * @param backKey the backKey to set
	 */
	public void setBackKey(int backKey) {
		this.backKey = backKey;
	}

	/**
	 * @return the leftKey
	 */
	public int getLeftKey() {
		return leftKey;
	}

	/**
	 * @param leftKey the leftKey to set
	 */
	public void setLeftKey(int leftKey) {
		this.leftKey = leftKey;
	}

	/**
	 * @return the rightKey
	 */
	public int getRightKey() {
		return rightKey;
	}

	/**
	 * @param rightKey the rightKey to set
	 */
	public void setRightKey(int rightKey) {
		this.rightKey = rightKey;
	}

	/**
	 * @return the upKey
	 */
	public int getUpKey() {
		return upKey;
	}

	/**
	 * @param upKey the upKey to set
	 */
	public void setUpKey(int upKey) {
		this.upKey = upKey;
	}

	/**
	 * @return the downKey
	 */
	public int getDownKey() {
		return downKey;
	}

	/**
	 * @param downKey the downKey to set
	 */
	public void setDownKey(int downKey) {
		this.downKey = downKey;
	}

	/**
	 * @return the upDown
	 */
	public boolean isUpDown() {
		return upDown;
	}

	/**
	 * @param upDown the upDown to set
	 */
	public void setUpDown(boolean upDown) {
		this.upDown = upDown;
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#getId()
	 */
	@Override
	public int getId() {
		return 5;
	}

	/**
	 * @return the physicsComponent
	 */
	public PhysicsComponent getPhysicsComponent() {
		return physicsComponent;
	}

	/**
	 * @param physicsComponent the physicsComponent to set
	 */
	public void setPhysicsComponent(PhysicsComponent physicsComponent) {
		this.physicsComponent = physicsComponent;
	}
	
}
