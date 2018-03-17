package com.tedros.fxapi.domain;

import javafx.scene.effect.BlendMode;

public enum TBlendMode  {
	
	 ADD 		(BlendMode.ADD),
	 SRC_OVER 	(BlendMode.SRC_OVER),
	 SRC_ATOP	(BlendMode.SRC_ATOP),
	 MULTIPLY	(BlendMode.MULTIPLY),
	 SCREEN		(BlendMode.SCREEN),
	 OVERLAY	(BlendMode.OVERLAY),
	 DARKEN		(BlendMode.DARKEN),
	 LIGHTEN	(BlendMode.LIGHTEN),
	 COLOR_DODGE(BlendMode.COLOR_DODGE),
	 COLOR_BURN	(BlendMode.COLOR_BURN),
	 HARD_LIGHT	(BlendMode.HARD_LIGHT),
	 SOFT_LIGHT	(BlendMode.SOFT_LIGHT),
	 DIFFERENCE	(BlendMode.DIFFERENCE),
	 EXCLUSION	(BlendMode.EXCLUSION),
	 RED		(BlendMode.RED),
	 GREEN		(BlendMode.GREEN),
	 BLUE 		(BlendMode.BLUE),
	 NULL		(null);
	 
	 
	 BlendMode value;
	 
	 private TBlendMode(BlendMode value){
		 this.value = value;
	 }
	 
	 public BlendMode getValue() {
		return value;
	}

}
