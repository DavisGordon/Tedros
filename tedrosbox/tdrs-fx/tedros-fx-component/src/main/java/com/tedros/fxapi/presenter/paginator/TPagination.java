/**
 * 
 */
package com.tedros.fxapi.presenter.paginator;

/**
 * @author Davis Gordon
 *
 */
public class TPagination {

	private String search;
	private int start;
	private int totalRows;
	
	
	/**
	 * @param search
	 * @param start
	 * @param totalRows
	 */
	TPagination(String search, int start, int totalRows) {
		this.search = search;
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
	
	
}
