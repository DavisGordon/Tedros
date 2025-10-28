/**
 * 
 */
package org.tedros.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The chart model
 * @author Davis Gordon
 *
 */
public class TChartModel<X, Y> implements ITChartModel<X, Y> {

	private static final long serialVersionUID = -3916820531073263262L;
	private List<ITChartSerie<X, Y>> series;

	/**
	 * 
	 */
	public TChartModel() {
		
	}
	
	/**
	 * Adds a serie to the list
	 * @param serie
	 */
	public void addSerie(ITChartSerie<X,Y> serie) {
		if(series==null)
			series = new ArrayList<>();
		series.add(serie);
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.model.ITChartModel#getSeries()
	 */
	@Override
	public List<ITChartSerie<X, Y>> getSeries() {
		return series;
	}

}
