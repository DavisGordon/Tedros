package org.tedros.ai.service;

import org.tedros.ai.service.grok.GrokAiTerosService;
import org.tedros.ai.service.openai.OpenAITerosService;

public class AiTerosServiceFactory {
	
	private AiTerosServiceFactory() {
		
	}
	
	public static IAiTerosService create(String apiKey, String aiModel, String assistantPrompt, AiServiceProvider provider) {
		switch (provider) {
		case GROK:
			return GrokAiTerosService.create(apiKey, aiModel, assistantPrompt);
		case OPENAI:
			return OpenAITerosService.create(apiKey, aiModel, assistantPrompt);
		default:
			throw new IllegalArgumentException("Provider not supported: " + provider);
		}
	}

}
