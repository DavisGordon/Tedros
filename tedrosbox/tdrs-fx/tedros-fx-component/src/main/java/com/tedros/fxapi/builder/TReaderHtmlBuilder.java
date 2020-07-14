/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.model.ITModelView;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.reader.THtmlReader;
import com.tedros.fxapi.util.TMaskUtil;
import com.tedros.util.TStripTagUtil;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


/**
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TReaderHtmlBuilder 
extends TBuilder
implements ITReaderHtmlBuilder<TReaderHtml, Property> {

	private static TStripTagUtil tStripTagUtil;
	private static TReaderHtmlBuilder instance;
	
	private TReaderHtmlBuilder(){
		tStripTagUtil = new TStripTagUtil();
	}
	
	public static TReaderHtmlBuilder getInstance(){
		if(instance==null)
			instance = new TReaderHtmlBuilder();
		return instance;
	}

	@SuppressWarnings("unchecked")
	public THtmlReader build(final TReaderHtml tAnnotation, Property property) throws Exception {
		
		final TComponentDescriptor descriptor = getComponentDescriptor();
		final ITModelView modelView = descriptor.getModelView();
		final String fieldName = descriptor.getFieldDescriptor().getFieldName();
		//final String uuid = UUID.randomUUID().toString();
		
		String value = iEngine.getString(getValue(tAnnotation, property.getValue())); 
		String htmlTemplate = replaceTags(tAnnotation.htmlTemplateForControlValue());
		
		THtmlReader reader;
		
		if(StringUtils.isNotBlank(htmlTemplate) && StringUtils.isNotBlank(tAnnotation.cssForControlValue())){
			
			htmlTemplate = htmlTemplate
					.replaceAll(THtmlConstant.STYLE, replaceTags(tAnnotation.cssForControlValue()))
					.replaceAll(THtmlConstant.NAME, fieldName)
					.replaceAll(THtmlConstant.ID, fieldName);
			
			reader = new THtmlReader(htmlTemplate, value);
		}else
			reader = new THtmlReader(value);
		
		//build listener
		ChangeListener<Object> listener = new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<? extends Object> arg0,	Object arg1, Object arg2) {
				WebView wv = ((ITModelForm) descriptor.getForm()).gettWebView();
				if(wv!=null){
					WebEngine engine = wv.getEngine();
					
					/*String html = (String) engine.executeScript("document.documentElement.outerHTML");
					
					System.out.println(html);
					System.out.println("**************************************");
					System.out.println(fieldName);*/
					try{
						String newValue = iEngine.getString(getValue(tAnnotation, arg2));
						engine.executeScript("if(document.getElementById('"+fieldName+"')) document.getElementById('"+fieldName+"').innerHTML = '"+newValue+"'");
					}catch(Exception e){
						System.err.println("WARNING: ERROR ON TReaderHtmlBuilder line 98 The listeners still active after read mode was changed to edit mode. "+e.getMessage());
					}
				};
			}
		};
		//add listener to the property
		property.addListener(listener);
		// add listener to the model view repository
		modelView.addListener(fieldName, listener);
		
		return reader;
	}
	
	private String getValue(TReaderHtml tAnnotation, final Object objectValue) {
		
		if(objectValue==null)
			return "";
		
		if(objectValue instanceof String || objectValue instanceof Number){
			return StringUtils.isNotBlank(tAnnotation.mask()) 
					? TMaskUtil.applyMask(String.valueOf(objectValue), tAnnotation.mask()) 
							:  getCodeValue(tAnnotation, objectValue);
		}
		
		if(objectValue instanceof Date)
			return new SimpleDateFormat(tAnnotation.datePattern()).format(objectValue);
		
		if(objectValue instanceof Boolean)
			return ((Boolean)objectValue) ? tAnnotation.booleanValues().trueValue() : tAnnotation.booleanValues().falseValue();
		
		if(objectValue instanceof TModelView)
			return ((TModelView)objectValue).getDisplayProperty().getValue();
		
		return objectValue.toString();	
	}
	
	private String getCodeValue(TReaderHtml tAnnotation, Object object){
		TCodeValue[] codeValues = tAnnotation.codeValues();
		String value = String.valueOf(object);
		if(!(codeValues.length==1 && StringUtils.isBlank(codeValues[0].code())))
			for (TCodeValue tCodeValue : codeValues) 
				if(tCodeValue.code().equals(value))
					return tCodeValue.value();
		return value;
	}
	
	private String replaceTags(String content) {
		if(tStripTagUtil.isTagPresent(content)){
			String[] keys = tStripTagUtil.getTags(content);
			for (String key : keys) {
				TStyleResourceValue tResourceValue = TStyleResourceValue.getByName(key);
				if(tResourceValue!=null){
					String value = tResourceValue.customStyle();
					if(value!=null)
						return tStripTagUtil.replaceTag(content, key, value);
				}	
			}
		}
		
		return content;
	}
	
}
