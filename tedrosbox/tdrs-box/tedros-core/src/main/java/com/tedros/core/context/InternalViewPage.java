package com.tedros.core.context;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.TModule;
import com.tedros.core.control.PopOver;
import com.tedros.core.control.PopOver.ArrowLocation;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


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
    
	public Node createModule(){
    	try {
            // create view
    		context.buildModule();
            final TModule view = (TModule) context.getModule();
            return view;            
        } catch (Exception e) {
            e.printStackTrace();
            return new Text("Failed to create view because of ["+e.getMessage()+"]");
        }
    }

	private void setMenuIcon() {
		if(context.getModuleDescriptor().getMenuIconImageViewClass()!=null){
			try {
				ImageView icon = context.getModuleDescriptor().getMenuIconImageViewClass().newInstance();
				icon.setFitHeight(24);
				icon.setFitWidth(24);
				setGraphic(icon);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
        }
	}
    
    private Node getIcon() throws InstantiationException, IllegalAccessException {
    	
    	if(context.getModuleDescriptor().getIconImageViewClass()!=null){
    		return context.getModuleDescriptor().getIconImageViewClass().newInstance();
    	}else{
            
        	/*ImageView imageView = new ImageView(new Image(TedrosContext.getExternalURLFile(TedrosFolderEnum.IMAGES_FOLDER, "icon-overlay.png").toString()));
            imageView.setMouseTransparent(true);
            Rectangle overlayHighlight = new Rectangle(-8,-8,130,130);
            overlayHighlight.setFill(new LinearGradient(0,0.5,0,1,true, CycleMethod.NO_CYCLE, new Stop[]{ new Stop(0,Color.BLACK), new Stop(1,Color.web("#444444"))}));
            overlayHighlight.setOpacity(0.8);
            overlayHighlight.setMouseTransparent(true);
            overlayHighlight.setBlendMode(BlendMode.ADD);
            Rectangle background = new Rectangle(-8,-8,130,130);
            background.setFill(Color.web("#b9c0c5"));
            Group group = new Group(background);
            Rectangle clipRect = new Rectangle(114,114);
            clipRect.setArcWidth(38);
            clipRect.setArcHeight(38);
            group.setClip(clipRect);
            Node content = createIconContent();
            if (content != null) {
                content.setTranslateX((int)((114-content.getBoundsInParent().getWidth())/2)-(int)content.getBoundsInParent().getMinX());
                content.setTranslateY((int)((114-content.getBoundsInParent().getHeight())/2)-(int)content.getBoundsInParent().getMinY());
                group.getChildren().add(content);
            }
            group.getChildren().addAll(overlayHighlight,imageView);
            // Wrap in extra group as clip dosn't effect layout without it
            //return new Group(group);*/
            
        	return null;
        }
    }

    /*@SuppressWarnings("unchecked")
	public Node createIconContent() {
        try {
            Method createIconContent = viewClass.getDeclaredMethod("createIconContent");
            return (Node)createIconContent.invoke(viewClass);
        } catch (NoSuchMethodException e) {
            System.err.println("view ["+getName()+"] is missing a icon.");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Node createTile() {
        
    	Node icon;
    	String desc = context.getModuleDescriptor().getDescription();
		try {
			icon = getIcon();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			icon = null;
		}
    	
    	if(icon==null){
    	
	    	final BorderPane pane = new BorderPane();
	        pane.setId("t-module-icon");
	    	pane.setPrefSize(214, 114);
	        pane.setMaxSize(214, 114);
	        pane.setMinSize(214, 114);
	        
	        final Label moduleName = new Label(getName().trim());
	        moduleName.setId("t-module-name");
	        moduleName.setWrapText(true);
	        
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
            tile.setMinSize(140,145);
            tile.setPrefSize(140,145);
            tile.setMaxSize(140,145);
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
				Label l = new Label(TInternationalizationEngine.getInstance(null).getString(desc));
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
