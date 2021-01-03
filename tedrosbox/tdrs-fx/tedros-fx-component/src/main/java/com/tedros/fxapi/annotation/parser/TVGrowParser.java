package com.tedros.fxapi.annotation.parser;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.descriptor.TFieldDescriptor;

import javafx.scene.layout.VBox;

public class TVGrowParser extends TAnnotationParser<TVGrow, VBox> {

	@Override
	public void parse(TVGrow annotation, VBox object, String... byPass) throws Exception {
		
		if(annotation.priority().length>0){
			String currField = getComponentDescriptor().getFieldDescriptor().getFieldName();
			TPriority[] arr = annotation.priority();
			for (TPriority tPriority : arr) {
				if(StringUtils.isBlank(tPriority.field()))
					continue;
				TFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor(tPriority.field());
				if(fd!=null) {
					if(currField.equals(tPriority.field()))
						VBox.setVgrow(fd.getComponent(), tPriority.priority());
					else if(fd.hasLayout())
						VBox.setVgrow(fd.getLayout(), tPriority.priority());
					else
						VBox.setVgrow(fd.getComponent(), tPriority.priority());
				}
			}
		}
	}
}
