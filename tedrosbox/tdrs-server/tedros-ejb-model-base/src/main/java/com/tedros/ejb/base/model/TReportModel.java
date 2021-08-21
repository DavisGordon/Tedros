/**
 * 
 */
package com.tedros.ejb.base.model;

import java.util.List;

/**
 * @author Davis Gordon
 *
 */
public abstract class TReportModel<M extends ITModel> implements ITReportModel<M> {

	private static final long serialVersionUID = -6958152044379424961L;

	private String orderBy;
	
	private String orderType;
	
	private List<M> result;

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the result
	 */
	public List<M> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List<M> result) {
		this.result = result;
	}

}
