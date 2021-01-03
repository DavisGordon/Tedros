/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.TDebugConfig;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
class TModelViewLoader<M extends ITModelView<?>> extends TFieldLoader<M> {
	
	private SimpleIntegerProperty count = new SimpleIntegerProperty(0);
	private final Object lock = new Object();
	public TModelViewLoader(M modelView, ITModelForm<M> form) {
		super(modelView, form);
	}
	
	private  void lessOne() {
		synchronized(lock){
			count.setValue(count.getValue() - 1);
		}
	}
	
	public void getControls(final ObservableList<Node> nodesLoaded) throws Exception {
		
		Long startTime;
		if(TDebugConfig.detailParseExecution) 
			startTime = System.nanoTime();
		
		initialize();
		
		final List<TFieldDescriptor> controlsFd = new ArrayList<>();
		final List<TFieldDescriptor> layoutFd = new ArrayList<>();
		
		ChangeListener<Boolean> chl = (ob, o, n) ->{
			if(n) {
				super.getFieldDescriptorList().sort(new Comparator<TFieldDescriptor>() {
					@Override
					public int compare(TFieldDescriptor o1, TFieldDescriptor o2) {
						return Integer.compare(o1.getOrder(), o2.getOrder());
					}
				});
				for(TFieldDescriptor fd : super.getFieldDescriptorList()) {
					if(!fd.isLoaded() || (fd.isLoaded() && fd.hasParent()))
						continue;
				
					if(fd.hasLayout())
						nodesLoaded.add(fd.getLayout());
					else
						nodesLoaded.add(fd.getComponent());
				}
			}
			
		};
		repo.add("allLoaded", chl);
		super.allLoadedProperty().addListener(new WeakChangeListener<>(chl));
		
		int order = 0;
		for(final String fieldName : getFieldsName()){
		
			final TFieldDescriptor tFieldDescriptor = getFieldDescriptor(fieldName);
			
			tFieldDescriptor.setOrder(order);
			order++;
			if(tFieldDescriptor.isIgnorable() 
					|| (!tFieldDescriptor.hasControl() && !tFieldDescriptor.hasLayout()))
				continue;
			
			
			if(tFieldDescriptor.hasControl())
				controlsFd.add(tFieldDescriptor);
			
			if(tFieldDescriptor.hasLayout())
				layoutFd.add(tFieldDescriptor);
		}
		
		count.setValue(controlsFd.size());
		
		if(!layoutFd.isEmpty())
			count.addListener((obj, o, n) ->{
				if(n.intValue()==0) {
					layoutFd.sort(new Comparator<TFieldDescriptor>() {
						@Override
						public int compare(TFieldDescriptor o1, TFieldDescriptor o2) {
							return Integer.compare(o2.getOrder(), o1.getOrder());
						}
					});
					descriptor.setMode(TViewMode.EDIT);
					for(TFieldDescriptor fd : layoutFd) {
						if(fd.isLoaded())
							continue;
		            	try {
							TComponentDescriptor cd = new TComponentDescriptor(descriptor, fd.getFieldName());
		            		TControlLayoutBuilder builder = new TControlLayoutBuilder();
		            		 builder.getLayoutField(cd);
						} catch (Exception e) {
							e.printStackTrace();
						}    
					}
				}
			});
		
		
		
		for(TFieldDescriptor fd : controlsFd) {
			if(fd.isLoaded())
				continue;
			Thread taskThread = new Thread(new Runnable() {
			      @Override
			      public void run() {
					Platform.runLater(new Runnable() {
			            @Override
			            public void run() {
			            	try {
								TComponentDescriptor cd = new TComponentDescriptor(descriptor, fd.getFieldName());
			            		
			            		TControlLayoutBuilder builder = new TControlLayoutBuilder();
			            		
			            		builder.getControlField(cd);
								lessOne();
							} catch (Exception e) {
								e.printStackTrace();
							}
							
			            }
			          });
			      	}
				});
			taskThread.setDaemon(true);
			taskThread.start();
			
		}
		
		if(TDebugConfig.detailParseExecution){
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			System.out.println("[TModelViewLoader][ModelView: "+getModelViewClassName()+"][Form: "+getFormClassName()+"][Build duration: "+(duration/1000000)+"ms, "+(TimeUnit.MILLISECONDS.toSeconds(duration/1000000))+"s] ");
		}
		//return nodesLoaded;
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
