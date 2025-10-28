/**
 * 
 */
package org.tedros.tools.module.notify.model;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.TLanguage;
import org.tedros.core.notify.model.TAction;
import org.tedros.core.notify.model.TState;
import org.tedros.fx.control.TVRadioGroup;
import org.tedros.fx.form.TSetting;
import org.tedros.tools.start.TConstant;

import javafx.scene.control.RadioButton;

/**
 * @author Davis Gordon
 *
 */
public class TNotifyMVSetting extends TSetting {

	public TNotifyMVSetting(ITComponentDescriptor descriptor) {
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
		
		TVRadioGroup rg = super.getControl("action");
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
