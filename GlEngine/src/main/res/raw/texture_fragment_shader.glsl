precision mediump float; 
      	 				
uniform sampler2D u_TextureUnit;
uniform float u_opacity;
uniform vec4 u_TextureOverlayColor;
varying vec2 v_TextureCoordinates;

vec4 overlay(in vec4 srcColor,in vec4 u_TextureOverlayColor){

    //cache source color attributes
    float sR = u_TextureOverlayColor.r;
    float sG = u_TextureOverlayColor.g;
    float sB = u_TextureOverlayColor.b;
    float sA = u_TextureOverlayColor.a;

    //cache destination color attributes
    float dR = srcColor.r;
    float dG = srcColor.g;
    float dB = srcColor.b;
    float dA = srcColor.a;

    //implement tinting function
    float dstAlpha =  sA + dA*(1 -sA);

    float dstRed   = (sR*sA + dR*dA*(1-sA)) / dstAlpha;
    float dstGreen = (sG*sA + dG*dA*(1-sA)) / dstAlpha;
    float dstBlue  = (sB*sA + dB*dA*(1-sA)) / dstAlpha;

    dstRed  = dstRed * srcColor.a;
    dstGreen = dstGreen * srcColor.a;
    dstBlue = dstBlue * srcColor.a;
    dstAlpha  = srcColor.a;

    return vec4(dstRed,dstGreen,dstBlue,dstAlpha);
}

//This is the real tint function , but we need an overlay rather than real tint
//vec4 tint(in vec4 srcColor,in vec4 tintColor){
//    return srcColor + tintColor * srcColor.a;
//}

void main()
{
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
    gl_FragColor.a *= u_opacity;

    if(u_TextureOverlayColor.a > 0){
        //do overlay color
        gl_FragColor = overlay(gl_FragColor,u_TextureOverlayColor);
    }
}
