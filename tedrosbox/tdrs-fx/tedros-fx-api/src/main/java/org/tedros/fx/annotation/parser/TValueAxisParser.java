package org.tedros.fx.annotation.parser;

import java.lang.reflect.Modifier;

import org.tedros.fx.annotation.chart.TValueAxis;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.builder.TNumberAxisFormatterBuilder;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.NumberAxis.DefaultFormatter;
import javafx.scene.chart.ValueAxis;

/**
 * <pre>
 * The {@link TValueAxis} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link ValueAxis} component.
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TValueAxisParser extends TAnnotationParser<TValueAxis, ValueAxis>{
	@SuppressWarnings("unchecked")
	@Override
	public void parse(TValueAxis ann, ValueAxis object, String... byPass) throws Exception {
		if(!Modifier.isAbstract(ann.tickLabelFormatter().getModifiers())) {
			TNumberAxisFormatterBuilder b = ann.tickLabelFormatter().getDeclaredConstructor().newInstance();
			b.setComponentDescriptor(getComponentDescriptor());
			b.setAxis((NumberAxis) object);
			DefaultFormatter f = b.build();
			object.setTickLabelFormatter(f);
		}

		super.parse(ann, object, "tickLabelFormatter");
	}
}
