package org.tedros.fx.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TEffectParser;
import org.tedros.fx.annotation.parser.engine.ITEffectParse;
import org.tedros.fx.effect.ITEffectBuilder;

import javafx.scene.effect.Bloom;



/**
 * <pre>
 * A high-level effect that makes brighter portions 
 * of the input image appear to glow, based on a 
 * configurable threshold.
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TBloom {
	
	public final class NullEffectBuilder implements ITEffectBuilder<Bloom>{
		@Override
		public Bloom build() {
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
	 * <pre>
	 * Custom class builder for this Effect, 
	 * setting this will ignore all others properties.
	 * 
	 * Default value: null
	 * </pre>
	 * */
	public Class<? extends ITEffectBuilder<Bloom>> builder() default NullEffectBuilder.class;
	
	/**
	 * <pre>
	 * Sets the value of the property input.
	 * 
	 * Property description:
	 * 
	 * The input for this Effect. If set to null, or left unspecified, 
	 * a graphical image of the Node to which the Effect is attached 
	 * will be used as the input.
	 * 
	 * Default value: null
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEffectBuilder> input() default ITEffectBuilder.class;
	
	/**
	 * <pre>
	 * Sets the value of the property input.
	 * 
	 * Property description:
	 * 
	 * The threshold value controls the minimum luminosity 
	 * value of the pixels that will be made to glow.
	 * 
	 *       Min: 0.0
	 *       Max: 1.0
	 *   Default: 0.3
	 *  Identity: n/a
	 * 
	 * Default value: 0.3
	 * </pre>
	 * */
	public double threshold() default 0.3;
	
	
}
