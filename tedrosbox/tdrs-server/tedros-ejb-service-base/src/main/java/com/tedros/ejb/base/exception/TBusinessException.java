/**
 * 
 */
package com.tedros.ejb.base.exception;

/**
 * @author Davis Gordon
 *
 */
public class TBusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8301843629801226495L;

	/**
	 * 
	 */
	public TBusinessException() {
	}

	/**
	 * @param message
	 */
	public TBusinessException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TBusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
