/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 23/03/2014
 */
package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.parser.ITListFileFieldBuilder;
import com.tedros.fxapi.builder.TListFileFieldBuilder;
import com.tedros.fxapi.control.TListFileModelField;
import com.tedros.fxapi.control.action.TActionEvent;
import com.tedros.fxapi.domain.TFileExtension;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;

/**
 * <pre>
 * Build a {@link TListFileModelField} component.
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TListFileField {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITListFileFieldBuilder> parser() default TListFileFieldBuilder.class;

	public Pos alignment() default Pos.CENTER_LEFT;
	public boolean disable() default false;
	public boolean required() default false;
	public int width() default -1; 
	public int height() default -1;
	public boolean visible() default true;
	public String style() default "";
	public String[] styleClass() default {};
	
	public TFileExtension extensions() default TFileExtension.ALL_FILES;
	public String[] moreExtensions() default {};
	public double minFileSize() default -1;
	public double maxFileSize() default -1;
	public double minImageWidth() default -1;
	public double maxImageWidth() default -1;
	public double minImageHeight() default -1;
	public double maxImageHeight() default -1;
	public boolean preLoadBytesFile() default false;
	
	public boolean showImage() default true;
	public boolean showFilePath() default false;
	public boolean showFileTypeIcon() default true;
	public boolean showFileName() default true;
	public boolean showFileSize() default false;
	public Orientation orientation() default Orientation.HORIZONTAL;
	public boolean scroll() default false;
	
	public Class<? extends TActionEvent> imageClickAction() default TActionEvent.class;
	
	public String componentId() default "";
	
}
