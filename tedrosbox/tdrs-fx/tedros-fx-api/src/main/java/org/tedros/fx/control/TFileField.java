/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 21/03/2014
 */
package org.tedros.fx.control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.control.PopOver;
import org.tedros.fx.control.action.TEventHandler;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.util.TFileUtil;
import org.tedros.util.TUrlUtil;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TFileField extends StackPane {
	
	private final Stage appStage;
	private BorderPane boxImageLabelSpace;
	private VBox vBox;
	private Button cleanButton;
    private TTextField fileNameField;
    private Label filePathLabel;
    private StackPane imageSpace;
    private ImageView imageView;
    private Button openButton;
    private Button selectButton;
    private ToolBar toolbar;
    private FileChooser fileChooser;
    
	private SimpleObjectProperty<File> fileProperty;
	private SimpleObjectProperty<byte[]> byteArrayProperty;
	private SimpleStringProperty fileNameProperty;
	private SimpleLongProperty fileSizeProperty;
	private SimpleLongProperty bytesEntityIdProperty;
	
	private String[] extensions;
	private String initialDirectory = System.getProperty("user.home");
	
	private Double minFileSize;
	private Double maxFileSize;
	private Double minImageWidth;
	private Double maxImageWidth;
	private Double minImageHeight;
	private Double maxImageHeight;
	
	private boolean showImage;
	private boolean showFilePath;
	
	@SuppressWarnings("rawtypes")
	private TEventHandler openAction;
	@SuppressWarnings("rawtypes")
	private TEventHandler cleanAction;
	//private TEventHandler loadAction;
	@SuppressWarnings("rawtypes")
	private TEventHandler selectAction;
	@SuppressWarnings("rawtypes")
	private TEventHandler imageAction;
	
	private EventHandler<ActionEvent> openEventHandler;
	private EventHandler<ActionEvent> downloadEventHandler;
	
	TLanguage iEngine;
	
	public TFileField(final Stage stage) throws IOException {	
		appStage = stage;
		initialize();
		buildListeners();
	}

	private void initialize() {
		
		iEngine = TLanguage.getInstance(null);
		
		fileProperty = new SimpleObjectProperty<>();
		fileChooser = new FileChooser();
		selectButton = new Button();
		fileNameField = new TTextField();
		fileNameProperty = new SimpleStringProperty();
		fileSizeProperty = new SimpleLongProperty();
		byteArrayProperty = new SimpleObjectProperty<>();
		bytesEntityIdProperty = new SimpleLongProperty();
		
		vBox = new VBox();
		boxImageLabelSpace = new BorderPane();
		toolbar = new ToolBar();
		cleanButton = new Button();
		selectButton = new Button();
		openButton = new Button();
		
		fileNameProperty.bindBidirectional(fileNameField.textProperty());
		
		//boxImageLabelSpace.setId("t-image-pane");
		
		toolbar.setId("t-file-toolbar");
		toolbar.setMaxWidth(Double.MAX_VALUE);
		toolbar.autosize();
		
		selectButton.setText(iEngine.getString("#{tedros.fxapi.button.select}"));
		openButton.setText(iEngine.getString("#{tedros.fxapi.button.open}"));
		cleanButton.setText(iEngine.getString("#{tedros.fxapi.button.clean}"));
		
		setAlignment(toolbar, Pos.CENTER_LEFT);
		
		selectButton.setId("t-button");
		openButton.setId("t-button");
		cleanButton.setId("t-last-button");
		
		fileNameField.setStyle("-fx-text-fill: #000000; -fx-font-weight: bold; ");
		fileNameField.setPrefWidth(250);
		fileNameField.setMaxHeight(Double.MAX_VALUE);
		fileNameField.setDisable(true);
		
		extensions = TFileExtension.ALL_FILES.getExtension();
		
		toolbar.getItems().addAll(fileNameField, selectButton, openButton, cleanButton);
		
		vBox.setAlignment(Pos.CENTER_LEFT);
		HBox hBox = new HBox();
		HBox.setHgrow(toolbar, Priority.ALWAYS);
		hBox.getChildren().add(toolbar);
		
		
		
		vBox.getChildren().addAll(boxImageLabelSpace, hBox);
		getChildren().add(vBox);
	}
	
	private void buildListeners() {
			
		bytesEntityIdProperty.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				if(bytesEntityIdProperty.getValue()!=null && byteArrayProperty.getValue()==null){
					setLoadByteAction();
				}else if(bytesEntityIdProperty.getValue()!=null && byteArrayProperty.getValue()!=null){
					setShowImage(true);
					loadImageView(byteArrayProperty.getValue());
					setOpenAction();
				}else if(byteArrayProperty.getValue()!=null){
					setOpenAction();
				}
				
			}
		});	
		
		byteArrayProperty.addListener(new ChangeListener<byte[]>() {
			@Override
			public void changed(ObservableValue<? extends byte[]> arg0, byte[] arg1, byte[] arg2) {
				
				if(showImage && arg2!=null ){
					loadImageView(arg2);
				}
			}
		});
		
		selectButton.setOnAction(e->{
			configureFileChooser(fileChooser,extensions);
            final File file = fileChooser.showOpenDialog(appStage);
            if (file != null) {
            	openFile(file);
            	openButton.setDisable(false);
            }
		});
		
		cleanButton.setOnAction(e->{
			cleanField();
		});
		
		
		downloadEventHandler = arg0->{
			try {
				TBytesLoader.loadBytesFromTFileEntity(bytesEntityIdProperty.getValue(), byteArrayProperty);
			} catch (TProcessException e) {
				e.printStackTrace();
			}
		};
		
		openEventHandler = arg0->{
			if(filePathLabel!=null && StringUtils.isNotBlank(filePathLabel.getText())){
				if(!TFileUtil.open(new File(filePathLabel.getText()))) {
                	showModal(iEngine.getString("#{tedros.fxapi.message.os.not.support.operation}"));
                }
			}else{
				if(byteArrayProperty!=null && byteArrayProperty.getValue()!=null && fileNameField!=null && fileNameField.getText()!=null){
					try {
						String folder = FileUtils.getUserDirectoryPath();
						File file = FileUtils.toFile(TUrlUtil.getURL(folder+"/"+fileNameField.getText()));
						int x = 1;
						boolean flag = file.exists();
						while(flag){
							String[] arr = new String[]{FilenameUtils.getBaseName(file.getName()), FilenameUtils.getExtension(file.getName())};
							file = FileUtils.toFile(TUrlUtil.getURL(folder+"/"+arr[0]+" "+(x++)+"."+arr[1]));
							flag = file.exists();
						}
						
						FileUtils.writeByteArrayToFile(file, byteArrayProperty.getValue());
						if(!TFileUtil.open(file)) {
		                	showModal(iEngine.getString("#{tedros.fxapi.message.os.not.support.operation}"));
		                }
					} catch (Exception e) {
						showModal(iEngine.getFormatedString("#{tedros.fxapi.message.cannot.open.file}", e.getMessage()));
					}
				}
			}
		};
		
		
		openButton.setOnAction(openEventHandler);
	}
	
	private void setLoadByteAction() {
		openButton.setText(iEngine.getString("#{tedros.fxapi.button.load}"));
		openButton.setOnAction(downloadEventHandler);
	}
	
	private void setOpenAction() {
		openButton.setText(iEngine.getString("#{tedros.fxapi.button.open}"));
		openButton.setOnAction(openEventHandler);
	}
	
	private void showModal(String msg){
		Label label = new Label(msg);
		label.setId("t-label");
		label.setStyle(	"-fx-font: Arial; "+
						"-fx-font-size: 1.0em; "+
						"-fx-font-weight: bold; "+
						"-fx-font-smoothing-type:lcd; "+
						"-fx-text-fill: #000000; "+
						"-fx-padding: 2 5 5 2; ");
		
		PopOver p = new PopOver(label);
		p.show(selectButton);
	}
	
	private void configureFileChooser(final FileChooser fileChooser, String[] extensions){
		fileChooser.setTitle(TLanguage.getInstance(null).getString("#{tedros.fxapi.label.select.file}"));
        fileChooser.setInitialDirectory(new File(this.initialDirectory)); 
        for(String ext : extensions){
        	if(ext!=null)
        		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(ext, ext));
        }
    }
	
	private void openFile(File file)  {
    	try {
    		long size = FileUtils.sizeOf(file);
    		if(maxFileSize!=null && maxFileSize < size)
    			showModal(iEngine.getFormatedString("#{tedros.fxapi.message.file.max.size}", maxFileSize.toString()));
    		else if(minFileSize!=null && minFileSize > FileUtils.sizeOf(file))
    			showModal(iEngine.getFormatedString("#{tedros.fxapi.message.file.min.size}", minFileSize.toString()));
    		else{
	    		if(showFilePath)
	    			filePathLabel.setText(file.getAbsolutePath());
	    		
	            this.fileProperty.setValue(file);
	    		this.fileNameProperty.setValue(file.getName());
	    		this.byteArrayProperty.setValue(FileUtils.readFileToByteArray(file));
	    		this.fileSizeProperty.setValue(size);
    		}
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

	public final File getFile() {
		return (fileProperty!=null) ? fileProperty.getValue() : null;
	}
	
	public final SimpleObjectProperty<File> fileProperty() {
		return fileProperty;
	}
	
	public final SimpleLongProperty fileSizeProperty() {
		return fileSizeProperty;
	}
	
	public SimpleLongProperty bytesEntityIdProperty() {
		return bytesEntityIdProperty;
	}
	
	public final String[] getExtensions() {
		return extensions;
	}

	public final void setExtensions(String[] extensions) {
		this.extensions = extensions;
	}

	public final Double getMaxFileSize() {
		return maxFileSize;
	}

	public final void setMaxFileSize(Double maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public final Button getSelectFileButton() {
		return selectButton;
	}

	public final void setSelectFileButton(Button selectFileButton) {
		this.selectButton = selectFileButton;
	}

	public final SimpleObjectProperty<byte[]> byteArrayProperty() {
		return byteArrayProperty;
	}

	
	public final SimpleStringProperty fileNameProperty() {
		return fileNameProperty;
	}
	
	public final byte[] getByteArray() {
		return (byteArrayProperty!=null) ? byteArrayProperty.getValue() : null;
	}
	
	public final String getFileName() {
		return (fileNameProperty!=null) ? fileNameProperty.getValue() : null;
	}
	
	public SimpleBooleanProperty requiredProperty() {
		return fileNameField.requiredProperty();
	}
	
	public SimpleBooleanProperty requirementAccomplishedProperty() {
		return fileNameField.requirementAccomplishedProperty();
	}
	
	public boolean isRequirementAccomplished(){
		return fileNameField.isRequirementAccomplished(); 
	}

	public final void setByteArray(byte[] byteArray) {
		this.byteArrayProperty.setValue(byteArray);
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
		if(!showImage && !showFilePath){
			removeBoxImageSpace();
		}else if(!vBox.getChildren().contains(boxImageLabelSpace)){
			addBoxImageSpace();
		}
	}

	private void addBoxImageSpace() {
		vBox.getChildren().add(0, boxImageLabelSpace);
	}

	private void removeBoxImageSpace() {
		vBox.getChildren().remove(boxImageLabelSpace);
	}

	private void removeImageView() {
		boxImageLabelSpace.setCenter(null);
	}

	private void addImageView() {
		if(imageView==null){
			imageView = new ImageView();
			imageView.setCache(false);
		}
		if(imageSpace==null){
			imageSpace = new StackPane();
			imageSpace.setPadding(new Insets(5,10,5,10));
			imageSpace.getChildren().add(imageView);
		}
		if(boxImageLabelSpace.getCenter()==null)
			boxImageLabelSpace.setCenter(imageSpace);
	}
	
	public void cleanField() {
		fileProperty.setValue(null);
		fileNameProperty.setValue(null);
		byteArrayProperty.setValue(null);
		fileSizeProperty.setValue(null);
		
		if(imageView!=null)
			imageView.setImage(null);
		if(filePathLabel!=null)
			filePathLabel.setText("");
		setOpenAction();
		openButton.setDisable(true);
	}

	public final boolean isShowFilePath() {
		return showFilePath;
	}

	public final void setShowFilePath(boolean showFilePath) {
		if(showFilePath){
			addFilePath();
		}else{
			removeFilePath();
		}
		this.showFilePath = showFilePath;
		showHideImageSpace();
	}

	private void removeFilePath() {
		boxImageLabelSpace.setBottom(null);
	}

	private void addFilePath() {
		filePathLabel = new Label();
		filePathLabel.setId("t-label");
		boxImageLabelSpace.setBottom(filePathLabel);
	}

	public final Button getCleanButton() {
		return cleanButton;
	}

	public final TTextField getFileNameField() {
		return fileNameField;
	}

	public final Label getFilePathLabel() {
		return filePathLabel;
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

	public final Button getSelectButton() {
		return selectButton;
	}
	
	public final Double getMinFileSize() {
		return minFileSize;
	}

	public final void setMinFileSize(Double minFileSize) {
		this.minFileSize = minFileSize;
	}

	public final Double getMinImageWidth() {
		return minImageWidth;
	}

	public final void setMinImageWidth(Double minImageWidth) {
		this.minImageWidth = minImageWidth;
	}

	public final Double getMaxImageWidth() {
		return maxImageWidth;
	}

	public final void setMaxImageWidth(Double maxImageWidth) {
		this.maxImageWidth = maxImageWidth;
	}

	public final Double getMinImageHeight() {
		return minImageHeight;
	}

	public final void setMinImageHeight(Double minImageHeight) {
		this.minImageHeight = minImageHeight;
	}

	public final Double getMaxImageHeight() {
		return maxImageHeight;
	}

	public final void setMaxImageHeight(Double maxImageHeight) {
		this.maxImageHeight = maxImageHeight;
	}	
	
	public void setRequired(boolean required){
		if(fileNameField!=null)
			fileNameField.setRequired(required);
	}
	
	public StringProperty textProperty(){
		return fileNameField.textProperty();
	}

	@SuppressWarnings("unchecked")
	private void loadImageView(byte[] arg2) {
		if((fileNameProperty!=null && StringUtils.isNotBlank(fileNameProperty.getValue()))){
			String[] arr = TFileExtension.ALL_IMAGES.getExtension();
			for (String ext : arr) {
				final String[] e = ext.split("\\.");
				if(e[1].equals("\\\\*"))
					continue;
				String fileExt = FilenameUtils.getExtension(fileNameProperty.getValue());
				if(StringUtils.containsIgnoreCase(fileExt, e[1])){
					Image image = new Image(new ByteArrayInputStream(arg2));
					
					double iW = image.getWidth();
					double iH = image.getHeight();
					String msg = "";
					if(maxImageWidth!=null && iW>maxImageWidth)
						msg += iEngine.getFormatedString("#{tedros.fxapi.message.image.min.width}", maxImageWidth.toString()) + "\n";
					if(minImageWidth!=null && iW<minImageWidth)
						msg += iEngine.getFormatedString("#{tedros.fxapi.message.image.max.width}", minImageWidth.toString()) + "\n";
					if(maxImageHeight!=null && iH>maxImageHeight)
						msg += iEngine.getFormatedString("#{tedros.fxapi.message.image.min.height}", maxImageHeight.toString()) + "\n";
					if(minImageHeight!=null && iH>minImageHeight)
						msg += iEngine.getFormatedString("#{tedros.fxapi.message.image.max.height}", minImageHeight.toString()) + "\n";
					
					if(StringUtils.isNotBlank(msg)){
						showModal(msg);
						cleanField();
					}else{
						
						if(imageAction!=null){
							imageView.addEventHandler(this.imageAction.getEventType(), this.imageAction);
						}
						
						imageView.setImage(image);
						
					}
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public final TEventHandler getOpenAction() {
		return openAction;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final void setOpenAction(TEventHandler openAction) {
		this.openAction = openAction;
		this.openButton.addEventHandler(openAction.getEventType(), openAction);
	}

	@SuppressWarnings("rawtypes")
	public final TEventHandler getCleanAction() {
		return cleanAction;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final void setCleanAction(TEventHandler cleanAction) {
		this.cleanAction = cleanAction;
		this.cleanButton.addEventHandler(cleanAction.getEventType(), cleanAction);
	}

	
	@SuppressWarnings("rawtypes")
	public final TEventHandler getSelectAction() {
		return selectAction;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final void setSelectAction(TEventHandler selectAction) {
		this.selectAction = selectAction;
		this.selectButton.addEventHandler(selectAction.getEventType(), selectAction);
	}

	@SuppressWarnings("rawtypes")
	public final TEventHandler getImageAction() {
		return imageAction;
	}

	@SuppressWarnings("rawtypes")
	public final void setImageAction(TEventHandler imageClickAction) {
		this.imageAction = imageClickAction;
	}

	/**
	 * @return the initialDirectory
	 */
	public String getInitialDirectory() {
		return initialDirectory;
	}

	/**
	 * @param initialDirectory the initialDirectory to set
	 */
	public void setInitialDirectory(String initialDirectory) {
		this.initialDirectory = initialDirectory;
	}
}
