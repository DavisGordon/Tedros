package com.tedros.fxapi.presenter.behavior;

import com.tedros.core.ITModule;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.module.TListenerRepository;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITView;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TFormBuilder;
import com.tedros.fxapi.form.TReaderFormBuilder;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Region;

@SuppressWarnings("rawtypes")
public abstract class TBehavior<M extends TModelView, P extends ITPresenter> implements ITBehavior<M, P> {
	
	private P presenter;
	private ITModelForm<M> form;
	private SimpleObjectProperty<TModelView> modelViewProperty;
	private ObservableList<M> models;
	private TViewMode tMode;
	private TListenerRepository listenerRepository;
	private SimpleBooleanProperty invalidateProperty;
	
	
	protected TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
	
	public TBehavior() {
		modelViewProperty = new SimpleObjectProperty<>();
		invalidateProperty = new SimpleBooleanProperty(false);
		listenerRepository = new TListenerRepository();
		
		ChangeListener<Boolean> invCL = (a0, a1, a2) -> {
			if(a2) {
				removeAllListenerFromModelView();
				removeAllListenerFromModelViewList();
				listenerRepository.clear();
			}
		};
		listenerRepository.addListener("invalidateModelAndRepo", invCL);
		invalidateProperty.addListener(new WeakChangeListener<>(invCL));
		
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
	public TListenerRepository getListenerRepository() {
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
	
	public ITModelForm<M> buildForm(TViewMode mode) {
		setViewMode(mode);
		return buildForm();
	}
	
	@SuppressWarnings("unchecked")
	public ITModelForm<M> buildForm() {
		
		if(tMode==null)
			tMode = TViewMode.READER;
		
		return (ITModelForm<M>) (tMode.equals(TViewMode.READER) 
				? TReaderFormBuilder.create(getModelView()).build() 
						: TFormBuilder.create(getModelView()).presenter(getPresenter()).build());
	}
	
	public void clearForm() {
		getView().gettFormSpace().getChildren().clear();
		form = null;
	}
	
	@SuppressWarnings("unchecked")
	public void setForm(ITModelForm form) {
    	clearForm();
    	this.form = form;
    	
    	if(this.form.gettPresenter()==null)
    		this.form.settPresenter(this.presenter);
    	
    	((Region)form).layout();
    	ScrollPane scroll = new ScrollPane();
    	scroll.setId("t-form-scroll");
    	scroll.setContent((Region)form);
    	scroll.setFitToWidth(true);
    	scroll.maxHeight(Double.MAX_VALUE);
    	scroll.maxWidth(Double.MAX_VALUE);
    	scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scroll.setStyle("-fx-background-color: transparent;");
    	
    	getView().gettFormSpace().getChildren().add(scroll);
    	
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
		final TForm tForm = this.form.gettModelView().getClass().getAnnotation(TForm.class);
		return (tForm!=null) ? tForm.name() : "@TForm(name='SET A NAME')";
	}

	public ITModelForm<M> getForm() {
		return form;
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
	

	
}
