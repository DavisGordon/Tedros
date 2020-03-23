package com.tedros.fxapi.form;

import java.lang.annotation.Annotation;
import java.util.List;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.effect.Effect;

public abstract class TFieldLoader<M extends ITModelView<?>> {
	
	
	private final TComponentDescriptor descriptor;
	
	
	public TFieldLoader(M modelView, ITModelForm<M> form) {
		descriptor = new TComponentDescriptor(form, modelView, TViewMode.EDIT);
	}
	
	protected void initialize(){
		descriptor.getFieldBoxMap().clear();
		for(TFieldDescriptor a : descriptor.getFieldDescriptorList())
			a.setLoaded(false);
	}
	
	protected List<TFieldDescriptor> getFieldDescriptorList(){
		return descriptor.getFieldDescriptorList();
	}
	
	protected TFieldBox getFieldBox(String fieldName){
		if(descriptor.getFieldBoxMap().containsKey(fieldName))
			return descriptor.getFieldBoxMap().get(fieldName);
		else
			return null;
	}
	
	protected ObservableMap<String, TFieldBox> getFieldBoxMap() {
		return descriptor.getFieldBoxMap();
	}
	
	protected final TFieldDescriptor getFieldDescriptor(final String fieldName){
		for(final TFieldDescriptor field : descriptor.getFieldDescriptorList())
			if(field.getFieldName().equals(fieldName))
				return field;
		return null;
	}
	
	protected void loadEditFieldBox(final ObservableList<Node> nodesLoaded, final TFieldDescriptor tFieldDescriptor) throws Exception{
		
		/*int x=0;
		if(tFieldDescriptor.getFieldName().equals("painelCorTexto"))
			x=1;*/
		
		descriptor.setMode(TViewMode.EDIT);
		descriptor.setFieldDescriptor(tFieldDescriptor);
		final List<Annotation> fieldEffects = TReflectionUtil.getEffectAnnotations(descriptor.getFieldAnnotationList());
		
		Node control = TComponentBuilder.getField(descriptor);
		
		if(control==null){
			System.err.println("WARNING: Control null to "+tFieldDescriptor.getFieldName());
			return;
		}
		
		if(control!=null && !fieldEffects.isEmpty()){
			for (final Annotation effect : fieldEffects) {
				Effect e = TComponentBuilder.getEffect(effect);
				if(e!=null)
					control.setEffect(e);
			}
		}

		for (Annotation annotation : descriptor.getFieldAnnotationList()) {
			if(annotation instanceof TLabel)
				continue;
			boolean builderMethod;
			boolean parserMethod;
			try{
				builderMethod = (annotation.getClass().getMethod("builder")!=null);
			}catch(NoSuchMethodException e){ builderMethod = false;}
			try{
				parserMethod = (annotation.getClass().getMethod("parser")!=null);
			}catch(NoSuchMethodException e){ parserMethod = false;}
			
			if(parserMethod && !builderMethod){
				TAnnotationParser.callParser(annotation, control, descriptor);
			}
		}
		
		nodesLoaded.add(control);
	}
	
	protected void loadReaderFieldBox(final ObservableList<Node> nodesLoaded, final TFieldDescriptor tFieldDescriptor) throws Exception{
		
		/*int x=0;
		if(tAnnotatedField.getFieldName().equals("imagem"))
			x=1;*/
		descriptor.setMode(TViewMode.READER);
		descriptor.setFieldDescriptor(tFieldDescriptor);
		final Node reader = TComponentBuilder.getField(descriptor);
			
		if(reader==null){
			System.err.println("WARNING: Reader null to "+tFieldDescriptor.getFieldName());
			return;
		}	
		
		nodesLoaded.add(reader);
	}
	
	protected String getModelViewClassName(){
		return descriptor.getModelView().getClass().getSimpleName();
	}
	
	protected String getFormClassName(){
		return descriptor.getForm().getClass().getSimpleName();
	}
	
	protected List<String> getFieldsName(){
		return descriptor.getFieldNameList();
	}
	
	
}
