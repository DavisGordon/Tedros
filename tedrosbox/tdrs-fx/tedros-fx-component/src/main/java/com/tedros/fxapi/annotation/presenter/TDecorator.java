package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.presenter.decorator.ITDecorator;

@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.ANNOTATION_TYPE)
public @interface TDecorator {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITDecorator> type();
	
	/**
	 * Set the new button text
	 * */
	public String newButtonText() default TAnnotationDefaultValue.TVIEW_newButtonText;
	
	/**
	 * Set the save button text
	 * */
	public String saveButtonText() default TAnnotationDefaultValue.TVIEW_saveButtonText;
	
	/**
	 * Set the delete button text
	 * */
	public String deleteButtonText() default TAnnotationDefaultValue.TVIEW_deleteButtonText;
	
	/**
	 * Set the edit button text
	 * */
	public String editButtonText() default TAnnotationDefaultValue.TVIEW_editButtonText;
	
	/**
	 * Set the edit button text
	 * */
	public String cancelButtonText() default TAnnotationDefaultValue.TVIEW_cancelButtonText;
	
	/**
	 * Set the view title text
	 * */
	public String viewTitle() default TAnnotationDefaultValue.TVIEW_viewTitle;
	
	/**
	 * Set the list view title text
	 * */
	public String listTitle() default TAnnotationDefaultValue.TVIEW_listTitle;
	
	/**
	 * <pre>
	 * Set the edit mode title text
	 * 
	 * Default value: Edit
	 * </pre>
	 * */
	public String editModeTitle() default TAnnotationDefaultValue.TVIEW_editModeTitle;
	
	/**
	 * <pre>
	 * Set the reader mode title text
	 * 
	 * Default value: Read
	 * </pre>
	 * */
	public String readerModeTitle() default TAnnotationDefaultValue.TVIEW_readerModeTitle;

}
