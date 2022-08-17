package org.tedros.fx.presenter.modal.decorator;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.core.control.TProgressIndicator;
import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TEditModalPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.control.TButton;
import org.tedros.fx.presenter.decorator.ITListViewDecorator;
import org.tedros.fx.presenter.decorator.TListViewHelper;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.ITDynaView;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.presenter.paginator.TPaginator;
import org.tedros.server.entity.ITEntity;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TEditModalDecorator<M extends TEntityModelView<? extends ITEntity>> 
extends TDynaViewCrudBaseDecorator<M> implements ITListViewDecorator<M>{
	
	private Button tCloseButton;
    
	private TListViewHelper<M> helper;
	
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
		
		String title = tPresenter!=null ? tPresenter.decorator().listTitle() : null;
		// get the list view settings
		TEditModalPresenter tAnnotation = getPresenter().getModelViewClass()
				.getAnnotation(TEditModalPresenter.class);
		if(tAnnotation!=null)
			helper = new TListViewHelper<>(title, tAnnotation.listViewMaxWidth(), tAnnotation.listViewMinWidth(), null);
		else
			helper = new TListViewHelper<>(title, 250, 250, null);
		
		// add the list view box at the left 
		addItemInTLeftContent(helper.gettListViewPane());
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
		addItemInTCenterContent(getView().gettFormSpace());
		StackPane.setMargin(getView().gettFormSpace(), new Insets(0,0,0,3));
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
	

    /* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#isListContentVisible()
	 */
    @Override
	public boolean isListContentVisible() {
    	final ITDynaView<M> view = getView();
		return view.gettLeftContent().getChildren().contains(helper.gettListViewPane());
    }
    
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#hideListContent()
	 */
	@Override
	public void hideListContent() {
		final ITDynaView<M> view = getView();
		view.gettLeftContent().getChildren().remove(helper.gettListViewPane());
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#showListContent()
	 */
	@Override
	public void showListContent() {
		addItemInTLeftContent(helper.gettListViewPane());
		
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#gettListViewLayout()
	 */
	@Override
	public VBox gettListViewLayout() {
		return helper.gettListViewLayout();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#settListViewLayout(javafx.scene.layout.VBox)
	 */
	@Override
	public void settListViewLayout(VBox tListViewLayout) {
		helper.settListViewLayout(tListViewLayout);
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#gettListViewTitle()
	 */
	@Override
	public Label gettListViewTitle() {
		return helper.gettListViewTitle();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#settListViewTitle(javafx.scene.control.Label)
	 */
	@Override
	public void settListViewTitle(Label tListViewTitle) {
		helper.settListViewTitle(tListViewTitle);
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#gettListView()
	 */
	@Override
	public ListView<M> gettListView() {
		return helper.gettListView();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#settListView(javafx.scene.control.ListView)
	 */
	@Override
	public void settListView(ListView<M> tListView) {
		helper.settListView(tListView);
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#getListViewMaxWidth()
	 */
	@Override
	public double getListViewMaxWidth() {
		return helper.getListViewMaxWidth().get();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#setListViewMaxWidth(double)
	 */
	@Override
	public void setListViewMaxWidth(double listViewMaxWidth) {
		helper.getListViewMaxWidth().setValue(listViewMaxWidth);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#getListViewMinWidth()
	 */
	@Override
	public double getListViewMinWidth() {
		return helper.getListViewMinWidth().get();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#setListViewMinWidth(double)
	 */
	@Override
	public void setListViewMinWidth(double listViewMinWidth) {
		helper.getListViewMinWidth().setValue(listViewMinWidth);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#gettPaginator()
	 */
	@Override
	public TPaginator gettPaginator() {
		return helper.gettPaginator();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#settPaginator(org.tedros.fx.presenter.paginator.TPaginator)
	 */
	@Override
	public void settPaginator(TPaginator tPaginator) {
		helper.settPaginator(tPaginator);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#gettPaginatorAccordion()
	 */
	@Override
	public Accordion gettPaginatorAccordion() {
		return helper.gettPaginatorAccordion();
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#gettListViewProgressIndicator()
	 */
	@Override
	public TProgressIndicator gettListViewProgressIndicator() {
		return helper.gettListViewProgressIndicator();
	}

}
