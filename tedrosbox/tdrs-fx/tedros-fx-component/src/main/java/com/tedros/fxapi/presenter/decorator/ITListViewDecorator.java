package com.tedros.fxapi.presenter.decorator;

import com.tedros.core.control.TProgressIndicator;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.paginator.TPaginator;

import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public interface ITListViewDecorator<M extends TEntityModelView<? extends ITEntity>> {

	boolean isListContentVisible();

	void hideListContent();

	void showListContent();

	VBox gettListViewLayout();

	void settListViewLayout(VBox tListViewLayout);

	Label gettListViewTitle();

	void settListViewTitle(Label tListViewTitle);

	ListView<M> gettListView();

	void settListView(ListView<M> tListView);

	double getListViewMaxWidth();

	void setListViewMaxWidth(double listViewMaxWidth);

	double getListViewMinWidth();

	void setListViewMinWidth(double listViewMinWidth);

	/**
	 * @return the tPaginator
	 */
	TPaginator gettPaginator();

	/**
	 * @param tPaginator the tPaginator to set
	 */
	void settPaginator(TPaginator tPaginator);

	/**
	 * @return the tPaginatorAccordion
	 */
	Accordion gettPaginatorAccordion();

	/**
	 * @return the tListViewProgressIndicator
	 */
	TProgressIndicator gettListViewProgressIndicator();

}