package com.tedros.fxapi.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.Effect;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.TDebugConfig;
import com.tedros.fxapi.annotation.parser.ITEffectParse;
import com.tedros.fxapi.annotation.parser.ITListFileFieldBuilder;
import com.tedros.fxapi.builder.ITBuilder;
import com.tedros.fxapi.builder.ITChartBuilder;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.ITFileBuilder;
import com.tedros.fxapi.builder.ITLayoutBuilder;
import com.tedros.fxapi.builder.ITReaderBuilder;
import com.tedros.fxapi.builder.ITReaderHtmlBuilder;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;
import com.tedros.fxapi.property.TSimpleFileModelProperty;
import com.tedros.fxapi.reader.THtmlReader;
import com.tedros.fxapi.util.TReflectionUtil;

public final class TComponentBuilder {
	
	private final static Map<String, Object> parserMap = new HashMap<>();
	
	private TComponentBuilder() {
		
	}
	
	public static final Effect getEffect(Annotation annotation) throws Exception{
		
		final Method parserMethod = TReflectionUtil.getParserMethod(annotation);
		
		if(parserMethod!=null){
			
			final Class<?> parseClass = (Class<?>) parserMethod.invoke(annotation);
		
			/*
			 * ITEffectParse 
			 * */
			if(TReflectionUtil.isImplemented(parseClass, ITEffectParse.class)){
				
				ITEffectParse parser = null;
				if(parserMap.containsKey(parseClass.getName())){
					parser = (ITEffectParse) parserMap.get(parseClass.getName());
				}else{
					parser = (ITEffectParse) parseClass.newInstance();	
					parserMap.put(parseClass.getName(), parser);
				} 
				return parser.parse(annotation);
			}
		}
		
		return null;
		
	}
	
	public static final Node getField(TComponentDescriptor descriptor) throws Exception{
		long startTime = System.nanoTime();
		Node node = descriptor.getMode() == TViewMode.EDIT ? getEditField(descriptor) : getReaderField(descriptor);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		if(TDebugConfig.detailParseExecution)
			try{
				System.out.println("[TComponentBuilder][Field: "+descriptor.getFieldDescriptor().getFieldName()+"][Node: "+( (node==null) ? "null" : (node instanceof TFieldBox) ? ((TFieldBox) node).gettControl().getClass().getSimpleName(): node.getClass().getSimpleName())+"][Build duration: "+(duration/1000000)+"ms, "+(TimeUnit.MILLISECONDS.toSeconds(duration/1000000))+"s] ");
			}catch (Exception e) {
				e.printStackTrace();
			}
		return node;
	}
	
	@SuppressWarnings({"rawtypes"})
	private static final Node getEditField(TComponentDescriptor descriptor) throws Exception{
		
		/*int x=0;
		if(descriptor.getFieldDescriptor().getFieldName().equals("stringField"))
			x=1;*/
		
		if(descriptor.getFieldDescriptor().isLoaded())
			return null;
		
		final String fieldName = descriptor.getFieldDescriptor().getFieldName();
		final ITModelView modelView = descriptor.getModelView();
		final Method modelViewGetMethod = getMethod(fieldName, modelView);
		final List<Annotation> fieldAnnotations = descriptor.getFieldAnnotationList();
		
		Object[] arrLayout = TReflectionUtil.getLayoutBuilder(fieldAnnotations);
		Object[] arrControl = TReflectionUtil.getControlBuilder(fieldAnnotations);
		
		ITFieldBuilder layoutBuilder = null;
		ITFieldBuilder controlBuilder = null;
		Annotation controlAnnotation = null;
		Annotation layoutAnnotation = null;
		
		if(arrControl !=null ){
			controlAnnotation = (Annotation) arrControl[0];
			controlBuilder = (ITFieldBuilder) arrControl[1];
			
			if(!isModeActive(controlAnnotation, TViewMode.EDIT)){
				controlAnnotation = null;
				controlBuilder = null;
			}else
			if(controlBuilder instanceof ITBuilder){
				((ITBuilder) controlBuilder).setComponentDescriptor(descriptor);
			}
		}
		
		if(arrLayout !=null ){
			layoutAnnotation = (Annotation) arrLayout[0];
			layoutBuilder = (ITFieldBuilder) arrLayout[1];
			
			if(!isModeActive(layoutAnnotation, TViewMode.EDIT)){
				layoutAnnotation = null;
				layoutBuilder = null;
			}else			
			if(layoutBuilder instanceof ITBuilder){
				((ITBuilder) layoutBuilder).setComponentDescriptor(descriptor);
			}
		}
		
		if(layoutBuilder==null && controlBuilder==null){
			return null;
		}
		
		if(layoutBuilder!=null && controlBuilder!=null){
			Node control = buildControl(descriptor, modelView, modelViewGetMethod, controlBuilder, controlAnnotation);
			TFieldBox fieldBox = TFieldBoxBuilder.build(control, descriptor);
			Node layout = ((ITLayoutBuilder) layoutBuilder).build(layoutAnnotation, fieldBox);
			checkAsLoaded(descriptor, fieldBox);
			return layout;
		}else{
			return buildFieldBox(buildControl(descriptor, modelView, modelViewGetMethod, controlBuilder, controlAnnotation), descriptor);
		}
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
				Node layout = ((ITLayoutBuilder) layoutBuilder).build(layoutAnnotation, fieldBox);
				checkAsLoaded(descriptor, fieldBox);
				return layout;
			}else{
				THtmlReader control = buildHtmlBox((THtmlReader) buildReader(modelView, modelViewGetMethod, readerAnnotation, readerBuilder), descriptor);
				TFieldBox fieldBox = new TFieldBox(descriptor.getFieldDescriptor().getFieldName(), null, control, null);
				THtmlReader layout = ((ITLayoutBuilder) layoutBuilder).build(layoutAnnotation, control);
				checkAsLoaded(descriptor, fieldBox);
				return layout;
			}
		}else{
			if(htmlReaderFlag){ 
				return buildHtmlBox((THtmlReader) buildReader(modelView, modelViewGetMethod, readerAnnotation, readerBuilder), descriptor);
			}else
				return buildFieldBox((Node) buildReader(modelView, modelViewGetMethod, readerAnnotation, readerBuilder), descriptor);
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
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Node buildControl(TComponentDescriptor descriptor, final ITModelView modelView, final Method modelViewGetMethod, ITFieldBuilder controlBuilder, Annotation controlAnnotation)
			throws IllegalAccessException, InvocationTargetException, Exception, NoSuchMethodException {
		
		if(controlBuilder instanceof ITControlBuilder){
			final Object attrProperty = modelViewGetMethod.invoke(modelView);
			return ((ITControlBuilder) controlBuilder).build(controlAnnotation, attrProperty);
		}
		
		if(controlBuilder instanceof ITChartBuilder){
			final Node chart = (Node) modelViewGetMethod.invoke(modelView);
			return ((ITChartBuilder) controlBuilder).build(controlAnnotation, chart);
		}
		
		if(controlBuilder instanceof ITListFileFieldBuilder){
			final ObservableList<TSimpleFileModelProperty<?>> attrProperty = (ObservableList<TSimpleFileModelProperty<?>>) modelViewGetMethod.invoke(modelView);
			return ((ITListFileFieldBuilder) controlBuilder).build(controlAnnotation, attrProperty);
		}
		
		/*
		 * ITFileBuilder 
		 * */
		if(controlBuilder instanceof ITFileBuilder){
						
			final Object obj = (Object) modelViewGetMethod.invoke(modelView);
			
			if(obj instanceof TSimpleFileEntityProperty){
				TSimpleFileEntityProperty<?> attrProperty = (TSimpleFileEntityProperty<?>) obj;
				return ((ITFileBuilder) controlBuilder).build(controlAnnotation, attrProperty);
				
			}else if(obj instanceof TSimpleFileModelProperty){
				TSimpleFileModelProperty<?> attrProperty = (TSimpleFileModelProperty<?>) obj;
				return ((ITFileBuilder) controlBuilder).build(controlAnnotation, attrProperty);
			}else{
				
				Method fileNameFieldMethod = null;
				for (Method method : controlAnnotation.getClass().getMethods()) {
					if(method.getName().equals("fileNameField")){
						fileNameFieldMethod = method;
						break;
					}
				}
				
				if(fileNameFieldMethod==null)
					throw new Exception("T_ERROR: The annotation "+controlAnnotation+" must have a fileNameField() method of String type");
				
				final String fileName = (String) fileNameFieldMethod.invoke(controlAnnotation);
				
				Property<?> attrProperty = (Property<?>) obj;
				final Method fileNameFieldGetMethod = modelView.getClass().getMethod("get"+StringUtils.capitalize(fileName));
				final SimpleStringProperty fileNameProperty = (SimpleStringProperty) fileNameFieldGetMethod.invoke(modelView);
				return ((ITFileBuilder) controlBuilder).build(controlAnnotation, fileNameProperty, (Property<byte[]>) attrProperty);
				
			}
			
		}
		
		return null;
	}
	
	private static Node buildFieldBox(Node node, final TComponentDescriptor descriptor) {
		final TFieldBox fieldBox = TFieldBoxBuilder.build(node, descriptor);
		checkAsLoaded(descriptor, fieldBox);
		return fieldBox;
		
	}
	
	private static THtmlReader buildHtmlBox(THtmlReader node, final TComponentDescriptor descriptor) {
		final THtmlReader tHtmlReader = THtmlBoxBuilder.build(node, descriptor);
		TFieldBox fieldBox = new TFieldBox(descriptor.getFieldDescriptor().getFieldName(), null, tHtmlReader, null);
		checkAsLoaded(descriptor, fieldBox);
		return tHtmlReader;
		
	}
	
	private static void checkAsLoaded(final TComponentDescriptor descriptor, final TFieldBox fieldBox) {
		descriptor.getFieldDescriptor().setLoaded(true);
		descriptor.getFieldBoxMap().put(descriptor.getFieldDescriptor().getFieldName(), fieldBox);
	}
	
	private static Method getMethod(final String fieldName, final Object modelView) throws NoSuchMethodException {
		return modelView.getClass().getMethod("get"+StringUtils.capitalize(fieldName));
	}
	
}

