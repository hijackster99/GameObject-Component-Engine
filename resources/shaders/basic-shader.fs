#version 330

in vec2 texCoord0;

uniform vec3 r_color;

void main(){
	gl_FragColor = vec4(r_color, 1);
}