package org.tedros.fx.builder;

import java.text.DateFormat;

import org.tedros.api.descriptor.ITComponentDescriptor;

public class DateTimeFormatBuilder implements ITGenericBuilder<DateFormat>{

	private ITComponentDescriptor descriptor;
	
	@Override
	public ITComponentDescriptor getComponentDescriptor() {
		return descriptor;
	}

	@Override
	public void setComponentDescriptor(ITComponentDescriptor componentDescriptor) {
		descriptor = componentDescriptor;
	}

	@Override
	public DateFormat build() {
		return DateFormat.getDateTimeInstance();
	}

}
