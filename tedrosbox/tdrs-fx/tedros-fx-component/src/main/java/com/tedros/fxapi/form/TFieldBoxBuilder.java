/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.form;

import java.lang.annotation.Annotation;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelDefaultSetting;
import com.tedros.fxapi.annotation.parser.TAnnotationParser;
import com.tedros.fxapi.annotation.reader.TByteArrayReader;
import com.tedros.fxapi.annotation.reader.TDetailReader;
import com.tedros.fxapi.annotation.reader.TReader;
import com.tedros.fxapi.annotation.reader.TReaderDefaultSetting;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

/**
 * <pre>
 * Build a {@link TFieldBox}
 * </pre>
 * @author Davis Gordon
 *
 */
final class TFieldBoxBuilder {
	
	public static TFieldBox build(final Node control, final TComponentDescriptor descriptor){
		return generate(control, descriptor);
	}	
	
	private static TFieldBox generate(final Node control, final TComponentDescriptor descriptor) {
		
		final TLabel fieldLabel = (TLabel) descriptor.getFieldLabelAnnotation();
		final TLabelDefaultSetting tLabelDefaultSetting = (TLabelDefaultSetting) getDefaultSetting(fieldLabel, descriptor);
		final String controlFieldName =  (String) descriptor.getFieldDescriptor().getFieldName();
		TAnnotationParser<TLabel, Labeled> parser = null;
		Class<? extends TAnnotationParser<TLabel, Labeled>> parserClass = null;
		
		if(descriptor.getMode() == TViewMode.READER)
			setTLabelReaderSettings(fieldLabel, tLabelDefaultSetting, descriptor);
		
		Node label = null;
		TLabelPosition position = null;
		if(fieldLabel!=null && StringUtils.isNotBlank(fieldLabel.text())){
			
			if(parser == null | parserClass != fieldLabel.parser()){
				parserClass = fieldLabel.parser();
				try {
					parser = (TAnnotationParser<TLabel, Labeled>) parserClass.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			
			position = (tLabelDefaultSetting!=null && fieldLabel.position().equals(TLabelPosition.DEFAULT))
					? tLabelDefaultSetting.position() : fieldLabel.position();
			try {
				
				label = new Label();
				
				if(StringUtils.isBlank(label.getId()))
					label.setId((descriptor.getMode() == TViewMode.EDIT) ? "t-form-control-label": "t-reader-label");
				
				parser.setComponentDescriptor(descriptor);
				parser.parse(fieldLabel, (Labeled) label);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		TFieldBox fieldBox = new TFieldBox(controlFieldName, label, control, position);
		com.tedros.fxapi.annotation.control.TFieldBox tAnnotation = (com.tedros.fxapi.annotation.control.TFieldBox) 
				descriptor.getFieldDescriptor().getField().getAnnotation(com.tedros.fxapi.annotation.control.TFieldBox.class);
		if(tAnnotation!=null){
			try {
				TAnnotationParser.callParser(tAnnotation, fieldBox, descriptor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return fieldBox; 
	}
	
	private static void setTLabelReaderSettings(TLabel tLabel, final Annotation defaultSetting, final TComponentDescriptor descriptor) {
		
		Annotation annotation = getReaderAnnotation(descriptor);
		
		if(annotation instanceof TDetailReader){
			TDetailReader tAnnotation = (TDetailReader) annotation;
			if(tAnnotation.suppressLabels())
				tLabel = null;
			else if(StringUtils.isNotBlank(tAnnotation.label().text())){
				tLabel = tAnnotation.label();
			}
		}
		
		if(annotation instanceof TByteArrayReader){
			TByteArrayReader tAnnotation = (TByteArrayReader) annotation;
			if(tAnnotation.suppressLabels())
				tLabel = null;
			else if(StringUtils.isNotBlank(tAnnotation.label().text())){
				tLabel = tAnnotation.label();
			}
		}
		
		if(annotation instanceof TReader){
			TReader tAnnotation = (TReader) annotation;
			TReaderDefaultSetting tDefaultSetting = (defaultSetting!=null &&  defaultSetting instanceof TReaderDefaultSetting) 
					? (TReaderDefaultSetting) defaultSetting 
							: null;
			boolean flag = (tDefaultSetting!=null) ? (tAnnotation.suppressLabels()) ? tAnnotation.suppressLabels() : tDefaultSetting.suppressLabels()  
					:  tAnnotation.suppressLabels();
			
			if(flag)
				tLabel = null;
			else if(StringUtils.isNotBlank(tAnnotation.label().text())){
				tLabel = tAnnotation.label();
			}
		}
	}
	
	private static Annotation getReaderAnnotation(TComponentDescriptor descriptor){
		Object[] arr = TReflectionUtil.getReaderBuilder(descriptor.getFieldAnnotationList());
		if(arr!=null)
			return (Annotation) arr[0];
		return null;
	}
	
	private static Annotation getDefaultSetting(Annotation annotation, final TComponentDescriptor descriptor) {
		if(annotation != null){
			List<Annotation> typeAnnotations = (List<Annotation>) descriptor.getModelViewAnnotationList();
			String nameToCompare = TReflectionUtil.getAnnotationName(annotation).toLowerCase()+"defaultsetting";
			for (Annotation target : typeAnnotations)
				if(TReflectionUtil.getAnnotationName(target).toLowerCase().equals(nameToCompare))
					return target;
		}
		return null;
	}
}
