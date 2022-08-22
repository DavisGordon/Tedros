package org.tedros.fx.form;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.control.ITTriggeredable;
import org.tedros.fx.exception.TErrorType;

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
		
		this.form.gettFieldDescriptorList().parallelStream()
		.forEach(tFieldDescriptor -> {
		
			final String fieldName = tFieldDescriptor.getFieldName();
			
			tFieldDescriptor.getAnnotations()
			.parallelStream()
			.filter(a->{ return a instanceof TTrigger; })
			.findFirst()
			.ifPresent(a -> {
				TTrigger tAnnotation = (TTrigger) a;
				boolean buildTrigger = ArrayUtils.contains(tAnnotation.mode(), this.form.gettMode());
				if(!buildTrigger)
					return;
				
				TFieldBox source = this.form.gettFieldBox(fieldName);
				TFieldBox target = (StringUtils.isNotBlank(tAnnotation.targetFieldName())) 
						? this.form.gettFieldBox(tAnnotation.targetFieldName()) 
								: null;
						
				if(source==null){
					System.err.println("\n\nT_ERROR "
							+ "\nType: "+TErrorType.TRIGGER_BUILD
							+ "\nFIELD: "+this.form.gettModelView().getClass().getSimpleName() + "."+fieldName
							+ "\n\nThe source field not found, maybe the field are not visible for the selected mode\n\n");
				}
				
				try {
					final org.tedros.fx.control.trigger.TTrigger trigger = tAnnotation.triggerClass()
							.getConstructor(TFieldBox.class, TFieldBox.class).newInstance(source, target);
				
					trigger.setForm(this.form);
					
					for(String associatedFieldName : tAnnotation.associatedFieldBox())
						if(!associatedFieldName.trim().equals(""))
							trigger.addAssociatedField(this.form.gettFieldBox(associatedFieldName));
					
					final Node sourceControl = source.gettControl();
					
					final Observable ob = tAnnotation.triggerFieldBox() 
							? source.tValueProperty()
									: ((ITTriggeredable) sourceControl).tValueProperty();
							
					String key =  "trigger_"+fieldName;
							
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
							trigger.run(value);
						};
						this.form.gettObjectRepository().add(key, l);
						ObservableList obt = (ObservableList) ob;
						obt.addListener(new WeakListChangeListener<>(l));
					} else 
					if(ob instanceof ObservableSet) {
						final SetChangeListener l = (value) -> {
							trigger.run(value);
						};
						this.form.gettObjectRepository().add(key, l);
						ObservableSet obt = (ObservableSet) ob;
						obt.addListener(new WeakSetChangeListener<>(l));
					}
					
					if(tAnnotation.runAfterFormBuild())
						trigger.run(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
	}
	
}
