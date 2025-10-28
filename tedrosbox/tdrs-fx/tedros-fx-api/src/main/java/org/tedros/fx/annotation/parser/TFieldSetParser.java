package org.tedros.fx.annotation.parser;

import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TFieldSet;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.scene.Node;


/**
 * <pre>
 * The {@link TFieldSet} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link org.tedros.fx.layout.TFieldSet} component.
 * </pre>
 * @author Davis Gordon
 * */
public class TFieldSetParser extends TAnnotationParser<TFieldSet, org.tedros.fx.layout.TFieldSet> {

	
	@Override
	public void parse(TFieldSet annotation, org.tedros.fx.layout.TFieldSet object, String... byPass) throws Exception {
		
		object.tAddLegend(iEngine.getString(annotation.legend()));

		for(String field: annotation.fields()) 
			addNode(annotation, object, field);
		
		super.parse(annotation, object, "layoutType", "fields", "legend", "skipAnnotatedField", "mode" );
	}
	
	private void addNode(TFieldSet annotation, final org.tedros.fx.layout.TFieldSet object, String field) {
		ITFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor();
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
