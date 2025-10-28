package org.tedros.fx.presenter.dynamic.decorator;

import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.TDefaultValue;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.presenter.TSelectionModalPresenter;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.page.TPager;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;

/**
 * The basic decorator of the open modal view 
 * to select an entity.
 * It can be set using the 
 * {@link TSelectionModalPresenter} annotation on 
 * the TEntityModelView. 
 * @author Davis Gordon
 *
 * @param <M>
 */
@SuppressWarnings("rawtypes")
public abstract class TDynaViewSelectionBaseDecorator<M extends TModelView> 
extends TDynaViewSimpleBaseDecorator<M> {
	
	private Button tCleanButton;
	private Button tSearchButton;
	private Button tCancelButton;
	private Button tCloseButton;
	private Button tSelectAllButton;
	private Button tAddButton;
	
	private ListView tListView;
	private TLabel tListViewTitle;
	
	private TableView<M> tTableView;
	
	private TPager tPaginator;
	protected double listViewMaxWidth = TSelectionModalPresenter.WIDTH;
	protected double listViewMinWidth = TSelectionModalPresenter.WIDTH;
    
    
    @Override
    public void setPresenter(TDynaPresenter<M> presenter) {
    	super.setPresenter(presenter);
    }
    
    /**
     * Sets the tTableView
     * @param tableView
     */
    @SuppressWarnings("unchecked")
	public void setTableView(TableView tableView) {
    	tTableView = tableView;
    	
    }
    
    /**
     * Build the ListView
     */
    public void buildListView() {
    	// build the list view
		tListView = new ListView<>();
		tListView.setCache(false);
		tListView.autosize();
		tListView.setMaxWidth(listViewMaxWidth);
		tListView.setMinWidth(listViewMinWidth);
		tListView.setTooltip(new Tooltip(TLanguage
				.getInstance(null)
				.getString(TFxKey.DOUBLE_CLICK_REMOVE)));
		
    }

    /**
     * Build the list view title
     * @param text
     */
	public void buildListViewTitle(String text) {
		// build the label for the list view
		tListViewTitle = new TLabel(iEngine.getString(text==null 
				? TDefaultValue.TVIEW_selected : text));
		tListViewTitle.setId("t-title-label");
		tListViewTitle.maxWidth(listViewMaxWidth);
	}
    
	/**
	 * Build the paginator
	 */
    public void buildPaginator() {
    	tPaginator = new TPager(false, false);
    }
    
    /**
	 * <p>
	 * Build a button for the add action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{addButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_addButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildAddButton(String text) {
		if(text==null){
			tAddButton = new TButton();
			tAddButton.setText(iEngine.getString(TDefaultValue.TVIEW_addButtonText));
		}else {
			tAddButton = new TButton();
			tAddButton.setText(iEngine.getString(text));
		}
	}
	
	/**
	 * <p>
	 * Build a button for the select all action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{selectAllButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_selectAllButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildSelectAllButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tSelectAllButton = new TButton();
			tSelectAllButton.setText(iEngine.getString(tPresenter==null 
							? TDefaultValue.TVIEW_selectAllButtonText 
									: tPresenter.decorator().selectAllButtonText()));
		}else {
			tSelectAllButton = new TButton();
			tSelectAllButton.setText(iEngine.getString(text));
		}
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
			tCancelButton = new TButton();
			tCancelButton.setText(iEngine.getString(tPresenter==null 
							? TDefaultValue.TVIEW_cancelButtonText 
									: tPresenter.decorator().cancelButtonText()));
		}else {
			tCancelButton = new TButton();
			tCancelButton.setText(iEngine.getString(text));
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
			tCloseButton = new TButton();
			tCloseButton.setText(iEngine.getString(tPresenter==null 
							? TDefaultValue.TVIEW_closeButtonText 
									: tPresenter.decorator().closeButtonText()));
		}else {
			tCloseButton = new TButton();
			tCloseButton.setText(iEngine.getString(text));
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
			tSearchButton = new TButton();
			tSearchButton.setText(iEngine.getString(tPresenter==null 
							? TDefaultValue.TVIEW_searchButtonText 
									: tPresenter.decorator().searchButtonText()));
		}else {
			tSearchButton = new TButton();
			tSearchButton.setText(iEngine.getString(text));
		}
	}

	/**
	 * <p>
	 * Build a button for the clear action.<br><br>
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
			tCleanButton = new TButton();
			tCleanButton.setText(iEngine.getString(tPresenter==null 
					? TDefaultValue.TVIEW_cleanButtonText 
							: tPresenter.decorator().cleanButtonText()));
		}else {
			tCleanButton = new TButton();
			tCleanButton.setText(iEngine.getString(text));
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
	public TPager gettPaginator() {
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

	/**
	 * @return the tSelectAllButton
	 */
	public Button gettSelectAllButton() {
		return tSelectAllButton;
	}

	/**
	 * @param tSelectAllButton the tSelectAllButton to set
	 */
	public void settSelectAllButton(Button tSelectAllButton) {
		this.tSelectAllButton = tSelectAllButton;
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
}
