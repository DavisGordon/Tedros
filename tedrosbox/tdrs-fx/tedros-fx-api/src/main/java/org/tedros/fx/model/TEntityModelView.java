/**
 * Tedros Box
 */
package org.tedros.fx.model;

import java.beans.Transient;

import org.tedros.core.model.ITEntityModelView;
import org.tedros.server.entity.ITEntity;

import javafx.beans.property.SimpleLongProperty;


/**
 * <pre>
 * This engine can bind all javafx property between a {@link TEntityModelView} and a 
 * {@link ITEntity}, listeners are build to listen changes in the propertys and 
 * set the new values in the {@link ITEntity}.
 * 
 *  Example:
 *  
 *	// The entity
 * 	public class ExampleEntity implements ITEntity {
 * 
 *		private Long id;
 *		...
 *		private String <b style='color:red'>name</b>;
 * 		private BigDecimal <b style='color:green'>value</b>;
 * 		private Date <b style='color:blue'>date</b>;
 * 		...		
 * 		// getters and setters
 * 	}
 * 
 * 	// The TEntityModelView
 * 	public class ExampleEntityModelView extends TEntityModelView&lt;ExampleEntity&gt;{
 * 
 * 		private SimpleLongProperty id;
 * 		...
 * 		private SimpleStringProperty <b style='color:red'>name</b>;
 * 		private SimpleObjectProperty&lt;BigDecimal&gt; <b style='color:green'>value</b>;
 * 		private SimpleObjectProperty&lt;Date&gt; <b style='color:blue'>date</b>;
 * 		
 * 
 * 		public ExampleEntityModelView(ExampleEntity model) {
 *			<b>super(model);</b>
 *		}
 *		...
 *		// getters and setters
 * 	}
 * </pre>
 * @author Davis Gordon
 */
public abstract class TEntityModelView<E extends ITEntity> 
extends TModelView<E> implements ITEntityModelView<E> {
	
	private SimpleLongProperty id;
	
	public TEntityModelView(final E entity) {
		super(entity);
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.core.model.ITEntityModelView#getEntity()
	 */
	@Override
	@Transient
	public E getEntity(){
		return getModel();
	}

	/* (non-Javadoc)
	 * @see org.tedros.core.model.ITEntityModelView#getId()
	 */
	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see org.tedros.core.model.ITEntityModelView#setId(javafx.beans.property.SimpleLongProperty)
	 */
	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) {
		
		if(obj instanceof TEntityModelView) {
			return getEntity().equals(((TEntityModelView)obj).getEntity());
		}
		
		return false;
	}
		
}
