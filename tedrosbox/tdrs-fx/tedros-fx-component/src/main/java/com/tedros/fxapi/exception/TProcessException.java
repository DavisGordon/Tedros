package com.tedros.fxapi.exception;

public class TProcessException extends TException {

	private static final long serialVersionUID = 4053809883360237297L;
	
	public TProcessException() {
		
	}
	
	public TProcessException(String message) {
		super(message);
	}
	
	public TProcessException(Throwable e) {
		super(e);
	}
	
	public TProcessException(String message, Throwable e) {
		super(message, e);
	}
	
	public TProcessException(String message, String applicationMessage) {
		super(message, applicationMessage);
		
	}
	
	public TProcessException(Throwable e, String applicationMessage) {
		super(e, applicationMessage);
	}
	
	public TProcessException(Throwable e, String message, String applicationMessage) {
		super(e, message, applicationMessage);
		
	}
	
}
