package org.tedros.chat.module.client.behaviour;

import org.apache.commons.lang3.StringUtils;
import org.tedros.chat.module.client.model.ChatMV;
import org.tedros.fx.exception.TException;
import org.tedros.fx.util.TEntityListViewCallback;
import org.tedros.server.entity.ITEntity;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ChatListViewCallback extends TEntityListViewCallback<ChatMV> {

	@Override
	public ListCell<ChatMV> call(final ListView<ChatMV> param) {
		
        final ListCell<ChatMV> cell = new ListCell<ChatMV>() {
            
        	@Override
            public void updateItem(final ChatMV item, boolean empty) {
        		super.updateItem(item, empty);
            	
        		if (item == null) {
        			setId(ITEM_CSS_ID);
                	textProperty().unbind();
        			setText(null);
        			setGraphic(null);
        		}else{
        			
        			if(item.toStringProperty()==null)
        				throw new RuntimeException(new TException("The method toStringProperty must return a not null value!"));
        			
                	textProperty().bind(item.toStringProperty());
                	
                	if(!validateNew(item)) 
                		validateUnreadMessages(item);
                	
                	item.lastHashCodeProperty().addListener((a,o,n)->{
						if(!validateNew(item)) 
							validateUnreadMessages(item);
					});
                	
                	item.loadedProperty().addListener((a,o,n)->{
                		validateUnreadMessages(item);
					});
                }	
            }

			private boolean validateNew(final ChatMV item) {
				if(item.getModel() instanceof ITEntity && ((ITEntity)item.getModel()).isNew()){
					if(StringUtils.isBlank(item.toStringProperty().getValue()))
						setId(NEW_ITEM_EMPTY_CSS_ID);
					else
						setId(NEW_ITEM_CSS_ID);
					return true;
				}
				return false;
			}

			/**
			 * @param item
			 */
			private void validateUnreadMessages(final ChatMV item) {
				if(item.getTotalUnreadMessages()>0)
					setId(BOLD_ITEM_CSS_ID);
				else
					setId(ITEM_CSS_ID);
			}
        };
        return cell;
    }

}
