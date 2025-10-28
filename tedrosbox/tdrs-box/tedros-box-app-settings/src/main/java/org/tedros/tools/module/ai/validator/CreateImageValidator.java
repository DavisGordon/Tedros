/**
 * 
 */
package org.tedros.tools.module.ai.validator;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.fx.control.validator.TFieldResult;
import org.tedros.fx.control.validator.TValidator;
import org.tedros.tools.ToolsKey;

import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
public class CreateImageValidator extends TValidator {

	/**
	 * @param label
	 * @param value
	 */
	public CreateImageValidator(String label, Object value) {
		super(label, value);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.validator.TValidator#validate()
	 */
	@Override
	public TFieldResult validate() {
		
		ObservableList<TFileEntity> lst = (ObservableList<TFileEntity>) super.getValue();
		TFieldResult r = new TFieldResult();
		if(lst.isEmpty()) {
			r.setValid(false);
			r.setMessage(TLanguage.getInstance()
					.getString(ToolsKey.MESSAGE_AI_CREATE_IMAGE_REQUIRED));
			return r;
		}
		return null;
	}

}
