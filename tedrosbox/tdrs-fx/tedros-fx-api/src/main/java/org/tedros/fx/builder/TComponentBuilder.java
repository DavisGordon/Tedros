/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.util.UUID;

import org.tedros.api.form.ITForm;
import org.tedros.fx.component.ITComponent;
import org.tedros.fx.component.TComponent;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
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
		
		ChangeListener<Boolean> disableChl = (obs, ov, nv) -> {
			if(nv) {
				control.tStopComponent();
			}
		};
		
		ITForm form = super.getComponentDescriptor().getForm();
		form.gettObjectRepository().add(UUID.randomUUID().toString(), disableChl);
		form.tDisposeProperty().addListener(new WeakChangeListener<>(disableChl));
		
		return (Node) control;
	}
}
