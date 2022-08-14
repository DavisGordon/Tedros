/**
 * 
 */
package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.annotation.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TTableViewParser;
import org.tedros.fx.annotation.property.TBooleanProperty;
import org.tedros.fx.annotation.property.TObjectProperty;
import org.tedros.fx.annotation.property.TReadOnlyObjectProperty;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.builder.ITColumnResizePolicyCallBackBuilder;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.ITNodeBuilder;
import org.tedros.fx.builder.ITRowFactoryCallBackBuilder;
import org.tedros.fx.builder.TTableViewBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TDefaultValues;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TableView;

/**
 * Build a {@link TableView}
 * 
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface TTableView {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.ANNOTATION_TYPE)
	public @interface TTableViewSelectionModel{
		
		/**
		* <pre>
		* {@link TableView.TableViewSelectionModel} Class
		* 
		*  Sets the value of the property cellSelectionEnabled. 
		*  
		*  Property description: 
		*  
		*  A boolean property used to represent whether the TableView 
		*  is in row or cell selection modes. 
		*  
		*  By default a TableView is in row selection mode which means 
		*  that individual cells can not be selected. 
		*  
		*  Setting cellSelectionEnabled to be true results in cells being 
		*  able to be selected (but not rows).
		* </pre>
		**/
		public boolean cellSelectionEnabled() default false;
		
		/**
		* <pre>
		* {@link TableView.TableViewSelectionModel} Class
		* 
		*  A boolean property used to represent whether the TableView 
		*  is in row or cell selection modes. 
		*  
		*  By default a TableView is in row selection mode which means 
		*  that individual cells can not be selected. 
		*  
		*  Setting cellSelectionEnabled to be true results in cells being 
		*  able to be selected (but not rows)
		* </pre>
		**/
		public TBooleanProperty cellSelectionEnabledProperty() default @TBooleanProperty(parse = false);


	};
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.ANNOTATION_TYPE)
	public @interface TTableViewFocusModel{
		
		/**
		* <pre>
		* {@link TableView.TableViewFocusModel} Class
		* 
		*  Causes the item at the given index to receive the focus. 
		*  
		*  This does not cause the current selection to change. 
		*  Updates the focusedItem and focusedIndex properties such 
		*  that focusedIndex = -1 unless
		*  
		*  0 <= index < model size
		* </pre>
		**/
		public int focus() default TAnnotationDefaultValue.DEFAULT_INT_VALUE_IDENTIFICATION;
		
		/**
		* <pre>
		* {@link TableView.TableViewFocusModel} Class
		* 
		*  The position of the current item in the TableView which has the focus
		* </pre>
		**/
		public TReadOnlyObjectProperty focusedCellProperty() default @TReadOnlyObjectProperty(parse = false);		
	}
	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link TTableViewBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder<TableView, ITObservableList>> builder() default TTableViewBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TTableViewParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TTableViewParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} settings
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} settings
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);
	
	
	
	/**
	* <pre>
	* {@link TableView} Class
	*  
	*  The TableColumns that are part of this TableView. As the user reorders the TableView columns, 
	*  this list will be updated to reflect the current visual ordering.
	* </pre>
	**/
	public TTableColumn[] columns();
	
	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  Sets the value of the property tableMenuButtonVisible. 
	*  
	*  Property description: 
	*  
	*  This controls whether a menu button is available when the user clicks in a designated 
	*  space within the TableView, within which is a radio menu item for each TableColumn in 
	*  this table. This menu allows for the user to show and hide all TableColumns easily.
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean tableMenuButtonVisible() default false;

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  Sets the value of the property columnResizePolicy. Property description: This is the function called when the user completes a column-resize operation. The two most common policies are available as static functions in the TableView class: UNCONSTRAINED_RESIZE_POLICY and CONSTRAINED_RESIZE_POLICY.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITColumnResizePolicyCallBackBuilder> columnResizePolicy() default ITColumnResizePolicyCallBackBuilder.class;

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  Sets the value of the property rowFactory. Property description: A function which produces a TableRow. The system is responsible for reusing TableRows. Return from this function a TableRow which might be usable for representing a single row in a TableView. Note that a TableRow is not a TableCell. A TableRow is simply a container for a TableCell, and in most circumstances it is more likely that you'll want to create custom TableCells, rather than TableRows. The primary use case for creating custom TableRow instances would most probably be to introduce some form of column spanning support. You can create custom TableCell instances per column by assigning the appropriate function to the cellFactory property in the TableColumn class.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITRowFactoryCallBackBuilder> rowFactory() default ITRowFactoryCallBackBuilder.class;

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  Sets the value of the property placeholder. Property description: This Node is shown to the user when the table has no content to show. This may be the case because the table model has no data in the first place, that a filter has been applied to the table model, resulting in there being nothing to show the user, or that there are no currently visible columns.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITNodeBuilder> placeholder() default ITNodeBuilder.class;

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  Sets the value of the property selectionModel. 
	*  
	*  Property description: 
	*  
	*  The SelectionModel provides the API through which it 
	*  is possible to select single or multiple items within 
	*  a TableView, as well as inspect which items have been 
	*  selected by the user. Note that it has a generic type 
	*  that must match the type of the TableView itself.
	* </pre>
	**/
	public TTableViewSelectionModel selectionModel() default @TTableViewSelectionModel();

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  Sets the value of the property focusModel. 
	*  
	*  Property description: 
	*  
	*  Represents the currently-installed TableView.TableViewFocusModel 
	*  for this TableView. Under almost all circumstances leaving this 
	*  as the default focus model will suffice.
	* </pre>
	**/
	public TTableViewFocusModel focusModel() default @TTableViewFocusModel;

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  Sets the value of the property editable. Property description: Specifies whether this TableView is editable - only if the TableView, the TableColumn (if applicable) and the TableCells within it are both editable will a TableCell be able to go into their editing state.
	* </pre>
	**/
	public boolean editable() default false;

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  The underlying data model for the TableView.
	* </pre>
	**/
	public TObjectProperty itemsProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  This controls whether a menu button is available when the user clicks in a designated space within the TableView, within which is a radio menu item for each TableColumn in this table. This menu allows for the user to show and hide all TableColumns easily
	* </pre>
	**/
	public TBooleanProperty tableMenuButtonVisibleProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  This is the function called when the user completes a column-resize operation. The two most common policies are available as static functions in the TableView class: UNCONSTRAINED_RESIZE_POLICY and CONSTRAINED_RESIZE_POLICY
	* </pre>
	**/
	public TObjectProperty columnResizePolicyProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  A function which produces a TableRow. The system is responsible for reusing TableRows. Return from this function a TableRow which might be usable for representing a single row in a TableView. Note that a TableRow is not a TableCell. A TableRow is simply a container for a TableCell, and in most circumstances it is more likely that you'll want to create custom TableCells, rather than TableRows. The primary use case for creating custom TableRow instances would most probably be to introduce some form of column spanning support. You can create custom TableCell instances per column by assigning the appropriate function to the cellFactory property in the TableColumn class
	* </pre>
	**/
	public TObjectProperty rowFactoryProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  This Node is shown to the user when the table has no content to show. This may be the case because the table model has no data in the first place, that a filter has been applied to the table model, resulting in there being nothing to show the user, or that there are no currently visible columns
	* </pre>
	**/
	public TObjectProperty placeholderProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  The SelectionModel provides the API through which it is possible to select single or multiple items within a TableView, as well as inspect which items have been selected by the user. Note that it has a generic type that must match the type of the TableView itself
	* </pre>
	**/
	public TObjectProperty selectionModelProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  Represents the currently-installed TableView.TableViewFocusModel for this TableView. Under almost all circumstances leaving this as the default focus model will suffice
	* </pre>
	**/
	public TObjectProperty focusModelProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  Specifies whether this TableView is editable - only if the TableView, the TableColumn (if applicable) and the TableCells within it are both editable will a TableCell be able to go into their editing state
	* </pre>
	**/
	public TBooleanProperty editableProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link TableView} Class
	* 
	*  Represents the current cell being edited, or null if there is no cell being edited
	* </pre>
	**/
	public TReadOnlyObjectProperty editingCellProperty() default @TReadOnlyObjectProperty(parse = false);

}
