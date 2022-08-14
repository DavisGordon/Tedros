/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.core.style.TStyleResourceValue;
import org.tedros.fx.annotation.layout.TAccordion;
import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.form.TControlLayoutReaderBuilder;
import org.tedros.fx.html.THtmlAccordionGenerator;
import org.tedros.fx.html.THtmlLayoutGenerator;
import org.tedros.fx.reader.THtmlReader;

import javafx.scene.Node;
import javafx.scene.control.Accordion;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TAccordionBuilder 
extends TBuilder 
implements ITLayoutBuilder<Accordion> {
	

	@Override
	public Accordion build(Annotation annotation) throws Exception {
		TAccordion tAnnotation = (TAccordion) annotation;
		Accordion node = new Accordion();
		node.autosize();
		callParser(tAnnotation, node);
		return node;
	}

	@Override
	public THtmlReader build(Annotation annotation, THtmlReader tHtmlReader)
			throws Exception {
		
		
		TAccordion tAnnotation = (TAccordion) annotation;
		
		THtmlAccordionGenerator htmlAccordion = new THtmlAccordionGenerator();		
		
		htmlAccordion.setAccordionColor(TStyleResourceValue.PANEL_BACKGROUND_COLOR.customStyle());
		htmlAccordion.setContentColor(TStyleResourceValue.READER_BACKGROUND_COLOR.customStyle());
		
		htmlAccordion.setAccordionTextColor(TStyleResourceValue.PANEL_TEXT_COLOR.customStyle());
		htmlAccordion.setAccordionTextSize(TStyleResourceValue.PANEL_TEXT_SIZE.customStyle());
		htmlAccordion.setContentTextColor(TStyleResourceValue.READER_TEXT_COLOR.customStyle());
		htmlAccordion.setAccordionTextSize(TStyleResourceValue.READER_TEXT_SIZE.customStyle());
		
		
		for (TTitledPane tTitledPane : tAnnotation.panes()) {
			if(tTitledPane.fields().length>0){
				final TLayoutType layout = tTitledPane.layoutType();
				
				THtmlLayoutGenerator tHtmlLayoutGenerator = new THtmlLayoutGenerator(layout);
				
				for(String field: tTitledPane.fields()){
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
				
				htmlAccordion.addSection(tTitledPane.text(), tHtmlLayoutGenerator.getHtml());
			}
		}
		
		
		return new THtmlReader(htmlAccordion.getHtml());
	}
	
	
	
}
