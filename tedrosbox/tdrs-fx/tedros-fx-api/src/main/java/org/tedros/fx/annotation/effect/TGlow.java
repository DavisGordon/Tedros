/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/11/2014
 */
package org.tedros.fx.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.ITEffectParse;
import org.tedros.fx.annotation.parser.TEffectParser;
import org.tedros.fx.effect.ITEffectBuilder;

import javafx.scene.effect.Glow;

/**
 * A high-level effect that makes the input image appear to glow, based on a configurable threshold.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TGlow {

	public final class NullEffectBuilder implements ITEffectBuilder<Glow>{
		@Override
		public Glow build() {
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
	public Class<? extends ITEffectBuilder<Glow>> builder() default NullEffectBuilder.class;
	
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
	 * Sets the value of the property level.
	 * 
	 * Property description:
	 * The level value, which controls the intensity of the glow effect.
	 *        Min: 0.0
	 *        Max: 1.0
	 *    Default: 0.3
	 *   Identity: 0.0
	 *   
	 *   Default value: 0.3
	 * </pre>
	 * */
	public double level() default 0.3;
	
}
