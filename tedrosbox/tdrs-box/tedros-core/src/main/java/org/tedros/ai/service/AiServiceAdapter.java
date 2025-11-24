package org.tedros.ai.service;

import java.util.List;

import org.tedros.ai.function.TFunction;

import com.openai.client.OpenAIClient;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseReasoningItem;
import com.openai.models.responses.ResponseUsage;

import javafx.beans.property.ReadOnlyLongProperty;

public interface AiServiceAdapter {

	OpenAIClient getClient();

	ResponseUsage getLastUsage();

	void functions(List<TFunction> functions);

	List<ResponseOutputItem> sendToolCallResult(String model, List<ResponseInputItem> messages);

	List<ResponseOutputItem> sendChatRequest(String model, List<ResponseInputItem> messages);

	ResponseInputItem buildReasoningMessage(ResponseReasoningItem reasoning);

	ResponseInputItem buildAssistantMessage(String content);

	ResponseInputItem buildUserMessage(String content);

	ResponseInputItem buildSysMessage(String content);

	ReadOnlyLongProperty totalInputTokenProperty();

}