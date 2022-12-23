package org.tedros.server.model;

/**
 * The data chart contract
 * @author Davis Gordon
 *
 */
public interface ITChartData<X, Y> extends ITModel {

	/**
	 * Returnsi the horizontal X data.
	 * @return <X>
	 */
	X getX();
	
	/**
	 * ReturnS the vertical Y data.
	 * @return <Y>
	 */
	Y getY();
	
	/**
	 * Returns an extra value.
	 * @return Object
	 */
	Object getExtra();
}
