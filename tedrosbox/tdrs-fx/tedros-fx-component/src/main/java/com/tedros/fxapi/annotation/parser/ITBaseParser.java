package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;

public interface ITBaseParser<T> {

	public T parse(Annotation annotation) throws Exception;
	
}
