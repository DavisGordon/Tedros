package org.tedros.ai.service;

import org.tedros.ai.service.grok.GrokAiTerosService;
import org.tedros.ai.service.openai.reasoning.OpenAIReasoningTerosService;

public class AiTerosServiceFactory {
	
	private AiTerosServiceFactory() {
		
	}
	
	public static IAiTerosService create(String apiKey, String aiModel, String assistantPrompt, AiServiceProvider provider) {
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
	}

}
