/**
 * 
 */
package org.tedros.core.ai.model;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public abstract class TBaseResult implements ITModel {

	private static final long serialVersionUID = 3732086291879005175L;

	private String log;
    
    private boolean success;
	/**
	 * 
	 */
	public TBaseResult() {
	}
	/**
	 * @param log
	 * @param success
	 */
	public TBaseResult(String log, boolean success) {
		this.log = log;
		this.success = success;
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
