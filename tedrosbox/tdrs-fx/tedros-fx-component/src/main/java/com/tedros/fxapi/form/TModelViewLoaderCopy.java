/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import java.util.concurrent.TimeUnit;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.TDebugConfig;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
class TModelViewLoaderCopy<M extends ITModelView<?>> extends TFieldLoader<M> {
	
	private SimpleIntegerProperty count = new SimpleIntegerProperty(0);
	
	public TModelViewLoaderCopy(M modelView, ITModelForm<M> form) {
		super(modelView, form);
	}
	
	public ObservableList<Node> getControls() throws Exception {
		
		Long startTime;
		if(TDebugConfig.detailParseExecution) 
			startTime = System.nanoTime();
		
		final ObservableList<Node> nodesLoaded = FXCollections.observableArrayList();
		initialize();
		for(final String fieldName : getFieldsName()){
		
			final TFieldDescriptor tFieldDescriptor = getFieldDescriptor(fieldName);
			
			if(TReflectionUtil.isIgnoreField(tFieldDescriptor) || tFieldDescriptor.isLoaded())
				continue;
			
			//loadEditFieldBox(nodesLoaded, tFieldDescriptor);
		}
		if(TDebugConfig.detailParseExecution){
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			System.out.println("[TModelViewLoader][ModelView: "+getModelViewClassName()+"][Form: "+getFormClassName()+"][Build duration: "+(duration/1000000)+"ms, "+(TimeUnit.MILLISECONDS.toSeconds(duration/1000000))+"s] ");
		}
		return nodesLoaded;
	}
	
	public ObservableList<Node> getReaders() throws Exception {
		
		Long startTime;
		if(TDebugConfig.detailParseExecution) 
			startTime = System.nanoTime();
		
		final ObservableList<Node> nodesLoaded = FXCollections.observableArrayList();
		
		initialize();
		
		for(final String fieldName : getFieldsName()){
		
			final TFieldDescriptor tFieldDescriptor = getFieldDescriptor(fieldName);
			
			if(TReflectionUtil.isIgnoreField(tFieldDescriptor) || tFieldDescriptor.isLoaded())
				continue;
			
			loadReaderFieldBox(nodesLoaded, tFieldDescriptor);
		}
		if(TDebugConfig.detailParseExecution){
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			System.out.println("[TModelViewLoader][ModelView: "+getModelViewClassName()+"][Form: "+getFormClassName()+"][Build duration: "+(duration/1000000)+"ms, "+(TimeUnit.MILLISECONDS.toSeconds(duration/1000000))+"s]\n\n ");
		}
		return nodesLoaded;
	}
}
