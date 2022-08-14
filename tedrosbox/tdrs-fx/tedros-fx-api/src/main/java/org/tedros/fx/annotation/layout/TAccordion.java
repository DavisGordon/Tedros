/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TAccordionParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.TAccordionBuilder;
import org.tedros.fx.builder.TVBoxBuilder;
import org.tedros.fx.domain.TViewMode;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Control;
import javafx.scene.layout.Region;

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
 *          <i>@</i>TRadioButton(userData="A", text="The Java Programming Language"),
 *          <i>@</i>TRadioButton(userData="B", text="The Java Language Specification")
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
public @interface TAccordion  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITLayoutBuilder} for this component.
	 * 
	 *  Default value: {@link TVBoxBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITLayoutBuilder<Accordion>> builder() default TAccordionBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TAccordionParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TAccordionParser.class};
	
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
	public TControl control() default @TControl(parse = false);
	

	/**
	 * <pre>
	 * The {@link Region} settings.
	 * </pre>
	 * */
	public TRegion region() default @TRegion(parse = false);
	
	/**
	* <pre>
	* {@link Accordion} Class
	* 
	*  The expanded TitledPane that is currently visible. While it is technically possible to 
	*  set the expanded pane to a value that is not in getPanes(), doing so will be treated by 
	*  the skin as if expandedPane is null. If a pane is set as the expanded pane, and is 
	*  subsequently removed from getPanes(), then expanded pane will be set to null, if possible. 
	*  (This will not be possible if you have manually bound the expanded pane to some value, for example).
	* </pre>
	**/
	public String expandedPane() default "";
	
	/**
	 * @see TTitledPane
	 * */
	public TTitledPane[] panes();
	
	/**
	* <pre>
	* Specifies the view mode to use this compent.
	* 
	* Set to TMode.READER will build this component only when the user set the view to Reader mode.
	* 
	* Default value:
	* {TMode.EDIT, TMode.READER}
	* </pre>
	**/
	public TViewMode[] mode() default {TViewMode.EDIT, TViewMode.READER}; 
	
	
}
