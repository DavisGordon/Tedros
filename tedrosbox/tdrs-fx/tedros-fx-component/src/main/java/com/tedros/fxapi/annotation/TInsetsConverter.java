package com.tedros.fxapi.annotation;

import javafx.geometry.Insets;

import com.tedros.fxapi.annotation.scene.control.TInsets;
import com.tedros.fxapi.form.TConverter;

public class TInsetsConverter extends TConverter<TInsets, Insets> {
	
	public TInsetsConverter() {
		
	}
	
	public TInsetsConverter(TInsets in) {
		super(in);
	}

	@Override
	public Insets getOut() {
		TInsets in = getIn();
		return new Insets(in.top(), in.right(), in.bottom(), in.left());
	}

}
