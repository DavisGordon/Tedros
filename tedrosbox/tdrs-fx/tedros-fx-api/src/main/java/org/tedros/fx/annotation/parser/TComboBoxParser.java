package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.fx.control.TItem;
import org.tedros.fx.util.TReflectionUtil;

import javafx.scene.control.ComboBox;

@SuppressWarnings("rawtypes")
public class TComboBoxParser extends TAnnotationParser<Annotation, ComboBox> {

	@Override
	public void parse(Annotation ann, ComboBox object, String... byPass)
			throws Exception {
		
		super.parse(ann, object, "firstItemText", "optionsList", "required");
		parseFirstItemText(ann, object);
	}

	/**
	 * @param ann
	 * @param object
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public static void parseFirstItemText(Annotation ann, ComboBox object)
			throws IllegalAccessException, InvocationTargetException {
		Method m = TReflectionUtil.getMethod(ann, "firstItemText");
		if(m!=null) {
			final String firstItemText = (String) m.invoke(ann);
			if(StringUtils.isNotBlank(firstItemText)){
				object.getItems().addAll(0, 
						Arrays.asList(new TItem(TLanguage.getInstance().getString(firstItemText), null)));
			}
		}
	}
	
}
