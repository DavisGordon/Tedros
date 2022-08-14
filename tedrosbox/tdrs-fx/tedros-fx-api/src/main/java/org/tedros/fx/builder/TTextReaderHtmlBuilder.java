/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.tedros.core.model.ITModelView;
import org.tedros.core.style.TStyleResourceValue;
import org.tedros.fx.annotation.TCodeValue;
import org.tedros.fx.annotation.reader.TTextReaderHtml;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.domain.THtmlConstant;
import org.tedros.fx.form.ITModelForm;
import org.tedros.fx.reader.THtmlReader;
import org.tedros.fx.util.TMaskUtil;
import org.tedros.util.TStripTagUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


/**
 *
 * @author Davis Gordon
 *
 */
public class TTextReaderHtmlBuilder 
extends TBuilder
implements ITReaderHtmlBuilder<TTextReaderHtml, SimpleStringProperty> {

	private final TStripTagUtil tStripTagUtil;
	
	public TTextReaderHtmlBuilder(){
		tStripTagUtil = new TStripTagUtil();
	}
	
	
	@SuppressWarnings("rawtypes")
	public THtmlReader build(final TTextReaderHtml tAnnotation, SimpleStringProperty property) throws Exception {
		
		final TComponentDescriptor descriptor = getComponentDescriptor();
		final ITModelView modelView = descriptor.getModelView();
		final String fieldName = descriptor.getFieldDescriptor().getFieldName();
		final String uuid = UUID.randomUUID().toString();
		
		final String fieldValue = StringUtils.isNotBlank(property.getValue()) 
				? property.getValue() 
						: tAnnotation.text();
		
		String value = iEngine.getString(getValue(tAnnotation, fieldValue)); 
		String htmlTemplate = replaceTags(tAnnotation.htmlTemplateForControlValue());
		
		THtmlReader tHtmlReader;
		
		if(StringUtils.isNotBlank(htmlTemplate) && StringUtils.isNotBlank(tAnnotation.cssForControlValue())){
			htmlTemplate = htmlTemplate
					.replaceAll(THtmlConstant.STYLE, replaceTags(tAnnotation.cssForControlValue()))
					.replaceAll(THtmlConstant.NAME, fieldName)
					.replaceAll(THtmlConstant.ID, uuid);
			
			tHtmlReader = new THtmlReader(htmlTemplate, value);
		}else
			tHtmlReader = new THtmlReader(value);
		
		final String template = htmlTemplate;
		
		ChangeListener<String> listener = new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> arg0,	String arg1, String arg2) {
				WebView wv = ((ITModelForm) descriptor.getForm()).gettWebView();
				if(wv!=null){
					WebEngine engine = wv.getEngine();
					String newValue = iEngine.getString(getValue(tAnnotation, arg2));
					System.out.println("document.getElementById('"+uuid+"').innerHTML = \""+newValue+"\"");
					try{
					engine.executeScript("document.getElementById('"+uuid+"').innerHTML = \""+newValue+"\"");
					}catch(Exception e){
						System.err.println("TWarning: Reader html form cant find the element with "+uuid+" id generated to "+fieldName+" field.");
						System.out.println("To work check if the id attribute is setted like this \"id='%ID%'\" in the template "+template);
						e.printStackTrace();	
					}
				}
			}
		};
				
		property.addListener(listener);
		
		modelView.addListener(fieldName, listener);
		
		return tHtmlReader;
	}
	
	private String getValue(TTextReaderHtml tAnnotation, String objectValue) {
		
		if(StringUtils.isBlank(objectValue))
			return "";
		
		if(StringUtils.isNotBlank(tAnnotation.mask()))
			return TMaskUtil.applyMask(String.valueOf(objectValue), tAnnotation.mask());
		
		if(StringUtils.isNotBlank(tAnnotation.datePattern()))
			return new SimpleDateFormat(tAnnotation.datePattern()).format(objectValue);
		
		if(!(tAnnotation.booleanValues().trueCodes().length==1 
				&& tAnnotation.booleanValues().falseCodes().length==1 
				&& StringUtils.isBlank(tAnnotation.booleanValues().trueCodes()[0])
				&& StringUtils.isBlank(tAnnotation.booleanValues().falseCodes()[0])))
		{	
			if(ArrayUtils.contains(tAnnotation.booleanValues().trueCodes(), objectValue)){
				return tAnnotation.booleanValues().trueValue();
			}else
			if(ArrayUtils.contains(tAnnotation.booleanValues().falseCodes(), objectValue)){
				return tAnnotation.booleanValues().falseValue();
			}else
				return objectValue;
		}
		
		return getCodeValue(tAnnotation, objectValue);
	}
	
	private String getCodeValue(TTextReaderHtml tAnnotation, Object object){
		TCodeValue[] codeValues = tAnnotation.codeValues();
		String value = String.valueOf(object);
		if(!(codeValues.length==1 && StringUtils.isBlank(codeValues[0].code())))
			for (TCodeValue tCodeValue : codeValues) 
				if(tCodeValue.code().equals(value))
					return tCodeValue.value();
		return value;
	}
	
	private String replaceTags(String styleContent) {
		if(tStripTagUtil.isTagPresent(styleContent)){
			String[] keys = tStripTagUtil.getTags(styleContent);
			for (String key : keys) {
				TStyleResourceValue tResourceValue = TStyleResourceValue.getByName(key);
				if(tResourceValue!=null){
					String value = tResourceValue.customStyle();
					if(value!=null)
						return tStripTagUtil.replaceTag(styleContent, key, value);
				}	
			}
		}
		
		return styleContent;
	}
	
}
