/**
 * 
 */
package com.editorweb.module.site.model;

import java.util.List;

import com.editorweb.module.template.model.CssClassMV;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TComponentConfig;

import javafx.beans.binding.Binding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * @author Davis Gordon
 *
 */
public class WebEngineConfig extends TComponentConfig<WebEngine> {

	//private ChangeListener<String> descChl;
	private ChangeListener<String> codeChl;
	private ListChangeListener<CssClassMV> classChl;
	
	public WebEngineConfig(TComponentDescriptor descriptor, WebEngine component) {
		super(descriptor, component);
	}
	
	@Override
	public void config() {
		WebEngine we = super.getComponent();
		ContentMV mv = (ContentMV) super.getDescriptor().getModelView();
		mv.getCode().bindBidirectional(mv.getDesc());
		classChl = change -> {
			while(change.next()) {
				List<CssClassMV> rem = (List<CssClassMV>) change.getRemoved();
				List<CssClassMV> add = (List<CssClassMV>) change.getAddedSubList();
				
				rem.forEach(i ->{
					we.executeScript("removeClass('"+i.getModel().getName()+"')");
				});
				add.forEach(i ->{
					we.executeScript("addClass('"+i.getModel().getName()+"')");
				});
			}
		};
		mv.getCssClassList().addListener(classChl);
		/*descChl = (a, b, n) -> {
			n = cleanHtml(n);
			mv.getCode().removeListener(codeChl);
			mv.getCode().setValue(n);
			insertHtml(n);
			mv.getCode().addListener(codeChl);
		};*/
		codeChl = (a, b, n) -> {
			n = cleanHtml(n);
			//mv.getDesc().removeListener(descChl);
			//mv.getDesc().setValue(n);
			insertHtml(n);
			//mv.getDesc().addListener(descChl);
		};
		//mv.getDesc().addListener(descChl);
		mv.getCode().addListener(codeChl);
		
		we.setJavaScriptEnabled(true);
		we.getLoadWorker().stateProperty().addListener((a, b, n)->{
			if(State.SUCCEEDED==n) {
				//setBridge(we,  (ContentMV) super.getDescriptor().getModelView());
				JSObject window = (JSObject) we.executeScript("window");
				super.getDescriptor().getForm().gettObjectRepository().add("webviewBridge", this);
				window.setMember("tedros", this);
			}
		});
		
	}
	
	public void setHtml(String html) {
		ContentMV mv = (ContentMV) super.getDescriptor().getModelView();
		//mv.getDesc().removeListener(descChl);
		mv.getCode().removeListener(codeChl);
		//mv.getDesc().setValue(html);
		mv.getCode().setValue(html);
		//mv.getDesc().addListener(descChl);
		mv.getCode().addListener(codeChl);
	}

	public void setElementTag(String tag) {
		ContentMV mv = (ContentMV) super.getDescriptor().getModelView();
		mv.getSelected().setValue(tag);
	}
	
	public void remElementTag() {
		ContentMV mv = (ContentMV) super.getDescriptor().getModelView();
		mv.getSelected().setValue(null);
	}
	
	public void log(String text) {
		System.out.println(text);
	}
	
	private String cleanHtml(String s) {
		s = s.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
		s = s.replace("</body></html>", "");
		return s;
	}
	
	private void insertHtml(String  s) {
		WebEngine we = super.getComponent();
		we.executeScript("setElement('"+s.replaceAll("\n", "").replace("'", "\\'")+"')");
	}
}
