package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

public interface ITBaseParser<T> {

	public T parse(Annotation annotation) throws Exception;
	
}
