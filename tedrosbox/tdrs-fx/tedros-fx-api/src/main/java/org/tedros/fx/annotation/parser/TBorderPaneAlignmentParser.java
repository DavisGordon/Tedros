package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TBorderPane.TAlignment;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.annotation.layout.TPos;

import javafx.scene.layout.BorderPane;

public class TBorderPaneAlignmentParser extends TAnnotationParser<TAlignment, BorderPane> {


	@Override
	public void parse(TAlignment annotation, BorderPane object, String... byPass) throws Exception {
		if(annotation.values().length>0){
			String currField = getComponentDescriptor().getFieldDescriptor().getFieldName();
			TPos[] arr = annotation.values();
			for (TPos ann : arr) {
				if(StringUtils.isBlank(ann.field()))
					continue;
				ITFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor(ann.field());
				if(fd!=null) {
					if(currField.equals(ann.field()))
						BorderPane.setAlignment(fd.getComponent(), ann.value());
					else if(fd.hasLayout())
						BorderPane.setAlignment(fd.getLayout(), ann.value());
					else
						BorderPane.setAlignment(fd.getComponent(), ann.value());
				}
			}
		}
	}
	
}
