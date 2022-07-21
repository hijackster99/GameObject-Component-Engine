package com.hijackster99.engine.rendering.meshLoading;

public class OBJIndex {

	public int vertexIndex;
	public int texCoordIndex;
	public int normalIndex;
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof OBJIndex) {
			OBJIndex index = (OBJIndex) obj;
			
			return vertexIndex == index.vertexIndex & texCoordIndex == index.texCoordIndex & normalIndex == index.normalIndex;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int BASE = 17;
		final int MULTIPLIER = 31;
		
		int result = BASE;
		
		result = MULTIPLIER * result + vertexIndex;
		result = MULTIPLIER * result + texCoordIndex;
		result = MULTIPLIER * result + normalIndex;
		
		return result;
	}
	
}
