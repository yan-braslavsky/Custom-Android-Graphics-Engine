precision mediump float;
varying vec4 v_Color;
varying vec2 v_texCoord;
uniform sampler2D s_texture;

void main()
{
    gl_FragColor = texture2D( s_texture, v_texCoord ) * v_Color;
    gl_FragColor.rgb *= v_Color.a;
}