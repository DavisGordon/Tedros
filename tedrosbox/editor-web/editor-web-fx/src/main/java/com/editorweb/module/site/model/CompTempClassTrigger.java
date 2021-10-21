/**
 * 
 */
package com.editorweb.module.site.model;

import java.util.List;

import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;

import javafx.collections.ListChangeListener.Change;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Davis Gordon
 *
 */
public class CompTempClassTrigger extends TTrigger<Change> {

	public CompTempClassTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run(Change change) {
		while(change.next()) {
			List rem = change.getRemoved();
			List add = change.getAddedSubList();
			
			WebView wv = (WebView) super.getForm().gettFieldBox("webview").gettControl();
			WebEngine en = wv.getEngine();
			rem.forEach(i ->{
				en.executeScript("removeClass('"+i+"')");
			});
			add.forEach(i ->{
				en.executeScript("addClass('"+i+"')");
			});
		}
	}

}
