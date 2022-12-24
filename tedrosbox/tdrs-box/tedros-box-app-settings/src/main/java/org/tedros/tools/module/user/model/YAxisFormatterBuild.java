/**
 * 
 */
package org.tedros.tools.module.user.model;

import org.tedros.fx.builder.TNumberAxisFormatterBuilder;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.NumberAxis.DefaultFormatter;

/**
 * @author Davis Gordon
 *
 */
public class YAxisFormatterBuild extends TNumberAxisFormatterBuilder {
	@Override
	public DefaultFormatter build() {
		return new NumberAxis.DefaultFormatter(getAxis(), "$ ", null);
	}
}
