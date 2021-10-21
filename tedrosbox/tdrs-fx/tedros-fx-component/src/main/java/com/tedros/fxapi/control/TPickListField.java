package com.tedros.fxapi.control;

import java.io.IOException;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.effect.TEffectUtil;

public class TPickListField<E extends ITModelView<?>> extends StackPane implements ITTriggeredable {
		
		@FXML private Label sourceLabel;
		@FXML private Label selectedLabel;
	    @FXML private ListView<E> selectedListView;
	    @FXML private ListView<E> sourceListView;
	    @FXML private VBox buttonsVbox;
	    @FXML private Button addAll;
	    @FXML private Button addOne;
	    @FXML private Button remAll;
	    @FXML private Button remOne;
	    
	    private ObservableList<E> sourceList;
	    private ObservableList<E> selectedList;
	    private SimpleBooleanProperty mandatoryResultProperty;
	    private Effect notNullEffect;
	    @SuppressWarnings("rawtypes")
		private ListChangeListener notNullListener;
	    private SimpleBooleanProperty requiredProperty;
	   
		public TPickListField(String sourceLabel, String selectedLabel, 
				ObservableList<E> sourceObservableList, ObservableList<E> selectedObservableList, 
				double width, double height,
				boolean required) {
	    	try{
		    	loadFXML();     
		        initializeListViews(sourceObservableList, selectedObservableList);
		        configMouseDblClickEvent();
		        configButtons();
		        setRequired(required);
		        configLabels(sourceLabel, selectedLabel);
		        if(width>0) {
		        	
		        	this.selectedListView.setPrefWidth(width);
		        	this.sourceListView.setPrefWidth(width);
		        }
		        if(height>0) {
		        	this.selectedListView.setPrefHeight(height);
		        	this.sourceListView.setPrefHeight(height);
		        }
		        
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
		}

		private void configLabels(String sourceLabelText, String selectedLabelText) {
			
			this.sourceLabel.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String new_value) {
					sourceLabel.setVisible( StringUtils.isNotBlank(new_value));
				}
			});
			
			this.selectedLabel.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String new_value) {
					selectedLabel.setVisible(StringUtils.isNotBlank(new_value));
				}
			});
			
			TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
			
			this.sourceLabel.setText(iEngine.getString(sourceLabelText));
			this.selectedLabel.setText(iEngine.getString(selectedLabelText));
			this.sourceLabel.setId("t-form-control-label");
			this.selectedLabel.setId("t-form-control-label");
			this.sourceLabel.getStyleClass().add("t-form-control-label");
			this.selectedLabel.getStyleClass().add("t-form-control-label");
		}
		
		private void buildNotNullEffect(){
			if(notNullEffect == null)
				notNullEffect = TEffectUtil.buildNotNullFieldFormEffect();
		}
		
		@SuppressWarnings("rawtypes")
		private void buildNotNullListener(){
			if(notNullListener == null)
				notNullListener = new ListChangeListener() {
					@Override
					public void onChanged(javafx.collections.ListChangeListener.Change arg0) {
						if(getSelectedList().size()==0)
							applyEffect();
						else
							removeEffect();
					}					
				};
		}
		
		private void buildMandatoryResultProperty(){
			if(mandatoryResultProperty == null)
				mandatoryResultProperty = new SimpleBooleanProperty();
		}
		
		private void removeEffect() {
			if(mandatoryResultProperty!=null)
				mandatoryResultProperty.set(true);
			getDestinoListView().setEffect(null);
		}

		private void applyEffect() {
			if(mandatoryResultProperty!=null)
				mandatoryResultProperty.set(false);
			getDestinoListView().setEffect(notNullEffect);
		}
		

		private void configButtons() {
			addAll.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					
					System.out.println("----------------");
					selectedListView.getItems().addAll(
							CollectionUtils.subtract(sourceListView.getItems(), selectedListView.getItems())
						);
					sourceListView.getItems().clear();
				}
			});
			
			addOne.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					addOne();
				}
			});
			
			remAll.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("----------------");
					sourceListView.getItems().addAll(
							CollectionUtils.subtract(selectedListView.getItems(), sourceListView.getItems())
						);
					selectedListView.getItems().clear();
				}
			});
			
			remOne.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					removeOne();
				}
			});
		}

		private void configMouseDblClickEvent() {
			selectedListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if(event.getClickCount()==2)
						removeOne();
				}
			});
			
			sourceListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if(event.getClickCount()==2)
						addOne();
				}
			});
		}
		
		private void initializeListViews(ObservableList<E> sourceObservableList, ObservableList<E> selectedObservableList) {
			
			this.selectedList = selectedObservableList;
			if(sourceObservableList!=null){
				final ObservableList<E> sourceListTmp = FXCollections.observableArrayList();
				sourceListTmp.addAll(CollectionUtils.subtract(sourceObservableList, this.selectedList));
				this.sourceList = sourceListTmp;
			}
			
			sourceListView.setEditable(true);
			sourceListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			selectedListView.setEditable(true);
			selectedListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			
			if(this.sourceList!=null)
				sourceListView.setItems(this.sourceList);
			
			selectedListView.setItems(this.selectedList);
		}

		private void loadFXML() throws IOException {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource( "tPickListField.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
		}
	    	    
		@SuppressWarnings("unchecked")
		public void setRequired(boolean required){
	    	
			if(this.requiredProperty == null)
				this.requiredProperty = new SimpleBooleanProperty();
			
			this.requiredProperty.addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean new_value) {
					if(new_value){
			    		buildNotNullEffect();
			    		buildNotNullListener();
			    		buildMandatoryResultProperty();
			    		getSelectedList().addListener(notNullListener);
						if(getSelectedList().size()==0)
							applyEffect();
						else
							removeEffect();
			    	}else{
			    		mandatoryResultProperty = null;
			    		removeEffect();
			    		if(notNullListener!=null)
			    			getSelectedList().removeListener(notNullListener);
			    	}
				}
			});
			
			this.requiredProperty.set(required);
	    }

		

		public ObservableList<E> getSourceList() {
			if(sourceList == null)
				sourceList = FXCollections.emptyObservableList();
			return sourceList;
		}

		public void setSourceList(ObservableList<E> sourceList) {
			
			if(sourceList==null)
				throw new IllegalArgumentException("The sourceList canot be null");
			
			final ObservableList<E> sourceListTmp = FXCollections.observableArrayList();
			if(this.selectedList!=null){
				sourceListTmp.addAll(CollectionUtils.removeAll(sourceList, this.selectedList));
			}
			
			
			if(this.sourceList==null){
				this.sourceList = sourceListTmp;
				sourceListView.setItems(this.sourceList);
			}else{
				this.sourceList.clear();
				this.sourceList.addAll(sourceListTmp);
			}
		}

		public ObservableList<E> getSelectedList() {
			return selectedList;
		}

		public void setSelectedList(ObservableList<E> selectedList) {
			this.selectedList = selectedList;
		}

		private void removeOne() {
			System.out.println("----------------");
			E model = selectedListView.getSelectionModel().getSelectedItem();
			if(model!=null){
				sourceListView.getItems().add(model);
				selectedListView.getItems().remove(selectedListView.getSelectionModel().getSelectedIndex());
				selectedListView.getSelectionModel().clearSelection();
			}
		}

		private void addOne() {
			System.out.println("----------------");
			E model = sourceListView.getSelectionModel().getSelectedItem();
			if(model!=null){
				selectedListView.getItems().add(model);
				sourceListView.getItems().remove(sourceListView.getSelectionModel().getSelectedIndex());
				sourceListView.getSelectionModel().clearSelection();
			}
		}
		
		public Button getAddAll() {
			return addAll;
		}

		public void setAddAll(Button addAll) {
			this.addAll = addAll;
		}

		public Button getAddOne() {
			return addOne;
		}

		public void setAddOne(Button addOne) {
			this.addOne = addOne;
		}

		public ListView<E> getDestinoListView() {
			return selectedListView;
		}

		public void setDestinoListView(ListView<E> destinoListView) {
			this.selectedListView = destinoListView;
		}

		public ListView<E> getOrigemListView() {
			return sourceListView;
		}

		public void setOrigemListView(ListView<E> origemListView) {
			this.sourceListView = origemListView;
		}

		public Button getRemAll() {
			return remAll;
		}

		public void setRemAll(Button remAll) {
			this.remAll = remAll;
		}

		public Button getRemOne() {
			return remOne;
		}

		public void setRemOne(Button remOne) {
			this.remOne = remOne;
		}

		public Label getSourceLabel() {
			return sourceLabel;
		}

		public void setSourceLabel(Label sourceLabel) {
			this.sourceLabel = sourceLabel;
		}

		public Label getSelectedLabel() {
			return selectedLabel;
		}

		public void setSelectedLabel(Label selectedLabel) {
			this.selectedLabel = selectedLabel;
		}

		public SimpleBooleanProperty mandatoryResultProperty() {
			return mandatoryResultProperty;
		}
		
		public final SimpleBooleanProperty requiredProperty() {
			return requiredProperty;
		}
		
		public boolean isMandatoryAccepted(){
			return (mandatoryResultProperty!=null) ? mandatoryResultProperty.get() : true;
		}

		@Override
		public Observable tValueProperty() {
			return this.selectedList;
		}


}
