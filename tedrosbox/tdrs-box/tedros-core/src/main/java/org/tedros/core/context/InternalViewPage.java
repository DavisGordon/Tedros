package org.tedros.core.context;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.TModule;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.PopOver.ArrowLocation;
import org.tedros.core.style.TStyleResourceValue;
import org.tedros.util.TLoggerUtil;
import org.tedros.util.TedrosFolder;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


class InternalViewPage extends Page{
	
	protected TModuleContext context;
    
	public InternalViewPage(TModuleContext context){ 
        super(context.getModuleDescriptor().getModuleName());
        this.context = context;
        setMenuIcon();
    }

    public InternalViewPage(InternalViewPage pageToClone){
        super(pageToClone.getName());
        context = pageToClone.context;
        setMenuIcon();
    }
    
    public Node getModule() {
    	return (TModule) context.getModule();
    }
    
	public Node createModule(){
    	try {
            // create view
    		context.buildModule();
            return getModule();
        } catch (Exception e) {
        	TLoggerUtil.error(getClass(), e.getMessage(), e);
            return new Text("Failed to create view because of ["+e.getMessage()+"]");
        }
    }

	private void setMenuIcon() {
		ImageView icon = context.getMenuIcon();
		if(icon!=null){
			icon.setFitHeight(24);
			icon.setFitWidth(24);
			setGraphic(icon);
        }
	}
    
    private Node getIcon() throws InstantiationException, IllegalAccessException {
    	
    	double size = Double.valueOf(TStyleResourceValue.APP_ICON_SIZE.defaultStyle(true));
    	ImageView icon = context.getIcon();
    	if(icon!=null){
    		icon.setFitHeight(size);
    		icon.setFitWidth(size);
    		return icon;
    	}else{
            Integer r =  Integer.valueOf(TStyleResourceValue.PANEL_BACKGROUND_RED.customStyle(true));
            Integer g =  Integer.valueOf(TStyleResourceValue.PANEL_BACKGROUND_GREEN.customStyle());
            Integer b =  Integer.valueOf(TStyleResourceValue.PANEL_BACKGROUND_BLUE.customStyle());
            Random random = new Random();
            Integer r1 =  random.nextInt(0, 255);
            Integer g1 =  random.nextInt(0, 255);
            Integer b1 =  random.nextInt(0, 255);
            
            double overlayDiff = 16; 
            
        	ImageView imageView = new ImageView(
        			new Image(TedrosContext.getExternalURLFile(TedrosFolder.IMAGES_FOLDER, "icon-overlay.png").toString()));
            imageView.setMouseTransparent(true);
            
            Rectangle overlayHighlight = new Rectangle(-8,-8,size+overlayDiff,size+overlayDiff);
            overlayHighlight.setFill(
            		new LinearGradient(0,0.5,0,1,true, CycleMethod.NO_CYCLE, 
            				new Stop[]{ new Stop(0,Color.rgb(r, g, b)), 
            						new Stop(1,Color.rgb(r1, g1, b1))})
            );
            overlayHighlight.setOpacity(0.8);
            overlayHighlight.setMouseTransparent(true);
            overlayHighlight.setBlendMode(BlendMode.ADD);
            
            r =  Integer.valueOf(TStyleResourceValue.FORM_BACKGROUND_RED.customStyle());
            g =  Integer.valueOf(TStyleResourceValue.FORM_BACKGROUND_GREEN.customStyle());
            b =  Integer.valueOf(TStyleResourceValue.FORM_BACKGROUND_BLUE.customStyle());
            Double o = 0.1;
           
            Rectangle background = new Rectangle(-8,-8,size+overlayDiff,size+overlayDiff);
            background.setFill(Color.rgb(r, g, b, o));
            Group group = new Group(background);
            Rectangle clipRect = new Rectangle(size,size);
            clipRect.setArcWidth(38);
            clipRect.setArcHeight(38);
            group.setClip(clipRect);
            Label content = new Label(getName().trim());
            content.setFont(Font.font("Bungee", FontWeight.BOLD, 30));
            if (content != null) {
                content.setTranslateX((int)((size-content.getBoundsInParent().getWidth())/3)-(int)content.getBoundsInParent().getMinX());
                content.setTranslateY((int)((size-content.getBoundsInParent().getHeight())/2)-(int)content.getBoundsInParent().getMinY());
                group.getChildren().add(content);
            }
            group.getChildren().addAll(overlayHighlight,imageView);
            // Wrap in extra group as clip dosn't effect layout without it
            return new Group(group);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Node createTile() {
        
    	Node icon;
    	String desc = context.getModuleDescriptor().getDescription();
		try {
			icon = getIcon();
		} catch (InstantiationException | IllegalAccessException e) {
			TLoggerUtil.error(getClass(), e.getMessage(), e);
			icon = null;
		}
    	
    	if(icon==null){
    		
    		double size = Double.valueOf(TStyleResourceValue.APP_ICON_SIZE.defaultStyle(true));
    	
	    	final BorderPane pane = new BorderPane();
	        pane.setId("t-module-icon");
	    	pane.setPrefSize(214, size);
	        pane.setMaxSize(214, size);
	        pane.setMinSize(214, size);
	        
	        final Label moduleName = new Label(getName().trim());
	        moduleName.setId("t-module-name");
	        moduleName.setWrapText(true);
	        moduleName.setMaxWidth(214);
	        
	        final DropShadow shadow = new DropShadow();
	        shadow.setBlurType(BlurType.THREE_PASS_BOX);
	        shadow.setWidth(30);
	        shadow.setHeight(30);
	        shadow.setRadius(12);
	        shadow.setSpread(0);
	        shadow.setColor(Color.BLANCHEDALMOND);
	        
	        pane.setEffect(shadow);
	        
	        pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					Glow glow = new Glow();
					glow.setLevel(0.5);
					glow.setInput(shadow);
					pane.setEffect(glow);
				}
			});
	        pane.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					pane.setEffect(shadow);
				}
			});
	        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					TedrosContext.setPageProperty(InternalViewPage.this, true, false, true);
				}
			});       
        
        	pane.setCenter(moduleName);
        	
        	buildPopover(desc, pane);
        	
        	return pane;
        	
        }else{
        	Button tile = new Button(getName().trim(),icon);
        	tile.setWrapText(true);
        	tile.setTextAlignment(TextAlignment.CENTER);
        	tile.setTextOverrun(OverrunStyle.WORD_ELLIPSIS);
            tile.setMaxSize(140,165);
            tile.setContentDisplay(ContentDisplay.TOP);
            tile.getStyleClass().clear();
            tile.getStyleClass().add("t-app-tile");
            tile.setOnAction(new EventHandler() {
                public void handle(Event event) {
                	TedrosContext.setPageProperty(InternalViewPage.this, true, false, true);
                }
            });
            
            buildPopover(desc, tile);
            
            return tile;
        }
    }

	/**
	 * @param desc
	 * @param tile
	 */
	private void buildPopover(String desc, Node tile) {
		if(StringUtils.isNotBlank(desc)) {
			PopOver popover = new PopOver();
			tile.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
				Label l = new Label(TLanguage.getInstance(null).getString(desc));
				l.setId("t-label");
				l.setWrapText(true);
				l.setMaxWidth(350);
				StackPane p = new StackPane();
				p.getChildren().add(l);
				StackPane.setMargin(l, new Insets(5,10,5,10));
				
		    	popover.setArrowLocation(ArrowLocation.TOP_CENTER);
		    	popover.setCloseButtonEnabled(false);
		    	popover.setContentNode(p);
		    	popover.setAnimated(true);
		    	popover.setAutoHide(true);
		    	popover.setMaxWidth(350);
		    	popover.show(tile);
			});
			tile.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
				popover.hide();
			});
		}
	}
    
}
