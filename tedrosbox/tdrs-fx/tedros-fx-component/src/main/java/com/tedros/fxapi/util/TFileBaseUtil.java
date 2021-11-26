/**
 * 
 */
package com.tedros.fxapi.util;

import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.TFileModel;

/**
 * @author Davis Gordon
 *
 */
public class TFileBaseUtil {

	public static ITFileEntity convert(TFileModel m) {
		TFileEntity e = new TFileEntity();
		e.setFileExtension(m.getFileExtension());
		e.setFileName(m.getFileName());
		e.setFileSize(m.getFileSize());
		e.getByte().setBytes(m.getByte().getBytes());
		return e;
	}
	
	public static TFileModel convert(ITFileEntity e) {
		TFileModel m = new TFileModel();
		m.setFileExtension(e.getFileExtension());
		m.setFileName(e.getFileName());
		m.setFileSize(e.getFileSize());
		m.getByte().setBytes(e.getByte().getBytes());
		
		return m;
	}
	
}
