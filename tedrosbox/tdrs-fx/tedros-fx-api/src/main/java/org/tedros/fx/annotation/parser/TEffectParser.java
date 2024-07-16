package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.annotation.TDefaultValue;
import org.tedros.fx.annotation.effect.TBlend;
import org.tedros.fx.annotation.effect.TDisplacementMap;
import org.tedros.fx.annotation.effect.TDropShadow;
import org.tedros.fx.annotation.effect.TEffect;
import org.tedros.fx.annotation.effect.TEffect.NullEffectBuilder;
import org.tedros.fx.annotation.effect.TFloatMap;
import org.tedros.fx.annotation.effect.TFloatMap.TSample;
import org.tedros.fx.annotation.effect.TFloatMap.TSamples;
import org.tedros.fx.annotation.effect.TLighting;
import org.tedros.fx.annotation.effect.TLighting.TLightDistant;
import org.tedros.fx.annotation.effect.TLighting.TLightPoint;
import org.tedros.fx.annotation.effect.TLighting.TLightSpot;
import org.tedros.fx.builder.TEffectBuilder;
import org.tedros.fx.effect.ITEffectBuilder;
import org.tedros.fx.util.TReflectionUtil;

import javafx.scene.effect.Effect;
import javafx.scene.effect.FloatMap;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;


public final class TEffectParser implements ITEffectParse {
	
	private static final String FX_EFFECT_PACKAGE = "javafx.scene.effect";
	private static final String BUILDER_METHOD_NAME = "builder";
	private static final String INPUT_METHOD_NAME = "input";
	private static final String BUMP_INPUT_METHOD_NAME = "bumpInput";
	private static final String CONTENT_INPUT_METHOD_NAME = "contentInput";
	private static final String TOP_INPUT_METHOD_NAME = "topInput";
	private static final String BOTTOM_INPUT_METHOD_NAME = "bottomInput";
	private static final String PAINT_METHOD_NAME = "paint";
	private static final String COLOR_METHOD_NAME = "color";
	private static final String DEFAULT_STRING_VALUE = "default";
	private static final String MAP_DATA_NAME = "mapData";
	private static final String LIGHTDISTANT_METHOD_NAME = "lightDistant";
	private static final String LIGHTPOINT_METHOD_NAME = "lightPoint";
	private static final String LIGHTSPOT_METHOD_NAME = "lightSpot";
	private static final double DOUBLE_DEFAULT_VALUE = TDefaultValue.DEFAULT_DOUBLE_VALUE_IDENTIFICATION; 
	private static final float FLOAT_DEFAULT_VALUE = TDefaultValue.DEFAULT_FLOAT_VALUE_IDENTIFICATION;
	private static final int INT_DEFAULT_VALUE = TDefaultValue.DEFAULT_INT_VALUE_IDENTIFICATION;
	private static final String SET = "set";
	private static final String HEIGHT = "height";
	private static final String WIDTH = "width";
		
	
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public final Effect parse(Annotation annotation) throws Exception{
		
		if(annotation instanceof TEffect){
			return analyse((TEffect) annotation);
		}
		
		String name = TReflectionUtil.getAnnotationName(annotation);
		if(name!=null){
			
			if(!isOkToBuild(name, annotation))
				return null;
			
			name = name.substring(1, name.length());
			Class<?> clazz = Class.forName(FX_EFFECT_PACKAGE+"."+name);
			Object componentInstance = clazz.getDeclaredConstructor().newInstance();
			
			Map<String, Object> params = TReflectionUtil.readAnnotation(annotation);
			
			Class builderClass = (Class) params.get(BUILDER_METHOD_NAME);
			ITEffectBuilder<?> builder = (ITEffectBuilder<?>) builderClass.getDeclaredConstructor().newInstance();
			Effect effect = builder.build();
			if(effect!=null){
				return effect;
			}
			
			for(String key : params.keySet()){
								
				Object value = params.get(key);
				final Method method = getSetterMethod(clazz, key);
				
				if( key.equals(BUILDER_METHOD_NAME) 
						|| (isInputMethod(key) && ((Class)value) == ITEffectBuilder.class))
					continue;
				
				if(isInputMethod(key)){
					
					ITEffectBuilder<?> inputBuilder = (ITEffectBuilder<?>) ((Class)value).getDeclaredConstructor().newInstance();
					Effect e = inputBuilder.build();
					if(e!=null)
						invokeSetterMethod(method, componentInstance, e);
					
				}else if(isBlendInputMethod(key)){
					
					TEffect tEffect = (key.equals(TOP_INPUT_METHOD_NAME)) 
							? ((TBlend)annotation).topInput() 
									: ((TBlend)annotation).bottomInput();
							
					invokeSetterMethod(method, componentInstance, parse(tEffect));
					
				}else if(key.equals(COLOR_METHOD_NAME) || key.equals(PAINT_METHOD_NAME)){
						
					invokeSetterMethod(method, componentInstance, Color.web((String)value));
						
				}else if(key.equals(MAP_DATA_NAME) && annotation instanceof TDisplacementMap){
					
					Map<String, Object> map =  (Map<String, Object>) value;
					FloatMap floatMap = new FloatMap();
					floatMap.setHeight((int)map.get(HEIGHT));
					floatMap.setWidth((int) map.get(WIDTH));
					
					TFloatMap tFloatMap = (TFloatMap) ((TDisplacementMap)annotation).mapData();
					
					for(TSample tSample : tFloatMap.sample()){
						
						final int x 	= (int) 	tSample.x();
						final int y 	= (int) 	tSample.y();
						final int band 	= (int) 	tSample.band();
						final float s 	= (float) 	tSample.s();
						if(x!=INT_DEFAULT_VALUE 
								& y!=INT_DEFAULT_VALUE 
								& band!=DOUBLE_DEFAULT_VALUE 
								& s!=INT_DEFAULT_VALUE)
							floatMap.setSample(x, y, band, s);
					}
					
					
					for(TSamples tSamples : tFloatMap.samples()){
						
						final int x 	= (int) 	tSamples.x();
						final int y 	= (int) 	tSamples.y();
						final float s0 	= (float) 	tSamples.s0();
						final float s1 	= (float) 	tSamples.s1();
						final float s2 	= (float) 	tSamples.s2();
						final float s3 	= (float) 	tSamples.s3();
						if(x!=INT_DEFAULT_VALUE 
							& y!=INT_DEFAULT_VALUE
							& s0!=FLOAT_DEFAULT_VALUE 
							& s1==FLOAT_DEFAULT_VALUE & s2==FLOAT_DEFAULT_VALUE & s3==FLOAT_DEFAULT_VALUE)
							floatMap.setSamples(x, y, s0);
						if(x!=INT_DEFAULT_VALUE 
								& y!=INT_DEFAULT_VALUE
								& s0!=FLOAT_DEFAULT_VALUE 
								& s1!=FLOAT_DEFAULT_VALUE 
								& s2==FLOAT_DEFAULT_VALUE & s3==FLOAT_DEFAULT_VALUE)
								floatMap.setSamples(x, y, s0, s1);
						if(x!=INT_DEFAULT_VALUE 
								& y!=INT_DEFAULT_VALUE
								& s0!=FLOAT_DEFAULT_VALUE 
								& s1!=FLOAT_DEFAULT_VALUE 
								& s2!=FLOAT_DEFAULT_VALUE 
								& s3==FLOAT_DEFAULT_VALUE)
								floatMap.setSamples(x, y, s0, s1, s2);
						if(x!=INT_DEFAULT_VALUE 
								& y!=INT_DEFAULT_VALUE
								& s0!=FLOAT_DEFAULT_VALUE 
								& s1!=FLOAT_DEFAULT_VALUE 
								& s2!=FLOAT_DEFAULT_VALUE 
								& s3!=FLOAT_DEFAULT_VALUE)
								floatMap.setSamples(x, y, s0, s1, s2, s3 );
					}
					
					invokeSetterMethod(method, componentInstance, floatMap);
						
				}else if(key.equals(LIGHTDISTANT_METHOD_NAME)){
					TLightDistant lightAnnotation = ((TLighting) annotation).lightDistant();
					final String color = lightAnnotation.color();
					final double azimuth = lightAnnotation.azimuth();
					final double elevation = lightAnnotation.elevation();
					if(!color.equals(DEFAULT_STRING_VALUE) & 
							azimuth!=DOUBLE_DEFAULT_VALUE & 
							elevation!=DOUBLE_DEFAULT_VALUE)
					{
						Light.Distant light = new Light.Distant();
						light.setAzimuth(azimuth);
						light.setColor(Color.web((String)color));
						light.setElevation(elevation);
						invokeSetterMethod(method, componentInstance, light);
					}
				
				}else if(key.equals(LIGHTPOINT_METHOD_NAME)){
					TLightPoint lightAnnotation = ((TLighting) annotation).lightPoint();
					final String color = lightAnnotation.color();
					final double x = lightAnnotation.x();
					final double y = lightAnnotation.y();
					final double z = lightAnnotation.z();
					if(!color.equals(DEFAULT_STRING_VALUE) & 
							x!=DOUBLE_DEFAULT_VALUE & 
							y!=DOUBLE_DEFAULT_VALUE & 
							z!=DOUBLE_DEFAULT_VALUE)
					{
						Light.Point light = new Light.Point();
						light.setColor(Color.web((String)color));
						light.setX(x);
						light.setY(y);
						light.setZ(z);
						invokeSetterMethod(method, componentInstance, light);
					}
					
				}else if(key.equals(LIGHTSPOT_METHOD_NAME)){
					TLightSpot lightAnnotation = ((TLighting) annotation).lightSpot();
					final String color = lightAnnotation.color();
					final double pointsAtX = lightAnnotation.pointsAtX();
					final double pointsAtY = lightAnnotation.pointsAtY();
					final double pointsAtZ = lightAnnotation.pointsAtZ();
					final double specularExponent = lightAnnotation.specularExponent();
					final double x = lightAnnotation.x();
					final double y = lightAnnotation.y();
					final double z = lightAnnotation.z();
					if(!color.equals(DEFAULT_STRING_VALUE) &
							pointsAtX!=DOUBLE_DEFAULT_VALUE &
							pointsAtY!=DOUBLE_DEFAULT_VALUE &
							pointsAtZ!=DOUBLE_DEFAULT_VALUE &
							specularExponent!=DOUBLE_DEFAULT_VALUE &
							x!=DOUBLE_DEFAULT_VALUE & 
							y!=DOUBLE_DEFAULT_VALUE & 
							z!=DOUBLE_DEFAULT_VALUE)
					{
						Light.Spot light = new Light.Spot();
						light.setColor(Color.web((String)color));
						light.setPointsAtX(pointsAtX);
						light.setPointsAtY(pointsAtY);
						light.setPointsAtZ(pointsAtZ);
						light.setSpecularExponent(specularExponent);
						light.setX(x);
						light.setY(y);
						light.setZ(z);
						invokeSetterMethod(method, componentInstance, light);
					}
					
				}else{
					invokeSetterMethod(method, componentInstance, value);
				}
					
			}
				
			return (Effect) componentInstance;
		}
		
		return null;
	}
	
	private final Effect analyse(TEffect tEffect) throws Exception{
		
		if(tEffect.bloom().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.bloom());
		
		if(tEffect.boxBlur().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.boxBlur());
		
		if(tEffect.colorAdjust().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.colorAdjust());
		
		if(tEffect.colorInput().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.colorInput());
		
		if(tEffect.displacementMap().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.displacementMap());
		
		if(tEffect.dropShadow().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.dropShadow());
		
		if(tEffect.gaussianBlur().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.gaussianBlur());
		
		if(tEffect.glow().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.glow());
		
		if(tEffect.innerShadow().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.innerShadow());
		
		if(tEffect.lighting().bumpInput()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.lighting());
		
		if(tEffect.motionBlur().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.motionBlur());
		
		if(tEffect.perspectiveTransform().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.perspectiveTransform());
		
		if(tEffect.reflection().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.reflection());
		
		if(tEffect.sepiaTone().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.sepiaTone());
		
		if(tEffect.shadow().input()!=NullEffectBuilder.class)
			return TEffectBuilder.getEffect(tEffect.shadow());
		
		return null;
	}
	
	private static boolean isInputMethod(final String key){
		return (key.equals(INPUT_METHOD_NAME) || key.equals(BUMP_INPUT_METHOD_NAME) || key.equals(CONTENT_INPUT_METHOD_NAME));
	}
	
	private static boolean isBlendInputMethod(final String key){
		return (key.equals(TOP_INPUT_METHOD_NAME) || key.equals(BOTTOM_INPUT_METHOD_NAME));
	}

	private static boolean isOkToBuild(String name, Annotation annotation) {
		if(name.equals(TDropShadow.class.getSimpleName())){
			return !(((TDropShadow) annotation).color().equals(DEFAULT_STRING_VALUE));
		}
		
		return true;
	}
	
	private static Method getSetterMethod(Class<?> clazz, String key){
		for(Method m : clazz.getDeclaredMethods()){
			if(!m.getName().equals(SET+StringUtils.capitalize(key)))
				continue;
			if(m.getParameterTypes().length!=1)
				continue;
			return m;
		}
		return null;
	}

	private static void invokeSetterMethod(Method method, Object obj, Object value) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		if(method!=null)		
			method.invoke(obj, value);
	}

}
