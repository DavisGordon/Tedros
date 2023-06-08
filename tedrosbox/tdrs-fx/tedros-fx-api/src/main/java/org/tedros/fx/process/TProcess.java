package org.tedros.fx.process;

import java.util.Calendar;

import org.apache.commons.lang3.RandomStringUtils;
import org.tedros.app.process.ITProcess;
import org.tedros.server.info.ITAppInfo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * The Tedros process ia a JavaFX Service 
 * and can be used to running process in background.
 * 
 * @author Davis Gordon
 * */
public abstract class TProcess<V> extends Service<V> implements ITProcess<V> {
	
	private boolean autoStart = false;
	
	private Timeline timeline = null;
	
	private String processName;
	private ITAppInfo moduleInfo;
	private String processID;
	
	{		
		processID = String.valueOf(Calendar.getInstance().getTimeInMillis())+RandomStringUtils.randomAlphanumeric(4);		
	}
	
	/**
	 * <pre>Create the task to be executed</pre>
	 * @return TTaskImpl 
	 * */
	protected abstract TTaskImpl<V> createTask();
	
	
	@Override
	public String getProcessId() {
		return processID;
	}
	
	@Override
	public void startFromScheduler() {
		if(isScheduled())
			timeline.playFrom("end");
	}
	
	@Override
	public boolean isScheduled() {
		return (timeline!=null);
	}
	
	@Override
	public void setProcessInfo(String processName, ITAppInfo moduleInfo) {
		this.processName = processName;
		this.moduleInfo = moduleInfo;
	}
	
	@Override
	public void setScheduled(Duration atEveryTime, boolean repeat) {
		timeline = new Timeline(new KeyFrame(atEveryTime, new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				initialize();
			}
			
		}));
		if(repeat)
			timeline.setCycleCount(Animation.INDEFINITE);
	}
	
	@Override
	public void setAutoStart(boolean autoStart) {
		this.autoStart = autoStart;
	}
	
	@Override
	public boolean isAutoStart() {
		return autoStart;
	}
	
	@Override
	public void startProcess(){
		stop();
		initialize();
	}
	
	@Override
	public void stopProcess(){
		stop();
		initialize();
	}
	
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public ITAppInfo getModuleInfo() {
		return moduleInfo;
	}

	public void setModuleInfo(ITAppInfo moduleInfo) {
		this.moduleInfo = moduleInfo;
	}

	private void initialize() {
		if(this.getState().equals(State.READY))
			start();
		else
			restart();
	}

	private void stop() {
		if(!this.getState().equals(State.READY)){
			cancel();
		}
	}
}
