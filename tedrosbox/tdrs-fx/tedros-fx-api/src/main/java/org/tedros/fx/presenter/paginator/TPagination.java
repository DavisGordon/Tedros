/**
 * 
 */
package org.tedros.fx.presenter.paginator;

/**
 * Pagination data 
 * 
 * @author Davis Gordon
 *
 */
public class TPagination {

	private String searchFieldName;
	private String search;
	private String orderBy;
	private String orderByAlias;
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
	public TPagination(String search, String orderBy, String orderByAlias, boolean orderByAsc, int start, int totalRows) {
		this.search = search;
		this.orderBy = orderBy;
		this.orderByAsc = orderByAsc;
		this.orderByAlias = orderByAlias;
		this.start = start;
		this.totalRows = totalRows;
	}
	/**
	 * @param searchFieldName
	 * @param search
	 * @param orderBy
	 * @param orderByAsc
	 * @param start
	 * @param totalRows
	 */
	public TPagination(String searchFieldName, String search, String orderBy, String orderByAlias, boolean orderByAsc, int start,
			int totalRows) {
		this.searchFieldName = searchFieldName;
		this.search = search;
		this.orderBy = orderBy;
		this.orderByAsc = orderByAsc;
		this.orderByAlias = orderByAlias;
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
	/**
	 * @return the searchFieldName
	 */
	public String getSearchFieldName() {
		return searchFieldName;
	}
	/**
	 * @return the orderByAlias
	 */
	public String getOrderByAlias() {
		return orderByAlias;
	}
	
	
}
