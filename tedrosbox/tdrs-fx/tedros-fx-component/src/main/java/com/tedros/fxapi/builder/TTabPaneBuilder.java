/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.annotation.Annotation;

import javafx.scene.control.TabPane;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */

public class TTabPaneBuilder 
extends TBuilder
implements ITControlBuilder<TabPane, Object> {

	private static TTabPaneBuilder instance;
	
	private TTabPaneBuilder(){
		
	}
	
	public static TTabPaneBuilder getInstance(){
		if(instance==null)
			instance = new TTabPaneBuilder();
		return instance;
	}
	
	public TabPane build(final Annotation annotation, final Object attrProperty) throws Exception {
		final TabPane tabPane = new TabPane();
		tabPane.autosize();
		callParser(annotation, tabPane);
		return tabPane;
	}
	
}
