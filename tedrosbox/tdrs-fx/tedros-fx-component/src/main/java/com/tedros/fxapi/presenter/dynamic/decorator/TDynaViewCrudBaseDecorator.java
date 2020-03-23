package com.tedros.fxapi.presenter.dynamic.decorator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TSecurityDescriptor;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.layout.TBreadcrumbForm;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.view.ITDynaView;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioButtonBuilder;
import javafx.scene.control.ToolBar;

@SuppressWarnings("rawtypes")
public abstract class TDynaViewCrudBaseDecorator<M extends TModelView> 
extends TDynaViewSimpleBaseDecorator<M> {
	
	private Button tColapseButton;
	private Button tNewButton;
	private Button tSaveButton;
	private Button tDeleteButton;
	private Button tEditButton;
	private Button tCancelButton;
	
    private RadioButton tEditModeRadio;
    private RadioButton tReadModeRadio;
    
    private ToolBar tBreadcrumbFormToolBar;
    private TBreadcrumbForm tBreadcrumbForm;
    private boolean showBreadcrumBar;
    
    private List<TAuthorizationType> userAuthorizations;
    
    public List<TAuthorizationType> getUserAuthorizations() {
		return userAuthorizations;
	}
    
    public boolean isUserAuthorized(TAuthorizationType type){
    	return userAuthorizations==null || userAuthorizations.contains(type);
    }
    
    public boolean isUserNotAuthorized(TAuthorizationType type){
    	return userAuthorizations!=null && !userAuthorizations.contains(type);
    }
    
    @Override
    public void setPresenter(TDynaPresenter<M> presenter) {
    	super.setPresenter(presenter);
    	buildUserAuthorizations();
    }
    
    private void buildUserAuthorizations() {
    	TSecurity modelViewSecurity = getPresenter().getModelViewClass().getAnnotation(TSecurity.class);
		if(modelViewSecurity!=null){
			TSecurityDescriptor securityDescriptor = new TSecurityDescriptor(modelViewSecurity);
			userAuthorizations = new ArrayList<>();
			for(TAuthorizationType type : new TAuthorizationType[]{TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW}){
				if(TedrosContext.isUserAuthorized(securityDescriptor, type))
					userAuthorizations.add(type);
			}
		}
	}
    
    
    /**
	 * <p>
	 *  Build a {@link TBreadcrumbForm} to use.
	 *  The {@link TBreadcrumbForm} is a breadcrumbBar used to navigate between opened forms. 
	 * </p>
	 * */
	public void buildTBreadcrumbForm(){
		final ITDynaView<M> view = getView();
		tBreadcrumbForm = new TBreadcrumbForm(view.gettFormSpace());
		tBreadcrumbFormToolBar = new ToolBar();
        tBreadcrumbFormToolBar.setId("t-app-header-toolbar");
        tBreadcrumbFormToolBar.setMaxSize(Double.MAX_VALUE, Control.USE_PREF_SIZE);
        tBreadcrumbFormToolBar.getItems().addAll(tBreadcrumbForm);
	}

	/**
	 * <p>
	 * Build a radio buttons for the select mode action.<br><br>
	 * 
	 * The select mode action can be Edit or Reader, see {@link TViewMode}.
	 * 
	 * If the parameters was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{editModeTitle='', readerModeTitle='' }} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_editModeTitle and TAnnotationDefaultValue.TVIEW_readerModeTitle) 
	 * will be used.<br><br> 
	 * 
	 * This will initialize with "t-title-label" id.
	 * </p>
	 * */
	public void buildModesRadioButton(String editRadiotext, String readRadioText) {
		
		final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
		if(editRadiotext==null)		
			tEditModeRadio = RadioButtonBuilder.create()
			.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_editModeTitle : tPresenter.decorator().editModeTitle()))
			.id("t-title-label")
			.build();
		else
			tEditModeRadio = RadioButtonBuilder.create()
			.text(iEngine.getString(editRadiotext))
			.id("t-title-label")
			.build();
		
		if(readRadioText==null)	
			tReadModeRadio = RadioButtonBuilder.create()
			.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_readerModeTitle : tPresenter.decorator().readerModeTitle()))
			.id("t-title-label")
			.build();
		else
			tReadModeRadio = RadioButtonBuilder.create()
			.text(iEngine.getString(readRadioText))
			.id("t-title-label")
			.build();
		
		if(isUserNotAuthorized(TAuthorizationType.EDIT))
			tEditModeRadio.setDisable(true);
		if(isUserNotAuthorized(TAuthorizationType.READ))
			tReadModeRadio.setDisable(true);
	}
	
	/**
	 * <p>
	 * Build a button for the edit action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{editButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_editButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildEditButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tEditButton = ButtonBuilder.create()
					.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_editButtonText : tPresenter.decorator().editButtonText()))
					.id("t-button")
					.build();
		}else
			tEditButton = ButtonBuilder.create()
			.text(iEngine.getString(text))
			.id("t-button")
			.build();
		
		if(isUserNotAuthorized(TAuthorizationType.EDIT))
			tEditButton.setDisable(true);
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
			tCancelButton = ButtonBuilder.create()
					.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_cancelButtonText : tPresenter.decorator().cancelButtonText()))
					.id("t-button")
					.build();
		}else
			tCancelButton = ButtonBuilder.create()
			.text(iEngine.getString(text))
			.id("t-button")
			.build();
	}
	
	/**
	 * <p>
	 * Build a button for the save action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{saveButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_saveButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildSaveButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tSaveButton = ButtonBuilder.create()
					.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_saveButtonText : tPresenter.decorator().saveButtonText()))
					.id("t-button")
					.build();
		}else
			tSaveButton = ButtonBuilder.create()
			.text(iEngine.getString(text))
			.id("t-button")
			.build();
		
		if(isUserNotAuthorized(TAuthorizationType.SAVE))
			tSaveButton.setDisable(true);
	}

	/**
	 * <p>
	 * Build a button for the delete action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{deleteButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_deleteButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildDeleteButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tDeleteButton = ButtonBuilder.create()
					.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_deleteButtonText : tPresenter.decorator().deleteButtonText()))
					.id("t-button")
					.build();
		}else
			tDeleteButton = ButtonBuilder.create()
			.text(iEngine.getString(text))
			.id("t-button")
			.build();
		
		if(isUserNotAuthorized(TAuthorizationType.DELETE))
			tDeleteButton.setDisable(true);
	}

	/**
	 * <p>
	 * Build a button for the new action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{newButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_newButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildNewButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tNewButton = ButtonBuilder.create()
					.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_newButtonText : tPresenter.decorator().newButtonText()))
					.id("t-button")
					.build();
		}else
			tNewButton = ButtonBuilder.create()
			.text(iEngine.getString(text))
			.id("t-button")
			.build();
		
		if(isUserNotAuthorized(TAuthorizationType.NEW))
			tNewButton.setDisable(true);
	}

	/**
	 * <p>
	 * Build a button for the colapse action.
	 * 
	 * A "<|>" string will be used if given parameter was null. 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildColapseButton(String text) {
		tColapseButton = ButtonBuilder.create()
				.text(iEngine.getString(StringUtils.isBlank(text) ? TAnnotationDefaultValue.TVIEW_colapse : text))
				.id("t-button")
				.build();
	}

	// getters and setters
	
	/**
	 * 	Get the colapse button.
	 * */
	public Button gettColapseButton() {
		return tColapseButton;
	}

	/**
	 * 	Get the new button.
	 * */
	public Button gettNewButton() {
		return tNewButton;
	}

	/**
	 * 	Get the save button.
	 * */
	public Button gettSaveButton() {
		return tSaveButton;
	}

	/**
	 * 	Get the delete button.
	 * */
	public Button gettDeleteButton() {
		return tDeleteButton;
	}
	
	/**
	 * 	Get the edit button.
	 * */
	public Button gettEditButton(){
		return tEditButton;
	}
	
	/**
	 * 	Get the cancel button.
	 * */
	public Button gettCancelButton(){
		return tCancelButton;
	}

	/**
	 * 	Get the edit radio button.
	 * */
	public RadioButton gettEditModeRadio() {
		return tEditModeRadio;
	}

	/**
	 * 	Get the reader radio button.
	 * */
	public RadioButton gettReadModeRadio() {
		return tReadModeRadio;
	}

	/**
	 * 	Get the breadcrumbform tool bar.
	 * */
	public ToolBar gettBreadcrumbFormToolBar() {
		return tBreadcrumbFormToolBar;
	}

	/**
	 * 	Get the breadcrumbform.
	 * */
	public TBreadcrumbForm gettBreadcrumbForm() {
		return tBreadcrumbForm;
	}

	/**
	 * Return true if the breadcrumbar is set up to show in the view
	 * */
	public boolean isShowBreadcrumBar() {
		return showBreadcrumBar;
	}

	/**
	 * True enable the breadcrumbbar to navigate between opened forms in the view.  
	 * */
	public void setShowBreadcrumBar(boolean showBreadcrumBar) {
		this.showBreadcrumBar = showBreadcrumBar;
	}

	
}
