package com.tedros.fxapi.presenter.behavior;

import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.layout.Region;

import com.tedros.core.ITModule;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.core.presenter.view.ITView;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TFormBuilder;
import com.tedros.fxapi.form.TReaderFormBuilder;
import com.tedros.fxapi.presenter.model.TModelView;

@SuppressWarnings("rawtypes")
public abstract class TBehavior<M extends TModelView, P extends ITPresenter> implements ITBehavior<M, P> {
	
	private P presenter;
	private ITModelForm<M> form;
	private TModelView model;
	private ObservableList<M> models;
	private TViewMode tMode;
	
	protected TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
	
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
    	ScrollPane scroll = ScrollPaneBuilder.create()
    			.id("t-form-scroll")
				.content((Region)form)
				.fitToWidth(true)
				.maxHeight(Double.MAX_VALUE)
				.maxWidth(Double.MAX_VALUE)
				.vbarPolicy(ScrollBarPolicy.AS_NEEDED)
				.hbarPolicy(ScrollBarPolicy.AS_NEEDED)
				.style("-fx-background-color: transparent;")
				.build();
    	
    	getView().gettFormSpace().getChildren().add(scroll);
    	
    }
	
	@SuppressWarnings("unchecked")
	public <V extends ITView> V getView(){
		return (V) this.presenter.getView();
	}
	
	@Override
	public  void setModelView(TModelView modelView) {
		/*if(this.model!=null)
			this.model.removeAllListener();*/
		this.model = modelView;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends TModelView> T getModelView() {
		return (T) model;
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
		if(this.model!=null)
			this.model.removeAllListener();
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
		if(this.model!=null)
			return (T) this.model.removeListener(listenerId);
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
