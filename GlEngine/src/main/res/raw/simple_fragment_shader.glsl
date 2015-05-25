precision mediump float; 
      	 								
uniform vec4 u_Color;
uniform float u_opacity;
  
void main()                    		
{                              	
    gl_FragColor = u_Color;
    gl_FragColor.a *= u_opacity;
}