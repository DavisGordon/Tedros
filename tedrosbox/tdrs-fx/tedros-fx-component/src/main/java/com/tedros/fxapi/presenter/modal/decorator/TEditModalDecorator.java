package com.tedros.fxapi.presenter.modal.decorator;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import com.tedros.core.control.TProgressIndicator;
import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TEditModalPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.control.TButton;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.ITDynaView;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

@SuppressWarnings("rawtypes")
public class TEditModalDecorator<M extends TEntityModelView> 
extends TDynaViewCrudBaseDecorator<M> {
	
	private VBox 		tListViewLayout;
	private StackPane	tListViewPane;
    private Label 		tListViewTitle;
    private ListView<M> tListView;
    private TProgressIndicator tListViewProgressIndicator;
    

	private Button tCloseButton;
    
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
		TEditModalPresenter tAnnotation = getPresenter().getModelViewClass().getAnnotation(TEditModalPresenter.class);
		if(tAnnotation!=null){
			listViewMaxWidth = tAnnotation.listViewMaxWidth();
			listViewMinWidth = tAnnotation.listViewMinWidth();
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
		
		
		tListViewPane = new StackPane();
		tListViewPane.getStyleClass().add("t-panel-background-color");
		// build the list view box
		tListViewLayout = new VBox();
		tListViewLayout.maxWidth(listViewMaxWidth+2);
		tListViewPane.getChildren().add(tListViewLayout);
		tListViewProgressIndicator = new TProgressIndicator(tListViewPane);
		tListViewProgressIndicator.setMediumLogo();
		tListViewLayout.getChildren().addAll(Arrays.asList(tListViewTitle, tListView));

		VBox.setVgrow(tListView, Priority.ALWAYS);
		// add the list view box at the left 
		
		addItemInTLeftContent(tListViewPane);
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
		
		if(tDeco.buildCancelButton()) {
			buildCancelButton(null);
			nodes = ArrayUtils.add(nodes, gettCancelButton());
		}
		
		buildCloseButton(null);
		nodes = ArrayUtils.add(nodes, gettCloseButton());
		
		if(nodes.length>0)
			addItemInTHeaderToolBar(nodes);
		
		
		// set padding at rigth in left content pane
		addPaddingInTLeftContent(0, 4, 0, 0);
	}
	
	public void setSingleMode() {
		Node[] nodes = new Node[0];
		if(gettColapseButton()!=null)
			nodes = ArrayUtils.add(nodes, gettColapseButton());
		if(gettNewButton()!=null)
			nodes = ArrayUtils.add(nodes, gettNewButton());
		if(nodes.length>0) {
			final ITDynaView<M> view = getView();
			view.gettHeaderToolBar().getItems().removeAll(nodes);
		}
		this.hideListContent();
	}
	
	/**
	 * <p>
	 * Build a button for the close action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{closeButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_closeButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildCloseButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tCloseButton = new TButton();
			tCloseButton.setText(iEngine.getString(tPresenter==null 
							? TAnnotationDefaultValue.TVIEW_closeButtonText 
									: tPresenter.decorator().closeButtonText()));
			tCloseButton.setId("t-button");
		}else {
			tCloseButton = new TButton();
			tCloseButton.setText(iEngine.getString(text));
			tCloseButton.setId("t-button");
		}
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
		return view.gettLeftContent().getChildren().contains(tListViewPane);
    }
    
	public void hideListContent() {
		final ITDynaView<M> view = getView();
		view.gettLeftContent().getChildren().remove(tListViewPane);
	}
	
	public void showListContent() {
		addItemInTLeftContent(tListViewPane);
		
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
	 * @return the tListViewProgressIndicator
	 */
	public TProgressIndicator gettListViewProgressIndicator() {
		return tListViewProgressIndicator;
	}

	/**
	 * @return the tCloseButton
	 */
	public Button gettCloseButton() {
		return tCloseButton;
	}

	/**
	 * @param tCloseButton the tCloseButton to set
	 */
	public void settCloseButton(Button tCloseButton) {
		this.tCloseButton = tCloseButton;
	}

}
