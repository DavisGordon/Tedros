package com.tedros.fxapi.presenter.dynamic.view;

import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import com.tedros.core.presenter.view.ITView;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;

@SuppressWarnings("rawtypes")
public interface ITDynaView<M extends TModelView>  extends ITView<TDynaPresenter<M>>{

	public VBox gettLayout();

	public void settLayout(VBox tLayout);

	public StackPane gettHeaderContent();

	public void settHeaderContent(StackPane tHeaderContent);

	public VBox gettHeaderVerticalLayout();

	public void settHeaderVerticalLayout(VBox tHeaderVerticalLayout);

	public HBox gettHeaderHorizontalLayout();

	public void settHeaderHorizontalLayout(HBox tHeaderHorizontalLayout);

	public HBox gettViewTitleLayout();

	public void settViewTitleLayout(HBox tViewTitleLayout);

	public Label gettViewTitle();

	public void settViewTitle(Label tViewTitle);

	public ToolBar gettHeaderToolBar();

	public void settHeaderToolBar(ToolBar tHeaderToolBar);

	public StackPane gettContent();

	public void settContent(StackPane tContent);

	public BorderPane gettContentLayout();

	public void settContentLayout(BorderPane tContentLayout);

	public StackPane gettTopContent();

	public void settTopContent(StackPane tTopContent);

	public StackPane gettCenterContent();

	public void settCenterContent(StackPane tCenterContent);

	public StackPane gettRightContent();

	public void settRightContent(StackPane tRightContent);

	public StackPane gettBottomContent();

	public void settBottomContent(StackPane tBottomContent);

	public StackPane gettLeftContent();

	public void settLeftContent(StackPane tLeftContent);

	public StackPane gettFormSpace();

}