package org.tedros.fx.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TEffectParser;
import org.tedros.fx.annotation.parser.engine.ITEffectParse;
import org.tedros.fx.effect.ITEffectBuilder;

import javafx.scene.effect.ColorInput;



/**
 * An effect that renders a rectangular region that is filled ("flooded") with the given Paint. This is equivalent to rendering a filled rectangle into an image and using an ImageInput effect, except that it is more convenient and potentially much more efficient.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TColorInput {
	
	
	public final class NullEffectBuilder implements ITEffectBuilder<ColorInput>{
		@Override
		public ColorInput build() {
			return null;
		}	
	}
	
	/**
	 * <pre>
	 * The parser class for this annotation.
	 * 
	 * Default value:
	 * org.tedros.fx.annotation.parser.TEffectParser.class
	 * </pre>
	 * */
	public Class<? extends ITEffectParse> parser() default TEffectParser.class;
	
	/**
	 * <pre>Custom class builder for this Effect, setting this will ignore all others properties.
	 * 
	 * Default value: null</pre>
	 * */
	public Class<? extends ITEffectBuilder<ColorInput>> builder() default NullEffectBuilder.class;
	
	/**
	 * <pre>Sets the value of the property input.
	 * 
	 * Property description:
	 * The input for this Effect. If set to null, or left unspecified, a graphical image of the Node to which the Effect is attached will be used as the input.
	 * Default value: null</pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEffectBuilder> input() default ITEffectBuilder.class;
	
	/**
	 * <pre>Sets the value of the property paint.
	 * 
	 * Property description:
	 * The Paint used to flood the region.
	 * Min: n/a
	 * Max: n/a
	 * Default: "red" or #ff0000
	 * Identity: n/a
	 * 
	 * Default value: "red"</pre>
	 * */
	public String paint() default "red";
	
	/**
	 * <pre>Sets the value of the property x.
	 * 
	 * Property description:
	 * Sets the x location of the region to be flooded, relative to the local coordinate space of the content Node.
	 * Min: n/a
	 * Max: n/a
	 * Default: 0.0  
	 * Identity: 0.0
	 * 
	 * Default value: 0.0</pre>
	 * */
	public double x() default 0.0;
	
	/**
	 * <pre>Sets the value of the property y.
	 * 
	 * Property description:
	 * Sets the y location of the region to be flooded, relative to the local coordinate space of the content Node.
	 * Min: n/a
	 * Max: n/a
	 * Default: 0.0  
	 * Identity: 0.0
	 * 
	 * Default value: 0.0</pre>
	 * */
	public double y() default 0.0;
	
	/**
	 * <pre>Sets the value of the property width.
	 * 
	 * Property description:
	 * Sets the width of the region to be flooded, relative to the local coordinate space of the content Node.
	 * Min: n/a
	 * Max: n/a
	 * Default: 0.0  
	 * Identity: 0.0
	 * 
	 * Default value: 0.0</pre>
	 * */
	public double width() default 0.0;
	
	/**
	 * <pre>Sets the value of the property height.
	 * 
	 * Property description:
	 * Sets the height of the region to be flooded, relative to the local coordinate space of the content Node.
	 * Min: n/a
	 * Max: n/a
	 * Default: 0.0  
	 * Identity: 0.0
	 * 
	 * Default value: 0.0</pre>
	 * */
	public double height() default 0.0;
			
	
}
