package com.tedros.core.context;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.tedros.app.process.ITProcess;
/**
 * The process manager
 * 
 * @author Davis Gordon
 * */
@SuppressWarnings({ "rawtypes" })
public final class TedrosProcessManager {

	private TedrosProcessManager() {
		
	}
	
	/**
	 * Returns the process instance of the class.
	 * */
	public static final ITProcess getProcess(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe);
	}
	/**
	 * The global process string property with messages of the all process in execution.
	 * */
	public static final StringProperty globalProcessStringProperty(){
		return TedrosProcess.globalProcessStringProperty;
	}
	
	/**
	 * messageProperty of the process to bind.
	 * */
	public static final ReadOnlyStringProperty processMessageProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).messageProperty();
	}
	
	/**
	 * valueProperty of the process to bind.
	 * */
	public static final ReadOnlyObjectProperty processValueProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).valueProperty();
	}
	
	/**
	 * progressProperty of the process to bind.
	 * */
	public static final ReadOnlyDoubleProperty processProgressProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).progressProperty();
	}
	
	/**
	 * stateProperty of the process to bind.
	 * */
	public static final ReadOnlyObjectProperty processStateProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).stateProperty();
	}
	
	/**
	 * runningProperty of the process to bind.
	 * */
	public static final ReadOnlyBooleanProperty processRunningProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).runningProperty();
	}
	
	/**
	 * titleProperty of the process to bind
	 * */
	public static final ReadOnlyStringProperty processTitleProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).titleProperty();
	}
	
	/**
	 * totalWorkProperty of the process to bind
	 * */
	public static final ReadOnlyDoubleProperty processTotalWorkProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).totalWorkProperty();
	}
	
	
	/**
	 * <pre>Creates an instance of the process class and add it in the pool.
	 * 
	 * Will be started from scheduler if the process is scheduled 
	 * or will be started if the process is to be auto start.
	 * </pre>
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * */
	public static final void addProcess(Class<? extends ITProcess> classe) throws InstantiationException, IllegalAccessException{
		
		if(TedrosProcess.moduleProcessMap.containsKey(classe))
			return;
		
		ITProcess process = classe.newInstance();
		process.messageProperty().addListener(new ChangeListener<String>() {
			@Override
	    	public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				TedrosProcess.globalProcessStringProperty.set(arg2);
	    	}
		});
		
		if(process.isScheduled())
			process.startFromScheduler();
		else if(process.isAutoStart())
			process.startProcess();
		
		TedrosProcess.moduleProcessMap.put(classe, process);
		
	}
	/**
	 * Stop the process
	 * */
	public static final void stopProcess(Class classe){
		if(TedrosProcess.moduleProcessMap.containsKey(classe))
			TedrosProcess.moduleProcessMap.get(classe).stopProcess();
	}
	/**
	 * Start the process
	 * */
	public static final void startProcess(Class classe){
		if(TedrosProcess.moduleProcessMap.containsKey(classe))
			TedrosProcess.moduleProcessMap.get(classe).startProcess();
	}
	/**
	 * Stop and remove the process
	 * */
	public static final void removeProcess(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return;
		stopProcess(classe);
		TedrosProcess.moduleProcessMap.remove(classe);
	}
}
