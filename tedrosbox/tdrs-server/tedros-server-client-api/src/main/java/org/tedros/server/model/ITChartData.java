package org.tedros.server.model;

/**
 * The data chart contract
 * @author Davis Gordon
 *
 */
public interface ITChartData<X, Y> extends ITModel {

	/**
	 * Return the horizontal X data.
	 * @return <X>
	 */
	X getX();
	
	/**
	 * Return the vertical Y data.
	 * @return <Y>
	 */
	Y getY();
}
