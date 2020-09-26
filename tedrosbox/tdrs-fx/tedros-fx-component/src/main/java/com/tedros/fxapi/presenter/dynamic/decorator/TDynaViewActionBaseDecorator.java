package com.tedros.fxapi.presenter.dynamic.decorator;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

@SuppressWarnings("rawtypes")
public abstract class TDynaViewActionBaseDecorator<M extends TModelView> 
extends TDynaViewSimpleBaseDecorator<M> {
	
	private Button tCleanButton;
	private Button tActionButton;
	private Button tCloseButton;
	
	private RadioButton tEditModeRadio;
    private RadioButton tReadModeRadio;
    
    @Override
    public void setPresenter(TDynaPresenter<M> presenter) {
    	super.setPresenter(presenter);
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
		if(editRadiotext==null) {		
			tEditModeRadio = new RadioButton();
			tEditModeRadio.setText(iEngine.getString(tPresenter==null
					? TAnnotationDefaultValue.TVIEW_editModeTitle 
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
					? TAnnotationDefaultValue.TVIEW_readerModeTitle 
							: tPresenter.decorator().readerModeTitle()));
			tReadModeRadio.setId("t-title-label");
		}else {
			tReadModeRadio = new RadioButton();
			tReadModeRadio.setText(iEngine.getString(readRadioText));
			tReadModeRadio.setId("t-title-label");
		}
		
		
	}
	/**
	 * <p>
	 * Build a button for the close action.<br><br>
	 * 
	 * If the parameter was null this will use the text set up
	 * in @{@link TPresenter}{decorator= @{@link TDecorator}{closeButtonText=''}} 
	 * but if the given {@link TModelView} was not annotated with {@link TPresenter} 
	 * or with a custom view annotation which contains a {@link TPresenter} 
	 * a default string (TAnnotationDefaultValue.TVIEW_closeButtonText) will be used.<br><br> 
	 * 
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildCloseButton(String text) {
		if(text==null){
			final TPresenter tPresenter = getPresenter().getPresenterAnnotation();
			tCloseButton = new Button();
			tCloseButton.setText(iEngine.getString(tPresenter==null 
							? TAnnotationDefaultValue.TVIEW_closeButtonText 
									: tPresenter.decorator().closeButtonText()));
			tCloseButton.setId("t-button");
		}else {
			tCloseButton = new Button();
			tCloseButton.setText(iEngine.getString(text));
			tCloseButton.setId("t-button");
		}
	}
	
	/**
	 * <p>
	 * Build a button for the actio action.<br><br>
	 * This will initialize with "t-button" id.
	 * </p>
	 * */
	public void buildActionButton(String text) {
		
		tActionButton = new Button();
		tActionButton.setText(iEngine.getString(text));
		tActionButton.setId("t-button");
		
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
			tCleanButton = new Button();
			tCleanButton.setText(iEngine.getString(tPresenter==null 
					? TAnnotationDefaultValue.TVIEW_cleanButtonText 
							: tPresenter.decorator().cleanButtonText()));
			tCleanButton.setId("t-button");
		}else {
			tCleanButton = new Button();
			tCleanButton.setText(iEngine.getString(text));
			tCleanButton.setId("t-button");
		}
		
	}

	/**
	 * @return the tCleanButton
	 */
	public Button gettCleanButton() {
		return tCleanButton;
	}


	/**
	 * @return the tActionButton
	 */
	public Button gettActionButton() {
		return tActionButton;
	}

	/**
	 * @return the tCloseButton
	 */
	public Button gettCloseButton() {
		return tCloseButton;
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
	
	

	
}
