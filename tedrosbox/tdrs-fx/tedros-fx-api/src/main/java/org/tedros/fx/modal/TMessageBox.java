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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TMessageBox extends StackPane {
	
	protected ObservableList<Node> messagesPane;
	
	private String header;

	private DoubleProperty height;
	
	private ChangeListener<Number> resizeListener; 
	
	private VBox vb ;
	private BorderPane bp;
	
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
    	
    	height = new SimpleDoubleProperty(header!=null ? 10 : 0);
    	
    	messagesPane = FXCollections.observableArrayList();
    	
    	vb = new VBox(5);
    	vb.setAlignment(Pos.TOP_CENTER);
    	vb.setStyle("-fx-background-color: transparent");

    	bp = new BorderPane();
    	
    	this.messagesPane.addListener((Change<? extends Node> c)->{
    		if(c.next())
    			vb.getChildren().addAll(c.getAddedSubList());
    	});
    	
    	super.setStyle("-fx-background-color: transparent");
    	
    	setMaxWidth(530);
    	resizeListener = (a,o,n)->{
    		if(n!=null) {
    			applyHeight(n.doubleValue(), bp);
    		}
    	};
    	
    	super.sceneProperty().addListener((a,o,n)->{
    		if(n!=null) {
    			double x = ((Pane)super.getParent()).getHeight();
    			super.setMaxHeight(x-40);
    			applyHeight(/*height.getValue().doubleValue()*/x-50, bp);
    	    	height.addListener(resizeListener);
    	    	loadPanes();
    		}
    	});
    }

	/**
	 * 
	 */
	private void loadPanes() {
		ScrollPane scroll = new ScrollPane();
    	scroll.setFitToHeight(true);
    	scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scroll.setStyle("-fx-background-color: transparent");
    	scroll.setContent(vb);
    	scroll.setPadding(new Insets(8));
    	bp.setCenter(scroll);
		bp.setMaxWidth(530);
		BorderPane.setAlignment(scroll, Pos.CENTER);
		BorderPane.setMargin(scroll, new Insets(8,3,16,3));
		super.getChildren().add(bp);
    	
    	if(header!=null) {
    		Label text = new Label(header);
    		text.setId("t-title-label");
    		StackPane sp = new StackPane();
    		sp.getChildren().add(text);
    		sp.getStyleClass().add("t-panel-color");
    		sp.setStyle("-fx-background-radius: 20 20 0 0;");
    		StackPane.setAlignment(text, Pos.CENTER_LEFT);
    		StackPane.setMargin(text, new Insets(5,5,5,10));
    		bp.setId("t-view");
    		bp.setTop(sp);
    	}
	}

	/**
	 * @param v
	 */
	private void applyHeight(double v, Pane p) {
		
		if(super.getParent()!=null) {
			double x = ((Pane)super.getParent()).getHeight();
			if(v < (x-40)) {
				p.setMinHeight(v);
				p.setMaxHeight(v);
				p.setPrefHeight(v);
			}
		}
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
		messagesPane.clear();
		if(messages!=null)
	        for (String string : messages)
				tAddMessage(string, type);
	}
	
	public void tAddMessage(String msg) {
		this.tAddMessage(msg, TMessageType.GENERIC);
	}
	
	public void tAddMessages(ObservableList<TMessage> messages) {
		messagesPane.clear();
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
		TButton btn = new TButton(buttonText);
		btn.setOnAction(ev->{
			buttonAction.accept(ev);
		});
		HBox box = buildPane(text, null, btn);
		messagesPane.add(box);
	}
	
	public void tAddMessage(String string, TMessageType type) {
		TText text = buildText(string);
		HBox box = buildPane(text, type, null);
		messagesPane.add(box);
	}

	/**
	 * @param type 
	 * @param text 
	 * @param text
	 * @return
	 */
	private HBox buildPane(TText text, TMessageType type, TButton btn) {
		HBox box = new HBox(8);
		box.setAlignment(Pos.CENTER);
		box.setId("t-fieldbox-message");
		
		HBox.setHgrow(text, Priority.ALWAYS);
		box.getChildren().add(text);

		box.setMinWidth(480);
    	box.setMaxWidth(480);
    	box.setPrefWidth(480);
    	
		if(btn!=null) {
			box.getChildren().add(btn);
			HBox.setHgrow(btn, Priority.SOMETIMES);
		}
		
		if(type!=null && !type.equals(TMessageType.GENERIC)) {
			StackPane icon = new StackPane();
			icon.setId("t-"+type.name().toLowerCase()+"-icon");
			icon.setMinSize(48, 48);
			box.getChildren().add(icon);
		}
		
		int s = box.getChildren().size();
		if(s==3)
			text.setWrappingWidth(250);
		
		box.heightProperty().addListener((a,o,n)->{
			if(n!=null) {
				height.setValue(height.get() + n.doubleValue());
			}
		});
		return box;
	}
	
	
	/**
	 * @param string
	 * @return
	 */
	private TText buildText(String string) {
		TText text = new TText(string);
		text.settTextStyle(TTextStyle.LARGE);
		text.setWrappingWidth(300);
		return text;
	}
	
}
