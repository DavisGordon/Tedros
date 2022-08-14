package org.tedros.fx.annotation.parser;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.annotation.layout.TMargin;
import org.tedros.fx.annotation.layout.TVBoxMargin;

public class TVBoxMarginParser extends TAnnotationParser<TVBoxMargin, VBox> {


	@Override
	public void parse(TVBoxMargin annotation, VBox object, String... byPass) throws Exception {
		
		if(annotation.margin().length>0){
			TMargin[] arr = annotation.margin();
			for (TMargin tMargin : arr) {
				String field = tMargin.field();
				if(StringUtils.isBlank(field))
					continue;
				if(getComponentDescriptor().getComponents().containsKey(field)){
					Node node = getComponentDescriptor().getComponents().get(field);
					VBox.setMargin(node, (Insets) TTypeAnalyserParserDelegate.parse(tMargin.insets(), getComponentDescriptor()));
				}else{
					System.out.println("[WARNING]" + getClass().getSimpleName()+
							": Component not found for "+field+" field, maybe they didnt loaded yet.");
				}
				/*if(getComponentDescriptor().getFieldBoxMap().containsKey(tMargin.field())){
					Node node = getComponentDescriptor().getFieldBoxMap().get(tMargin.field());
					
				}else{
					System.out.println("Warning: TVBoxMarginParser cant find the fieldBox for the field "+tMargin.field()+" at fieldBox list, maybe they was not builded yet!");
				}*/
					
			}
			
		}
	}
	
}
