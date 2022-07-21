#version 120

attribute vec3 pos;
attribute vec2 texCoord;

varying vec2 texCoord0;

uniform mat4 t_MVP;

void main(){

	gl_Position = t_MVP * vec4(pos, 1.0);
	texCoord0 = texCoord;

}