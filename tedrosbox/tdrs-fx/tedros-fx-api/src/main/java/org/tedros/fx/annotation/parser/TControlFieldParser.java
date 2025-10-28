package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.parser.engine.ITControlFieldParser;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

public abstract class TControlFieldParser<A extends Annotation, T> extends TAnnotationParser<A, T> implements ITControlFieldParser<A, T> {

	
}
