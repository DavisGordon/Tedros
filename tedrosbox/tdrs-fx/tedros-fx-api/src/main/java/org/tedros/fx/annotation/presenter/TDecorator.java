package org.tedros.fx.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.presenter.decorator.ITDecorator;
import org.tedros.fx.annotation.TDefaultValue;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;

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
	public String newButtonText() default TDefaultValue.TVIEW_newButtonText;
	
	/**
	 * Set the save button text
	 * */
	public String saveButtonText() default TDefaultValue.TVIEW_saveButtonText;
	
	/**
	 * Set the search button text
	 * */
	public String searchButtonText() default TDefaultValue.TVIEW_searchButtonText;
	
	/**
	 * Set the delete button text
	 * */
	public String deleteButtonText() default TDefaultValue.TVIEW_deleteButtonText;
	
	/**
	 * Set the edit button text
	 * */
	public String editButtonText() default TDefaultValue.TVIEW_editButtonText;

	/**
	 * Set the print button text
	 * */
	public String printButtonText() default TDefaultValue.TVIEW_printButtonText;
	
	/**
	 * Set the cancel button text
	 * */
	public String cancelButtonText() default TDefaultValue.TVIEW_cancelButtonText;
	
	/**
	 * Set the modal close button text
	 * */
	public String closeButtonText() default TDefaultValue.TVIEW_closeButtonText;
	
	/**
	 * <pre>
	 * Set the clean button text
	 * </pre>
	 * */
	public String cleanButtonText() default TDefaultValue.TVIEW_cleanButtonText;

	/**
	 * <pre>
	 * Set the excel button text
	 * </pre>
	 * */
	public String excelButtonText() default TDefaultValue.TVIEW_excelButtonText;

	/**
	 * <pre>
	 * Set the word button text
	 * </pre>
	 * */
	public String wordButtonText() default TDefaultValue.TVIEW_wordButtonText;

	/**
	 * <pre>
	 * Set the PDF button text
	 * </pre>
	 * */
	public String pdfButtonText() default TDefaultValue.TVIEW_pdfButtonText;

	/**
	 * <pre>
	 * Set the Open Export Folder button text
	 * </pre>
	 * */
	public String openExportFolderButtonText() default TDefaultValue.TVIEW_openExportFolderButtonText;
	/**
	 * <pre>
	 * Set the Select button text
	 * </pre>
	 * */
	public String selectButtonText() default TDefaultValue.TVIEW_selectButtonText;
	
	/**
	 * <pre>
	 * Set the Select All button text
	 * </pre>
	 * */
	public String selectAllButtonText() default TDefaultValue.TVIEW_selectAllButtonText;
	
	/**
	 * <pre>
	 * Set the Import button text
	 * </pre>
	 * */
	public String importButtonText() default TDefaultValue.TVIEW_importButtonText;
	
	/**
	 * Set the view title text
	 * */
	public String viewTitle() default TDefaultValue.TVIEW_viewTitle;
	
	/**
	 * Set the list view title text
	 * */
	public String listTitle() default TDefaultValue.TVIEW_listTitle;
	
	/**
	 * <pre>
	 * Set the edit mode title text
	 * 
	 * Default value: Edit
	 * </pre>
	 * */
	public String editModeTitle() default TDefaultValue.TVIEW_editModeTitle;
	
	/**
	 * <pre>
	 * Set the reader mode title text
	 * 
	 * Default value: Read
	 * </pre>
	 * */
	public String readerModeTitle() default TDefaultValue.TVIEW_readerModeTitle;

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
	 * Default value: false
	 * </pre>
	 * */
	public boolean buildModesRadioButton() default false;
	
}
