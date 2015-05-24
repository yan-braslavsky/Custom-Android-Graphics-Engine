precision mediump float;
varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
uniform float u_opacity;
uniform vec4 u_TextColor;

void main()
{
    gl_FragColor = texture2D( u_TextureUnit, v_TextureCoordinates ) * u_TextColor;
}