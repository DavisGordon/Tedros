/**
 * 
 */
package com.tedros.fxapi.annotation.scene.web;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TWebViewParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.builder.ITControlBuilder;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.TWebViewBuilder;

import javafx.scene.Node;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebView;


/**
 * <pre>
 * Build a WebView 
 * 
 * Example: 
 * <b>Load an url from web:</b>
 * <i>@</i>TWebView(engine=<i>@</i>TWebEngine(load="https://www.tedrosbox.com"))
 * private SimpleStringProperty webContent;
 * 
 * <b>Load a content from the file system:</b>
 * <i>@</i>TWebView(engine=<i>@</i>TWebEngine(loadContent="file:c://usr/index.html"))
 * private SimpleStringProperty localContent;
 * </pre>
 * @author Davis Gordon
 * 
 * @see {@link TWebEngine}
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface TWebView {
	
	/**
	 *<pre>
	 * The builder of type {@link ITControlBuilder} for this component.
	 * 
	 *  Default value: {@link TWebViewBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TWebViewBuilder.class;
	
	/**
	* <pre>
	* The parser class for this annotation
	* 
	* Default value: {TWebViewParser.class}
	* </pre>
	* */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TWebViewParser.class};
	
	/**
	 * <pre>
	 * The {@link Node} properties.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	
	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets current zoom factor applied to the whole page contents. 
	*  Parameters: value - zoom factor to be set 
	* </pre>
	**/
	public TWebEngine engine();
	
	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets current zoom factor applied to the whole page contents. 
	*  Parameters: value - zoom factor to be set 
	* </pre>
	**/
	public double zoom() default -1;

	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets the value of the property fontScale. 
	*  Property description: Specifies scale factor applied to font. 
	*  This setting affects text content but not images and fixed size elements. 
	*  Default value: 1.0
	* </pre>
	**/
	public double fontScale() default 1.0;

	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets minimum width.
	* </pre>
	**/
	public double minWidth() default -1;

	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets minimum height.
	* </pre>
	**/
	public double minHeight() default -1;

	
	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets preferred width.
	* </pre>
	**/
	public double prefWidth() default -1;

	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets preferred height.
	* </pre>
	**/
	public double prefHeight() default -1;

	
	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets maximum width.
	* </pre>
	**/
	public double maxWidth() default -1;

	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets maximum height.
	* </pre>
	**/
	public double maxHeight() default -1;

	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets the value of the property fontSmoothingType. 
	*  Property description: Specifies a requested font smoothing type : gray or LCD. 
	*  The width of the bounding box is defined by the widest row. 
	*  Note: LCD mode doesn't apply in numerous cases, such as various compositing modes, 
	*  where effects are applied and very large glyphs. 
	*  Default value: FontSmoothingType.LCD Since: JavaFX 2.2
	* </pre>
	**/
	public FontSmoothingType fontSmoothingType() default FontSmoothingType.LCD;

	/**
	* <pre>
	* {@link WebView} Class
	* 
	*  Sets the value of the property contextMenuEnabled. 
	*  Property description: Specifies whether context menu is enabled. 
	*  Default value: true Since: JavaFX 2.2
	* </pre>
	**/
	public boolean contextMenuEnabled() default true;



}
