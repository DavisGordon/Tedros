package org.tedros.ai.openai.model;

import java.util.List;

import org.tedros.common.model.TFileContentInfo;

/**
 * Resultado de uma execução de função/tool.
 */
public class ToolCallResult {

    private final String message;
    private Object result;
    private List<TFileContentInfo> filesContentInfo;
    private boolean revertToTheAIModelInCaseOfSuccess;

    public ToolCallResult(String message, Object result, boolean revertToTheAIModelInCaseOfSuccess) {
        this.message = message;
        this.result = result;
        this.revertToTheAIModelInCaseOfSuccess = revertToTheAIModelInCaseOfSuccess;
    }
    
    public ToolCallResult(String message, List<TFileContentInfo> filesContentInfo, boolean revertToTheAIModelInCaseOfSuccess) {
        this.message = message;
        this.filesContentInfo = filesContentInfo;
        this.revertToTheAIModelInCaseOfSuccess = revertToTheAIModelInCaseOfSuccess;
    }
    
    public ToolCallResult(String message, Object result, List<TFileContentInfo> filesContentInfo, boolean revertToTheAIModelInCaseOfSuccess) {
        this.message = message;
        this.result = result;
        this.filesContentInfo = filesContentInfo;
        this.revertToTheAIModelInCaseOfSuccess = revertToTheAIModelInCaseOfSuccess;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }

	public List<TFileContentInfo> getFilesContentInfo() {
		return filesContentInfo;
	}
	
	public boolean itShouldRevertToTheAIModelInCaseOfSuccess() {
		return revertToTheAIModelInCaseOfSuccess;
	}

}
