package org.tedros.fx.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.ITEffectParse;
import org.tedros.fx.annotation.parser.TEffectParser;
import org.tedros.fx.effect.ITEffectBuilder;

import javafx.scene.effect.DisplacementMap;

/**
 * <pre>
 * An effect that shifts each pixel by a distance specified by the first two bands of 
 * the specified FloatMap. For each pixel in the output, the corresponding data from the 
 * mapData is retrieved, scaled and offset by the scale and offset attributes, scaled again 
 * by the size of the source input image and used as an offset from the destination pixel 
 * to retrieve the pixel data from the source input.
 *     dst[x,y] = src[(x,y) + (offset+scale*map[x,y])*(srcw,srch)]
 * 
 * A value of (0.0,&nbsp;0.0) would specify no offset for the pixel data whereas a value 
 * of (0.5,&nbsp;0.5) would specify an offset of half of the source image size.
 * 
 * Note that the mapping is the offset from a destination pixel to the source pixel location 
 * from which it is sampled which means that filling the map with all values of 0.5 would 
 * displace the image by half of its size towards the upper left since each destination pixel 
 * would contain the data that comes from the source pixel below and to the right of it.
 * 
 * Also note that this effect does not adjust the coordinates of input events or any methods that 
 * measure containment on a Node. The results of mouse picking and the containment methods are 
 * undefined when a Node has a DisplacementMap effect in place.
 * 
 * Example:
 * 
 * int width = 220;
 * int height = 100;
 * 
 * FloatMap floatMap = new FloatMap();
 * floatMap.setWidth(width);
 * floatMap.setHeight(height);
 * 
 * for (int i = 0; i < width; i++) {
 *     double v = (Math.sin(i / 20.0 * Math.PI) - 0.5) / 40.0;
 *     for (int j = 0; j < height; j++) {
 *         floatMap.setSamples(i, j, 0.0f, (float) v);
 *     }
 * }
 *
 * DisplacementMap displacementMap = new DisplacementMap();
 * displacementMap.setMapData(floatMap);
 *
 * Text text = new Text();
 * text.setX(40.0);
 * text.setY(80.0);
 * text.setText("Wavy Text");
 * text.setFill(Color.web("0x3b596d"));
 * text.setFont(Font.font(null, FontWeight.BOLD, 50));
 * text.setEffect(displacementMap);
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface TDisplacementMap {
	
	public final class NullEffectBuilder implements ITEffectBuilder<DisplacementMap>{
		@Override
		public DisplacementMap build() {
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
	public Class<? extends ITEffectBuilder<DisplacementMap>> builder() default NullEffectBuilder.class;
	
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
	 * Sets the value of the property mapData.
	 * 
	 * Property description:
	 * The map data for this Effect.
	 * 
	 * Default value: an empty map
	 * </pre>
	 * */
	public TFloatMap mapData() default @TFloatMap;
	
	
	/**
	 * <pre>
	 * Sets the value of the property scaleX.
	 * 
	 * Property description:
	 * The scale factor by which all x coordinate offset values in the FloatMap are multiplied.
	 *        Min: n/a
	 *        Max: n/a
	 *    Default: 1.0
	 *   Identity: 1.0
	 *   
	 *   Default value: 1.0
	 * </pre>
	 * */
	public double scaleX() default 1.0;
	
	/**
	 * <pre>
	 * Sets the value of the property scaleY.
	 * 
	 * Property description:
	 * The scale factor by which all y coordinate offset values in the FloatMap are multiplied.
	 *        Min: n/a
	 *        Max: n/a
	 *    Default: 1.0
	 *   Identity: 1.0
	 *   
	 *   Default value: 1.0
	 * </pre>
	 * */
	public double scaleY() default 1.0;
	
	/**
	 * <pre>
	 * Sets the value of the property offsetX.
	 * 
	 * Property description:
	 * The offset by which all x coordinate offset values in the FloatMap are displaced after they are scaled.
	 *        Min: n/a
	 *        Max: n/a
	 *    Default: 0.0
	 *   Identity: 0.0
	 *   
	 *   Default value: 0.0
	 * </pre>
	 * */
	public double offsetX() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property offsetY.
	 * 
	 * Property description:
	 * The offset by which all y coordinate offset values in the FloatMap are displaced after they are scaled.
	 *        Min: n/a
	 *        Max: n/a
	 *    Default: 0.0
	 *   Identity: 0.0
	 *   
	 *   Default value: 0.0
	 * </pre>
	 * */
	public double offsetY() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property wrap.
	 * 
	 * Property description:
	 * Defines whether values taken from outside the edges of the map "wrap around" or not.
	 *      
	 *       Min:  n/a
	 *       Max:  n/a
	 *   Default: false
	 *  Identity:  n/a
	 * 
	 * Default value: false
	 * </pre>
	 * */
	public boolean wrap() default false;

}
