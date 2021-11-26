/**
 * 
 */
package com.editorweb.module.site.model;

import com.tedros.common.model.TFileEntity;
import com.tedros.editorweb.model.ComponentTemplate;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.property.TSimpleFileProperty;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class CompTempTrigger extends TTrigger<Change> {

	public CompTempTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void run(Change change) {
		ObservableList value = change!= null ? change.getList() : null;
		ContentMV mv = (ContentMV) super.getForm().gettModelView();
		ComponentTemplateFindMV c = value!=null && value.size()==1 ? (ComponentTemplateFindMV) value.get(0) : null;
		TFileEntity e = c!=null ? c.getImgExample().getValue() : new TFileEntity();
		TSimpleFileProperty<TFileEntity> p = (TSimpleFileProperty<TFileEntity>) mv.getProperty("templateImg");
		p.setValue(e);
		
		if(c!=null) {
			WebView wv = (WebView) super.getForm().gettFieldBox("webview").gettControl();
			WebEngine en = wv.getEngine();
			en.executeScript("clear()");
			String code = c.getModel().getCode();
			mv.getCode().setValue(code);
			
		}else if(change!=null) {
			mv.getCode().setValue("");
		}else {
			ComponentTemplate ct = mv.getTemplate().getValue();
			if(ct!=null && ct.getImgExample()!=null && !ct.getImgExample().isNew()) {
				p.setValue(ct.getImgExample());
			}
		}
	}
}
