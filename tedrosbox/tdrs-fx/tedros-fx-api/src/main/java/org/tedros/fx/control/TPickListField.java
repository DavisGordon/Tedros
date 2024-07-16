package org.tedros.fx.control;

import java.io.IOException;
import java.util.Comparator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.effect.TEffectUtil;
import org.tedros.util.TLoggerUtil;

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

public class TPickListField<E> extends StackPane implements ITTriggeredable {
		
		@FXML private Label tSourceLabel;
		@FXML private Label tTargetLabel;
	    @FXML private ListView<E> tTargetListView;
	    @FXML private ListView<E> tSourceListView;
	    @FXML private Button tAddAllButton;
	    @FXML private Button tAddButton;
	    @FXML private Button tRemoveAllButton;
	    @FXML private Button tRemoveButton;
	    
	    private ObservableList<E> tSourceList;
	    private ObservableList<E> tTargetList;
	    private SimpleBooleanProperty mandatoryResultProperty;
	    private Effect notNullEffect;
	    private ListChangeListener<E> notNullListener;
	    private SimpleBooleanProperty requiredProperty;
	    private TRepository repository = new TRepository();
	    private Comparator<E> comparator = (x, y) -> x.toString().compareTo(y.toString());
	   
		public TPickListField(String sourceLabel, String targetLabel, 
				ObservableList<E> sourceList, ObservableList<E> targetList, 
				double width, double height,
				boolean required, SelectionMode mode) {
	    	try{
		    	loadFXML();     
		        initializeListViews(sourceList, targetList, mode);
		        configMouseDblClickEvent();
		        configButtons();
		        setRequired(required);
		        configLabels(sourceLabel, targetLabel);
		        if(width>0) {
		        	this.tTargetListView.setPrefWidth(width);
		        	this.tSourceListView.setPrefWidth(width);
		        }
		        if(height>0) {
		        	this.tTargetListView.setPrefHeight(height);
		        	this.tSourceListView.setPrefHeight(height);
		        }
		        
	    	}catch(Exception e){
	    		TLoggerUtil.error(getClass(), e.getMessage(), e);
	    	}
		}

		private void configLabels(String sourceLabelText, String targetLabelText) {
			
			ChangeListener<String> srcLblChl = (a, b, n) -> { tSourceLabel.setVisible(StringUtils.isNotBlank(n)); };
			this.repository.add("srcLblChl", srcLblChl);
			this.tSourceLabel.textProperty().addListener(new WeakChangeListener<>(srcLblChl));
			
			ChangeListener<String> slcLblChl = (a, b, n) -> { tTargetLabel.setVisible(StringUtils.isNotBlank(n)); };
			this.repository.add("slcLblChl", slcLblChl);
			this.tTargetLabel.textProperty().addListener(new WeakChangeListener<>(slcLblChl));
			
			TLanguage iEngine = TLanguage.getInstance(null);
			
			this.tSourceLabel.setText(iEngine.getString(sourceLabelText));
			this.tTargetLabel.setText(iEngine.getString(targetLabelText));
			this.tSourceLabel.setId("t-form-control-label");
			this.tTargetLabel.setId("t-form-control-label");
			this.tSourceLabel.getStyleClass().add("t-form-control-label");
			this.tTargetLabel.getStyleClass().add("t-form-control-label");
		}
		
		private void buildNotNullEffect(){
			if(notNullEffect == null)
				notNullEffect = TEffectUtil.buildNotNullFieldFormEffect();
		}
		
		private void buildNotNullListener(){
			if(notNullListener == null)
				notNullListener = change -> {
						if(tTargetList.size()==0)
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
			tTargetListView.setEffect(null);
		}

		private void applyEffect() {
			if(mandatoryResultProperty!=null)
				mandatoryResultProperty.set(false);
			tTargetListView.setEffect(notNullEffect);
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
			tTargetListView.setOnMouseClicked(new WeakEventHandler<>(slcMsEv));
			
			EventHandler<MouseEvent> srcMsEv = e -> {
				if(e.getClickCount()==2)
					tAddAction(); 
				};
			this.repository.add("srcMsEv", srcMsEv);
			tSourceListView.setOnMouseClicked(new WeakEventHandler<>(srcMsEv));
		}
		
		private void initializeListViews(ObservableList<E> sourceList, ObservableList<E> targetList, SelectionMode mode) {
			if(targetList==null)
				this.tTargetList = FXCollections.observableArrayList();
			else
				this.tTargetList = targetList;
			if(sourceList!=null){
				final ObservableList<E> resultList = FXCollections.observableArrayList();
				resultList.addAll(CollectionUtils.subtract(sourceList, this.tTargetList));
				this.tSourceList = resultList;
			}else 
				this.tSourceList = FXCollections.observableArrayList();
			
			tSourceListView.setEditable(true);
			tSourceListView.getSelectionModel().setSelectionMode(mode);
			tTargetListView.setEditable(true);
			tTargetListView.getSelectionModel().setSelectionMode(mode);
			
			tSourceListView.setItems(this.tSourceList);
			tTargetListView.setItems(this.tTargetList);
			
			tSourceListView.getItems().sort(comparator);
			tTargetListView.getItems().sort(comparator);
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
				    		tTargetList.addListener(notNullListener);
							if(tTargetList.size()==0)
								applyEffect();
							else
								removeEffect();
				    	}else{
				    		mandatoryResultProperty = null;
				    		removeEffect();
				    		if(notNullListener!=null)
				    			tTargetList.removeListener(notNullListener);
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
			if(this.tTargetList!=null){
				resultList.addAll(CollectionUtils.removeAll(sourceList, this.tTargetList));
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

		public ObservableList<E> gettTargetList() {
			return tTargetList;
		}
		
		public void tClearTargetList() {
			if(this.tTargetList!=null && !this.tTargetList.isEmpty()){
				this.tSourceList.addAll(this.tTargetList);
				this.tTargetList.clear();
				tSourceListView.getItems().sort(comparator);
			}
		}

		public void settTargetList(ObservableList<E> targetList) {
			
			if(targetList==null)
				throw new IllegalArgumentException("The argument targetList canot be null");
			
			this.tClearTargetList();
			
			if(this.tSourceList!=null){
				this.tSourceList.retainAll(CollectionUtils.removeAll(this.tSourceList, targetList));
			}
			
			if(this.tTargetList==null){
				this.tTargetList = targetList;
				this.tTargetListView.setItems(this.tTargetList);
			}else{
				this.tTargetList.clear();
				this.tTargetList.addAll(targetList);
			}
			
			tTargetListView.getItems().sort(comparator);
		}

		public void tRemoveAction() {
			ObservableList<E> model = tTargetListView.getSelectionModel().getSelectedItems();
			if(!model.isEmpty()){
				tSourceListView.getItems().addAll(model);
				tTargetListView.getItems().removeAll(model);
				tTargetListView.getSelectionModel().clearSelection();
				tSourceListView.getItems().sort(comparator);
			}
		}

		public void tAddAction() {
			ObservableList<E> model = tSourceListView.getSelectionModel().getSelectedItems();
			if(!model.isEmpty()){
				tTargetListView.getItems().addAll(model);
				tSourceListView.getItems().removeAll(model);
				tSourceListView.getSelectionModel().clearSelection();
				tTargetListView.getItems().sort(comparator);
			}
		}
		
		/**
		 * 
		 */
		public void tAddAllAction() {
			tTargetListView.getItems().addAll(
					CollectionUtils.subtract(tSourceListView.getItems(), tTargetListView.getItems())
				);
			tSourceListView.getItems().clear();
			tTargetListView.getItems().sort(comparator);
		}

		/**
		 * 
		 */
		public void tRemoveAllAction() {
			tSourceListView.getItems().addAll(
					CollectionUtils.subtract(tTargetListView.getItems(), tSourceListView.getItems())
				);
			tTargetListView.getItems().clear();
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
			return this.tTargetList;
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
		 * @return the tTargetLabel
		 */
		public Label gettTargetLabel() {
			return tTargetLabel;
		}

		/**
		 * @param tTargetLabel the tTargetLabel to set
		 */
		public void settTargetLabel(Label tTargetLabel) {
			this.tTargetLabel = tTargetLabel;
		}

		/**
		 * @return the tTargetListView
		 */
		public ListView<E> gettTargetListView() {
			return tTargetListView;
		}

		/**
		 * @param tTargetListView the tTargetListView to set
		 */
		public void settTargetListView(ListView<E> tTargetListView) {
			this.tTargetListView = tTargetListView;
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
		
		public void settSourceListViewStyle(String style) {
			this.tSourceListView.setStyle(style);
		}
		
		public void settTargetListViewStyle(String style) {
			this.tTargetListView.setStyle(style);
		}
		
		public void settSourceListViewId(String id) {
			this.tSourceListView.setId(id);
		}
		
		public void settTargetListViewId(String id) {
			this.tTargetListView.setId(id);
		}
		
		public void settButtonsStyle(String style) {
			this.tAddAllButton.setStyle(style);
			this.tAddButton.setStyle(style);
			this.tRemoveAllButton.setStyle(style);
			this.tRemoveButton.setStyle(style);
		}


}
