package org.tedros.ai.function.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("An empty model class, used when no parameters are needed.")
public class Empty {

	@JsonPropertyDescription("This param is not used.")
	private String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	

}
