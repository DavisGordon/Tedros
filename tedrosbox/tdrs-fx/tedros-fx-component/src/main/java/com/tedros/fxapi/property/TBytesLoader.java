package com.tedros.fxapi.property;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;

import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TFileEntityLoadProcess;
import com.tedros.global.model.TFileEntity;

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

}
