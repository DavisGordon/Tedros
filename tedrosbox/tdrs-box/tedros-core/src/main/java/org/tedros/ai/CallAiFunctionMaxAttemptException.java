package org.tedros.ai;

public class CallAiFunctionMaxAttemptException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CallAiFunctionMaxAttemptException() {
		super("Reached the max attempts to call AI function!");
	}

}
