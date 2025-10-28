package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

public class TTextFieldParser extends TAnnotationParser<TTextField, org.tedros.fx.control.TTextField> {
	
	  @Override 
	  public void parse(TTextField annotation, org.tedros.fx.control.TTextField object, String... byPass) throws Exception {
		  super.parse(annotation, object, "+maxLength"); 
	  }
	 
}
