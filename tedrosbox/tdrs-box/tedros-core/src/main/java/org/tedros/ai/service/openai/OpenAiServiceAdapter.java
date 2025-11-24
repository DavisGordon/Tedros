package org.tedros.ai.service.openai;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.service.AiHelper;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.openai.client.OpenAIClient;
import com.openai.models.Reasoning;
import com.openai.models.Reasoning.Summary;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.EasyInputMessage;
import com.openai.models.responses.FunctionTool;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseReasoningItem;
import com.openai.models.responses.ResponseUsage;
import com.openai.models.responses.Tool;
import com.openai.models.responses.ToolChoiceOptions;

import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleLongProperty;

/**
 * Adaptador genérico para criar requisições de chat.
 */
public class OpenAiServiceAdapter {

    private static final Logger LOGGER = TLoggerUtil.getLogger(OpenAiServiceAdapter.class);

    private static ObjectMapper mapper = new ObjectMapper();
    private static JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);

    private final OpenAIClient client;

    private ResponseCreateParams.Builder builder;

    private List<Tool> chatCompletionTools;
    
    private SimpleLongProperty totalInputTokenProperty = new SimpleLongProperty(0);
    
    private ResponseUsage lastUsage;
    
    private String aiModel;

    public OpenAiServiceAdapter(String apiKey, String aiModel) {
        this.client = OpenAIClientFactory.getClient(apiKey);
        this.aiModel = aiModel;
    }

    public OpenAIClient getClient() {
		return client;
	}
    
    public void setAiModel(String model) {
		this.aiModel = model;
	}
    
    public String getAiModel() {
		return aiModel;
	}
    
    public ResponseUsage getLastUsage() {
		return lastUsage;
	}
    
    @SuppressWarnings("rawtypes")
    public void functions(List<TFunction> functions) {
        this.chatCompletionTools = functions.stream()
            .map(f -> {
                try {
                	
                	FunctionTool.Builder builder = FunctionTool.builder()
							.name(f.getName())
						    .description(f.getDescription())
						    .strict(false);
                	
                    // Generate JSON Schema from the model class
                    JsonSchema jsonSchema = schemaGen.generateSchema(f.getModel());

                    // Convert schema to Map<String, Object>
                    Map<String, Object> schemaMap = mapper.convertValue(jsonSchema, new TypeReference<Map<String, Object>>() {});

                    // Recursively add 'required' arrays to the schema and all nested objects
                    AiHelper.addRequiredFieldsRecursively(schemaMap, f.getModel());

                    // Manually adjust to match original constraints
                    schemaMap.put("additionalProperties", false);

                    // Build FunctionParameters from the schema map
                    FunctionTool.Parameters.Builder paramsBuilder = FunctionTool.Parameters.builder();
                    schemaMap.forEach((key, value) -> paramsBuilder.putAdditionalProperty(key, AiHelper.toJsonValue(value)));

                    builder.parameters(paramsBuilder.build());
                    
                    FunctionTool functionTool = builder.build();
                    
                    //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(functionTool));
					
					return Tool.ofFunction(functionTool);

                } catch (Exception e) {
                    throw new RuntimeException("Erro ao gerar schema para função: " + f.getName(), e);
                }
            })
            .toList();
    }

        

    public List<ResponseOutputItem> sendToolCallResult(List<ResponseInputItem> messages) {
        try {
        	
            builder = (builder == null)
                ? ResponseCreateParams.builder()
                    .model(aiModel)
                    .input(ResponseCreateParams.Input.ofResponse(messages))
                    .temperature(1.0)
                    .tools(chatCompletionTools)
                    .toolChoice(ToolChoiceOptions.AUTO)
                    .parallelToolCalls(true)
                    .reasoning(Reasoning.builder()
                    		.effort(ReasoningEffort.MEDIUM)
                    		.summary(Summary.AUTO)
                    		.build())
                    
                : builder.input(ResponseCreateParams.Input.ofResponse(messages)); 

            Response response = client.responses().create(builder.build());
            
            verifyUsageTokens(messages, response);

            return response.output();

        } catch (Exception e) {
            LOGGER.error("Erro ao chamar OpenAI: {}", e.getMessage());
            throw new RuntimeException("Erro ao chamar OpenAI", e);
        }
    }

    public List<ResponseOutputItem> sendChatRequest(List<ResponseInputItem> messages) {
        try {
            if (builder == null) {
                builder = chatCompletionTools != null
                    ? ResponseCreateParams.builder()
                        .model(aiModel)
                        .input(ResponseCreateParams.Input.ofResponse(messages))  // Corrigido: inputOfResponse -> Input.ofResponse
                        .temperature(1.0)
                        .tools(chatCompletionTools)
                        .toolChoice(ToolChoiceOptions.AUTO)
                        .parallelToolCalls(true)  // ← ATIVADO
                        .reasoning(Reasoning.builder()
                        		.effort(ReasoningEffort.MEDIUM)
                        		.summary(Summary.AUTO)
                        		.build())
                    : ResponseCreateParams.builder()
                        .model(aiModel)
                        .input(ResponseCreateParams.Input.ofResponse(messages))
                        .temperature(1.0)
                        .reasoning(Reasoning.builder()
                        		.effort(ReasoningEffort.MEDIUM)
                        		.summary(Summary.AUTO)
                        		.build());
            } else {
            
            	builder.input(ResponseCreateParams.Input.ofResponse(messages));
            }
            
            Response response = client.responses().create(builder.build());
            
            verifyUsageTokens(messages, response);
            
            return response.output();

        } catch (Exception e) {
            LOGGER.error("Erro ao chamar OpenAI: {}", e.getMessage());
            throw new RuntimeException("Erro ao chamar OpenAI", e);
        }
    }
    
    public ResponseInputItem buildReasoningMessage(ResponseReasoningItem reasoning) {
        return ResponseInputItem.ofReasoning(reasoning);
    }

    public ResponseInputItem buildAssistantMessage(String content) {
        return ResponseInputItem.ofEasyInputMessage(EasyInputMessage.builder()
            .role(EasyInputMessage.Role.ASSISTANT)
            .content(content)
            .build());
    }

    public ResponseInputItem buildUserMessage(String content) {
        return ResponseInputItem.ofEasyInputMessage(EasyInputMessage.builder()
            .role(EasyInputMessage.Role.USER)
            .content(content)
            .build());
    }

    public ResponseInputItem buildSysMessage(String content) {
        return ResponseInputItem.ofEasyInputMessage(EasyInputMessage.builder()
            .role(EasyInputMessage.Role.SYSTEM)
            .content(content)
            .build());
    }
    
    public ReadOnlyLongProperty totalInputTokenProperty() {
    	return totalInputTokenProperty;
    }
    
    private void verifyUsageTokens(List<ResponseInputItem> messages, Response response) {
		Optional<ResponseUsage> optRespUsage = response.usage();
		if(optRespUsage.isPresent()) {
			lastUsage = optRespUsage.get();
			 
			totalInputTokenProperty.setValue(lastUsage.inputTokens());
			// Log token usage
		    LOGGER.info("Total messages: {}, Usage Tokens: inputTokens={}, outputTokens={}, totalTokens={}", 
					messages.size(),
					lastUsage.inputTokens(),
					lastUsage.outputTokens(),
					lastUsage.totalTokens());
		}
	}
    
    
}