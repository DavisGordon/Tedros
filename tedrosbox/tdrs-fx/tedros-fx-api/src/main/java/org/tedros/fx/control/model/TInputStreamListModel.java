package org.tedros.fx.control.model;

import java.io.InputStream;
import java.util.List;

import org.tedros.server.model.ITListModel;
import org.tedros.server.model.ITReportItemModel;
import org.tedros.server.model.ITValueModel;

@SuppressWarnings("rawtypes")
public class TInputStreamListModel implements ITReportItemModel<ITValueModel>, ITListModel<ITValueModel<InputStream>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3238354395269632146L;
	private List<ITValueModel<InputStream>> values;
	
	public TInputStreamListModel() {
	}

	@Override
	public ITValueModel getModel() {
		return null;
	}

	@Override
	public List<ITValueModel<InputStream>> getValues() {
		return values;
	}

	@Override
	public void setValues(List<ITValueModel<InputStream>> values) {
		this.values = values;
	}

}
