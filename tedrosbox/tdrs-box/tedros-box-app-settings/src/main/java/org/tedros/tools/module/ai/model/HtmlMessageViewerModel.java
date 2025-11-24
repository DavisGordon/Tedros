package org.tedros.tools.module.ai.model;

import org.tedros.server.model.ITModel;

public class HtmlMessageViewerModel implements ITModel {

	private static final long serialVersionUID = 1L;
	
	private String webContent;

	public String getWebContent() {
		return webContent;
	}

	public void setWebContent(String webContent) {
		this.webContent = webContent;
	}

}
