/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/11/2014
 */
package com.tedros.fxapi.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.effect.GaussianBlur;

import com.tedros.fxapi.annotation.parser.ITEffectParse;
import com.tedros.fxapi.annotation.parser.TEffectParser;
import com.tedros.fxapi.effect.ITEffectBuilder;

/**
 * A blur effect using a Gaussian convolution kernel, with a configurable radius.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TGaussianBlur {

	public final class NullEffectBuilder implements ITEffectBuilder<GaussianBlur>{
		@Override
		public GaussianBlur build() {
			return null;
		}
	}
	
	/**
	 * <pre>
	 * The parser class for this annotation.
	 * 
	 * Default value:
	 * com.tedros.fxapi.annotation.parser.TEffectParser.class
	 * </pre>
	 * */
	public Class<? extends ITEffectParse> parser() default TEffectParser.class;
	
	/**
	 * <pre>Custom class builder for this Effect, setting this will ignore all others properties.
	 * 
	 * Default value: null</pre>
	 * */
	public Class<? extends ITEffectBuilder<GaussianBlur>> builder() default NullEffectBuilder.class;
	
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
	 * <pre>
	 * Sets the value of the property radius.
	 * 
	 * Property description:
	 * The radius of the blur kernel.
	 *        Min:  0.0
	 *        Max: 63.0
	 *    Default: 10.0
	 *   Identity:  0.0
	 *   
	 *   Default value: 10.0
	 * </pre>
	 * */
	public double radius() default 10.0;
	
}
