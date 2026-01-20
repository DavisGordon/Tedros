package org.tedros.ai.openai.model;

import java.util.List;

import org.tedros.common.model.TFileContentInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

/**
 * Resultado de uma execução de função/tool.
 * Refatorado para usar Builder pattern e reduzir boilerplate.
 */

@Getter
@Builder
@ToString
public class ToolCallResult {

    private final String message;

    private final Object result; 

    @Singular("filesContentInfo")
    private final List<TFileContentInfo> filesContentInfo;

    @Builder.Default 
    private final boolean revertToTheAIModelInCaseOfSuccess = true;

}
