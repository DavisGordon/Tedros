package com.tedros.fxapi.presenter.entity.decorator;

import org.apache.commons.lang3.ArrayUtils;

import com.tedros.core.control.TProgressIndicator;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.presenter.decorator.ITListViewDecorator;
import com.tedros.fxapi.presenter.decorator.TListViewHelper;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.ITDynaView;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.paginator.TPaginator;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class TDetailCrudViewDecorator<M extends TEntityModelView<? extends ITEntity>> 
extends TDynaViewCrudBaseDecorator<M> implements ITListViewDecorator<M>{
	
	private TListViewHelper<M> helper;
 
	public void decorate() {
		
		// get the model view annotation array 
		final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
		
		final TForm tForm = this.getPresenter().getFormAnnotation();
		setShowBreadcrumBar((tForm!=null) ? tForm.showBreadcrumBar() : false);
		
		// get the views
		final ITDynaView<M> view = getPresenter().getView();
		
		addItemInTCenterContent(view.gettFormSpace());
		setViewTitle(null);
		
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

		// get the list view settings
		TListViewPresenter tAnnotation = getPresenter().getModelViewClass().getAnnotation(TListViewPresenter.class);
		String title = tPresenter!=null ? tPresenter.decorator().listTitle() : null;
		if(tAnnotation!=null)
			helper = new TListViewHelper<>(title, tAnnotation.listViewMaxWidth(), tAnnotation.listViewMinWidth(), null);
		else
			helper = new TListViewHelper<>(title, 250, 250, null);
		
		// add the list view box at the left 
		addItemInTLeftContent(helper.gettListViewPane());
	}
	

    /* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#isListContentVisible()
	 */
    @Override
	public boolean isListContentVisible() {
    	final ITDynaView<M> view = getView();
		return view.gettLeftContent().getChildren().contains(helper.gettListViewPane());
    }
    
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#hideListContent()
	 */
	@Override
	public void hideListContent() {
		final ITDynaView<M> view = getView();
		view.gettLeftContent().getChildren().remove(helper.gettListViewPane());
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#showListContent()
	 */
	@Override
	public void showListContent() {
		addItemInTLeftContent(helper.gettListViewPane());
		
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#gettListViewLayout()
	 */
	@Override
	public VBox gettListViewLayout() {
		return helper.gettListViewLayout();
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#settListViewLayout(javafx.scene.layout.VBox)
	 */
	@Override
	public void settListViewLayout(VBox tListViewLayout) {
		helper.settListViewLayout(tListViewLayout);
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#gettListViewTitle()
	 */
	@Override
	public Label gettListViewTitle() {
		return helper.gettListViewTitle();
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#settListViewTitle(javafx.scene.control.Label)
	 */
	@Override
	public void settListViewTitle(Label tListViewTitle) {
		helper.settListViewTitle(tListViewTitle);
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#gettListView()
	 */
	@Override
	public ListView<M> gettListView() {
		return helper.gettListView();
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#settListView(javafx.scene.control.ListView)
	 */
	@Override
	public void settListView(ListView<M> tListView) {
		helper.settListView(tListView);
	}
	
	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#getListViewMaxWidth()
	 */
	@Override
	public double getListViewMaxWidth() {
		return helper.getListViewMaxWidth().get();
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#setListViewMaxWidth(double)
	 */
	@Override
	public void setListViewMaxWidth(double listViewMaxWidth) {
		helper.getListViewMaxWidth().setValue(listViewMaxWidth);
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#getListViewMinWidth()
	 */
	@Override
	public double getListViewMinWidth() {
		return helper.getListViewMinWidth().get();
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#setListViewMinWidth(double)
	 */
	@Override
	public void setListViewMinWidth(double listViewMinWidth) {
		helper.getListViewMinWidth().setValue(listViewMinWidth);
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#gettPaginator()
	 */
	@Override
	public TPaginator gettPaginator() {
		return helper.gettPaginator();
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#settPaginator(com.tedros.fxapi.presenter.paginator.TPaginator)
	 */
	@Override
	public void settPaginator(TPaginator tPaginator) {
		helper.settPaginator(tPaginator);
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#gettPaginatorAccordion()
	 */
	@Override
	public Accordion gettPaginatorAccordion() {
		return helper.gettPaginatorAccordion();
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.entity.decorator.ITListViewDecorator#gettListViewProgressIndicator()
	 */
	@Override
	public TProgressIndicator gettListViewProgressIndicator() {
		return helper.gettListViewProgressIndicator();
	}


}
