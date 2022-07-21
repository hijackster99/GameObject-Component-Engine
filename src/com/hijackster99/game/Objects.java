/**
 * 
 */
package com.hijackster99.game;

import java.util.HashMap;
import java.util.Map;

import com.hijackster99.engine.components.Camera;
import com.hijackster99.engine.components.FreeLook;
import com.hijackster99.engine.components.FreeMove;
import com.hijackster99.engine.components.MeshRenderer;
import com.hijackster99.engine.components.PanLook;
import com.hijackster99.engine.components.PhysicsComponent;
import com.hijackster99.engine.core.Game;
import com.hijackster99.engine.core.GameObject;
import com.hijackster99.engine.rendering.Material;
import com.hijackster99.engine.rendering.Mesh;
import com.hijackster99.engine.rendering.Texture;

/**
 * @author jonat
 *
 */
public class Objects {

	public static Map<String, GameObject> objs;
	public static GameObject MAP;
	public static GameObject CAMERA;
	public static GameObject BOX;
	public static GameObject SPHERE;
	
	public static void init(Game game) {
		//MAP
		Mesh mesh = new Mesh("map.obj");
		Material mapMaterial = new Material();
		mapMaterial.addTexture("map.png", new Texture("map.png"));
		mapMaterial.addFloat("specularPower", 0);
		mapMaterial.addFloat("specularIntensity", 0);
		MeshRenderer meshRend = new MeshRenderer(mesh, mapMaterial);
		MAP = new GameObject("map");
		MAP.addComponent(meshRend.enable());
		MAP.setEngine(game.getEngine());
		
		//CAMERA
		CAMERA = new GameObject("camera");
		Camera camera = new Camera((float) Math.toRadians(70.0f), 16f / 9f, 0.01f, 1000.0f);
		PhysicsComponent phys1 = new PhysicsComponent(1, 10);
		CAMERA.addComponent(camera.enable()).addComponent(new FreeLook(0.3f, true).enable()).addComponent(new FreeMove(10.0f, true, phys1).disable()).addComponent(new PanLook(10f).enable()).addComponent(phys1.enable())/*.addComponent(new HoverComponent3d())*/;
		CAMERA.setEngine(game.getEngine());
		
		//BOX
		BOX = new GameObject("box");
		PhysicsComponent phys2 = new PhysicsComponent(1, 10);
		Objects.BOX.addComponent(new FreeMove(10.0f, false, phys2).disable()).addComponent(phys2.enable());
		BOX.setEngine(game.getEngine());
		
		//SPHERE
		SPHERE = new GameObject("sphere");
		PhysicsComponent phys3 = new PhysicsComponent(1, 10);
		Objects.SPHERE.addComponent(new FreeMove(10.0f, false, phys3).disable()).addComponent(phys3.enable());
		SPHERE.setEngine(game.getEngine());

		//Map
		objs = new HashMap<String, GameObject>();
		objs.put(MAP.getIdentifier(), MAP);
		game.getRootObj().addChild(MAP);
		objs.put(CAMERA.getIdentifier(), CAMERA);
		game.getRootObj().addChild(CAMERA);
		objs.put(BOX.getIdentifier(), BOX);
		game.getRootObj().addChild(BOX);
		objs.put(SPHERE.getIdentifier(), SPHERE);
		game.getRootObj().addChild(SPHERE);
	}
	
}
