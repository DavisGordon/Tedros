package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.descriptor.TFieldDescriptor;

import javafx.scene.layout.HBox;

public class THGrowParser extends TAnnotationParser<THGrow, HBox> {

	@Override
	public void parse(THGrow annotation, HBox object, String... byPass) throws Exception {
		
		if(annotation.priority().length>0){
			String currField = getComponentDescriptor().getFieldDescriptor().getFieldName();
			TPriority[] arr = annotation.priority();
			for (TPriority tPriority : arr) {
				if(StringUtils.isBlank(tPriority.field()))
					continue;
				TFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor(tPriority.field());
				if(fd!=null) {
					if(currField.equals(tPriority.field()))
						HBox.setHgrow(fd.getComponent(), tPriority.priority());
					else if(fd.hasLayout())
						HBox.setHgrow(fd.getLayout(), tPriority.priority());
					else
						HBox.setHgrow(fd.getComponent(), tPriority.priority());
				}
			}
		}
	}
}
