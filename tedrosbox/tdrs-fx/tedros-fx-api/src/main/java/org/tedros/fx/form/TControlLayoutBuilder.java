package org.tedros.fx.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.api.form.ITFieldBox;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.app.component.ITActionComponent;
import org.tedros.fx.annotation.TDebugConfig;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.parser.ITEffectParse;
import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.fx.builder.ITBuilder;
import org.tedros.fx.builder.ITChartBuilder;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.ITFieldBuilder;
import org.tedros.fx.builder.ITFileBuilder;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.ITReaderBuilder;
import org.tedros.fx.builder.ITReaderHtmlBuilder;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.fx.reader.THtmlReader;
import org.tedros.fx.util.TReflectionUtil;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.effect.Effect;

public final class TControlLayoutBuilder {
	
	private final Map<String, Object> parserMap = new HashMap<>();
	
	public TControlLayoutBuilder() {
		
	}
	
	public Effect getEffect(Annotation annotation) throws Exception{
		
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
	
	public void getControlField(TComponentDescriptor descriptor) throws Exception{
		
		Long startTime = TDebugConfig.detailParseExecution ? System.nanoTime() : null;
		
		if(descriptor.getMode() == TViewMode.EDIT )
			getControl(descriptor);
		else 
			getReader(descriptor);
		
		Long endTime = TDebugConfig.detailParseExecution ? System.nanoTime() : null;
		
		if(TDebugConfig.detailParseExecution) {
			try{
				long duration = (endTime - startTime);
				System.out.println("[TControlLayoutReaderBuilder][Field: "+descriptor.getFieldDescriptor().getFieldName()+
						"][Build duration: "+(duration/1000000)+"ms, "+(TimeUnit.MILLISECONDS.toSeconds(duration/1000000))+"s] ");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getLayoutField(ITComponentDescriptor descriptor) throws Exception{
		
		Long startTime = TDebugConfig.detailParseExecution ? System.nanoTime() : null;
		
		if(descriptor.getMode() == TViewMode.EDIT)
			getLayout(descriptor);
		else
			getLayoutReader(descriptor);
		
		Long endTime = TDebugConfig.detailParseExecution ? System.nanoTime() : null;
		
		if(TDebugConfig.detailParseExecution) {
			try{
				long duration = (endTime - startTime);
				System.out.println("[TControlLayoutReaderBuilder][Field: "+descriptor.getFieldDescriptor().getFieldName()+
						"][Build duration: "+(duration/1000000)+"ms, "+(TimeUnit.MILLISECONDS.toSeconds(duration/1000000))+"s] ");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({"rawtypes"})
	private void getLayout(ITComponentDescriptor descriptor) throws Exception{
		
		if(descriptor.getFieldDescriptor().isLoaded())
			return;
		
		Object[] arrLayout = descriptor.getFieldDescriptor().getArrLayout();
		
		ITFieldBuilder layoutBuilder = null;
		Annotation layoutAnnotation = null;
		
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
		
		if(layoutBuilder==null)
			return;
		
		ITFieldDescriptor fd = descriptor.getFieldDescriptor();
		Node control = fd.getComponent();
		Node layout = ((ITLayoutBuilder) layoutBuilder).build(layoutAnnotation);
		descriptor.getComponents().put(fd.getFieldName(), layout);
		if(control instanceof ITFieldBox)
			descriptor.getFieldBoxMap().put(fd.getFieldName(), (ITFieldBox) control);
		fd.setLayout(layout);
	}
	
	private void getControl(TComponentDescriptor descriptor) throws Exception{
		
		if(descriptor.getFieldDescriptor().isLoaded())
			return;
			
		Object[] arrControl = descriptor.getFieldDescriptor().getArrControl();
		
		ITFieldBuilder controlBuilder = null;
		Annotation controlAnnotation = null;
		
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
		
		if(controlBuilder==null)
			return;
		
		Node control =  buildControl(descriptor, controlBuilder, controlAnnotation);
		if(control instanceof ITActionComponent)
			descriptor.getFieldDescriptor().setComponent(control);
		else {
			Node component = buildFieldBox(control, descriptor);
			descriptor.getFieldDescriptor().setComponent(component);
		}
		
	}
	
	private void getReader(TComponentDescriptor descriptor) throws Exception{
		
		if(descriptor.getFieldDescriptor().isLoaded())
			return;
		
		final List<Annotation> fieldAnnotations = descriptor.getFieldAnnotationList();
		
		ITFieldBuilder readerBuilder = null;
		Annotation readerAnnotation = null;
		
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
		
		if(readerBuilder==null)
			return;
		
		Node node = null;
		if(htmlReaderFlag)
			node = buildHtmlBox((THtmlReader) buildReader(descriptor, readerAnnotation, readerBuilder), descriptor);
		else
			node = buildFieldBox((Node) buildReader(descriptor, readerAnnotation, readerBuilder), descriptor);
		
		descriptor.getFieldDescriptor().setComponent(node);
	}


	@SuppressWarnings({"rawtypes"})
	private final void getLayoutReader(ITComponentDescriptor descriptor) throws Exception{
		
		if(descriptor.getFieldDescriptor().isLoaded())
			return;
		
		final List<Annotation> fieldAnnotations = descriptor.getFieldAnnotationList();
		
		ITFieldBuilder layoutBuilder = null;
		Annotation layoutAnnotation = null;
		
		Object[] arrLayout = TReflectionUtil.getLayoutBuilder(fieldAnnotations);
		
		boolean htmlReaderFlag = true;
		
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
		
		if(layoutBuilder==null){
			return;
		}
		
		ITFieldDescriptor fd = descriptor.getFieldDescriptor();
		Node layout = null;
		
		if(!htmlReaderFlag){
			layout = ((ITLayoutBuilder) layoutBuilder).build(layoutAnnotation);
		}else{
			TFieldBox fieldBox = (TFieldBox) fd.getComponent();
			layout = ((ITLayoutBuilder) layoutBuilder).build(layoutAnnotation, (THtmlReader) fieldBox.gettControl());
			
		}
	
		
		TFieldBox fieldBox = (TFieldBox) fd.getComponent();
		descriptor.getComponents().put(fd.getFieldName(), layout);
		if(fieldBox!=null)
			descriptor.getFieldBoxMap().put(fd.getFieldName(), fieldBox);
		fd.setLayout(layout);
		
	}
	
	private boolean isModeActive(Annotation annotation, TViewMode mode) {
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
	private <T> T runConverter(Annotation annotation, Object obj){
		TConverter converter = TReflectionUtil.getConverter(annotation);
		if(converter!=null){
			converter.setIn(obj);
			return (T) converter.getOut();
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> T buildReader(final TComponentDescriptor descriptor, Annotation annotation, ITFieldBuilder builder) throws IllegalAccessException, InvocationTargetException {
		
		if(builder instanceof ITReaderHtmlBuilder){
			
			final Object obj = descriptor.getFieldValue();
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
			
			final Object obj = descriptor.getFieldValue(); 
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
	private Node buildControl(ITComponentDescriptor descriptor, ITFieldBuilder controlBuilder, Annotation controlAnnotation)
			throws IllegalAccessException, InvocationTargetException, Exception, NoSuchMethodException {
		
		Node node = null;
		if(controlBuilder instanceof ITControlBuilder){
			final Object attrProperty = descriptor.getFieldValue();// modelViewGetMethod.invoke(modelView);
			node = ((ITControlBuilder) controlBuilder).build(controlAnnotation, attrProperty);
		}else
		if(controlBuilder instanceof ITChartBuilder){
			final Node chart = (Node) descriptor.getFieldValue();//modelViewGetMethod.invoke(modelView);
			node =  ((ITChartBuilder) controlBuilder).build(controlAnnotation, chart);
		}else
		if(controlBuilder instanceof ITFileBuilder){
						
			final Object obj = (Object) descriptor.getFieldValue();//modelViewGetMethod.invoke(modelView);
			
			if(obj instanceof TSimpleFileProperty){
				TSimpleFileProperty<?> attrProperty = (TSimpleFileProperty<?>) obj;
				node =  ((ITFileBuilder) controlBuilder).build(controlAnnotation, attrProperty);
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
				final Method fileNameFieldGetMethod = descriptor.getModelView().getClass().getMethod("get"+StringUtils.capitalize(fileName));
				final SimpleStringProperty fileNameProperty = (SimpleStringProperty) fileNameFieldGetMethod.invoke(descriptor.getModelView());
				node =  ((ITFileBuilder) controlBuilder).build(controlAnnotation, fileNameProperty, (Property<byte[]>) attrProperty);
				
			}
			
		}
	
		this.applyEffects(descriptor, node);
		descriptor.getFieldDescriptor().setControl(node);
		return node;
	}
	
	private void applyEffects(ITComponentDescriptor descriptor, Node control) throws Exception {
		if(control==null){
			System.err.println("WARNING: Control null to "+descriptor.getFieldDescriptor().getFieldName());
			return;
		}
		
		final List<Annotation> fieldEffects = TReflectionUtil.getEffectAnnotations(descriptor.getFieldAnnotationList());

		if(control!=null && !fieldEffects.isEmpty()){
			for (final Annotation effect : fieldEffects) {
				Effect e = getEffect(effect);
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
		
		
	}
	
	private Node buildFieldBox(Node node, final TComponentDescriptor descriptor) {
		final TFieldBox fieldBox = TFieldBoxBuilder.build(node, descriptor);
		descriptor.getFieldBoxMap().put(descriptor.getFieldDescriptor().getFieldName(), fieldBox);
		descriptor.getComponents().put(descriptor.getFieldDescriptor().getFieldName(), fieldBox);
		return fieldBox;
		
	}
	
	private THtmlReader buildHtmlBox(THtmlReader node, final TComponentDescriptor descriptor) {
		final THtmlReader tHtmlReader = THtmlBoxBuilder.build(node, descriptor);
		TFieldBox fieldBox = new TFieldBox(descriptor.getFieldDescriptor().getFieldName(), null, tHtmlReader, null);
		descriptor.getFieldBoxMap().put(descriptor.getFieldDescriptor().getFieldName(), fieldBox);
		descriptor.getComponents().put(descriptor.getFieldDescriptor().getFieldName(), fieldBox);
		return tHtmlReader;
		
	}
}

