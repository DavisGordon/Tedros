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
    
    private String errorCode;
    
    
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
	
	public TBaseResult(String log, String errorCode) {
		super();
		this.log = log;
		this.errorCode = errorCode;
		this.success = false;
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
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public TOpenAiError getErrorType() {
		String e = this.getStatusCode();
		return e!=null 
				? TOpenAiError.value(e)
						: null;
	}
	
	public String getCode() {
		return errorCode!=null 
				? errorCode.split(":")[1]
						: null;
	}
	
	public String getStatusCode() {
		return errorCode!=null 
				? errorCode.split(":")[0]
						: null;
	}

}
