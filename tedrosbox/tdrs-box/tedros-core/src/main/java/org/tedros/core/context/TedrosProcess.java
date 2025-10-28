package org.tedros.core.context;

import java.util.HashMap;
import java.util.Map;

import org.tedros.app.process.ITProcess;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A holder for all process 
 * */
final class TedrosProcess {
	
	@SuppressWarnings("rawtypes")
	protected static Map<Class, ITProcess> moduleProcessMap = new HashMap<Class, ITProcess>(0);
	protected final static StringProperty globalProcessStringProperty = new SimpleStringProperty();

	private TedrosProcess() {
		
	}
	/**
	 * Stop all process
	 * */
	@SuppressWarnings("rawtypes")
	public final static void stopAllServices(){
		for(Class classe : moduleProcessMap.keySet()){
			ITProcess service = moduleProcessMap.get(classe);
			service.stopProcess();
		}
	}
	
}
