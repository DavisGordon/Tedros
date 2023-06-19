package org.tedros.fx.annotation;

import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.converter.TConverter;

import javafx.geometry.Insets;

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
