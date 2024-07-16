/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.form.TComponentReaderBuilder;
import org.tedros.fx.html.THtmlFieldSetGenerator;
import org.tedros.fx.layout.TFieldSet;
import org.tedros.fx.reader.THtmlReader;
import org.tedros.util.TLoggerUtil;

import javafx.scene.Node;


/**
 * <pre>
 * The {@link TFieldSet} builder used in {@link org.tedros.fx.annotation.layout.TFieldSet} annotation.
 * </pre>
 * @see {@link org.tedros.fx.annotation.layout.TFieldSet}, {@link TFieldSet}
 * @author Davis Gordon
 */
public class TFieldSetBuilder 
extends TBuilder 
implements ITLayoutBuilder<TFieldSet> {
	
	@Override
	public TFieldSet build(Annotation annotation) throws Exception {
		org.tedros.fx.annotation.layout.TFieldSet tAnnotation = (org.tedros.fx.annotation.layout.TFieldSet) annotation;
		TFieldSet node = new TFieldSet(tAnnotation.layoutType());
		callParser(tAnnotation, node);
		return node;
	}

	@Override
	public THtmlReader build(Annotation annotation, THtmlReader tHtmlReader)
			throws Exception {
		org.tedros.fx.annotation.layout.TFieldSet tAnnotation = (org.tedros.fx.annotation.layout.TFieldSet) annotation;
		
		THtmlFieldSetGenerator tHtmlFieldSetGenerator = new THtmlFieldSetGenerator(tAnnotation.layoutType());
		tHtmlFieldSetGenerator.setLegend(iEngine.getString(tAnnotation.legend()));
		for(String field: tAnnotation.fields()){
			Node node = null;
			if(tHtmlReader!=null && getComponentDescriptor().getFieldDescriptor().getFieldName().equals(field)){
				node = tHtmlReader;
			}else{
				final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), field);
				node = TComponentReaderBuilder.getField(descriptor);
			}
			
			if(node==null)
				continue;
			
			if(!(node instanceof THtmlReader)){
				TLoggerUtil.warn(getClass(), "The field "+field+" in "+getComponentDescriptor().getModelView().getClass().getName()
						+"  must be annotated with @TReaderHtml()");
			}else{
				THtmlReader fieldHtmlReader = (THtmlReader) node;
				tHtmlFieldSetGenerator.addContent(fieldHtmlReader.getText());
			}
		}
		
		
		return new THtmlReader(tHtmlFieldSetGenerator.getHtml());
	}
	
	
	
}
