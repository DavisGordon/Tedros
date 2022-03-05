/**
 * 
 */
package com.tedros.settings.layout.model;

import com.tedros.fxapi.control.TSelectImageField;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TSetting;

/**
 * @author Davis Gordon
 *
 */
public class BackgroundSetting extends TSetting {

	/**
	 * @param descriptor
	 */
	public BackgroundSetting(TComponentDescriptor descriptor) {
		super(descriptor);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.form.TSetting#run()
	 */
	@Override
	public void run() {
		TSelectImageField sif = super.getControl("fileModel");
		sif.settLocalFolder(BackgroundDomain.FOLDER);
	}

}
