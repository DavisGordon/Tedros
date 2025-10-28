package org.tedros.tools.logged.user;

import org.tedros.core.context.TedrosContext;
import org.tedros.fx.builder.TBaseEventHandlerBuilder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LogoutEventBuilder extends TBaseEventHandlerBuilder<ActionEvent>{

	@Override
	public EventHandler<ActionEvent> build() {
		return  e -> {
			TedrosContext.logOut();
		};
	}

}
