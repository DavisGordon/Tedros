package org.tedros.fx.annotation.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.annotation.parser.TPaneParser;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.layout.TRegion;

import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * <pre>
 * Base class for layout panes which need to expose the children list as public so that users of the subclass can freely add/remove children.
 * 
 * To add a Region or a Separator node as a child use the constants:
 * TPane.REGION, TPane.HORIZONTAL_SEPARATOR or TPane.VERTICAL_SEPARATOR
 * Example:
 * </pre>
 * <pre>
 * <i>@</i>TLabel(text="#{tedros.login.user}")
 * <i>@</i>TTextField(required=true, node=<i>@</i>TNode(requestFocus=true, parse=true))
 * <i>@</i>THBox(pane=<i>@</i>TPane(children= {"user", "sep"+<b>TPane.VERTICAL_SEPARATOR</b>, "password", "reg"+<b>TPane.REGION</b>}),
 * 	hgrow=<i>@</i>THGrow(priority={<i>@</i>TPriority(field="user", priority=Priority.ALWAYS), 
 *  				<i>@</i>TPriority(field="password", priority=Priority.ALWAYS),
 * 				<i>@</i>TPriority(field="reg"+TPane.REGION, priority=Priority.ALWAYS)
 * 	}))
 * private SimpleStringProperty user;
 * 
 * <i>@</i>TLabel(text="#{tedros.login.password}")
 * <i>@</i>TPasswordField(required=true) 
 * private SimpleStringProperty password;
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD})
public @interface TPane {
	
	/***
	 * Key constant to be used at the children property to automatically create a {@link Region} node 
	 */
	public static final String	REGION = "_region_";
	/***
	 * Key constant to be used at the children property to automatically create a horizontal {@link Separator} node.
	 */
	public static final String	HORIZONTAL_SEPARATOR = "_h_separator_"; 
	/***
	 * Key constant to be used at the children property to automatically create a vertical {@link Separator} node.
	 */
	public static final String	VERTICAL_SEPARATOR = "_v_separator_";
	
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TPaneParser.class};
	
	/**
	 * <pre>
	 * The {@link Pane} settings.
	 * 
	 * Assign a list of children of this Parent.
	 * Example:
	 * 
	 * <i>@</i>TPane(children={"user", "sep"+<b>TPane.VERTICAL_SEPARATOR</b>, "password", "reg"+<b>TPane.REGION</b>})
	 * </pre>
	 * */
	public String[] children() default {};
	
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
}
