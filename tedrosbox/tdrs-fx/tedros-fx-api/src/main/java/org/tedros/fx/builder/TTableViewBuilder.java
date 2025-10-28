/**
 * 
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;

import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.collections.ITObservableList;

import javafx.scene.control.TableView;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TTableViewBuilder extends TBuilder implements
	ITControlBuilder<TableView, ITObservableList> {

	/* (non-Javadoc)
	 * @see org.tedros.fx.builder.ITLayoutBuilder#build(java.lang.annotation.Annotation, org.tedros.fx.form.TFieldBox)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TableView build(Annotation annotation, ITObservableList observableList) throws Exception {
		
		TTableView tAnnotation = (TTableView) annotation;
		TableView node = new TableView();
		node.setItems(observableList);
		callParser(tAnnotation, node);
		return node;
	}

}
