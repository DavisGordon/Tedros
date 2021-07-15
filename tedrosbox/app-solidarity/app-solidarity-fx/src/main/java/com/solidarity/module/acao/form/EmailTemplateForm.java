/**
 * 
 */
package com.solidarity.module.acao.form;

import com.solidarity.model.Mailing;
import com.solidarity.module.acao.model.MailingModelView;
import com.solidarity.module.acao.util.MailingUtil;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.TVBoxForm;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebViewBuilder;

/**
 * @author Davis Gordon
 *
 */
public class EmailTemplateForm extends TVBoxForm<MailingModelView> {

	private TViewMode mode;
	private WebView webView;
	private SimpleBooleanProperty loaded = new SimpleBooleanProperty(false);
	

	public EmailTemplateForm(MailingModelView modelView) {
		super(modelView);
		loaded.bind(super.tLoadedProperty());
		setId(null);
	}
	
	public EmailTemplateForm(MailingModelView modelView, Boolean reader ) {
		super(modelView, reader);
		setId(null);
		if(!reader)
			loaded.bind(super.tLoadedProperty());
	}
	
	@SuppressWarnings("rawtypes")
	public EmailTemplateForm(ITPresenter presenter, MailingModelView modelView) {
		super(presenter, modelView);
		loaded.bind(super.tLoadedProperty());
		setId(null);
	}
	
	@SuppressWarnings("rawtypes")
	public EmailTemplateForm(ITPresenter presenter, MailingModelView modelView, Boolean reader ) {
		super(presenter, modelView, reader);
		
		if(!reader) {
			setId(null);
			loaded.bind(super.tLoadedProperty());
		}
	}
	
	@Override
	public TViewMode gettMode() {
		return mode;
	}
	
	@SuppressWarnings("resource")
	public void settReaderMode(){
		mode = TViewMode.READER;
		if(loaded==null)
			loaded = new SimpleBooleanProperty(false);
		this.loaded.setValue(false);
		if(getChildren()!=null){
			try{
				getChildren().clear();
			}catch(IllegalArgumentException e){
				// bug
				getChildren().clear();
			}
		}
		setId("t-reader");
		
		webView = new WebView();
		tAddFormItem(webView);
		webView.setDisable(true);
		
		ChangeListener<Number> hListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				webView.setPrefHeight((double) arg2);
			}
		};
		((Pane)this).heightProperty().addListener(hListener);
		
		Mailing mailing = gettModelView().getEntity();
		String str = MailingUtil.buildConteudo(mailing);
		
		final StringBuffer sbf = new StringBuffer(str);
		
		webView.sceneProperty().addListener(new ChangeListener<Scene>() {

	        @Override
	        public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene scene) {
	            if (scene != null) {
	                webView.setMaxSize(getScene().getWidth(), getScene().getHeight());
	                webView.maxWidthProperty().bind(getScene().widthProperty());
	                webView.maxHeightProperty().bind(getScene().heightProperty());
	          
	            } else {
	                webView.maxWidthProperty().unbind();
	                webView.maxHeightProperty().unbind();
	            }
	        }
	    });
		
		webView.getEngine().load(MailingUtil.buildTemplateLink());
		
		webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    webView.getEngine().executeScript("document.getElementById('content').innerHTML = \""+sbf.toString().replace("\r\n", " ").replace("\n", " ")+"\"");
		
                }
            }
        });
		
		this.loaded.setValue(true);
		tInitializeReader();
	}
	
	@Override
	public ReadOnlyBooleanProperty tLoadedProperty() {
		return this.loaded;
	}
	
	
	public void settEditMode(){
		mode = TViewMode.EDIT;
		super.settEditMode();
		setId(null);
	}

	@Override
	public void tInitializeForm() {
		formatForm();
	}

	@Override
	public void tInitializeReader() {
		formatForm();
		
	}
	
	private void formatForm() {
		autosize();
		setSpacing(8);
		setPadding(new Insets(10, 10, 10, 10));
		setMaxHeight(Double.MAX_VALUE);
		setMaxWidth(Double.MAX_VALUE);
		addEndSpacer();
	}
}
