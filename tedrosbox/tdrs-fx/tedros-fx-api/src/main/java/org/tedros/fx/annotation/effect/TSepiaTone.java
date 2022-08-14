package org.tedros.fx.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.ITEffectParse;
import org.tedros.fx.annotation.parser.TEffectParser;
import org.tedros.fx.effect.ITEffectBuilder;

import javafx.scene.effect.SepiaTone;

/**
 * <pre>
 * A filter that produces a sepia tone effect, similar to antique photographs.
 * Example:
 * 
 * SepiaTone sepiaTone = new SepiaTone();
 * sepiaTone.setLevel(0.7);
 * 
 * Image image = new Image("boat.jpg");
 * ImageView imageView = new ImageView(image);
 * imageView.setFitWidth(200);
 * imageView.setPreserveRatio(true);
 * imageView.setEffect(sepiaTone);
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TSepiaTone {

	public final class NullEffectBuilder implements ITEffectBuilder<SepiaTone>{
		@Override
		public SepiaTone build() {
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
	public Class<? extends ITEffectBuilder<SepiaTone>> builder() default NullEffectBuilder.class;
	
	/**
	 * <pre>Sets the value of the property input.
	 * 
	 * Property description:
	 * 
	 * The input for this Effect. If set to null, or left unspecified, a graphical 
	 * image of the Node to which the Effect is attached will be used as the input.
	 * 
	 * Default value: null
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEffectBuilder> input() default ITEffectBuilder.class;
	
	/**
	 * <pre>
	 * Sets the value of the property level.
	 * 
	 * Property description:
	 * 
	 * The level value, which controls the intensity of the sepia effect.
	 * 
	 *        Min: 0.0f
	 *        Max: 1.0f
	 *    Default: 1.0f
	 *   Identity: 0.0f
	 *   
	 * Default value: 1.0f
	 * <pre>
	 * */
	public double level() default 1.0f;
}
