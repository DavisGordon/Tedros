/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/11/2013
 */
package com.tedros.fxapi.exception;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TException extends Throwable {

	private static final long serialVersionUID = 9133851635313239167L;
	
	private String applicationMessage;
	
	public TException() {
		
	}
	
	public TException(String message) {
		super(message);
	}
	
	public TException(String message, String applicationMessage) {
		super(message);
		this.applicationMessage = applicationMessage;
	}
	
	public TException(Throwable e) {
		super(e);
	}
	
	public TException(String message, Throwable e) {
		super(message, e);
	}
	
	public TException(Throwable e, String applicationMessage) {
		super(e.getMessage(), e);
		this.applicationMessage = applicationMessage;
	}
	
	public TException(Throwable e, String message, String applicationMessage) {
		super(message, e);
		this.applicationMessage = applicationMessage;
	}

	public String getApplicationMessage() {
		return applicationMessage;
	}

	public void setApplicationMessage(String applicationMessage) {
		this.applicationMessage = applicationMessage;
	}
	
}


