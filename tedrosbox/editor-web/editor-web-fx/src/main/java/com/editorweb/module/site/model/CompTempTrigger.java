/**
 * 
 */
package com.editorweb.module.site.model;

import com.tedros.common.model.TFileEntity;
import com.tedros.editorweb.model.ComponentTemplate;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;

import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
public class CompTempTrigger extends TTrigger<ObservableList> {

	public CompTempTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run(ObservableList value) {
		ComponentTemplateFindMV c = value!=null && value.size()==1 ? (ComponentTemplateFindMV) value.get(0) : null;
		TFileEntity e = c!=null ? c.getImgExample().getValue() : new TFileEntity();
		TSimpleFileEntityProperty<TFileEntity> p = (TSimpleFileEntityProperty<TFileEntity>)
				super.getForm().gettModelView().getProperty("templateImg");
		p.setValue(e);
	}

}
