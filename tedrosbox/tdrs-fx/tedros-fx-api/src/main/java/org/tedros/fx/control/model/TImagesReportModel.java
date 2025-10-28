/**
 * 
 */
package org.tedros.fx.control.model;

import java.util.List;

import org.tedros.server.model.ITReportModel;

/**
 * @author Davis Gordon
 *
 */
public class TImagesReportModel implements ITReportModel<TInputStreamListModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TInputStreamListModel> result;
	
	/**
	 * 
	 */
	public TImagesReportModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getOrderBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrderType() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the result
	 */
	public List<TInputStreamListModel> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List<TInputStreamListModel> result) {
		this.result = result;
	}
}
