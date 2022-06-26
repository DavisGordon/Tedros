/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.tools.control.builder;

import java.lang.annotation.Annotation;

import com.tedros.core.TLanguage;
import com.tedros.core.notify.model.TNotify;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.TBuilder;
import com.tedros.fxapi.builder.TGenericBuilder;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.tools.control.TNotifyLink;

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
		com.tedros.tools.annotation.TNotifyLink tAnn = (com.tedros.tools.annotation.TNotifyLink) ann;
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
