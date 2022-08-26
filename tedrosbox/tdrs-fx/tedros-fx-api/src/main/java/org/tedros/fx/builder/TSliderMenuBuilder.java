/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.form.TControlLayoutReaderBuilder;
import org.tedros.fx.html.THtmlLayoutGenerator;
import org.tedros.fx.layout.TSliderMenu;
import org.tedros.fx.reader.THtmlReader;

import javafx.scene.Node;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TSliderMenuBuilder 
extends TBuilder 
implements ITLayoutBuilder<TSliderMenu> {
	

	@Override
	public TSliderMenu build(Annotation annotation) throws Exception {
		org.tedros.fx.annotation.layout.TSliderMenu ann = (org.tedros.fx.annotation.layout.TSliderMenu) annotation;
		Node c = this.getComponent(ann.content());
		Node m = this.getComponent(ann.menu());
		TSliderMenu node = new TSliderMenu(c, ann.menuWidth());
		node.settMenuContent(m);
		node.settMenuVisible(true);
		node.settMenuOpened(ann.menuExpanded());
		node.autosize();
		callParser(ann, node);
		return node;
	}

	@Override
	public THtmlReader build(Annotation annotation, THtmlReader tHtmlReader)
			throws Exception {
		
		org.tedros.fx.annotation.layout.TSliderMenu ann = (org.tedros.fx.annotation.layout.TSliderMenu) annotation;
		THtmlLayoutGenerator tHtmlLayoutGenerator = new THtmlLayoutGenerator(TLayoutType.HBOX);
		
		for(String field : new String[] {ann.menu(), ann.content()}){
			Node node = null;
			if(tHtmlReader!=null && getComponentDescriptor().getFieldDescriptor().getFieldName().equals(field)){
				node = tHtmlReader;
			}else{
				final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), field);
				node = TControlLayoutReaderBuilder.getField(descriptor);
			}
			
			if(node==null)
				continue;
			
			if(!(node instanceof THtmlReader)){
				System.err.println("TWarning from "+this.getClass().getSimpleName()
						+": The field "+field+" in "+getComponentDescriptor().getModelView().getClass().getName()
						+"  must be annotated with @TReaderHtml()");
			}else{
				THtmlReader fieldHtmlReader = (THtmlReader) node;
				tHtmlLayoutGenerator.addContent(fieldHtmlReader.getText());
			}
		}
		
		
		
		return new THtmlReader(tHtmlLayoutGenerator.getHtml());
	}
	
	private Node getComponent(String field) {
		ITFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor();
		if(fd.hasParent())
			return null;
		Node node = null;
		if(fd.getFieldName().equals(field)) {
			node = fd.getComponent();
		}else {
			fd = getComponentDescriptor().getFieldDescriptor(field);
			fd.setHasParent(true);
			 node = fd.hasLayout() 
					 ? fd.getLayout()
							 : fd.getComponent();
		}
		return node;
	}
	
}
