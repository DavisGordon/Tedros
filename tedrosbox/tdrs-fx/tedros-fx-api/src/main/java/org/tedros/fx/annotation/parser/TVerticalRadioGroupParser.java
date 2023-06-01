package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TVerticalRadioGroup;

public class TVerticalRadioGroupParser extends TAnnotationParser<TVerticalRadioGroup, org.tedros.fx.control.TVerticalRadioGroup> {

	/* (non-Javadoc)
	 * @see org.tedros.fx.annotation.parser.TAnnotationParser#parse(java.lang.annotation.Annotation, java.lang.Object, java.lang.String[])
	 */
	@Override
	public void parse(TVerticalRadioGroup annotation, org.tedros.fx.control.TVerticalRadioGroup object,
			String... byPass) throws Exception {
		super.parse(annotation, object, "radioButtons");
	}

}
