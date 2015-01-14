precision mediump float;
varying vec4 v_Color;
varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;

void main()
{
    gl_FragColor = texture2D( u_TextureUnit, v_TextureCoordinates );

    if(gl_FragColor.r > 0.5 && gl_FragColor.g >0.5 && gl_FragColor.b > 0.5){
        gl_FragColor.a = 1.0;
    }else {
         gl_FragColor.a = 0.0;
    }

}