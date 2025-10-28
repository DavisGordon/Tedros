package org.tedros.fx.annotation.parser.engine;

import java.lang.annotation.Annotation;

import javafx.scene.effect.Effect;

public interface ITEffectParse extends ITBaseParser<Effect> {
		
	public Effect parse(Annotation annotation) throws Exception;

}
