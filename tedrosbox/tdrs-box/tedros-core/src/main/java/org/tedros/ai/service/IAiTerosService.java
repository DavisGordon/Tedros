package org.tedros.ai.service;

public interface IAiTerosService extends IAiServiceBase {

	void createFunctionExecutor(org.tedros.ai.function.TFunction<?>... functions);

	String call(String userPrompt, String sysPrompt);

	void setAiModel(String model);

	String getAiModel();

}