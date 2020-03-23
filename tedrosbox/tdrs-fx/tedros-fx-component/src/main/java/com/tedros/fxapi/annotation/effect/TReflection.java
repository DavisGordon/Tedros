package com.tedros.fxapi.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.effect.Reflection;

import com.tedros.fxapi.annotation.parser.ITEffectParse;
import com.tedros.fxapi.annotation.parser.TEffectParser;
import com.tedros.fxapi.effect.ITEffectBuilder;

/**
 * <pre>
 * An effect that renders a reflected version of the input below the actual input content.
 * Note that the reflection of a Node with a Reflection effect installed will not respond 
 * to mouse events or the containment methods on the Node.
 * 
 * Example:


 * Reflection reflection = new Reflection();
 * reflection.setFraction(0.7);
 * 
 * Text text = new Text();
 * text.setX(10.0);
 * text.setY(50.0);
 * text.setCache(true);
 * text.setText("Reflections on JavaFX...");
 * text.setFill(Color.web("0x3b596d"));
 * text.setFont(Font.font(null, FontWeight.BOLD, 40));
 * text.setEffect(reflection);
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TReflection {

	public final class NullEffectBuilder implements ITEffectBuilder<Reflection>{
		@Override
		public Reflection build() {
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
	 * <pre>
	 * Custom class builder for this Effect, 
	 * setting this will ignore all others properties.
	 * 
	 * Default value: null
	 * </pre>
	 * */
	public Class<? extends ITEffectBuilder<Reflection>> builder() default NullEffectBuilder.class;
	
	/**
	 * <pre>
	 * Sets the value of the property input.
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
	 * Sets the value of the property topOffset.
	 * 
	 * Property description:
	 * 
	 * The top offset adjustment, which is the distance between the bottom of the input and the top of the reflection.
	 * 
	 *        Min: n/a
	 *        Max: n/a
	 *    Default: 0.0
	 *   Identity: 0.0
	 *   
	 *   Default value: 0.0
	 * </pre>
	 * */
	public double topOffset() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property topOpacity.
	 * 
	 * Property description:
	 * 
	 * The top opacity value, which is the opacity of the reflection at its top extreme.
	 * 
	 *        Min: 0.0
	 *        Max: 1.0
	 *    Default: 0.5
	 *   Identity: 1.0
	 *   
	 *   Default value: 0.5
	 * </pre>
	 * */
	public double topOpacity() default 0.5;
	
	/**
	 * <pre>
	 * Sets the value of the property bottomOpacity.
	 * 
	 * Property description:
	 * 
	 * The bottom opacity value, which is the opacity of the reflection at its bottom extreme.
	 * 
	 *        Min: 0.0
	 *        Max: 1.0
	 *    Default: 0.0
	 *   Identity: 1.0
	 *   
	 *   Default value: 0.0
	 * </pre>
	 * */
	public double bottomOpacity() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property fraction.
	 * 
	 * Property description:
	 * 
	 * The fraction of the input that is visible in the reflection. 
	 * For example, a value of 0.5 means that only the bottom half 
	 * of the input will be visible in the reflection.
	 * 
	 *        Min: 0.0
	 *        Max: 1.0
	 *    Default: 0.75
	 *   Identity: 1.0
	 *   
	 *   Default value: 0.75
	 * </pre>
	 * */
	public double fraction() default 0.75;
	
}
