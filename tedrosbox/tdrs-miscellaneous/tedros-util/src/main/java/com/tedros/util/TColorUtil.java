package com.tedros.util;

import javafx.scene.paint.Color;


public class TColorUtil {

	private TColorUtil(){
		
	}
	
	public static String toHexadecimal(Color color){
		if(color==null)
			return null;
		String s = color.toString();
		s = s.substring(2, s.length());
		return "#"+s.substring(0, 6);
	}
	
	public static java.awt.Color hex2Rgb(String colorStr) {
	    return new java.awt.Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
}
