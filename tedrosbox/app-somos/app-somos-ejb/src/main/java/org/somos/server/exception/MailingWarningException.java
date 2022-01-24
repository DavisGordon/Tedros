/**
 * 
 */
package org.somos.server.exception;

/**
 * @author Davis Gordon
 *
 */
public class MailingWarningException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4374404009496701650L;

	/**
	 * 
	 */
	public MailingWarningException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public MailingWarningException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public MailingWarningException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MailingWarningException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MailingWarningException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
