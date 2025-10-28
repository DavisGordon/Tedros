/**
 * 
 */
package org.tedros.tools.module.scheme.model;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.fx.control.TSelectImageField;
import org.tedros.fx.form.TSetting;

/**
 * @author Davis Gordon
 *
 */
public class TBackgroundSetting extends TSetting {

	/**
	 * @param descriptor
	 */
	public TBackgroundSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.TSetting#run()
	 */
	@Override
	public void run() {
		TSelectImageField sif = super.getControl("fileModel");
		sif.settLocalFolder(TBackgroundDomain.FOLDER);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
