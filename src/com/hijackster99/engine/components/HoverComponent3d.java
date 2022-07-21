/**
 * 
 */
package com.hijackster99.engine.components;

import org.lwjgl.input.Keyboard;

import com.hijackster99.engine.core.Input;
import com.hijackster99.engine.core.Vector2f;
import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.Window;

/**
 * @author jonat
 *
 */
public class HoverComponent3d extends GameComponent {

	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#update(float)
	 */
	@Override
	public void update(float delta) {
		float x = Input.getMousePosition().sub(Window.getCenter()).getX();
		float y = Input.getMousePosition().sub(Window.getCenter()).getY();
		
		float adj = (float) (1d/(Math.tan(Math.toRadians(35)) / Window.getCenter().getY()));
		
		boolean rotY = x != 0;
		boolean rotX = y != 0;
		
		if(rotX) {
			getTransform().rotate(getTransform().getRot().getLeft(), (float) Math.atan(y/adj));
		}
		
		if(rotY) {
			getTransform().rotate(getTransform().getRot().getUp(), (float) Math.atan(x/adj));
		}
		
		//Encode.addHoverCheck(getTransform().getTransformedPos(), getTransform().getRot().getForward(), getParent());
		
		if(rotX) {
			getTransform().rotate(getTransform().getRot().getLeft(), -1 * (float) Math.atan(y/adj));
		}
		
		if(rotY) {
			getTransform().rotate(getTransform().getRot().getUp(), -1 * (float) Math.atan(x/adj));
		}
		
	}
	
}
