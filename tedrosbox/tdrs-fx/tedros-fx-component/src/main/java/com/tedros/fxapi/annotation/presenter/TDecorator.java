package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.presenter.decorator.ITDecorator;
import com.tedros.fxapi.presenter.entity.decorator.TMasterCrudViewDecorator;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.ANNOTATION_TYPE)
public @interface TDecorator {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITDecorator> type() default TMasterCrudViewDecorator.class;
	
	/**
	 * <pre>
	 * The {@link Node} settings.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Region} settings.
	 * </pre>
	 * */
	public TRegion region() default @TRegion(parse = false);
	
	/**
	 * <pre>
	 * The {@link Pane} settings.
	 * </pre>
	 * */
	public TPane pane() default @TPane;
	
	/**
	 * Set the new button text
	 * */
	public String newButtonText() default TAnnotationDefaultValue.TVIEW_newButtonText;
	
	/**
	 * Set the save button text
	 * */
	public String saveButtonText() default TAnnotationDefaultValue.TVIEW_saveButtonText;
	
	/**
	 * Set the search button text
	 * */
	public String searchButtonText() default TAnnotationDefaultValue.TVIEW_searchButtonText;
	
	/**
	 * Set the delete button text
	 * */
	public String deleteButtonText() default TAnnotationDefaultValue.TVIEW_deleteButtonText;
	
	/**
	 * Set the edit button text
	 * */
	public String editButtonText() default TAnnotationDefaultValue.TVIEW_editButtonText;

	/**
	 * Set the print button text
	 * */
	public String printButtonText() default TAnnotationDefaultValue.TVIEW_printButtonText;
	
	/**
	 * Set the cancel button text
	 * */
	public String cancelButtonText() default TAnnotationDefaultValue.TVIEW_cancelButtonText;
	
	/**
	 * Set the modal close button text
	 * */
	public String closeButtonText() default TAnnotationDefaultValue.TVIEW_closeButtonText;
	
	
	
	/**
	 * <pre>
	 * Set the clean button text
	 * </pre>
	 * */
	public String cleanButtonText() default TAnnotationDefaultValue.TVIEW_cleanButtonText;

	/**
	 * <pre>
	 * Set the excel button text
	 * </pre>
	 * */
	public String excelButtonText() default TAnnotationDefaultValue.TVIEW_excelButtonText;

	/**
	 * <pre>
	 * Set the word button text
	 * </pre>
	 * */
	public String wordButtonText() default TAnnotationDefaultValue.TVIEW_wordButtonText;

	/**
	 * <pre>
	 * Set the PDF button text
	 * </pre>
	 * */
	public String pdfButtonText() default TAnnotationDefaultValue.TVIEW_pdfButtonText;

	/**
	 * <pre>
	 * Set the Select button text
	 * </pre>
	 * */
	public String selectButtonText() default TAnnotationDefaultValue.TVIEW_selectButtonText;
	
	/**
	 * <pre>
	 * Set the Select All button text
	 * </pre>
	 * */
	public String selectAllButtonText() default TAnnotationDefaultValue.TVIEW_selectAllButtonText;
	
	/**
	 * <pre>
	 * Set the Import button text
	 * </pre>
	 * */
	public String importButtonText() default TAnnotationDefaultValue.TVIEW_importButtonText;
	
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

	/**
	 * <pre>
	 * Build the print button if true
	 * 
	 * Default value: false
	 * </pre>
	 * */
	public boolean buildPrintButton() default false;

	/**
	 * <pre>
	 * Build the import button if true
	 * 
	 * Default value: false
	 * </pre>
	 * */
	public boolean buildImportButton() default false;

	/**
	 * <pre>
	 * Build the New button
	 * 
	 * Default value: true
	 * </pre>
	 * */
	public boolean buildNewButton() default true;

	/**
	 * <pre>
	 * Build the Save button
	 * 
	 * Default value: true
	 * </pre>
	 * */
	public boolean buildSaveButton() default true;

	/**
	 * <pre>
	 * Build the Delete button
	 * 
	 * Default value: true
	 * </pre>
	 * */
	public boolean buildDeleteButton() default true;
	
	/**
	 * <pre>
	 * Build the Cancel button
	 * 
	 * Default value: true
	 * </pre>
	 * */
	public boolean buildCancelButton() default true;
	
	/**
	 * <pre>
	 * Build the Collapse button
	 * 
	 * Default value: true
	 * </pre>
	 * */
	public boolean buildCollapseButton() default true;
	
	/**
	 * <pre>
	 * Build the Modes Radio button
	 * 
	 * Default value: true
	 * </pre>
	 * */
	public boolean buildModesRadioButton() default true;
	
}
