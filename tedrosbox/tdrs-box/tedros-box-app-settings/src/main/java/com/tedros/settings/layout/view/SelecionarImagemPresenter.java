package com.tedros.settings.layout.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.style.TStyleResourceName;
import com.tedros.settings.start.TConstant;
import com.tedros.util.TFileUtil;
import com.tedros.util.TUrlUtil;
import com.tedros.util.TedrosFolderEnum;

public class SelecionarImagemPresenter {

	private SelecionarImagemView view;
	
	public SelecionarImagemPresenter(SelecionarImagemView v) throws MalformedURLException {
		this.view = v;
		final FileChooser fileChooser = new FileChooser();
		
		String propFilePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+TStyleResourceName.BACKGROUND_STYLE;
		Properties prop = new Properties();
		try {
			InputStream is = new FileInputStream(propFilePath);
			prop.load(is);
			if(StringUtils.isNotBlank(prop.getProperty("image"))){
				view.imageView.setImage(new Image(TedrosContext.getBackGroundImageInputStream(prop.getProperty("image"))));
				view.imageView.autosize();
				view.imageView.setUserData(prop.getProperty("image"));
			}
			
			view.repetirCheckBox.setText(TInternationalizationEngine.getInstance(TConstant.UUI).getString("#{label.repeat}"));
			String repeat = prop.getProperty("repeat");
			if(StringUtils.isNotBlank(repeat) && repeat.equals("repeat"))
				view.repetirCheckBox.setSelected(true);
			else
				view.repetirCheckBox.setSelected(false);
			
			view.ativarCheckBox.setText(TInternationalizationEngine.getInstance(TConstant.UUI).getString("#{label.enable}"));
			String ativar = prop.getProperty("ativar");
			if(StringUtils.isNotBlank(ativar) && ativar.equals("true"))
				view.ativarCheckBox.setSelected(true);
			else
				view.ativarCheckBox.setSelected(false);
			
		} catch (FileNotFoundException  e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		this.view.selecioneBtn.setId("t-button");
		this.view.selecioneBtn.setText(TInternationalizationEngine.getInstance(TConstant.UUI).getString("#{label.selectimage}"));
		this.view.selecioneBtn.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        configureFileChooser(fileChooser);
                        File file = fileChooser.showOpenDialog(TedrosContext.getStage());
                        if (file != null) {
                            openFile(file);
                        }
                    }
                });
        
        loadSavedImages();
		
	}
	
	private void loadSavedImages() throws MalformedURLException {
		File imagesfolder = new File(TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.BACKGROUND_IMAGES_FOLDER.getFolder());
		this.view.imagens.getChildren().clear();
    	if(imagesfolder.exists()){
    		
    		File[] listOfFiles = imagesfolder.listFiles();
    		for(int i = 0; i < listOfFiles.length; i++){
    			if(listOfFiles[i].isFile()){
    				String file = listOfFiles[i].getName();
    				if (file.toLowerCase().endsWith(".jpg") || file.toLowerCase().endsWith(".png")){
    					buildImageViewIcon(file);
    				}
    			}
    		}
    			
    		
    	}
	}

	private void buildImageViewIcon(final String file) {
		InputStream is = TedrosContext.getBackGroundImageInputStream(file);
		final ImageView imgView = new ImageView(new Image(is));
		imgView.fitHeightProperty().setValue(150);
		imgView.fitWidthProperty().setValue(200);
		imgView.setCursor(Cursor.HAND);
		imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				view.imageView.setImage(imgView.getImage());
				view.imageView.setUserData(file);
			}
		});
		this.view.imagens.getChildren().add(0,imgView);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void configureFileChooser(final FileChooser fileChooser){                           
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
    }
            
 
    private void openFile(File file) {
    	try {
    		String imagePath = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.BACKGROUND_IMAGES_FOLDER.getFolder()+file.getName();
    		File image = new File(imagePath);
    		FileInputStream fi = new FileInputStream(file);
            ImageIO.write(SwingFXUtils.fromFXImage(new Image(fi),null), "png", image);
            this.view.imageView.setImage(new Image(TUrlUtil.getURL(imagePath).toExternalForm()));
            this.view.imageView.setUserData(file.getName());
            buildImageViewIcon(file.getName());
            fi.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    	
    }
	
	
}
