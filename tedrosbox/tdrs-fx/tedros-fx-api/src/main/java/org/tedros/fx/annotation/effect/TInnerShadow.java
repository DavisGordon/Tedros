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

import org.tedros.fx.annotation.parser.TEffectParser;
import org.tedros.fx.annotation.parser.engine.ITEffectParse;
import org.tedros.fx.effect.ITEffectBuilder;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;

/**
 * A high-level effect that renders a shadow inside the edges of the given content with the specified color, radius, and offset.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TInnerShadow {
	
	public final class NullEffectBuilder implements ITEffectBuilder<InnerShadow>{
		@Override
		public InnerShadow build() {
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
	 * Custom class builder for this Effect, setting this will ignore all others properties.
	 * 
	 * Default value: null
	 * </pre>
	 * */
	public Class<? extends ITEffectBuilder<InnerShadow>> builder() default NullEffectBuilder.class;
	
	/**
	 * <pre>
	 * Sets the value of the property input.
	 * 
	 * Property description:
	 * 
	 * The input for this Effect. If set to null, or left unspecified, a graphical image of the Node 
	 * to which the Effect is attached will be used as the input.
	 * Default value: null
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEffectBuilder> input() default ITEffectBuilder.class;
	
	/**
	 * <pre>
	 * Sets the value of the property blurType.
	 * 
	 * Property description:
	 * 
	 * The algorithm used to blur the shadow.
	 * 
	 *      Min: n/a
	 *      Max: n/a
	 *  Default: BlurType.THREE_PASS_BOX
	 * Identity: n/a
	 * 
	 * Default value: THREE_PASS_BOX
	 * </pre>
	 * */
	public BlurType blurType() default BlurType.THREE_PASS_BOX;
	
	/**
	 * <pre>
	 * Sets the value of the property radius.
	 * 
	 * Property description:
	 * 
	 * The radius of the shadow blur kernel. This attribute controls the distance 
	 * that the shadow is spread to each side of the source pixels. Setting the 
	 * radius is equivalent to setting both the width and height attributes 
	 * to a value of (2 * radius + 1).
	 * 
	 *        Min:  0.0
	 *        Max: 127.0
	 *    Default: 10.0
	 *   Identity:  0.0
	 *   
	 *   Default value: 10.0
	 * </pre>
	 * */
	public double radius() default 10.0;
	
	/**
	 * <pre>
	 * Sets the value of the property width.
	 * 
	 * Property description:
	 * 
	 * The horizontal size of the shadow blur kernel. This attribute controls 
	 * the horizontal size of the total area over which the shadow of a single 
	 * pixel is distributed by the blur algorithm. Values less than 1.0 are not 
	 * distributed beyond the original pixel and so have no blurring effect on 
	 * the shadow.
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
	 * The vertical size of the shadow blur kernel. This attribute controls 
	 * the vertical size of the total area over which the shadow of a single 
	 * pixel is distributed by the blur algorithm. Values less than 1.0 are not 
	 * distributed beyond the original pixel and so have no blurring effect on 
	 * the shadow.
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
	 * Sets the value of the property choke.
	 * 
	 * Property description:
	 * 
	 * The choke of the shadow. The choke is the portion of the radius where the 
	 * contribution of the source material will be 100%. The remaining portion of 
	 * the radius will have a contribution controlled by the blur kernel. A choke 
	 * of 0.0 will result in a distribution of the shadow determined entirely by 
	 * the blur algorithm. A choke of 1.0 will result in a solid growth inward of 
	 * the shadow from the edges to the limit of the radius with a very sharp cutoff 
	 * to transparency inside the radius.
	 * 
	 *      Min: 0.0
	 *      Max: 1.0
	 *  Default: 0.0 
	 * Identity: 0.0
	 * 
	 * Default value: 0.0
	 * </pre>
	 * */
	public double choke() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property color.
	 * 
	 * Property description:
	 * 
	 * The shadow Color.
	 * 
	 *        Min: n/a
	 *        Max: n/a
	 *    Default: #000000 or black
	 *   Identity: n/a
	 *   
	 *   Default value: #000000
	 *</pre>
	 * */
	public String color() default "#000000";
	
	/**
	 * <pre>
	 * Sets the value of the property offsetX.
	 * 
	 * Property description:
	 * 
	 * The shadow offset in the x direction, in pixels.
	 * 
	 *        Min: n/a
	 *        Max: n/a   
	 *    Default: 0.0  
	 *   Identity: 0.0
	 *   
	 *   Default value:0.0 
	 * </pre>
	 * */
	public double offsetX() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property offsetY.
	 * 
	 * Property description:
	 * 
	 * The shadow offset in the y direction, in pixels.
	 * 
	 *        Min: n/a
	 *        Max: n/a   
	 *    Default: 0.0  
	 *   Identity: 0.0
	 *   
	 *   Default value:0.0 
	 * </pre>
	 * */
	public double offsetY() default 0.0;

}
