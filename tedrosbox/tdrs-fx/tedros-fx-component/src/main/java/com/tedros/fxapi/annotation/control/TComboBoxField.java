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

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TComboBoxParser;
import com.tedros.fxapi.annotation.parser.TRequiredComboBoxParser;
import com.tedros.fxapi.annotation.property.TIntegerProperty;
import com.tedros.fxapi.annotation.property.TObjectProperty;
import com.tedros.fxapi.annotation.property.TReadOnlyObjectProperty;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TComboBoxBase;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.NullCallbackListViewListCellBuilder;
import com.tedros.fxapi.builder.NullListCellBuilder;
import com.tedros.fxapi.builder.NullObservableListBuilder;
import com.tedros.fxapi.builder.TComboBoxFieldBuilder;
import com.tedros.fxapi.control.TRequiredCheckBox;
import com.tedros.fxapi.domain.TDefaultValues;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.process.TOptionsProcess;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.TComboBoxField} component.
 * 
 * The annotatin for the implementation of the ComboBoxBase abstract class for the most common form of ComboBox, 
 * where a popup list is shown to users providing them with a choice that they may select from. 
 * For more information around the general concepts and API of ComboBox, refer to the ComboBoxBase 
 * class documentation.
 * 
 * On top of ComboBoxBase, the ComboBox class introduces additional API. Most importantly, it adds 
 * an items property that works in much the same way as the ListView items property. In other words, 
 * it is the content of the items list that is displayed to users when they click on the ComboBox button.
 * 
 * By default, when the popup list is showing, the maximum number of rows visible is 10, but this can 
 * be changed by modifying the visibleRowCount property. If the number of items in the ComboBox is less 
 * than the value of visibleRowCount, then the items size will be used instead so that the popup list is 
 * not exceedingly long.
 * 
 * As with ListView, it is possible to modify the selection model that is used, although this is likely 
 * to be rarely changed. This is because the ComboBox enforces the need for a SingleSelectionModel instance, 
 * and it is not likely that there is much need for alternate implementations. Nonetheless, the option is 
 * there should use cases be found for switching the selection model.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TComboBoxField  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 * Default value: {@link TComboBoxFieldBuilder}
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITControlBuilder> builder() default TComboBoxFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation.
	 * 
	 * Default value: {TRequiredComboBoxParser.class, TComboBoxParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TRequiredComboBoxParser.class, TComboBoxParser.class};
	
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
	
	/**
	 * <pre>
	 * Set the value of the firstItemText property.
	 * 
	 * Property description:
	 * 
	 * The text to show on the first item.
	 * 
	 * Default value: Empty string.
	 * </pre>
	 * */
	public String firstItemTex() default "";

	/**
	 * <pre>
	 * The option list process
	 * </pre>
	 * */
	public TOptionsList optionsList() default @TOptionsList(entityClass = ITEntity.class, 
			optionModelViewClass = TModelView.class, 
			optionsProcessClass = TOptionsProcess.class);
	
	
	// 	PROPERTYS
	
	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  The list of items to show within the ComboBox popup
	* </pre>
	**/
	public TObjectProperty itemsProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  Converts the user-typed input (when the ComboBox is editable) to an object of type T, such that the input may be retrieved via the value property
	* </pre>
	**/
	public TObjectProperty converterProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  Providing a custom cell factory allows for complete customization of the rendering of items in the ComboBox. Refer to the Cell javadoc for more information on cell factories
	* </pre>
	**/
	public TObjectProperty cellFactoryProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  The button cell is used to render what is shown in the ComboBox 'button' area. If a cell is set here, 
	*  it does not change the rendering of the ComboBox popup list - that rendering is controlled via the cell 
	*  factory 
	* </pre>
	**/
	public TObjectProperty buttonCellProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  The selection model for the ComboBox. A ComboBox only supports single selection
	* </pre>
	**/
	public TObjectProperty selectionModelProperty() default @TObjectProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  The maximum number of rows to be visible in the ComboBox popup when it is showing. 
	*  By default this value is 10, but this can be changed to increase or decrease the height of the popup
	* </pre>
	**/
	public TIntegerProperty visibleRowCountProperty() default @TIntegerProperty(parse = false);

	/**
	* <pre>
	* {@link ComboBox} Class
	* 
	*  The editor for the ComboBox. The editor is null if the ComboBox is not editable
	* </pre>
	**/
	public TReadOnlyObjectProperty editorProperty() default @TReadOnlyObjectProperty(parse = false);
}
