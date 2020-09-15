package com.tedros.fxapi.presenter.dynamic.decorator;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.control.TLabel;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.paginator.TPaginator;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;

@SuppressWarnings("rawtypes")
public abstract class TDynaViewSelectionBaseDecorator<M extends TModelView> 
extends TDynaViewSimpleBaseDecorator<M> {
	
	private Button tCleanButton;
	private Button tSearchButton;
	private Button tCancelButton;
	private Button tCloseButton;
	
	private ListView tListView;
	private TLabel tListViewTitle;
	
	private TableView<M> tTableView;
	
	private TPaginator tPaginator;
	private double listViewMaxWidth = 250;
	private double listViewMinWidth = 250;
    
    
    @Override
    public void setPresenter(TDynaPresenter<M> presenter) {
    	super.setPresenter(presenter);
    }
    
    @SuppressWarnings("unchecked")
	public void setTableView(TableView tableView) {
    	tTableView = tableView;
    	
    }
    
    public void buildListView() {
    	// build the list view
		tListView = new ListView<>();
		tListView.setCache(false);
		tListView.autosize();
		tListView.setMaxWidth(listViewMaxWidth);
		tListView.setMinWidth(listViewMinWidth);
		tListView.setTooltip(new Tooltip(TInternationalizationEngine
				.getInstance(null)
				.getString("#{tedros.fxapi.label.double.click.remove}")));
		
    }

	public void buildListViewTitle(String text) {
		// build the label for the list view
		tListViewTitle = new TLabel(iEngine.getString(text==null ? TAnnotationDefaultValue.TVIEW_selected : text));
		tListViewTitle.setId("t-title-label");
		tListViewTitle.maxWidth(listViewMaxWidth);
	}
    
    public void buildPaginator() {
    	tPaginator = new TPaginator(false, false);
    }
    
	
	/**
	 * <p>
	 * Build a button for the cancel action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{cancelButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_cancelButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildCancelButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tCancelButton = new Button();
			tCancelButton.setText(iEngine.getString(tPresenter==null 
							? TAnnotationDefaultValue.TVIEW_cancelButtonText 
									: tPresenter.decorator().cancelButtonText()));
			tCancelButton.setId("t-button");
		}else {
			tCancelButton = new Button();
			tCancelButton.setText(iEngine.getString(text));
			tCancelButton.setId("t-button");
		}
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
			tCloseButton = new Button();
			tCloseButton.setText(iEngine.getString(tPresenter==null 
							? TAnnotationDefaultValue.TVIEW_closeButtonText 
									: tPresenter.decorator().closeButtonText()));
			tCloseButton.setId("t-button");
		}else {
			tCloseButton = new Button();
			tCloseButton.setText(iEngine.getString(text));
			tCloseButton.setId("t-button");
		}
	}
	
	/**
	 * <p>
	 * Build a button for the search action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{searchButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_searchButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildSearchButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tSearchButton = new Button();
			tSearchButton.setText(iEngine.getString(tPresenter==null 
							? TAnnotationDefaultValue.TVIEW_searchButtonText 
									: tPresenter.decorator().searchButtonText()));
			tSearchButton.setId("t-button");
		}else {
			tSearchButton = new Button();
			tSearchButton.setText(iEngine.getString(text));
			tSearchButton.setId("t-button");
		}
	}

	/**
	 * <p>
	 * Build a button for the clean action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{cleanButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_cleanButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildCleanButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tCleanButton = new Button();
			tCleanButton.setText(iEngine.getString(tPresenter==null 
					? TAnnotationDefaultValue.TVIEW_cleanButtonText 
							: tPresenter.decorator().cleanButtonText()));
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
	 * @return the tSearchButton
	 */
	public Button gettSearchButton() {
		return tSearchButton;
	}


	/**
	 * @param tSearchButton the tSearchButton to set
	 */
	public void settSearchButton(Button tSearchButton) {
		this.tSearchButton = tSearchButton;
	}


	/**
	 * @return the tCancelButton
	 */
	public Button gettCancelButton() {
		return tCancelButton;
	}


	/**
	 * @param tCancelButton the tCancelButton to set
	 */
	public void settCancelButton(Button tCancelButton) {
		this.tCancelButton = tCancelButton;
	}

	/**
	 * @return the tTableView
	 */
	public TableView<M> gettTableView() {
		return tTableView;
	}

	/**
	 * @return the tPaginator
	 */
	public TPaginator gettPaginator() {
		return tPaginator;
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

	/**
	 * @return the tListView
	 */
	public ListView gettListView() {
		return tListView;
	}

	/**
	 * @return the tListViewTitle
	 */
	public TLabel gettListViewTitle() {
		return tListViewTitle;
	}

	

	// getters and setters
	
	

	
}
