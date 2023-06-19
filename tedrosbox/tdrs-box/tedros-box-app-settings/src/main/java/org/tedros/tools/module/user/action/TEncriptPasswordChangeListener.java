/**
 * 
 */
package org.tedros.tools.module.user.action;

import org.tedros.api.form.ITForm;
import org.tedros.fx.annotation.listener.TChangeListener;
import org.tedros.fx.control.TPasswordField;
import org.tedros.tools.module.user.model.TUserMV;
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
			final TUserMV m = (TUserMV) getComponentDescriptor().getModelView();
			if((m.getPassword()!=null && m.getLastPassword()!=null && 
					m.getPassword().getValue()!=null && m.getLastPassword().getValue()==null) ||
					(m.getPassword()!=null && m.getLastPassword()!=null 
					&& m.getPassword().getValue()!=null && m.getLastPassword().getValue()!=null
					&& !m.getPassword().getValue().equals(m.getLastPassword().getValue()))){
				final ITForm form = getComponentDescriptor().getForm();
				final TPasswordField field = (TPasswordField) form.gettFieldBox("password").gettControl();
				String encriptedPassword = TEncriptUtil.encript(field.getText()); 
				field.setText(encriptedPassword);
				m.getLastPassword().setValue(encriptedPassword);
			}	
		}
	}		
}
