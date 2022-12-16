package org.tedros.fx.presenter.modal.decorator;

import org.tedros.fx.presenter.dynamic.decorator.TDynaViewActionBaseDecorator;
import org.tedros.fx.presenter.model.TModelView;

import javafx.scene.control.ScrollPane;

/**
 * The decorator of the import file view.
 * @author Davis Gordon
 *
 * @param <M>
 */
@SuppressWarnings("rawtypes")
public class TImportFileModalDecorator<M extends TModelView> 
extends TDynaViewActionBaseDecorator<M> {
	
	@Override
	public void decorate() {
		
		setViewTitle(null);
		buildActionButton(iEngine.getString("#{tedros.fxapi.button.import}"));
		buildCloseButton(null);
		buildModesRadioButton(iEngine.getString("#{tedros.fxapi.radio-button.edit}"), 
				iEngine.getString("#{tedros.fxapi.radio-button.file.specification}"));
		
		// add the mode radio buttons
		addItemInTHeaderHorizontalLayout(gettEditModeRadio(), gettReadModeRadio());
				
		
		ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(getPresenter().getView().gettFormSpace());
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
       // scrollPane.setMinWidth(725);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 20 20 20 20;");
        
		//addItemInTTopContent(webView);
		addItemInTCenterContent(scrollPane);
		// add the buttons at the header tool bar
		addItemInTHeaderToolBar(gettActionButton(), gettCloseButton());
		
	}


}
