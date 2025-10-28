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
import org.tedros.core.repository.TRepository;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.control.validator.TFieldResult;
import org.tedros.fx.control.validator.TValidatorResult;
import org.tedros.fx.exception.TValidatorException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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

public class TMessageBox extends Pane {
	
	protected static double size = 0;
	protected TRepository repo;
	private SimpleStringProperty header;
	private ObservableList<TMessage> messages;
	protected ScrollPane scroll;
	protected VBox messagesPane;
	
	protected TLanguage iEngine = TLanguage.getInstance(null);
	
    protected TMessageBox() {
    	init();
    }
    
    public TMessageBox(String msg) {
    	init();
    	tAddMessage(Arrays.asList(msg), TMessageType.GENERIC);
	}
    
    public TMessageBox(String header, String msg) {
    	init();
    	settHeader(header);
    	if(msg!=null)
    		tAddMessage(Arrays.asList(msg), TMessageType.GENERIC);
	}
    
    public void settHeader(String header) {
		this.header.setValue(header);
	}

	public TMessageBox(String msg, TMessageType type) {
    	init();
    	tAddMessage(Arrays.asList(msg), type);
	}
    
    public TMessageBox(String header, String msg, TMessageType type) {
    	init();
    	settHeader(header);
    	if(msg!=null)
    		tAddMessage(Arrays.asList(msg), type);
	}
    
    public TMessageBox(List<String> messages, String header) {
    	init();
    	settHeader(header);
    	tAddMessage(messages, TMessageType.GENERIC);
	}

    public TMessageBox(List<String> messages, TMessageType type) {
    	init();
    	tAddMessage(messages, type);
	}

    public TMessageBox(String header, List<String> messages, TMessageType type) {
    	init();
    	settHeader(header);
    	tAddMessage(messages, type);
	}
    
    public TMessageBox(List<TMessage> messages) {
    	init();
    	tAddMessage(messages);
	}
    
    public TMessageBox(String header, List<TMessage> messages) {
    	init();
    	settHeader(header);
    	tAddMessage(messages);
	}
    
    public TMessageBox(Throwable e) {
    	init();
    	process(e);
	}
    
    public TMessageBox(Throwable e, String header) {
    	init();
    	settHeader(header);
    	process(e);
    }

	public TMessageBox(TValidatorException e) {
    	init();
    	processValidator(e);
	}

	public TMessageBox(TValidatorException e, String header) {
    	init();
    	settHeader(header);
    	processValidator(e);
	}
	/**
	 * @param e
	 */
	public void process(Throwable e) {
		String msg = e.getMessage();
    	if(StringUtils.isBlank(msg))
    		msg = TFxKey.MESSAGE_ERROR;
    	tAddMessage(Arrays.asList(msg), TMessageType.ERROR);
	}
    
	/**
	 * @param e
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void processValidator(TValidatorException e) {
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
    	tAddMessage(messages, TMessageType.WARNING);
	}

	private void init() {
    	repo = new TRepository();
    	header = new SimpleStringProperty();
    	messages = FXCollections.observableArrayList();
    	
    	ListChangeListener<TMessage> lstChl = c ->{
    		if(c.next()) {
    			if(c.wasAdded())
    				this.addMessagePane(c.getAddedSubList());
    			else if(c.wasRemoved() && c.getList().size()==0)
    				this.buildMessagePane();
    		}
    	};
    	repo.add("lstChl", lstChl);
		messages.addListener(new WeakListChangeListener<>(lstChl));
		
    	super.setStyle("-fx-background-color: transparent");
    	
    	setMaxWidth(530);
    	
    	scroll = new ScrollPane();
    	scroll.setFitToHeight(true);
    	scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scroll.setStyle("-fx-background-color: transparent");
    	scroll.setPadding(new Insets(8));
    	
		buildMessagePane();
		
    	BorderPane vb = new BorderPane();
		vb.setCenter(scroll);
		
    	ChangeListener<String> headerChl = (a,o,n)->{
    		vb.setTop(null);
    		vb.setId(null);
    		if(n!=null) {
	    		size = 50;
	    		Label text = new Label(n);
	    		text.setId("t-title-label");
	    		StackPane sp = new StackPane();
	    		sp.getChildren().add(text);
	    		sp.getStyleClass().add("t-panel-color");
	    		sp.setStyle("-fx-background-radius: 20 20 0 0;");
	    		StackPane.setAlignment(text, Pos.CENTER_LEFT);
	    		StackPane.setMargin(text, new Insets(5,5,5,10));
	    		vb.setId("t-view");
	    		vb.setTop(sp);
	    	}
    	};
    	repo.add("headerChl", headerChl);
    	header.addListener(new WeakChangeListener<>(headerChl));
		StackPane main = new StackPane();
    	
    	main.getChildren().add(vb);
    	super.getChildren().add(main);
	}

	private void buildMessagePane() {
		if(messagesPane==null) {
			messagesPane = new VBox(5);
	    	messagesPane.setAlignment(Pos.TOP_CENTER);
	    	messagesPane.setStyle("-fx-background-color: transparent");
			scroll.setContent(this.messagesPane);
		}else
			this.messagesPane.getChildren().clear();
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
    
	public void tAddMessage(List<String> messages, TMessageType type) {
        if(messages!=null)
	        for (String m : messages)
				tAddMessage(m, type, null, null);
	}
	
	public void tAddMessage(List<TMessage> messages) {
		this.messages.addAll(messages);
	}
	
	public void tAddMessage(String msg) {
		this.tAddMessage(msg, TMessageType.GENERIC, null, null);
	}
	
	public void tClearMessages() {
		this.messages.clear();
	}
	
	private void addMessagePane(List<? extends TMessage> lst) {
		lst.forEach(t -> {
			Pane p = buildMessagePane(t);
			if(p!=null)
				messagesPane.getChildren().add(p);
		});
		
	}
	
	
	private void tAddMessage(String value, TMessageType type, String buttonText, Consumer<ActionEvent> buttonAction) {
		messages.add(new TMessage(type, value, buttonText, buttonAction));
	}
	
	public void tDispose() {
		this.repo.clear();
		this.header = null;
		this.messages = null;
		this.messagesPane = null;
		this.scroll = null;
		this.getChildren().clear();
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
			icon.setMinSize(28, 28);
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
		text.settTextStyle(TTextStyle.CUSTOM);
		text.setWrappingWidth(300);
		return text;
	}
	
}
