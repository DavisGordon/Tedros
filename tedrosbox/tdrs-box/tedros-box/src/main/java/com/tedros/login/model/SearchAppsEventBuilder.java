package com.tedros.login.model;

import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.builder.TBaseEventHandlerBuilder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SearchAppsEventBuilder extends TBaseEventHandlerBuilder<ActionEvent>{

	@Override
	public EventHandler<ActionEvent> build() {
		return  e -> {
			TedrosContext.logOut();
		};
	}

}
