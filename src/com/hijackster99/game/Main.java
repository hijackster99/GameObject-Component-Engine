/**
 * 
 */
package com.hijackster99.game;

import java.io.PrintStream;

import com.hijackster99.engine.core.CoreEngine;

/**
 * @author jonat
 *
 */
public class Main {

	public static CoreEngine engine;
	
	public static void main(String[] args) {
		PrintStream stream = new PrintStream(System.out) {
			@Override
			public void println(String x) {
				super.println(Thread.currentThread().getStackTrace()[2] + ": " + x);
			}
			
			@Override
			public void println(double x) {
				super.println(Thread.currentThread().getStackTrace()[2] + ": " + Double.toString(x));
			}
			
			@Override
			public void println(float x) {
				super.println(Thread.currentThread().getStackTrace()[2] + ": " + Float.toString(x));
			}
			
			@Override
			public void println(int x) {
				super.println(Thread.currentThread().getStackTrace()[2] + ": " + Integer.toString(x));
			}
			
			@Override
			public void println(boolean x) {
				super.println(Thread.currentThread().getStackTrace()[2] + ": " + Boolean.toString(x));
			}
			
			@Override
			public void println(Object x) {
				super.println(Thread.currentThread().getStackTrace()[2] + ": " + x.toString());
			}
		};
		System.setOut(stream);
		engine = new CoreEngine(144, null);
		engine.setGame(new MobaGame(engine));
		engine.createWindow(1280, 720, "MOBA");
		engine.start();
	}
	
}
