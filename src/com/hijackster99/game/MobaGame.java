/**
 * 
 */
package com.hijackster99.game;


import com.hijackster99.engine.core.CoreEngine;
import com.hijackster99.engine.core.Game;
import com.hijackster99.engine.core.Quaternion;
import com.hijackster99.engine.core.Vector3f;
import com.hijackster99.engine.rendering.RenderingEngine;

/**
 * @author jonat
 *
 */
public class MobaGame extends Game{
	
	/**
	 * @param engine
	 */
	public MobaGame(CoreEngine engine) {
		super(engine);
	}

	/* (non-Javadoc)
	 * @see com.hijackster99.engine.core.Game#init()
	 */
	@Override
	public void init() {
		super.init();
		Objects.init(this);
		Objects.MAP.getTransform().setPos(0,  -5,  0);
		Objects.CAMERA.getTransform().setPos(0, -3, 0);
		Objects.CAMERA.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(15)));
		//Objects.CAMERA.getComponents().get(1).disable();
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.core.Game#input(float)
	 */
	@Override
	public void input(float delta) {
		super.input(delta);
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.core.Game#update(float)
	 */
	@Override
	public void update(float delta) {
		super.update(delta);
		
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.core.Game#render(com.hijackster99.engine.rendering.RenderingEngine)
	 */
	@Override
	public void render(RenderingEngine renderingEngine) {
		super.render(renderingEngine);
	}
	
}
