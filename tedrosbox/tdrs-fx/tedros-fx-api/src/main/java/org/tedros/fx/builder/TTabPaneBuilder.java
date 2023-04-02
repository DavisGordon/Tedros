/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.form.TControlLayoutReaderBuilder;
import org.tedros.fx.html.THtmlLayoutGenerator;
import org.tedros.fx.reader.THtmlReader;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */

public class TTabPaneBuilder 
extends TBuilder
implements ITLayoutBuilder<TabPane> {
	
	public TabPane build(final Annotation annotation) throws Exception {
		final TabPane tabPane = new TabPane();
	
		tabPane.sceneProperty().addListener(new ChangeListener<Scene> () {

			@Override
			public void changed(ObservableValue<? extends Scene> arg0, Scene arg1, Scene ne) {
				if(ne!=null) {
					tabPane.sceneProperty().removeListener(this);
					tabPane.layout();
				}
			}
			
		});
		callParser(annotation, tabPane);
		return tabPane;
	}
	
	
	@Override
	public THtmlReader build(Annotation annotation, THtmlReader tHtmlReader)
			throws Exception {
		
		TTabPane tAnnotation = (TTabPane) annotation;
		List<String> fields  = new ArrayList<>();
		
		for (TTab tTab : tAnnotation.tabs()) {
			TContent tContent = tTab.content();
			
			if(!(tContent.detailForm().fields()[0].equals("") && tContent.detailForm().fields().length==1)){
				fields.addAll(Arrays.asList(tContent.detailForm().fields()));
			}
		}
				
		THtmlLayoutGenerator tHtmlLayoutGenerator = new THtmlLayoutGenerator(TLayoutType.VBOX);
		
		for(String field : fields){
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
}
