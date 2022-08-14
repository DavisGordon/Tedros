package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.control.TItem;
import org.tedros.fx.util.TReflectionUtil;

import javafx.scene.control.ComboBox;

@SuppressWarnings("rawtypes")
public class TComboBoxParser extends TAnnotationParser<Annotation, ComboBox> {

	@Override
	@SuppressWarnings("unchecked")
	public void parse(Annotation ann, ComboBox object, String... byPass)
			throws Exception {
		
		super.parse(ann, object, "firstItemTex", "optionsList");
		Method m = TReflectionUtil.getMethod(ann, "firstItemTex");
		if(m!=null) {
			final String firstItemText = (String) m.invoke(ann);
			if(StringUtils.isNotBlank(firstItemText)){
				object.getItems().addAll(0, Arrays.asList(new TItem(iEngine.getString(firstItemText), null)));
			}
		}
	}
	
}
