package org.tedros.ai.service;

import java.util.Map;

import org.slf4j.Logger;
import org.tedros.core.TCoreKeys;
import org.tedros.core.TLanguage;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class AiServiceBase implements IAiServiceBase {
	
	static final Logger log = TLoggerUtil.getLogger(AiServiceBase.class);

	protected static final int MAX_RECURSION_DEPTH = 5;
	protected static final String NO_RESPONSE = TLanguage.getInstance().getString(TCoreKeys.AI_NO_RESPONSE);
	protected static final double SUMMARIZATION_THRESHOLD_PERCENT = 0.65;
	private static final Map<String, Integer> MODEL_CONTEXT_LENGTHS = AiHelper.buildModelContextLengths();
	
	protected String assistantPrompt;
	protected final ObjectMapper mapper = new ObjectMapper();
	protected ObservableList<String> reasoningsMessageProperty = FXCollections.observableArrayList();

	protected AiServiceBase() {
		super();
	}

	@Override
	public void setPromptAssistant(String prompt) {
	    assistantPrompt = prompt;
	    log.info("Assistant prompt em uso: {}", prompt);
	}

	@Override
	public ObservableList<String> reasoningsMessageProperty() {
		return reasoningsMessageProperty;
	}
	
	public abstract String getAiModel();
	
	public abstract void setAiModel(String aiModel);

	/**
	 * Calcula o threshold dinâmico com base no modelo atual
	 */
	protected long getDynamicSummarizationThreshold() {
	    if (getAiModel() == null || getAiModel().isBlank()) {
	    	log.warn("Modelo não definido, usando fallback de 85k tokens para sumarização.");
	        return 85_000;
	    }
	
	    String key = getAiModel().toLowerCase();
	
	    // Match exato primeiro
	    Integer max = MODEL_CONTEXT_LENGTHS.get(key);
	    if (max != null) {
	        long calc = (long) (max * SUMMARIZATION_THRESHOLD_PERCENT);
	        log.debug("Threshold calculado: {} tokens ({}% de {} para modelo {})", 
	        		calc, (SUMMARIZATION_THRESHOLD_PERCENT/100),  max, getAiModel());
	        
	        return calc;
	    }
	
	    // Match parcial 
	    max = MODEL_CONTEXT_LENGTHS.entrySet().stream()
	        .filter(e -> key.contains(e.getKey()))
	        .map(Map.Entry::getValue)
	        .findFirst()
	        .orElse(null);
	    
	    if (max != null) {
	        long calc = (long) (max * SUMMARIZATION_THRESHOLD_PERCENT);
	        log.debug("Threshold calculado: {} tokens ({}% de {} para modelo {})", 
	        		calc, (SUMMARIZATION_THRESHOLD_PERCENT/100),  max, getAiModel());
	        
	        return calc;
	    }
	
	    // Fallbacks por família
	    if (key.contains("grok-code")) return (long) (256_000 * 0.65);
	    if (key.contains("grok-4")) return (long) (200_000_000 * 0.65);
	    if (key.contains("gpt-5") || key.contains("o3")) return (long) (200_000 * 0.65);
	    if (key.contains("gpt-4o") || key.contains("o1") || key.contains("gpt-4.1") || key.contains("o4")) return (long) (128_000 * 0.65);
	    if (key.contains("gpt-4-turbo")) return (long) (128_000 * 0.65);
	    if (key.contains("gpt-4")) return (long) (8_192 * 0.65);
	    if (key.contains("gpt-3.5")) return (long) (16_385 * 0.65);
	
	    log.warn("Modelo desconhecido para contexto: {}. Usando 85k como fallback.", getAiModel());
	    return 85_000;
	}

}