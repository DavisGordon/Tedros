package com.tedros.fxapi.annotation;

import com.tedros.fxapi.annotation.scene.control.TSize;
import com.tedros.fxapi.form.TConverter;

public class TSizeConverter extends TConverter<TSize, Double[]> {

	public TSizeConverter(){
		
	}
	
	public TSizeConverter(TSize in){
		super(in);
	}
	
	@Override
	public Double[] getOut() {	
		TSize in = getIn();
		return new Double[]{in.width(), in.height()};
	}

}
