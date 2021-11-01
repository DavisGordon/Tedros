/**
 * 
 */
package com.editorweb.module.site.model;

import java.util.List;

import com.editorweb.module.template.model.CssClassMV;
import com.tedros.common.model.TFileEntity;
import com.tedros.editorweb.model.CssClass;
import com.tedros.fxapi.control.TPickListField;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;
import com.tedros.fxapi.util.TModelViewUtil;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.FXCollections;
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
		TSimpleFileEntityProperty<TFileEntity> p = (TSimpleFileEntityProperty<TFileEntity>) mv.getProperty("templateImg");
		p.setValue(e);
		
		if(c!=null) {
			WebView wv = (WebView) super.getForm().gettFieldBox("webview").gettControl();
			WebEngine en = wv.getEngine();
			en.executeScript("clear()");
			String code = c.getModel().getCode();
			mv.getCode().setValue(code);
			
		}else if(change!=null) {
			mv.getCode().setValue(null);
			WebView wv = (WebView) super.getForm().gettFieldBox("webview").gettControl();
			WebEngine en = wv.getEngine();
			en.executeScript("clear()");
			//updateCssPickList(null);
		}
		
	}

	/**
	 * @param c
	 */
	@SuppressWarnings("unchecked")
	private void updateCssPickList(ComponentTemplateFindMV c) {
		TPickListField plf = (TPickListField) super.getTarget().gettControl();
		plf.gettSelectedList().clear();
		if(c!=null && c.getModel().getCssClassList()!=null) {
			List<CssClassMV> lst = new TModelViewUtil(CssClassMV.class, CssClass.class, 
							c.getModel().getCssClassList()).convertToModelViewList();
			plf.settSourceList(FXCollections.observableArrayList(lst));
	
		}
	}
}
