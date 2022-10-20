package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TFieldInset;
import org.tedros.fx.annotation.layout.TGridPane.TMargin;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TGridPaneMarginParser extends TAnnotationParser<TMargin, GridPane> {


	@Override
	public void parse(TMargin annotation, GridPane object, String... byPass) throws Exception {
		
		if(annotation.values().length>0){
			String curField = super.getComponentDescriptor().getFieldDescriptor().getFieldName();
			TFieldInset[] arr = annotation.values();
			for (TFieldInset tFieldInset : arr) {
				String field = tFieldInset.field();
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
						 
				GridPane.setMargin(node, (Insets) TTypeAnalyserParserDelegate.parse(tFieldInset.insets(), getComponentDescriptor()));		
			}
			
		}
	}
	
}
