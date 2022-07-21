/**
 * 
 */
package com.hijackster99.engine.components;

import com.hijackster99.engine.core.Input;
import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.Window;

/**
 * @author jonat
 *
 */
public class PanLook extends GameComponent {
	
	private float scrollSpeed;
	
	public PanLook(float scrollSpeed) {
		this.scrollSpeed = scrollSpeed;
		Input.setCursor(true);
	}

	/** (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#update(float)
	 */
	@Override
	public void input(float delta) {
		Vector3f dir = getDir();
		if(!dir.equals(new Vector3f(0, 0, 0))) {
			PhysicsComponent comp = getParent().getComponent(PhysicsComponent.class);
			if(comp != null)
				comp.setVelocity(dir.mult(scrollSpeed));
		}
	}
	
	public Vector3f getDir() {
		Vector3f res = new Vector3f(0, 0, 0);
		
		if(Input.getMousePosition().getX() == 0) res = res.add(new Vector3f(getTransform().getTransformedRot().getLeft().getX(), 0, getTransform().getTransformedRot().getLeft().getZ()));
		else if(Input.getMousePosition().getX() == Window.getWidth() - 1) res = res.add(new Vector3f(getTransform().getTransformedRot().getRight().getX(), 0, getTransform().getTransformedRot().getRight().getZ()));
		
		if(Input.getMousePosition().getY() == 0) res = res.add(new Vector3f(getTransform().getTransformedRot().getBack().getX(), 0, getTransform().getTransformedRot().getBack().getZ()));
		else if(Input.getMousePosition().getY() == Window.getHeight() - 1) res = res.add(new Vector3f(getTransform().getTransformedRot().getForward().getX(), 0, getTransform().getTransformedRot().getForward().getZ()));
		
		return res.normalized();
	}
	
	/** (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#update(float)
	 */
	@Override
	public void update(float delta) {
		super.update(delta);
		Input.setCursor(true);
	}
	
	public float getScrollSpeed() {
		return scrollSpeed;
	}

	public void setScrollSpeed(float scrollSpeed) {
		this.scrollSpeed = scrollSpeed;
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#getId()
	 */
	@Override
	public int getId() {
		return 7;
	}
	
}
