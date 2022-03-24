/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.form;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TLanguage;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.reader.THtmlReader;
import com.tedros.fxapi.util.TReflectionUtil;
import com.tedros.util.TStripTagUtil;

/**
 * <pre>
 * Build a {@link TFieldBox}
 * </pre>
 * @author Davis Gordon
 *
 */
final class THtmlBoxBuilder {
	
	private static TStripTagUtil tStripTagUtil = new TStripTagUtil();
	
	private static StringBuffer controlTemplate = new StringBuffer("<div style='"+THtmlConstant.STYLE+"'>"
																+"<div style='"+THtmlConstant.CONTENT_STYLE+"'>"+THtmlConstant.CONTENT+"</div>"
															  +"</div>");
	
	private static StringBuffer topTemplate = new StringBuffer("<div style='"+THtmlConstant.STYLE+"'>"
																+"<span style='"+THtmlConstant.LABEL_STYLE+"'>"+THtmlConstant.LABEL+"</span>"
																+"<div style='"+THtmlConstant.CONTENT_STYLE+"'>"+THtmlConstant.CONTENT+"</div>"
															  +"</div>");
	
	private static StringBuffer rightTemplate = new StringBuffer("<div style='"+THtmlConstant.STYLE+"'>"
																+"<span style='"+THtmlConstant.CONTENT_STYLE+"'>"+THtmlConstant.CONTENT+"</span>"
																+"<span style='"+THtmlConstant.LABEL_STYLE+"'>"+THtmlConstant.LABEL+"</span>"
															  +"</div>");
	
	private static StringBuffer bottomTemplate = new StringBuffer("<div style='"+THtmlConstant.STYLE+"'>"
																	+"<div style='"+THtmlConstant.CONTENT_STYLE+"'>"+THtmlConstant.CONTENT+"</div>"
																	+"<span style='"+THtmlConstant.LABEL_STYLE+"'>"+THtmlConstant.LABEL+"</span>"
																  +"</div>");
	
	private static StringBuffer leftTemplate = new StringBuffer("<div style='"+THtmlConstant.STYLE+"'>"
																+"<span style='"+THtmlConstant.LABEL_STYLE+"'>"+THtmlConstant.LABEL+"</span>"
																+"<span style='"+THtmlConstant.CONTENT_STYLE+"'>"+THtmlConstant.CONTENT+"</span>"
															  +"</div>");
	
	public static THtmlReader build(final THtmlReader control, final TComponentDescriptor descriptor){
		
		Annotation annotation = getReaderHtmlAnnotation(descriptor);
		
		String cssForBox = replaceTags((String)TReflectionUtil.getValue(annotation, "cssForHtmlBox"));
		String cssForLabel = replaceTags((String)TReflectionUtil.getValue(annotation, "cssForLabelValue"));
		String cssForContent = replaceTags((String)TReflectionUtil.getValue(annotation, "cssForContentValue"));
		
		TLabel fieldLabel = getLabelFromReader(annotation);
		if(fieldLabel==null ||  StringUtils.isBlank(fieldLabel.text()))
			fieldLabel = (TLabel) descriptor.getFieldLabelAnnotation();
		
		StringBuffer box = new StringBuffer();
		
		if(fieldLabel==null){
			box.append(controlTemplate.toString()
					.replace(THtmlConstant.CONTENT, control.getText())
					.replace(THtmlConstant.STYLE, cssForBox)
					.replace(THtmlConstant.CONTENT_STYLE, cssForContent));
			return new THtmlReader(box.toString()); 
		}
		
		TLabelPosition position = fieldLabel.position();
		if(position ==  null)
			position = TLabelPosition.TOP;
		
		TLanguage iEngine = TLanguage.getInstance(null);
		
		if(position == TLabelPosition.TOP || position == TLabelPosition.DEFAULT){
			box.append(topTemplate.toString()
					.replace(THtmlConstant.LABEL, iEngine.getString(fieldLabel.text()))
					.replace(THtmlConstant.CONTENT, control.getText())
					.replace(THtmlConstant.STYLE, cssForBox)
					.replace(THtmlConstant.LABEL_STYLE, cssForLabel)
					.replace(THtmlConstant.CONTENT_STYLE, cssForContent));
		}
		
		if(position == TLabelPosition.BOTTOM){
			box.append(bottomTemplate.toString()
					.replace(THtmlConstant.LABEL, iEngine.getString(fieldLabel.text()))
					.replace(THtmlConstant.CONTENT, control.getText())
					.replace(THtmlConstant.STYLE, cssForBox)
					.replace(THtmlConstant.LABEL_STYLE, cssForLabel)
					.replace(THtmlConstant.CONTENT_STYLE, cssForContent));
		}
		
		if(position == TLabelPosition.RIGHT){
			box.append(rightTemplate.toString()
					.replace(THtmlConstant.LABEL, iEngine.getString(fieldLabel.text()))
					.replace(THtmlConstant.CONTENT, control.getText())
					.replace(THtmlConstant.STYLE, cssForBox)
					.replace(THtmlConstant.LABEL_STYLE, cssForLabel)
					.replace(THtmlConstant.CONTENT_STYLE, cssForContent));
		}
		
		if(position == TLabelPosition.LEFT){
			box.append(leftTemplate.toString()
					.replace(THtmlConstant.LABEL, iEngine.getString(fieldLabel.text()))
					.replace(THtmlConstant.CONTENT, control.getText())
					.replace(THtmlConstant.STYLE, cssForBox)
					.replace(THtmlConstant.LABEL_STYLE, cssForLabel)
					.replace(THtmlConstant.CONTENT_STYLE, cssForContent));
		}
		
		return new THtmlReader(box.toString()); 
	}
	
	private static TLabel getLabelFromReader(Annotation annotation) {
		return TReflectionUtil.getValue(annotation, "label");
	}
	
	private static Annotation getReaderHtmlAnnotation(TComponentDescriptor descriptor){
		Object[] arr = TReflectionUtil.getReaderHtmlBuilder(descriptor.getFieldAnnotationList());
		if(arr!=null)
			return (Annotation) arr[0];
		return null;
	}
	
	private static String replaceTags(String styleContent) {
		
		styleContent = StringUtils.defaultIfBlank(styleContent, "");
		
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
