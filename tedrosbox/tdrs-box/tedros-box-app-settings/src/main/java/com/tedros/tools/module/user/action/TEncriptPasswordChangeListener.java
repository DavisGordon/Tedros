/**
 * 
 */
package com.tedros.tools.module.user.action;

import javafx.beans.value.ObservableValue;

import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.control.TPasswordField;
import com.tedros.fxapi.form.ITForm;
import com.tedros.tools.module.user.model.TUserMV;
import com.tedros.util.TEncriptUtil;

/**
 * @author Davis Gordon
 *
 */
public class TEncriptPasswordChangeListener extends TChangeListener<Boolean> {

	@Override
	public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldVal, Boolean newVal) {
		if(!newVal){
			final TUserMV modelView = (TUserMV) getComponentDescriptor().getModelView();
			if((modelView.getPassword()!=null && modelView.getLastPassword()!=null && 
					modelView.getPassword().getValue()!=null && modelView.getLastPassword().getValue()==null) ||
					(modelView.getPassword()!=null && modelView.getLastPassword()!=null 
					&& modelView.getPassword().getValue()!=null && modelView.getLastPassword().getValue()!=null
					&& !modelView.getPassword().getValue().equals(modelView.getLastPassword().getValue()))){
				final ITForm form = getComponentDescriptor().getForm();
				final TPasswordField field = (TPasswordField) form.gettFieldBox("password").gettControl();
				String encriptedPassword = TEncriptUtil.encript(field.getText()); 
				field.setText(encriptedPassword);
				modelView.getLastPassword().setValue(encriptedPassword);
			}	
		}
	}		
}
