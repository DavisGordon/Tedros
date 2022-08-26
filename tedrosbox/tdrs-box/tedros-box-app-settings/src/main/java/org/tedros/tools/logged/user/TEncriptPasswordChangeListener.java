/**
 * 
 */
package org.tedros.tools.logged.user;

import org.tedros.api.form.ITForm;
import org.tedros.fx.annotation.listener.TChangeListener;
import org.tedros.fx.control.TPasswordField;
import org.tedros.util.TEncriptUtil;

import javafx.beans.value.ObservableValue;

/**
 * @author Davis Gordon
 *
 */
public class TEncriptPasswordChangeListener extends TChangeListener<Boolean> {

	@Override
	public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldVal, Boolean newVal) {
		if(!newVal){
			final TUserSettingModelView modelView = (TUserSettingModelView) getComponentDescriptor().getModelView();
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
