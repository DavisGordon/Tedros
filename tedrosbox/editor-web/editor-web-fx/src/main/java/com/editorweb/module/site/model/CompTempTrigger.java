/**
 * 
 */
package com.editorweb.module.site.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.common.model.TFileEntity;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Davis Gordon
 *
 */
public class CompTempTrigger extends TTrigger<Change> {

	public CompTempTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run(Change change) {
		ObservableList value = change!= null ? change.getList() : null;
		ContentMV mv = (ContentMV) super.getForm().gettModelView();
		ComponentTemplateFindMV c = value!=null && value.size()==1 ? (ComponentTemplateFindMV) value.get(0) : null;
		TFileEntity e = c!=null ? c.getImgExample().getValue() : new TFileEntity();
		TSimpleFileEntityProperty<TFileEntity> p = (TSimpleFileEntityProperty<TFileEntity>) mv.getProperty("templateImg");
		p.setValue(e);
		
		if(c!=null) {
			String code = c.getModel().getCode();
			
			
			/*String title = mv.getTitle().getValue();
			String desc = mv.getDesc().getValue();
			if(StringUtils.isNotBlank(title))
				code = code.replaceAll("#TITLE#", title);
			if(StringUtils.isNotBlank(desc)) {
				desc = desc.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
				desc = desc.replace("</body></html>", "");
				code = code.replaceAll("#CONTENT#", desc);
			}*/
			mv.getDesc().setValue(code);
			mv.getCode().setValue(code);
			WebView wv = (WebView) super.getForm().gettFieldBox("webview").gettControl();
			WebEngine en = wv.getEngine();
			en.executeScript("clear()");
			en.executeScript("addFirst('"+code.replaceAll("\n", "")+"')");
			
		}else if(change!=null) {
			mv.getCode().setValue(null);
			WebView wv = (WebView) super.getForm().gettFieldBox("webview").gettControl();
			WebEngine en = wv.getEngine();
			en.executeScript("clear()");
		}
		
	}

}
