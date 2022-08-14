package org.tedros.app.process;

import org.tedros.server.info.ITAppInfo;

import javafx.concurrent.Worker;
import javafx.util.Duration;
/**
 * A process contract.  
 * 
 * @author Davis Gordon
 * */
public interface ITProcess<V> extends Worker<V> {

	/**
 	 * Start the process from scheduler
	 * */
	void startFromScheduler();
	
	/**
	 * Start the process
	 * */
	void startProcess();
	
	/**
	 * Returns true if the process are scheduled.
	 * */
	boolean isScheduled();
	
	/**
	 * Schedule the process
	 * */
	void setScheduled(Duration atEveryTime, boolean repeat);
	
	/**
	 * Sets informations about the process.
	 * */
	void setProcessInfo(String processName, ITAppInfo moduleInfo);
	
	/**
	 * Stop the process.
	 * */
	void stopProcess();
	
	/**
	 * If set to true the process will auto start at Tedros initialization. 
	 * */
	void setAutoStart(boolean autoStart);
	
	/**
	 * Returns true if the process are set to auto start.
	 * */
	boolean isAutoStart();
	
	/**
	 * Returns the process name
	 * */
	public String getProcessName();
	
	/**
	 * Sets a name to the process.
	 * */
	public void setProcessName(String processName);
	
	/**
	 * Returns {@link ITAppInfo} with module information 
	 * */
	public ITAppInfo getModuleInfo();
			
	/**
	 * Sets {@link ITAppInfo} with module information
	 * */
	public void setModuleInfo(ITAppInfo moduleInfo);
	
	/**
	 * Returns the process id
	 * */
	public String getProcessId();
	
}
