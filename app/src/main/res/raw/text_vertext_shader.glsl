uniform mat4 uMVPMatrix;
attribute vec4 vPosition;
attribute vec4 a_Color;
attribute vec2 a_texCoord;
varying vec4 v_Color;
varying vec2 v_texCoord;

void main()
{
  gl_Position = uMVPMatrix * vPosition;
  v_texCoord = a_texCoord;
  v_Color = a_Color;
}
