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
import org.tedros.fx.exception.TException;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.model.TModelViewBuilder;
import org.tedros.server.entity.TEntity;
import org.tedros.server.query.TSelect;

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
		
		TSelect sel = TSelectQueryBuilder.build(tAnn.query(), super.getComponentDescriptor());
		
		final org.tedros.fx.control.TAutoCompleteEntity control 
		= new org.tedros.fx.control.TAutoCompleteEntity(sel, 
				tAnn.startSearchAt(), tAnn.showMaxItems(), tAnn.service());
		
		if(tAnn.converter()!=TFunctionEntityToStringBuilder.class) {
			TFunctionEntityToStringBuilder b = tAnn.converter().newInstance();
			control.settConverter(b.build());
		}
		
		if(tAnn.modelViewType()!=TEntityModelView.class) {
			if(attrProperty.getValue() instanceof TEntityModelView)
				control.tSelectedItemProperty().setValue((TEntity) 
						((TEntityModelView)attrProperty.getValue()).getEntity());
			ChangeListener<TEntityModelView> chl0 = (a,o,n) -> {
				if(n!=null) {
					control.tSelectedItemProperty().setValue((TEntity) n.getEntity());
				}else
					control.tSelectedItemProperty().setValue(null);
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(super.getComponentDescriptor().getFieldDescriptor().getFieldName()
					+"_autocompentity_chl0", chl0);
			attrProperty.addListener(new WeakChangeListener<>(chl0));
			
			ChangeListener<TEntity> chl = (a,o,n) -> {
				if(n!=null) {
					try {
						TEntityModelView mv = (TEntityModelView) TModelViewBuilder.create()
								.modelViewClass((Class<? extends ITModelView<?>>) tAnn.modelViewType())
								.entityClass(sel.getType())
								.build(n);
						attrProperty.setValue(mv);
					} catch (TException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
			};
			super.getComponentDescriptor().getForm().gettObjectRepository()
			.add(super.getComponentDescriptor().getFieldDescriptor().getFieldName()
					+"_autocompentity_chl", chl);
			control.tSelectedItemProperty()
			.addListener(new WeakChangeListener<>(chl));
		}else {
			if(attrProperty.getValue() instanceof TEntity)
				control.tSelectedItemProperty().setValue((TEntity) attrProperty.getValue());
			attrProperty.bindBidirectional(control.tSelectedItemProperty());
		}
		
		callParser(tAnn, control);
		
		return control;
	}
	
}
