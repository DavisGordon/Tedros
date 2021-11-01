/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TStackPaneParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TInsets;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.builder.ITLayoutBuilder;
import com.tedros.fxapi.builder.TSliderMenuBuilder;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * <pre>
 * Build a {@link Accordion}.
 * 
 * Example: 
 * 
 *  <i>@</i><strong>TAccordion</strong>(panes = {<i>@</i>TTitledPane(text = "Group Fields", fields={"<strong style="color:red">optionsField</strong>", "<strong style="color:blue">passField"</strong>, "<strong style="color:green">integerField</strong>"})})
 *  <i>@</i>TLabel(text="Choose a book:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TVerticalRadioGroup(spacing=5, required=true, alignment= Pos.CENTER_LEFT
 *          ,radioButtons={
 *          <i>@</i>TRadioButtonField(userData="A", text="The Java Programming Language"),
 *          <i>@</i>TRadioButtonField(userData="B", text="The Java Language Specification")
 *  })
 *  private SimpleStringProperty <strong style="color:red">optionsField</strong>;
 *  
 *  <i>@</i>TLabel(text="Password:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TPasswordField(required=true, maxLength=6)
 *  private SimpleStringProperty <strong style="color:blue">passField</strong>;
 *		
 *  <i>@</i>TLabel(text="Number field", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TIntegerField(zeroValidation=TZeroValidation.MORE_THAN_ZERO, control=<i>@</i>TControl(tooltip="Max value: "+Integer.MAX_VALUE))
 *  private SimpleIntegerProperty <strong style="color:green">integerField</strong>;
 * 
 * Oracle documentation:
 * 
 * An accordion is a group of TitlePanes. Only one TitledPane can be opened at a time.
 *
 * The TitledPane content in an accordion can be any Node such as UI controls or groups of nodes added to a layout container.
 * 
 * It is not recommended to set the MinHeight, PrefHeight, or MaxHeight for this control. 
 * Unexpected behavior will occur because the Accordion's height changes when a TitledPane is opened or closed.
 * 
 * Accordion sets focusTraversable to false.
 *</pre>
 * @author Davis Gordon
 * @see TTitledPane
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TSliderMenu  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITLayoutBuilder} for this component.
	 * 
	 *  Default value: {@link TSliderMenuBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITLayoutBuilder<com.tedros.fxapi.layout.TSliderMenu>> builder() default TSliderMenuBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TStackPaneParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TStackPaneParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} settings
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
	* {@link StackPane} Class
	* 
	*  Sets the margin for the child when contained by a stackpane. 
	*  If set, the stackpane will layout the child with the margin space around it.
	*  Default value: @TInsets(left=50)
	* </pre>
	**/
	public TInsets margin() default @TInsets;
	
	/**
	 * The field name with the components to set as content
	 * */
	public String content();
	
	/**
	 * The field name with the components to set as menu
	 * */
	public String menu();
	
	/**
	 * <pre>
	 * The left menu Width
	 * Default value: 280
	 * </pre>
	 * */
	public double menuWidth() default 280;
	
	/**
	 * <pre>
	 * Start with an expanded menu
	 * Default value: true
	 * </pre>
	 * */
	public boolean menuExpanded() default true;
	
	/**
	 * <pre>
	 * Set padding values to the content
	 * Default value: @TInsets(left=50)
	 * </pre>
	 * */
	//public TInsets contentPading() default @TInsets(left=50);
}
