package org.tedros.fx.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.TDebugConfig;
import org.tedros.fx.builder.ITBuilder;
import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.ITReaderBuilder;
import org.tedros.fx.builder.ITReaderHtmlBuilder;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.reader.THtmlReader;
import org.tedros.fx.util.TReflectionUtil;

import javafx.scene.Node;

public final class TControlLayoutReaderBuilder {
	
	
	private TControlLayoutReaderBuilder() {
		
	}
	
	public static final Node getField(TComponentDescriptor descriptor) throws Exception{
		
		Long startTime = TDebugConfig.detailParseExecution ? System.nanoTime() : null;
		
		Node node = getReaderField(descriptor);
		
		Long endTime = TDebugConfig.detailParseExecution ? System.nanoTime() : null;
		
		if(TDebugConfig.detailParseExecution) {
			try{
				long duration = (endTime - startTime);
				System.out.println("[TControlLayoutReaderBuilder][Field: "+descriptor.getFieldDescriptor().getFieldName()+"][Node: "+( (node==null) ? "null" : (node instanceof TFieldBox) ? ((TFieldBox) node).gettControl().getClass().getSimpleName(): node.getClass().getSimpleName())+"][Build duration: "+(duration/1000000)+"ms, "+(TimeUnit.MILLISECONDS.toSeconds(duration/1000000))+"s] ");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return node;
	}
	
	@SuppressWarnings({"rawtypes"})
	private static final Node getReaderField(TComponentDescriptor descriptor) throws Exception{
		
		if(descriptor.getFieldDescriptor().isLoaded())
			return null;
		
		/*int x=0;
		if(descriptor.getFieldDescriptor().getFieldName().equals("textoCadastro"))
			x=1;*/
		
		final String fieldName = descriptor.getFieldDescriptor().getFieldName();
		final ITModelView modelView = descriptor.getModelView();
		final Method modelViewGetMethod = getMethod(fieldName, modelView);
		final List<Annotation> fieldAnnotations = descriptor.getFieldAnnotationList();
		
		ITFieldBuilder layoutBuilder = null;
		ITFieldBuilder readerBuilder = null;
		Annotation readerAnnotation = null;
		Annotation layoutAnnotation = null;
		
		Object[] arrLayout = TReflectionUtil.getLayoutBuilder(fieldAnnotations);
		Object[] arrReaderHtml = TReflectionUtil.getReaderHtmlBuilder(fieldAnnotations);
		Object[] arrReader = (arrReaderHtml==null) 
				?  TReflectionUtil.getReaderBuilder(fieldAnnotations) 
						: null;
		
		boolean htmlReaderFlag = true;
		
		if(arrReaderHtml !=null ){
			readerAnnotation = (Annotation) arrReaderHtml[0];
			readerBuilder = (ITFieldBuilder) arrReaderHtml[1];
			
			if(!isModeActive(readerAnnotation, TViewMode.READER)){
				readerAnnotation = null;
				readerBuilder = null;
			}else			
			if(readerBuilder instanceof ITBuilder){
				((ITBuilder) readerBuilder).setComponentDescriptor(descriptor);
			}
			
		}else 
		if(arrReader !=null ){
			htmlReaderFlag = false;
			readerAnnotation = (Annotation) arrReader[0];
			readerBuilder = (ITFieldBuilder) arrReader[1];
			
			if(!isModeActive(readerAnnotation, TViewMode.READER)){
				readerAnnotation = null;
				readerBuilder = null;
			}else			
			if(readerBuilder instanceof ITBuilder){
				((ITBuilder) readerBuilder).setComponentDescriptor(descriptor);
			}
		}
		
		if(arrLayout !=null ){
			layoutAnnotation = (Annotation) arrLayout[0];
			layoutBuilder = (ITFieldBuilder) arrLayout[1];
			
			if(!isModeActive(layoutAnnotation, TViewMode.READER)){
				layoutAnnotation = null;
				layoutBuilder = null;
			}else			
			if(layoutBuilder!=null && layoutBuilder instanceof ITBuilder){
				((ITBuilder) layoutBuilder).setComponentDescriptor(descriptor);
			}
		}
		
		if(layoutBuilder==null && readerBuilder==null){
			return null;
		}
		
		if(layoutBuilder!=null && readerBuilder!=null){
			if(!htmlReaderFlag){
				Node control = buildReader(modelView, modelViewGetMethod, readerAnnotation, readerBuilder);
				TFieldBox fieldBox = TFieldBoxBuilder.build(control, descriptor);
				Node layout = ((ITLayoutBuilder) layoutBuilder).build(layoutAnnotation);
				registerComponent(descriptor, fieldBox);
				descriptor.getFieldDescriptor().setComponentLoaded(true);
				descriptor.getFieldDescriptor().setLayoutLoaded(true);
				return layout;
			}else{
				THtmlReader control = buildHtmlBox((THtmlReader) buildReader(modelView, modelViewGetMethod, readerAnnotation, readerBuilder), descriptor);
				TFieldBox fieldBox = new TFieldBox(descriptor.getFieldDescriptor().getFieldName(), null, control, null);
				THtmlReader layout = ((ITLayoutBuilder) layoutBuilder).build(layoutAnnotation, control);
				registerComponent(descriptor, fieldBox);
				descriptor.getFieldDescriptor().setComponentLoaded(true);
				descriptor.getFieldDescriptor().setLayoutLoaded(true);
				return layout;
			}
		}else if(layoutBuilder!=null && readerBuilder==null) {
			if(!htmlReaderFlag){
				Node layout = ((ITLayoutBuilder) layoutBuilder).build(layoutAnnotation);
				registerComponent(descriptor, layout);
				descriptor.getFieldDescriptor().setLayoutLoaded(true);
				return layout;
			}else{
				THtmlReader layout = ((ITLayoutBuilder) layoutBuilder).build(layoutAnnotation, null);
				registerComponent(descriptor, layout);
				descriptor.getFieldDescriptor().setLayoutLoaded(true);
				return layout;
			}
		}else{
			if(htmlReaderFlag){ 
				THtmlReader control =  buildHtmlBox((THtmlReader) buildReader(modelView, modelViewGetMethod, readerAnnotation, readerBuilder), descriptor);
				descriptor.getFieldDescriptor().setComponentLoaded(true);
				return control;
			}else {
				Node control =   buildFieldBox((Node) buildReader(modelView, modelViewGetMethod, readerAnnotation, readerBuilder), descriptor);
				descriptor.getFieldDescriptor().setComponentLoaded(true);
				return control;
			}
		}
		
	}

	private static boolean isModeActive(Annotation annotation, TViewMode mode) {
		TViewMode[] modes = TReflectionUtil.getValue(annotation, "mode");
		if(modes!=null){
			for (TViewMode tMode : modes) {
				if(tMode==mode)
					return true;
			}
			return false;
		}else
			return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> T runConverter(Annotation annotation, Object obj){
		TConverter converter = TReflectionUtil.getConverter(annotation);
		if(converter!=null){
			converter.setIn(obj);
			return (T) converter.getOut();
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> T buildReader(final ITModelView modelView, final Method modelViewGetMethod, Annotation annotation, ITFieldBuilder builder) throws IllegalAccessException, InvocationTargetException {
		
		if(builder instanceof ITReaderHtmlBuilder){
			
			final Object obj = (Object) modelViewGetMethod.invoke(modelView);
			final THtmlReader node = runConverter(annotation, obj);
			
			if(node!=null){
				return (T) node;
			}else			
				try{
					return (T) ((ITReaderHtmlBuilder) builder).build(annotation, obj);
				}catch (Exception e) {
					e.printStackTrace();
				}
		}
		
		if(builder instanceof ITReaderBuilder){
			
			final Object obj = (Object) modelViewGetMethod.invoke(modelView);
			final Node node = runConverter(annotation, obj);
			
			if(node!=null){
				return (T) node;
			}else			
				try{
					return (T) ((ITReaderBuilder) builder).build(annotation, obj);
				}catch (Exception e) {
					e.printStackTrace();
				}
		}
		
		return null;
	}
	
	private static Node buildFieldBox(Node node, final TComponentDescriptor descriptor) {
		final TFieldBox fieldBox = TFieldBoxBuilder.build(node, descriptor);
		registerComponent(descriptor, fieldBox);
		return fieldBox;
		
	}
	
	private static THtmlReader buildHtmlBox(THtmlReader node, final TComponentDescriptor descriptor) {
		final THtmlReader tHtmlReader = THtmlBoxBuilder.build(node, descriptor);
		TFieldBox fieldBox = new TFieldBox(descriptor.getFieldDescriptor().getFieldName(), null, tHtmlReader, null);
		registerComponent(descriptor, fieldBox);
		return tHtmlReader;
		
	}
	
	private static void registerComponent(final ITComponentDescriptor descriptor, final Node node) {
		descriptor.getComponents().put(descriptor.getFieldDescriptor().getFieldName(), node);
	}
	
	private static Method getMethod(final String fieldName, final Object modelView) throws NoSuchMethodException {
		return modelView.getClass().getMethod("get"+StringUtils.capitalize(fieldName));
	}
	
}

