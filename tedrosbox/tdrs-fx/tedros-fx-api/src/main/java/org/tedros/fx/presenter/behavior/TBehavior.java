package org.tedros.fx.presenter.behavior;

import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.ITPresenter;
import org.tedros.api.presenter.behavior.ITBehavior;
import org.tedros.api.presenter.view.ITView;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.api.presenter.view.TViewState;
import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.model.ITModelView;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.form.TBuildFormStatus;
import org.tedros.fx.form.TFormBuilder;
import org.tedros.fx.form.TProgressIndicatorForm;
import org.tedros.fx.form.TReaderFormBuilder;

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

/**
 * The root behavior class
 * 
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public abstract class TBehavior<M extends ITModelView, P extends ITPresenter> implements ITBehavior<M, P> {
	
	private P presenter;
	private SimpleObjectProperty<M> modelViewProperty;
	private ObservableList<M> models;
	private TViewMode tMode;
	private TRepository listenerRepository;
	private SimpleBooleanProperty invalidateProperty;
	private SimpleObjectProperty<TBuildFormStatus> buildFormStatusProperty;
	private SimpleObjectProperty<ITModelForm<M>> formProperty;
	private ChangeListener<TBuildFormStatus> loadChl ;
	
	protected TLanguage iEngine = TLanguage.getInstance(null);
	
	@SuppressWarnings("unchecked")
	public TBehavior() {
		modelViewProperty = new SimpleObjectProperty<>();
		formProperty = new SimpleObjectProperty<>();
		invalidateProperty = new SimpleBooleanProperty(false);
		listenerRepository = new TRepository();
		buildFormStatusProperty = new SimpleObjectProperty();
		
		//form added listener
		ChangeListener<ITModelForm<M>> formCL = (a0, oldForm, form) -> {
			
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
				
		    	TForm ann = form.gettModelView().getClass().getAnnotation(TForm.class);
		    	if(ann!=null && !ann.scroll()) {
		    		((Region)form).layout();
			    	getView().gettFormSpace().getChildren().clear();
			    	getView().gettFormSpace().getChildren().add((Node)form);
		    	}else {
		    		((Region)form).layout();
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
			}
			
			if(oldForm!=null)
				oldForm.tDispose();
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
				formProperty.setValue(null);
			}
		};
		listenerRepository.add("invalidateModelAndRepo", invCL);
		invalidateProperty.addListener(new WeakChangeListener<>(invCL));
		

		loadChl = new ChangeListener<TBuildFormStatus>() {
			@Override
			public void changed(ObservableValue<? extends TBuildFormStatus> a, TBuildFormStatus o,
					TBuildFormStatus n) {
				if(n!=null && n.equals(TBuildFormStatus.FINISHED)) {
					setViewStateAsReady();
				}
			}
		};
		buildFormStatusProperty.addListener(loadChl);
		
	}

	private void buildFormTask() {
		this.buildFormStatusProperty.setValue(TBuildFormStatus.BUILDING);
		
		Platform.runLater(()-> {
            	try {
            		@SuppressWarnings("unchecked")
					ITModelForm<M> form = (ITModelForm<M>) (tMode.equals(TViewMode.READER) 
    						? TReaderFormBuilder.create(getModelView()).build() 
    								: TFormBuilder.create(getModelView()).presenter(getPresenter()).build());
    				setForm(form);
				} catch (Exception e) {
					e.printStackTrace();
				}
            
          });
	}
	
	/**
	 * Set the view state as Ready
	 */
	public void setViewStateAsReady() {
		buildFormStatusProperty().removeListener(loadChl);
		getPresenter().getView().settState(TViewState.READY);
	}

	/**
	 * Build the form as the mode param
	 * @param mode
	 */
	public void buildForm(TViewMode mode) {
		setViewMode(mode);
		buildForm();
	}
	
	/**
	 * Build the form. 
	 * Build the form as TViewMode.READER 
	 * if getViewMode() is null
	 */
	public void buildForm() {
		
		if(tMode==null)
			tMode = TViewMode.READER;
		
		this.buildFormStatusProperty.setValue(TBuildFormStatus.STARTING);
	}
	
	/***
	 * Clear the form
	 */
	public void clearForm() {
		this.buildFormStatusProperty.setValue(null);
		getView().gettFormSpace().getChildren().clear();
		this.formProperty.setValue(null);
	}
	
	/**
	 * Set the form
	 * @param form
	 */
	@SuppressWarnings("unchecked")
	public void setForm(ITModelForm form) {
		TProgressIndicatorForm pif = (form instanceof TProgressIndicatorForm) 
				? (TProgressIndicatorForm) form 
						: new TProgressIndicatorForm(form);
    	this.formProperty.setValue(pif);
    }
	
	/**
	 * @return the invalidate value
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
	 * @see org.tedros.fx.presenter.behavior.ITBehavior#invalidate()
	 */
	@Override
	public boolean invalidate() {
		this.invalidateProperty.setValue(true);
		return true;
	}

	/**
	 * Set the invalidate value
	 * @param val the invalidate to set
	 */
	public void setInvalidate(boolean val) {
		this.invalidateProperty.setValue(val);
	}


	/**
	 * @return the listenerRepository
	 */
	public TRepository getListenerRepository() {
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
	
	@Override
	public TViewMode getViewMode() {
		return tMode;
	}
	
	@Override
	public void setViewMode(TViewMode mode){
		this.tMode = mode;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <V extends ITView> V getView(){
		return  this.presenter==null ? null : (V) this.presenter.getView();
	}
	
	@Override
	public void loadModelView(M modelView) {
		setModelView(modelView);
	}
	
	@Override
	public  void setModelView(M modelView) {
		this.modelViewProperty.setValue((M)modelView);
	}

	@Override
	public M getModelView() {
		return modelViewProperty.getValue();
	}

	/**
	 * Get the modelViewProperty
	 * @return SimpleObjectProperty<M>
	 */
	public SimpleObjectProperty<M> modelViewProperty() {
		return modelViewProperty;
	}
	
	@Override
	public void setModelViewList(ObservableList<M> models) {
		this.models = models;
	}
	
	@Override
	public void loadModelViewList(ObservableList<M> models) {
		if(this.models == null)
			this.setModelViewList(models);
		else {
			this.models.clear();
			this.models.addAll(models);
		}
	}

	@Override
	public ObservableList<M> getModels() {
		return this.models;
	}

	@Override
	public String getFormName() {
		final TForm tForm = getForm().gettModelView().getClass().getAnnotation(TForm.class);
		return (tForm!=null) ? tForm.header() : "@TForm(name='SET A NAME')";
	}

	@Override
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
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T removeListenerFromModelView(String listenerId) {
		if(this.modelViewProperty.getValue()!=null)
			return (T) this.modelViewProperty.getValue().removeListener(listenerId);
		return null;
	}
	
	@Override
	public String getApplicationUUID() {
		String uuid = null;
		ITModule module = getPresenter().getModule();
		if(module!=null){
			uuid = TedrosAppManager.getInstance().getModuleContext(module).getModuleDescriptor().getApplicationUUID();
		}
		return uuid;
	}

	/**
	 * Get the formProperty
	 * @return the ReadOnlyObjectProperty
	 */
	public ReadOnlyObjectProperty<ITModelForm<M>> formProperty() {
		return formProperty;
	}

	/**
	 * Get the buildFormStatusProperty
	 * @return the ReadOnlyObjectProperty
	 */
	public ReadOnlyObjectProperty<TBuildFormStatus> buildFormStatusProperty() {
		return buildFormStatusProperty;
	}
	

	
}
