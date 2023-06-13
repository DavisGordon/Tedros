package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TVRadioGroup;

public class TVRadioGroupParser extends TAnnotationParser<TVRadioGroup, org.tedros.fx.control.TVRadioGroup> {

	/* (non-Javadoc)
	 * @see org.tedros.fx.annotation.parser.TAnnotationParser#parse(java.lang.annotation.Annotation, java.lang.Object, java.lang.String[])
	 */
	@Override
	public void parse(TVRadioGroup annotation, org.tedros.fx.control.TVRadioGroup object,
			String... byPass) throws Exception {
		super.parse(annotation, object, "radio");
	}

}
