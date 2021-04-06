package com.tedros.fxapi.modal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.control.TAccordion;
import com.tedros.fxapi.control.validator.TFieldResult;
import com.tedros.fxapi.control.validator.TValidatorResult;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.exception.TValidatorException;
import com.tedros.fxapi.form.TFieldBox;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TMessageBox extends StackPane {
	
	@FXML public ScrollPane messageScrollPane;
	@FXML public VBox messageVBox;
    
    public TMessageBox() {
    	load(null);
    }
    public TMessageBox(List<String> messages) {
    	load(messages);
	}
    
    public TMessageBox(Map<String, String> messages) {
    	loadMap(messages);
	}
    
    public TMessageBox(TException e) {
    	load(Arrays.asList(e.getMessage()));
	}
    
    @SuppressWarnings({"unchecked", "rawtypes"})
	public TMessageBox(TValidatorException e) {
    	
    	List<TValidatorResult<ITModelView>> results = (List<TValidatorResult<ITModelView>>) e.getValidatorResults();
    	List<String> messages = new ArrayList<>(0);
    	
    	for(TValidatorResult<?> result : results){
    		
    		String itemName = result.getModelView().getDisplayProperty()!=null 
    				? result.getModelView().getDisplayProperty().getValue()
    				 : null;
    		
    		
    		if(itemName != null)
    			messages.add("* "+itemName);
    		
    		for(TFieldResult TFieldResult: result.getFields()){
    			if(StringUtils.isNotBlank(TFieldResult.getFieldLabel())){
    				messages.add(TFieldResult.getFieldLabel()+": "+TFieldResult.getMessage());
    			}
    			if(StringUtils.isBlank(TFieldResult.getFieldLabel())){
    				messages.add(TFieldResult.getMessage());
    			}
    		}
    	}
    	load(messages);
	}
    
    public TMessageBox(Throwable e) {
    	load(Arrays.asList(e.getMessage()));
	}
    
    public TMessageBox(String msg) {
    	load(Arrays.asList(msg));
	}
	private void loadMap(Map<String, String> messages) {
		try{
	    	loadFXML();
	        
			Map<String, Node> content = new HashMap<>();
			if(messages!=null)
		        for (String label : messages.keySet()) {
		        	Text text = new Text(messages.get(label));
		        	text.setWrappingWidth(350);
		        	content.put(label, text);
		        }
			final TAccordion accordionMsg = new TAccordion(content);
			accordionMsg.addItem(content);
			messageVBox.getChildren().add(accordionMsg);
					
			
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	public void loadFXML() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("message.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
		//winBtn = new TWindowButtons(false, false, true);
	}

	private void load(List<String> messages) {
		try{
	    	loadFXML();
	    	setMaxWidth(400);
	    	setMaxHeight(200);
	    	
	        if(messages!=null)
		        for (String string : messages)
					tAddMessage(string);
			
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	public void tAddMessage(ObservableList<? extends String> messages) {
		messageVBox.getChildren().clear();
		if(messages!=null)
	        for (String string : messages)
				tAddMessage(string);

	}
	


	public void tAddMessage(String string) {
		Text text = new Text(string);
		text.setId("t-form-item-text");
		text.setWrappingWidth(this.getMaxWidth()-100);
		DropShadow ef = new DropShadow();
		ef.setColor(Color.BLACK);
		ef.setBlurType(BlurType.THREE_PASS_BOX);
		ef.setRadius(15.119047619047619);
		ef.setWidth(35.16666666666667);
		ef.setHeight(35.16666666666667);
		TFieldBox fieldBox = new TFieldBox(null, null, text, null);
		fieldBox.setAlignment(Pos.CENTER);
		fieldBox.setId("t-form");
		fieldBox.setEffect(ef);
		
		messageVBox.getChildren().add(fieldBox);
	}
	
	public VBox gettMessageVBox() {
		return messageVBox;
	}

	public void settMessageVBox(VBox messageVBox) {
		this.messageVBox = messageVBox;
	}

	public VBox getMessageVBox() {
		return messageVBox;
	}

	public void setMessageVBox(VBox messageVBox) {
		this.messageVBox = messageVBox;
	}

	public ScrollPane getMessageScrollPane() {
		return messageScrollPane;
	}
	public void setMessageScrollPane(ScrollPane messageScrollPane) {
		this.messageScrollPane = messageScrollPane;
	}

}
