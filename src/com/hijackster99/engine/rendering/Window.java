package com.hijackster99.engine.rendering;

import java.awt.Canvas;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.hijackster99.engine.core.Vector2f;

public class Window {

	public static void createWindow(int width, int height, String title) {
		Display.setTitle(title);
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setResizable(true);
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean wasResized() {
		return Display.wasResized();
	}
	
	public static void update() {
		System.out.println(Display.getHeight());
		System.out.println(Display.getWidth());
		System.out.println(Display.getDisplayMode().getHeight());
		System.out.println(Display.getDisplayMode().getWidth());
	}
	
	public static void render() {
		Display.update();
	}
	
	public static boolean isCloseRequested() {
		return Display.isCloseRequested();
	}
	
	public static int getWidth() {
		return Display.getWidth();
	}
	
	public static int getHeight() {
		return Display.getHeight();
	}
	
	public static void setDisplayMode() {
		
	}
	
	public static String getTitle() {
		return Display.getTitle();
	}
	
	public static void dispose() {
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
	public static Vector2f getCenter() {
		
		return new Vector2f(getWidth()/2, getHeight()/2);
		
	}
	
	public static void setWidth(int width) {
		try {
			Display.setDisplayMode(new DisplayMode(width, Display.getHeight()));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setHeight(int height) {
		try {
			Display.setDisplayMode(new DisplayMode(Display.getWidth(), height));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
}
