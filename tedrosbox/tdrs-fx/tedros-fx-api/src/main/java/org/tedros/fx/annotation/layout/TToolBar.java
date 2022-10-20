package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TToolBarParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.TToolBarBuilder;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Region;


/**
 * <pre>
 * Build a {@link ToolBar} layout.
 * 
 * Example:
 * 
 * <i>@</i><strong>TToolBar</strong>(items={"<strong style="color:red;">clearBtnField</strong>", "<strong style="color:blue;">integerField</strong>", "<strong style="color:green;">bigIntegerField</strong>"})
 *  <i>@</i>TButtonField(labeled = <i>@</i>TLabeled(text="Clear", parse = true),
 * 	buttonBase=<i>@</i>TButtonBase(onAction=ClearEventBuilder.class))
 *  private SimpleStringProperty <strong style="color:red;">clearBtnField;</strong>
 *		
 *  <i>@</i>TLabel(text="Number field", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TIntegerField(zeroValidation=TZeroValidation.MORE_THAN_ZERO, control=<i>@</i>TControl(tooltip="Max val: "+Integer.MAX_VALUE))
 *  private SimpleIntegerProperty <strong style="color:blue;">integerField</strong>;
 *		
 *  <i>@</i>TLabel(text="Big number field:", control=<i>@</i>TControl(prefWidth=500))
 *  <i>@</i>TBigIntegerField(zeroValidation=TZeroValidation.MORE_THAN_ZERO, control=<i>@</i>TControl(tooltip="Max val: infinito"))
 *  private SimpleObjectProperty&gt;BigInteger&lt; <strong style="color:green;">bigIntegerField</strong>;
 * 
 * 
 * Oracle documentation:
 * 
 * A ToolBar is a control which displays items 
 * horizontally or vertically. The most common items 
 * to place within a ToolBar are Buttons, ToggleButtons 
 * and Separators, but you are not restricted to just 
 * these, and can insert any Node into them. 
 * If there are too many items to fit in the ToolBar 
 * an overflow button will appear. The overflow button 
 * allows you to select items that are not currently 
 * visible in the toolbar.
 * ToolBar sets focusTraversable to false
 * </pre>
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
public @interface TToolBar {
	
	/**
	 *<pre>
	 * The builder of type {@link ITLayoutBuilder} for this component.
	 * 
	 *  Default value: {@link TToolBarBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITLayoutBuilder<org.tedros.fx.layout.TToolBar>> builder() default TToolBarBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TToolBarParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TToolBarParser.class};
	
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
	 * {@link ToolBar} Class
	 * 
	 * The items contained in the ToolBar. 
	 * Typical use case for a ToolBar suggest that 
	 * the most common items to place within it are
	 * Buttons, ToggleButtons, and Separators, but 
	 * you are not restricted to just these, and can 
	 * insert any Node. The items added must not be null.
	 * </pre>
	 * 
	 * */
	public String[] items();
	
	/**
	* <pre>
	* {@link ToolBar} Class
	* 
	*  Sets the value of the property orientation. 
	*  
	*  Property description: 
	*  The orientation of the ToolBar - this can either be 
	*  horizontal or vertical.
	*  
	*  Default value: Orientation.HORIZONTAL
	* </pre>
	**/
	public Orientation orientation() default Orientation.HORIZONTAL;
}
