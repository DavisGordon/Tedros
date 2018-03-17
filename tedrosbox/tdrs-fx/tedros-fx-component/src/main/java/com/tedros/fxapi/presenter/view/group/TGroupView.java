package com.tedros.fxapi.presenter.view.group;


import java.net.URL;
import java.util.List;

import com.tedros.core.ITModule;
import com.tedros.core.presenter.ITGroupPresenter;
import com.tedros.core.presenter.view.ITGroupView;
import com.tedros.core.presenter.view.ITGroupViewItem;
import com.tedros.fxapi.presenter.view.TView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

@SuppressWarnings("rawtypes")
public class TGroupView<P extends ITGroupPresenter> extends TView<P> implements ITGroupView<P> {
		
	private static final String FXML = "ViewsGroupView.fxml";
    
    @FXML private BorderPane tContentPane;
    @FXML private Label tGroupTitle;
    @FXML private ToolBar tGroupToolbar;
    
    private StackPane tFormSpace;
    
    
	@SuppressWarnings("unchecked")
	public TGroupView(ITModule module, String groupTitle, ObservableList<ITGroupViewItem> itens) {
		super((P) new TGroupPresenter(module, groupTitle, itens), getURL());
		
	}
	
	@SuppressWarnings("unchecked")
	public TGroupView(ITModule module, String groupTitle, ITGroupViewItem... itens) {
		super((P) new TGroupPresenter(module, groupTitle, FXCollections.observableArrayList(itens)), getURL());
	}
	
	@SuppressWarnings("unchecked")
	public TGroupView(ITModule module, String groupTitle, List<ITGroupViewItem> itens) {
		super((P) new TGroupPresenter(module, groupTitle, FXCollections.observableArrayList(itens)), getURL());
	}
	
	@SuppressWarnings("unchecked")
	public TGroupView(ITModule module, String groupTitle, ObservableList<ITGroupViewItem> itens, URL fxmlURL ) {
		super((P) new TGroupPresenter(module, groupTitle, itens), fxmlURL);
	}
	
	private static URL getURL() {
		return TGroupView.class.getResource(FXML);
	}
	

	public BorderPane gettContentPane() {
		return tContentPane;
	}



	public void settContentPane(BorderPane tContentPane) {
		this.tContentPane = tContentPane;
	}



	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupView#gettGroupTitle()
	 */
	@Override
	public Label gettGroupTitle() {
		return tGroupTitle;
	}



	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupView#settGroupTitle(javafx.scene.control.Label)
	 */
	@Override
	public void settGroupTitle(Label tGroupTitle) {
		this.tGroupTitle = tGroupTitle;
	}



	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupView#gettGroupToolbar()
	 */
	@Override
	public ToolBar gettGroupToolbar() {
		return tGroupToolbar;
	}



	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.view.group.ITGroupView#settGroupToolbar(javafx.scene.control.ToolBar)
	 */
	@Override
	public void settGroupToolbar(ToolBar tGroupToolbar) {
		this.tGroupToolbar = tGroupToolbar;
	}


	@Override
	public StackPane gettFormSpace() {
		if(tFormSpace == null)
			tFormSpace = new StackPane();
		return tFormSpace;
	}
	
}
