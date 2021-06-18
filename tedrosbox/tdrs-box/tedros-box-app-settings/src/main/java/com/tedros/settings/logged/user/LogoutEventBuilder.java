package com.tedros.settings.logged.user;

import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.builder.TBaseEventHandlerBuilder;

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
