package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.control.DatePicker;

public class TDatePickerParser extends TAnnotationParser<TDatePickerField, DatePicker> {

	/* (non-Javadoc)
	 * @see org.tedros.fx.annotation.parser.TAnnotationParser#parse(java.lang.annotation.Annotation, java.lang.Object, java.lang.String[])
	 */
	@Override
	public void parse(TDatePickerField annotation, DatePicker object, String... byPass) throws Exception {
		super.parse(annotation, object, "required");
	}

	

}
