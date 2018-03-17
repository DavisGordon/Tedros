package com.tedros.fxapi.presenter.entity.decorator;

import java.util.Arrays;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.view.TEntityCrudViewWithListView;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;

@SuppressWarnings("rawtypes")
public class TMainCrudViewWithListViewDecorator<M extends TEntityModelView> 
extends TDynaViewCrudBaseDecorator<M> {
	
	private VBox 		tListViewLayout;
    private Label 		tListViewTitle;
    private ListView<M> tListView;
    
    private double hideWidth = 4;
    private double listViewMaxWidth = 250;
    private double listViewMinWidth = 250;
  
    @SuppressWarnings("unchecked")
	public void decorate() {
		
		// get the model view annotation array 
		final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
		
		// get the view
		final TDynaView<M> view = getPresenter().getView();
		
		addItemInTCenterContent(view.gettFormSpace());
		setViewTitle(null);
		
		// get the list view settings
		TEntityCrudViewWithListView tAnnotation = getPresenter().getModelViewClass().getAnnotation(TEntityCrudViewWithListView.class);
		if(tAnnotation!=null){
			listViewMaxWidth = tAnnotation.listViewMaxWidth();
			listViewMinWidth = tAnnotation.listViewMinWidth();
		}
		
		final TForm tForm = this.getPresenter().getFormAnnotation();
		setShowBreadcrumBar((tForm!=null) ? tForm.showBreadcrumBar() : false);
		
		if(isShowBreadcrumBar())
			buildTBreadcrumbForm();
		
		buildColapseButton(null);
		buildNewButton(null);
		buildDeleteButton(null);
		buildSaveButton(null);
		buildCancelButton(null);
		buildModesRadioButton(null, null);
		
		// add the buttons at the header tool bar
		addItemInTHeaderToolBar(gettColapseButton(), gettNewButton(), gettDeleteButton(), gettSaveButton(), gettCancelButton());
		
		// add the mode radio buttons
		addItemInTHeaderHorizontalLayout(gettEditModeRadio(), gettReadModeRadio());
		
		// set padding at rigth in left content pane
		addPaddingInTLeftContent(0, 4, 0, 0);
		
		
		// build the list view
		tListView = new ListView<>();
		tListView.setCache(false);
		tListView.autosize();
		tListView.setMaxWidth(listViewMaxWidth);
		tListView.setMinWidth(listViewMinWidth);
		
		// build the label for the list view
		tListViewTitle = LabelBuilder.create()
				.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_listTitle : tPresenter.decorator().listTitle()))
				.id("t-title-label")
				.maxWidth(listViewMaxWidth)
				.build();
		
		// build the list view box
		tListViewLayout = VBoxBuilder.create()
				.children(Arrays.asList(tListViewTitle, tListView))
				.maxWidth(listViewMaxWidth+2)
				.build();
		
		VBox.setVgrow(tListView, Priority.ALWAYS);
		// add the list view box at the left 
		
		addItemInTLeftContent(tListViewLayout);
		
	}
	
	public void hideListContent() {
		final StackPane pane = (StackPane) ((TDynaView) getView()).gettContentLayout().getLeft();
		((TDynaView) getView()).gettContentLayout().managedProperty().bind(pane.visibleProperty());
		tListViewTitle.setMaxWidth(hideWidth);
		tListView.setMinWidth(hideWidth);
		tListView.setMaxWidth(hideWidth);
		pane.setVisible(false);
		tListView.layout();
	}
	
	public void showListContent() {
		final StackPane pane = (StackPane) ((TDynaView) getView()).gettContentLayout().getLeft();
		((TDynaView) getView()).gettContentLayout().managedProperty().bind(pane.visibleProperty());
		tListViewTitle.setMaxWidth(listViewMaxWidth);
		tListView.setMinWidth(listViewMinWidth);
		tListView.setMaxWidth(listViewMaxWidth);
		pane.setVisible(true);
		tListView.layout();
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
