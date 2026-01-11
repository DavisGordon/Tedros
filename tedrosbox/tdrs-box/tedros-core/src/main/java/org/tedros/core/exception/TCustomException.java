/**
 * 
 */
package org.tedros.core.exception;

/**
 * 
 */
public class TCustomException extends RuntimeException {
	
	private static final long serialVersionUID = 5493578161692187557L;
	
	public static final String DEFAULT_CODE = "TCUSTOMEXCEPTION";
	
	private final String customMessage;	
	private final String code;
	
	public TCustomException(String customMessage) {
		super(customMessage);
		this.customMessage = customMessage;
		this.code = DEFAULT_CODE;
	}
	
	public TCustomException(String code, String customMessage) {
		super(customMessage);
		this.customMessage = customMessage;
		this.code = code;
	}
	
	public String getCustomMessage() {
		return customMessage;
	}
	
	public String getCode() {
		return code;
	}

}
