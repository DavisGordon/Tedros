/**
 * 
 */
package org.tedros.test.ai.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Davis Gordon
 *
 */
public abstract class TJsonModel<T> implements Serializable {

	private static final long serialVersionUID = 3145363913096000781L;

	private String model;
	private String assistantMessage;
	private String status;
	private List<T> data;
	
	public void addData(T item) {
		if(data==null)
			data = new ArrayList<>();
		data.add(item);
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}


	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TJsonModel [" + (model != null ? "model=" + model + ", " : "") + (data != null ? "data=" + data : "")
				+ "]";
	}

	/**
	 * @return the assistantMessage
	 */
	public String getAssistantMessage() {
		return assistantMessage;
	}

	/**
	 * @param assistantMessage the assistantMessage to set
	 */
	public void setAssistantMessage(String assistantMessage) {
		this.assistantMessage = assistantMessage;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
