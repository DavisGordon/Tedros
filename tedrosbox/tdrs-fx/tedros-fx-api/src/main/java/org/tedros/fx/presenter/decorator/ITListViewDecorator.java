package org.tedros.fx.presenter.decorator;

import org.tedros.core.control.TProgressIndicator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.paginator.TPaginator;
import org.tedros.server.entity.ITEntity;

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