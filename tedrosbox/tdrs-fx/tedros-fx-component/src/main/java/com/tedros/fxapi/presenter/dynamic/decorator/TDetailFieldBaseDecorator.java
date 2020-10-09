package com.tedros.fxapi.presenter.dynamic.decorator;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;

@SuppressWarnings("rawtypes")
public abstract class TDetailFieldBaseDecorator<M extends TModelView> 
extends TDynaViewSimpleBaseDecorator<M> {
	
	private Button tCleanButton;
	private Button tAddButton;
	private Button tRemoveButton;
	
	private TableView<M> tTableView;
	
    
    @Override
    public void setPresenter(TDynaPresenter<M> presenter) {
    	super.setPresenter(presenter);
    }
	
	/**
	 * <p>
	 * Build a button for the Remove action.<br><br>
	 * 
	 * If the parameter was null this will use the 
	 * default string (TAnnotationDefaultValue.TVIEW_removeButtonText).<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildRemoveButton(String text) {
		if(text==null){
			tRemoveButton = new Button();
			tRemoveButton.setText(iEngine.getString(TAnnotationDefaultValue.TVIEW_removeButtonText));
			tRemoveButton.setId("t-button");
		}else {
			tRemoveButton = new Button();
			tRemoveButton.setText(iEngine.getString(text));
			tRemoveButton.setId("t-button");
		}
	}
	
	/**
	 * <p>
	 * Build a button for the Add action.<br><br>
	 * 
	 * If the parameter was null this will use the 
	 * default string (TAnnotationDefaultValue.TVIEW_addButtonText).<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildAddButton(String text) {
		if(text==null){
			tAddButton = new Button();
			tAddButton.setText(iEngine.getString(TAnnotationDefaultValue.TVIEW_addButtonText));
			tAddButton.setId("t-button");
		}else {
			tAddButton = new Button();
			tAddButton.setText(iEngine.getString(text));
			tAddButton.setId("t-button");
		}
	}
	
	/**
	 * <p>
	 * Build a button for the clean action.<br><br>
	 * 
	 * If the parameter was null this will use the
	 * default string (TAnnotationDefaultValue.TVIEW_cleanButtonText).<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildCleanButton(String text) {
		if(text==null){
			tCleanButton = new Button();
			tCleanButton.setText(iEngine.getString(TAnnotationDefaultValue.TVIEW_cleanButtonText));
			tCleanButton.setId("t-button");
		}else {
			tCleanButton = new Button();
			tCleanButton.setText(iEngine.getString(text));
			tCleanButton.setId("t-button");
		}
		
	}

	/**
	 * @return the tCleanButton
	 */
	public Button gettCleanButton() {
		return tCleanButton;
	}


	/**
	 * @param tCleanButton the tCleanButton to set
	 */
	public void settCleanButton(Button tCleanButton) {
		this.tCleanButton = tCleanButton;
	}
	/**
	 * @return the tTableView
	 */
	public TableView<M> gettTableView() {
		return tTableView;
	}

	/**
	 * @return the tAddButton
	 */
	public Button gettAddButton() {
		return tAddButton;
	}

	/**
	 * @param tAddButton the tAddButton to set
	 */
	public void settAddButton(Button tAddButton) {
		this.tAddButton = tAddButton;
	}

	/**
	 * @return the tRemoveButton
	 */
	public Button gettRemoveButton() {
		return tRemoveButton;
	}

	/**
	 * @param tRemoveButton the tRemoveButton to set
	 */
	public void settRemoveButton(Button tRemoveButton) {
		this.tRemoveButton = tRemoveButton;
	}

	/**
	 * @param tTableView the tTableView to set
	 */
	public void settTableView(TableView<M> tTableView) {
		this.tTableView = tTableView;
	}

	

	// getters and setters
	
	

	
}
