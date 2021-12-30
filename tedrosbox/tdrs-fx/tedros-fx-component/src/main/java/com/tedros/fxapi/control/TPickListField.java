package com.tedros.fxapi.control;

import java.io.IOException;
import java.util.Comparator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.model.ITModelView;
import com.tedros.core.module.TObjectRepository;
import com.tedros.fxapi.effect.TEffectUtil;

import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class TPickListField<E extends ITModelView<?>> extends StackPane implements ITTriggeredable {
		
		@FXML private Label tSourceLabel;
		@FXML private Label tSelectedLabel;
	    @FXML private ListView<E> tSelectedListView;
	    @FXML private ListView<E> tSourceListView;
	    @FXML private Button tAddAllButton;
	    @FXML private Button tAddButton;
	    @FXML private Button tRemoveAllButton;
	    @FXML private Button tRemoveButton;
	    
	    private ObservableList<E> tSourceList;
	    private ObservableList<E> tSelectedList;
	    private SimpleBooleanProperty mandatoryResultProperty;
	    private Effect notNullEffect;
	    private ListChangeListener<E> notNullListener;
	    private SimpleBooleanProperty requiredProperty;
	    private TObjectRepository repository = new TObjectRepository();
	    private Comparator<E> comparator = (x, y) -> {
			return x.getDisplayProperty().getValue().compareTo(y.getDisplayProperty().getValue());
		};
	   
		public TPickListField(String sourceLabel, String selectedLabel, 
				ObservableList<E> sourceList, ObservableList<E> selectedList, 
				double width, double height,
				boolean required, SelectionMode mode) {
	    	try{
		    	loadFXML();     
		        initializeListViews(sourceList, selectedList, mode);
		        configMouseDblClickEvent();
		        configButtons();
		        setRequired(required);
		        configLabels(sourceLabel, selectedLabel);
		        if(width>0) {
		        	this.tSelectedListView.setPrefWidth(width);
		        	this.tSourceListView.setPrefWidth(width);
		        }
		        if(height>0) {
		        	this.tSelectedListView.setPrefHeight(height);
		        	this.tSourceListView.setPrefHeight(height);
		        }
		        
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
		}

		private void configLabels(String sourceLabelText, String selectedLabelText) {
			
			ChangeListener<String> srcLblChl = (a, b, n) -> { tSourceLabel.setVisible(StringUtils.isNotBlank(n)); };
			this.repository.add("srcLblChl", srcLblChl);
			this.tSourceLabel.textProperty().addListener(new WeakChangeListener<>(srcLblChl));
			
			ChangeListener<String> slcLblChl = (a, b, n) -> { tSelectedLabel.setVisible(StringUtils.isNotBlank(n)); };
			this.repository.add("slcLblChl", slcLblChl);
			this.tSelectedLabel.textProperty().addListener(new WeakChangeListener<>(slcLblChl));
			
			TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
			
			this.tSourceLabel.setText(iEngine.getString(sourceLabelText));
			this.tSelectedLabel.setText(iEngine.getString(selectedLabelText));
			this.tSourceLabel.setId("t-form-control-label");
			this.tSelectedLabel.setId("t-form-control-label");
			this.tSourceLabel.getStyleClass().add("t-form-control-label");
			this.tSelectedLabel.getStyleClass().add("t-form-control-label");
		}
		
		private void buildNotNullEffect(){
			if(notNullEffect == null)
				notNullEffect = TEffectUtil.buildNotNullFieldFormEffect();
		}
		
		private void buildNotNullListener(){
			if(notNullListener == null)
				notNullListener = change -> {
						if(tSelectedList.size()==0)
							applyEffect();
						else
							removeEffect();			
				};
		}
		
		private void buildMandatoryResultProperty(){
			if(mandatoryResultProperty == null)
				mandatoryResultProperty = new SimpleBooleanProperty();
		}
		
		private void removeEffect() {
			if(mandatoryResultProperty!=null)
				mandatoryResultProperty.set(true);
			tSelectedListView.setEffect(null);
		}

		private void applyEffect() {
			if(mandatoryResultProperty!=null)
				mandatoryResultProperty.set(false);
			tSelectedListView.setEffect(notNullEffect);
		}
		
		private void configButtons() {
			EventHandler<ActionEvent> addAllEv = e -> { tAddAllAction(); };
			this.repository.add("addAllEv", addAllEv);
			tAddAllButton.setOnAction(new WeakEventHandler<>(addAllEv));
			
			EventHandler<ActionEvent> addEv = e -> { tAddAction(); };
			this.repository.add("addEv", addEv);
			tAddButton.setOnAction(new WeakEventHandler<>(addEv));
			
			EventHandler<ActionEvent> remAllEv = e -> { tRemoveAllAction(); };
			this.repository.add("remAllEv", remAllEv);
			tRemoveAllButton.setOnAction(new WeakEventHandler<>(remAllEv));
			
			EventHandler<ActionEvent> remEv = e -> { tRemoveAction(); };
			this.repository.add("remEv", remEv);
			tRemoveButton.setOnAction(new WeakEventHandler<>(remEv));
		}

		private void configMouseDblClickEvent() {
			EventHandler<MouseEvent> slcMsEv = e -> {
				if(e.getClickCount()==2)
					tRemoveAction(); 
				};
			this.repository.add("slcMsEv", slcMsEv);
			tSelectedListView.setOnMouseClicked(new WeakEventHandler<>(slcMsEv));
			
			EventHandler<MouseEvent> srcMsEv = e -> {
				if(e.getClickCount()==2)
					tAddAction(); 
				};
			this.repository.add("srcMsEv", srcMsEv);
			tSourceListView.setOnMouseClicked(new WeakEventHandler<>(srcMsEv));
		}
		
		private void initializeListViews(ObservableList<E> sourceList, ObservableList<E> selectedList, SelectionMode mode) {
			if(selectedList==null)
				this.tSelectedList = FXCollections.observableArrayList();
			else
				this.tSelectedList = selectedList;
			if(sourceList!=null){
				final ObservableList<E> resultList = FXCollections.observableArrayList();
				resultList.addAll(CollectionUtils.subtract(sourceList, this.tSelectedList));
				this.tSourceList = resultList;
			}else 
				this.tSourceList = FXCollections.observableArrayList();
			
			tSourceListView.setEditable(true);
			tSourceListView.getSelectionModel().setSelectionMode(mode);
			tSelectedListView.setEditable(true);
			tSelectedListView.getSelectionModel().setSelectionMode(mode);
			
			tSourceListView.setItems(this.tSourceList);
			tSelectedListView.setItems(this.tSelectedList);
			
			tSourceListView.getItems().sort(comparator);
			tSelectedListView.getItems().sort(comparator);
		}

		private void loadFXML() throws IOException {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource( "tPickListField.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
		}
		
		public void setRequired(boolean required){
	    	
			if(this.requiredProperty == null) {
				this.requiredProperty = new SimpleBooleanProperty();
				ChangeListener<Boolean> rqdChl = (a, b, n) ->  {
						if(n){
				    		buildNotNullEffect();
				    		buildNotNullListener();
				    		buildMandatoryResultProperty();
				    		tSelectedList.addListener(notNullListener);
							if(tSelectedList.size()==0)
								applyEffect();
							else
								removeEffect();
				    	}else{
				    		mandatoryResultProperty = null;
				    		removeEffect();
				    		if(notNullListener!=null)
				    			tSelectedList.removeListener(notNullListener);
				    	}
					};
				this.repository.add("rqdChl", rqdChl);
				this.requiredProperty.addListener(new WeakChangeListener<>(rqdChl));
			}
			this.requiredProperty.set(required);
	    }

		public ObservableList<E> gettSourceList() {
			if(tSourceList == null)
				tSourceList = FXCollections.emptyObservableList();
			return tSourceList;
		}

		public void settSourceList(ObservableList<E> sourceList) {
			
			if(sourceList==null)
				throw new IllegalArgumentException("The argument sourceList canot be null");
			
			final ObservableList<E> resultList = FXCollections.observableArrayList();
			if(this.tSelectedList!=null){
				resultList.addAll(CollectionUtils.removeAll(sourceList, this.tSelectedList));
			}
			
			if(this.tSourceList==null){
				this.tSourceList = resultList;
				tSourceListView.setItems(this.tSourceList);
			}else{
				this.tSourceList.clear();
				this.tSourceList.addAll(resultList);
			}
			
			tSourceListView.getItems().sort(comparator);
		}

		public ObservableList<E> gettSelectedList() {
			return tSelectedList;
		}
		
		public void tClearSelectedList() {
			if(this.tSelectedList!=null && !this.tSelectedList.isEmpty()){
				this.tSourceList.addAll(this.tSelectedList);
				this.tSelectedList.clear();
				tSourceListView.getItems().sort(comparator);
			}
		}

		public void settSelectedList(ObservableList<E> selectedList) {
			
			if(selectedList==null)
				throw new IllegalArgumentException("The argument selectedList canot be null");
			
			this.tClearSelectedList();
			
			if(this.tSourceList!=null){
				this.tSourceList.retainAll(CollectionUtils.removeAll(this.tSourceList, selectedList));
			}
			
			if(this.tSelectedList==null){
				this.tSelectedList = selectedList;
				this.tSelectedListView.setItems(this.tSelectedList);
			}else{
				this.tSelectedList.clear();
				this.tSelectedList.addAll(selectedList);
			}
			
			tSelectedListView.getItems().sort(comparator);
		}

		public void tRemoveAction() {
			ObservableList<E> model = tSelectedListView.getSelectionModel().getSelectedItems();
			if(!model.isEmpty()){
				tSourceListView.getItems().addAll(model);
				tSelectedListView.getItems().removeAll(model);
				tSelectedListView.getSelectionModel().clearSelection();
				tSourceListView.getItems().sort(comparator);
			}
		}

		public void tAddAction() {
			ObservableList<E> model = tSourceListView.getSelectionModel().getSelectedItems();
			if(!model.isEmpty()){
				tSelectedListView.getItems().addAll(model);
				tSourceListView.getItems().removeAll(model);
				tSourceListView.getSelectionModel().clearSelection();
				tSelectedListView.getItems().sort(comparator);
			}
		}
		
		/**
		 * 
		 */
		public void tAddAllAction() {
			tSelectedListView.getItems().addAll(
					CollectionUtils.subtract(tSourceListView.getItems(), tSelectedListView.getItems())
				);
			tSourceListView.getItems().clear();
			tSelectedListView.getItems().sort(comparator);
		}

		/**
		 * 
		 */
		public void tRemoveAllAction() {
			tSourceListView.getItems().addAll(
					CollectionUtils.subtract(tSelectedListView.getItems(), tSourceListView.getItems())
				);
			tSelectedListView.getItems().clear();
			tSourceListView.getItems().sort(comparator);
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

		@SuppressWarnings("unchecked")
		@Override
		public Observable tValueProperty() {
			return this.tSelectedList;
		}

		/**
		 * @return the tSourceLabel
		 */
		public Label gettSourceLabel() {
			return tSourceLabel;
		}

		/**
		 * @param tSourceLabel the tSourceLabel to set
		 */
		public void settSourceLabel(Label tSourceLabel) {
			this.tSourceLabel = tSourceLabel;
		}

		/**
		 * @return the tSelectedLabel
		 */
		public Label gettSelectedLabel() {
			return tSelectedLabel;
		}

		/**
		 * @param tSelectedLabel the tSelectedLabel to set
		 */
		public void settSelectedLabel(Label tSelectedLabel) {
			this.tSelectedLabel = tSelectedLabel;
		}

		/**
		 * @return the tSelectedListView
		 */
		public ListView<E> gettSelectedListView() {
			return tSelectedListView;
		}

		/**
		 * @param tSelectedListView the tSelectedListView to set
		 */
		public void settSelectedListView(ListView<E> tSelectedListView) {
			this.tSelectedListView = tSelectedListView;
		}

		/**
		 * @return the tSourceListView
		 */
		public ListView<E> gettSourceListView() {
			return tSourceListView;
		}

		/**
		 * @param tSourceListView the tSourceListView to set
		 */
		public void settSourceListView(ListView<E> tSourceListView) {
			this.tSourceListView = tSourceListView;
		}

		/**
		 * @return the tAddAllButton
		 */
		public Button gettAddAllButton() {
			return tAddAllButton;
		}

		/**
		 * @param tAddAllButton the tAddAllButton to set
		 */
		public void settAddAllButton(Button tAddAllButton) {
			this.tAddAllButton = tAddAllButton;
		}

		/**
		 * @return the tAddButton
		 */
		public Button gettAddButton() {
			return tAddButton;
		}

		/**
		 * @param tAddButton the tAddButton to set
		 */
		public void settAddButton(Button tAddButton) {
			this.tAddButton = tAddButton;
		}

		/**
		 * @return the tRemoveAllButton
		 */
		public Button gettRemoveAllButton() {
			return tRemoveAllButton;
		}

		/**
		 * @param tRemoveAllButton the tRemoveAllButton to set
		 */
		public void settRemoveAllButton(Button tRemoveAllButton) {
			this.tRemoveAllButton = tRemoveAllButton;
		}

		/**
		 * @return the tRemoveButton
		 */
		public Button gettRemoveButton() {
			return tRemoveButton;
		}

		/**
		 * @param tRemoveButton the tRemoveButton to set
		 */
		public void settRemoveButton(Button tRemoveButton) {
			this.tRemoveButton = tRemoveButton;
		}


}
