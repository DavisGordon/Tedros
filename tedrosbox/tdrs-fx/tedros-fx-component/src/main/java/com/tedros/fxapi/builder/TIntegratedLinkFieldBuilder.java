/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.ejb.base.entity.TReceptiveEntity;
import com.tedros.fxapi.control.TIntegratedLink;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.Property;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TIntegratedLinkFieldBuilder 
extends TBuilder 
implements ITControlBuilder<TIntegratedLink, Property<String>> {
	
	@SuppressWarnings("rawtypes")
	public TIntegratedLink build(final Annotation annotation, final Property<String> attrProperty) throws Exception {
		TEntityModelView mv = (TEntityModelView) super.getComponentDescriptor().getModelView();
		TEntity e = (TEntity) mv.getEntity();
		if(e instanceof TReceptiveEntity) { 
			TReceptiveEntity re = (TReceptiveEntity) e;
			final TIntegratedLink control = new TIntegratedLink(re.getIntegratedModulePath());
			control.getStyleClass().add(TTextStyle.CUSTOM.getValue());
			control.settEntityClassName(re.getIntegratedEntity());
			control.settModelViewClassName(re.getIntegratedModelView());
			control.settEntityId(re.getIntegratedEntityId());
			callParser(annotation, control);
			return control;
		}else
			throw new IllegalStateException("The entity "+e.getClass().getName()
					+ " must extend TReceptiveEntity to build the component TIntegratedLink!");
	}
	
}
