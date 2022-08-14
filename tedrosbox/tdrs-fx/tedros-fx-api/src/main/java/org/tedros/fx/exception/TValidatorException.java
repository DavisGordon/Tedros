package org.tedros.fx.exception;


public class TValidatorException extends TException {

	private static final long serialVersionUID = 4053809883360237297L;
	
	private Object validatorResults;
	
	
	public TValidatorException(Object validatorResults) {
		super();
		this.validatorResults = validatorResults;
	}
	
	
	public Object getValidatorResults() {
		return validatorResults;
	}
		
}

