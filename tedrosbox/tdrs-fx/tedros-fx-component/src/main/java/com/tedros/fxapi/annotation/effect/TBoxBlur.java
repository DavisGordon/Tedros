package com.tedros.fxapi.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.effect.BoxBlur;

import com.tedros.fxapi.annotation.parser.ITEffectParse;
import com.tedros.fxapi.annotation.parser.TEffectParser;
import com.tedros.fxapi.effect.ITEffectBuilder;



/**
 * A blur effect using a simple box filter kernel, with separately configurable sizes in both dimensions, and an iteration parameter that controls the quality of the resulting blur.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TBoxBlur {
	
	
	public final class NullEffectBuilder implements ITEffectBuilder<BoxBlur>{
		@Override
		public BoxBlur build() {
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
	public Class<? extends ITEffectBuilder<BoxBlur>> builder() default NullEffectBuilder.class;
	
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
	 * <pre>Sets the value of the property width.
	 * 
	 * Property description:
	 * The horizontal dimension of the blur effect. The color information for a given pixel will be spread across a Box of the indicated width centered over the pixel. Values less than or equal to 1 will not spread the color data beyond the pixel where it originated from and so will have no effect.
	 * Min:   0.0
	 * Max: 255.0
	 * Default:   5.0
	 * Identity:  <1.0
	 * 
	 * Default value: 5.0</pre>
	 * */
	public double width() default 5.0;
	
	/**
	 * <pre>Sets the value of the property height.
	 * 
	 * Property description:
	 * The vertical dimension of the blur effect. The color information for a given pixel will be spread across a Box of the indicated height centered over the pixel. Values less than or equal to 1 will not spread the color data beyond the pixel where it originated from and so will have no effect.
	 * Min:   0.0
	 * Max: 255.0
	 * Default:   5.0  
	 * Identity:  <1.0
	 * 
	 * Default value: 5.0</pre>
	 * */
	public double height() default 5.0;
	
	/**
	 * <pre>Sets the value of the property iterations.
	 * 
	 * Property description:
	 * The number of times to iterate the blur effect to improve its "quality" or "smoothness". Iterating the effect 3 times approximates the quality of a Gaussian Blur to within 3%.
	 * Min:   0
	 * Max:   3   
	 * Default:   1  
	 * Identity:   0
	 * 
	 * Default value: 1</pre>
	 * */
	public int iterations() default 1;
	
		
	
}
