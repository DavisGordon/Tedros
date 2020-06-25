/**
 * 
 */
package com.tedros.core.server.local;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Calendar;

import org.apache.commons.lang3.RandomStringUtils;

import com.tedros.app.process.ITProcess;
import com.tedros.ejb.base.info.ITAppInfo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * @author Davis Gordon
 *
 */
public class TTomeeServerService extends Service<String> implements ITProcess<String>{

	private boolean autoStart = true;
	
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
	protected TedrosTask<String> createTask() {
		return new TedrosTask<String>() {
        	
        	@Override
			public String getServiceNameInfo() {
				return getProcessName();
			};
        	
        	protected String call() throws IOException, MalformedURLException {
        		
        		String outputFolder = System.getProperty("user.home");
        		//String cmd = outputFolder + "\\.tedros\\SERVER\\apache-tomee-webprofile-1.7.2\\bin\\startup.bat";
        		String cmd = outputFolder + "\\.tedros\\SERVER\\apache-tomee-webprofile-1.7.2\\bin\\";
        		//ProcessBuilder processBuilder = new ProcessBuilder();
        		//processBuilder.command("cmd", "/c", "startup.bat");
        		File dir = new File(outputFolder);
        		//processBuilder.directory(dir);
        		
        		
        		Runtime r = Runtime.getRuntime();
        		Process pr = r.exec( "cmd /C startup.bat", null, dir);
        		
        		InputStreamReader isr = new InputStreamReader( pr.getInputStream() );

        		BufferedReader stdInput = new BufferedReader(isr);
        		
        		do{
	        		String s ;
	        		while ((s = stdInput.readLine()) != null) {
	        		    System.out.println(s);
	        		}
        		}while(!isCancelled());
        		
        		stdInput.close();
        		isr.close();
        		
        	    return "Ok";
        	}
		};
	}
	
	
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
		//stop();
		initialize();
	}
	
	@Override
	public void stopProcess(){
		stop();
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
			
			
			String outputFolder = System.getProperty("user.home");
    		String cmd = outputFolder + "\\.tedros\\SERVER\\apache-tomee-webprofile-1.7.2\\bin\\shutdown.bat";
    		
    		Runtime r = Runtime.getRuntime();
    		Process pr;
			try {
				pr = r.exec(cmd);
	    		InputStreamReader isr = new InputStreamReader( pr.getInputStream() );
	
	    		BufferedReader stdInput = new BufferedReader(isr);
	    		
	        	String s ;
	        	while ((s = stdInput.readLine()) != null) {
	        		   System.out.println(s);
	        	}
	    		
	    		
	    		stdInput.close();
	    		isr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cancel();
		}
	}

}
