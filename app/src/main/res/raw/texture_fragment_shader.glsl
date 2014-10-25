precision mediump float; 
      	 				
uniform sampler2D u_TextureUnit;
uniform float u_opacity;
varying vec2 v_TextureCoordinates;      	   								
  
void main()                    		
{                              	
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
    gl_FragColor.a *= u_opacity;
}