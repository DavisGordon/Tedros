package org.tedros.fx.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.annotation.parser.TEffectParser;
import org.tedros.fx.annotation.parser.engine.ITEffectParse;
import org.tedros.fx.effect.ITEffectBuilder;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;

/**
 * <pre>
 * An effect that blends the two inputs together using one of the pre-defined BlendModes.
 * Example:
 * 
 * 
 * Blend blend = new Blend();
 * blend.setMode(BlendMode.COLOR_BURN);
 * 
 * ColorInput colorInput = new ColorInput();
 * colorInput.setPaint(Color.STEELBLUE);
 * colorInput.setX(10);
 * colorInput.setY(10);
 * colorInput.setWidth(100);
 * colorInput.setHeight(180);
 * 
 * blend.setTopInput(colorInput);
 * 
 * Rectangle rect = new Rectangle();
 * rect.setWidth(220);
 * rect.setHeight(100);
 * Stop[] stops = new Stop[]{new Stop(0, Color.LIGHTSTEELBLUE), new Stop(1, Color.PALEGREEN)};
 * LinearGradient lg = new LinearGradient(0, 0, 0.25, 0.25, true, CycleMethod.REFLECT, stops);
 * rect.setFill(lg);
 * 
 * Text text = new Text();
 * text.setX(15);
 * text.setY(65);
 * text.setFill(Color.PALEVIOLETRED);
 * text.setText("COLOR_BURN");
 * text.setFont(Font.font(null, FontWeight.BOLD, 30));
 * 
 * Group g = new Group();
 * g.setEffect(blend);
 * g.getChildren().addAll(rect, text);
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TBlend {

	public final class NullEffectBuilder implements ITEffectBuilder<Blend>{
		@Override
		public Blend build() {
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
	public Class<? extends ITEffectBuilder<Blend>> builder() default NullEffectBuilder.class;
	
	/**
	 * <pre>
	 * Sets the value of the property bottomInput.
	 * 
	 * Property description:
	 * 
	 * The bottom input for this Blend operation. If set to null, 
	 * or left unspecified, a graphical image of the Node to which 
	 * the Effect is attached will be used as the input.
	 * 
	 * Default value: null
	 * </pre>
	 * */
	public TEffect bottomInput() default @TEffect(parse = false);
	
	/**
	 * <pre>
	 * Sets the value of the property topInput.
	 * 
	 * Property description:
	 * 
	 * The top input for this Blend operation. If set to null, 
	 * or left unspecified, a graphical image of the Node to which 
	 * the Effect is attached will be used as the input.
	 * 
	 * Default value: null
	 * </pre>
	 * */
	public TEffect topInput() default @TEffect(parse = false);
	
	
	/**
	 * <pre>
	 * Sets the value of the property mode.
	 * 
	 * Property description:
	 * 
	 * The BlendMode used to blend the two inputs together.
	 * 
	 *        Min: n/a
	 *        Max: n/a
	 *    Default: BlendMode.SRC_OVER
	 *   Identity: n/a
	 *   
	 * Default value: SRC_OVER
	 * </pre>
	 * */
	public BlendMode mode() default BlendMode.SRC_OVER; 
	
	/**
	 * <pre>
	 * Sets the value of the property opacity.
	 * 
	 * Property description:
	 * 
	 * The opacity value, which is modulated with the top input prior to blending.
	 * 
	 *        Min: 0.0
	 *        Max: 1.0
	 *    Default: 1.0
	 *   Identity: 1.0
	 *   
	 *   Default value: 1.0
	 * </pre>
	 * */
	public double opacity() default 1.0;
	
	/**
	 * <pre>
	 * Force the parser execution 
	 * </pre>
	 * */
	public boolean parse();
	
}
