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
import java.net.MalformedURLException;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.control.PopOver;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.control.action.TEventHandler;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.layout.TToolBar;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.util.TFileUtil;
import org.tedros.util.TUrlUtil;
import org.tedros.util.TedrosFolder;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
	private TButton cleanButton;
    private TTextField fileNameField;
    private Label filePathLabel;
    private StackPane imageSpace;
    private ImageView imageView;
    private TButton openButton;
    private TButton selectButton;
    private ToolBar toolbar;
    private FileChooser fileChooser;
    
	private SimpleObjectProperty<File> fileProperty;
	private SimpleObjectProperty<byte[]> byteArrayProperty;
	private SimpleStringProperty fileNameProperty;
	private SimpleLongProperty fileSizeProperty;
	private SimpleLongProperty bytesEntityIdProperty;
	private SimpleBooleanProperty fileChooserOpenedProperty;
	
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
		fileNameField = new TTextField();
		fileNameProperty = new SimpleStringProperty();
		fileSizeProperty = new SimpleLongProperty();
		byteArrayProperty = new SimpleObjectProperty<>();
		bytesEntityIdProperty = new SimpleLongProperty();
		fileChooserOpenedProperty = new SimpleBooleanProperty(false);
		
		vBox = new VBox();
		boxImageLabelSpace = new BorderPane();
		toolbar = new ToolBar();
		cleanButton = new TButton();
		selectButton = new TButton();
		openButton = new TButton();
		
		fileNameProperty.bindBidirectional(fileNameField.textProperty());
		
		//boxImageLabelSpace.setId("t-image-pane");
		
		toolbar.setId("t-file-toolbar");
		toolbar.setMaxWidth(Double.MAX_VALUE);
		toolbar.autosize();
		
		selectButton.setText(iEngine.getString(TFxKey.BUTTON_SELECT));
		openButton.setText(iEngine.getString(TFxKey.BUTTON_OPEN));
		cleanButton.setText(iEngine.getString(TFxKey.BUTTON_CLEAN));
		
		setAlignment(toolbar, Pos.CENTER_LEFT);
		
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
		
		byteArrayProperty.addListener((a,o,n)-> {
			if(n!=null) {
				this.setOpenAction();
				if(showImage)
					loadImageView(n);
			}
		});
		
		selectButton.setOnAction(e->{
			configureFileChooser(fileChooser,extensions);
			fileChooserOpenedProperty.setValue(true);
            final File file = fileChooser.showOpenDialog(appStage);
			fileChooserOpenedProperty.setValue(false);
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
		
		openEventHandler = event -> {
			File file = null;
			String folder = TedrosFolder.EXPORT_FOLDER.getFullPath();	
			try {if(filePathLabel!=null && StringUtils.isNotBlank(filePathLabel.getText())){
					if(!TFileUtil.open(new File(filePathLabel.getText()))) 
						showModal(openButton, 
								iEngine.getString(TFxKey.MESSAGE_OS_NOT_SUPPORT_OPERATION));
				}else{
					if(byteArrayProperty!=null && byteArrayProperty.getValue()!=null && fileNameField!=null && fileNameField.getText()!=null) {
						file = FileUtils.toFile(TUrlUtil.getURL(folder+"/"+fileNameField.getText()));
						
						if(file.exists()) {
							openButton.setDisable(true);
							String m1 = iEngine.getFormatedString(TFxKey.MESSAGE_FILE_ALREADY_EXISTS, file.getName());
							String m2 = iEngine.getString(TFxKey.MESSAGE_REPLACE_FILE);
							String m3 = iEngine.getString(TFxKey.MESSAGE_REPLACE_FILE_NO);
							this.showConfirmModal(openButton, m1+"\n"+m2+"\n"+m3, yes->{
								try {
									File f = FileUtils.toFile(TUrlUtil.getURL(folder+"/"+fileNameField.getText()));
									try {
										if(!yes){
											int x = 1;
											while(f.exists()){
												String[] arr = new String[]{
														FilenameUtils.getBaseName(f.getName()), 
														FilenameUtils.getExtension(f.getName())
													};
												if(arr[0].contains("(TCopy")) {
													String v = StringUtils.substringBetween(arr[0], "(TCopy ", ")");
													arr[0] = StringUtils.remove(arr[0], "(TCopy "+v.trim()+")").trim();
												}
												f = FileUtils.toFile(TUrlUtil.getURL(folder+"/"
												+arr[0]+" (TCopy "+(x++)+")."+arr[1]));
											}
										}
										writeAndOpen(f);
									} catch (Exception e1) {
										processOpenFileException(folder, f, e1);
									}
								} catch (MalformedURLException e1) {
									showModal(openButton, 
										iEngine.getFormatedString(TFxKey.MESSAGE_CANNOT_OPEN_FILE, e1.getMessage()));
								}
								openButton.setDisable(false);
							});
						}else {
							writeAndOpen(file);
						}
					}
				}
			} catch (Exception e) {
				processOpenFileException(folder, file, e);
			}
		};
		openButton.setOnAction(openEventHandler);
	}

	private void writeAndOpen(File f) throws IOException {
		FileUtils.writeByteArrayToFile(f, byteArrayProperty.getValue());
		if(!TFileUtil.open(f))
			showModal(openButton, 
				iEngine.getString(TFxKey.MESSAGE_OS_NOT_SUPPORT_OPERATION));
	}

	private void processOpenFileException(String folder, File f, Exception e1) {
		try {
			if(f!=null && f.exists())
				TFileUtil.open(new File(folder));
			else
				showModal(openButton, 
						iEngine.getFormatedString(TFxKey.MESSAGE_CANNOT_OPEN_FILE, e1.getMessage()));
		} catch (IOException e2) {
			e2.printStackTrace();
			showModal(openButton, 
					iEngine.getFormatedString(TFxKey.MESSAGE_CANNOT_OPEN_FILE, e1.getMessage()));
		}
	}
	
	private void setLoadByteAction() {
		openButton.setText(iEngine.getString(TFxKey.BUTTON_LOAD));
		openButton.setOnAction(downloadEventHandler);
	}
	
	private void setOpenAction() {
		openButton.setText(iEngine.getString(TFxKey.BUTTON_OPEN));
		openButton.setOnAction(openEventHandler);
	}
	
	private void showConfirmModal(Node node, String msg, Consumer<Boolean> confirmed){
		
		TToolBar bar = new TToolBar();
		TButton yesBtn = new TButton(iEngine.getString(TUsualKey.YES));
		TButton noBtn = new TButton(iEngine.getString(TUsualKey.NO));
		bar.getItems().addAll(yesBtn, noBtn);
		
		Label label = new Label(msg);
		label.setId("t-label");
		label.setMaxWidth(400);
		label.setWrapText(true);
		
		VBox box = new VBox(15);
		box.getChildren().addAll(label, bar);
		box.setPadding(new Insets(15));
		box.setAlignment(Pos.CENTER);
		PopOver p = new PopOver(box);
		p.setAutoHide(false);
		yesBtn.setOnAction(e->{
			confirmed.accept(true);
			p.hide();
		});
		noBtn.setOnAction(e->{
			confirmed.accept(false);
			p.hide();
		});
		p.show(node);
	}
	
	private void showModal(Node node, String msg){
		Label label = new Label(msg);
		label.setId("t-label");
		label.setMaxWidth(400);
		label.setWrapText(true);
		
		PopOver p = new PopOver(label);
		p.show(node);
	}
	
	private void configureFileChooser(final FileChooser fileChooser, String[] extensions){
		fileChooser.setTitle(TLanguage.getInstance(null).getString(TFxKey.SELECT_FILE));
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
    			showModal(selectButton, 
    					iEngine.getFormatedString(TFxKey.MESSAGE_FILE_MAX_SIZE, 
    							maxFileSize.toString()));
    		else if(minFileSize!=null && minFileSize > FileUtils.sizeOf(file))
    			showModal(selectButton, 
    					iEngine.getFormatedString(TFxKey.MESSAGE_FILE_MIN_SIZE, 
    							minFileSize.toString()));
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

	public final TButton getSelectFileButton() {
		return selectButton;
	}

	public final void setSelectFileButton(TButton selectFileButton) {
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
	

	/**
	 * @return the fileChooserOpenedProperty
	 */
	public SimpleBooleanProperty fileChooserOpenedProperty() {
		return fileChooserOpenedProperty;
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
						msg += iEngine.getFormatedString(TFxKey.MESSAGE_IMAGE_MIN_WIDTH, maxImageWidth.toString()) 
						+ "\n";
					if(minImageWidth!=null && iW<minImageWidth)
						msg += iEngine.getFormatedString(TFxKey.MESSAGE_IMAGE_MAX_WIDTH, minImageWidth.toString()) 
						+ "\n";
					if(maxImageHeight!=null && iH>maxImageHeight)
						msg += iEngine.getFormatedString(TFxKey.MESSAGE_IMAGE_MIN_HEIGHT, maxImageHeight.toString()) 
						+ "\n";
					if(minImageHeight!=null && iH>minImageHeight)
						msg += iEngine.getFormatedString(TFxKey.MESSAGE_IMAGE_MAX_HEIGHT, minImageHeight.toString()) 
						+ "\n";
					
					if(StringUtils.isNotBlank(msg)){
						showModal(selectButton, msg);
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
