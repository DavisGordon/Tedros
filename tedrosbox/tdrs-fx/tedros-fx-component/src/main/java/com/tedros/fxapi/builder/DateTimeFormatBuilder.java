package com.tedros.fxapi.builder;

import java.text.DateFormat;

import com.tedros.fxapi.descriptor.TComponentDescriptor;

public class DateTimeFormatBuilder implements ITGenericBuilder<DateFormat>{

	private TComponentDescriptor descriptor;
	
	@Override
	public TComponentDescriptor getComponentDescriptor() {
		return descriptor;
	}

	@Override
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor) {
		descriptor = componentDescriptor;
	}

	@Override
	public DateFormat build() {
		return DateFormat.getDateTimeInstance();
	}

}
