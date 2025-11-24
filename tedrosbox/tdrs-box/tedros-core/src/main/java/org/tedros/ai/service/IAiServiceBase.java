package org.tedros.ai.service;

import javafx.collections.ObservableList;

public interface IAiServiceBase {

	void setPromptAssistant(String prompt);

	ObservableList<String> reasoningsMessageProperty();

}