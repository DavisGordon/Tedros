package org.tedros.fx.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.json.JsonSanitizer;

/**
 * https://github.com/FasterXML/jackson-databind/
 * */
public class JsonHelper {

	private static ObjectMapper mapper;;
	
	static {
		mapper = JsonMapper.builder()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.build();
		// to allow serialization of "empty" POJOs (no properties to serialize)
		// (without this setting, an exception is thrown in those cases)
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
	
	public static <T> String write(T obj) {
		try {
			String s = mapper.writeValueAsString(obj);
			return s;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T read(String json, Class<T> type) {
		try {
			json = JsonSanitizer.sanitize(json);
			T o = mapper.readValue(json, type);
			return o;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
