/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package org.tedros.fx.properties;

import java.io.IOException;
import java.util.Properties;

import org.tedros.util.TLoggerUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TFXComponentsPropertiesUtil {
	
	private static Properties patternKeyValueProp;
	
	static{
		patternKeyValueProp = new Properties();
		try {
			patternKeyValueProp.load(TFXComponentsPropertiesUtil.class.getResourceAsStream("patterns.properties"));
		} catch (IOException e) {
			TLoggerUtil.error(TFXComponentsPropertiesUtil.class, e.getMessage(), e);
		}
	}
	
	public static enum PatternKeyValue{
		date_format;
	};

	public static String getPatternValue(PatternKeyValue keyValue){
		return patternKeyValueProp.getProperty(keyValue.name());
		
	}
	
}
