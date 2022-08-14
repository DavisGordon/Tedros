package org.tedros.fx.annotation;

import org.tedros.fx.annotation.scene.control.TSize;
import org.tedros.fx.form.TConverter;

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
