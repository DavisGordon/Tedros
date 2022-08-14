package org.tedros.fx.annotation.scene.shape;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TAnnotationParser;
import org.tedros.fx.annotation.parser.TShapeParser;

import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

/**
 * <pre>
 * The Shape class provides definitions of common properties for objects that 
 * represent some form of geometric shape.
 *  
 * These properties include:
 * <ul><li>The Paint to be applied to the fillable interior of the shape (see setFill).</li>
 * <li>The Paint to be applied to stroke the outline of the shape (see setStroke).</li>
 * <li>The decorative properties of the stroke, including:</li><ul>
 * <li>The width of the border stroke.</li>
 * <li>Whether the border is drawn as an exterior padding to the edges of the shape, as an 
 * interior edging that follows the inside of the border, or as a wide path that follows along 
 * the border straddling it equally both inside and outside (see StrokeType).</li>
 * <li>Decoration styles for the joins between path segments and the unclosed ends of paths.</li>
 * <li>Dashing attributes.</li></ul></ul>
 * <strong>Interaction with coordinate systems</strong>
 * 
 * Most nodes tend to have only integer translations applied to them and quite often they are 
 * defined using integer coordinates as well. For this common case, fills of shapes with straight 
 * line edges tend to be crisp since they line up with the cracks between pixels that fall on integer 
 * device coordinates and thus tend to naturally cover entire pixels.
 * 
 * On the other hand, stroking those same shapes can often lead to fuzzy outlines because the default 
 * stroking attributes specify both that the default stroke width is 1.0 coordinates which often maps 
 * to exactly 1 device pixel and also that the stroke should straddle the border of the shape, falling 
 * half on either side of the border. Since the borders in many common shapes tend to fall directly on 
 * integer coordinates and those integer coordinates often map precisely to integer device locations, 
 * the borders tend to result in 50% coverage over the pixel rows and columns on either side of the border 
 * of the shape rather than 100% coverage on one or the other. Thus, fills may typically be crisp, but 
 * strokes are often fuzzy.
 * 
 * Two common solutions to avoid these fuzzy outlines are to use wider strokes that cover more pixels completely 
 * - typically a stroke width of 2.0 will achieve this if there are no scale transforms in effect - or to specify 
 * either the StrokeType.INSIDE or StrokeType.OUTSIDE stroke styles - which will bias the default single unit stroke 
 * onto one of the full pixel rows or columns just inside or outside the border of the shape.
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TShape {
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TShapeParser.class}
	 * </pre>
	 * */
	public Class<? extends TAnnotationParser<TShape, Shape>>[] parser() default {TShapeParser.class};
	
	/**
	* <pre>
	* {@link Shape} Class
	* 
	*  Sets the value of the property strokeType. 
	*  
	*  Property description: 
	*  
	*  Defines the direction (inside, centered, or outside) that the strokeWidth is 
	*  applied to the boundary of the shape. The image shows a shape without stroke 
	*  and with a thick stroke applied inside, centered and outside. 
	*  
	*  Default value: CENTERED
	* </pre>
	**/
	public StrokeType strokeType() default StrokeType.CENTERED;

	/**
	* <pre>
	* {@link Shape} Class
	* 
	*  Sets the value of the property strokeWidth. 
	*  
	*  Property description: 
	*  
	*  Defines a square pen line width. A value of 0.0 specifies a hairline stroke. 
	*  A value of less than 0.0 will be treated as 0.0. 
	*  
	*  Default value: 1.0
	* </pre>
	**/
	public double strokeWidth() default 1.0;

	/**
	* <pre>
	* {@link Shape} Class
	* 
	*  Sets the value of the property strokeLineJoin. 
	*  
	*  Property description: 
	*  
	*  Defines the decoration applied where path segments meet. 
	*  The value must have one of the following values: StrokeLineJoin.MITER, 
	*  StrokeLineJoin.BEVEL, and StrokeLineJoin.ROUND. 
	*  
	*  The image shows a shape using the values in the mentioned order. 
	*  
	*  Default value: MITER
	* </pre>
	**/
	public StrokeLineJoin strokeLineJoin() default StrokeLineJoin.MITER;

	/**
	* <pre>
	* {@link Shape} Class
	* 
	*  Sets the value of the property strokeLineCap. 
	*  
	*  Property description: 
	*  
	*  The end cap style of this Shape as one of the following values that define 
	*  possible end cap styles: StrokeLineCap.BUTT, StrokeLineCap.ROUND, and StrokeLineCap.SQUARE. 
	*  
	*  The image shows a line using the values in the mentioned order. 
	*  
	*  Default value: SQUARE
	* </pre>
	**/
	public StrokeLineCap strokeLineCap() default StrokeLineCap.SQUARE;

	/**
	* <pre>
	* {@link Shape} Class
	* 
	*  Sets the value of the property strokeMiterLimit. 
	*  
	*  Property description: 
	*  
	*  Defines the limit for the StrokeLineJoin.MITER line join style. 
	*  A value of less than 1.0 will be treated as 1.0. The image demonstrates the behavior. 
	*  Miter length (A) is computed as the distance of the most inside point to the most 
	*  outside point of the joint, with the stroke width as a unit. If the miter length is 
	*  bigger than the given miter limit, the miter is cut at the edge of the shape (B). 
	*  For the situation in the image it means that the miter will be cut at B for limit 
	*  values less than 4.65. 
	*  
	*  Default value: 10.0
	* </pre>
	**/
	public double strokeMiterLimit() default 10.0;

	/**
	* <pre>
	* {@link Shape} Class
	* 
	*  Sets the value of the property strokeDashOffset. 
	*  
	*  Property description: 
	*  
	*  Defines a distance specified in user coordinates that represents an offset into the 
	*  dashing pattern. In other words, the dash phase defines the point in the dashing pattern 
	*  that will correspond to the beginning of the stroke. 
	*  
	*  The image shows a stroke with dash array [25, 20, 5, 20] and a stroke with the same pattern 
	*  and offset 45 which shifts the pattern about the length of the first dash segment and the 
	*  following space. 
	*  
	*  Default value: 0
	* </pre>
	**/
	public double strokeDashOffset() default 0;

	/**
	* <pre>
	* {@link Shape} Class
	* 
	*  Sets the value of the property fill. 
	*  
	*  Property description: 
	*  
	*  Defines parameters to fill the interior of an Shape using the settings of the Paint context. 
	*  The default value is Color.BLACK for all shapes except Line, Polyline, and Path. 
	*  The default value is null for those shapes.
	* </pre>
	**/
	public String fill() default "#000000";

	/**
	* <pre>
	* {@link Shape} Class
	* 
	*  Sets the value of the property stroke. 
	*  
	*  Property description: 
	*  
	*  Defines parameters of a stroke that is drawn around the outline of a Shape using the settings 
	*  of the specified Paint. The default value is null for all shapes except Line, Polyline, and Path. 
	*  The default value is Color.BLACK for those shapes.
	* </pre>
	**/
	public String stroke() default "#000000";

	/**
	* <pre>
	* {@link Shape} Class
	* 
	*  Sets the value of the property smooth. 
	*  
	*  Property description: 
	*  
	*  Defines whether antialiasing hints are used or not for this Shape. 
	*  If the value equals true the rendering hints are applied. 
	*  
	*  Default value: true
	* </pre>
	**/
	public boolean smooth() default true;
	
	/**
	 * <pre>
	 * Set true to enable the parser execution 
	 * </pre>
	 * */
	public boolean parse();
}
