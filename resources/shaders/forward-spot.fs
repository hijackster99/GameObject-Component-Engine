#version 120
#include "lighting.glh"

varying vec2 texCoord0;
varying vec3 normal0;
varying vec3 worldPos0;

uniform sampler2D diffuse;
uniform SpotLight r_spotLight;

void main(){
	
	gl_FragColor = texture2D(diffuse, texCoord0.xy) * calcSpotLight(r_spotLight, normalize(normal0), worldPos0);
	
}