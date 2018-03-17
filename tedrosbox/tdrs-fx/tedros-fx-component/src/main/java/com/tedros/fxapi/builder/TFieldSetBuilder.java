/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TComponentBuilder;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.html.THtmlFieldSetGenerator;
import com.tedros.fxapi.layout.TFieldSet;
import com.tedros.fxapi.reader.THtmlReader;

import javafx.scene.Node;


/**
 * <pre>
 * The {@link TFieldSet} builder used in {@link com.tedros.fxapi.annotation.layout.TFieldSet} annotation.
 * </pre>
 * @see {@link com.tedros.fxapi.annotation.layout.TFieldSet}, {@link TFieldSet}
 * @author Davis Gordon
 */
public class TFieldSetBuilder 
extends TBuilder 
implements ITLayoutBuilder<TFieldSet> {
	
	private static TFieldSetBuilder instance;
	
	private TFieldSetBuilder(){
		
	}
	
	public static TFieldSetBuilder getInstance(){
		if(instance==null)
			instance = new TFieldSetBuilder();
		return instance;
	}

	@Override
	public TFieldSet build(Annotation annotation, TFieldBox fieldBox) throws Exception {
		com.tedros.fxapi.annotation.layout.TFieldSet tAnnotation = (com.tedros.fxapi.annotation.layout.TFieldSet) annotation;
		TFieldSet node = new TFieldSet(tAnnotation.layoutType());
		if(!tAnnotation.skipAnnotatedField())
			node.setUserData(fieldBox);
		callParser(tAnnotation, node);
		return node;
	}

	@Override
	public THtmlReader build(Annotation annotation, THtmlReader tHtmlReader)
			throws Exception {
		com.tedros.fxapi.annotation.layout.TFieldSet tAnnotation = (com.tedros.fxapi.annotation.layout.TFieldSet) annotation;
		
		THtmlFieldSetGenerator tHtmlFieldSetGenerator = new THtmlFieldSetGenerator(tAnnotation.layoutType());
		tHtmlFieldSetGenerator.setLegend(iEngine.getString(tAnnotation.legend()));
		for(String field: tAnnotation.fields()){
			Node node = null;
			if(getComponentDescriptor().getFieldDescriptor().getFieldName().equals(field)){
				node = tHtmlReader;
			}else{
				final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), field);
				node = TComponentBuilder.getField(descriptor);
			}
			
			if(node==null)
				continue;
			
			if(!(node instanceof THtmlReader)){
				System.err.println("TWarning from "+this.getClass().getSimpleName()
						+": The field "+field+" in "+getComponentDescriptor().getModelView().getClass().getName()
						+"  must be annotated with @TReaderHtml()");
			}else{
				THtmlReader fieldHtmlReader = (THtmlReader) node;
				tHtmlFieldSetGenerator.addContent(fieldHtmlReader.getText());
			}
		}
		
		
		return new THtmlReader(tHtmlFieldSetGenerator.getHtml());
	}
	
	
	
}
