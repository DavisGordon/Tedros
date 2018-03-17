/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 20/01/2014
 */
package com.tedros.fxapi.presenter;


import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ToolBarBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.util.Callback;

import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.view.ITView;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.modal.TConfirmMessageBox;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.presenter.view.TTabDetailView;

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
		final ToolBar menuBar = ToolBarBuilder.create()
				.id("t-view-toolbar")
				.items(Arrays.asList(getView().getNewBtn(), getView().getRemoveBtn(), spacer))
				.build();
		
		
		HBox.setHgrow(menuBar, Priority.ALWAYS);
		// menu box
		final HBox menuBox = HBoxBuilder.create()
				.id("t-header-box")
				.children(Arrays.asList(menuBar))
				.maxWidth(Double.MAX_VALUE)
				.alignment(Pos.CENTER_LEFT)
				.build();
		
		//Layout
		final BorderPane layout = BorderPaneBuilder.create()
				.top(menuBox)
				.build();
		
		
		
		final ListView<M> listView  = new ListView<M>();
		getView().setListView(listView);
		
		
		Label listName = LabelBuilder.create().id("t-title-label").text(this.listName).build(); 
		Region spacer2 = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		HBox hBox = HBoxBuilder.create()
				.id("t-header-box")
				.fillHeight(true)
				.children(Arrays.asList(listName, spacer2))
				.build();
		
		
		VBox.setVgrow(listView, Priority.ALWAYS);
		final VBox vBox = VBoxBuilder.create()
				.fillWidth(true)
				.id("t-pane")
				.children(Arrays.asList(hBox, listView))
				.build();
		
		
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
		        			
		        			if(item.getDisplayProperty()==null)
		        				throw new RuntimeException(new TException("The method getDisplayProperty in "+modelViewClass.getSimpleName()+" must return a not null value!"));
		        			
		                	textProperty().bind(item.getDisplayProperty());
		                	
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
		getView().setNewBtn(ButtonBuilder.create().text("Novo").id("t-button").build());
		getView().getNewBtn().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try{
					final M model = modelViewClass.getConstructor(entityClass).newInstance(entityClass.newInstance());
					final ListView<M> list = view.getListView();
					list.getItems().add(model);
					list.selectionModelProperty().get().select(list.getItems().size()-1);
					setModel(model);
					gerarForm();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Adiciona a��o no bot�o excluir
	 * */
	private void initializeRemoveBtn(final TTabDetailView<M> view) {
		getView().setRemoveBtn(ButtonBuilder.create().text("Excluir").id("t-last-button").build());
		view.getRemoveBtn().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(getModel()==null)
					return;
				
				final TConfirmMessageBox confirm = new TConfirmMessageBox("Deseja realmente excluir o registro: "+getModel().getDisplayProperty()+" ?");
				confirm.gettConfirmProperty().addListener(new ChangeListener<Number>() {
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
			
			ScrollPane scroll = ScrollPaneBuilder.create()
					.content(form)
					.fitToWidth(true)
					.maxHeight(Double.MAX_VALUE)
					.maxWidth(Double.MAX_VALUE)
					.vbarPolicy(ScrollBarPolicy.AS_NEEDED)
					.hbarPolicy(ScrollBarPolicy.AS_NEEDED)
					.id("t-form-scroll")
					.build();
			scroll.layout();
			
			getView().getLayout().setCenter(scroll);
			
			
		} catch (Exception e) {
			e.printStackTrace();
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
