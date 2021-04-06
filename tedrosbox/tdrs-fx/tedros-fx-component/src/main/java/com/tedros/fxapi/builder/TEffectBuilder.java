/**
 * 
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.tedros.fxapi.annotation.parser.ITEffectParse;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.scene.effect.Effect;

/**
 * @author Davis Gordon
 *
 */
public class TEffectBuilder {

	private TEffectBuilder() {
		
	}

	public static final Effect getEffect(Annotation annotation) throws Exception{
		
		final Method parserMethod = TReflectionUtil.getParserMethod(annotation);
		
		if(parserMethod!=null){
			
			final Class<?> parseClass = (Class<?>) parserMethod.invoke(annotation);
		
			/*
			 * ITEffectParse 
			 * */
			if(TReflectionUtil.isImplemented(parseClass, ITEffectParse.class)){
				
				ITEffectParse parser = (ITEffectParse) parseClass.newInstance();	
					
				return parser.parse(annotation);
			}
		}
		
		return null;
		
	}

}
