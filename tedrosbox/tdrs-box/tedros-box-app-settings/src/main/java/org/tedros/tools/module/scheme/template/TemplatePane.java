/**
 * 
 */
package org.tedros.tools.module.scheme.template;

import java.io.IOException;
import java.io.InputStream;

import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.tools.module.scheme.SchemeModule;
import org.tedros.util.TColorUtil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * @author Davis Gordon
 *
 */
public class TemplatePane extends StackPane {

	private BorderPane root = new BorderPane();
	private ToolBar toolBar;
	private ToolBar pageToolBar;
	private Label appName;
	private TopMenu topMenu;
	private Button tile;
	private Pane pageArea;
	
	public TemplatePane() {
		
		TLanguage iEngine = TLanguage.getInstance(null);
		
		setDepthTest(DepthTest.DISABLE);
        getChildren().add(root);
        
        root.getStyleClass().add("application");
		root.setId("t-tedros-color");
        
        // create main toolbar
        toolBar = new ToolBar();
        toolBar.setId("t-main-toolbar");
        
      //add appName
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.BLACK);
        
        StackPane logoPane = new StackPane();
        InputStream is = TedrosContext.getImageInputStream("logo-tedros-small.png");
        Image logo = new Image(is);
        try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        ImageView imgLogo = new ImageView();
        imgLogo.setImage(logo);
        imgLogo.setEffect(ds);
        
        appName = new Label();
        appName.setEffect(ds);
        appName.setCache(true);
        appName.setText("Tedros Box");
        appName.setId("t-app-name");
        
        HBox h = new HBox();
        h.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(imgLogo, new Insets(8,0,0,8));
        h.getChildren().addAll(imgLogo);
        
        logoPane.getChildren().addAll(h, appName);
        StackPane.setMargin(appName, new Insets(0,0,0,55));
        toolBar.getItems().add(logoPane);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolBar.getItems().add(spacer);
        toolBar.setPrefHeight(50);
        toolBar.setMinHeight(50);
        toolBar.setMaxHeight(50);
        GridPane.setConstraints(toolBar, 0, 0);
        // add close min max
        final HeaderButton windowButtons = new HeaderButton();
        toolBar.getItems().add(windowButtons);
        /// create page toolbar
        pageToolBar = new ToolBar();
        pageToolBar.setId("t-tedros-toolbar");
        pageToolBar.setMaxSize(Double.MAX_VALUE, Control.USE_PREF_SIZE);
        // Inicio breadcrumbar
        topMenu = new TopMenu();
        topMenu.setPath(iEngine.getString("#{template.menu}"));
        pageToolBar.getItems().addAll(topMenu);
        // Fim breadcrumbar 
     // create page area
        pageArea = new Pane() {
            @Override protected void layoutChildren() {
                for (Node child:pageArea.getChildren()) {
                    child.resizeRelocate(0, 0, pageArea.getWidth(), pageArea.getHeight());
                }
            }
        };
        pageArea.setId("t-app-area");
        pageArea.setStyle("-fx-background-color: transparent;");
        // create main pane
        BorderPane mainPane = new BorderPane();
        BorderPane.setMargin(pageToolBar, new Insets(0,0,0,82));
        mainPane.setTop(pageToolBar);
        mainPane.setCenter(pageArea);
        mainPane.setMinWidth(300);
        mainPane.setStyle("-fx-effect: dropshadow( three-pass-box , #000000 , 9, 0.1 , 0 , 4); "
        		+ "-fx-text-fill: #FFFFFF; -fx-background-color: transparent;");
        
        root.setTop(toolBar);
        root.setCenter(mainPane);
        root.setStyle("-fx-background-color: transparent;");
        
        ImageView icon = TedrosAppManager.getInstance().getModuleContext(SchemeModule.class).getIcon();
        
        tile = new Button(iEngine.getString("#{label.app.title}"), icon);
        tile.setMinSize(140,145);
        tile.setPrefSize(140,145);
        tile.setMaxSize(140,145);
        tile.setContentDisplay(ContentDisplay.TOP);
        tile.getStyleClass().clear();
        tile.getStyleClass().add("t-app-tile");
        
        pageArea.getChildren().add(tile);
        
	}
	
	public void settIconStyle(Color c, String t) {
		String f = TColorUtil.toHexadecimal(c);
		String s = "-fx-skin: \"com.sun.javafx.scene.control.skin.ButtonSkin\";\r\n" + 
				"    -fx-text-fill: "+f+";\r\n" + 
				"    -fx-graphic-hpos: center;\r\n" + 
				"    -fx-graphic-vpos: top;\r\n" + 
				"    -fx-alignment: center;\r\n" + 
				"    -fx-padding: 4px 4px 3px 4px;\r\n" + 
				"    -fx-font-size: "+t+"em;";
		this.tile.setStyle(s);
	}
	
	public void settTopMenuBarStyle(Color t, Color c, String o) {
		java.awt.Color rgb = TColorUtil.hex2Rgb(TColorUtil.toHexadecimal(c));
		String r = String.valueOf(rgb.getRed());
		String g = String.valueOf(rgb.getGreen());
		String b = String.valueOf(rgb.getBlue());
		String v = TColorUtil.toHexadecimal(t);
		String s = "-fx-font-size: 1.4em;\r\n" + 
				"	-fx-text-fill: "+v+";\r\n" + 
				"	-fx-background-color: rgba("+r+","+g+","+b+","+o.replaceAll(",", ".")+");\r\n" +
				"   -fx-spacing: 0;";
		this.pageToolBar.setStyle(s);
	}
	
	public void settTopMenuStyle(Color c) {
		String v = TColorUtil.toHexadecimal(c);
		String s = "-fx-padding: 0;\r\n" + 
				"	-fx-text-fill: "+v+";";
		this.topMenu.setStyle(s);
		
		this.topMenu.getChildren().forEach(n->{
			n.setStyle("-fx-text-fill: "+v);
		});
	}
	
	public void settAppNameStyle(Color c) {
		String v = TColorUtil.toHexadecimal(c);
		String s = "-fx-font: Arial;\r\n" + 
				"	-fx-font-size: 22;\r\n" + 
				"	-fx-font-weight: bold;\r\n" + 
				"	-fx-font-smoothing-type:lcd;\r\n" + 
				"	-fx-text-fill: "+v+";";
		this.appName.setStyle(s);
	}
	
	public void settHeaderStyle(Color c, String o) {
		java.awt.Color rgb = TColorUtil.hex2Rgb(TColorUtil.toHexadecimal(c));
		String r = String.valueOf(rgb.getRed());
		String g = String.valueOf(rgb.getGreen());
		String b = String.valueOf(rgb.getBlue());
		
		String s = "-fx-background-color: radial-gradient(focus-angle 105deg , focus-distance -3% , center -11% 48% , radius 120% , rgba("+r+","+g+","+b+","+o.replaceAll(",", ".")+") 30% , rgb(0,0,0,0));\r\n" + 
				"	-fx-background-size: cover;\r\n" + 
				"	-fx-border-color: transparent transparent lightslategrey  transparent;\r\n" + 
				"	-fx-border-width: 0.9;\r\n" + 
				"    -fx-border-insets: 0, 0 0 1 0;";
		this.toolBar.setStyle(s);
	}

	
}
