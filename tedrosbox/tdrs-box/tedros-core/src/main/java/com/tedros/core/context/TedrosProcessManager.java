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
 * Gerenciador de processos globais
 * @author Davis Gordon
 * 
 * */
@SuppressWarnings({ "rawtypes" })
public final class TedrosProcessManager {

	private TedrosProcessManager() {
		
	}
	
	public static final ITProcess getProcess(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe);
	}
	/**
	 * {@link StringProperty} com as mensagens de todos os processos em execu��o
	 * */
	public static final StringProperty globalProcessStringProperty(){
		return TedrosProcess.globalProcessStringProperty;
	}
	
	/**
	 * MessageProperty do processo para bind.
	 * */
	public static final ReadOnlyStringProperty processMessageProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).messageProperty();
	}
	
	/**
	 * ValueProperty do processo para bind.
	 * */
	public static final ReadOnlyObjectProperty processValueProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).valueProperty();
	}
	
	/**
	 * ProgressProperty do processo para bind.
	 * */
	public static final ReadOnlyDoubleProperty processProgressProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).progressProperty();
	}
	
	/**
	 * StateProperty do processo para bind.
	 * */
	public static final ReadOnlyObjectProperty processStateProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).stateProperty();
	}
	
	/**
	 * RunningProperty do processo para bind.
	 * */
	public static final ReadOnlyBooleanProperty processRunningProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).runningProperty();
	}
	
	/**
	 * TitleProperty do processo para bind.
	 * */
	public static final ReadOnlyStringProperty processTitleProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).titleProperty();
	}
	
	/**
	 * TitleProperty do processo para bind.
	 * */
	public static final ReadOnlyDoubleProperty processTotalWorkProperty(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return null;
		return TedrosProcess.moduleProcessMap.get(classe).totalWorkProperty();
	}
	
	
	/**
	 * Adiciona um processo do tipo {@link ITProcess} na lista de execu��o
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
	 * Cancela a execu��o do processo
	 * */
	public static final void stopProcess(Class classe){
		if(TedrosProcess.moduleProcessMap.containsKey(classe))
			TedrosProcess.moduleProcessMap.get(classe).stopProcess();
	}
	/**
	 * Inicializa o processo
	 * */
	public static final void startProcess(Class classe){
		if(TedrosProcess.moduleProcessMap.containsKey(classe))
			TedrosProcess.moduleProcessMap.get(classe).startProcess();
	}
	/**
	 * Cancela a execu��o e depois remove o processo da lista de processos
	 * */
	public static final void removeProcess(Class classe){
		if(!TedrosProcess.moduleProcessMap.containsKey(classe))
			return;
		stopProcess(classe);
		TedrosProcess.moduleProcessMap.remove(classe);
	}
}
