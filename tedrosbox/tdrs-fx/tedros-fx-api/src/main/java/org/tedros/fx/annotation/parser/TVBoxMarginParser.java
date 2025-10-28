package org.tedros.fx.annotation.parser;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TFieldInset;
import org.tedros.fx.annotation.layout.TVBox.TMargin;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.annotation.parser.engine.TTypeAnalyserParserDelegate;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class TVBoxMarginParser extends TAnnotationParser<TMargin, VBox> {


	@Override
	public void parse(TMargin annotation, VBox object, String... byPass) throws Exception {
		
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
						 
				VBox.setMargin(node, (Insets) TTypeAnalyserParserDelegate.parse(tFieldInset.insets(), getComponentDescriptor()));		
			
			}
			
		}
	}
	
}
