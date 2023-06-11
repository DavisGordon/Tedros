/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.control.TIntegratedLink;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.entity.TReceptiveEntity;

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
		ITEntity e =  mv.getEntity();
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
