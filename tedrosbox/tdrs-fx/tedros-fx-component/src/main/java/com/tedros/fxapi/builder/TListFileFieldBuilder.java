/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.collections.ObservableList;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.control.TListFileField;
import com.tedros.fxapi.annotation.parser.ITListFileFieldBuilder;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;
import com.tedros.fxapi.property.TSimpleFileModelProperty;
import com.tedros.fxapi.property.TBytesLoader;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TListFileFieldBuilder implements ITListFileFieldBuilder<com.tedros.fxapi.control.TListFileModelField> {

	public com.tedros.fxapi.control.TListFileModelField build(final Annotation annotation, final ObservableList<TSimpleFileModelProperty<?>> fileModelPropertyList) throws Exception {
		TListFileField tAnnotation = (TListFileField) annotation;
		final com.tedros.fxapi.control.TListFileModelField control = new com.tedros.fxapi.control.TListFileModelField(fileModelPropertyList,tAnnotation.orientation());
		setProperties(tAnnotation, control);
		return control;
	}
	
	public static void preLoadBytes(final TSimpleFileEntityProperty<?> fileEntityProperty) {
		try {
			TBytesLoader.loadBytesFromTFileEntity(fileEntityProperty.getValue().getByteEntity().getId(), fileEntityProperty.bytesProperty());
		} catch (TProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void setProperties(TListFileField tAnnotation, final com.tedros.fxapi.control.TListFileModelField control) {
		
		//if(tAnnotation.alignment()!=null) control.setAlignment(tAnnotation.alignment());
		if(tAnnotation.width()>0) control.setPrefWidth(tAnnotation.width());
		if(tAnnotation.height()>0) control.setPrefHeight(tAnnotation.height());
		//if(tAnnotation.opacity()>0) control.setOpacity(tAnnotation.opacity());
		if(StringUtils.isNotBlank(tAnnotation.style())) control.setStyle(tAnnotation.style());
		if(tAnnotation.styleClass()!=null && tAnnotation.styleClass().length>0) control.getStyleClass().addAll(tAnnotation.styleClass());
		//if(StringUtils.isNotBlank(tAnnotation.promptText())) control.getFileNameField().setPromptText(tAnnotation.promptText());
		//if(StringUtils.isNotBlank(tAnnotation.toolTip())) control.getFileNameField().setTooltip(new Tooltip(tAnnotation.toolTip()));
		
		control.setVisible(tAnnotation.visible());
		control.setDisable(tAnnotation.disable());
		//control.setRequired(tAnnotation.required());
		
		String[] extensions = tAnnotation.extensions().getExtension();
		if(tAnnotation.moreExtensions()!=null && tAnnotation.moreExtensions().length>0)
			extensions = ArrayUtils.addAll(extensions, tAnnotation.moreExtensions());
		
//		control.setShowImage(tAnnotation.showImage());
//		control.setShowFilePath(tAnnotation.showFilePath());
//		control.setExtensions(extensions);
		
//		if(tAnnotation.minFileSize()>0) control.setMinFileSize(tAnnotation.minFileSize());
//		if(tAnnotation.maxFileSize()>0) control.setMaxFileSize(tAnnotation.maxFileSize());
//		if(tAnnotation.minImageWidth()>0) control.setMinImageWidth(tAnnotation.minImageWidth());
//		if(tAnnotation.maxImageWidth()>0) control.setMaxImageWidth(tAnnotation.maxImageWidth());
//		if(tAnnotation.minImageHeight()>0) control.setMinImageHeight(tAnnotation.minImageHeight());
//		if(tAnnotation.maxImageHeight()>0) control.setMaxImageHeight(tAnnotation.maxImageHeight());
		/*try {
			if(tAnnotation.openAction() != TActionEvent.class)
				control.setOpenAction(tAnnotation.openAction().newInstance());
			if(tAnnotation.cleanAction() != TActionEvent.class)
				control.setCleanAction(tAnnotation.cleanAction().newInstance());
			if(tAnnotation.loadAction() != TActionEvent.class)
				control.setLoadAction(tAnnotation.loadAction().newInstance());
			if(tAnnotation.selectAction() != TActionEvent.class)
				control.setSelectAction(tAnnotation.selectAction().newInstance());
			if(tAnnotation.imageClickAction() != TActionEvent.class)
				control.setImageClickAction(tAnnotation.imageClickAction().newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}*/
	}
	
	
}
