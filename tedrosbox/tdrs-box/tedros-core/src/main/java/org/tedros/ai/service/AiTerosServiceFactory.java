package org.tedros.ai.service;

import org.tedros.ai.service.grok.GrokAiTerosService;
import org.tedros.ai.service.langchain.LangChainGeminiTerosService;
import org.tedros.ai.service.langchain.LangChainGrokTerosService;
import org.tedros.ai.service.langchain.LangChainOpenAITerosService;
import org.tedros.ai.service.openai.reasoning.OpenAIReasoningTerosService;

public class AiTerosServiceFactory {
	
	private static final String PROVIDER_NOT_SUPPORTED = "Provider not supported: ";

	private AiTerosServiceFactory() {
		
	}
	
	public static IAiTerosService createWithLangChain4jAdapters(String apiKey, String aiModel, String assistantPrompt, AiServiceProvider provider) {
		switch (provider) {
		case GROK:
			return LangChainGrokTerosService.create(apiKey, aiModel, assistantPrompt);
		case OPENAI:
			return LangChainOpenAITerosService.create(apiKey, aiModel, assistantPrompt);
		case GEMINI:
			return LangChainGeminiTerosService.create(apiKey, aiModel, assistantPrompt);
		default:
			throw new IllegalArgumentException(PROVIDER_NOT_SUPPORTED + provider);
		}
	}
	
	public static IAiTerosService newInstanceWithLangChain4jAdapters(String apiKey, String aiModel, String assistantPrompt, AiServiceProvider provider) {
		switch (provider) {
		case GROK:
			return LangChainGrokTerosService.newInstance(apiKey, aiModel, assistantPrompt);
		case OPENAI:
			return LangChainOpenAITerosService.newInstance(apiKey, aiModel, assistantPrompt);
		case GEMINI:
			return LangChainGeminiTerosService.newInstance(apiKey, aiModel, assistantPrompt);
		default:
			throw new IllegalArgumentException(PROVIDER_NOT_SUPPORTED + provider);
		}
	}
	
	public static IAiTerosService createWithOpenaiJavaAdapters(String apiKey, String aiModel, String assistantPrompt, AiServiceProvider provider) {
		switch (provider) {
		case GROK:
			return GrokAiTerosService.create(apiKey, aiModel, assistantPrompt);
		case OPENAI:
			return OpenAIReasoningTerosService.create(apiKey, aiModel, assistantPrompt);
		default:
			throw new IllegalArgumentException(PROVIDER_NOT_SUPPORTED + provider);
		}
	}
	
	public static IAiTerosService newInstanceWithOpenaiJavaAdapters(String apiKey, String aiModel, String assistantPrompt, AiServiceProvider provider) {
		switch (provider) {
		case GROK:
			return GrokAiTerosService.newInstance(apiKey, aiModel, assistantPrompt);
		case OPENAI:
			return OpenAIReasoningTerosService.newInstance(apiKey, aiModel, assistantPrompt);
		default:
			throw new IllegalArgumentException(PROVIDER_NOT_SUPPORTED + provider);
		}
	}

}
