package com.tedros.app.process;

import javafx.concurrent.Worker;
import javafx.util.Duration;

import com.tedros.ejb.base.info.ITAppInfo;
/**
 * A simple process interface  
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
	 * Return true if the process are scheduled.
	 * */
	boolean isScheduled();
	
	/**
	 * Set schedule information.
	 * */
	void setScheduled(Duration atEveryTime, boolean repeat);
	
	/**
	 * Set informations about the process.
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
	 * Return true if the process are set to auto start.
	 * */
	boolean isAutoStart();
	
	/**
	 * Return the process name
	 * */
	public String getProcessName();
	
	/**
	 * Set a name to the process.
	 * */
	public void setProcessName(String processName);
	
	/**
	 * Return {@link ITAppInfo} with module information 
	 * */
	public ITAppInfo getModuleInfo();
			
	/**
	 * Set {@link ITAppInfo} with module information
	 * */
	public void setModuleInfo(ITAppInfo moduleInfo);
	
	/**
	 * Return the process id
	 * */
	public String getProcessId();
	
}
