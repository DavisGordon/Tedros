/**
 * 
 */
package org.tedros.fx.presenter.behavior;

/**
 * The view/presenter action type
 * 
 * @author Davis Gordon
 *
 */
public enum TActionType {

	/**
	 * <pre>
	 * Specifies an action to the new event dispatched by the new button;
	 * </pre>
	 * */
	NEW, 
	/**
	 * <pre>
	 * Specifies an action to the save event dispatched by the save button;
	 * </pre>
	 * */
	SAVE, 
	/**
	 * <pre>
	 * Specifies an action to the delete event dispatched by the delete button;
	 * </pre>
	 * */
	DELETE, 
	/**
	 * <pre>
	 * Specifies an action to the select event dispatched from the select button;
	 * </pre>
	 * */
	SELECT, 
	/**
	 * <pre>
	 * Specifies an action to the add event dispatched from the add button;
	 * </pre>
	 * */
	ADD, 
	/**
	 * <pre>
	 * Specifies an action to the remove event dispatched from the remove button;
	 * </pre>
	 * */
	REMOVE, 
	/**
	 * <pre>
	 * Specifies an action to the edit event dispatched from the edit button;
	 * </pre>
	 * */
	EDIT,
	/**
	 * <pre>
	 * Specifies an action to the change mode event dispatched by the new change mode radio button;
	 * </pre>
	 * */
	CHANGE_MODE,
	/**
	 * <pre>
	 * Specifies an action to the action event dispatched from the action button;
	 * </pre>
	 * */
	ACTION,
	/**
	 * <pre>
	 * Specifies an action to the select item event dispatched by the list view;
	 * </pre>
	 * */
	SELECTED_ITEM,
	/**
	 * <pre>
	 * Specifies an action to the print event dispatched by the print button;
	 * </pre>
	 * */
	PRINT, 
	/**
	 * <pre>
	 * Specifies an action to the import event dispatched from the import button;
	 * </pre>
	 * */
	IMPORT, 
	/**
	 * <pre>
	 * Specifies an action to the excel event dispatched from the excel button;
	 * </pre>
	 * */
	EXPORT_EXCEL, 
	/**
	 * <pre>
	 * Specifies an action to the pdf event dispatched from the pdf button;
	 * </pre>
	 * */
	EXPORT_PDF,
	/**
	 * <pre>
	 * Specifies an action to the search event dispatched from the search button;
	 * </pre>
	 * */
	SEARCH, 
	/**
	 * <pre>
	 * Specifies an action to the cancel event dispatched from the cancel button;
	 * </pre>
	 * */
	CANCEL, 
	/**
	 * <pre>
	 * Specifies an action to the clean event dispatched from the clean button;
	 * </pre>
	 * */
	CLEAN, 
	/**
	 * <pre>
	 * Specifies an action to the modal close event dispatched from the close button;
	 * </pre>
	 * */
	CLOSE;
	
	
}
