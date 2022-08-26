package org.tedros.api.descriptor;

import java.lang.annotation.Annotation;
import java.util.List;

import org.tedros.api.form.ITFieldBox;
import org.tedros.api.form.ITForm;
import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.model.ITModelView;

import com.sun.javafx.collections.MappingChange.Map;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Node;

public interface ITComponentDescriptor {

	ITFieldDescriptor getFieldDescriptor(String fieldName);

	boolean isLoaded();

	ReadOnlyBooleanProperty loadedProperty();

	/**
	 * <pre>
	 * Return the annotation
	 * </pre>
	 * */
	<A extends Annotation> A getFieldAnnotation(Class<A> type);

	/**
	 * <pre>
	 * Return a {@link List} of all {@link Annotation} used in the field. 
	 * </pre>
	 * 
	 * @return {@link List} of {@link Annotation}
	 * */
	List<Annotation> getFieldAnnotationList();

	/**
	 * <pre>
	 * Return a {@link List} of {@link ITFieldDescriptor} of all fields in the model view. 
	 * </pre>
	 * 
	 * @return {@link List} of {@link ITFieldDescriptor}
	 * */
	List<ITFieldDescriptor> getFieldDescriptorList();

	/**
	 * <pre>
	 * Return the {@link ITForm} of the model view. 
	 * </pre>
	 * 
	 * @return {@link ITForm}
	 * */
	ITForm getForm();

	/**
	 * <pre>
	 * Return the {@link ITModelView} in execution.
	 * </pre>
	 * 
	 * @return {@link ITModelView}
	 * */
	@SuppressWarnings("rawtypes")
	ITModelView getModelView();

	/**
	 * <pre>
	 * Return a {@link List} of {@link String} with 
	 * the name of all fields in the model view in execution. 
	 * </pre>
	 * 
	 * @return {@link List} of {@link String}
	 * */
	List<String> getFieldNameList();

	/**
	 * <pre>
	 * Return a {@link List} of {@link Annotation} used in the model view declaration type.   
	 * </pre>
	 * 
	 * @return {@link List} of {@link Annotation}
	 * */
	List<Annotation> getModelViewAnnotationList();

	/**
	 * <pre>
	 * Return a {@link Map} of <{@link String}, {@link Node}> with all components builded at the present moment.
	 * 
	 * The key value represents the field name in the model view.
	 * </pre>
	 * 
	 * @return {@link Map} of <{@link String}, {@link Node}>
	 * */
	ObservableMap<String, Node> getComponents();

	/**
	 * <pre>
	 * Return the {@link TFieldDescriptor} of the field in execution.
	 * </pre>
	 * 
	 * @return {@link TFieldDescriptor}
	 * */
	ITFieldDescriptor getFieldDescriptor();

	/**
	 * <pre>
	 * Set the {@link TFieldDescriptor} of the field in execution.
	 * </pre>
	 * */
	void setFieldDescriptor(ITFieldDescriptor fieldDescriptor);

	/**
	 * <pre>
	 * Return the {@link TViewMode} that indicates which mode are executing.
	 * </pre>
	 * 
	 * @return {@link TViewMode}
	 * */
	TViewMode getMode();

	/**
	 * <pre>
	 * Set the {@link TViewMode} that indicates which mode are executing.
	 * </pre>
	 * */
	void setMode(TViewMode mode);

	/**
	 * <pre>
	 * Return the annotation property name in execution by the parser. 
	 * </pre>
	 * 
	 * @return {@link String}
	 * */
	String getAnnotationPropertyInExecution();

	/**
	 * <pre>
	 * Set the annotation property name in execution by the parser. 
	 * </pre>
	 * */
	void setAnnotationPropertyInExecution(String annotationPropertyInExecution);

	/**
	 * <pre>
	 * Return the class of the annotation parser in execution. 
	 * </pre>
	 * 
	 * @return {@link Class} of {@link ITAnnotationParser} type. 
	 * */
	@SuppressWarnings("rawtypes")
	Class<? extends ITAnnotationParser> getParserClassInExecution();

	/**
	 * <pre>
	 * Set the class of the annotation parser in execution. 
	 * </pre> 
	 * */
	@SuppressWarnings("rawtypes")
	void setParserClassInExecution(Class<? extends ITAnnotationParser> parserClassInExecution);

	/**
	 * <pre>
	 * Return the value of the field in execution. 
	 * </pre>
	 * @return {@link Object}. 
	 * */
	Object getFieldValue() throws IllegalArgumentException, IllegalAccessException;

	ITFieldBox getFieldBox(String field);

	/**
	 * @return the fieldBoxMap
	 */
	java.util.Map<String, ITFieldBox> getFieldBoxMap();

}