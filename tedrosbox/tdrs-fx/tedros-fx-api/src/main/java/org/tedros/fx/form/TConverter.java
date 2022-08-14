/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 09/03/2014
 */
package org.tedros.fx.form;

import org.tedros.fx.descriptor.TComponentDescriptor;



/**
 * <pre>
 * A converter abstract class.
 * 
 * Must implement the getOut method.
 * 
 * Example:
 * 
 * final public class TOptionConverter extends TConverter<<strong>SimpleStringProperty</strong>, <strong>Label</strong>>{
 *	
 *  public TOptionConverter() {
 *	
 *  }
 *
 *  public TOptionConverter(SimpleStringProperty value) {
 *      super(value);
 *  }
 *	
 *  public Label getOut() {
 *		
 *      SimpleStringProperty value = getIn();
 *		
 *      if(value==null || value.getValue()==null)
 *         return new Label("No option");
 *
 *      return new Label("Option "+value.getValue());;
 *  }
 *
 *}
 * 
 *</pre>
 *
 * @author Davis Gordon
 */
public abstract class TConverter<IN, OUT> {

	private TComponentDescriptor componentDescriptor;
	private IN value;
	
	public TConverter(IN value) {
		this.value = value;
	}
	
	public TConverter() {
		
	}
	
	/**
	 * Return the original oject
	 * */
	public IN getIn(){
		return value;
	}
	
	/**
	 * Sets the object to converted.
	 * */
	public void setIn(IN value){
		this.value = value;
	}

	/**
	 * Return the converted object.
	 * */
	public abstract OUT getOut();

	public final TComponentDescriptor getComponentDescriptor() {
		return componentDescriptor;
	}

	public final void setComponentDescriptor(
			TComponentDescriptor componentDescriptor) {
		this.componentDescriptor = componentDescriptor;
	}

	public final IN getValue() {
		return value;
	}

	public final void setValue(IN value) {
		this.value = value;
	}
	
}
