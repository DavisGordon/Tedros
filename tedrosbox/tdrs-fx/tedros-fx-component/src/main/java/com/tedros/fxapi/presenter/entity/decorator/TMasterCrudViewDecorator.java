package com.tedros.fxapi.presenter.entity.decorator;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.ITDynaView;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.paginator.TPaginator;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

@SuppressWarnings("rawtypes")
public class TMasterCrudViewDecorator<M extends TEntityModelView> 
extends TDynaViewCrudBaseDecorator<M> {
	
	private VBox 		tListViewLayout;
    private Label 		tListViewTitle;
    private ListView<M> tListView;
    
    private Accordion tPaginatorAccordion;
    private TPaginator tPaginator;
    
    private double listViewMaxWidth = 250;
    private double listViewMinWidth = 250;
    
    private TPresenter tPresenter;
  
    public void decorate() {
    	tPresenter = getPresenter().getPresenterAnnotation();
		configFormSpace();
		configViewTitle();
		configBreadcrumBar();
		configAllButtons();
		configListView();
	}

	protected void configListView() {
		
		
		// get the list view settings
		TListViewPresenter tAnnotation = getPresenter().getModelViewClass().getAnnotation(TListViewPresenter.class);
		if(tAnnotation!=null){
			listViewMaxWidth = tAnnotation.listViewMaxWidth();
			listViewMinWidth = tAnnotation.listViewMinWidth();
			if(tAnnotation.paginator().show()) {
				tPaginator = new TPaginator(tAnnotation.paginator().showSearchField(), tAnnotation.paginator().showOrderBy());
				if(tAnnotation.paginator().showOrderBy())
					for(com.tedros.fxapi.annotation.view.TOption o : tAnnotation.paginator().orderBy())
						tPaginator.addOrderByOption(o.text(), o.value());
			}
			
		}
		// build the list view
		tListView = new ListView<>();
		tListView.setCache(false);
		tListView.autosize();
		tListView.setMaxWidth(listViewMaxWidth);
		tListView.setMinWidth(listViewMinWidth);
		
		// build the label for the list view
		tListViewTitle = new Label(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_listTitle : tPresenter.decorator().listTitle()));
		tListViewTitle.setId("t-title-label");
		tListViewTitle.maxWidth(listViewMaxWidth);
		
		
		
		// build the list view box
		tListViewLayout = new VBox();
		tListViewLayout.maxWidth(listViewMaxWidth+2);
		
		if(tPaginator!=null) {
			
			tPaginatorAccordion = new Accordion();
			tPaginatorAccordion.autosize();
			
			TitledPane tp = new TitledPane(iEngine.getString("#{tedros.fxapi.label.pagination}"), tPaginator);
			tPaginatorAccordion.getPanes().add(tp);
			tPaginator.setMaxWidth(listViewMaxWidth);
			tPaginator.setMinWidth(listViewMinWidth);
			tListViewLayout.getChildren().addAll(Arrays.asList(tListViewTitle, tListView, tPaginatorAccordion));
			
			//VBox.setVgrow(tPaginator, Priority.ALWAYS);
		}else
			tListViewLayout.getChildren().addAll(Arrays.asList(tListViewTitle, tListView));

		
		
		VBox.setVgrow(tListView, Priority.ALWAYS);
		// add the list view box at the left 
		
		addItemInTLeftContent(tListViewLayout);
	}

	protected void configAllButtons() {
		TDecorator tDeco = tPresenter.decorator();
		Node[] nodes = new Node[0];
		
		if(tDeco.buildCollapseButton()) {
			buildColapseButton(null);
			nodes = ArrayUtils.add(nodes, gettColapseButton());
		}
		if(tDeco.buildImportButton()) {
			buildImportButton(null);
			nodes = ArrayUtils.add(nodes, gettImportButton());
		}
		
		if(tDeco.buildNewButton()) {
			buildNewButton(null);
			nodes = ArrayUtils.add(nodes, gettNewButton());
		}
		
		if(tDeco.buildDeleteButton()) {
			buildDeleteButton(null);
			nodes = ArrayUtils.add(nodes, gettDeleteButton());
		}
		
		if(tDeco.buildSaveButton()) {
			buildSaveButton(null);
			nodes = ArrayUtils.add(nodes, gettSaveButton());
		}
		
		if(tDeco.buildPrintButton()) {
			buildPrintButton(null);
			nodes = ArrayUtils.add(nodes, gettPrintButton());
		}
		
		if(tDeco.buildCancelButton()) {
			buildCancelButton(null);
			nodes = ArrayUtils.add(nodes, gettCancelButton());
		}
		
		if(nodes.length>0)
			addItemInTHeaderToolBar(nodes);
		
		// add the mode radio buttons
		if(tDeco.buildModesRadioButton()) {
			buildModesRadioButton(null, null);
			addItemInTHeaderHorizontalLayout(gettEditModeRadio(), gettReadModeRadio());
		}
		
		
		// set padding at rigth in left content pane
		addPaddingInTLeftContent(0, 4, 0, 0);
	}

	protected void configBreadcrumBar() {
		final TForm tForm = this.getPresenter().getFormAnnotation();
		setShowBreadcrumBar((tForm!=null) ? tForm.showBreadcrumBar() : false);
		
		if(isShowBreadcrumBar())
			buildTBreadcrumbForm();
	}

	protected void configViewTitle() {
		setViewTitle(null);
	}

	protected void configFormSpace() {
		addItemInTCenterContent(getPresenter().getView().gettFormSpace());
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
	 * @return the tPaginatorAccordion
	 */
	public Accordion gettPaginatorAccordion() {
		return tPaginatorAccordion;
	}

}
