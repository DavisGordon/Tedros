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
public class TBackgroundSetting extends TSetting {

	/**
	 * @param descriptor
	 */
	public TBackgroundSetting(TComponentDescriptor descriptor) {
		super(descriptor);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.form.TSetting#run()
	 */
	@Override
	public void run() {
		TSelectImageField sif = super.getControl("fileModel");
		sif.settLocalFolder(TBackgroundDomain.FOLDER);
	}

}
