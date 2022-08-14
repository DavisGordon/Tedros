/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.tools.control.builder;

import java.lang.annotation.Annotation;

import org.tedros.core.TLanguage;
import org.tedros.core.notify.model.TNotify;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.TBuilder;
import org.tedros.fx.builder.TGenericBuilder;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.tools.control.TNotifyLink;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TNotifyLinkBuilder 
extends TBuilder 
implements ITControlBuilder<TNotifyLink, Property<String>> {
	
	public TNotifyLink build(final Annotation ann, final Property<String> attrProperty) throws Exception {
		org.tedros.tools.annotation.TNotifyLink tAnn = (org.tedros.tools.annotation.TNotifyLink) ann;
		TGenericBuilder<TNotify> b = tAnn.entityBuilder().newInstance();
		b.setComponentDescriptor(super.getComponentDescriptor());
		final TNotifyLink control = new TNotifyLink(b);
		control.getStyleClass().add(TTextStyle.CUSTOM.getValue());
		control.textProperty().bindBidirectional(attrProperty);
		callParser(ann, control);
		if(control.getText()==null){
			control.setText(TLanguage.getInstance().getString("#{label.notify.link}"));
		}
		return control;
	}
	
}
