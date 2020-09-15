/**
 * 
 */
package com.tedros.fxapi.presenter.paginator;

/**
 * Pagination data 
 * 
 * @author Davis Gordon
 *
 */
public class TPagination {

	private String search;
	private String orderBy;
	private boolean orderByAsc;
	private int start;
	private int totalRows;
	
	
	
	/**
	 * @param search
	 * @param orderBy
	 * @param orderByAsc
	 * @param start
	 * @param totalRows
	 */
	public TPagination(String search, String orderBy, boolean orderByAsc, int start, int totalRows) {
		this.search = search;
		this.orderBy = orderBy;
		this.orderByAsc = orderByAsc;
		this.start = start;
		this.totalRows = totalRows;
	}
	/**
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}
	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @return the totalRows
	 */
	public int getTotalRows() {
		return totalRows;
	}
	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @return the orderByAsc
	 */
	public boolean isOrderByAsc() {
		return orderByAsc;
	}
	
	
}
