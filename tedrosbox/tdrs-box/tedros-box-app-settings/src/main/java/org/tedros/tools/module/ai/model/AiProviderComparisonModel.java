package org.tedros.tools.module.ai.model;

import org.tedros.server.model.ITModel;

public class AiProviderComparisonModel implements ITModel {

	private static final long serialVersionUID = 1L;
	
	private Object component;

	public Object getComponent() {
		return component;
	}

	public void setComponent(Object component) {
		this.component = component;
	}



}
