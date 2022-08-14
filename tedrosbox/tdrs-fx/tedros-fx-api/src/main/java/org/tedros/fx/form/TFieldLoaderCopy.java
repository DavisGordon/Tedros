package org.tedros.fx.form;

import java.lang.annotation.Annotation;
import java.util.List;

import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.fx.builder.TEffectBuilder;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.descriptor.TFieldDescriptor;
import org.tedros.fx.domain.TViewMode;
import org.tedros.fx.util.TReflectionUtil;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.effect.Effect;

public abstract class TFieldLoaderCopy<M extends ITModelView<?>> {
	
	
	private final TComponentDescriptor descriptor;
	
	
	public TFieldLoaderCopy(M modelView, ITModelForm<M> form) {
		descriptor = new TComponentDescriptor(form, modelView, TViewMode.EDIT);
	}
	
	protected void initialize(){
		descriptor.getComponents().clear();
		//for(TFieldDescriptor a : descriptor.getFieldDescriptorList())
			//a.setLoaded(false);
	}
	
	protected List<TFieldDescriptor> getFieldDescriptorList(){
		return descriptor.getFieldDescriptorList();
	}
	
	protected TFieldBox getFieldBox(String fieldName){
		if(descriptor.getComponents().containsKey(fieldName))
			return (TFieldBox) descriptor.getComponents().get(fieldName);
		else
			return null;
	}
	
	protected ObservableMap<String, Node> getFieldBoxMap() {
		return descriptor.getComponents();
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
		
		Node control = TControlLayoutReaderBuilder.getField(descriptor);
		
		if(control==null){
			System.err.println("WARNING: Control null to "+tFieldDescriptor.getFieldName());
			return;
		}
		
		if(control!=null && !fieldEffects.isEmpty()){
			for (final Annotation effect : fieldEffects) {
				Effect e = TEffectBuilder.getEffect(effect);
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
		final Node reader = TControlLayoutReaderBuilder.getField(descriptor);
			
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
