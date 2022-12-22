/**
 * 
 */
package org.tedros.fx.builder;

import org.tedros.fx.collections.ITObservableList;
import org.tedros.server.model.TChartModel;

/**
 * The TChartModel builder
 * @author Davis Gordon
 *
 */
public abstract class TChartModelBuilder<X,Y> extends TGenericBuilder<TChartModel<X,Y>> {

	@SuppressWarnings("rawtypes")
	private ITObservableList observableList;

	/**
	 * @return the observableList
	 */
	@SuppressWarnings("rawtypes")
	public ITObservableList getObservableList() {
		return observableList;
	}

	/**
	 * @param observableList the observableList to set
	 */
	@SuppressWarnings("rawtypes")
	public void setObservableList(ITObservableList observableList) {
		this.observableList = observableList;
	}
}
