package org.tedros.fx.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TEffectParser;
import org.tedros.fx.annotation.parser.engine.ITEffectParse;
import org.tedros.fx.effect.ITEffectBuilder;

import javafx.scene.effect.ColorAdjust;



/**
 * An effect that allows for per-pixel adjustments of hue, saturation, brightness, and contrast.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TColorAdjust {
	
	
	public final class NullEffectBuilder implements ITEffectBuilder<ColorAdjust>{
		@Override
		public ColorAdjust build() {
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
	public Class<? extends ITEffectBuilder<ColorAdjust>> builder() default NullEffectBuilder.class;
	
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
	 * <pre>Sets the value of the property hue.
	 * 
	 * Property description:
	 * The hue adjustment value.
	 * Min: -1.0
	 * Max: +1.0
	 * Default:  0.0
	 * Identity:  0.0
	 * 
	 * Default value: 0.0</pre>
	 * */
	public double hue() default 0.0;
	
	/**
	 * <pre>Sets the value of the property saturation.
	 * 
	 * Property description:
	 * The saturation adjustment value.
	 * Min: -1.0
	 * Max: +1.0
	 * Default:  0.0
	 * Identity:  0.0
	 * 
	 * Default value: 0.0</pre>
	 * */
	public double saturation() default 0.0;
	
	/**
	 * <pre>Sets the value of the property brightness.
	 * 
	 * Property description:
	 * The brightness adjustment value.
	 * Min: -1.0
	 * Max: +1.0
	 * Default:  0.0
	 * Identity:  0.0
	 * 
	 * Default value: 0.0</pre>
	 * */
	public double brightness() default 0.0;
	
	/**
	 * <pre>Sets the value of the property contrast.
	 * 
	 * Property description:
	 * The contrast adjustment value.
	 * Min: -1.0
	 * Max: +1.0
	 * Default:  0.0
	 * Identity:  0.0
	 * 
	 * Default value: 0.0</pre>
	 * */
	public double contrast() default 0.0;
	
		
	
}
