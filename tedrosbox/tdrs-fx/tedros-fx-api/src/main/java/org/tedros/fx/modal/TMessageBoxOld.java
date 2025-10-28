package org.tedros.fx.modal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.control.validator.TFieldResult;
import org.tedros.fx.control.validator.TValidatorResult;
import org.tedros.fx.exception.TValidatorException;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TMessageBoxOld extends Pane {
	
	private ObservableList<TMessage> messages;

	protected static double size = 0;
	private String header;
	protected VBox messagesPane;
	protected TLanguage iEngine = TLanguage.getInstance(null);
	
    protected TMessageBoxOld() {
    	init();
    }
    
    public TMessageBoxOld(String msg) {
    	init();
    	load(Arrays.asList(msg), TMessageType.GENERIC);
	}
    
    public TMessageBoxOld(String header, String msg) {
    	this.header = header;
    	init();
    	if(msg!=null)
    		load(Arrays.asList(msg), TMessageType.GENERIC);
	}
    
    public TMessageBoxOld(String msg, TMessageType type) {
    	init();
    	load(Arrays.asList(msg), type);
	}
    
    public TMessageBoxOld(String header, String msg, TMessageType type) {
    	this.header = header;
    	init();
    	if(msg!=null)
    		load(Arrays.asList(msg), type);
	}
    
    public TMessageBoxOld(List<String> messages, String header) {
    	this.header = header;
    	init();
    	load(messages, TMessageType.GENERIC);
	}

    public TMessageBoxOld(List<String> messages, TMessageType type) {
    	init();
    	load(messages, type);
	}

    public TMessageBoxOld(String header, List<String> messages, TMessageType type) {
    	this.header = header;
    	init();
    	load(messages, type);
	}
    
    public TMessageBoxOld(List<TMessage> messages) {
    	init();
    	load(messages);
	}
    
    public TMessageBoxOld(String header, List<TMessage> messages) {
    	this.header = header;
    	init();
    	load(messages);
	}
    
    public TMessageBoxOld(Throwable e) {
    	init();
    	process(e);
	}
    
    public TMessageBoxOld(Throwable e, String header) {
    	this.header = header;
    	init();
    	process(e);
    }

	/**
	 * @param e
	 */
	public void process(Throwable e) {
		String msg = e.getMessage();
    	if(StringUtils.isBlank(msg))
    		msg = TFxKey.MESSAGE_ERROR;
    	load(Arrays.asList(msg), TMessageType.ERROR);
	}
    
	public TMessageBoxOld(TValidatorException e) {
    	init();
    	processValidator(e);
	}

	public TMessageBoxOld(TValidatorException e, String header) {
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
    	
    	
    	messages = FXCollections.observableArrayList();
    	
    	messagesPane = new VBox(5);
    	messagesPane.setAlignment(Pos.TOP_CENTER);
    	messagesPane.setStyle("-fx-background-color: transparent");

    	StackPane main = new StackPane();
    	
    	super.setStyle("-fx-background-color: transparent");
    	
    	setMaxWidth(530);
    	super.sceneProperty().addListener((a,o,n)->{
    		if(n==null){
    			this.getChildren().clear();
    			this.messagesPane = null;
    			this.messages = null;
    			this.iEngine = null;
    		}
    	});
    	
		ScrollPane scroll = new ScrollPane();
    	scroll.setFitToHeight(true);
    	scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scroll.setStyle("-fx-background-color: transparent");
    	scroll.setPadding(new Insets(8));
		scroll.setContent(this.messagesPane);
		
    	VBox vb = new VBox(8);
		
    	if(header!=null) {
    		size = 50;
    		Label text = new Label(header);
    		text.setId("t-title-label");
    		StackPane sp = new StackPane();
    		sp.getChildren().add(text);
    		sp.getStyleClass().add("t-panel-color");
    		sp.setStyle("-fx-background-radius: 20 20 0 0;");
    		StackPane.setAlignment(text, Pos.CENTER_LEFT);
    		StackPane.setMargin(text, new Insets(5,5,5,10));
    		vb.setId("t-view");
    		vb.getChildren().add(sp);
    	}
		vb.getChildren().add(scroll);
    	main.getChildren().add(vb);
    	super.getChildren().add(main);
	}

    @Override 
    protected void layoutChildren() {
    	
        List<Node> managed = getManagedChildren();
        double height = getHeight();
        double top = getInsets().getTop();
        double left = getInsets().getLeft();
        double bottom = getInsets().getBottom();
        
        double h = height - top - bottom;
        double f = (size>h) ? h : size;
        
        for (int i = 0; i < managed.size(); i++) {
            Node child = managed.get(i);
            layoutInArea(child, left, top,
            		530, f, 
            		0, Insets.EMPTY, true, true, HPos.CENTER, VPos.CENTER);
        }
        
    }  
    
	private void load(List<String> messages, TMessageType type) {
        if(messages!=null)
	        for (String m : messages)
				tAddMessage(m, type, null, null);
        addMessagePane();
		this.messages.addListener((Change<? extends TMessage> c)->{
    		if(c.next())
    			this.addMessagePane(c.getAddedSubList());
    	});
			
    	
	}
	
	private void load(List<TMessage> messages) {
		this.messages.addAll(messages);
		addMessagePane();
		this.messages.addListener((Change<? extends TMessage> c)->{
    		if(c.next())
    			this.addMessagePane(c.getAddedSubList());
    	});
	}
	
	protected void tAddMessage(String msg) {
		this.tAddMessage(msg, TMessageType.GENERIC, null, null);
	}
	
	private void addMessagePane() {
		messages.forEach(t -> {
			Pane p = buildMessagePane(t);
			if(p!=null)
				messagesPane.getChildren().add(p);
		});
		
	}

	private void addMessagePane(List<? extends TMessage> lst) {
		lst.forEach(t -> {
			Pane p = buildMessagePane(t);
			if(p!=null)
				messagesPane.getChildren().add(p);
		});
		
	}
	
	public static Pane buildMessagePane(TMessage msg) {
		if(!msg.isLoaded()) {
			msg.setLoaded(true);
			TText text = buildText(msg.getValue());
			TButton btn = null;
			if(msg.getButtonText()!=null) {
				btn = new TButton(msg.getButtonText());
				final Consumer<ActionEvent> action = msg.getButtonAction();
				btn.setOnAction(ev->{
					action.accept(ev);
				});
			}
			return buildPane(text, msg.getType(), btn);
		}
		return null;
	}
	
	private void tAddMessage(String value, TMessageType type, String buttonText, Consumer<ActionEvent> buttonAction) {
		messages.add(new TMessage(type, value, buttonText, buttonAction));
	}

	/**
	 * @param type 
	 * @param text 
	 * @param text
	 * @return
	 */
	private static HBox buildPane(TText text, TMessageType type, TButton btn) {
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
		
		return box;
	}
	
	/**
	 * @param string
	 * @return
	 */
	private static TText buildText(String string) {
		
		string = TLanguage.getInstance().getString(string);
		
		int p = 30;
		int t = string.length();
		double r = t<p ? 1 : t / p;
		
		size = size + (r*120);
		
		TText text = new TText(string);
		text.settTextStyle(TTextStyle.LARGE);
		text.setWrappingWidth(300);
		return text;
	}
	
}
