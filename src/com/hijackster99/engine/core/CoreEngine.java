package com.hijackster99.engine.core;

import com.hijackster99.engine.physics.PhysicsEngine;
import com.hijackster99.engine.rendering.RenderingEngine;
import com.hijackster99.engine.rendering.Window;

public class CoreEngine {

	private Game game;
	private boolean isRunning;
	private double frameTime;
	private RenderingEngine renderingEngine;
	private PhysicsEngine physicsEngine;
	
	public CoreEngine(double framerate, Game game) {
		this.isRunning = false;
		this.game = game;
		this.frameTime = 1.0/framerate;
	}
	
	public void createWindow(int width, int height, String title) {
		
		Window.createWindow(width, height, title);
		this.renderingEngine = new RenderingEngine();
		this.physicsEngine = new PhysicsEngine();
		this.game.getRootObj().setEngine(this);
		
	}
	
	public void start() {
		if(isRunning) {
			return;
		}
		run();
	}
	
	public void stop() {
		if(!isRunning) {
			return;
		}
		isRunning = false;
	}
	
	private void run() {
		isRunning = true;
		
		int frames = 0;
		double frameCounter = 0;
		
		game.init();
//		try {
//			Client.initClient();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0D;
		
		while(isRunning) {
			boolean render = false;
			
			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime) {
				render = true;
				
				unprocessedTime -= frameTime;

				if(Window.isCloseRequested()) {
					stop();
				}
				
				Input.update();
				
				game.input((float) frameTime);
				game.update((float) frameTime);
				
				physicsEngine.simulate((float) frameTime);
				physicsEngine.checkSimulation();
				
				if(frameCounter >= 1) {
					System.out.println(frames);
					
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render) {
				game.render(renderingEngine);
				Window.render();
				frames++;
			}else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		cleanUp();
	}
	
	private void cleanUp() {
		Window.dispose();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getWidth() {
		return Window.getWidth();
	}

	public void setWidth(int width) {
		Window.setWidth(width);
	}

	public int getHeight() {
		return Window.getHeight();
	}

	public void setHeight(int height) {
		Window.setHeight(height);
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public RenderingEngine getRenderingEngine() {
		return renderingEngine;
	}
	public PhysicsEngine getPhysicsEngine() {
		return physicsEngine;
	}
}
