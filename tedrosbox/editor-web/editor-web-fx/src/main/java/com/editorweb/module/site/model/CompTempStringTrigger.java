/**
 * 
 */
package com.editorweb.module.site.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.editorweb.model.ComponentTemplate;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;

/**
 * @author Davis Gordon
 *
 */
public class CompTempStringTrigger extends TTrigger<String> {

	public CompTempStringTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run(String value) {
		ContentMV mv = (ContentMV) super.getForm().gettModelView();
		ComponentTemplate c = mv.getTemplate().getValue();
		if(c!=null) {
			String code = c.getCode();
			String title = mv.getTitle().getValue();
			String desc = mv.getDesc().getValue();
			if(StringUtils.isNotBlank(title))
				code = code.replaceAll("#TITLE#", title);
			if(StringUtils.isNotBlank(desc)) {
				desc = desc.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
				desc = desc.replace("</body></html>", "");
				code = code.replaceAll("#CONTENT#", desc);
			}
			mv.getCode().setValue(code);
		}else {
			mv.getCode().setValue(null);
		}
		
	}

}
