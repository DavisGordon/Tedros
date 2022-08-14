package org.tedros.fx.presenter.modal.decorator;

import org.tedros.fx.annotation.presenter.TSelectionModalPresenter;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewSelectionBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.ITDynaView;
import org.tedros.fx.presenter.model.TModelView;

import javafx.scene.control.Accordion;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

@SuppressWarnings("rawtypes")
public class TSelectionModalDecorator<M extends TModelView> 
extends TDynaViewSelectionBaseDecorator<M> {
	
	private VBox box;
	private Accordion tAccordion;
	private TitledPane tFilterTiTlePane;
	private TitledPane tResultTitlePane;
	
	 public void decorate() {
		
		
		// get the view
		final ITDynaView<M> view = getPresenter().getView();
		
		TSelectionModalPresenter ann = super.getPresenter().getModelViewClass().getAnnotation(TSelectionModalPresenter.class);
		super.listViewMaxWidth = ann.listViewMaxWidth();
		super.listViewMinWidth = ann.listViewMinWidth();
		setViewTitle(null);
		buildListView();
		buildListViewTitle(null);
		buildCleanButton(null);
		buildSearchButton(null);
		buildCancelButton(null);
		buildCloseButton(null);
		buildSelectAllButton(null);
		buildAddButton(null);
		buildPaginator();
		// add the buttons at the header tool bar
		addItemInTHeaderToolBar(gettSearchButton(), gettCleanButton(), 
				gettCancelButton(), gettSelectAllButton(), 
				gettAddButton(), gettCloseButton());
		
		box = new VBox();
		
		tAccordion = new Accordion();
		tFilterTiTlePane = new TitledPane(iEngine.getString("#{tedros.fxapi.label.filter}"), view.gettFormSpace());
		tResultTitlePane = new TitledPane(iEngine.getString("#{tedros.fxapi.label.result}"), box);
		
		tAccordion.getPanes().addAll(tFilterTiTlePane, tResultTitlePane);
		
		VBox listBox = new VBox();
		listBox.getChildren().addAll(super.gettListViewTitle(), super.gettListView());
		listBox.getStyleClass().add("t-panel-background-color");
		VBox.setVgrow(super.gettListView(), Priority.ALWAYS);
		
		super.addPaddingInTLeftContent(5, 15, 5, 15);
		super.addItemInTLeftContent(listBox);
		super.addItemInTCenterContent(tAccordion);
		expandFilterPane();
	}
	
	 public void expandResultPane() {
		 tAccordion.setExpandedPane(tResultTitlePane);
	 }
	 
	 public void expandFilterPane() {
		 tAccordion.setExpandedPane(tFilterTiTlePane);
	 }
	
	 public void setTableView(TableView tableView) {
	    	super.setTableView(tableView);
	    	box.getChildren().addAll(tableView, gettPaginator());
	    }

}
