package org.tedros.fx.presenter.dynamic.decorator;

import org.tedros.api.presenter.decorator.ITDecorator;
import org.tedros.api.presenter.view.ITDynaView;
import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.model.TModelView;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

@SuppressWarnings("rawtypes")
public abstract class TDynaViewSimpleBaseDecorator<M extends TModelView> 
extends org.tedros.fx.presenter.decorator.TDecorator<TDynaPresenter<M>>
implements ITDecorator<TDynaPresenter<M>>{
	
	/**
	 * <p>
	 *  Add a {@link Node} in the top of the content layout identified by the tTopContent field.<br><br>
	 * 	The top content pane is a {@link StackPane} in the top of a {@link BorderPane} called tContentLayout. 
	 * </p>
	 * */
    public void addItemInTTopContent(Node item) {		 
		final ITDynaView<M> view = getView();
		view.gettTopContent().getChildren().add(item);
	}
	
    /**
	 * <p>
	 * Set padding in the top content pane. 
	 * </p>
	 * */
	public void setPaddingInTTopContent(double top, double right, double bottom, double left) {
		final ITDynaView<M> view = getView(); 
		view.gettTopContent().setPadding(new Insets(top, right, bottom, left));
	}
	
	/**
	 * <p>
	 *  Add a {@link Node} in the right of the content layout identified by the tRightContent field.<br><br>
	 * 	The right content pane is a {@link StackPane} in the right of a {@link BorderPane} called tContentLayout. 
	 * </p>
	 * */
	public void addItemInTRightContent(Node item) {		 
		final ITDynaView<M> view = getView();
		view.gettRightContent().getChildren().add(item);
	}
	
	/**
	 * <p>
	 * Set padding in the right content pane. 
	 * </p>
	 * */
	public void addPaddingInTRightContent(double top, double right, double bottom, double left) {
		final ITDynaView<M> view = getView(); 
		view.gettRightContent().setPadding(new Insets(top, right, bottom, left));
	}
	
	/**
	 * <p>
	 *  Add a {@link Node} in the bottom of the content layout identified by the tBottomContent field.<br><br>
	 * 	The bottom content pane is a {@link StackPane} in the bottom of a {@link BorderPane} called tContentLayout. 
	 * </p>
	 * */
	public void addItemInTBottomContent(Node item) {		 
		final ITDynaView<M> view = getView();
		view.gettBottomContent().getChildren().add(item);
	}

	/**
	 * <p>
	 * Set padding in the bottom content pane. 
	 * </p>
	 * */
	public void addPaddingInTBottomContent(double top, double right, double bottom, double left) {
		final ITDynaView<M> view = getView(); 
		view.gettBottomContent().setPadding(new Insets(top, right, bottom, left));
	}
	
	/**
	 * <p>
	 *  Add a {@link Node} in the left of the content layout identified by the tLeftContent field.<br><br>
	 * 	The left content pane is a {@link StackPane} in the left of a {@link BorderPane} called tContentLayout. 
	 * </p>
	 * */
	public void addItemInTLeftContent(Node item) {		 
		final ITDynaView<M> view = getView();
		if(!view.gettLeftContent().getChildren().contains(item))
			view.gettLeftContent().getChildren().add(item);
	}

	/**
	 * <p>
	 * Set padding in the left content pane. 
	 * </p>
	 * */
	public void addPaddingInTLeftContent(double top, double right, double bottom, double left) {
		final ITDynaView<M> view = getView(); 
		view.gettLeftContent().setPadding(new Insets(top, right, bottom, left));
	}
	
	/**
	 * <p>
	 *  Add a {@link Node} in the center of the content layout identified by the tCenterContent field.<br><br>
	 * 	The center content pane is a {@link StackPane} in the center of a {@link BorderPane} called tContentLayout. 
	 * </p>
	 * */
	public void addItemInTCenterContent(Node item) {
		final ITDynaView<M> view = getView();
		view.gettCenterContent().getChildren().add(item);
	}
	
	/**
	 * <p>
	 * Set padding in the center content pane. 
	 * </p>
	 * */
	public void addPaddingInTCenterContent(double top, double right, double bottom, double left) {
		final ITDynaView<M> view = getView(); 
		view.gettCenterContent().setPadding(new Insets(top, right, bottom, left));
	}
	
	/**
	 * <p>
	 *  Add a {@link Node} in the vertical header pane identified by the tHeaderVerticalLayout field.<br><br>
	 * 	The vertical header pane is a {@link VBox} inside a {@link StackPane} called tHeaderContent. 
	 * </p>
	 * */
	public void addItemInTHeaderVerticalLayout(Node item){
		StackPane pane = new StackPane();
		pane.setId("t-header-box");
		pane.getChildren().add(item);
		
		final ITDynaView<M> view = getView();
		int index = view.gettHeaderVerticalLayout().getChildren().size();
		view.gettHeaderVerticalLayout().getChildren().add(index, pane);
	}
	
	/**
	 * <p>
	 *  Add a {@link Node} in the horizontal header pane identified by the tHeaderHorizontalLayout field.<br><br>
	 * 	The horizontal header pane is a {@link HBox} at the first position on the {@link VBox} called tHeaderVerticalLayout.
	 * 
	 *  By default the first position of this {@link HBox} is filled with a view title layout called tViewTitleLayout and
	 *  the second position is filled with a tool bar called tHeaderToolBar.
	 * </p>
	 * */
	public void addItemInTHeaderHorizontalLayout(Node... item) {
		final ITDynaView<M> view = getView();
		view.gettHeaderHorizontalLayout().getChildren().addAll(item);
	}

	/**
	 * <p>
	 *  Add a {@link Node} in the header tool bar identified by the tHeaderToolBar field.<br><br>
	 * 	The header tool bar is a {@link ToolBar} at the second position on the {@link HBox} called tHeaderHorizontalLayout.
	 * </p>
	 * */
	public void addItemInTHeaderToolBar(Node... item) {
		final ITDynaView<M> view = getView();
		view.gettHeaderToolBar().getItems().addAll(item);
	}
	
	/**
	 * <p>
	 * Set a title to the view.<br><br>
	 * 
	 * A view title is a {@link Label} node identified 
	 * by {@link ITDynaView}.gettViewTitle().<br><br>
	 * 
	 * If the parameter was null this will use the title set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{viewTitle=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_viewTitle) will be used.
	 * </p>
	 * */
	public void setViewTitle(String text) {
		final ITDynaView<M> view = getView();
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			view.gettViewTitle().setText(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_viewTitle : tPresenter.decorator().viewTitle()));
		}else
			view.gettViewTitle().setText(iEngine.getString(text));
	}
	
}
