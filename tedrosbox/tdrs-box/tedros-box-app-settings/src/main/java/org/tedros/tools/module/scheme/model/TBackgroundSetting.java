/**
 * 
 */
package org.tedros.tools.module.scheme.model;

import org.tedros.fx.control.TSelectImageField;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.form.TSetting;

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
