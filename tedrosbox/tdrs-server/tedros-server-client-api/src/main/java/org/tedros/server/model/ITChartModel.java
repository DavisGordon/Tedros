/**
 * 
 */
package org.tedros.server.model;

import java.util.List;

/**
 * The chart contract.
 * @author Davis Gordon
 *
 */
public interface ITChartModel<X,Y> extends ITModel {

	List<ITChartSerie<X,Y>> getSeries();
}
