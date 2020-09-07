/**
 * 
 */
package com.covidsemfome.module.acao.decorator;

import com.covidsemfome.module.acao.model.MailingModelView;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;

/**
 * @author Davis Gordon
 *
 */
public class MailingDecorator extends TMainCrudViewWithListViewDecorator<MailingModelView> {

	public void decorate() {
		
    	configFormSpace();
		configViewTitle();
		
		
		buildCancelButton(null);
		buildSaveButton(null);
		
		buildModesRadioButton(null, "Ver email");
		// add the buttons at the header tool bar
		addItemInTHeaderToolBar(gettSaveButton(), gettCancelButton());
		
		// add the mode radio buttons
		addItemInTHeaderHorizontalLayout(gettEditModeRadio(), gettReadModeRadio());
		
		// set padding at rigth in left content pane
		addPaddingInTLeftContent(0, 4, 0, 0);
		configListView();
		
		
	}
	
	public void hideListContent() {
		
	}
	
	public void showListContent() {
		
	}
	

}
