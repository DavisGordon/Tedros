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

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TTabPaneParser;
import com.tedros.fxapi.annotation.property.TBooleanProperty;
import com.tedros.fxapi.annotation.property.TDoubleProperty;
import com.tedros.fxapi.annotation.property.TObjectProperty;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.TTabPaneBuilder;
import com.tedros.fxapi.domain.TDefaultValues;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Region;

/**
 * <pre>
 * A  control that allows switching between a group of Tabs. Only one tab is visible at a time. 
 * Tabs are added to the TabPane by using the tabs().
 * Tabs in a TabPane can be positioned at any of the four sides by specifying the Side.
 * A TabPane has two modes floating or recessed. Applying the styleclass STYLE_CLASS_FLOATING will 
 * change the TabPane mode to floating.
 * 
 * The tabs width and height can be set to a specific size by setting the min and max for height 
 * and width. TabPane default width will be determined by the largest content width in the TabPane. 
 * This is the same for the height. If a different size is desired the width and height of the TabPane 
 * can be overridden by setting the min, pref and max size.
 * 
 * When the number of tabs do not fit the TabPane a menu button will appear on the right. The menu button 
 * is used to select the tabs that are currently not visible.
 * 
 * Example:
 * 
 * <i>@</i><strong>TTabPane</strong>(tabs = {
 * <i>@</i>TTab(text="#{label.documents}", closable=false,
 * 		 content = <i>@</i>TContent(detailForm = <i>@</i>TDetailForm(fields= {"<strong style="color:red;">documents</strong>"}))),
 *                          
 * <i>@</i>TTab(text="#{label.contacts}", closable=false, 
 *       content = <i>@</i>TContent(detailForm = <i>@</i>TDetailForm(fields= {"<strong style="color:green;">contacts</strong>"}))))
 *  <i>@</i><b>TDetailListField</b>(entityModelViewClass = DocumentModelView.class, entityClass = Document.class)
 *  <i>@</i><b>TModelViewCollectionType</b>(entityClass=Document.class, modelViewClass=DocumentModelView.class)
 *  private <b>ITObservableList</b>&lt;DocumentModelView&gt; <strong style="color:red;">documents</strong>;
 *  
 *  <i>@</i><b>TDetailListField</b>(entityModelViewClass = ContactModelView.class, entityClass = Contact.class)
 *  <i>@</i><b>TModelViewCollectionType</b>(entityClass=Contact.class, modelViewClass=ContactModelView.class)
 *  private <b>ITObservableList</b>&lt;ContactModelView&gt; <strong style="color:green;">contacts</strong>;
 * 
 * </pre>
 * 
 * @see {@link TDetailListField}, {@link TTab}, {@link TModelViewCollectionType}
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TTabPane  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TTabPaneBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TTabPaneBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TTabPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TTabPaneParser.class};
	
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
	 * The {@link Control} settings.
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);
	
	/**
	 * <pre>
	 * The {@link TabPane} Class.
	 * 
	 * The tabs to display in this TabPane.
	 * </pre>
	 * */
	public TTab[] tabs();
	
	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  Sets the model used for tab selection. 
	*  By changing the model you can alter how the tabs 
	*  are selected and which tabs are first or last.
	* </pre>
	**/
	public TSingleSelectionModel selectionModel() default @TSingleSelectionModel();

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The position to place the tabs in this TabPane. 
	*  Whenever this changes the TabPane will immediately 
	*  update the location of the tabs to reflect this.
	* </pre>
	**/
	public Side side() default Side.TOP;

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  Specifies how the TabPane handles tab closing 
	*  from an end-users perspective. 
	*  
	*  The options are: 
	*  	
	*  TabClosingPolicy.UNAVAILABLE: 
	*  Tabs can not be closed by the user
	*   
	*  TabClosingPolicy.SELECTED_TAB: 
	*  Only the currently selected tab will have the 
	*  option to be closed, with a graphic next to the 
	*  tab text being shown. The graphic will disappear 
	*  when a tab is no longer selected.
	*   
	*  TabClosingPolicy.ALL_TABS: 
	*  All tabs will have the option to be closed. 
	*  Refer to the TabPane.TabClosingPolicy enumeration 
	*  for further details. The default closing policy is 
	*  TabClosingPolicy.SELECTED_TAB
	* </pre>
	**/
	public TabClosingPolicy tabClosingPolicy() default TabClosingPolicy.UNAVAILABLE;

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  Specifies whether the graphic inside a Tab is rotated 
	*  or not, such that it is always upright, or rotated in 
	*  the same way as the Tab text is. 
	*  
	*  By default rotateGraphic is set to false, to represent 
	*  the fact that the graphic isn't rotated, resulting in it 
	*  always appearing upright. If rotateGraphic is set to true, 
	*  the graphic will rotate such that it rotates with the tab text.
	* </pre>
	**/
	public boolean rotateGraphic() default false;

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The minimum width of the tabs in the TabPane. 
	*  This can be used to limit the length of text in tabs 
	*  to prevent truncation. Setting the min equal to the max will 
	*  fix the width of the tab. 
	*  
	*  By default the min equals to the max. 
	*  This value can also be set via CSS using -fx-tab-min-width
	* </pre>
	**/
	public double tabMinWidth() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  Sets the value of the property tabMaxWidth. 
	*  
	*  Property description: 
	*  
	*  The maximum width of the tabs in the TabPane.
	* </pre>
	**/
	public double tabMaxWidth() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The minimum height of the tabs in the TabPane. 
	*  This can be used to limit the height in tabs. 
	*  Setting the min equal to the max will fix the height of the tab. 
	*  
	*  By default the min equals to the max. 
	*  This value can also be set via CSS using -fx-tab-min-height
	* </pre>
	**/
	public double tabMinHeight() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  Sets the value of the property tabMaxHeight. 
	*  
	*  Property description: 
	*  
	*  The maximum height of the tabs in the TabPane.
	* </pre>
	**/
	public double tabMaxHeight() default TAnnotationDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION;
	
	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The selection model used for selecting tabs
	* </pre>
	**/
	public TObjectProperty selectionModelProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The position of the tabs in the TabPane
	* </pre>
	**/
	public TObjectProperty sideProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The closing policy for the tabs
	* </pre>
	**/
	public TObjectProperty tabClosingPolicyProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The rotatedGraphic state of the tabs in the TabPane
	* </pre>
	**/
	public TBooleanProperty rotateGraphicProperty() default @TBooleanProperty(parse = false);

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The minimum width of the tabs in the TabPane
	* </pre>
	**/
	public TDoubleProperty tabMinWidthProperty() default @TDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The maximum width of the tabs in the TabPane
	* </pre>
	**/
	public TDoubleProperty tabMaxWidthProperty() default @TDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The minimum height of the tab
	* </pre>
	**/
	public TDoubleProperty tabMinHeightProperty() default @TDoubleProperty(parse = false);

	/**
	* <pre>
	* {@link TabPane} Class
	* 
	*  The maximum height of the tabs in the TabPane
	* </pre>
	**/
	public TDoubleProperty tabMaxHeightProperty() default @TDoubleProperty(parse = false);


	
}
