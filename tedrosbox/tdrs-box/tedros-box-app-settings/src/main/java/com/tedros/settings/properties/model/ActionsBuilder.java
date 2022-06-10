/**
 * 
 */
package com.tedros.settings.properties.model;

import java.util.ArrayList;
import java.util.List;

import com.tedros.core.TLanguage;
import com.tedros.core.notify.model.TAction;
import com.tedros.core.notify.model.TState;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.TBuilder;
import com.tedros.fxapi.builder.TGenericBuilder;
import com.tedros.fxapi.control.TItem;
import com.tedros.settings.start.TConstant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class ActionsBuilder extends TGenericBuilder<ObservableList> {

	private TLanguage lan = TLanguage.getInstance(TConstant.UUI);
	
	private TItem buildItem(TAction e) {
		return new TItem(lan.getString(e.getValue()),e);
	}

	@Override
	public ObservableList build() {
		List<TItem> l = new ArrayList<>();
		l.add(buildItem(TAction.NONE));
		TNotifyMV m = (TNotifyMV) super.getComponentDescriptor().getModelView();
		TState st = m.getModel().getState();
		if(st!=null)
			switch(st) {
			case SENT:
			case CANCELED:
			case ERROR:
				l.add(buildItem(TAction.SEND));
				l.add(buildItem(TAction.TO_QUEUE));
				l.add(buildItem(TAction.TO_SCHEDULE));
				break;
			case QUEUED:
			case SCHEDULED:
				l.add(buildItem(TAction.CANCEL));
				break;
			}
		else
			l.add(buildItem(TAction.SEND));
			l.add(buildItem(TAction.TO_QUEUE));
			l.add(buildItem(TAction.TO_SCHEDULE));
		return FXCollections.observableArrayList(l);
	}

}
