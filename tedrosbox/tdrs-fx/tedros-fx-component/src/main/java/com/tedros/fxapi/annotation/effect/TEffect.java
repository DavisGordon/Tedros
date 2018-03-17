/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 21/11/2014
 */
package com.tedros.fxapi.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.effect.Effect;

import com.tedros.fxapi.annotation.parser.ITEffectParse;
import com.tedros.fxapi.annotation.parser.TEffectParser;
import com.tedros.fxapi.effect.ITEffectBuilder;

/**
 * <pre>
 * The annotation version for the abstract base class for all effect implementations. 
 * An effect is a graphical algorithm that produces an image, typically as a modification 
 * of a source image. An effect can be associated with a scene graph Node by setting the 
 * Node.effect attribute. 
 * 
 * Some effects change the color properties of the source pixels (such as ColorAdjust), others 
 * combine multiple images together (such as Blend), while still others warp or move the pixels 
 * of the source image around (such as DisplacementMap or PerspectiveTransform). All effects 
 * have at least one input defined and the input can be set to another effect to chain the effects 
 * together and combine their results, or it can be left unspecified in which case the effect will 
 * operate on a graphical rendering of the node it is attached to.
 * 
 * <h2>Atention, just one effect can be set.</h2>
 * 
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE})
public @interface TEffect {
	
	@SuppressWarnings("rawtypes")
	public class NullEffectBuilder implements ITEffectBuilder{
		@Override
		public Effect build() {
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
	 * A high-level effect that makes brighter portions of the input 
	 * image appear to glow, based on a configurable threshold.
	 * </pre>
	 * @see TBloom
	 * */
	public TBloom bloom() default @TBloom(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * A blur effect using a simple box filter kernel, with separately 
	 * configurable sizes in both dimensions, and an iteration parameter
	 * that controls the quality of the resulting blur.
	 * </pre>
	 * @see TBoxBlur
	 * */
	public TBoxBlur boxBlur() default @TBoxBlur(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * An effect that allows for per-pixel adjustments of hue, 
	 * saturation, brightness, and contrast.
	 * </pre>
	 * @see TColorAdjust
	 * */
	public TColorAdjust colorAdjust() default @TColorAdjust(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * An effect that renders a rectangular region that is filled 
	 * ("flooded") with the given Paint. This is equivalent to rendering 
	 * a filled rectangle into an image and using an ImageInput effect, 
	 * except that it is more convenient and potentially much more efficient.
	 * </pre>
	 * @see TColorInput
	 * */
	public TColorInput colorInput() default @TColorInput(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * An effect that shifts each pixel by a distance specified by the first two bands of
	 * the specified FloatMap. For each pixel in the output, the corresponding data from the
	 * mapData is retrieved, scaled and offset by the scale and offset attributes, scaled again
	 * by the size of the source input image and used as an offset from the destination pixel
	 * to retrieve the pixel data from the source input.
	 * 
	 * dst[x,y] = src[(x,y) + (offset+scale*map[x,y])*(srcw,srch)]
	 * 
	 * A value of (0.0, 0.0) would specify no offset for the pixel data whereas a value
	 * of (0.5, 0.5) would specify an offset of half of the source image size.
	 * </pre>
	 * @see TDisplacementMap
	 * */
	public TDisplacementMap displacementMap() default @TDisplacementMap(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * A high-level effect that renders a shadow of the given content 
	 * behind the content with the specified color, radius, and offset.
	 * </pre>
	 * @see TDropShadow
	 * */
	public TDropShadow dropShadow() default @TDropShadow(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * A blur effect using a Gaussian convolution kernel, 
	 * with a configurable radius.
	 * </pre>
	 * @see TGaussianBlur
	 * */
	public TGaussianBlur gaussianBlur() default @TGaussianBlur(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * A high-level effect that makes the input image appear to glow, 
	 * based on a configurable threshold.
	 * </pre>
	 * @see TGlow
	 * */
	public TGlow glow() default @TGlow(input=NullEffectBuilder.class);
		
	/**
	 * <pre>
	 * A high-level effect that renders a shadow inside the edges 
	 * of the given content with the specified color, radius, and offset.
	 * </pre>
	 * @see TInnerShadow
	 * */
	public TInnerShadow innerShadow() default @TInnerShadow(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * An effect that simulates a light source shining on
	 * the given content, which can be used to give flat
	 * objects a more realistic, three-dimensional appearance.
	 * </pre>
	 * @see TLighting
	 * */
	public TLighting lighting() default @TLighting(bumpInput=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * A motion blur effect using a Gaussian convolution kernel, 
	 * with a configurable radius and angle.
	 * </pre>
	 * @see TMotionBlur
	 * */
	public TMotionBlur motionBlur() default @TMotionBlur(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * An effect that provides non-affine transformation of the input content. 
	 * Most typically PerspectiveTransform is used to provide a "faux" three-dimensional 
	 * effect for otherwise two-dimensional content.
	 * </pre>
	 * @see TPerspectiveTransform
	 * */
	public TPerspectiveTransform perspectiveTransform() default @TPerspectiveTransform(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * An effect that renders a reflected version of the input below the actual input content.
	 * Note that the reflection of a Node with a Reflection effect installed will not respond 
	 * to mouse events or the containment methods on the Node.
	 * </pre>
	 * @see TReflection
	 * */
	public TReflection reflection() default @TReflection(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * A filter that produces a sepia tone effect, similar to antique photographs.
	 * </pre>
	 * @see TSepiaTone
	 * */
	public TSepiaTone sepiaTone() default @TSepiaTone(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * An effect which creates a monochrome duplicate of an 
	 * input with blurry edges. This effect is primarily used 
	 * along with its default black color for purposes of 
	 * combining it with the original to create a shadow. 
	 * It can also be used with a light color and combined 
	 * with an original to create a glow effect.
	 * </pre>
	 * @see TShadow
	 * */
	public TShadow shadow() default @TShadow(input=NullEffectBuilder.class);
	
	/**
	 * <pre>
	 * Force the parser execution 
	 * </pre>
	 * */
	public boolean parse();
}
