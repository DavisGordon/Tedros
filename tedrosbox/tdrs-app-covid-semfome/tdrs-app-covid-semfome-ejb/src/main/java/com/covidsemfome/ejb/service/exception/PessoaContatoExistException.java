/**
 * 
 */
package com.covidsemfome.ejb.service.exception;

/**
 * @author Davis Gordon
 *
 */
public class PessoaContatoExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2412898395401481359L;

	/**
	 * 
	 */
	public PessoaContatoExistException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public PessoaContatoExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public PessoaContatoExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PessoaContatoExistException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PessoaContatoExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
