package com.tedros.fxapi.presenter.entity.decorator;

import org.apache.commons.lang3.ArrayUtils;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.ITDynaView;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

@SuppressWarnings("rawtypes")
public class TDetailCrudViewDecorator<M extends TEntityModelView> 
extends TDynaViewCrudBaseDecorator<M>
 {
	
	private VBox 		tListViewLayout;
    private Label 		tListViewTitle;
    private ListView<M> tListView;
    
    private double listViewMaxWidth = 250;
    private double listViewMinWidth = 250;
 
	public void decorate() {
		
		// get the model view annotation array 
		final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
		
		final TForm tForm = this.getPresenter().getFormAnnotation();
		setShowBreadcrumBar((tForm!=null) ? tForm.showBreadcrumBar() : false);
		
		// get the views
		final ITDynaView<M> view = getPresenter().getView();
		
		addItemInTCenterContent(view.gettFormSpace());
		setViewTitle(null);
		
		// get the list view settings
		TListViewPresenter tAnnotation = getPresenter().getModelViewClass().getAnnotation(TListViewPresenter.class);
		if(tAnnotation!=null){
			listViewMaxWidth = tAnnotation.listViewMaxWidth();
			listViewMinWidth = tAnnotation.listViewMinWidth();
		}
		
		buildColapseButton(null);
		buildNewButton(null);
		buildDeleteButton(null);
		Node[] nodes = new Node[0];
		nodes = ArrayUtils.addAll(nodes, gettColapseButton(), gettNewButton(), gettDeleteButton());
		if(tForm!=null && tForm.showBreadcrumBar()) {
			buildEditButton(null);
			nodes = ArrayUtils.add(nodes, gettEditButton());
		}
		
		if(tPresenter.decorator().buildPrintButton()) {
			buildPrintButton(tPresenter.decorator().printButtonText());
			nodes = ArrayUtils.add(nodes, gettPrintButton());
		}
		
		addItemInTHeaderToolBar(nodes);
		
		// add the mode radio buttons
		if(tPresenter.decorator().buildModesRadioButton()) {
			buildModesRadioButton(null, null);
			addItemInTHeaderHorizontalLayout(gettEditModeRadio(), gettReadModeRadio());
		}
		
		// set padding at rigth in left content pane
		addPaddingInTLeftContent(0, 4, 0, 0);
		
		// build the list view
		tListView = new ListView<>();
		tListView.setCache(false);
		tListView.autosize();
		tListView.setMaxWidth(listViewMaxWidth);
		tListView.setMinWidth(listViewMinWidth);
		//tListView.setPrefHeight(120);
		
		
		// build the label for the list view
		tListViewTitle = new Label();
		tListViewTitle.setText(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_listTitle : tPresenter.decorator().listTitle()));
		tListViewTitle.setId("t-title-label");
		tListViewTitle.maxWidth(listViewMaxWidth);
		
		// build the list view box
		tListViewLayout = new VBox();
		tListViewLayout.getChildren().addAll(tListViewTitle, tListView);
		tListViewLayout.maxWidth(listViewMaxWidth+2);
		tListViewLayout.getStyleClass().add("t-panel-background-color");
		VBox.setVgrow(tListView, Priority.ALWAYS);
		// add the list view box at the left 
		
		addItemInTLeftContent(tListViewLayout);
		
	}
	
	
	public boolean isListContentVisible() {
    	final ITDynaView<M> view = getView();
		return view.gettLeftContent().getChildren().contains(tListViewLayout);
    }
    
	public void hideListContent() {
		final ITDynaView<M> view = getView();
		view.gettLeftContent().getChildren().remove(tListViewLayout);
	}
	
	public void showListContent() {
		addItemInTLeftContent(tListViewLayout);
		
	}
	
	public VBox gettListViewLayout() {
		return tListViewLayout;
	}

	
	public void settListViewLayout(VBox tListViewLayout) {
		this.tListViewLayout = tListViewLayout;
	}


	public Label gettListViewTitle() {
		return tListViewTitle;
	}

	
	public void settListViewTitle(Label tListViewTitle) {
		this.tListViewTitle = tListViewTitle;
	}
	
	public ListView<M> gettListView() {
		return tListView;
	}

	
	public void settListView(ListView<M> tListView) {
		this.tListView = tListView;
	}
	
	public double getListViewMaxWidth() {
		return listViewMaxWidth;
	}

	public void setListViewMaxWidth(double listViewMaxWidth) {
		this.listViewMaxWidth = listViewMaxWidth;
	}

	public double getListViewMinWidth() {
		return listViewMinWidth;
	}

	public void setListViewMinWidth(double listViewMinWidth) {
		this.listViewMinWidth = listViewMinWidth;
	}

}
