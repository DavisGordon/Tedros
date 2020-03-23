package com.tedros.fxapi.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;

import com.tedros.fxapi.annotation.parser.ITEffectParse;
import com.tedros.fxapi.annotation.parser.TEffectParser;
import com.tedros.fxapi.effect.ITEffectBuilder;

/**
 * <pre>
 * An effect which creates a monochrome duplicate of an 
 * input with blurry edges. This effect is primarily used 
 * along with its default black color for purposes of 
 * combining it with the original to create a shadow. 
 * It can also be used with a light color and combined 
 * with an original to create a glow effect. 
 * 
 * The DropShadow effect is a utility effect which 
 * automatically combines this Shadow effect with an original 
 * graphic for ease of adding a shadow to an existing 
 * scene graph Node with a single effect.
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TShadow {
	
	public final class NullEffectBuilder implements ITEffectBuilder<Shadow>{
		@Override
		public Shadow build() {
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
	public Class<? extends ITEffectBuilder<Shadow>> builder() default NullEffectBuilder.class;
	
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
	 * 
	 * The radius of the shadow blur kernel. This attribute 
	 * controls the distance that the shadow is spread to each 
	 * side of the source pixels. Setting the radius is equivalent 
	 * to setting both the width and height attributes to a value of (2 * radius + 1).
	 * 
	 * Min:   0.0
	 * Max: 127.0
	 * Default:  10.0
	 * Identity:  0.0
	 * 
	 * Default value:
	 * 10.0
	 * </pre>
	 * */
	public double radius() default 10.0;
	
	/**
	 * <pre>
	 * Sets the value of the property width.
	 * 
	 * Property description:
	 * 
	 * The horizontal size of the shadow blur kernel. 
	 * This attribute controls the horizontal size of 
	 * the total area over which the shadow of a single 
	 * pixel is distributed by the blur algorithm. 
	 * Values less than 1.0 are not distributed beyond 
	 * the original pixel and so have no blurring effect 
	 * on the shadow.
	 * 
	 * Min:   0.0
	 * Max: 255.0
	 * Default:  21.0
	 * Identity:  <1.0
	 * 
	 * Default value: 21.0
	 * </pre>
	 * */
	public double width() default 21.0;
	
	/**
	 * <pre>
	 * Sets the value of the property height.
	 * 
	 * Property description:
	 * 
	 * The vertical size of the shadow blur kernel. 
	 * This attribute controls the vertical size of 
	 * the total area over which the shadow of a 
	 * single pixel is distributed by the blur algorithm. 
	 * Values less than 1.0 are not distributed beyond 
	 * the original pixel and so have no blurring effect 
	 * on the shadow.
	 * 
	 * Min:   0.0
	 * Max: 255.0
	 * Default:  21.0
	 * Identity:  <1.0
	 * 
	 * Default value: 21.0
	 * </pre>
	 * */
	public double height() default 21.0;
	
	
	/**
	 * <pre>
	 * Sets the value of the property blurType.
	 * 
	 * Property description:
	 * 
	 * The algorithm used to blur the shadow.
	 *
	 *       Min: n/a
	 *       Max: n/a
	 *   Default: BlurType.THREE_PASS_BOX
	 *  Identity: n/a
	 *  
	 * Default value: THREE_PASS_BOX 
	 * </pre>
	 * */
	public BlurType blurType() default BlurType.THREE_PASS_BOX;
	
	/**
	 * <pre>
	 * Sets the value of the property color.
	 * 
	 * Property description:
	 * 
	 * The shadow Color.
	 *        Min: n/a
	 *        Max: n/a
	 *    Default: #000000
	 *   Identity: n/a
	 *   
	 *   Default value: #000000 (black)</pre>
	 * */
	public String color() default "#000000";
	
}
