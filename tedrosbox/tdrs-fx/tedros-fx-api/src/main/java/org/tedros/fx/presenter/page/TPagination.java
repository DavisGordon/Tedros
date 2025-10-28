/**
 * 
 */
package org.tedros.fx.presenter.page;

/**
 * Pagination data 
 * 
 * @author Davis Gordon
 *
 */
public class TPagination {

	private Object value;
	private TSearch search;
	private String orderBy;
	private String orderByAlias;
	private boolean orderByAsc;
	private int start;
	private int totalRows;
	
	
	/**
	 * @param value
	 * @param search
	 * @param orderBy
	 * @param orderByAlias
	 * @param orderByAsc
	 * @param start
	 * @param totalRows
	 */
	public TPagination(Object value, TSearch search, String orderBy, String orderByAlias, boolean orderByAsc, int start,
			int totalRows) {
		super();
		this.value = value;
		this.search = search;
		this.orderBy = orderBy;
		this.orderByAlias = orderByAlias;
		this.orderByAsc = orderByAsc;
		this.start = start;
		this.totalRows = totalRows;
	}
	/**
	 * @return the search
	 */
	public TSearch getSearch() {
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
	 * @return the orderByAlias
	 */
	public String getOrderByAlias() {
		return orderByAlias;
	}
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	
	
}
