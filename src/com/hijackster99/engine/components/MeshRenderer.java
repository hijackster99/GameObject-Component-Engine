package com.hijackster99.engine.components;

import org.lwjgl.opengl.GL11;

import com.hijackster99.engine.rendering.Material;
import com.hijackster99.engine.rendering.Mesh;
import com.hijackster99.engine.rendering.RenderingEngine;
import com.hijackster99.engine.rendering.Shader;

public class MeshRenderer extends GameComponent{

	private Mesh mesh;
	private Material material;
	
	public MeshRenderer(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
		
	}
	
	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) {
		
		shader.bind();
		shader.updateUniforms(getTransform(), material, renderingEngine);
		mesh.draw(GL11.GL_TRIANGLES);
		
	}
	
	/* (non-Javadoc)
	 * @see com.hijackster99.engine.components.GameComponent#getId()
	 */
	@Override
	public int getId() {
		return 6;
	}
	
}
