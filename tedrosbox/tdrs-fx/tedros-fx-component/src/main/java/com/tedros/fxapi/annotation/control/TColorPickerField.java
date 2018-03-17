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

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.util.Callback;
import javafx.util.StringConverter;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TRequiredColorPickerFieldParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TComboBoxBase;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.NullCallbackListViewListCellBuilder;
import com.tedros.fxapi.builder.NullListCellBuilder;
import com.tedros.fxapi.builder.NullObservableListBuilder;
import com.tedros.fxapi.builder.TColorPickerFieldBuilder;
import com.tedros.fxapi.control.TRequiredCheckBox;
import com.tedros.fxapi.domain.TDefaultValues;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.TColorPickerField} component.
 * 
 * ColorPicker control allows the user to select a color from either a standard 
 * palette of colors with a simple one click selection OR define their own custom color.
 * 
 * The value is the currently selected Color. An initial color can be set by calling 
 * setColor or via the constructor. If nothing is specified, a default initial color is used.
 * 
 * The ColorPicker control provides a color palette with a predefined set of colors. 
 * If the user does not want to choose from the predefined set, they can create a custom color 
 * by interacting with a custom color dialog. This dialog provides RGB, HSB and Web modes of 
 * interaction, to create new colors. It also lets the opacity of the color to be modified.
 * 
 * Once a new color is defined, users can choose whether they want to save it or just use it. 
 * If the new color is saved, this color will then appear in the custom colors area on the color 
 * palette. Also getCustomColors returns the list of saved custom colors.
 * 
 * The promptText is not supported and hence is a no-op. But it may be supported in the future.
 * </pre>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TColorPickerField  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TColorPickerFieldBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder> builder() default TColorPickerFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TRequiredColorPickerFieldParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TRequiredColorPickerFieldParser.class};
	
	/**
	 * <pre>
	 * The {@link ComboBoxBase} properties.
	 * </pre>
	 * */
	public TComboBoxBase comboBoxBase() default @TComboBoxBase(parse = false);
	
	/**
	 * <pre>
	 * The {@link Node} properties.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} properties.
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);
	
	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  Sets the value of the property items. 
	*  
	*  Property description: 
	*  
	*  The list of items to show within the ComboBox popup.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITGenericBuilder<ObservableList>> items() default NullObservableListBuilder.class;

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  Sets the value of the property converter. 
	*  
	*  Property description: 
	*  
	*  Converts the user-typed input (when the ComboBox is editable) to an object 
	*  of type T, such that the input may be retrieved via the value property.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends StringConverter> converter() default StringConverter.class; 

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  Sets the value of the property cellFactory. 
	*  
	*  Property description: 
	*  
	*  Providing a custom cell factory allows for complete customization of the 
	*  rendering of items in the ComboBox. Refer to the Cell javadoc for more 
	*  information on cell factories.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITGenericBuilder<Callback<ListView,ListCell>>> cellFactory() default NullCallbackListViewListCellBuilder.class;

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  Sets the value of the property buttonCell. 
	*  
	*  Property description: 
	*  
	*  The button cell is used to render what is shown in the ComboBox 'button' area. 
	*  If a cell is set here, it does not change the rendering of the ComboBox 
	*  popup list - that rendering is controlled via the cell factory API.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends ITGenericBuilder<ListCell>> buttonCell() default NullListCellBuilder.class;

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  Sets the value of the property selectionModel. 
	*  
	*  Property description: 
	*  
	*  The selection model for the ComboBox. 
	*  A ComboBox only supports single selection.
	* </pre>
	**/
	@SuppressWarnings("rawtypes")
	public Class<? extends SingleSelectionModel> selectionModel() default SingleSelectionModel.class;

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  Sets the value of the property visibleRowCount. 
	*  
	*  Property description: 
	*  
	*  The maximum number of rows to be visible in the ComboBox popup when 
	*  it is showing. By default this value is 10, but this can be changed 
	*  to increase or decrease the height of the popup.
	* </pre>
	**/
	public int visibleRowCount() default TAnnotationDefaultValue.DEFAULT_INT_VALUE_IDENTIFICATION;
	
	/**
	 * <pre>
	 * {@link TRequiredCheckBox} Class
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
}
