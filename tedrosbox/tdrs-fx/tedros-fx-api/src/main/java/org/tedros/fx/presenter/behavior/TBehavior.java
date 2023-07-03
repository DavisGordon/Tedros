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
public abstract class TBehavior<M extends ITModelView, P extends ITPresenter> 
implements ITBehavior<M, P>{
	
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
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#setViewStateAsReady()
	 */
	@Override
	public void setViewStateAsReady() {
		buildFormStatusProperty().removeListener(loadChl);
		getPresenter().getView().settState(TViewState.READY);
	}

	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#buildForm(org.tedros.api.presenter.view.TViewMode)
	 */
	@Override
	public void buildForm(TViewMode mode) {
		setViewMode(mode);
		buildForm();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#buildForm()
	 */
	@Override
	public void buildForm() {
		
		if(tMode==null)
			tMode = TViewMode.READER;
		
		this.buildFormStatusProperty.setValue(TBuildFormStatus.STARTING);
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#clearForm()
	 */
	@Override
	public void clearForm() {
		this.buildFormStatusProperty.setValue(null);
		getView().gettFormSpace().getChildren().clear();
		this.formProperty.setValue(null);
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#setForm(org.tedros.api.form.ITModelForm)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setForm(ITModelForm form) {
		TProgressIndicatorForm pif = (form instanceof TProgressIndicatorForm) 
				? (TProgressIndicatorForm) form 
						: new TProgressIndicatorForm(form);
    	this.formProperty.setValue(pif);
    }
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#getInvalidate()
	 */
	@Override
	public Boolean getInvalidate() {
		return invalidateProperty.getValue();
	}

	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#invalidateProperty()
	 */
	@Override
	public SimpleBooleanProperty invalidateProperty() {
		return invalidateProperty;
	}


	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.behavior.ITBehavior#invalidate()
	 */
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#invalidate()
	 */
	@Override
	public boolean invalidate() {
		this.invalidateProperty.setValue(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#setInvalidate(boolean)
	 */
	@Override
	public void setInvalidate(boolean val) {
		this.invalidateProperty.setValue(val);
	}


	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#getListenerRepository()
	 */
	@Override
	public TRepository getListenerRepository() {
		return listenerRepository;
	}


	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#getPresenter()
	 */
	@Override
	public P getPresenter() {
		return presenter;
	}


	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#setPresenter(P)
	 */
	@Override
	public void setPresenter(P presenter) {
		this.presenter = presenter;
		iEngine.setCurrentUUID(getApplicationUUID());
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#getViewMode()
	 */
	@Override
	public TViewMode getViewMode() {
		return tMode;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#setViewMode(org.tedros.api.presenter.view.TViewMode)
	 */
	@Override
	public void setViewMode(TViewMode mode){
		this.tMode = mode;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#getView()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <V extends ITView> V getView(){
		return  this.presenter==null ? null : (V) this.presenter.getView();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#loadModelView(M)
	 */
	@Override
	public void loadModelView(M modelView) {
		setModelView(modelView);
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#setModelView(M)
	 */
	@Override
	public  void setModelView(M modelView) {
		this.modelViewProperty.setValue((M)modelView);
	}

	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#getModelView()
	 */
	@Override
	public M getModelView() {
		return modelViewProperty.getValue();
	}

	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#modelViewProperty()
	 */
	@Override
	public SimpleObjectProperty<M> modelViewProperty() {
		return modelViewProperty;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#setModelViewList(javafx.collections.ObservableList)
	 */
	@Override
	public void setModelViewList(ObservableList<M> models) {
		this.models = models;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#loadModelViewList(javafx.collections.ObservableList)
	 */
	@Override
	public void loadModelViewList(ObservableList<M> models) {
		if(this.models == null)
			this.setModelViewList(models);
		else {
			this.models.clear();
			this.models.addAll(models);
		}
	}

	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#getModels()
	 */
	@Override
	public ObservableList<M> getModels() {
		return this.models;
	}

	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#getFormName()
	 */
	@Override
	public String getFormName() {
		final TForm tForm = getForm().gettModelView().getClass().getAnnotation(TForm.class);
		return (tForm!=null) ? tForm.header() : "@TForm(name='SET A NAME')";
	}

	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#getForm()
	 */
	@Override
	public ITModelForm<M> getForm() {
		return formProperty.get();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#removeAllListenerFromModelView()
	 */
	@Override
	public void removeAllListenerFromModelView() {
		if(this.modelViewProperty.getValue()!=null)
			this.modelViewProperty.getValue().removeAllListener();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#removeAllListenerFromModelViewList()
	 */
	@Override
	public void removeAllListenerFromModelViewList() {
		if(models!=null && models.size()>0)
			for (M m : this.models) 
				m.removeAllListener();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#removeListenerFromModelView(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T removeListenerFromModelView(String listenerId) {
		if(this.modelViewProperty.getValue()!=null)
			return (T) this.modelViewProperty.getValue().removeListener(listenerId);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#getApplicationUUID()
	 */
	@Override
	public String getApplicationUUID() {
		String uuid = null;
		ITModule module = getPresenter().getModule();
		if(module!=null){
			uuid = TedrosAppManager.getInstance().getModuleContext(module).getModuleDescriptor().getApplicationUUID();
		}
		return uuid;
	}

	/* (non-Javadoc)
	 * @see org.tedros.api.presenter.behavior.ITBehavior#formProperty()
	 */
	@Override
	public ReadOnlyObjectProperty<ITModelForm<M>> formProperty() {
		return formProperty;
	}

	public ReadOnlyObjectProperty<TBuildFormStatus> buildFormStatusProperty() {
		return buildFormStatusProperty;
	}
	

	
}
