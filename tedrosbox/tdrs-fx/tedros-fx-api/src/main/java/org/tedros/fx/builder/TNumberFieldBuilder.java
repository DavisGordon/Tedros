package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TNumberField;
import org.tedros.fx.util.TReflectionUtil;

import javafx.beans.property.Property;
import javafx.scene.Node;

/**
 * <pre>
 * The abstract field builder class for {@link Number} types.
 * 	
 * @author davis.dun
 * </pre>
 * */
public abstract class TNumberFieldBuilder<N extends Node, P extends Property<?>> 
extends TBuilder
implements ITControlBuilder<N, P> {

	/**
	 * Return a field represented by the N generic parameter.
	 *  
	 * @param annotation - the field annotation
	 * @param attrProperty - the modelview property with the value to bind with the field builded and returned
	 * @return A field represented by the N generic parameter of this class.
	 * @throws Exception
	 * */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public N build(final Annotation annotation, final P attrProperty) throws Exception {
		Class clazz = TReflectionUtil.getGenericParamClass(this.getClass(), 0);
		final N control = (N) clazz.getDeclaredConstructor().newInstance();
		callParser(annotation, control);
		//TRequiredNumeberFieldParser.getInstance().parse(annotation, null, (TRequiredNumberField) control);
		//TNumberFieldParser.getInstance().parse(annotation, null, (TNumberField) control);
		((TNumberField)control).valueProperty().bindBidirectional(attrProperty);
		return control;
	}
	
}
