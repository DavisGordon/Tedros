package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Stream;

import org.tedros.fx.annotation.layout.TGridPane;
import org.tedros.fx.annotation.layout.TGridPane.TField;
import org.tedros.fx.annotation.layout.TGridPane.TFieldBoolean;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.util.TDescriptorUtil;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TGridPaneParser extends TAnnotationParser<Annotation, GridPane> {

	@Override
	public void parse(Annotation annotation, GridPane object, String... byPass) throws Exception {
		
		TGridPane ann = (TGridPane) annotation;
		
		if(ann.add().length>0) {
			Stream<TFieldBoolean> fws = (ann.fillWidth().length>0) 
					? Arrays.stream(ann.fillWidth())
							: null;
			
			Stream<TFieldBoolean> fhs = (ann.fillHeight().length>0) 
					? Arrays.stream(ann.fillHeight())
							: null;
			
			for(TField f : ann.add()) {
				Node node = TDescriptorUtil.getComponent(f.name(), super.getComponentDescriptor());
				
				if(node!=null) {
					
					object.add(node, f.columnIndex(), f.rowIndex(), f.columnspan(), f.rowspan());
					
					if(fws!=null) {
						fws.filter(p->{
							return p.field().equals(f.name());
						})
						.findFirst()
						.ifPresent(c->{
							GridPane.setFillWidth(node, c.value());
						});
					}
					if(fhs!=null) {
						fhs.filter(p->{
							return p.field().equals(f.name());
						})
						.findFirst()
						.ifPresent(c->{
							GridPane.setFillHeight(node, c.value());
						});
					}
				}else
					throwException(f.name());
			}
		}
		super.parse(annotation, object, "fillWidth", "fillHeight", "add");
	}


	/**
	 * @param ann
	 */
	private void throwException(String field) {
		throw new RuntimeException("The field "+field+" hasn't configured with a componet to be used."
				+ getComponentDescriptor().getModelView().getClass().getSimpleName());
	}
	
	
}
