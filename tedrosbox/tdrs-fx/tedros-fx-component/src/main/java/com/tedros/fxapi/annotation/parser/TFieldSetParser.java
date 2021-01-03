package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.layout.TFieldSet;
import com.tedros.fxapi.descriptor.TFieldDescriptor;

import javafx.scene.Node;


/**
 * <pre>
 * The {@link TFieldSet} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link com.tedros.fxapi.layout.TFieldSet} component.
 * </pre>
 * @author Davis Gordon
 * */
public class TFieldSetParser extends TAnnotationParser<TFieldSet, com.tedros.fxapi.layout.TFieldSet> {

	
	@Override
	public void parse(TFieldSet annotation, com.tedros.fxapi.layout.TFieldSet object, String... byPass) throws Exception {
		
		object.tAddLegend(iEngine.getString(annotation.legend()));

		for(String field: annotation.fields()) 
			addNode(annotation, object, field);
		
		super.parse(annotation, object, "layoutType", "fields", "legend", "skipAnnotatedField", "mode" );
	}
	
	private void addNode(TFieldSet annotation, final com.tedros.fxapi.layout.TFieldSet object, String field) {
		TFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor();
		if(fd.hasParent())
			return;
		Node node = null;
		if(fd.getFieldName().equals(field)) {
			if(annotation.skipAnnotatedField())
				return;
			node = fd.getComponent();
		}else {
			fd = getComponentDescriptor().getFieldDescriptor(field);
			fd.setHasParent(true);
			 node = fd.hasLayout() 
					 ? fd.getLayout()
							 : fd.getComponent();
		}
		if(node!=null && !object.getChildren().contains(node))
			object.tAddContent(node);
	}
}
