/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 20/01/2014
 */
package org.tedros.fx.presenter;


import java.util.Arrays;

import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.TLanguage;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.TFxKey;
import org.tedros.fx.exception.TException;
import org.tedros.fx.form.TDefaultForm;
import org.tedros.fx.modal.TConfirmMessageBox;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.view.TTabDetailView;
import org.tedros.server.entity.ITEntity;
import org.tedros.util.TLoggerUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TTabDetailPresenter<M extends TEntityModelView> {

	
	final private ITView masterView;
	final private TTabDetailView<M> view;
	final private ObservableList<M> models;
	private Class<? extends ITModelForm<?>> formClass;
	private Class<M> modelViewClass;
	private Class<? extends ITEntity> entityClass;
	private M model;
	private final String listName;
	
	
	
	public TTabDetailPresenter(final ITView masterView, final String listName, final TTabDetailView<M> view, final ObservableList<M> models, Class<M> modelClass, Class<? extends ITEntity> entityClass, 
			Class<? extends ITModelForm<?>> formClass) {
		
		this.listName = listName;
		this.masterView = masterView;
		this.view = view;
		this.models = models;
		this.modelViewClass = modelClass;
		this.entityClass = entityClass;
		this.formClass = formClass;
		
		buildView();
		loadListView();
		initializeListViewListener();
		addEmptyForm();
	}
	
	private void buildView() {
		
		initializeNewBtn();
		initializeRemoveBtn(view);
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		//menu bar
		final ToolBar menuBar = new ToolBar();
		menuBar.setId("t-view-toolbar");
		menuBar.getItems().addAll(Arrays.asList(getView().getNewBtn(), getView().getRemoveBtn(), spacer));
		
		HBox.setHgrow(menuBar, Priority.ALWAYS);
		// menu box
		final HBox menuBox = new HBox();
		menuBox.setId("t-header-box");
		menuBox.getChildren().addAll(Arrays.asList(menuBar));
		menuBox.setMaxWidth(Double.MAX_VALUE);
		menuBox.setAlignment(Pos.CENTER_LEFT);
		
		//Layout
		final BorderPane layout = new BorderPane();
		layout.setTop(menuBox);
		
		final ListView<M> listView  = new ListView<M>();
		getView().setListView(listView);
		
		
		Label listName = new Label();
		listName.setId("t-title-label");
		listName.setText(this.listName); 
		Region spacer2 = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		HBox hBox = new HBox();
		hBox.setId("t-header-box");
		hBox.setFillHeight(true);
		hBox.getChildren().addAll(Arrays.asList(listName, spacer2));
		
		VBox.setVgrow(listView, Priority.ALWAYS);
		final VBox vBox = new VBox();
		vBox.setFillWidth(true);
		vBox.setId("t-pane");
		vBox.getChildren().addAll(Arrays.asList(hBox, listView));
		
		layout.setLeft(vBox);
		
		getView().setLayout(layout);
		getView().setContent(layout);
	}
	
	private void loadListView() {
		getView().getListView().setItems(this.models);
		getView().getListView().setEditable(true);
		getView().getListView().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		getView().getListView().setCellFactory(new Callback<ListView<M>, ListCell<M>>() {
		    public ListCell<M> call(ListView<M> param) {
		        final ListCell<M> cell = new ListCell<M>() {
		            
		        	@Override
		            public void updateItem(final M item, boolean empty) {
		            	
		        		super.updateItem(item, empty);
		            	
		        		if (item != null) {
		        			
		        			if(item.toStringProperty()==null)
		        				throw new RuntimeException(new TException("The method getDisplayProperty in "+modelViewClass.getSimpleName()+" must return a not null value!"));
		        			
		                	textProperty().bind(item.toStringProperty());
		                	
		                	if(item.getEntity().isNew())
		                		setStyle("-fx-text-fill: green;");
		                	
		                	item.lastHashCodeProperty().addListener(new ChangeListener<Number>() {
								@Override
								public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
									setStyle("-fx-text-fill: red;");
								}
							});
		                	
		                	item.loadedProperty().addListener(new ChangeListener<Number>() {
								@Override
								public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
									setStyle("-fx-text-fill: black;");
								}
							});
		                	
		                }
		                
		            }
		        	
		        };
		        return cell;
		    }
		});
		
	}
	
	private void initializeListViewListener() {
		getView().getListView().getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<M>() {
	                public void changed(final ObservableValue<? extends M> ov, final M old_val, final M new_val) {
	                	if(new_val==null)
	                		return;
	                	setModel(new_val);
	                	gerarForm();
	            }	
	        });
	}
	
	/**
	 * Adiciona a��o no bot�o Add Perfil
	 * */
	private void initializeNewBtn() {
		Button b = new Button();
		b.setText(TLanguage.getInstance().getString(TFxKey.BUTTON_NEW));
		b.setId("t-button");
		getView().setNewBtn(b);
		getView().getNewBtn().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try{
					final M model = modelViewClass.getConstructor(entityClass).newInstance(entityClass.getDeclaredConstructor().newInstance());
					final ListView<M> list = view.getListView();
					list.getItems().add(model);
					list.selectionModelProperty().get().select(list.getItems().size()-1);
					setModel(model);
					gerarForm();
				}catch(Exception e){
					TLoggerUtil.error(getClass(), e.getMessage(), e);
				}
			}
		});
	}
	
	/**
	 * Adiciona a��o no bot�o excluir
	 * */
	private void initializeRemoveBtn(final TTabDetailView<M> view) {
		Button b = new Button();
		b.setText(TLanguage.getInstance().getString(TFxKey.BUTTON_REMOVE));
		b.setId("t-last-button");
		getView().setRemoveBtn(b);
		view.getRemoveBtn().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(getModel()==null)
					return;
				
				final TConfirmMessageBox confirm = new TConfirmMessageBox("Deseja realmente excluir o registro: "+getModel().toStringProperty()+" ?");
				confirm.tConfirmProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
						if(arg2.equals(1)){
							executarProcessoRemover();
						}
						masterView.tHideModal();	
					}
				});
				
				masterView.tShowModal(confirm, false);
			}

			public void executarProcessoRemover() {
					remove(getView().getListView().getSelectionModel().getSelectedIndex());
			}
			
		});
	}
	
	private void remove(final int selected) {
		getView().getListView().getItems().remove(selected);
		getView().getListView().getSelectionModel().clearSelection();
		setModel(null);
		addEmptyForm();
	}
	
	protected M getModel() {
		return model;
	}
	
	protected void setModel(M model) {
		this.model = model;
	}
	
	@SuppressWarnings("unchecked")
	private void gerarForm() {
		
		final Class formClass = this.formClass == null ? TDefaultForm.class : this.formClass;
		try {
			
			Region form = (Region) formClass.getConstructor(ITModelView.class).newInstance(getModel());
			form.layout();
			
			ScrollPane scroll = new ScrollPane();
			scroll.setContent(form);
			scroll.setFitToWidth(true);
			scroll.maxHeight(Double.MAX_VALUE);
			scroll.maxWidth(Double.MAX_VALUE);
			scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
			scroll.setId("t-form-scroll");
			
			scroll.layout();
			
			getView().getLayout().setCenter(scroll);
			
			
		} catch (Exception e) {
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
		
		
	}
	
	private void addEmptyForm() {
		StackPane pane = new StackPane();
		pane.setId("t-form-pane");
		getView().getLayout().setCenter(pane);
	}

	public TTabDetailView<M> getView() {
		return view;
	}
	
}
