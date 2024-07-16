/**
 * 
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.tedros.fx.annotation.parser.ITEffectParse;
import org.tedros.fx.util.TReflectionUtil;

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
				
				ITEffectParse parser = (ITEffectParse) parseClass.getDeclaredConstructor().newInstance();	
					
				return parser.parse(annotation);
			}
		}
		
		return null;
		
	}

}
