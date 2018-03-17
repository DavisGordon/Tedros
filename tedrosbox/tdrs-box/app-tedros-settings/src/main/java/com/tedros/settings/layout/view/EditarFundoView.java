package com.tedros.settings.layout.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.style.TStyleResourceName;
import com.tedros.settings.layout.TemplateStyleId;
import com.tedros.settings.layout.template.GradientBuilderApp;
import com.tedros.settings.start.TConstant;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolderEnum;

public class EditarFundoView extends StackPane {
	
	@FXML public Label viewTitleLabel;
    @FXML public Button aplicarBtn;
    @FXML private Button radialBtn;
    @FXML private Button imagemBtn;
    @FXML private StackPane formSpace;
    final private SelecionarImagemView selecionarImagemView;
    final private Node gradientPane;
    final private SimpleStringProperty backgroundStyleProperty = new SimpleStringProperty();
    
    private enum Radial{
		FOCUS_ANGLE("focus-angle"),
		FOCUS_DISTANCE("focus-distance"),
		CENTER("center"),
		RADIUS("radius"),
		REPEAT("repeat"),
		REFLECT("reflect"),
		COLOR_STOP("#");
		
		private String atr;
		
		private Radial(String atr){
			this.atr = atr;
			
		}
		
		@Override
		public String toString() {
			return atr;
		}
	}
    
    
	//public EditarFundoView(final TedrosTemplate template) throws Exception {
    public EditarFundoView() throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("customizar_fundo.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
		
        this.gradientPane = new GradientBuilderApp().build(backgroundStyleProperty);
        this.selecionarImagemView = new SelecionarImagemView();
        
        this.viewTitleLabel.setText(TInternationalizationEngine.getInstance(TConstant.UUI).getString("#{label.background}"));
        
        final ScrollPane scroll = new ScrollPane();
        scroll.setStyle("-fx-background-color: transparent;");
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setContent(selecionarImagemView);
        aplicarBtn.setText(TInternationalizationEngine.getInstance(TConstant.UUI).getString("#{label.apply}"));
        aplicarBtn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					File css = new File(TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+"background-image.css");
					if(scroll.getContent().equals(selecionarImagemView)){
						String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.BACKGROUND_STYLE;
						String imageName = (String) selecionarImagemView.imageView.getUserData();
						String repeat = selecionarImagemView.repetirCheckBox.isSelected() ? "repeat" : "no-repeat";
						String ativar = selecionarImagemView.ativarCheckBox.isSelected() ? "true" : "false";
						FileOutputStream fos = new FileOutputStream(propFilePath);
						Properties prop = new Properties();
						prop.setProperty("image", imageName);
						prop.setProperty("repeat", repeat);
						prop.setProperty("ativar", ativar);
						prop.store(fos, "background styles");
						
						StringBuffer sbf = new StringBuffer("#t-tedros-color { -fx-background-size: auto; -fx-background-image: url(\"../IMAGES/FUNDO/"+imageName+"\"); -fx-background-repeat: "+repeat+"; }");
						if(css.exists()){
							css.delete();
							css.createNewFile();
						}
						TFileUtil.saveFile(sbf.toString(), css);
					}else{
						final String backgroundCssStyle = backgroundStyleProperty.getValue();
						StringBuffer sbf = new StringBuffer("#t-tedros-color { "+backgroundCssStyle+" }");
						if(css.exists()){
							css.delete();
							css.createNewFile();
						}
						TFileUtil.saveFile(sbf.toString(), css);
						
						try {
							final String radial = backgroundCssStyle.replaceAll(TemplateStyleId.BACKGROUND_COLOR.toString(), "");
							String focusAngle = null;
							String focusDistance = null;
							String center = null;
							String radius = null;
							String reflect = null;
							String repeat = null;
							String colorStop = null;
							
							if(radial.contains(Radial.FOCUS_ANGLE.toString()))
								focusAngle = StringUtils.substringBetween(radial, Radial.FOCUS_ANGLE.toString(), ",");
							if(radial.contains(Radial.FOCUS_DISTANCE.toString()))
								focusDistance = StringUtils.substringBetween(radial, Radial.FOCUS_DISTANCE.toString(), ",");
							if(radial.contains(Radial.CENTER.toString()))
								center = StringUtils.substringBetween(radial, Radial.CENTER.toString(), ",");
							if(radial.contains(Radial.RADIUS.toString()))
								radius = StringUtils.substringBetween(radial, Radial.RADIUS.toString(), ",");
							if(radial.contains(Radial.REFLECT.toString()))
								reflect = Radial.REFLECT.name().toLowerCase();
							if(radial.contains(Radial.REPEAT.toString()))
								repeat = Radial.REPEAT.name().toLowerCase();
							
							if(radial.contains(Radial.COLOR_STOP.toString()))
								colorStop = StringUtils.substringBetween(radial, Radial.COLOR_STOP.toString(), ")");
							
							String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.BACKGROUND_STYLE;
							FileOutputStream fos = new FileOutputStream(propFilePath);
							Properties prop = new Properties();
							
							if(null!=focusAngle) prop.setProperty(Radial.FOCUS_ANGLE.name(), focusAngle);
							if(null!=focusDistance) prop.setProperty(Radial.FOCUS_DISTANCE.name(), focusDistance);
							if(null!=center) prop.setProperty(Radial.CENTER.name(), center);
							if(null!=radius) prop.setProperty(Radial.RADIUS.name(), radius);
							if(null!=reflect) prop.setProperty(Radial.REFLECT.name(), reflect);
							if(null!=repeat) prop.setProperty(Radial.REPEAT.name(), repeat);
							if(null!=colorStop) prop.setProperty(Radial.COLOR_STOP.name(), colorStop);
							
							prop.store(fos, "background-style");
							
						} catch (Exception  e1) {
							e1.printStackTrace();
						}
					}
					TedrosContext.reloadStyle();
				} catch (FileNotFoundException  e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
        
        radialBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				scroll.setContent(gradientPane);
			}
		});
        
        imagemBtn.setText(TInternationalizationEngine.getInstance(TConstant.UUI).getString("#{label.image}"));
        imagemBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				scroll.setContent(selecionarImagemView);
			}
		});
        
        formSpace.getChildren().add(scroll);	
	}

	

}
