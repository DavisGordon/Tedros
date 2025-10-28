/**
 * 
 */
package org.tedros.core.ai.model;

/**
 * @author Davis Gordon
 *
 */
public enum TOpenAiError {

	ACCOUNT_ID ("401", "#{error.ai.account}"),
	QUOTA_OR_OVERLOADED ("429", "#{error.ai.quota.overloaded}"),
	SERVER_ERROR ("500", "#{error.ai.server}");
	
	private String statusCode;
	private String msgKey;
	
	private TOpenAiError(String statusCode, String msgKey) {
		this.statusCode = statusCode;
		this.msgKey = msgKey;
	}
	
	public static TOpenAiError value(String statusCode) {
		for(TOpenAiError e : values())
			if(e.statusCode.equals(statusCode))
				return e;
		return null;
	}

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @return the msgKey
	 */
	public String getMsgKey() {
		return msgKey;
	}
}
