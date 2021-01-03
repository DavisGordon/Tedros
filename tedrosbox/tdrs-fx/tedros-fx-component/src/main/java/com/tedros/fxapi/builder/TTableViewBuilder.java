/**
 * 
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.scene.control.TableView;

import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.collections.ITObservableList;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TTableViewBuilder extends TBuilder implements
	ITControlBuilder<TableView, ITObservableList> {

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.builder.ITLayoutBuilder#build(java.lang.annotation.Annotation, com.tedros.fxapi.form.TFieldBox)
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
