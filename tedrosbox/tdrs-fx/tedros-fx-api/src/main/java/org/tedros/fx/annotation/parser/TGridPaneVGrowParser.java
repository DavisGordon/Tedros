package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TGridPane.TVGrow;
import org.tedros.fx.annotation.layout.TPriority;

import javafx.scene.layout.GridPane;

public class TGridPaneVGrowParser extends TAnnotationParser<TVGrow, GridPane> {

	@Override
	public void parse(TVGrow annotation, GridPane object, String... byPass) throws Exception {
		
		if(annotation.priority().length>0){
			String currField = getComponentDescriptor().getFieldDescriptor().getFieldName();
			TPriority[] arr = annotation.priority();
			for (TPriority tPriority : arr) {
				if(StringUtils.isBlank(tPriority.field()))
					continue;
				ITFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor(tPriority.field());
				if(fd!=null) {
					if(currField.equals(tPriority.field()))
						GridPane.setVgrow(fd.getComponent(), tPriority.priority());
					else if(fd.hasLayout())
						GridPane.setVgrow(fd.getLayout(), tPriority.priority());
					else
						GridPane.setVgrow(fd.getComponent(), tPriority.priority());
				}
			}
		}
	}
}
