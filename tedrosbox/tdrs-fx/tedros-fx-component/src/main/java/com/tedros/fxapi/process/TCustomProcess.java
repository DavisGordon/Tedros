package com.tedros.fxapi.process;



public abstract class TCustomProcess<IN, OUT> extends TProcess<OUT>  {
	
	private IN object;
	private String operationID;
	
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
	
	public void setObjectToProcess(IN objectToProcess){
		this.object = objectToProcess;
	}
	
	public IN getObjectToProcess() {
		return object;
	}

	public String getOperationID() {
		return operationID;
	}

	public void setOperationID(String operationID) {
		this.operationID = operationID;
	}
	
	
}
