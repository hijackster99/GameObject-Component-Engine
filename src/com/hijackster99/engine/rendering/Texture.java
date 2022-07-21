package com.hijackster99.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import com.hijackster99.engine.core.BiMap;
import com.hijackster99.engine.core.Util;
import com.hijackster99.engine.rendering.resourceManagement.TextureResource;

public class Texture {

	private static BiMap<String, TextureResource> loadedTextures = new BiMap<String, TextureResource>();
	private TextureResource resource;
	
	public Texture(String fileName) {
		TextureResource oldResource = loadedTextures.get(fileName);
		
		if(oldResource != null) {
			resource = oldResource;
			resource.addRef();
		}
		else {
			resource = loadTexture(fileName);
			loadedTextures.put(fileName, resource);
		}
	}
	
	public void bind() {
		bind(0);
	}
	
	public void bind(int samplerSlot) {
		assert(samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, resource.getId());
	}
	
	public int getId() {
		return resource.getId();
	}
	
	private static TextureResource loadTexture(String fileName) {
		String[] split = fileName.split("\\.");
		String ext = split[split.length - 1];
		
		if(!ext.equals("png")) {
			System.err.println("Error: file format not found : " + ext);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		try {
			BufferedImage image = ImageIO.read(new File("./resources/textures/" + fileName));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			
			ByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);
			boolean hasAlpha = image.getColorModel().hasAlpha();
			
			for(int y = 0; y < image.getHeight(); y++) {
				for(int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];
					
					buffer.put((byte)((pixel >> 16) & 0xFF));
					buffer.put((byte)((pixel >> 8) & 0xFF));
					buffer.put((byte)((pixel) & 0xFF));
					if(hasAlpha)
						buffer.put((byte)((pixel >> 24) & 0xFF));
					else
						buffer.put((byte) 0xFF);
				}
			}
			buffer.flip();
			
			TextureResource resource = new TextureResource();
			glBindTexture(GL_TEXTURE_2D, resource.getId());
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			
			return resource;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void finalize() throws Throwable {
		if(resource.removeRef()) {
			loadedTextures.removeKey(resource);
		}
	}
}
