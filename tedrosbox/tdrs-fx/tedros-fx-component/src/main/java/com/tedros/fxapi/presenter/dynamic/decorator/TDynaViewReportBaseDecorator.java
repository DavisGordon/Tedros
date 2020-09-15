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
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioButtonBuilder;

@SuppressWarnings("rawtypes")
public abstract class TDynaViewReportBaseDecorator<M extends TModelView> 
extends TDynaViewSimpleBaseDecorator<M> {
	
	private Button tColapseButton;
	private Button tCleanButton;
	private Button tSearchButton;
	private Button tExcelButton;
	private Button tPdfButton;
	private Button tWordButton;
	private Button tCancelButton;
	
    private RadioButton tEditModeRadio;
    private RadioButton tReadModeRadio;
    
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
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW
					, TAuthorizationType.SEARCH, TAuthorizationType.EXPORT}){
				if(TedrosContext.isUserAuthorized(securityDescriptor, type))
					userAuthorizations.add(type);
			}
		}
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
			tSearchButton = ButtonBuilder.create()
					.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_searchButtonText : tPresenter.decorator().searchButtonText()))
					.id("t-button")
					.build();
		}else
			tSearchButton = ButtonBuilder.create()
			.text(iEngine.getString(text))
			.id("t-button")
			.build();
		
		if(isUserNotAuthorized(TAuthorizationType.SEARCH))
			tSearchButton.setDisable(true);
	}

	/**
	 * <p>
	 * Build a button for the clean action.<br><br>
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
			tCleanButton = ButtonBuilder.create()
					.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_cleanButtonText : tPresenter.decorator().cleanButtonText()))
					.id("t-button")
					.build();
		}else
			tCleanButton = ButtonBuilder.create()
			.text(iEngine.getString(text))
			.id("t-button")
			.build();
		
		
	}

	/**
	 * <p>
	 * Build a button for the Excel action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{excelButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_excelButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildExcelButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tExcelButton = ButtonBuilder.create()
					.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_excelButtonText : tPresenter.decorator().excelButtonText()))
					.id("t-button")
					.build();
		}else
			tExcelButton = ButtonBuilder.create()
			.text(iEngine.getString(text))
			.id("t-button")
			.build();
		
		if(isUserNotAuthorized(TAuthorizationType.EXPORT))
			tExcelButton.setDisable(true);
	}

	/**
	 * <p>
	 * Build a button for the Word action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{wordButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_wordButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildWordButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tWordButton = ButtonBuilder.create()
					.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_wordButtonText : tPresenter.decorator().wordButtonText()))
					.id("t-button")
					.build();
		}else
			tWordButton = ButtonBuilder.create()
			.text(iEngine.getString(text))
			.id("t-button")
			.build();
		
		if(isUserNotAuthorized(TAuthorizationType.EXPORT))
			tWordButton.setDisable(true);
	}
	
	/**
	 * <p>
	 * Build a button for the Pdf action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{pdfButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_pdfButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildPdfButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tPdfButton = ButtonBuilder.create()
					.text(iEngine.getString(tPresenter==null ? TAnnotationDefaultValue.TVIEW_pdfButtonText : tPresenter.decorator().pdfButtonText()))
					.id("t-button")
					.build();
		}else
			tPdfButton = ButtonBuilder.create()
			.text(iEngine.getString(text))
			.id("t-button")
			.build();
		
		if(isUserNotAuthorized(TAuthorizationType.EXPORT))
			tPdfButton.setDisable(true);
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
	 * @return the tCleanButton
	 */
	public Button gettCleanButton() {
		return tCleanButton;
	}

	/**
	 * @return the tSearchButton
	 */
	public Button gettSearchButton() {
		return tSearchButton;
	}

	/**
	 * @return the tExcelButton
	 */
	public Button gettExcelButton() {
		return tExcelButton;
	}

	/**
	 * @return the tPdfButton
	 */
	public Button gettPdfButton() {
		return tPdfButton;
	}

	/**
	 * @return the tWordButton
	 */
	public Button gettWordButton() {
		return tWordButton;
	}

	
}
