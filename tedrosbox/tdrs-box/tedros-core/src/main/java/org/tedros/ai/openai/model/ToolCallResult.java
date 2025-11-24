package org.tedros.ai.openai.model;

import java.util.List;

import org.tedros.common.model.TFileContentInfo;

/**
 * Resultado de uma execução de função/tool.
 */
public class ToolCallResult {

    private final String name;
    private Object result;
    private List<TFileContentInfo> filesContentInfo;

    public ToolCallResult(String name, Object result) {
        this.name = name;
        this.result = result;
    }
    
    public ToolCallResult(String name, List<TFileContentInfo> filesContentInfo) {
        this.name = name;
        this.filesContentInfo = filesContentInfo;
    }
    
    public ToolCallResult(String name, Object result, List<TFileContentInfo> filesContentInfo) {
        this.name = name;
        this.result = result;
        this.filesContentInfo = filesContentInfo;
    }

    public String getName() {
        return name;
    }

    public Object getResult() {
        return result;
    }

	public List<TFileContentInfo> getFilesContentInfo() {
		return filesContentInfo;
	}

}
