package com.tedros.fxapi.process;


/**
 * A custom process receive and return a pre defined object
 *
 * @author Davis Gordon
 * */
public abstract class TCustomProcess<IN, OUT> extends TProcess<OUT>  {
	
	private IN object;
	private String operationID;
	
	/**
	 * Execute 
	 * */
	public abstract OUT process(IN obj);
	
	@Override
	protected TTaskImpl<OUT> createTask() {
		
		return new TTaskImpl<OUT>() {

			@Override
			public String getServiceNameInfo() {
				return getProcessName();
			}

			@Override
			protected OUT call() throws Exception {
				return process(object);
			}
			
		};	
	}
	/**
	 * Set an object to be process
	 * 
	 * @param objectToProcess
	 * */
	public void setObjectToProcess(IN objectToProcess){
		this.object = objectToProcess;
	}
	/**
	 * Return the object to process
	 * */
	public IN getObjectToProcess() {
		return object;
	}

	/**
	 * Return the operation id
	 * */
	public String getOperationID() {
		return operationID;
	}

	/**
	 * Define an operation id
	 * */
	public void setOperationID(String operationID) {
		this.operationID = operationID;
	}
	
	
}
