package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TVGrow;

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
				ITFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor(tPriority.field());
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
