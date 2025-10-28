package org.tedros.fx.presenter.dynamic.decorator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.context.TSecurityDescriptor;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.annotation.TDefaultValue;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.control.TButton;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

/**
 * The basic decorator of the report view.
 * @author Davis Gordon
 *
 * @param <M>
 */
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
	private Button tOpenExportFolderButton;
	
    private RadioButton tEditModeRadio;
    private RadioButton tReadModeRadio;
    
    private List<TAuthorizationType> userAuthorizations;
    
    /**
     * Returns an authorization list of the logged in user.
     * @return userAuthorizations
     */
    public List<TAuthorizationType> getUserAuthorizations() {
		return userAuthorizations;
	}
    /**
     * Checks the logged in user authorization.
     * @param type
     * @return true if authorized
     */
    public boolean isUserAuthorized(TAuthorizationType type){
    	return userAuthorizations==null || userAuthorizations.contains(type);
    }
    /**
     * Checks the logged in user no authorization.
     * @param type
     * @return true if don't authorized
     */
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
		if(editRadiotext==null)		{
			tEditModeRadio = new RadioButton();
			tEditModeRadio.setText(iEngine.getString(tPresenter==null 
				? TDefaultValue.TVIEW_editModeTitle 
						: tPresenter.decorator().editModeTitle()));
			tEditModeRadio.setId("t-title-label");
		}else {
			tEditModeRadio = new RadioButton();
			tEditModeRadio.setText(iEngine.getString(editRadiotext));
			tEditModeRadio.setId("t-title-label");
		}
		if(readRadioText==null)	{
			tReadModeRadio = new RadioButton();
			tReadModeRadio.setText(iEngine.getString(tPresenter==null 
					? TDefaultValue.TVIEW_readerModeTitle 
							: tPresenter.decorator().readerModeTitle()));
			tReadModeRadio.setId("t-title-label");
		}else {
			tReadModeRadio = new RadioButton();
			tReadModeRadio.setText(iEngine.getString(readRadioText));
			tReadModeRadio.setId("t-title-label");
		}
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
			tCancelButton = new TButton();
			tCancelButton.setText(iEngine.getString(tPresenter==null 
					? TDefaultValue.TVIEW_cancelButtonText 
							: tPresenter.decorator().cancelButtonText()));
			tCancelButton.setId("t-button");
		}else {
			tCancelButton = new TButton();
			tCancelButton.setText(iEngine.getString(text));
			tCancelButton.setId("t-button");
		}
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
			tSearchButton = new TButton();
			tSearchButton.setText(iEngine.getString(tPresenter==null 
					? TDefaultValue.TVIEW_searchButtonText 
							: tPresenter.decorator().searchButtonText()));
			tSearchButton.setId("t-button");
		}else {
			tSearchButton = new TButton();
			tSearchButton.setText(iEngine.getString(text));
			tSearchButton.setId("t-button");
		}
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
			tCleanButton = new TButton();
			tCleanButton.setText(iEngine.getString(tPresenter==null 
					? TDefaultValue.TVIEW_cleanButtonText 
							: tPresenter.decorator().cleanButtonText()));
			tCleanButton.setId("t-button");
		}else {
			tCleanButton = new TButton();
			tCleanButton.setText(iEngine.getString(text));
			tCleanButton.setId("t-button");
		}
		
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
			tExcelButton = new TButton();
			tExcelButton.setText(iEngine.getString(tPresenter==null 
					? TDefaultValue.TVIEW_excelButtonText 
							: tPresenter.decorator().excelButtonText()));
			tExcelButton.setId("t-button");
		}else {
			tExcelButton = new TButton();
			tExcelButton.setText(iEngine.getString(text));
			tExcelButton.setId("t-button");
		}
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
			tWordButton = new TButton();
			tWordButton.setText(iEngine.getString(tPresenter==null 
					? TDefaultValue.TVIEW_wordButtonText 
							: tPresenter.decorator().wordButtonText()));
			tWordButton.setId("t-button");
		}else {
			tWordButton = new TButton();
			tWordButton.setText(iEngine.getString(text));
			tWordButton.setId("t-button");
		}
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
			tPdfButton = new TButton();
			tPdfButton.setText(iEngine.getString(tPresenter==null 
					? TDefaultValue.TVIEW_pdfButtonText 
							: tPresenter.decorator().pdfButtonText()));
			tPdfButton.setId("t-button");
		}else {
			tPdfButton = new TButton();
			tPdfButton.setText(iEngine.getString(text));
			tPdfButton.setId("t-button");
		}
		if(isUserNotAuthorized(TAuthorizationType.EXPORT))
			tPdfButton.setDisable(true);
	}
	/**
	 * <p>
	 * Build a button for the Open Export Folder action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{openExportFolderButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_openExportFolderButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildOpenExportFolderButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tOpenExportFolderButton = new TButton();
			tOpenExportFolderButton.setText(iEngine.getString(tPresenter==null 
					? TDefaultValue.TVIEW_openExportFolderButtonText 
							: tPresenter.decorator().openExportFolderButtonText()));
			tOpenExportFolderButton.setId("t-button");
		}else {
			tOpenExportFolderButton = new TButton();
			tOpenExportFolderButton.setText(iEngine.getString(text));
			tOpenExportFolderButton.setId("t-button");
		}
		
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
		tColapseButton = new TButton();
		tColapseButton.setText(iEngine.getString(StringUtils.isBlank(text) 
				? TDefaultValue.TVIEW_colapse : text));
		tColapseButton.setId("t-button");
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

	/**
	 * @return the tOpenExportFolderButton
	 */
	public Button gettOpenExportFolderButton() {
		return tOpenExportFolderButton;
	}

	
}
