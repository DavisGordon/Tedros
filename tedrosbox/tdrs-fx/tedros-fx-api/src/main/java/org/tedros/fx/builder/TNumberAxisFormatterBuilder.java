/**
 * 
 */
package org.tedros.fx.builder;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.NumberAxis.DefaultFormatter;

/**
 * The NumberAxis.DefaultFormatter builder.
 * @author Davis Gordon
 *
 */
public abstract class TNumberAxisFormatterBuilder extends TGenericBuilder<DefaultFormatter> {

	private NumberAxis axis;

	/**
	 * @return the axis
	 */
	public NumberAxis getAxis() {
		return axis;
	}

	/**
	 * @param axis the axis to set
	 */
	public void setAxis(NumberAxis axis) {
		this.axis = axis;
	}

}
