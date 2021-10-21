/**
 * 
 */
package com.tedros.fxapi.annotation.scene.web;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.tedros.fxapi.builder.ITEventHandlerBuilder;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.NullBooleanWebEventBuilder;
import com.tedros.fxapi.builder.NullFileBuilder;
import com.tedros.fxapi.builder.NullPopupFeaturesWebEngineCallBackBuilder;
import com.tedros.fxapi.builder.NullPromptDataStringCallBackBuilder;
import com.tedros.fxapi.builder.NullRectangle2DWebEventBuilder;
import com.tedros.fxapi.builder.NullStringBooleanCallBackBuilder;
import com.tedros.fxapi.builder.NullStringWebEventBuilder;
import com.tedros.fxapi.builder.NullWebErrorEventBuilder;
import com.tedros.fxapi.form.TComponentConfig;

import javafx.geometry.Rectangle2D;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.PromptData;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebEvent;
import javafx.util.Callback;

/**
 * Config the WebEngine
 * 
 * @author Davis Gordon
 *
 */
@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface TWebEngine {
	
	public Class<? extends TComponentConfig> componentConfig() default TComponentConfig.class;
	
	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Loads a Web page into this engine.
	* </pre> 
	**/
	public String load() default "";
	
	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	* Loads the given HTML content directly.
	* </pre> 
	**/
	public String loadContent() default "";
	
	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	* the content type.
	* </pre> 
	**/
	public String contentType() default "";

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the value of the property javaScriptEnabled. 
	*  Property description: Specifies whether JavaScript execution is enabled.
	*   Default value: true Since: JavaFX 2.2
	* </pre>
	**/
	public boolean javaScriptEnabled() default true;

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the value of the property userStyleSheetLocation. 
	*  Property description: Location of the user stylesheet as a string URL. 
	*  This should be a local URL, i.e. either 'data:', 'file:', or 'jar:'.
	*  Remote URLs are not allowed for security reasons. 
	*  Default value: null Since: JavaFX 2.2
	* </pre>
	**/
	public String userStyleSheetLocation() default "";

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the value of the property userDataDirectory. 
	*  Property description: Specifies the directory to be used 
	*  by this WebEngine to store local user data. If the value 
	*  of this property is not null, the WebEngine will attempt
	*  to store local user data in the respective directory. 
	*  If the value of this property is null, the WebEngine will 
	*  attempt to store local user data in an automatically selected 
	*  system-dependent user- and application-specific directory. 
	*  When a WebEngine is about to start loading a web page or 
	*  executing a script for the first time, it checks whether 
	*  it can actually use the directory specified by this property. 
	*  If the check fails for some reason, the WebEngine invokes the 
	*  WebEngine.onError event handler, if any, with a WebErrorEvent
	*  describing the reason. If the invoked event handler modifies 
	*  the userDataDirectory property, the WebEngine retries with the 
	*  new value as soon as the handler returns. If the handler does 
	*  not modify the userDataDirectory property (which is the default), 
	*  the WebEngine continues without local user data. Once the WebEngine
	*  has started loading a web page or executing a script, changes made 
	*  to this property have no effect on where the WebEngine stores or 
	*  will store local user data. Currently, the directory specified by 
	*  this property is used only to store the data that backs the 
	*  window.localStorage objects. In the future, more types of data can 
	*  be added. 
	*  Default value: null Since: JavaFX 8.0
	* </pre>
	**/
	public Class<? extends ITGenericBuilder<File>> userDataDirectory() default NullFileBuilder.class;

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the value of the property userAgent. 
	*  Property description: Specifies user agent ID string. 
	*  This string is the value of the User-Agent HTTP header. 
	*  Default value: system dependent Since: JavaFX 8.0
	* </pre>
	**/
	public String userAgent() default "";

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the JavaScript alert handler. 
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<WebEvent<String>>> onAlert() default NullStringWebEventBuilder.class;

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the JavaScript status handler.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<WebEvent<String>>> onStatusChanged() default NullStringWebEventBuilder.class;

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the JavaScript window resize handler. 
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<WebEvent<Rectangle2D>>> onResized() default NullRectangle2DWebEventBuilder.class;

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the JavaScript window visibility handler.
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<WebEvent<Boolean>>> onVisibilityChanged() default NullBooleanWebEventBuilder.class;

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the JavaScript popup handler. 
	* </pre>
	**/
	public Class<? extends ITGenericBuilder<Callback<PopupFeatures,WebEngine>>> createPopupHandler() default NullPopupFeaturesWebEngineCallBackBuilder.class;

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the JavaScript confirm handler. 
	* </pre>
	**/
	public Class<? extends ITGenericBuilder<Callback<String,Boolean>>> confirmHandler() default NullStringBooleanCallBackBuilder.class;

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the JavaScript prompt handler.
	* </pre>
	**/
	public Class<? extends ITGenericBuilder<Callback<PromptData,String>>> promptHandler() default NullPromptDataStringCallBackBuilder.class;

	/**
	* <pre>
	* {@link WebEngine} Class
	* 
	*  Sets the value of the property onError. 
	*  Property description: The event handler called when an error occurs. 
	*  Default value: null Since: JavaFX 8.0
	* </pre>
	**/
	public Class<? extends ITEventHandlerBuilder<WebErrorEvent>> onError() default NullWebErrorEventBuilder.class;


	
}
