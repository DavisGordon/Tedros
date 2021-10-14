package com.tedros.fxapi.form;

import java.lang.annotation.Annotation;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.control.ITTriggeredable;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.exception.TErrorType;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.collections.WeakSetChangeListener;
import javafx.scene.Node;

public class TTriggerLoader<M extends ITModelView<?>, F extends ITModelForm<M>> {

	
	private final F form;
	
	public TTriggerLoader(F form) {
		this.form = form;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public void buildTriggers() throws Exception {
		int idx = 0;
		for(final TFieldDescriptor tFieldDescriptor : this.form.gettFieldDescriptorList()){
		
			 
			final String fieldName = tFieldDescriptor.getFieldName();
			idx++;
			for (final Annotation annotation : (List<Annotation>) tFieldDescriptor.getAnnotations()) {
				if (!(annotation instanceof TTrigger))
					continue;
				
				TTrigger tAnnotation = (TTrigger) annotation;
				
				boolean buildTrigger = false;
				for(TViewMode mode : tAnnotation.mode()){
					if(this.form.gettMode().equals(mode))
						buildTrigger = true;
				}
				
				if(!buildTrigger)
					return;
				
				TFieldBox source = this.form.gettFieldBox(fieldName);
				TFieldBox target = (StringUtils.isNotBlank(tAnnotation.targetFieldName())) 
						? this.form.gettFieldBox(tAnnotation.targetFieldName()) 
								: null;
						
				if(source==null){
					throw new Exception("\n\nT_ERROR "
							+ "\nType: "+TErrorType.TRIGGER_BUILD
							+ "\nFIELD: "+this.form.gettModelView().getClass().getSimpleName() + "."+fieldName
							+ "\n\nThe source field not found, maybe the field are not visible for the selected mode\n\n");
				}
				
				final com.tedros.fxapi.control.trigger.TTrigger trigger = tAnnotation.triggerClass()
						.getConstructor(TFieldBox.class, TFieldBox.class).newInstance(source, target);
				
				trigger.setForm(this.form);
				
				for(String associatedFieldName : tAnnotation.associatedFieldBox())
					if(!associatedFieldName.trim().equals(""))
						trigger.addAssociatedField(this.form.gettFieldBox(associatedFieldName));
				
				final Node sourceControl = source.gettControl();
				
				final Observable ob = tAnnotation.triggerFieldBox() 
						? source.tValueProperty()
								: ((ITTriggeredable) sourceControl).tValueProperty();
						
				String key =  "trigger"+idx+"_"+fieldName;
						
				if(ob instanceof ObservableValue) {
					final ChangeListener l = (obs, old, value) -> {
						trigger.run(value);
					};
					this.form.gettObjectRepository().add(key, l);
					ObservableValue obt = (ObservableValue) ob;
					obt.addListener(new WeakChangeListener<>(l));
					
				} else 
				if(ob instanceof ObservableList) {
					final ListChangeListener l = (value) -> {
						trigger.run(value.getList());
					};
					this.form.gettObjectRepository().add(key, l);
					ObservableList obt = (ObservableList) ob;
					obt.addListener(new WeakListChangeListener<>(l));
				} else 
				if(ob instanceof ObservableSet) {
					final SetChangeListener l = (value) -> {
						trigger.run(value.getSet());
					};
					this.form.gettObjectRepository().add(key, l);
					ObservableSet obt = (ObservableSet) ob;
					obt.addListener(new WeakSetChangeListener<>(l));
				}
				
				if(tAnnotation.runAfterFormBuild())
					trigger.run(null);
				
			}
		}
	}
	
}
