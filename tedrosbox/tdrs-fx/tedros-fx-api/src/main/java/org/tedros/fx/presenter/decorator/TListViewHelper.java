/**
 * 
 */
package org.tedros.fx.presenter.decorator;

import java.util.Arrays;

import org.tedros.core.TLanguage;
import org.tedros.core.control.TProgressIndicator;
import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.paginator.TPaginator;
import org.tedros.server.entity.ITEntity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class TListViewHelper<M extends TEntityModelView<? extends ITEntity>>{

	private VBox 		tListViewLayout;
	private StackPane	tListViewPane;
    private Label 		tListViewTitle;
    private ListView<M> tListView;
    private TProgressIndicator tListViewProgressIndicator;
    
    private Accordion tPaginatorAccordion;
    private TPaginator tPaginator;
    
    private SimpleDoubleProperty listViewMaxWidth = new SimpleDoubleProperty(250);
    private SimpleDoubleProperty listViewMinWidth = new SimpleDoubleProperty(250);
    
	public TListViewHelper(String title, double maxWidth, double minWidth, org.tedros.fx.annotation.view.TPaginator paginator ) {
		
		// build the list view
		tListView = new ListView<>();
		tListView.setCache(false);
		tListView.autosize();
		tListView.maxWidthProperty().bind(listViewMaxWidth);
		tListView.minWidthProperty().bind(listViewMinWidth);
		tListView.setEditable(true);
		tListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		// build the label for the list view
		tListViewTitle = new Label(TLanguage.getInstance().getString(title==null ? TAnnotationDefaultValue.TVIEW_listTitle : title));
		tListViewTitle.setId("t-title-label");
		tListViewTitle.maxWidthProperty().bind(listViewMaxWidth);
		tListViewTitle.minWidthProperty().bind(listViewMinWidth);
		
		tListViewPane = new StackPane();
		tListViewPane.getStyleClass().add("t-panel-background-color");
		tListViewPane.setAlignment(Pos.CENTER);
		//tListViewPane.maxWidthProperty().bind(listViewMaxWidth);
		//tListViewPane.minWidthProperty().bind(listViewMinWidth);
		
		// build the list view box
		tListViewLayout = new VBox(4);
		StackPane.setMargin(tListViewLayout, new Insets(0, 2, 0, 0));
		tListViewPane.getChildren().add(tListViewLayout);
		VBox.setVgrow(tListView, Priority.ALWAYS);
		tListViewLayout.setAlignment(Pos.CENTER);
		tListViewProgressIndicator = new TProgressIndicator(tListViewPane);
		tListViewProgressIndicator.setSmallLogo();
		
		if(paginator!=null){
			if(paginator.show()) {
				tPaginator = new TPaginator(paginator.showSearchField(), paginator.showOrderBy());
				if(paginator.showOrderBy()) {
					for(org.tedros.fx.annotation.view.TOption o : paginator.orderBy())
						tPaginator.addOrderByOption(o.text(), o.value());
					tPaginator.setOrderBy(paginator.orderBy()[0].value());
				}
				tPaginatorAccordion = new Accordion();
				tPaginatorAccordion.autosize();
				//tPaginatorAccordion.getStyleClass().add("t-accordion");
				TitledPane tp = new TitledPane(TLanguage.getInstance().getString("#{tedros.fxapi.label.pagination}"), tPaginator);
				tPaginatorAccordion.getPanes().add(tp);
				tPaginator.maxWidthProperty().bind(listViewMaxWidth);
				tPaginator.minWidthProperty().bind(listViewMinWidth);
				tListViewLayout.getChildren().addAll(Arrays.asList(tListViewTitle, tListView, tPaginatorAccordion));
			}
		}
		
		if(tPaginator==null) 
			tListViewLayout.getChildren().addAll(Arrays.asList(tListViewTitle, tListView));
		
		listViewMaxWidth.setValue(maxWidth);
		listViewMinWidth.setValue(minWidth);
	}

	/**
	 * @return the tListViewLayout
	 */
	public VBox gettListViewLayout() {
		return tListViewLayout;
	}

	/**
	 * @param tListViewLayout the tListViewLayout to set
	 */
	public void settListViewLayout(VBox tListViewLayout) {
		this.tListViewLayout = tListViewLayout;
	}

	/**
	 * @return the tListViewPane
	 */
	public StackPane gettListViewPane() {
		return tListViewPane;
	}

	/**
	 * @param tListViewPane the tListViewPane to set
	 */
	public void settListViewPane(StackPane tListViewPane) {
		this.tListViewPane = tListViewPane;
	}

	/**
	 * @return the tListViewTitle
	 */
	public Label gettListViewTitle() {
		return tListViewTitle;
	}

	/**
	 * @param tListViewTitle the tListViewTitle to set
	 */
	public void settListViewTitle(Label tListViewTitle) {
		this.tListViewTitle = tListViewTitle;
	}

	/**
	 * @return the tListView
	 */
	public ListView<M> gettListView() {
		return tListView;
	}

	/**
	 * @param tListView the tListView to set
	 */
	public void settListView(ListView<M> tListView) {
		this.tListView = tListView;
	}

	/**
	 * @return the tListViewProgressIndicator
	 */
	public TProgressIndicator gettListViewProgressIndicator() {
		return tListViewProgressIndicator;
	}

	/**
	 * @param tListViewProgressIndicator the tListViewProgressIndicator to set
	 */
	public void settListViewProgressIndicator(TProgressIndicator tListViewProgressIndicator) {
		this.tListViewProgressIndicator = tListViewProgressIndicator;
	}

	/**
	 * @return the tPaginatorAccordion
	 */
	public Accordion gettPaginatorAccordion() {
		return tPaginatorAccordion;
	}

	/**
	 * @param tPaginatorAccordion the tPaginatorAccordion to set
	 */
	public void settPaginatorAccordion(Accordion tPaginatorAccordion) {
		this.tPaginatorAccordion = tPaginatorAccordion;
	}

	/**
	 * @return the tPaginator
	 */
	public TPaginator gettPaginator() {
		return tPaginator;
	}

	/**
	 * @param tPaginator the tPaginator to set
	 */
	public void settPaginator(TPaginator tPaginator) {
		this.tPaginator = tPaginator;
	}

	/**
	 * @return the listViewMaxWidth
	 */
	public SimpleDoubleProperty getListViewMaxWidth() {
		return listViewMaxWidth;
	}

	/**
	 * @param listViewMaxWidth the listViewMaxWidth to set
	 */
	public void setListViewMaxWidth(SimpleDoubleProperty listViewMaxWidth) {
		this.listViewMaxWidth = listViewMaxWidth;
	}

	/**
	 * @return the listViewMinWidth
	 */
	public SimpleDoubleProperty getListViewMinWidth() {
		return listViewMinWidth;
	}

	/**
	 * @param listViewMinWidth the listViewMinWidth to set
	 */
	public void setListViewMinWidth(SimpleDoubleProperty listViewMinWidth) {
		this.listViewMinWidth = listViewMinWidth;
	}

}
