package com.tedros.fxapi.control;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TWindowButtons extends HBox{
	
	Button closeBtn;
	Button minBtn;
	Button maxBtn;
		    
	public TWindowButtons(boolean addMinButton, boolean addMaxButton, boolean addCloseButton)
    {
        super(4D);
        setId("t-window-buttons");
        setMaxHeight(17);
        setAlignment(Pos.CENTER);
        addMinBtn(addMinButton);
        addMaxBtn(addMaxButton);
        addCloseBtn(addCloseButton);
    }
	
	public ObjectProperty<EventHandler<ActionEvent>> getMinActionProperty(){
		if(null==minBtn) 
			return null;
		return minBtn.onActionProperty();
	}
	
	public ObjectProperty<EventHandler<ActionEvent>> getMaxActionProperty(){
		if(null==maxBtn) 
			return null;
		return maxBtn.onActionProperty();
	}
	
	public ObjectProperty<EventHandler<ActionEvent>> getCloseActionProperty(){
		if(null==closeBtn) 
			return null;
		return closeBtn.onActionProperty();
	}
	

	private void addMinBtn(boolean add) {
		if(!add) return;
		minBtn = new Button();
        minBtn.setId("t-window-min");
        minBtn.setTooltip(new Tooltip("Minimizar"));
        HBox.setHgrow(minBtn, Priority.ALWAYS);
        getChildren().add(minBtn);
	}
	private void addMaxBtn(boolean add) {
		if(!add) return;
		maxBtn = new Button();
        maxBtn.setId("t-window-max");
        maxBtn.setTooltip(new Tooltip("Expandir"));
        HBox.setHgrow(maxBtn, Priority.ALWAYS);
        getChildren().add(maxBtn);
	}
	private void addCloseBtn(boolean add) {
		if(!add) return;
		closeBtn = new Button();
        closeBtn.setId("t-window-close");
        closeBtn.setTooltip(new Tooltip("Fechar"));
        HBox.setHgrow(closeBtn, Priority.ALWAYS);
        getChildren().add(closeBtn);
	}

        
}
