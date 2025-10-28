package org.tedros.tools.logged.user;

import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.TFxKey;
import org.tedros.fx.builder.TBaseEventHandlerBuilder;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.modal.TModalPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

public class ClearHistoryEventBuilder extends TBaseEventHandlerBuilder<ActionEvent>{

	@Override
	public EventHandler<ActionEvent> build() {
		return  e -> {
			TedrosContext.clearPageHistory();
			Pane f = (Pane) super.getComponentDescriptor().getForm();
			TModalPane mp = new TModalPane((Pane)f.getParent());
			String msg = TLanguage.getInstance().getString(TFxKey.MESSAGE_HITORY_REMOVED);
			mp.showModal(new TMessageBox(msg), true);
		};
	}

}
