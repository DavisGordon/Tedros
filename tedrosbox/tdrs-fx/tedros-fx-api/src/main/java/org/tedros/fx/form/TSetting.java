/**
 * 
 */
package org.tedros.fx.form;

import org.tedros.core.model.ITModelView;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.descriptor.TFieldDescriptor;

import javafx.beans.Observable;

/**
 * @author Davis Gordon
 *
 */
public abstract class TSetting {
	
	private TComponentDescriptor descriptor;

	/**
	 * @param descriptor
	 * @param component
	 */
	public TSetting(TComponentDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	public abstract void run();

	/**
	 * @return the descriptor
	 */
	public TComponentDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * @return the control
	 */
	@SuppressWarnings("unchecked")
	public <T> T getControl(String field) {
		TFieldDescriptor fd =  this.descriptor.getFieldDescriptor(field);
		if(fd==null)
			throw new IllegalStateException("The argument "+field+" has no result.");
		T c = (T) fd.getControl();
		if(c==null)
			throw new IllegalStateException("The argument "+field+" has no control");
		return c;
	}
	
	/**
	 * @return the layout
	 */
	@SuppressWarnings("unchecked")
	public <T> T getLayout(String field) {
		TFieldDescriptor fd =  this.descriptor.getFieldDescriptor(field);
		if(fd==null)
			throw new IllegalStateException("The argument "+field+" has no result.");
		
		T c = (T) fd.getLayout();
		if(c==null)
			throw new IllegalStateException("The argument "+field+" has no layout");
		return c;
	}
	
	/**
	 * @return the TFieldBox
	 */
	public TFieldBox getFieldBox(String field) {
		TFieldBox fb =  this.descriptor.getFieldBox(field);
		if(fb==null)
			throw new IllegalStateException("The argument "+field+" has no result.");
		return fb;
	}
	
	/**
	 * @return the modelView registered observable property
	 */
	public Observable getProperty(String field) {
		Observable t = descriptor.getModelView().getProperty(field);
		if(t==null)
			throw new IllegalStateException("The argument "+field+" has no property or it was not registered.");
		
		return t;
	}
	
	/**
	 * @return the modelView 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends ITModelView> T getModelView() {
		return (T) descriptor.getModelView();
	}
	
	/**
	 * @return the form 
	 */
	public ITForm getForm() {
		return descriptor.getForm();
	}


}
