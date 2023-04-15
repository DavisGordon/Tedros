package org.tedros.fx.presenter.decorator;

import org.tedros.core.control.TProgressIndicator;
import org.tedros.fx.presenter.assistant.TAiAssistant;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.paginator.TPaginator;
import org.tedros.server.entity.ITEntity;

import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/***
 * The model list view decorator contract.
 * @author Davis Gordon
 *
 * @param <M>
 */
public interface ITListViewDecorator<M extends TEntityModelView<? extends ITEntity>> {

	/**
	 * Return the visible state of the model list content
	 * @return true if visible
	 */
	boolean isListContentVisible();

	/**
	 * Hide the list content
	 */
	void hideListContent();

	/**
	 * Show the list content
	 */
	void showListContent();

	/**
	 * Get the list view parent node
	 * @return VBox
	 */
	VBox gettListViewLayout();

	/**
	 * Set the list view parent node
	 * @param tListViewLayout
	 */
	void settListViewLayout(VBox tListViewLayout);

	/**
	 * Get the list view title
	 * @return Label
	 */
	Label gettListViewTitle();

	/**
	 * Set the list view title
	 * @param tListViewTitle
	 */
	void settListViewTitle(Label tListViewTitle);

	/**
	 * Get the list view
	 * @return ListView<M>
	 */
	ListView<M> gettListView();

	/**
	 * Set the list view
	 * @param tListView
	 */
	void settListView(ListView<M> tListView);

	/**
	 * Get the list view max width
	 * @return double
	 */
	double getListViewMaxWidth();

	/**
	 * Set the list view max width
	 * @param listViewMaxWidth
	 */
	void setListViewMaxWidth(double listViewMaxWidth);

	/**
	 * Get the list view min width
	 * @return double
	 */
	double getListViewMinWidth();

	/**
	 * Set the list view min width
	 * @param listViewMinWidth
	 */
	void setListViewMinWidth(double listViewMinWidth);

	/**
	 * Get the paginator
	 * @return the tPaginator
	 */
	TPaginator gettPaginator();
	
	/**
	 * Get the assistant
	 * @return tAiAssistant
	 * */
	TAiAssistant gettAiAssistant();
	
	
	/**
	 * Set the tAiAssistant
	 * @param tAiAssistant the tAiAssistant to set
	 * */
	void settAiAssistant(TAiAssistant tAiAssistant);

	/**
	 * Set the paginator
	 * @param tPaginator the tPaginator to set
	 */
	void settPaginator(TPaginator tPaginator);

	/**
	 * Get the paginator accordion
	 * @return the tPaginatorAccordion
	 */
	Accordion gettPaginatorAccordion();

	/**
	 * Get the list view progress indicator
	 * @return the tListViewProgressIndicator
	 */
	TProgressIndicator gettListViewProgressIndicator();

}