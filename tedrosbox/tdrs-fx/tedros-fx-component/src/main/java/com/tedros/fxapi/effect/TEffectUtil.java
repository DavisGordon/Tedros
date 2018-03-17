package com.tedros.fxapi.effect;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.LightingBuilder;
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
		
		return LightingBuilder.create().diffuseConstant(1.4761904761904763)
		.specularConstant(0.5222222222222221)
		.specularExponent(20)
		.surfaceScale(1.5)
		.bumpInput(new Shadow())
		.light(light)
		.build();
		
		/*
		return ColorAdjustBuilder.create()
				 .hue(-0.15267175572519087)
				 .saturation(0)
				 .brightness(0)
				 .contrast(0)
				 .build();*/
	}
	
	
}
