package com.tedros.fxapi.annotation;

import javafx.scene.DepthTest;

public enum TDepthTest {

	DISABLE (DepthTest.DISABLE),
	ENABLE (DepthTest.ENABLE),
	INHERIT (DepthTest.INHERIT),
	NULL (null);
	
	private DepthTest value;
	
	private TDepthTest(DepthTest value){
		this.value = value;
	}
	
	public DepthTest getValue() {
		return value;
	}
	
}
