package org.tedros.fx.property;

import org.tedros.common.model.TFileEntity;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TFileEntityLoadProcess;
import org.tedros.server.result.TResult;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;

public final class TBytesLoader {
	
	public static final void loadBytesFromTFileEntity(final Long byteEntityId, final SimpleObjectProperty<byte[]> bytesProperty) throws TProcessException {
		
		TFileEntity fileEntity = new TFileEntity();
		fileEntity.getByteEntity().setId(byteEntityId);
		
		final TFileEntityLoadProcess process = new TFileEntityLoadProcess();
		process.load(fileEntity);
		process.stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> arg0,
					State arg1, State arg2) {
				
					if(arg2.equals(State.SUCCEEDED)){
						
						TResult<TFileEntity> result = process.getValue();
						if(result.getValue()!=null){
							TFileEntity fileEntity = result.getValue();
							bytesProperty.setValue(fileEntity.getByteEntity().getBytes());
						}
					}	
			}
		});
		process.startProcess();
	}
	
	public static final void loadBytes(final TFileEntity fileEntity) throws TProcessException {
		
		final TFileEntityLoadProcess process = new TFileEntityLoadProcess();
		process.load(fileEntity);
		process.stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> arg0,
					State arg1, State arg2) {
				
					if(arg2.equals(State.SUCCEEDED)){
						
						TResult<TFileEntity> result = process.getValue();
						if(result.getValue()!=null){
							TFileEntity e = result.getValue();
							fileEntity.getByteEntity().setBytes(e.getByteEntity().getBytes());
						}
					}	
			}
		});
		process.startProcess();
	}

}
