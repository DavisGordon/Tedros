/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.exception.TException;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.model.TModelViewBuilder;
import org.tedros.server.entity.TEntity;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TAutoCompleteEntityBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TAutoCompleteEntity, Property> {

	@SuppressWarnings("unchecked")
	public org.tedros.fx.control.TAutoCompleteEntity build(final Annotation annotation, final Property attrProperty) throws Exception {
		TAutoCompleteEntity tAnn = (TAutoCompleteEntity) annotation;
		TEntry eAnn = tAnn.entries();
		
		
		final org.tedros.fx.control.TAutoCompleteEntity control 
		= new org.tedros.fx.control.TAutoCompleteEntity(eAnn.entityType(), 
				eAnn.service(), eAnn.field(), tAnn.startSearchAt(), tAnn.showMaxItems());
		
		if(tAnn.modelViewType()!=TEntityModelView.class) {
			ChangeListener<TEntity> chl = (a,o,n) -> {
				if(n!=null) {
					try {
						TEntityModelView mv = (TEntityModelView) TModelViewBuilder.create()
								.modelViewClass((Class<? extends ITModelView<?>>) tAnn.modelViewType())
								.entityClass(eAnn.entityType())
								.build(n);
						attrProperty.setValue(mv);
					} catch (TException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(eAnn.field()+"_autocompentity_chl", chl);
			control.tSelectedItemProperty()
			.addListener(new WeakChangeListener<>(chl));
		}else
			attrProperty.bind(control.tSelectedItemProperty());
		
		callParser(tAnn, control);
		
		return control;
	}
	
}
