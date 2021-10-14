/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.editorweb.module.site.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.tedros.common.model.TFileEntity;
import com.tedros.fxapi.control.TTableCell;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.property.TBytesLoader;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class CompTempImageTableCell extends TTableCell{

	
	public String processItem(Object item) {
		if(item!=null && item instanceof TFileEntity) {
			TFileEntity f = (TFileEntity) item;
			if(!f.isNew()) {
				SimpleObjectProperty<byte[]> p = new SimpleObjectProperty<>();
				ChangeListener<byte[]> ch = (a, b, n)->{
					ByteArrayInputStream bi = new ByteArrayInputStream(n);
					Image i = new Image(bi);
					ImageView v = new ImageView(i);
					v.setPreserveRatio(true);
					v.setFitWidth(300);
					super.setGraphic(v);
					try {
						bi.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				};
				p.addListener(ch);
				try {
					TBytesLoader.loadBytesFromTFileEntity(f.getByteEntity().getId(), p);
				} catch (TProcessException e) {
					e.printStackTrace();
				}
			}else
				super.setGraphic(null);
		}
		
		return null; 
	}


}
