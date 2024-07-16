package org.tedros.fx.form;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.tedros.api.form.ITModelForm;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.control.ITTriggeredable;
import org.tedros.fx.control.trigger.TTrigger.TEvent;
import org.tedros.fx.exception.TErrorType;
import org.tedros.util.TLoggerUtil;

import javafx.beans.Observable;
import javafx.beans.property.Property;
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

	private final static Logger LOGGER = TLoggerUtil.getLogger(TTriggerLoader.class);
	
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
				
				TFieldBox source = (TFieldBox) this.form.gettFieldBox(fieldName);
				TFieldBox target = (StringUtils.isNotBlank(tAnnotation.targetFieldName())) 
						? (TFieldBox) this.form.gettFieldBox(tAnnotation.targetFieldName()) 
								: null;
						
				if(source==null){
					LOGGER.warn("Type: "+TErrorType.TRIGGER_BUILD
							+ " FIELD: "+this.form.gettModelView().getClass().getSimpleName() + "."+fieldName
							+ "\n\t\tThe source field not found, maybe it are not visible for the selected mode");
				}
				
				try {
					final org.tedros.fx.control.trigger.TTrigger trigger = tAnnotation.type()
							.getConstructor(TFieldBox.class, TFieldBox.class).newInstance(source, target);
				
					trigger.setForm(this.form);
					
					for(String associatedFieldName : tAnnotation.associatedFieldBox())
						if(!associatedFieldName.trim().equals(""))
							trigger.addAssociatedField((TFieldBox)this.form.gettFieldBox(associatedFieldName));
					
					final Node sourceControl = source.gettControl();
					final Observable ob = tAnnotation.triggerFieldBox() 
							? source.tValueProperty()
									: sourceControl instanceof ITTriggeredable
									? ((ITTriggeredable) sourceControl).tValueProperty()
											:  form.gettModelView().getProperty(fieldName);
							
					String key =  "trigger_"+fieldName;
							
					if(ob instanceof ObservableValue) {
						final ChangeListener l = (obs, old, value) -> {
							trigger.run(TEvent.ADDED, value, old);
						};
						this.form.gettObjectRepository().add(key, l);
						ObservableValue obt = (ObservableValue) ob;
						obt.addListener(new WeakChangeListener<>(l));

						if(tAnnotation.runAfterFormBuild()) {
							trigger.run(TEvent.BUILD, ((Property)ob).getValue(), null);
						}
					} else 
					if(ob instanceof ObservableList) {
						final ListChangeListener l = (value) -> {
							if(value.next()) {
								if(value.wasAdded())
									trigger.run(TEvent.ADDED, value.getAddedSubList(), value.getList());
								if(value.wasRemoved())
									trigger.run(TEvent.REMOVED, value.getRemoved(), value.getList());
							}
						};
						this.form.gettObjectRepository().add(key, l);
						ObservableList obt = (ObservableList) ob;
						obt.addListener(new WeakListChangeListener<>(l));
						
						if(tAnnotation.runAfterFormBuild()) 
							trigger.run(TEvent.BUILD, (List) ob, null);
						
					} else 
					if(ob instanceof ObservableSet) {
						final SetChangeListener l = (value) -> {
							Set s = new HashSet();
							if(value.wasAdded()) {
								s.add(value.getElementAdded());
								trigger.run(TEvent.ADDED, s, value.getSet());
							}
							if(value.wasRemoved()) {
								s.add(value.getElementRemoved());
								trigger.run(TEvent.REMOVED, s, value.getSet());
							}
						};
						this.form.gettObjectRepository().add(key, l);
						ObservableSet obt = (ObservableSet) ob;
						obt.addListener(new WeakSetChangeListener<>(l));

						if(tAnnotation.runAfterFormBuild()) 
							trigger.run(TEvent.BUILD, (Set) ob, null);
						
					}
					
				} catch (Exception e) {
					LOGGER.error(e.getMessage(),e);
				}
			});
		});
	}
	
}
