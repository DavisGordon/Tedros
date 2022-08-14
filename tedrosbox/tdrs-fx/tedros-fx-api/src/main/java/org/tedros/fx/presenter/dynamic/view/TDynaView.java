package org.tedros.fx.presenter.dynamic.view;

import java.net.URL;

import org.tedros.core.ITModule;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.presenter.view.TView;
import org.tedros.server.model.ITModel;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

@SuppressWarnings("rawtypes")
public class TDynaView<M extends TModelView> 
extends TView<TDynaPresenter<M>> 
implements ITDynaView<M> {
	
	private static final String FXML = "TDynamicView.fxml";
	private static final String FXML2 = "TSimpleView.fxml";
	
	@FXML private VBox 		tLayout;
	@FXML private StackPane tHeaderContent;
	@FXML private VBox 		tHeaderVerticalLayout;
	@FXML private HBox 		tHeaderHorizontalLayout;
	
	@FXML private HBox 	tViewTitleLayout;
	@FXML private Label tViewTitle;
	
	@FXML private ToolBar tHeaderToolBar;
	
	@FXML private StackPane tContent;
	@FXML private BorderPane tContentLayout;
	@FXML private StackPane tTopContent;
	@FXML private StackPane tCenterContent;
    @FXML private StackPane tRightContent;
	@FXML private StackPane tBottomContent;
	@FXML private StackPane tLeftContent;
	
	private StackPane tFormSpace;
    
	public TDynaView(Class<M> modelViewClass) {
		super(new TDynaPresenter<>(modelViewClass), getURL());
	}
	
	public TDynaView(Class<M> modelViewClass, ObservableList<M> models){
		super(new TDynaPresenter<>(modelViewClass, models), getURL());
	}
	
	public TDynaView(Class<M> modelViewClass, ObservableList<M> models, boolean full){
		super(new TDynaPresenter<>(modelViewClass, models), getURL(full));
	}
	
	public TDynaView(Class<M> modelViewClass, Class<? extends ITModel> modelClass, ObservableList<M> models){
		super(new TDynaPresenter<>(modelViewClass, modelClass, models), getURL());
	}
	
	public TDynaView(Class<M> modelViewClass, URL fxmlURL) {
		super(new TDynaPresenter<>(modelViewClass), fxmlURL);
	}
	
	public TDynaView(TDynaPresenter<M> presenter) {
		super(presenter, getURL());
	}
    
	public TDynaView(TDynaPresenter<M> presenter, URL fxmlURL) {
		super(presenter, fxmlURL);
	}

	//
	
	public TDynaView(ITModule module, Class<M> modelViewClass) {
		super(new TDynaPresenter<>(module, modelViewClass), getURL());
	}
	
	public TDynaView(ITModule module, Class<M> modelViewClass, ObservableList<M> models){
		super(new TDynaPresenter<>(module, modelViewClass, models), getURL());
	}
	
	public TDynaView(ITModule module, Class<M> modelViewClass, Class<? extends ITModel> modelClass, ObservableList<M> models){
		super(new TDynaPresenter<>(module, modelViewClass, modelClass, models), getURL());
	}
	
	public TDynaView(ITModule module, Class<M> modelViewClass, URL fxmlURL) {
		super(new TDynaPresenter<>(module, modelViewClass), fxmlURL);
	}
    
	
	private static URL getURL() {
		return getURL(true);
	}
	
	private static URL getURL(boolean full) {
		return TDynaView.class.getResource(full ? FXML : FXML2);
	}

	@Override
	public VBox gettLayout() {
		return tLayout;
	}

	@Override
	public void settLayout(VBox tLayout) {
		this.tLayout = tLayout;
	}

	@Override
	public StackPane gettHeaderContent() {
		return tHeaderContent;
	}

	@Override
	public void settHeaderContent(StackPane tHeaderContent) {
		this.tHeaderContent = tHeaderContent;
	}

	@Override
	public VBox gettHeaderVerticalLayout() {
		return tHeaderVerticalLayout;
	}

	@Override
	public void settHeaderVerticalLayout(VBox tHeaderVerticalLayout) {
		this.tHeaderVerticalLayout = tHeaderVerticalLayout;
	}

	@Override
	public HBox gettHeaderHorizontalLayout() {
		return tHeaderHorizontalLayout;
	}

	@Override
	public void settHeaderHorizontalLayout(HBox tHeaderHorizontalLayout) {
		this.tHeaderHorizontalLayout = tHeaderHorizontalLayout;
	}

	@Override
	public HBox gettViewTitleLayout() {
		return tViewTitleLayout;
	}

	@Override
	public void settViewTitleLayout(HBox tViewTitleLayout) {
		this.tViewTitleLayout = tViewTitleLayout;
	}

	@Override
	public Label gettViewTitle() {
		return tViewTitle;
	}

	@Override
	public void settViewTitle(Label tViewTitle) {
		this.tViewTitle = tViewTitle;
	}

	@Override
	public ToolBar gettHeaderToolBar() {
		return tHeaderToolBar;
	}

	@Override
	public void settHeaderToolBar(ToolBar tHeaderToolBar) {
		this.tHeaderToolBar = tHeaderToolBar;
	}

	@Override
	public StackPane gettContent() {
		return tContent;
	}

	@Override
	public void settContent(StackPane tContent) {
		this.tContent = tContent;
	}

	@Override
	public BorderPane gettContentLayout() {
		return tContentLayout;
	}

	@Override
	public void settContentLayout(BorderPane tContentLayout) {
		this.tContentLayout = tContentLayout;
	}

	@Override
	public StackPane gettTopContent() {
		return tTopContent;
	}

	@Override
	public void settTopContent(StackPane tTopContent) {
		this.tTopContent = tTopContent;
	}

	@Override
	public StackPane gettCenterContent() {
		return tCenterContent;
	}

	@Override
	public void settCenterContent(StackPane tCenterContent) {
		this.tCenterContent = tCenterContent;
	}

	@Override
	public StackPane gettRightContent() {
		return tRightContent;
	}

	@Override
	public void settRightContent(StackPane tRightContent) {
		this.tRightContent = tRightContent;
	}

	@Override
	public StackPane gettBottomContent() {
		return tBottomContent;
	}

	@Override
	public void settBottomContent(StackPane tBottomContent) {
		this.tBottomContent = tBottomContent;
	}

	@Override
	public StackPane gettLeftContent() {
		return tLeftContent;
	}

	@Override
	public void settLeftContent(StackPane tLeftContent) {
		this.tLeftContent = tLeftContent;
	}

	@Override
	public StackPane gettFormSpace() {
		if(tFormSpace == null)
			tFormSpace = new StackPane();
			
		return tFormSpace;
	}

	
}
