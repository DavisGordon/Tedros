/**
 * 
 */
package org.tedros.server.model;

import java.util.List;

/**
 * The chart serie contract.
 * @author Davis Gordon
 *
 */
public interface ITChartSerie<X, Y> extends ITModel {

	/**
	 * Returns te serie name
	 * @return name
	 */
	String getName();
	
	/**
	 * Returns the data list
	 * @return datas
	 */
	List<ITChartData<X,Y>> getDatas();
}
