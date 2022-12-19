/**
 * 
 */
package org.tedros.server.controller;

import org.tedros.server.model.ITChartModel;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

/**
 * The chart controller.
 * @author Davis Gordon
 *
 */
public interface ITEjbChartController {
	
	/**
	 * Process the desired chart data.
	 * @param token
	 * @param params
	 * @return the data to plot the chart.
	 */
	@SuppressWarnings("rawtypes")
	TResult<? extends ITChartModel> process(TAccessToken token, TParam... params);

}
