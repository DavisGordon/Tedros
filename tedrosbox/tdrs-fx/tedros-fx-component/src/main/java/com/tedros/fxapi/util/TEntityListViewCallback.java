package com.tedros.fxapi.util;

import org.apache.commons.lang3.StringUtils;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *<pre>
 *A {@link Callback} to {@link ListView} for {@link TEntityModelView}.
 *
 *This call back use the getDisplayProperty() method to get the {@link StringProperty} to bind with the
 *{@link ListCell} textProperty.
 *
 *The selected item in the {@link ListView} will  
 *</pre>  
 * */

@SuppressWarnings("rawtypes")
public class TEntityListViewCallback<M extends TModelView> implements Callback<ListView<M>, ListCell<M>> {

	public static String ITEM_CSS_ID = "t-listcell-item";
	public static String NEW_ITEM_EMPTY_CSS_ID = "t-listcell-new-empty-item"; 
	public static String NEW_ITEM_CSS_ID = "t-listcell-new-item"; 
	public static String CHANGED_ITEM_CSS_ID = "t-listcell-changed-item";
	
	@Override
	public ListCell<M> call(final ListView<M> param) {
		
        final ListCell<M> cell = new ListCell<M>() {
            
        	@Override
            public void updateItem(final M item, boolean empty) {
        		super.updateItem(item, empty);
            	
        		if (item == null) {
        			setId(ITEM_CSS_ID);
                	textProperty().unbind();
        			setText(null);
        			setGraphic(null);
        		}else {
        			//if(empty) {
	        			if(item.getDisplayProperty()==null)
	        				throw new RuntimeException(new TException("The method getDisplayProperty must return a not null value!"));
	        			
	                	textProperty().bind(item.getDisplayProperty());
	                	
	                	validateNew(item);
	                	
	                	item.lastHashCodeProperty().addListener(new ChangeListener<Number>() {
							@Override
							public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
								if(!validateNew(item))
									setId(CHANGED_ITEM_CSS_ID);
							}
						});
	                	
	                	item.loadedProperty().addListener(new ChangeListener<Number>() {
							@Override
							public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
								setId(ITEM_CSS_ID);
							}
						});
        			
                }/**/
                	
            }

			private boolean validateNew(final M item) {
				if(item.getModel() instanceof ITEntity && ((ITEntity)item.getModel()).isNew()){
					if(StringUtils.isBlank(item.getDisplayProperty().getValue()))
						setId(NEW_ITEM_EMPTY_CSS_ID);
					else
						setId(NEW_ITEM_CSS_ID);
					return true;
				}
				return false;
			}
        };
        return cell;
    }

}
