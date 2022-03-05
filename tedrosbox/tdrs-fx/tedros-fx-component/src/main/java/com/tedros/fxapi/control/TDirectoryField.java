/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 21/03/2014
 */
package com.tedros.fxapi.control;

import java.io.File;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.fxapi.control.action.TEventHandler;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TDirectoryField extends StackPane {
	
	private final Stage appStage;
	
	private VBox vBox;
	private BorderPane boxLabelSpace;
	private ToolBar toolbar;
	
    private Label filePathLabel;
    private TTextField directoryNameField;
	private Button selectButton;
	private Button cleanButton;
    
    private DirectoryChooser directoryChooser;
    
	private SimpleObjectProperty<File> fileProperty;
	private SimpleObjectProperty<File> initialDirectoryProperty;
	
	private boolean showFilePath;
	
	private TEventHandler cleanAction;
	private TEventHandler selectAction;
	
	private ChangeListener<File> fileListener;
	private ChangeListener<File> initialDirectoryListener;
	
	private TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
	
	public TDirectoryField(final Stage stage) {	
		appStage = stage;
		buildProperty();
		buildListeners();
		initialize();
	}

	private void initialize() {
		
		boxLabelSpace.setId("t-image-pane");
		
		toolbar.setId("t-file-toolbar");
		toolbar.setMaxWidth(Double.MAX_VALUE);
		toolbar.autosize();
		
		filePathLabel.setId("t-label");
		selectButton.setText(iEngine.getString("#{tedros.fxapi.button.select.folder}"));
		cleanButton.setText("Limpar");
		
		setAlignment(toolbar, Pos.CENTER_LEFT);
		
		selectButton.setId("t-button");
		cleanButton.setId("t-last-button");
		
		directoryNameField.setStyle("-fx-text-fill: #000000; -fx-font-weight: bold; ");
		directoryNameField.setPrefWidth(250);
		directoryNameField.setMaxHeight(Double.MAX_VALUE);
		directoryNameField.setDisable(true);
		
		toolbar.getItems().addAll(directoryNameField, selectButton, cleanButton);
		
		settShowFilePath(true);
		settUserHomeAsInitialDirectory();
	}

	private void buildProperty() {
		
		fileProperty = new SimpleObjectProperty<>();
		directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle(iEngine.getString("#{tedros.fxapi.button.select}"));
		selectButton = new Button();
		directoryNameField = new TTextField();
		initialDirectoryProperty = new SimpleObjectProperty<>();
		
		vBox = new VBox();
		boxLabelSpace = new BorderPane();
		filePathLabel = new Label();

		toolbar = new ToolBar();
		cleanButton = new Button();
		selectButton = new Button();
		
		vBox.setAlignment(Pos.CENTER_LEFT);
		
		//HBox hBox = new HBox();
		//HBox.setHgrow(toolbar, Priority.ALWAYS);
		//hBox.getChildren().add(toolbar);
		//vBox.getChildren().addAll(boxLabelSpace, hBox);
		vBox.getChildren().addAll(boxLabelSpace, toolbar);
		getChildren().add(vBox);
	}
	
	private void buildListeners() {
		
		initialDirectoryListener = new ChangeListener<File>() {
			@Override
			public void changed(ObservableValue<? extends File> arg0, File arg1, File arg2) {
				directoryChooser.setInitialDirectory(arg2);
			}
		};
		
		initialDirectoryProperty.addListener(initialDirectoryListener);
		
		this.fileListener = new ChangeListener<File>() {
			@Override
			public void changed(ObservableValue<? extends File> arg0, File arg1, File file) {
				if (file != null){
					if(directoryNameField.getText()!=null && !directoryNameField.getText().equals(file.getAbsolutePath()))
						directoryNameField.textProperty().set(file.getAbsolutePath());
					filePathLabel.setText(file.getAbsolutePath());
				}else{
					directoryNameField.textProperty().set("");
					filePathLabel.setText("");
				}	
			}
		};
		
		fileProperty.addListener(fileListener);
		
		selectButton.setOnAction(e->{
            final File file = directoryChooser.showDialog(appStage);
            if (file != null) 
            	settFile(file);
        });
		
		cleanButton.setOnAction(e->{
			tCleanField();
		});
			
	}
	
	private void showHideFilePathSpace(){
		if(!showFilePath){
			vBox.getChildren().remove(boxLabelSpace);
		}else if(!vBox.getChildren().contains(boxLabelSpace)){
			vBox.getChildren().add(0, boxLabelSpace);
		}
	}

	private void removeFilePath() {
		boxLabelSpace.setBottom(null);
	}
	
	public void settUserHomeAsInitialDirectory(){
		settInitialDirectory(new File(System.getProperty("user.home")));
	}
	
	public void settInitialDirectory(File directory){
		this.initialDirectoryProperty.set(directory);
	}

	public final File gettFile() {
		return (fileProperty!=null) ? fileProperty.getValue() : null;
	}
	
	public final void settFile(File file) {
		this.fileProperty.setValue(file);
	}
	
	public final ObjectProperty<File> tFileProperty() {
		return fileProperty;
	}
	
	public SimpleBooleanProperty tRequiredProperty() {
		return directoryNameField.requiredProperty();
	}
	
	public SimpleBooleanProperty tRequirementAccomplishedProperty() {
		return directoryNameField.requirementAccomplishedProperty();
	}
	
	public boolean tIsRequirementAccomplished(){
		return directoryNameField.isRequirementAccomplished(); 
	}
	
	public void tCleanField() {
		if(fileProperty!=null)
			fileProperty.setValue(null);
	}
	
	public final boolean tIsShowFilePath() {
		return showFilePath;
	}

	public final void settShowFilePath(boolean enable) {
		showFilePath = enable;
		if(showFilePath){
			boxLabelSpace.setBottom(filePathLabel);
		}else{
			removeFilePath();
		}
		
		showHideFilePathSpace();
	}

	public final Button gettCleanButton() {
		return cleanButton;
	}

	public final TTextField gettFileNameField() {
		return directoryNameField;
	}
	
	public final Button gettSelectButton() {
		return selectButton;
	}
		
	public void settRequired(boolean required){
		if(directoryNameField!=null)
			directoryNameField.setRequired(required);
	}
	
	public final void settCleanAction(TEventHandler cleanAction) {
		this.cleanAction = cleanAction;
	}
	
	public final void settSelectAction(TEventHandler selectAction) {
		this.selectAction = selectAction;
	}

	public ObjectProperty<File>  tInitialDirectoryProperty() {
		return initialDirectoryProperty;
	}
}
