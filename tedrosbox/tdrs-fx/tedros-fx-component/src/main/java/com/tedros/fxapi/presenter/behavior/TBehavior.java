package com.tedros.fxapi.presenter.behavior;

import com.tedros.core.ITModule;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.module.TObjectRepository;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITView;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TBuildFormStatus;
import com.tedros.fxapi.form.TFormBuilder;
import com.tedros.fxapi.form.TProgressIndicatorForm;
import com.tedros.fxapi.form.TReaderFormBuilder;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Region;

@SuppressWarnings("rawtypes")
public abstract class TBehavior<M extends TModelView, P extends ITPresenter> implements ITBehavior<M, P> {
	
	private P presenter;
	private SimpleObjectProperty<TModelView> modelViewProperty;
	private ObservableList<M> models;
	private TViewMode tMode;
	private TObjectRepository listenerRepository;
	private SimpleBooleanProperty invalidateProperty;
	private SimpleObjectProperty<TBuildFormStatus> buildFormStatusProperty;
	private SimpleObjectProperty<ITModelForm<M>> formProperty;
	
	protected TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
	
	@SuppressWarnings("unchecked")
	public TBehavior() {
		modelViewProperty = new SimpleObjectProperty<>();
		formProperty = new SimpleObjectProperty<>();
		invalidateProperty = new SimpleBooleanProperty(false);
		listenerRepository = new TObjectRepository();
		buildFormStatusProperty = new SimpleObjectProperty();
		
		//form added listener
		ChangeListener<ITModelForm<M>> formCL = (a0, a1, form) -> {
			
			if(form!=null) {
				
				this.buildFormStatusProperty.setValue(TBuildFormStatus.LOADING);
				ChangeListener<Boolean> loadedListener = new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean n) {
						if(n) {
							form.tLoadedProperty().removeListener(this);
							buildFormStatusProperty.setValue(TBuildFormStatus.FINISHED);
						}
					}
				};
				form.tLoadedProperty().addListener(loadedListener);
				
				if(form.gettPresenter()==null)
		    		form.settPresenter(this.presenter);
				
		    	
				ScrollPane scroll = new ScrollPane();
		    	scroll.setId("t-form-scroll");
		    	scroll.setContent((Node)form);
		    	scroll.setFitToWidth(true);
		    	//scroll.setFitToHeight(true);
		    	
		    	scroll.maxHeight(Double.MAX_VALUE);
		    	scroll.maxWidth(Double.MAX_VALUE);
		    	scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		    	scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		    	scroll.setStyle("-fx-background-color: transparent;");
		    	((Region)form).layout();
		    	getView().gettFormSpace().getChildren().clear();
		    	getView().gettFormSpace().getChildren().add(scroll);
			}
		};
		listenerRepository.add("formPropCL", formCL);
		formProperty.addListener(new WeakChangeListener<>(formCL));
		
		// build form status listener
		ChangeListener<TBuildFormStatus> bfchl = (ob, o, n) -> {
			if(n!=null && n.equals(TBuildFormStatus.STARTING)) {
				buildFormTask();
			}
		};
		listenerRepository.add("buildingFormCHL", bfchl);
		buildFormStatusProperty.addListener(new WeakChangeListener(bfchl));
		
		// Invalidation listener
		ChangeListener<Boolean> invCL = (a0, a1, a2) -> {
			if(a2) {
				removeAllListenerFromModelView();
				removeAllListenerFromModelViewList();
				listenerRepository.clear();
			}
		};
		listenerRepository.add("invalidateModelAndRepo", invCL);
		invalidateProperty.addListener(new WeakChangeListener<>(invCL));
	}

	private void buildFormTask() {
		this.buildFormStatusProperty.setValue(TBuildFormStatus.BUILDING);
		Thread buildFormThread = new Thread(new Runnable() {
		      @Override
		      public void run() {
				Platform.runLater(new Runnable() {
		            @Override
		            public void run() {
		            	try {
		            		@SuppressWarnings("unchecked")
							ITModelForm<M> form = (ITModelForm<M>) (tMode.equals(TViewMode.READER) 
		    						? TReaderFormBuilder.create(getModelView()).build() 
		    								: TFormBuilder.create(getModelView()).presenter(getPresenter()).build());
		    				setForm(form);
						} catch (Exception e) {
							e.printStackTrace();
						}
		            }
		          });
		      	}
			});
		buildFormThread.setDaemon(true);
		buildFormThread.start();
	}

	public void buildForm(TViewMode mode) {
		setViewMode(mode);
		buildForm();
	}
	
	public void buildForm() {
		
		if(tMode==null)
			tMode = TViewMode.READER;
		
		this.buildFormStatusProperty.setValue(TBuildFormStatus.STARTING);
	}
	
	public void clearForm() {
		this.buildFormStatusProperty.setValue(null);
		getView().gettFormSpace().getChildren().clear();
		this.formProperty.setValue(null);
	}
	
	@SuppressWarnings("unchecked")
	public void setForm(ITModelForm form) {
		TProgressIndicatorForm pif = (form instanceof TProgressIndicatorForm) 
				? (TProgressIndicatorForm) form 
						: new TProgressIndicatorForm(form);
    	this.formProperty.setValue(pif);
    }
	


	/**
	 * @return the invalidateProperty
	 */
	public Boolean getInvalidate() {
		return invalidateProperty.getValue();
	}


	/**
	 * @return the invalidateProperty
	 */
	public SimpleBooleanProperty invalidateProperty() {
		return invalidateProperty;
	}


	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.behavior.ITBehavior#invalidate()
	 */
	@Override
	public boolean invalidate() {
		this.invalidateProperty.setValue(true);
		return true;
	}

	

	/**
	 * @param invalidateProperty the invalidateProperty to set
	 */
	public void setInvalidate(boolean v) {
		this.invalidateProperty.setValue(v);
	}


	/**
	 * @return the listenerRepository
	 */
	public TObjectRepository getListenerRepository() {
		return listenerRepository;
	}


	@Override
	public P getPresenter() {
		return presenter;
	}


	@Override
	public void setPresenter(P presenter) {
		this.presenter = presenter;
		iEngine.setCurrentUUID(getApplicationUUID());
	}
	
	public TViewMode getViewMode() {
		return tMode;
	}
	
	public void setViewMode(TViewMode mode){
		this.tMode = mode;
	}
	
	@SuppressWarnings("unchecked")
	public <V extends ITView> V getView(){
		return  this.presenter==null ? null : (V) this.presenter.getView();
	}
	
	@Override
	public  void setModelView(TModelView modelView) {
		this.modelViewProperty.setValue(modelView);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends TModelView> T getModelView() {
		return (T) modelViewProperty.getValue();
	}

	public SimpleObjectProperty<TModelView> modelViewProperty() {
		return modelViewProperty;
	}
	
	@Override
	public void setModelViewList(ObservableList<M> models) {
		this.models = models;
	}

	@Override
	public ObservableList<M> getModels() {
		return this.models;
	}


	public String getFormName() {
		final TForm tForm = getForm().gettModelView().getClass().getAnnotation(TForm.class);
		return (tForm!=null) ? tForm.name() : "@TForm(name='SET A NAME')";
	}

	public ITModelForm<M> getForm() {
		return formProperty.get();
	}
	
	@Override
	public void removeAllListenerFromModelView() {
		if(this.modelViewProperty.getValue()!=null)
			this.modelViewProperty.getValue().removeAllListener();
	}
	
	@Override
	public void removeAllListenerFromModelViewList() {
		if(models!=null && models.size()>0)
			for (M m : this.models) 
				m.removeAllListener();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T removeListenerFromModelView(String listenerId) {
		if(this.modelViewProperty.getValue()!=null)
			return (T) this.modelViewProperty.getValue().removeListener(listenerId);
		return null;
	}
	
	public String getApplicationUUID() {
		String uuid = null;
		ITModule module = getPresenter().getModule();
		if(module!=null){
			uuid = TedrosAppManager.getInstance().getModuleContext(module).getModuleDescriptor().getApplicationUUID();
		}
		return uuid;
	}

	/**
	 * @return the formProperty
	 */
	public ReadOnlyObjectProperty<ITModelForm<M>> formProperty() {
		return formProperty;
	}

	/**
	 * @return the buildFormStatusProperty
	 */
	public ReadOnlyObjectProperty<TBuildFormStatus> buildFormStatusProperty() {
		return buildFormStatusProperty;
	}
	

	
}
