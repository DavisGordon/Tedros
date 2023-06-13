/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.core.TLanguage;
import org.tedros.core.model.TModelViewUtil;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.control.TPickListField;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.helper.TLoadListHelper;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TPickListFieldBuilder 
extends TBuilder
implements ITControlBuilder<org.tedros.fx.control.TPickListField, ObservableList> {

	@SuppressWarnings("unchecked")
	public org.tedros.fx.control.TPickListField build(final Annotation annotation, final ObservableList attrProperty) throws Exception {
		TPickListField tAnnotation = (TPickListField) annotation;
		final org.tedros.fx.control.TPickListField control = 
				new org.tedros.fx.control.TPickListField<>(	tAnnotation.sourceLabel(), 
															tAnnotation.targetLabel(), 
															null, 
															attrProperty, 
															tAnnotation.width(),
															tAnnotation.height(),
															tAnnotation.required(),
															tAnnotation.selectionMode());
			
		ContextMenu ctx = new ContextMenu();
		MenuItem reload = new MenuItem(TLanguage.getInstance().getString(TFxKey.BUTTON_RELOAD));
		EventHandler<ActionEvent> reloadEvent = ev->{
			try {
				loadControl(tAnnotation, control);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		String k = super.getComponentDescriptor().getFieldDescriptor()
				.getFieldName()+"_picklist_reload_event";
		
		super.getComponentDescriptor().getForm()
		.gettObjectRepository()
		.add(k, reloadEvent);
		
		reload.setOnAction(new WeakEventHandler<>(reloadEvent));
		ctx.getItems().add(reload);
		
		control.gettSourceListView().setContextMenu(ctx);
			
		loadControl(tAnnotation, control);
		
		callParser(tAnnotation, control);
				
		return control;
	}

	/**
	 * @param tAnnotation
	 * @param control
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private void loadControl(TPickListField tAnnotation, final org.tedros.fx.control.TPickListField control)
			throws InstantiationException, IllegalAccessException {
		
		if(tAnnotation.items()!=NullObservableListBuilder.class) {
			ITGenericBuilder<ObservableList> b = tAnnotation.items().newInstance();
			b.setComponentDescriptor(getComponentDescriptor());
			ObservableList source = b.build();
			control.settSourceList(source);
		}else
		if(tAnnotation.process().query().entity()!=ITEntity.class) {
			TProcess ann = tAnnotation.process();
			final Class<? extends TEntityProcess> pClass = ann.type();
			final Class<? extends TModelView> mClass = ann.modelView();
			final Class<? extends ITEntity> eClass = ann.query().entity();
			final String service = ann.service();
			
			ObservableList source = FXCollections.observableArrayList();
	
			TQuery qry = ann.query();
			TSelect sel = TSelectQueryBuilder.build(qry, super.getComponentDescriptor());
			TModelViewUtil util = (mClass!=TModelView.class) ? new TModelViewUtil(mClass, eClass) : null;
			TLoadListHelper.load(service, eClass, mClass, pClass, sel, e -> {
				source.add(util!=null ? util.convertToModelView(e) : e);
			}, ok->{
				if(ok)
					control.settSourceList(source);
			});	
		}
	}
	
}
