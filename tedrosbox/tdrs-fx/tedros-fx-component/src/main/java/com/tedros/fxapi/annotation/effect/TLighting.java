package com.tedros.fxapi.annotation.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.scene.effect.Lighting;

import com.tedros.fxapi.annotation.parser.ITEffectParse;
import com.tedros.fxapi.annotation.parser.TEffectParser;
import com.tedros.fxapi.effect.ITEffectBuilder;


/**
 * <pre>
 * An effect that simulates a light source shining on 
 * the given content, which can be used to give flat 
 * objects a more realistic, three-dimensional appearance.
 * 
 * Example:
 * 
 * Light.Distant light = new Light.Distant();
 * light.setAzimuth(-135.0);
 *
 * Lighting lighting = new Lighting();
 * lighting.setLight(light);
 * lighting.setSurfaceScale(5.0);
 * 
 * Text text = new Text();
 * text.setText("JavaFX!");
 * text.setFill(Color.STEELBLUE);
 * text.setFont(Font.font(null, FontWeight.BOLD, 60));
 * text.setX(10.0);
 * text.setY(10.0);
 * text.setTextOrigin(VPos.TOP);
 * 
 * text.setEffect(lighting);
 * </pre>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TLighting {
	
	public final class NullEffectBuilder implements ITEffectBuilder<Lighting>{
		@Override
		public Lighting build() {
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
	public Class<? extends ITEffectBuilder<Lighting>> builder() default NullEffectBuilder.class;
	
	/**
	 * <pre>
	 * Sets the value of the property bumpInput.
	 * 
	 * Property description:
	 * 
	 * The optional bump map input. If not specified, a bump map 
	 * will be automatically generated from the default input. 
	 * If set to null, or left unspecified, a graphical image of 
	 * the Node to which the Effect is attached will be used to 
	 * generate a default bump map.
	 * 
	 * Default value: a Shadow effect with a radius of 10
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEffectBuilder> bumpInput() default ITEffectBuilder.class;
	
	/**
	 * <pre>
	 * Sets the value of the property contentInput.
	 * 
	 * Property description:
	 * 
	 * The content input for this Effect. If set to null, 
	 * or left unspecified, a graphical image of the Node 
	 * to which the Effect is attached will be used as the 
	 * input.
	 * 
	 * Default value: null
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITEffectBuilder> contentInput() default ITEffectBuilder.class;
	
	/**
	 * <pre>
	 * Sets the value of the property diffuseConstant. 
	 * 
	 * Property description:
	 * 
	 * The diffuse constant.
	 * 
	 *        Min: 0.0
	 *        Max: 2.0
	 *    Default: 1.0
	 *   Identity: n/a
	 *   
	 * Default value: 1.0
	 * </pre>
	 * */
	public double diffuseConstant() default 1.0;
	
	/**
	 * <pre>
	 * Sets the value of the property specularConstant.
	 * 
	 * Property description:
	 * 
	 * The specular constant.
	 * 
	 *        Min: 0.0
	 *        Max: 2.0
	 *    Default: 0.3
	 *   Identity: n/a
	 *   
	 * Default value: 0.3
	 * </pre>
	 * */
	public double specularConstant() default 0.3;
	
	/**
	 * <pre>
	 * Sets the value of the property specularExponent.
	 * 
	 * Property description:
	 * 
	 * The specular exponent.
	 * 
	 *        Min:  0.0
	 *        Max: 40.0
	 *    Default: 20.0
	 *   Identity:  n/a
	 *   
	 * Default value: 20.0
	 * </pre>
	 * */
	public double specularExponent() default 20.0;
	
	/**
	 * <pre>
	 * Sets the value of the property surfaceScale.
	 * 
	 * Property description:
	 * 
	 * The surface scale factor.
	 * 
	 *        Min:  0.0
	 *        Max: 10.0
	 *    Default:  1.5
	 *   Identity:  n/a
	 *   
	 *   Default value: 1.5
	 * </pre>
	 * */
	public double surfaceScale() default 1.5;
	
	
	/**
	 * <pre>
	 * The distant light source for this Lighting effect.
	 * </pre>
	 * */
	public TLightDistant lightDistant() default @TLightDistant(color="default", azimuth=-99229922, elevation=-99229922); 
	
	/**
	 * <pre>
	 * The light source at a given position in 
	 * 3D space for this Lighting effect.
	 * </pre>
	 * */
	public TLightPoint lightPoint() default @TLightPoint(color="default", x=-99229922, y=-99229922, z=-99229922);
	
	/**
	 * <pre>
	 * The spot light source at a given position in 
	 * 3D space, with configurable direction and focus 
	 * for this Lighting effect.
	 * </pre>
	 * */
	public TLightSpot lightSpot() default @TLightSpot(color="default", pointsAtX=-99229922, pointsAtY=-99229922, pointsAtZ=-99229922);
	
	
	
	/**
	 * Represents a distant light source.
	 * */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(value={ElementType.ANNOTATION_TYPE})
	public @interface TLightDistant{
		
		/**
		 * <pre>
		 * Sets the value of the property color.
		 * 
		 * Property description:
		 * 
		 * The color of the light source.
		 * 
		 *        Min: n/a
		 *        Max: n/a
		 *    Default: #ffffff (white)
		 *   Identity: n/a
		 *   
		 * Default value: #ffffff (white)
		 * </pre>
		 * */
		public String color() default "#ffffff";
		
		/**
		 * <pre>
		 * Sets the value of the property azimuth.
		 * 
		 * Property description:
		 * 
		 * The azimuth of the light. The azimuth is 
		 * the direction angle for the light source 
		 * on the XY plane, in degrees.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default: 45.0
		 *   Identity:  n/a
		 * 
		 * Default value: 45.0
		 * </pre>
		 * */
		public double azimuth() default 45.0;
		
		/**
		 * <pre>
		 * Sets the value of the property elevation.
		 * 
		 * Property description:
		 * 
		 * The elevation of the light. The elevation is 
		 * the direction angle for the light source on 
		 * the YZ plane, in degrees.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default: 45.0
		 *   Identity:  n/a
		 * 
		 * Default value: 45.0
		 * </pre>
		 * */
		public double elevation() default 45.0;
	}
	
	/**
	 * Represents a light source at a given position in 3D space.
	 * */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(value={ElementType.ANNOTATION_TYPE})
	public @interface TLightPoint{
		
		/**
		 * <pre>
		 * Sets the value of the property color.
		 * 
		 * Property description:
		 * 
		 * The color of the light source.
		 * 
		 *        Min: n/a
		 *        Max: n/a
		 *    Default: #ffffff (white)
		 *   Identity: n/a
		 *   
		 * Default value: #ffffff (white)
		 * </pre>
		 * */
		public String color() default "#ffffff";
		
		/**
		 * <pre>
		 * Sets the value of the property x.
		 * 
		 * Property description:
		 * 
		 * The x coordinate of the light position.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default:  0.0
		 *   Identity:  n/a
		 * 
		 * Default value: 0.0
		 * </pre>
		 * */
		public double x() default 0.0;
		
		/**
		 * <pre>
		 * Sets the value of the property y.
		 * 
		 * Property description:
		 * 
		 * The y coordinate of the light position.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default:  0.0
		 *   Identity:  n/a
		 * 
		 * Default value: 0.0
		 * </pre>
		 * */
		public double y() default 0.0;
		
		/**
		 * <pre>
		 * Sets the value of the property z.
		 * 
		 * Property description:
		 * 
		 * The z coordinate of the light position.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default:  0.0
		 *   Identity:  n/a
		 * 
		 * Default value: 0.0
		 * </pre>
		 * */
		public double z() default 0.0;
	}
	
	/**
	 * <pre>
	 * Represents a spot light source at a given position 
	 * in 3D space, with configurable direction and focus.
	 * </pre>
	 * */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(value={ElementType.ANNOTATION_TYPE})
	public @interface TLightSpot{
		
		/**
		 * <pre>
		 * Sets the value of the property color.
		 * 
		 * Property description:
		 * 
		 * The color of the light source.
		 * 
		 *        Min: n/a
		 *        Max: n/a
		 *    Default: #ffffff (white)
		 *   Identity: n/a
		 *   
		 * Default value: #ffffff (white)
		 * </pre>
		 * */
		public String color() default "#ffffff";
		
		/**
		 * <pre>
		 * Sets the value of the property pointsAtX.
		 * 
		 * Property description:
		 * 
		 * The x coordinate of the direction vector for this light.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default:  0.0
		 *   Identity:  n/a
		 * 
		 * Default value: 0.0
		 * </pre>
		 * */
		public double pointsAtX() default 0.0;
		
		/**
		 * <pre>
		 * Sets the value of the property pointsAtY.
		 * 
		 * Property description:
		 * 
		 * The y coordinate of the direction vector for this light.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default:  0.0
		 *   Identity:  n/a
		 * 
		 * Default value: 0.0
		 * </pre>
		 * */
		public double pointsAtY() default 0.0;
		
		/**
		 * <pre>
		 * Sets the value of the property pointsAtZ.
		 * 
		 * Property description:
		 * 
		 * The z coordinate of the direction vector for this light.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default:  0.0
		 *   Identity:  n/a
		 * 
		 * Default value: 0.0
		 * </pre>
		 * */
		public double pointsAtZ() default 0.0;
		
		/**
		 * <pre>
		 * Sets the value of the property specularExponent.
		 * 
		 * Property description:
		 * 
		 * The specular exponent, which controls the focus of this light source.
		 * 
		 *        Min:  0.0
		 *        Max:  4.0
		 *    Default:  1.0
		 *   Identity:  1.0
		 *   
		 * Default value: 1.0
		 * </pre>
		 * */
		public double specularExponent() default 1.0;
		
		/**
		 * <pre>
		 * Sets the value of the property x.
		 * 
		 * Property description:
		 * 
		 * The x coordinate of the light position.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default:  0.0
		 *   Identity:  n/a
		 * 
		 * Default value: 0.0
		 * </pre>
		 * */
		public double x() default 0.0;
		
		/**
		 * <pre>
		 * Sets the value of the property y.
		 * 
		 * Property description:
		 * 
		 * The y coordinate of the light position.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default:  0.0
		 *   Identity:  n/a
		 * 
		 * Default value: 0.0
		 * </pre>
		 * */
		public double y() default 0.0;
		
		/**
		 * <pre>
		 * Sets the value of the property z.
		 * 
		 * Property description:
		 * 
		 * The z coordinate of the light position.
		 * 
		 *        Min:  n/a
		 *        Max:  n/a
		 *    Default:  0.0
		 *   Identity:  n/a
		 * 
		 * Default value: 0.0
		 * </pre>
		 * */
		public double z() default 0.0;
	}

}
