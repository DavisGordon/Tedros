/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/03/2014
 */
package org.tedros.fx.annotation.reader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.core.model.ITModelView;
import org.tedros.fx.builder.ITReaderHtmlBuilder;
import org.tedros.fx.builder.TPageReaderHtmlBuilder;
import org.tedros.fx.domain.TStyleParameter;
import org.tedros.server.entity.ITEntity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * <pre>
 * Mark the field as a Reader type.
 * 
 * A reader type field is builded only when the user set the view to reader mode.
 * 
 * Types supported:
 * 
 * {@link SimpleStringProperty}, {@link SimpleBooleanProperty}, {@link SimpleLongProperty}, 
 * {@link SimpleDoubleProperty}, {@link SimpleFloatProperty}, {@link SimpleIntegerProperty}, 
 *  and {@link SimpleObjectProperty} 
 * 
 * Note: 
 * 
 * A field of {@link ITEntity} type in an entity class can be configured as 
 * SimpleObjectProperty&lt;ITEntity&gt; at the {@link ITModelView} representation. 
 * 
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TFormReaderHtml {
	
	
	/**
	 *<pre>
	 * The builder of type {@link ITReaderHtmlBuilder} for this component.
	 * 
	 *  Default value: TPageReaderHtmlBuilder.class
	 *</pre> 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITReaderHtmlBuilder> builder() default TPageReaderHtmlBuilder.class;
	
	public String title() default "";
	
	/**
	* <pre>
	*  Sets a css string to be used at the tag defined in htmlTemplateForControlValue(). 
	*  
	*  Default value: margin:2px;
	* </pre>
	**/
	public String bodyTemplateStyle() default "background-color: "+TStyleParameter.READER_BACKGROUND_COLOR+";";
	
	public String bodyTemplateAttribute() default "";
	
	public String style() default "body { color: "+TStyleParameter.READER_TEXT_COLOR+"; font-size: "+TStyleParameter.READER_TEXT_SIZE+"em; }";
	
	public String javascriptOnTop() default "";
	
	public String javascriptOnBottom() default "";
		
}
