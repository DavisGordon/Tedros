package org.tedros.ai.service;

import org.tedros.ai.service.langchain.LangChainGrokTerosService;
import org.tedros.ai.service.langchain.LangChainOpenAITerosService;

public class AiTerosServiceFactory {
	
	private AiTerosServiceFactory() {
		
	}
	
	public static IAiTerosService create(String apiKey, String aiModel, String assistantPrompt, AiServiceProvider provider) {
		switch (provider) {
		case GROK:
			return LangChainGrokTerosService.create(apiKey, aiModel, assistantPrompt);
		case OPENAI:
			return LangChainOpenAITerosService.create(apiKey, aiModel, assistantPrompt);
		default:
			throw new IllegalArgumentException("Provider not supported: " + provider);
		}
	}
	
	public static IAiTerosService newInstance(String apiKey, String aiModel, String assistantPrompt, AiServiceProvider provider) {
		switch (provider) {
		case GROK:
			return LangChainGrokTerosService.newInstance(apiKey, aiModel, assistantPrompt);
		case OPENAI:
			return LangChainOpenAITerosService.newInstance(apiKey, aiModel, assistantPrompt);
		default:
			throw new IllegalArgumentException("Provider not supported: " + provider);
		}
	}
	
	/*public static IAiTerosService create(String apiKey, String aiModel, String assistantPrompt, AiServiceProvider provider) {
		switch (provider) {
		case GROK:
			return GrokAiTerosService.create(apiKey, aiModel, assistantPrompt);
		case OPENAI:
			return OpenAIReasoningTerosService.create(apiKey, aiModel, assistantPrompt);
		default:
			throw new IllegalArgumentException("Provider not supported: " + provider);
		}
	}
	
	public static IAiTerosService newInstance(String apiKey, String aiModel, String assistantPrompt, AiServiceProvider provider) {
		switch (provider) {
		case GROK:
			return GrokAiTerosService.newInstance(apiKey, aiModel, assistantPrompt);
		case OPENAI:
			return OpenAIReasoningTerosService.newInstance(apiKey, aiModel, assistantPrompt);
		default:
			throw new IllegalArgumentException("Provider not supported: " + provider);
		}
	}*/

}
