package com.tedros.fxapi.descriptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITForm;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.util.TReflectionUtil;

/**
 * <pre>
 * The component descriptor class, contais all necessary information about the field in execution, 
 * used to build and parse a field to a node.  
 * </pre>
 * 
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TComponentDescriptor {
	
	private final ITForm form;
	private final ITModelView modelView;
	private final List<TFieldDescriptor> fieldDescriptorList;
	private final List<String> fieldsNameList;
	private final List<Annotation> modelViewAnnotationList;
	private final ObservableMap<String, TFieldBox> fieldBoxMap;
	private TFieldDescriptor fieldDescriptor;
	private TViewMode mode;
	private String annotationPropertyInExecution;
	private Class<? extends ITAnnotationParser> parserClassInExecution;
	
	/**
	 * <pre>
	 * Initialize a new {@link TComponentDescriptor}
	 * </pre>
	 * */
	public TComponentDescriptor(ITForm form, ITModelView modelView, TViewMode mode) {
		this.form = form;
		this.modelView = modelView;
		this.modelViewAnnotationList = Arrays.asList(this.modelView.getClass().getAnnotations());
		this.fieldBoxMap = FXCollections.observableHashMap();
		this.fieldDescriptorList = TReflectionUtil.getFieldDescriptorList(this.modelView);
		this.fieldsNameList = new ArrayList<>(0);
		this.mode = mode;
		loadFieldNameList(); 
	}
	
	/**
	 * <pre>
	 * Initialize a clone of the given {@link TComponentDescriptor} to start build a new field.
	 * 
	 * Used when one field need build another field.
	 * </pre>
	 * */
	public TComponentDescriptor(TComponentDescriptor componentDescriptor, String fieldToBuild) {
		this.form = componentDescriptor.getForm();
		this.modelView = componentDescriptor.getModelView();
		this.modelViewAnnotationList = componentDescriptor.getModelViewAnnotationList();
		this.fieldBoxMap = componentDescriptor.getFieldBoxMap();
		this.fieldDescriptorList = componentDescriptor.getFieldDescriptorList();
		this.mode = componentDescriptor.getMode();
		this.fieldsNameList = componentDescriptor.getFieldNameList();
		if(StringUtils.isNotBlank(fieldToBuild)){
			for (final TFieldDescriptor fd : fieldDescriptorList) {
				if(fd.getFieldName().equals(fieldToBuild)){
					this.fieldDescriptor = fd;
					break;
				}
			}
		}
	}
	
	/**
	 * <pre>
	 * Return the {@link TLabel} of the field in execution
	 * </pre>
	 * @return {@link TLabel}
	 * */
	public TLabel getFieldLabelAnnotation() {
		for(Annotation annotation : fieldDescriptor.getAnnotations())
			if(annotation instanceof TLabel)
				return (TLabel) annotation;
		return null;
	}
	
	/**
	 * <pre>
	 * Return a {@link List} of all {@link Annotation} used in the field. 
	 * </pre>
	 * 
	 * @return {@link List} of {@link Annotation}
	 * */
	public List<Annotation> getFieldAnnotationList(){
		return fieldDescriptor.getAnnotations();
	}
	
	/**
	 * <pre>
	 * Return a {@link List} of {@link TFieldDescriptor} of all fields in the model view. 
	 * </pre>
	 * 
	 * @return {@link List} of {@link TFieldDescriptor}
	 * */
	public List<TFieldDescriptor> getFieldDescriptorList() {
		return fieldDescriptorList;
	}

	/**
	 * <pre>
	 * Return the {@link ITForm} of the model view. 
	 * </pre>
	 * 
	 * @return {@link ITForm}
	 * */
	public final ITForm getForm() {
		return form;
	}

	
	/**
	 * <pre>
	 * Return the {@link ITModelView} in execution.
	 * </pre>
	 * 
	 * @return {@link ITModelView}
	 * */
	public final ITModelView getModelView() {
		return modelView;
	}

	/**
	 * <pre>
	 * Return a {@link List} of {@link String} with 
	 * the name of all fields in the model view in execution. 
	 * </pre>
	 * 
	 * @return {@link List} of {@link String}
	 * */
	public final List<String> getFieldNameList() {
		return fieldsNameList;
	}

	/**
	 * <pre>
	 * Return a {@link List} of {@link Annotation} used in the model view declaration type.   
	 * </pre>
	 * 
	 * @return {@link List} of {@link Annotation}
	 * */
	public final List<Annotation> getModelViewAnnotationList() {
		return modelViewAnnotationList;
	}

	/**
	 * <pre>
	 * Return a {@link Map} of <{@link String}, {@link TFieldBox}> with all components builded at the present moment.
	 * 
	 * The key value represents the field name in the model view.
	 * </pre>
	 * 
	 * @return {@link Map} of <{@link String}, {@link TFieldBox}>
	 * */
	public final ObservableMap<String, TFieldBox> getFieldBoxMap() {
		return fieldBoxMap;
	}

	/**
	 * <pre>
	 * Return the {@link TFieldDescriptor} of the field in execution.
	 * </pre>
	 * 
	 * @return {@link TFieldDescriptor}
	 * */
	public final TFieldDescriptor getFieldDescriptor() {
		return fieldDescriptor;
	}

	/**
	 * <pre>
	 * Set the {@link TFieldDescriptor} of the field in execution.
	 * </pre>
	 * */
	public final void setFieldDescriptor(TFieldDescriptor fieldDescriptor) {
		this.fieldDescriptor = fieldDescriptor;
	}

	/**
	 * <pre>
	 * Return the {@link TViewMode} that indicates which mode are executing.
	 * </pre>
	 * 
	 * @return {@link TViewMode}
	 * */
	public final TViewMode getMode() {
		return mode;
	}

	/**
	 * <pre>
	 * Set the {@link TViewMode} that indicates which mode are executing.
	 * </pre>
	 * */
	public final void setMode(TViewMode mode) {
		this.mode = mode;
	}

	/**
	 * <pre>
	 * Return the annotation property name in execution by the parser. 
	 * </pre>
	 * 
	 * @return {@link String}
	 * */
	public final String getAnnotationPropertyInExecution() {
		return annotationPropertyInExecution;
	}

	/**
	 * <pre>
	 * Set the annotation property name in execution by the parser. 
	 * </pre>
	 * */
	public final void setAnnotationPropertyInExecution(
			String annotationPropertyInExecution) {
		this.annotationPropertyInExecution = annotationPropertyInExecution;
	}

	/**
	 * <pre>
	 * Return the class of the annotation parser in execution. 
	 * </pre>
	 * 
	 * @return {@link Class} of {@link ITAnnotationParser} type. 
	 * */
	public final Class<? extends ITAnnotationParser> getParserClassInExecution() {
		return parserClassInExecution;
	}
	
	/**
	 * <pre>
	 * Set the class of the annotation parser in execution. 
	 * </pre> 
	 * */
	public final void setParserClassInExecution(
			Class<? extends ITAnnotationParser> parserClassInExecution) {
		this.parserClassInExecution = parserClassInExecution;
	}
	
	/**
	 * <pre>
	 * Return the value of the field in execution. 
	 * </pre>
	 * @return {@link Object}. 
	 * */
	public Object getFieldValue() throws IllegalArgumentException, IllegalAccessException{
		this.fieldDescriptor.getField().setAccessible(true);
		final Object obj = this.fieldDescriptor.getField().get(this.modelView);
		this.fieldDescriptor.getField().setAccessible(false);
		return obj;
	}
	
	/**
	 * Load the fieldsNameList
	 * */
	private void loadFieldNameList() {
		for(final TFieldDescriptor f : fieldDescriptorList)
			fieldsNameList.add(f.getFieldName());
	}
	
}
