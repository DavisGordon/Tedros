package org.tedros.fx.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.fx.annotation.parser.TListViewParser;
import org.tedros.fx.annotation.parser.TRequiredListViewParser;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.ITEventHandlerBuilder;
import org.tedros.fx.builder.ITGenericBuilder;
import org.tedros.fx.builder.ITNodeBuilder;
import org.tedros.fx.builder.NullObservableListBuilder;
import org.tedros.fx.builder.TBaseEventHandlerBuilder;
import org.tedros.fx.builder.TListViewFieldBuilder;
import org.tedros.fx.control.TListView;
import org.tedros.server.entity.ITEntity;

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Build a {@link TListView} control.
 * 
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TListViewField {

	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link TListViewFieldBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder> builder() default TListViewFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TRequiredListViewParser.class, TListViewParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TAnnotationParser>[] parser() default {TRequiredListViewParser.class, TListViewParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} properties.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Region} properties.
	 * </pre>
	 * */
	public TRegion region() default @TRegion(parse = false);

	/**
	 * <pre>
	 * The {@link Control} properties.
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(parse = false);
	

	/**
	* <pre>
	* {@link TListViewField} Class
	* 
	*  Sets the value of the property items. 
	*  
	*  Property description: 
	*  
	*  The list of items to show within the listview.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITGenericBuilder<ObservableList>> items() default NullObservableListBuilder.class;
	
	/**
	 * <pre>
	 * {@link TListViewField} Class
	 * 
	 * Sets the value of the property required.
	 * 
	 * Property description:
	 * 
	 * Determines with this control will be required.
	 * 
	 * Default value: false.
	 * </pre>
	 * */
	public boolean required() default false;
	
	/**
	 * <pre>
	 * {@link TPickListField} Class
	 * 
	 * Sets the value of the property process.
	 * 
	 * Property description:
	 * 
	 * Defines the process to fill the options at source list
	 * 
	 * Default value: Empty string.
	 * </pre>
	 * */
	public TProcess process() default @TProcess(
			query=@TQuery(entity = ITEntity.class),
			service = "");

	/**
	* <pre>
	* {@link ListView} Class
	* 
	*  Sets the value of the property placeholder. 
	*  Property description: This Node is shown to the user when the listview has no content to show. 
	*  This may be the case because the table model has no data in the first place or that a filter 
	*  has been applied to the list model, resulting in there being nothing to show the user.. 
	*  Since: JavaFX 8.0
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITNodeBuilder> placeholder() default ITNodeBuilder.class;

	/**
	* <pre>
	* {@link ListView} Class
	* 
	*  Sets the orientation of the ListView, which dictates whether it scrolls vertically or horizontally.
	*  Default value: Orientation.VERTICAL
	* </pre>
	**/
	public Orientation orientation() default Orientation.VERTICAL;

	/**
	* <pre>
	* {@link ListView} Class
	* 
	*  Sets a new cell factory to use in the ListView. 
	*  This forces all old ListCell's to be thrown away, 
	*  and new ListCell's created with the new cell factory.
	* </pre>
	**/
	//public TCellFactory cellFactory() default @TCellFactory(parse = false);

	/**
	* <pre>
	* {@link ListView} Class
	* 
	*  Sets the new fixed cell size for this control. 
	*  Any value greater than zero will enable fixed cell size mode,
	*  whereas a zero or negative value (or Region.USE_COMPUTED_SIZE) 
	*  will be used to disabled fixed cell size mode. 
	*  Parameters: value - The new fixed cell size value, or a value less 
	*  than or equal to zero (or Region.USE_COMPUTED_SIZE) to disable. 
	*  Since: JavaFX 8.0
	* </pre>
	**/
	public double fixedCellSize() default 0;

	/**
	* <pre>
	* {@link ListView} Class
	* 
	*  Sets the value of the property editable. 
	*  Property description: Specifies whether this ListView is editable 
	*  - only if the ListView and the ListCells within it are both editable 
	*  will a ListCell be able to go into their editing state.
	* </pre>
	**/
	public boolean editable() default false;

	/**
	* <pre>
	* {@link ListView} Class
	* 
	*  Sets the EventHandler that will be called when the user begins an edit. 
	*  This is a convenience method - the same result can be achieved by calling 
	*  addEventHandler(ListView.EDIT_START_EVENT, eventHandler).
	*  
	*  Event Type: ListView.EditEvent
	* </pre>
	* @see TBaseEventHandlerBuilder
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEventHandlerBuilder> onEditStart() default ITEventHandlerBuilder.class;

	/**
	* <pre>
	* {@link ListView} Class
	* 
	*  Sets the EventHandler that will be called when the user has completed their editing. 
	*  This is called as part of the ListCell.commitEdit(java.lang.Object) method. 
	*  This is a convenience method - the same result can be achieved by calling 
	*  addEventHandler(ListView.EDIT_START_EVENT, eventHandler).
	*  
	*  Event Type: ListView.EditEvent
	* </pre>
	* @see TBaseEventHandlerBuilder
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEventHandlerBuilder> onEditCommit() default ITEventHandlerBuilder.class;

	/**
	* <pre>
	* {@link ListView} Class
	* 
	*  Sets the EventHandler that will be called when the user cancels an edit.
	*  
	*  Event Type: ListView.EditEvent
	* </pre>
	* @see TBaseEventHandlerBuilder
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEventHandlerBuilder> onEditCancel() default ITEventHandlerBuilder.class;

	/**
	* <pre>
	* {@link ListView} Class
	* 
	*  Sets the value of the property onScrollTo. 
	*  Property description: Called when there's a request to scroll an index into 
	*  view using scrollTo(int) or scrollTo(Object) Since: JavaFX 8.0
	*  
	*  Event Type: ScrollToEvent&ltInteger&gt
	* </pre>
	* @see TBaseEventHandlerBuilder
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEventHandlerBuilder> onScrollTo() default ITEventHandlerBuilder.class;


}
