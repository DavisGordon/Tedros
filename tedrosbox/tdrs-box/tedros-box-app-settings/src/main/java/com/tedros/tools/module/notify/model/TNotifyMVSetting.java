/**
 * 
 */
package com.tedros.tools.module.notify.model;

import com.tedros.core.TLanguage;
import com.tedros.core.notify.model.TAction;
import com.tedros.core.notify.model.TState;
import com.tedros.fxapi.control.TVerticalRadioGroup;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TSetting;
import com.tedros.tools.start.TConstant;

import javafx.scene.control.RadioButton;

/**
 * @author Davis Gordon
 *
 */
public class TNotifyMVSetting extends TSetting {

	public TNotifyMVSetting(TComponentDescriptor descriptor) {
		super(descriptor);
	}

	private TLanguage lan = TLanguage.getInstance(TConstant.UUI);
	
	private RadioButton buildItem(TAction e) {
		RadioButton rb = new RadioButton(lan.getString(e.getValue()));
		rb.setUserData(e.getValue());
		return rb;
	}

	@Override
	public void run() {
		
		TNotifyMV m = (TNotifyMV) super.getModelView();
		m.getState().addListener((a,o,st)->{
			buildActions(st);
		});
		
		TState st = m.getModel().getState();
		buildActions(st);
	}

	/**
	 * @param st
	 */
	private void buildActions(TState st) {
		
		TVerticalRadioGroup rg = super.getControl("action");
		rg.getChildren().clear();
		rg.addRadioButton(buildItem(TAction.NONE));
		if(st!=null)
			switch(st) {
			case SENT:
			case CANCELED:
			case ERROR:
				rg.addRadioButton(buildItem(TAction.SEND));
				rg.addRadioButton(buildItem(TAction.TO_QUEUE));
				rg.addRadioButton(buildItem(TAction.TO_SCHEDULE));
				break;
			case QUEUED:
			case SCHEDULED:
				rg.addRadioButton(buildItem(TAction.CANCEL));
				break;
			}
		else {
			rg.addRadioButton(buildItem(TAction.SEND));
			rg.addRadioButton(buildItem(TAction.TO_QUEUE));
			rg.addRadioButton(buildItem(TAction.TO_SCHEDULE));
		}
	}

}
