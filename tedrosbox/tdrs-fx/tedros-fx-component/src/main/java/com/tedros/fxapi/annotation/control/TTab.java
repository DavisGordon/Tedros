/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.form.TDetailView;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TTabParser;
import com.tedros.fxapi.annotation.property.TBooleanProperty;
import com.tedros.fxapi.annotation.property.TObjectProperty;
import com.tedros.fxapi.annotation.property.TReadOnlyBooleanProperty;
import com.tedros.fxapi.annotation.property.TReadOnlyObjectProperty;
import com.tedros.fxapi.annotation.property.TStringProperty;
import com.tedros.fxapi.builder.ITEventHandlerBuilder;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.ITNodeBuilder;
import com.tedros.fxapi.builder.NullContextMenuBuilder;
import com.tedros.fxapi.builder.NullEventBuilder;
import com.tedros.fxapi.builder.NullNodeBuilder;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;

/**
 * <pre>
 * Config a {@link Tab}
 * 
 * Tabs are placed within a TabPane, where each tab represents a single 'page'.
 * Tabs can contain any Node such as UI controls or groups of nodes added to a layout container.
 * When the user clicks on a Tab in the TabPane the Tab content becomes visible to the user.
 * </pre>
 *
 * @author Davis Gordon
 * 
 * @see {@link TTabPane}, {@link TDetailView}, {@link TContent}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TTab  {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TTabParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TTabParser.class};
	
	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Sets the id of this tab. 
	*  
	*  This simple string identifier is useful for finding a specific Tab within the TabPane. 
	*  
	*  The default value is null.
	* </pre>
	**/
	public String id() default "";

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  A string representation of the CSS style associated with this tab. 
	*  This is analogous to the "style" attribute of an HTML element. 
	*  
	*  Note that, like the HTML style attribute, this variable contains 
	*  style properties and values and not the selector portion of a style rule. 
	*  Parsing this style might not be supported on some limited platforms. 
	*  It is recommended to use a standalone CSS file instead.
	* </pre>
	**/
	public String style() default "";

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Sets the text to show in the tab to allow the user to differentiate 
	*  between the function of each tab. The text is always visible
	* </pre>
	**/
	public String text();

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Sets the graphic to show in the tab to allow the user to 
	*  differentiate between the function of each tab. 
	*  
	*  By default the graphic does not rotate based on the 
	*  TabPane.tabPosition value, but it can be set to rotate 
	*  by setting TabPane.rotateGraphic to true.
	* </pre>
	**/
	public Class<? extends ITNodeBuilder<Node>> graphic() default NullNodeBuilder.class;

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The content to show within the main TabPane area. 
	*  The content can be any Node such as UI controls or 
	*  groups of nodes added to a layout container.
	* </pre>
	**/
	public TContent content();

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Specifies the context menu to show when the user right-clicks on the tab.
	* </pre>
	**/
	public Class<? extends ITGenericBuilder<ContextMenu>> contextMenu() default NullContextMenuBuilder.class;

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Sets true if the tab is closable. 
	*  
	*  If this is set to false, then regardless of the TabClosingPolicy, 
	*  it will not be possible for the user to close this tab. 
	*  Therefore, when this property is false, no 'close' button will 
	*  be shown on the tab. 
	*  
	*  The default is true.
	* </pre>
	**/
	public boolean closable() default true;

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Defines a function to be called when a selection changed has occurred on the tab.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<Event>> onSelectionChanged() default NullEventBuilder.class;

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Defines a function to be called when the tab is closed.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<Event>> onClosed() default NullEventBuilder.class;

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Specifies the tooltip to show when the user hovers over the tab.
	* </pre>
	**/
	public String tooltip() default "";

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Sets the disabled state of this tab. 
	*  
	*  Default value: false
	* </pre>
	**/
	public boolean disable() default false;

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Convenience method for setting a single Object property 
	*  that can be retrieved at a later date.  
	* </pre>
	**//*
	public Class<? extends Object> userData();*/

	
	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The id of this tab
	* </pre>
	**/
	public TStringProperty idProperty() default @TStringProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The CSS style string associated to this tab
	* </pre>
	**/
	public TStringProperty styleProperty() default @TStringProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The currently selected tab
	* </pre>
	**/
	public TReadOnlyBooleanProperty selectedProperty() default @TReadOnlyBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The TabPane that contains this tab
	* </pre>
	**/
	public TReadOnlyObjectProperty tabPaneProperty() default @TReadOnlyObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The text shown in the tab
	* </pre>
	**/
	public TStringProperty textProperty() default @TStringProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The graphic in the tab. Returns: The graphic in the tab
	* </pre>
	**/
	public TObjectProperty graphicProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The content associated with the tab
	* </pre>
	**/
	public TObjectProperty contentProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The context menu associated with the tab
	* </pre>
	**/
	public TObjectProperty contextMenuProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The closable state for this tab
	* </pre>
	**/
	public TBooleanProperty closableProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The event handler that is associated with a selection on the tab
	* </pre>
	**/
	public TObjectProperty onSelectionChangedProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The event handler that is associated with the tab when the tab is closed
	* </pre>
	**/
	public TObjectProperty onClosedProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  The tooltip associated with this tab
	* </pre>
	**/
	public TObjectProperty tooltipProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Sets the disabled state of this tab. A disable tab is no longer interactive or traversable, but the contents remain interactive. A disable tab can be selected using TabPane.getSelectionModel(). Since: 2.2 
	* </pre>
	**/
	public TBooleanProperty disableProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link Tab} Class
	* 
	*  Indicates whether or not this Tab is disabled. A Tab will become disabled if disable is set to true on either itself or if the TabPane is disabled. Since: 2.2 
	* </pre>
	**/
	public TReadOnlyBooleanProperty disabledProperty() default @TReadOnlyBooleanProperty(parse = false);



	
}
