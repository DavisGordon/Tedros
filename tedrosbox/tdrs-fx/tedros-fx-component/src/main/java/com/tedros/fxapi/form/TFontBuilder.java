package com.tedros.fxapi.form;

import javafx.scene.text.Font;

import com.tedros.fxapi.annotation.text.TFont;

public final class TFontBuilder {
	
	private TFontBuilder(){
		
	}
	
	public static Font build(TFont tFont){
		return Font.font(tFont.family(), tFont.weight(), tFont.posture(), tFont.size());
	}

}
