/**
 * 
 */
package com.hijackster99.engine.physics;

import java.util.ArrayList;

import com.hijackster99.engine.components.Collider;
import com.hijackster99.engine.components.CollisionHandler;
import com.hijackster99.engine.components.PhysicsComponent;
import com.hijackster99.engine.core.DoubleArrayList;
import com.hijackster99.engine.core.GameObject;
import com.hijackster99.engine.core.Quaternion;
import com.hijackster99.engine.core.Transform;
import com.hijackster99.engine.core.Vector3f;

/**
 * @author jonat
 *
 */
public class PhysicsEngine {

	private ArrayList<PhysicsComponent> components;
	private DoubleArrayList<PhysicsComponent, Transform> simulation;
	private DefaultCollisionHandler defHandler;
	
	public PhysicsEngine() {
		components = new ArrayList<PhysicsComponent>();
		simulation = new DoubleArrayList<PhysicsComponent, Transform>();
		defHandler = new DefaultCollisionHandler();
	}
	
	public void simulate(float delta) {
		for(int i = 0; i < components.size(); i++) {
			Transform o = new Transform(components.get(i).getTransform());
			
			o.setPos(o.getPos().add(components.get(i).getNetVel().mult(delta)));
			o.rotate(components.get(i).getNetRot().getAxis(), components.get(i).getNetRot().getAngularVelocity() * delta);
			
			simulation.add(components.get(i), o);
		}
	}
	
	public void checkSimulation() {
		DoubleArrayList<PhysicsComponent, Transform> notCollided = simulation.clone();
		for(int i = 0; i < simulation.size(); i++) {
			for(int j = i + 1; j < simulation.size(); j++) {
				if(checkCollision(simulation.getFirst(i), simulation.getSecond(i), simulation.getFirst(j), simulation.getSecond(j))) {
					CollisionHandler collHand = simulation.getFirst(i).getParent().getComponent(CollisionHandler.class);
					if(collHand != null)
						collHand.handleCollision(simulation.getFirst(i), simulation.getSecond(i), simulation.getFirst(j), simulation.getSecond(j));
					else 
						defHandler.handleCollision(simulation.getFirst(i), simulation.getSecond(i), simulation.getFirst(j), simulation.getSecond(j));
					notCollided.removeFirst(simulation.getFirst(i));
					notCollided.removeFirst(simulation.getFirst(j));
				}
			}
		}
		for(int i = 0; i < notCollided.size(); i++) {
			if(notCollided.getFirst(i).getParent().getIdentifier().equals("camera")) {
				//System.out.println(notCollided.getFirst(i));
			}
			notCollided.getFirst(i).getTransform().setTransform(notCollided.getSecond(i));
		}
		cleanUp();
	}
	
//	public void checkCollisions(){
//		for(int i = 0; i < abstractColliders.size(); i++) {
//			for(int j = 0; j < position.size(); j++) {
//				if(checkCollision(abstractColliders.get(i), position.getFirst(j), position.getSecond(j))) {
//					abstractColliders.get(i).handleCollisions();
//				}
//			}
//		}
//		ArrayList<GameObject> objs = Game.getWorldObj().getAllAttached();
//		objs.removeAll(position.getAllFirst());
//		for(int i = 0; i < abstractColliders.size(); i++) {
//			for(int j = 0; j < objs.size(); j++) {
//				if(checkCollision(abstractColliders.get(i), objs.get(i), objs.get(i).getTransform().getTransformedPos())) {
//					abstractColliders.get(i).handleCollisions();
//				}
//			}
//		}
//	}
	
//	public boolean willCollide(Vector3f dir, float amount, GameObject obj) {
//		@SuppressWarnings("unchecked")
//		ArrayList<GameObject> objs = (ArrayList<GameObject>) Registry.REGISTRY.getRegistry("source", "collide").getEntries();
//		objs.remove(obj);
//		
//		Transform o = obj.getTransform();
//		
//		Vector3f newPos = o.getPos();
//		newPos = o.transform(newPos.add(dir.mult(amount)));
//		
//		for(int i = 0; i < objs.size(); i++) {
//			if(checkCollision(obj, newPos, objs.get(i), objs.get(i).getTransform().getTransformedPos())) return true;
//		}
//		return false;
//	}
	
	public boolean checkCollision(PhysicsComponent obj1, Transform trans1, PhysicsComponent obj2, Transform trans2) {
		ArrayList<Collider> cols1 = obj1.getParent().getComponents(Collider.class);
		if(cols1 != null) {
			for(Collider c1 : cols1) {
				if(c1.enabled()) {
					ArrayList<Collider> cols2 = obj2.getParent().getComponents(Collider.class);
					if(cols2 != null) {
						for(Collider c2 : cols2) {
						if(c2.enabled())
							if(Collider.collides(c1, trans1, c2, trans2)) return true;
						return false;
						}
					}
				}
				return false;
			}
		}
		return false;
	}
//	
//	public boolean checkCollision(Collider c, GameObject obj2, Vector3f pos2) {
//		if(c.enabled()) {
//			for(Collider c2 : obj2.getColliders()) {
//				if(c2.enabled())
//					if(Collider.collides(c, c.getTransform().getTransformedPos(), c2, pos2)) return true;
//			}
//		}
//		return false;
//	}
	
	public void move(DoubleArrayList<GameObject, Vector3f> objs) {
		for(int i = 0; i < objs.size(); i++) {
			objs.getFirst(i).getTransform().setPos(objs.getFirst(i).getTransform().getPos().add(objs.getSecond(i).sub(objs.getFirst(i).getTransform().getTransformedPos())));
			//Encode.addMoveCommand(objs.getFirst(i).getTransform().getPos().add(objs.getSecond(i).sub(objs.getFirst(i).getTransform().getTransformedPos())), objs.getFirst(i));
		}
	}
	
	public void cleanUp() {
		simulation.clear();
	}
	
	public void addPhysicsComponent(PhysicsComponent physicsComponent) {
		components.add(physicsComponent);
	}
	
}
