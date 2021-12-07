package com.covidsemfome.module.empresaParceira.model;

import com.tedros.fxapi.builder.TBaseEventHandlerBuilder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EventAdd extends TBaseEventHandlerBuilder<ActionEvent>{

	@Override
	public EventHandler<ActionEvent> build() {
		return  e -> {
			SiteConteudoSetting s = (SiteConteudoSetting) super.getComponentDescriptor().getForm().gettSetting();
		};
	}

}
