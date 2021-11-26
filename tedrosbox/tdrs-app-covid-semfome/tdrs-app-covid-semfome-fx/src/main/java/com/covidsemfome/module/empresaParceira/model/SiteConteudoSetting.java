/**
 * 
 */
package com.covidsemfome.module.empresaParceira.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.covidsemfome.module.web.model.CssClassMV;
import com.covidsemfome.parceiro.model.ComponentTemplate;
import com.covidsemfome.parceiro.model.CssClass;
import com.tedros.fxapi.control.THTMLEditor;
import com.tedros.fxapi.control.TPickListField;
import com.tedros.fxapi.control.TTextAreaField;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TSetting;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * @author Davis Gordon
 *
 */
public class SiteConteudoSetting extends TSetting {

	private ChangeListener<ComponentTemplate> tempChl;
	private ChangeListener<String> codeChl;
	private ListChangeListener<CssClassMV> classChl;
	
	public SiteConteudoSetting(TComponentDescriptor descriptor) {
		super(descriptor);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		WebEngine we = getWebEngineFromHtmlEditor();
		try {
			we.load("file:C:/Users/Davis Gordon/git/Tedros/tedrosbox/tdrs-app-covid-semfome/tdrs-app-covid-semfome-webapp/src/main/webapp/temp/edit.html");
		}catch(Exception e) {}
		SiteConteudoModelView mv = getModelView();
		//mv.getCode().bindBidirectional(mv.getDesc());
		tempChl = (a, b, n) -> {
			if(n!=null) {
				this.insertHtml(n.getCode());
			}
		};
		mv.getTemplate().addListener(tempChl);
		classChl = change -> {
			while(change.next()) {
				List<CssClassMV> rem = (List<CssClassMV>) change.getRemoved();
				List<CssClassMV> add = (List<CssClassMV>) change.getAddedSubList();
				
				rem.forEach(i ->{
					try {
						we.executeScript("removeClass('"+i.getModel().getName()+"')");
					}catch(JSException e) {
						log(e.getMessage());
						e.printStackTrace();
					}
				});
				add.forEach(i ->{
					try {
						we.executeScript("addClass('"+i.getModel().getName()+"')");
					}catch(JSException e) {
						log(e.getMessage());
						e.printStackTrace();
					}
				});
			}
		};
		mv.getCssClassList().addListener(classChl);
		codeChl = (a, b, n) -> {
			if(StringUtils.isNotBlank(n)) {
				n = cleanHtml(n);
				try {
					insertHtml(n);
				}catch(JSException e) {
					log(e.getMessage());
					e.printStackTrace();
				}
			}else {
				removeSelection();
			}
		};
		//mv.getCode().addListener(codeChl);
		
		
		
		we.setJavaScriptEnabled(true);
		we.getLoadWorker().stateProperty().addListener((a, b, n)->{
			if(State.SUCCEEDED==n) {
				JSObject window = (JSObject) we.executeScript("window");
				//getForm().gettObjectRepository().add("webviewBridge", this);
				window.setMember("tedros", this);
				
				if(mv.getCode().getValue()!=null) {
					String c = cleanHtml(mv.getCode().getValue());
					try {
						insertHtml(c);
					}catch(JSException e) {
						log(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		});
		
	}
	
	public void prepareSave() {
		WebEngine we = getWebEngineFromHtmlEditor();
		we.executeScript("selectRoot()");
	}
	
	private void removeSelection(){
		WebEngine we = getWebEngineFromHtmlEditor();
		we.executeScript("removeSel()");
		we.executeScript("clear()");
		this.setCssClass(null);
	}
	
	public void setHtml(String html) {
		SiteConteudoModelView mv = getModelView();
		mv.getCode().removeListener(codeChl);
		mv.getCode().setValue(html);
		mv.getCode().addListener(codeChl);
	}

	public void setElementTag(String tag) {
		SiteConteudoModelView mv = getModelView();
		mv.getSelected().setValue(tag);
	}
	
	public void remElementTag() {
		SiteConteudoModelView mv = getModelView();
		mv.getSelected().setValue(null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setCssClass(String css) {
		TPickListField pl = super.getControl("cssClassList");
		if(StringUtils.isNotBlank(css)) {
			List<CssClassMV> lst = new ArrayList<>();
			List<String> classes = Arrays.asList(css.split(" "));
			ObservableList<CssClassMV> src = pl.gettSourceList();
			classes.forEach(e->{
				CssClassMV c = new CssClassMV(new CssClass(e));
				int idx = src.indexOf(c);
				if(idx!=-1)
					lst.add(src.get(idx));
				else
					lst.add(c);
			});
			pl.settSelectedList(FXCollections.observableArrayList(lst));
		}else {
			pl.tClearSelectedList();
		}
	}
	
	public void log(String text) {
		TTextAreaField f = super.getControl("log");
		f.appendText("\n"+text);
		f.setScrollTop(400);
	}
	
	private String cleanHtml(String s) {
		if(s!=null) {
			s = s.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
			s = s.replace("</body></html>", "");
		}
		return s;
	}
	
	private void insertHtml(String  s)  {
		if(s==null)
			return;
		WebEngine we = getWebEngineFromHtmlEditor();
		we.executeScript("setElement('"+s.replaceAll("\n", "").replace("'", "\\'")+"')");
	}

	private WebEngine getWebEngineFromHtmlEditor() {
		THTMLEditor e = super.getControl("editor");
		WebView wv = (WebView) e.gettHTMLEditor().lookup("WebView");
		return wv.getEngine();
	}

	/**
	 * @return
	 */
	private WebEngine getWebEngine() {
		return ((WebView)super.getControl("webview")).getEngine();
	}
}
