#version 120

attribute vec3 position;
attribute vec2 texCoord;
attribute vec3 normal;

varying vec2 texCoord0;
varying vec3 normal0;
varying vec3 worldPos0;

uniform mat4 t_model;
uniform mat4 t_MVP;

void main(){
	gl_Position = t_MVP * vec4(position, 1.0);
	texCoord0 = texCoord;
	normal0 = (t_model * vec4(normal, 0.0)).xyz;
	worldPos0 = (t_model * vec4(position, 1.0)).xyz;
}