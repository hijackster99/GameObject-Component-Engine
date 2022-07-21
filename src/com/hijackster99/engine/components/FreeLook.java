/**
 * 
 */
package com.hijackster99.engine.components;

import org.lwjgl.input.Keyboard;

import com.hijackster99.engine.core.Input;
import com.hijackster99.engine.core.Transform;
import com.hijackster99.engine.core.Vector2f;
import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.Window;

public class FreeLook extends GameComponent{

	public static final Vector3f yAxis = new Vector3f(0, 1, 0);
	private float sens = 0.02f;
	private boolean mouseLocked;

	public FreeLook(float sens, boolean mouseLocked) {
		this.sens = sens;
		this.mouseLocked = mouseLocked;
		Input.setCursor(!mouseLocked);
		Input.setMousePosition(new Vector2f(Window.getWidth()/2f, Window.getHeight()/2f));
	}
	
	public void input(float delta) {
		if(Input.getKeyDown(Keyboard.KEY_TAB)) {
			setMouseLocked(!mouseLocked);
		}
		float rotAmt = (float)(100 * delta);
		if(!mouseLocked) {
			if(Input.getKey(Keyboard.KEY_UP)) {
				getTransform().rotate(getTransform().getRot().getRight(), (float)Math.toRadians(-rotAmt));
			}
			if(Input.getKey(Keyboard.KEY_DOWN)) {
				getTransform().rotate(getTransform().getRot().getRight(), (float)Math.toRadians(rotAmt));
			}
			if(Input.getKey(Keyboard.KEY_LEFT)) {
				getTransform().rotate(yAxis, (float)Math.toRadians(-rotAmt));
			}
			if(Input.getKey(Keyboard.KEY_RIGHT)) {
				getTransform().rotate(yAxis, (float)Math.toRadians(rotAmt));
			}
		}else {
			Vector2f deltaPos = Input.getMousePosition().sub(new Vector2f(Window.getWidth()/2f, Window.getHeight()/2f));
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY)
				getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sens));
			if(rotX && (Vector3f.getAngle(getTransform().getRot().getForward(), new Vector3f(0, 1, 0)) > Math.abs(Math.toRadians(deltaPos.getY() * sens)) * 2 || Math.toRadians(deltaPos.getY() * sens) < 0) && (Vector3f.getAngle(getTransform().getRot().getForward(), new Vector3f(0, -1, 0)) > Math.abs(Math.toRadians(deltaPos.getY() * sens)) * 2 || Math.toRadians(deltaPos.getY() * sens) > 0))
				getTransform().rotate(getTransform().getRot().getLeft(), (float) Math.toRadians(deltaPos.getY() * sens));
			
			Input.setMousePosition(new Vector2f(Window.getWidth()/2f, Window.getHeight()/2f));
		}
	}

	/** (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#update(float)
	 */
	@Override
	public void update(float delta) {
		super.update(delta);
		Input.setCursor(!mouseLocked);
	}

	public float getSens() {
		return sens;
	}

	public void setSens(float sens) {
		this.sens = sens;
	}

	/**
	 * @return the mouseLocked
	 */
	public boolean isMouseLocked() {
		return mouseLocked;
	}

	/**
	 * @param mouseLocked the mouseLocked to set
	 * 		  Auto-hides cursor when appropriate
	 */
	public void setMouseLocked(boolean mouseLocked) {
		this.mouseLocked = mouseLocked;
		if(mouseLocked) 
			Input.setMousePosition(new Vector2f(Window.getWidth()/2f, Window.getHeight()/2f));
		Input.setCursor(!mouseLocked);
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#getId()
	 */
	@Override
	public int getId() {
		return 4;
	}
	
	/** (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#enable()
	 */
	@Override
	public GameComponent enable() {
		if(mouseLocked)
			Input.setMousePosition(new Vector2f(Window.getWidth()/2f, Window.getHeight()/2f));
		return super.enable();
	}
	
}
