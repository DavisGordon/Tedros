package org.tedros.fx.modal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.control.validator.TFieldResult;
import org.tedros.fx.control.validator.TValidatorResult;
import org.tedros.fx.exception.TValidatorException;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TMessageBox extends StackPane {
	
	public VBox msgListPane;
	
	private String header;
    
    public TMessageBox() {
    	init();
    }
    
    public TMessageBox(String msg) {
    	init();
    	load(Arrays.asList(msg), TMessageType.GENERIC);
	}
    
    public TMessageBox(String header, String msg) {
    	this.header = header;
    	init();
    	if(msg!=null)
    		load(Arrays.asList(msg), TMessageType.GENERIC);
	}
    
    public TMessageBox(List<String> messages) {
    	init();
    	load(messages, TMessageType.GENERIC);
	}

    public TMessageBox(List<String> messages, String header) {
    	this.header = header;
    	init();
    	load(messages, TMessageType.GENERIC);
	}
    
    public TMessageBox(Throwable e) {
    	init();
    	load(Arrays.asList(e.getMessage()), TMessageType.ERROR);
	}
    
    public TMessageBox(Throwable e, String header) {
    	this.header = header;
    	init();
    	load(Arrays.asList(e.getMessage()), TMessageType.ERROR);
	}
    
	public TMessageBox(TValidatorException e) {
    	init();
    	processValidator(e);
	}

	public TMessageBox(TValidatorException e, String header) {
    	this.header = header;
    	init();
    	processValidator(e);
	}
	/**
	 * @param e
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private void processValidator(TValidatorException e) {
		List<TValidatorResult<ITModelView>> results = (List<TValidatorResult<ITModelView>>) e.getValidatorResults();
    	List<String> messages = new ArrayList<>(0);
    	
    	for(TValidatorResult<?> result : results){
    		
    		String itemName = result.getModelView().toStringProperty()!=null 
    				? result.getModelView().toStringProperty().getValue()
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
    	load(messages, TMessageType.WARNING);
	}

    private void init() {
    	this.msgListPane = new VBox();
    	this.msgListPane.getChildren().addListener((Change<? extends Node> c)->{
    		if(c.getList().size()>0) {
    			int ls = c.getList().size();
    			calcHeight(ls);
    		}
    	});
    	this.msgListPane.setSpacing(5);
    	this.msgListPane.setAlignment(Pos.TOP_CENTER);
    	this.msgListPane.setStyle("-fx-background-color: transparent");
    	
    	ScrollPane scroll = new ScrollPane();
    	scroll.setFitToHeight(true);
    	scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scroll.setContent(msgListPane);
    	scroll.setStyle("-fx-background-color: transparent");
    	
    	if(header!=null) {
    		Label text = new Label(header);
    		text.setId("t-title-label");
    		StackPane sp = new StackPane();
    		sp.getChildren().add(text);
    		sp.getStyleClass().add("t-panel-color");
    		sp.setStyle("-fx-background-radius: 20 20 0 0;");
    		StackPane.setAlignment(text, Pos.CENTER_LEFT);
    		StackPane.setMargin(text, new Insets(5,5,5,10));
    		BorderPane bp = new BorderPane();
    		bp.setId("t-view");
    		bp.setTop(sp);
    		bp.setCenter(scroll);
    		BorderPane.setAlignment(scroll, Pos.CENTER);
    		BorderPane.setMargin(scroll, new Insets(8,4,4,4));
    		super.getChildren().add(bp);
    	}else {
    		super.getChildren().add(scroll);
    		StackPane.setAlignment(scroll, Pos.TOP_CENTER);
    	}
    	
    	super.setStyle("-fx-background-color: transparent");
    	super.setMaxWidth(500);
    	super.sceneProperty().addListener((a,o,n)->{
    		if(n!=null && this.msgListPane.getChildren().size()>0) {
    			calcHeight(this.msgListPane.getChildren().size());
    		}
    	});
    }

	/**
	 * @param ls
	 */
	private void calcHeight(int ls) {
		double i = (header!=null) ? 40 : 0;
		double s = i+(90*ls);
		if(super.getParent()!=null) {
			double x = getBoundsInParent().getHeight();
			if(x>=200 && s>=(x-50))
				s = x-70;
		}
			
		super.setMinHeight(s);
		super.setPrefHeight(s);
		super.setMaxHeight(s);
	}
  
	private void load(List<String> messages, TMessageType type) {
		try{
	        if(messages!=null)
		        for (String m : messages)
					tAddMessage(m, type);
			
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	public void tAddMessages(ObservableList<? extends String> messages, TMessageType type) {
		msgListPane.getChildren().clear();
		if(messages!=null)
	        for (String string : messages)
				tAddMessage(string, type);
	}
	
	public void tAddMessage(String msg) {
		this.tAddMessage(msg, TMessageType.GENERIC);
	}
	
	public void tAddMessages(ObservableList<TMessage> messages) {
		msgListPane.getChildren().clear();
		if(messages!=null)
	        for (TMessage m : messages)
				tAddMessage(m.getValue(), m.getType());
	}
	
	public void tAddMessage(TMessage msg) {
		this.tAddMessage(msg.getValue(), msg.getType());
	}
	
	public void tAddInfoMessage(String info) {
		this.tAddMessage(info, TMessageType.INFO);
	}
	
	public void tAddWarningMessage(String warning) {
		this.tAddMessage(warning, TMessageType.WARNING);
	}
	
	public void tAddErrorMessage(String error) {
		this.tAddMessage(error, TMessageType.ERROR);
	}

	public void tAddMessage(String msg, String buttonText, Consumer<ActionEvent> buttonAction) {
		TText text = buildText(msg);
		HBox box = buildPane();
		HBox.setHgrow(text, Priority.ALWAYS);
		TButton btn = new TButton(buttonText);
		btn.setOnAction(ev->{
			buttonAction.accept(ev);
		});
		box.getChildren().addAll(text, btn);
			
		msgListPane.getChildren().add(box);
	}
	
	public void tAddMessage(String string, TMessageType type) {
		TText text = buildText(string);
		HBox box = buildPane();
		HBox.setHgrow(text, Priority.ALWAYS);
		if(type==null || type.equals(TMessageType.GENERIC)) 
			box.getChildren().addAll(text);
		else {
			StackPane icon = new StackPane();
			icon.setId("t-"+type.name().toLowerCase()+"-icon");
			icon.setMinSize(48, 48);
			
			box.getChildren().addAll(text, icon);
		}
		
		msgListPane.getChildren().add(box);
	}

	/**
	 * @param text
	 * @return
	 */
	private HBox buildPane() {
		HBox box = new HBox(8);
		box.setAlignment(Pos.CENTER);
		box.setId("t-fieldbox-message");
		return box;
	}

	/**
	 * @param string
	 * @return
	 */
	private TText buildText(String string) {
		TText text = new TText(string);
		text.settTextStyle(TTextStyle.LARGE);
		text.setWrappingWidth(this.getMaxWidth()-100);
		return text;
	}
	
	public VBox gettMessageVBox() {
		return msgListPane;
	}

	public void settMessageVBox(VBox messageVBox) {
		this.msgListPane = messageVBox;
	}

}
