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
import org.tedros.fx.annotation.parser.TTableSubColumnParser;
import org.tedros.fx.annotation.property.TBooleanProperty;
import org.tedros.fx.annotation.property.TDoubleProperty;
import org.tedros.fx.annotation.property.TObjectProperty;
import org.tedros.fx.annotation.property.TReadOnlyDoubleProperty;
import org.tedros.fx.annotation.property.TReadOnlyObjectProperty;
import org.tedros.fx.annotation.property.TStringProperty;
import org.tedros.fx.builder.ITBuilder;
import org.tedros.fx.builder.ITComparatorBuilder;
import org.tedros.fx.builder.ITEventHandlerBuilder;
import org.tedros.fx.builder.ITNodeBuilder;
import org.tedros.fx.presenter.model.TModelView;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Config a sub {@link TableColumn}
 * 
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TTableSubColumn {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TTableSubColumnParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TTableSubColumnParser.class};
	
	/**
	* <pre>
	* Indicates the property name in the {@link TModelView} list type
	* to be bind with this column.
	* </pre>
	**/
	public String cellValue() default "";
	
	/**
	* <pre>
	* Sets the value of the property cellFactory.
	* </pre>
	**/
	public TCellFactory cellFactory() default @TCellFactory(parse = false);
	
	/**
	* <pre>
	* Sets the value of the property cellValueFactory.
	* </pre>
	**/
	public TCellValueFactory cellValueFactory() default @TCellValueFactory(parse = false);
	
	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property text. 
	*  
	*  Property description: 
	*  
	*  This is the text to show in the header for this column.
	* </pre>
	**/
	public String text();
	
	/**
	* <pre>
	* {@link TableView} Class
	*  
	*  The TableColumns that are part of this TableView. As the user reorders the TableView columns, 
	*  this list will be updated to reflect the current visual ordering.
	* </pre>
	**/
	public TTableNestedColumn[] columns()  default {};

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property visible. Property description: Toggling this will immediately toggle the visibility of this column, and all children columns.
	* </pre>
	**/
	public boolean visible() default true;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property contextMenu. 
	*  
	*  Property description: 
	*  
	*  This menu will be shown whenever the user right clicks within the header area of this TableColumn.
	* </pre>
	**/
	public Class<? extends ITBuilder> contextMenu() default ITBuilder.class;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the id of this TableColumn. This simple string identifier is useful for finding a specific TableColumn within the TableView. The default value is null. Since: 2.2
	* </pre>
	**/
	public String id() default "";;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  A string representation of the CSS style associated with this TableColumn. This is analogous to the "style" attribute of an HTML element. Note that, like the HTML style attribute, this variable contains style properties and values and not the selector portion of a style rule. Parsing this style might not be supported on some limited platforms. It is recommended to use a standalone CSS file instead. Since: 2.2
	* </pre>
	**/
	public String style() default "";

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the graphic to show in the TableColumn to allow the user to indicate graphically what is in the column. Since: 2.2
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITNodeBuilder> graphic() default ITNodeBuilder.class;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The node to use as the "sort arrow", shown to the user in situations where the TableColumn is part of the sort order. 
	*  It may be the only item in the sort order, or it may be a secondary, tertiary, or latter sort item, and the node 
	*  should reflect this visually. 
	*  
	*  This is only used in the case of the TableColumn being in the sort order. 
	*  
	*  If not specified, the TableColumn skin implementation is responsible for providing a default sort node. 
	*  
	*  Since: 2.2
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITNodeBuilder> sortNode() default ITNodeBuilder.class;;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property minWidth. Property description: The minimum width the TableColumn is permitted to be resized to.
	* </pre>
	**/
	public double minWidth() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property prefWidth. Property description: The preferred width of the TableColumn.
	* </pre>
	**/
	public double prefWidth() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property maxWidth. Property description: The maximum width the TableColumn is permitted to be resized to.
	* </pre>
	**/
	public double maxWidth() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property resizable. 
	*  
	*  Property description: 
	*  
	*  Used to indicate whether the width of this column can change. 
	*  It is up to the resizing policy to enforce this however.
	*  
	*  Default value: false
	*
	* </pre>
	**/
	public boolean resizable() default false;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property sortType. Property description: Used to state whether this column, 
	*  if it is part of the TableView.sortOrder ObservableList, should be sorted in ascending or descending order. 
	*  Simply toggling this property will result in the sort order changing in the TableView, assuming of course 
	*  that this column is in the sortOrder ObservableList to begin with.
	*  
	*  Default values: TableColumn.SortType.ASCENDING
	* </pre>
	**/
	public TableColumn.SortType sortType() default TableColumn.SortType.ASCENDING;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property sortable. 
	*  
	*  Property description: 
	*  
	*  A boolean property to toggle on and off the sortability of this column. 
	*  When this property is true, this column can be included in sort operations. 
	*  If this property is false, it will not be included in sort operations, even if it 
	*  is contained within TableView.sortOrder. If a TableColumn instance is contained 
	*  within the TableView sortOrder ObservableList, and its sortable property toggles state, 
	*  it will force the TableView to perform a sort, as it is likely the view will need updating.
	*  
	*  Default value: true
	* </pre>
	**/
	public boolean sortable() default true;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property comparator. Property description: Comparator function used when sorting this TableColumn. The two Objects given as arguments are the cell data for two individual cells in this column.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<ITComparatorBuilder> comparator() default ITComparatorBuilder.class;
	

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property editable. Property description: Specifies whether this TableColumn allows editing. This, unlike TableView, is true by default.
	* </pre>
	**/
	public boolean editable() default false;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property onEditStart. 
	*  
	*  Property description: 
	*  
	*  This event handler will be fired when the user successfully initiates editing.
	*  
	*  Example: 
	*  
	*  public class MyCellOnEditStartEvent implements ITEventHandlerBuilder&lt;TableColumn.CellEditEvent&lt;Person, String&gt;&gt; { 
	*  ...
	*  }
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEventHandlerBuilder> onEditStart() default ITEventHandlerBuilder.class;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property onEditCommit. Property description: This event handler will be fired when the user successfully commits their editing.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEventHandlerBuilder> onEditCommit() default ITEventHandlerBuilder.class;

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Sets the value of the property onEditCancel. Property description: This event handler will be fired when the user cancels editing a cell.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEventHandlerBuilder> onEditCancel() default ITEventHandlerBuilder.class;
	
	//****** PROPERTY
	
	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The TableView that this TableColumn belongs to
	* </pre>
	**/
	public TReadOnlyObjectProperty tableViewProperty() default @TReadOnlyObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  This is the text to show in the header for this column
	* </pre>
	**/
	public TStringProperty textProperty() default @TStringProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Toggling this will immediately toggle the visibility of this column, and all children columns
	* </pre>
	**/
	public TBooleanProperty visibleProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  This read-only property will always refer to the parent of this column, in the situation where nested columns are being used. To create a nested column is simply a matter of placing TableColumn instances inside the columns ObservableList of a TableColumn
	* </pre>
	**/
	public TReadOnlyObjectProperty parentColumnProperty() default @TReadOnlyObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  This menu will be shown whenever the user right clicks within the header area of this TableColumn
	* </pre>
	**/
	public TObjectProperty contextMenuProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The cell value factory needs to be set to specify how to populate all cells within a single TableColumn. A cell value factory is a Callback that provides a TableColumn.CellDataFeatures instance, and expects an ObservableValue to be returned. The returned ObservableValue instance will be observed internally to allow for immediate updates to the value to be reflected on screen. An example of how to set a cell value factory is: 
	 lastNameCol.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
	     public ObservableValue<String> call(CellDataFeatures<Person, String> p) {
	         // p.getValue() returns the Person instance for a particular TableView row
	         return p.getValue().lastNameProperty();
	     }
	  });
	 }
	 A common approach is to want to populate cells in a TableColumn using a single value from a Java bean. To support this common scenario, there is the PropertyValueFactory class. Refer to this class for more information on how to use it, but briefly here is how the above use case could be simplified using the PropertyValueFactory class: 
	 lastNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));
	 See Also: getCellValueFactory(), setCellValueFactory(Callback)
	* </pre>
	**/
	public TObjectProperty cellValueFactoryProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The cell factory for all cells in this column. The cell factory is responsible for rendering the data contained within each TableCell for a single table column. By default TableColumn uses the default cell factory, but this can be replaced with a custom implementation, for example to show data in a different way or to support editing. There is a lot of documentation on creating custom cell factories elsewhere (see Cell and TableView for example)
	* </pre>
	**/
	public TObjectProperty cellFactoryProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The id of this TableColumn. Since: 2.2 See Also: getId(), setId(String)
	* </pre>
	**/
	public TStringProperty idProperty() default @TStringProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The CSS style string associated to this TableColumn. Since: 2.2 See Also: getStyle(), setStyle(String)
	* </pre>
	**/
	public TStringProperty styleProperty() default @TStringProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The graphic in the TableColumn. Returns: The graphic in the TableColumn. Since: 2.2 See Also: getGraphic(), setGraphic(Node)
	* </pre>
	**/
	public TObjectProperty graphicProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The sort node is commonly seen represented as a triangle that rotates on screen to indicate whether the TableColumn is part of the sort order, and if so, what position in the sort order it is in. Since: 2.2 See Also: getSortNode(), setSortNode(Node)
	* </pre>
	**/
	public TObjectProperty sortNodeProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The width of this column. Modifying this will result in the column width adjusting visually. It is recommended to not bind this property to an external property, as that will result in the column width not being adjustable by the user through dragging the left and right borders of column headers
	* </pre>
	**/
	public TReadOnlyDoubleProperty widthProperty() default @TReadOnlyDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The minimum width the TableColumn is permitted to be resized to
	* </pre>
	**/
	public TDoubleProperty minWidthProperty() default @TDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The preferred width of the TableColumn
	* </pre>
	**/
	public TDoubleProperty prefWidthProperty() default @TDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  The maximum width the TableColumn is permitted to be resized to
	* </pre>
	**/
	public TDoubleProperty maxWidthProperty() default @TDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Used to indicate whether the width of this column can change. It is up to the resizing policy to enforce this however
	* </pre>
	**/
	public TBooleanProperty resizableProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Used to state whether this column, if it is part of the TableView.sortOrder ObservableList, should be sorted in ascending or descending order. Simply toggling this property will result in the sort order changing in the TableView, assuming of course that this column is in the sortOrder ObservableList to begin with
	* </pre>
	**/
	public TObjectProperty sortTypeProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  A boolean property to toggle on and off the sortability of this column. When this property is true, this column can be included in sort operations. If this property is false, it will not be included in sort operations, even if it is contained within TableView.sortOrder. If a TableColumn instance is contained within the TableView sortOrder ObservableList, and its sortable property toggles state, it will force the TableView to perform a sort, as it is likely the view will need updating
	* </pre>
	**/
	public TBooleanProperty sortableProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Comparator function used when sorting this TableColumn. The two Objects given as arguments are the cell data for two individual cells in this column
	* </pre>
	**/
	public TObjectProperty comparatorProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  Specifies whether this TableColumn allows editing. This, unlike TableView, is true by default
	* </pre>
	**/
	public TBooleanProperty editableProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  This event handler will be fired when the user successfully initiates editing
	* </pre>
	**/
	public TObjectProperty onEditStartProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  This event handler will be fired when the user successfully commits their editing
	* </pre>
	**/
	public TObjectProperty onEditCommitProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TableColumn} Class
	* 
	*  This event handler will be fired when the user cancels editing a cell
	* </pre>
	**/
	public TObjectProperty onEditCancelProperty() default @TObjectProperty(parse = false);



}
