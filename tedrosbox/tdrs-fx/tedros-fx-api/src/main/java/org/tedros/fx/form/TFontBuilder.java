package org.tedros.fx.form;

import org.tedros.fx.annotation.text.TFont;

import javafx.scene.text.Font;

public final class TFontBuilder {
	
	private TFontBuilder(){
		
	}
	
	public static Font build(TFont tFont){
		return Font.font(tFont.family(), tFont.weight(), tFont.posture(), tFont.size());
	}

}
