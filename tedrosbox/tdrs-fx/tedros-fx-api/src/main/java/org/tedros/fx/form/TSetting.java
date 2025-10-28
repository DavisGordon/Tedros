/**
 * 
 */
package org.tedros.fx.form;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.api.form.ITFieldBox;
import org.tedros.api.form.ITForm;
import org.tedros.api.form.ITSetting;
import org.tedros.core.model.ITModelView;

import javafx.beans.Observable;

/**
 * @author Davis Gordon
 *
 */
public abstract class TSetting implements ITSetting {
	
	private ITComponentDescriptor descriptor;

	/**
	 * @param descriptor
	 * @param component
	 */
	public TSetting(ITComponentDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITSetting#run()
	 */
	@Override
	public abstract void run();

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITSetting#getDescriptor()
	 */
	@Override
	public ITComponentDescriptor getDescriptor() {
		return descriptor;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITSetting#getControl(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getControl(String field) {
		ITFieldDescriptor fd =  this.descriptor.getFieldDescriptor(field);
		if(fd==null)
			throw new IllegalStateException("The argument "+field+" has no result.");
		T c = (T) fd.getControl();
		if(c==null)
			throw new IllegalStateException("The argument "+field+" has no control");
		return c;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITSetting#getLayout(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getLayout(String field) {
		ITFieldDescriptor fd =  this.descriptor.getFieldDescriptor(field);
		if(fd==null)
			throw new IllegalStateException("The argument "+field+" has no result.");
		
		T c = (T) fd.getLayout();
		if(c==null)
			throw new IllegalStateException("The argument "+field+" has no layout");
		return c;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITSetting#getFieldBox(java.lang.String)
	 */
	@Override
	public ITFieldBox getFieldBox(String field) {
		ITFieldBox fb =  this.descriptor.getFieldBox(field);
		if(fb==null)
			throw new IllegalStateException("The argument "+field+" has no result.");
		return fb;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITSetting#getProperty(java.lang.String)
	 */
	@Override
	public Observable getProperty(String field) {
		Observable t = descriptor.getModelView().getProperty(field);
		if(t==null)
			throw new IllegalStateException("The argument "+field+" has no property or it was not registered.");
		
		return t;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITSetting#getModelView()
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends ITModelView> T getModelView() {
		return (T) descriptor.getModelView();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITSetting#getForm()
	 */
	@Override
	public ITForm getForm() {
		return descriptor.getForm();
	}


}
