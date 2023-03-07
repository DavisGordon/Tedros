/**
 * 
 */
package org.tedros.core.ai.model;

import java.util.ArrayList;
import java.util.List;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class TImageResult implements ITModel {

	private static final long serialVersionUID = -6877305621095887716L;

	/**
     * The creation time in epoch seconds.
     */
    private Long createdAt;

    /**
     * List of image results.
     */
    private List<TImage> data;
    
    private String log;
    
    private boolean success;
	/**
	 * 
	 */
	public TImageResult() {
	}
	/**
	 * @param createdAt
	 */
	public TImageResult(Long createdAt) {
		this.success = true;
		this.createdAt = createdAt;
	}
	
	/**
	 * @param log
	 * @param success
	 */
	public TImageResult(String log, boolean success) {
		this.log = log;
		this.success = success;
	}
	public void addImage(String value, TResponseFormat format) {
		if(data==null)
			data = new ArrayList<>();
		data.add(new TImage(value, format));
	}
	/**
	 * @return the createdAt
	 */
	public Long getCreatedAt() {
		return createdAt;
	}
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * @return the data
	 */
	public List<TImage> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<TImage> data) {
		this.data = data;
	}
	/**
	 * @return the log
	 */
	public String getLog() {
		return log;
	}
	/**
	 * @param log the log to set
	 */
	public void setLog(String log) {
		this.log = log;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

}
