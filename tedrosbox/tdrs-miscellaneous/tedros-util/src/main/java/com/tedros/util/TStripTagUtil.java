package com.tedros.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class TStripTagUtil {

	public static final String OPEN_TAG = "#{";
	public static final String CLOSE_TAG = "}";
	private static final String OPEN_TAG_EXPRESSION = "#\\{";
	
	public synchronized String[] getTags(String content){
		if(StringUtils.isBlank(content))
			return new String[]{content};
		
		String[] keys = StringUtils.substringsBetween(content, OPEN_TAG, CLOSE_TAG);
		if(ArrayUtils.isEmpty(keys))
			return new String[]{content};
		
		return keys;
	}
	
	public synchronized String replaceTag(String content, String key, String value){
		return content.replaceAll(OPEN_TAG_EXPRESSION+key+CLOSE_TAG, value.toString());
	}
	
	public synchronized boolean isTagPresent(String content){
		return content!=null && content.contains(OPEN_TAG);
	}
	
}
