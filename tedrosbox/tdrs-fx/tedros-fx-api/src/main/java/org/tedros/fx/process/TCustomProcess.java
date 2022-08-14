package org.tedros.fx.process;

import org.tedros.fx.presenter.behavior.TActionType;

/**
 * A custom process receive and return a pre defined object
 *
 * @author Davis Gordon
 * */
public abstract class TCustomProcess<IN, OUT> extends TProcess<OUT>  {
	
	private IN object;
	private TActionType actionType;
	
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
	 * @return the actionType
	 */
	public TActionType getActionType() {
		return actionType;
	}

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(TActionType actionType) {
		this.actionType = actionType;
	}

	
	
	
}
