package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TGridPane.TVAlignment;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.annotation.layout.TVPos;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TGridPaneVAlignParser extends TAnnotationParser<TVAlignment, GridPane> {


	@Override
	public void parse(TVAlignment annotation, GridPane object, String... byPass) throws Exception {
		
		if(annotation.values().length>0){
			String curField = super.getComponentDescriptor().getFieldDescriptor().getFieldName();
			TVPos[] arr = annotation.values();
			for (TVPos v : arr) {
				String field = v.field();
				if(StringUtils.isBlank(field))
					continue;
				
				ITFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor(field);
				if(fd==null)
					throw new RuntimeException("[WARNING]" + getClass().getSimpleName()+
							": The field "+field+" was not found in "
							+ getComponentDescriptor().getModelView().getClass().getSimpleName()+", maybe they didnt loaded yet.");
				
				fd.setHasParent(true);
				Node node = field.equals(curField) 
						? fd.getComponent()
								: fd.hasLayout() 
									? fd.getLayout()
											: fd.getComponent();
						 
				GridPane.setValignment(node, v.value());
			}
			
		}
	}
	
}
