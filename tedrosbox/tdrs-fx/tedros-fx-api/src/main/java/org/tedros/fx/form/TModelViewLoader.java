/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package org.tedros.fx.form;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.util.TLoggerUtil;

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
	
	private final static Logger LOGGER = TLoggerUtil.getLogger(TModelViewLoader.class);
	
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
	
	public void loadEditFields(final ObservableList<Node> nodesLoaded) throws Exception {
		
		initialize();
		
		final List<ITFieldDescriptor> controlsFd = new ArrayList<>();
		final List<ITFieldDescriptor> layoutFd = new ArrayList<>();
		
		ChangeListener<Boolean> chl = (ob, o, n) ->{
			if(n) {
				super.getFieldDescriptorList().sort(new Comparator<ITFieldDescriptor>() {
					@Override
					public int compare(ITFieldDescriptor o1, ITFieldDescriptor o2) {
						return Integer.compare(o1.getOrder(), o2.getOrder());
					}
				});
				super.getFieldDescriptorList().stream().forEach(fd->{
					if(!(!fd.isLoaded() || (fd.isLoaded() && fd.hasParent()))) {
						
						if(fd.hasLayout())
							nodesLoaded.add(fd.getLayout());
						else
							nodesLoaded.add(fd.getComponent());
					}
				});
			}
			
		};
		repo.add("allLoaded", chl);
		super.allLoadedProperty().addListener(new WeakChangeListener<>(chl));
		
		int order = 0;
		for(final String fieldName : getFieldsName()){
		
			final ITFieldDescriptor tFieldDescriptor = getFieldDescriptor(fieldName);
			
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
					layoutFd.sort(new Comparator<ITFieldDescriptor>() {
						@Override
						public int compare(ITFieldDescriptor o1, ITFieldDescriptor o2) {
							return Integer.compare(o2.getOrder(), o1.getOrder());
						}
					});
					descriptor.setMode(TViewMode.EDIT);
					layoutFd.stream().forEach(fd->{
						if(!fd.isLoaded()) {
			            	try {
								TComponentDescriptor cd = new TComponentDescriptor(descriptor, fd.getFieldName());
			            		TComponentBuilder builder = new TComponentBuilder();
			            		 builder.processLayoutField(cd);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}  
						}
					});
				}
			});
		
		controlsFd.parallelStream().forEach(fd->{
			if(!fd.isLoaded()) {
				
				Thread taskThread = new Thread(new Runnable() {
				      @Override
				      public void run() {
						Platform.runLater(new Runnable() {
				            @Override
				            public void run() {
				            	try {
									TComponentDescriptor cd = new TComponentDescriptor(descriptor, fd.getFieldName());
				            		
				            		TComponentBuilder builder = new TComponentBuilder();
				            		
				            		builder.processControlField(cd);
									lessOne();
								} catch (Exception e) {
									LOGGER.error(e.getMessage(), e);
								}
								
				            }
				          });
				      	}
					});
				taskThread.setDaemon(true);
				taskThread.start();
			}
		});
	}
	
	public ObservableList<Node> getReaders() throws Exception {
		
		final ObservableList<Node> nodesLoaded = FXCollections.observableArrayList();
		
		initialize();
		
		for(final String fieldName : getFieldsName()){
		
			final ITFieldDescriptor tFieldDescriptor = getFieldDescriptor(fieldName);
			
			if(TReflectionUtil.isIgnoreField(tFieldDescriptor) || tFieldDescriptor.isLoaded())
				continue;
			
			loadReaderFieldBox(nodesLoaded, tFieldDescriptor);
		}
		
		return nodesLoaded;
	}
}
