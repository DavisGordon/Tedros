package org.tedros.fx.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TEffectParser;
import org.tedros.fx.annotation.parser.engine.ITEffectParse;
import org.tedros.fx.effect.ITEffectBuilder;

import javafx.scene.effect.PerspectiveTransform;

/**
 * <pre>
 * An effect that provides non-affine transformation of the input content. 
 * Most typically PerspectiveTransform is used to provide a "faux" three-dimensional 
 * effect for otherwise two-dimensional content.
 * 
 * A perspective transformation is capable of mapping an arbitrary quadrilateral into 
 * another arbitrary quadrilateral, while preserving the straightness of lines. Unlike 
 * an affine transformation, the parallelism of lines in the source is not necessarily 
 * preserved in the output.
 * 
 * Note that this effect does not adjust the coordinates of input events or any methods 
 * that measure containment on a Node. The results of mouse picking and the containment 
 * methods are undefined when a Node has a PerspectiveTransform effect in place.
 * 
 * Example:
 * 
 *  PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
 *  perspectiveTrasform.setUlx(10.0);
 *  perspectiveTrasform.setUly(10.0);
 *  perspectiveTrasform.setUrx(310.0);
 *  perspectiveTrasform.setUry(40.0);
 *  perspectiveTrasform.setLrx(310.0);
 *  perspectiveTrasform.setLry(60.0);
 *  perspectiveTrasform.setLlx(10.0);
 *  perspectiveTrasform.setLly(90.0);
 * 
 *  Group g = new Group();
 *  g.setEffect(perspectiveTrasform);
 *  g.setCache(true);
 * 
 *  Rectangle rect = new Rectangle();
 *  rect.setX(10.0);
 *  rect.setY(10.0);
 *  rect.setWidth(280.0);
 *  rect.setHeight(80.0);
 *  rect.setFill(Color.web("0x3b596d"));
 * 
 *  Text text = new Text();
 *  text.setX(20.0);
 *  text.setY(65.0);
 *  text.setText("Perspective");
 *  text.setFill(Color.ALICEBLUE);
 *  text.setFont(Font.font(null, FontWeight.BOLD, 36));
 * 
 *  g.getChildren().addAll(rect, text);
 *  </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TPerspectiveTransform {
	
	public final class NullEffectBuilder implements ITEffectBuilder<PerspectiveTransform>{
		@Override
		public PerspectiveTransform build() {
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
	 * Custom class builder for this Effect, 
	 * setting this will ignore all others properties.
	 * 
	 * Default value: null
	 * </pre>
	 * */
	public Class<? extends ITEffectBuilder<PerspectiveTransform>> builder() default NullEffectBuilder.class;
	
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
	 * Sets the value of the property ulx.
	 * 
	 * Property description:
	 * 
	 * The x coordinate of the output location onto which the upper left corner of the source is mapped.
	 * 
	 * Default value: 0.0
	 * </pre>
	 * */
	public double ulx() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property uly.
	 * 
	 * Property description:
	 * 
	 * The y coordinate of the output location onto which the upper left corner of the source is mapped.
	 * 
	 * Default value: 0.0
	 * </pre>
	 * */
	public double uly() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property urx.
	 * 
	 * Property description:
	 * 
	 * The x coordinate of the output location onto which the upper right corner of the source is mapped.
	 * 
	 * Default value: 0.0
	 * </pre>
	 * */
	public double urx() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property ury.
	 * 
	 * Property description:
	 * 
	 * The y coordinate of the output location onto which the upper right corner of the source is mapped.
	 * 
	 * Default value: 0.0
	 * </pre>
	 * */
	public double ury() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property lrx.
	 * 
	 * Property description:
	 * 
	 * The x coordinate of the output location onto which the lower right corner of the source is mapped.
	 * 
	 * Default value: 0.0
	 * </pre>
	 * */
	public double lrx() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property lry.
	 * 
	 * Property description:
	 * 
	 * The y coordinate of the output location onto which the lower right corner of the source is mapped.
	 * 
	 * Default value: 0.0
	 * </pre>
	 * */
	public double lry() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property llx.
	 * 
	 * Property description:
	 * 
	 * The x coordinate of the output location onto which the lower left corner of the source is mapped.
	 * 
	 * Default value: 0.0
	 * </pre>
	 * */
	public double llx() default 0.0;
	
	/**
	 * <pre>
	 * Sets the value of the property lly.
	 * 
	 * Property description:
	 * 
	 * The y coordinate of the output location onto which the lower left corner of the source is mapped.
	 * 
	 * Default value: 0.0
	 * </pre>
	 * */
	public double lly() default 0.0;

}
