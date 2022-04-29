/**
 * 
 */
package org.somos.module.mailing.form;

import org.somos.model.CampanhaMailConfig;
import org.somos.module.acao.util.MailingUtil;
import org.somos.module.mailing.model.CampanhaMailConfigModelView;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.TVBoxForm;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

/**
 * @author Davis Gordon
 *
 */
public class CampanhaEmailTemplateForm extends TVBoxForm<CampanhaMailConfigModelView> {

	private TViewMode mode;
	private WebView webView;
	public CampanhaEmailTemplateForm(CampanhaMailConfigModelView modelView) {
		super(modelView);
	}
	
	public CampanhaEmailTemplateForm(CampanhaMailConfigModelView modelView, Boolean reader ) {
		super(modelView, false);
		if(reader)
			settReaderMode();
	}
	
	@SuppressWarnings("rawtypes")
	public CampanhaEmailTemplateForm(ITPresenter presenter, CampanhaMailConfigModelView modelView) {
		super(presenter, modelView);
	}
	
	@SuppressWarnings("rawtypes")
	public CampanhaEmailTemplateForm(ITPresenter presenter, CampanhaMailConfigModelView modelView, Boolean reader ) {
		super(presenter, modelView, reader);
	}
	
	@Override
	public TViewMode gettMode() {
		return mode;
	}
	
	public void settReaderMode(){
		mode = TViewMode.READER;
		super.formEngine.resetForm();
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
		
		ChangeListener<Number> hListener = (arg0, arg1, arg2)-> {
			webView.setPrefHeight((double) arg2);
		};
		((Pane)this).heightProperty().addListener(hListener);
		
		CampanhaMailConfig mailing = gettModelView().getEntity();
		String str = MailingUtil.buildConteudo(mailing);
		
		final StringBuffer sbf = new StringBuffer(str);
		
		webView.sceneProperty().addListener(( observable,  oldValue,  scene) ->{
	            if (scene != null) {
	                webView.setMaxSize(getScene().getWidth(), getScene().getHeight());
	                webView.maxWidthProperty().bind(getScene().widthProperty());
	                webView.maxHeightProperty().bind(getScene().heightProperty());
	          
	            } else {
	                webView.maxWidthProperty().unbind();
	                webView.maxHeightProperty().unbind();
	            }
	        });
		
		webView.getEngine().load(MailingUtil.buildTemplateLink());
		
		webView.getEngine().getLoadWorker().stateProperty().addListener(( ov, oldState, newState)-> {
                if (newState == Worker.State.SUCCEEDED) {
                    webView.getEngine().executeScript("document.getElementById('content').innerHTML = \""+sbf.toString().replace("\r\n", " ").replace("\n", " ")+"\"");
                }});
		
		super.formEngine.setLoaded(true);
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
