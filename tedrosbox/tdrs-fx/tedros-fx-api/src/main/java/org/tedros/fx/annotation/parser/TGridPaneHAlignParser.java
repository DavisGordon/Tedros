package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TGridPane.THAlignment;
import org.tedros.fx.annotation.layout.THPos;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TGridPaneHAlignParser extends TAnnotationParser<THAlignment, GridPane> {


	@Override
	public void parse(THAlignment annotation, GridPane object, String... byPass) throws Exception {
		
		if(annotation.values().length>0){
			String curField = super.getComponentDescriptor().getFieldDescriptor().getFieldName();
			THPos[] arr = annotation.values();
			for (THPos v : arr) {
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
						 
				GridPane.setHalignment(node, v.value());
			}
			
		}
	}
	
}
