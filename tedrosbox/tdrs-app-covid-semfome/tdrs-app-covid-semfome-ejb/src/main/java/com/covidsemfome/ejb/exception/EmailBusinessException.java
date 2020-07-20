/**
 * 
 */
package com.covidsemfome.ejb.exception;

/**
 * @author Davis Gordon
 *
 */
public class EmailBusinessException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2391016396156217537L;

	/**
	 * 
	 */
	public EmailBusinessException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public EmailBusinessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public EmailBusinessException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmailBusinessException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public EmailBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
