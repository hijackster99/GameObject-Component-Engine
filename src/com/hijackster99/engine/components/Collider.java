/**
 * 
 */
package com.hijackster99.engine.components;

import com.hijackster99.engine.core.Transform;
import com.hijackster99.engine.core.Vector3f;

/**
 * @author jonat
 *
 */
public abstract class Collider extends GameComponent{

	private boolean checked;
	private Transform transform;
	private boolean enabled;
	
	protected Collider() {
		transform = new Transform();
	}
	
	public void setParent(Transform parent) {
		transform.setParent(parent);
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}
	
	public void input(float delta) {
		transform.update(delta);
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public static boolean collides(Collider c1, Transform trans1, Collider c2, Transform trans2) {
		//System.out.println(c1.toString() + " " + c2.toString());
		if(c1 instanceof BoundingCylinder) {
			
			if(c2 instanceof BoundingCylinder) {
				return collides((BoundingCylinder) c1, trans1, (BoundingCylinder) c2, trans2);
			}else {
				System.err.println("Unknown Collider Detected: " + c2);
				return false;
			}
		}else {
			System.err.println("Unknown Collider Detected: " + c1);
			return false;
		}
	}
	
	private static boolean collides(BoundingCylinder bc1, Transform trans1, BoundingCylinder bc2, Transform trans2) {
		if(trans2.getPos().getY() > trans1.getPos().getY() + bc1.getHeight() ^ trans1.getPos().getY() > trans2.getPos().getY() + bc2.getHeight()) return false;
		
		if(trans1.getPos().sub(trans2.getPos()).length() < bc1.getRadius() + bc2.getRadius()) return true;
		
		return false;
	}
	
	public abstract Vector3f getNormal(Vector3f pos);

	/**
	 * @return the transform
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public boolean enabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Collider enable() {
		enabled = true;
		
		return this;
	}
	
	public Collider disable() {
		enabled = false;
		
		return this;
	}
	
	//public abstract void handleCollisions();

}
