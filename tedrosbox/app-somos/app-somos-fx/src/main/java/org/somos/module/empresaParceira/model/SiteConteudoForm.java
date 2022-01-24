/**
 * 
 */
package org.somos.module.empresaParceira.model;

import org.somos.parceiro.model.SiteConteudo;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.TVBoxForm;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

/**
 * @author Davis Gordon
 *
 */
public class SiteConteudoForm extends TVBoxForm<SiteConteudoModelView> {

	private TViewMode mode;
	private WebView webView;
	private SiteConteudoForm _this;
	
	public SiteConteudoForm(SiteConteudoModelView modelView) {
		super(modelView);
		_this = this;
	}
	
	public SiteConteudoForm(SiteConteudoModelView modelView, Boolean reader ) {
		super(modelView, false);
		_this = this;
		if(reader)
			settReaderMode();
	}
	
	@SuppressWarnings("rawtypes")
	public SiteConteudoForm(ITPresenter presenter, SiteConteudoModelView modelView) {
		super(presenter, modelView);
		_this = this;
	}
	
	@SuppressWarnings("rawtypes")
	public SiteConteudoForm(ITPresenter presenter, SiteConteudoModelView modelView, Boolean reader ) {
		super(presenter, modelView, reader);
		_this = this;
	}
	
	@Override
	public TViewMode gettMode() {
		return mode;
	}
	
	@SuppressWarnings("resource")
	public void settReaderMode(){
		mode = TViewMode.READER;
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
		
		SiteConteudo entity = gettModelView().getEntity();
		//String str = SiteConteudoUtil.buildConteudo(mailing);
		
		//final StringBuffer sbf = new StringBuffer(str);
		
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
		
		webView.getEngine().load(SiteConteudoUtil.buildTemplateLink());
		
		webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    webView.getEngine().executeScript("load("+entity.getId().toString()+")");
		
                }
            }
        });
		
		
		tInitializeReader();
	}
	
	public void settEditMode(){
		mode = TViewMode.EDIT;
		super.settEditMode();
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
