package org.tedros.ai.service.grok;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.service.AiHelper;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientImpl;
import com.openai.client.okhttp.OkHttpClient;
import com.openai.core.ClientOptions;
import com.openai.models.FunctionDefinition;
import com.openai.models.FunctionParameters;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionFunctionTool;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.chat.completions.ChatCompletionTool;
import com.openai.models.files.FileCreateParams;
import com.openai.models.files.FileObject;
import com.openai.models.files.FilePurpose;

import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleLongProperty;

/**
 * Adaptador para usar o SDK da OpenAI com a API do Grok (xAI)
 */
public class GrokAiServiceAdapter {

    private static final Logger LOGGER = TLoggerUtil.getLogger(GrokAiServiceAdapter.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);

    private final OpenAIClient client;
    private List<ChatCompletionTool> tools = new ArrayList<>();

    private SimpleLongProperty totalInputTokenProperty = new SimpleLongProperty(0);
    private long lastInputTokens = 0;
    private long lastOutputTokens = 0;
    
    private String aiModel;

    public GrokAiServiceAdapter(String apiKey, String aiModel) {
    	this.aiModel = aiModel;
        this.client = new OpenAIClientImpl(ClientOptions.builder()
                .baseUrl("https://api.x.ai/v1")
                .httpClient(OkHttpClient.builder().build())
                .apiKey(apiKey)
                .build());
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

    public ReadOnlyLongProperty totalInputTokenProperty() {
        return totalInputTokenProperty;
    }

    public long getLastInputTokens() { return lastInputTokens; }
    public long getLastOutputTokens() { return lastOutputTokens; }

    @SuppressWarnings("rawtypes")
    public void functions(List<TFunction> functions) {
    	this.tools = functions.stream()
                .map(f -> {
                    try {
                    	
                    	JsonSchema jsonSchema = schemaGen.generateSchema(f.getModel());
                        Map<String, Object> schemaMap = mapper.convertValue(jsonSchema, new TypeReference<>() {});

                        AiHelper.addRequiredFieldsRecursively(schemaMap, f.getModel());

                        FunctionParameters.Builder paramsBuilder = FunctionParameters.builder();
                        schemaMap.forEach((k, v) -> paramsBuilder.putAdditionalProperty(k, AiHelper.toJsonValue(v)));
                    	
                        ChatCompletionTool functionTool = ChatCompletionTool.ofFunction(ChatCompletionFunctionTool.builder()
                                .function(FunctionDefinition.builder()
                                		.name(f.getName())
                                		.description(f.getDescription())
                                		.parameters(paramsBuilder.build())
                                		.build())                            
                                .build());
                        
                        return functionTool;

                    } catch (Exception e) {
                        throw new RuntimeException("Erro ao gerar schema para função: " + f.getName(), e);
                    }
                })
                .toList();
    	
    }

    public ChatCompletion sendChatRequest(List<ChatCompletionMessageParam> messages) {
        try {
            var builder = ChatCompletionCreateParams.builder()
                    .model(aiModel)
                    .messages(messages)
                    .temperature(0.3)
                    .topP(1.0)
                    .n(1);

            if (!tools.isEmpty()) {
                builder.tools(tools);
                //.toolChoice(ChatCompletionToolChoiceOption.Auto.AUTO);
            }

            ChatCompletion response = client.chat().completions().create(builder.build());

            var usage = response.usage();
            if (usage.isPresent()) {
                lastInputTokens = usage.get().promptTokens();
                lastOutputTokens = usage.get().completionTokens();
                totalInputTokenProperty.set(lastInputTokens);
                LOGGER.info("Tokens → input: {}, output: {}, total: {}", lastInputTokens, lastOutputTokens, usage.get().totalTokens());
            }

            return response;

        } catch (Exception e) {
            LOGGER.error("Erro ao chamar Grok: {}", e.getMessage());
            throw new RuntimeException("Erro ao chamar Grok", e);
        }
    }

    public FileObject uploadFile(byte[] bytes, String filename) {
    	Path tempFile = null;
        try {
            // 1. Cria um arquivo temporário com a extensão correta
            String prefix = "grok_upload_";
            String suffix = filename.contains(".") ? filename.substring(filename.lastIndexOf(".")) : ".tmp";
            tempFile = Files.createTempFile(prefix, suffix);
            
            // 2. Escreve os bytes no arquivo
            Files.write(tempFile, bytes);

            // 3. Faz o upload usando o Path (o SDK extrai o nome e tipo automaticamente)
            var params = FileCreateParams.builder()
                    .file(tempFile) // Passa o Path, não o InputStream
                    .purpose(FilePurpose.ASSISTANTS) // 'assistants' é o padrão para Code Interpreter/Analysis
                    .build();
            
            return client.files().create(params);

        } catch (Exception e) {
            throw new RuntimeException("Erro no upload de arquivo para Grok: " + e.getMessage(), e);
        } finally {
            // 4. Limpeza: Deleta o arquivo temporário
            if (tempFile != null) {
                try {
                    java.nio.file.Files.deleteIfExists(tempFile);
                } catch (Exception ignored) {}
            }
        }
    }
    
}