/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.component.ITComponent;
import org.tedros.fx.component.TComponent;

import javafx.beans.property.Property;
import javafx.scene.Node;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TComponentBuilder 
extends TBuilder
implements ITControlBuilder<Node, Property> {	
	public Node build(final Annotation annotation, final Property attrProperty) throws Exception {
		final TComponent ann = (TComponent) annotation;
		ITComponent control = ann.type().getDeclaredConstructor().newInstance();
		control.tInitializeComponent(getComponentDescriptor());
		return (Node) control;
	}
}
