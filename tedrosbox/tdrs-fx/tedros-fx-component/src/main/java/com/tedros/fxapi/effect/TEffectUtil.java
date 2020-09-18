package com.tedros.fxapi.effect;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Shadow;
import javafx.scene.paint.Color;

public final class TEffectUtil {
	
	
	private TEffectUtil(){
		
	}
	
	public static Lighting buildNotNullFieldFormEffect() {
		
		Light.Distant light = new Light.Distant();
		light.azimuthProperty().set(45);
		light.setElevation(45);
		light.setColor(Color.web("#ffbdbf"));
		
		Lighting ltg = new  Lighting();
		ltg.setDiffuseConstant(1.4761904761904763);
		ltg.setSpecularConstant(0.5222222222222221);
		ltg.setSpecularExponent(20);
		ltg.setSurfaceScale(1.5);
		ltg.setBumpInput(new Shadow());
		ltg.setLight(light);

		return ltg;
		
	}
	
	
}
