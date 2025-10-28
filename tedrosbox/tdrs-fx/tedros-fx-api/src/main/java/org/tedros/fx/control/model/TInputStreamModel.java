package org.tedros.fx.control.model;

import java.io.InputStream;

import org.tedros.server.model.ITModel;
import org.tedros.server.model.ITReportItemModel;
import org.tedros.server.model.ITValueModel;

@SuppressWarnings("rawtypes")
public class TInputStreamModel implements ITReportItemModel, ITValueModel<InputStream> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3238354395269632146L;
	private InputStream value;
	
	public TInputStreamModel() {
	}

	@Override
	public ITModel getModel() {
		return null;
	}

	/**
	 * @return the value
	 */
	public InputStream getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(InputStream value) {
		this.value = value;
	}

}
