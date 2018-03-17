/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 21/03/2014
 */
package com.tedros.fxapi.reader;

import java.io.ByteArrayInputStream;
import java.io.File;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.modal.TModalPane;
import com.tedros.util.TUrlUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TByteArrayReader extends StackPane {
	
	private BorderPane boxImageSpace;
	private VBox vBox;
	private Label fileNameLabel;
    private StackPane imageSpace;
    private ImageView imageView;
    private Button openButton;
    private ToolBar toolbar;
	
	private SimpleObjectProperty<byte[]> valueProperty;
	private SimpleStringProperty fileNameProperty;
	
	private boolean showImage;
	private final TModalPane modal;
	
	public TByteArrayReader() {
		
		fileNameLabel = new Label();
		fileNameProperty = new SimpleStringProperty();
		valueProperty = new SimpleObjectProperty<>();
		vBox = new VBox();
		boxImageSpace = new BorderPane();
		toolbar = new ToolBar();
		openButton = new Button();
		boxImageSpace.setId("t-image-pane");
		
		toolbar.setId("t-file-toolbar");
		toolbar.setMaxWidth(Double.MAX_VALUE);
		toolbar.autosize();
		openButton.setText("Abrir");
		
		setAlignment(toolbar, Pos.CENTER_LEFT);
		
		openButton.setId("t-last-button");
		
		toolbar.getItems().addAll(openButton);
		
		valueProperty.addListener(new ChangeListener<byte[]>() {
			@Override
			public void changed(ObservableValue<? extends byte[]> arg0,
					byte[] arg1, byte[] arg2) {
				if(showImage && arg2!=null ){
					if((fileNameProperty!=null && StringUtils.isNotBlank(fileNameProperty.getValue()))){
						String[] arr = TFileExtension.ALL_IMAGES.getExtension();
						for (String ext : arr) {
							final String[] e = ext.split("\\.");
							if(e[1].equals("\\\\*"))
								continue;
							String tmp[] = fileNameProperty.getValue().split("\\.");
							if(StringUtils.containsIgnoreCase(tmp[1], e[1])){
								fileNameLabel.setText(fileNameProperty.getValue());
								Image image = new Image(new ByteArrayInputStream(arg2));
								imageView.setImage(image);
							}
						}
					}
				}
			}
		});
		
		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				if(valueProperty!=null && valueProperty.getValue()!=null && fileNameProperty!=null && fileNameProperty.getValue()!=null){
					try {
						String folder = FileUtils.getUserDirectoryPath();
						
						File file = FileUtils.toFile(TUrlUtil.getURL(folder+"/"+fileNameProperty.getValue()));
						int x = 1;
						boolean flag = file.exists();
						while(flag){
							String[] arr = new String[]{FilenameUtils.getBaseName(file.getName()), FilenameUtils.getExtension(file.getName())};
							file = FileUtils.toFile(TUrlUtil.getURL(folder+"/"+arr[0]+" "+(x++)+"."+arr[1]));
							flag = file.exists();
						}
						
						FileUtils.writeByteArrayToFile(file, valueProperty.getValue());
						String[] cmdArray = {"explorer.exe", file.getAbsolutePath()};
						java.lang.Runtime.getRuntime().exec(cmdArray);
					} catch (Exception e) {
						showModal("N�o foi possivel abrir o arquivo! Erro:"+e.getMessage());
					}
				}
				
				
				
			}
		});
		
		vBox.setAlignment(Pos.CENTER_LEFT);
		
		HBox hBox = new HBox();
		HBox.setHgrow(toolbar, Priority.ALWAYS);
		hBox.getChildren().add(toolbar);
		vBox.getChildren().addAll(boxImageSpace, fileNameLabel, hBox);
		getChildren().add(vBox);
		
		modal = new TModalPane(this);
	}
	
	private void showModal(String msg){
		Label label = new Label(msg);
		label.setId("t-label");
		label.setStyle(	"-fx-font: Arial; "+
						"-fx-font-size: 1.0em; "+
						"-fx-font-weight: bold; "+
						"-fx-font-smoothing-type:lcd; "+
						"-fx-text-fill: #ffffff; "+
						"-fx-padding: 2 5 5 2; ");
		modal.showModal(label, true);
	}
	
	public final SimpleObjectProperty<byte[]> valueProperty() {
		return valueProperty;
	}

	
	public final SimpleStringProperty fileNameProperty() {
		return fileNameProperty;
	}
	
	public final byte[] getValue() {
		return (valueProperty!=null) ? valueProperty.getValue() : null;
	}
	
	public final String getFileName() {
		return (fileNameProperty!=null) ? fileNameProperty.getValue() : null;
	}
	
	public final void setFileName(String fileName) {
		fileNameProperty.setValue(fileName);
	}
	
	public final void setValue(byte[] byteArray) {
		this.valueProperty.set(byteArray);
	}

	public final boolean isShowImage() {
		return showImage;
	}

	public final void setShowImage(boolean showImage) {
		if(showImage){
			addImageView();
		}else{
			removeImageView();
		}
		this.showImage = showImage;
		showHideImageSpace();
	}
	
	private void showHideImageSpace(){
		if(!showImage){
			vBox.getChildren().remove(boxImageSpace);
		}else if(!vBox.getChildren().contains(boxImageSpace)){
			vBox.getChildren().add(0, boxImageSpace);
		}
	}

	private void removeImageView() {
		boxImageSpace.setCenter(null);
	}

	private void addImageView() {
		imageView = new ImageView();
		imageSpace = new StackPane();
		imageSpace.setPadding(new Insets(5,10,5,10));
		imageSpace.getChildren().add(imageView);
		boxImageSpace.setCenter(imageSpace);
		
	}
	
	public final Label getFileNameLabel() {
		return fileNameLabel;
	}

	public final StackPane getImageSpace() {
		return imageSpace;
	}

	public final ImageView getImageView() {
		return imageView;
	}

	public final Button getOpenButton() {
		return openButton;
	}
	
	
}
