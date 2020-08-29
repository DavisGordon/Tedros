package com.tedros.fxapi.presenter.dynamic;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import javafx.collections.ObservableList;

import com.tedros.core.ITModule;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.process.TModelProcess;
import com.tedros.fxapi.annotation.process.TReportProcess;
import com.tedros.fxapi.exception.TErrorType;
import com.tedros.fxapi.presenter.TPresenter;
import com.tedros.fxapi.presenter.behavior.ITBehavior;
import com.tedros.fxapi.presenter.decorator.ITDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.util.TReflectionUtil;

@SuppressWarnings("rawtypes")
public class TDynaPresenter<M extends TModelView>	extends TPresenter<TDynaView<M>> {
	
	private ITDecorator<TDynaPresenter<M>> decorator;
	private ITBehavior<M, TDynaPresenter<M>> 	behavior;
	
	private M modelView;
	private Class<M> modelViewClass;
	private ObservableList<M> modelViews;
	
	private Class<? extends ITModel> modelClass;
	
	private TReportProcess tReportProcess;
	private TModelProcess tModelProcess;
	private TEntityProcess tEntityProcess;
	private TEjbService tEjbService;
	private TForm tForm;
	//private TView tView;
	
	private com.tedros.fxapi.annotation.presenter.TPresenter tPresenter;
	
	@SuppressWarnings("unchecked")
	public TDynaPresenter(M modelView){
		this.modelView = modelView;
		this.modelViewClass = (Class<M>) modelView.getClass();
	}
	
	@SuppressWarnings("unchecked")
	public TDynaPresenter(ITModule module, M modelView){
		this.modelView = modelView;
		this.modelViewClass = (Class<M>) modelView.getClass();
		setModule(module);
	}
	
	public TDynaPresenter(Class<M> modelViewClass) {
		this.modelViewClass = modelViewClass;
	}
	
	public TDynaPresenter(ITModule module, Class<M> modelViewClass) {
		this.modelViewClass = modelViewClass;
		setModule(module);
	}
	
	
	public TDynaPresenter(Class<M> modelViewClass, ObservableList<M> modelViews){
		this.modelViews = modelViews;
		this.modelViewClass = modelViewClass;
	}
	
	public TDynaPresenter(Class<M> modelViewClass, Class<? extends ITModel> modelClass, ObservableList<M> modelViews){
		this.modelViews = modelViews;
		this.modelViewClass = modelViewClass;
		this.modelClass = modelClass;
	}
	
	@SuppressWarnings("unchecked")
	public TDynaPresenter(M modelView, ObservableList<M> modelViews){
		this.modelView = modelView;
		this.modelViewClass = (Class<M>) modelView.getClass();
		this.modelViews = modelViews;
	}
	
	public TDynaPresenter(ITModule module, Class<M> modelViewClass, ObservableList<M> modelViews){
		this.modelViews = modelViews;
		this.modelViewClass = modelViewClass;
		setModule(module);
	}
	
	public TDynaPresenter(ITModule module, Class<M> modelViewClass, Class<? extends ITModel> modelClass, ObservableList<M> modelViews){
		this.modelViews = modelViews;
		this.modelViewClass = modelViewClass;
		this.modelClass = modelClass;
		setModule(module);
	}
	
	@SuppressWarnings("unchecked")
	public TDynaPresenter(ITModule module, M modelView, ObservableList<M> modelViews){
		this.modelView = modelView;
		this.modelViewClass = (Class<M>) modelView.getClass();
		this.modelViews = modelViews;
		setModule(module);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize() {
		// get the model view annotation array 
		Annotation[] modelAnnotations = this.getModelViewClass().getAnnotations();
		if(modelAnnotations == null)
			throwExceptionForNullTPresenter();
		
		Object[] arr = TReflectionUtil.getViewBuilder(Arrays.asList(modelAnnotations));
		
		// get the presenter settings
		if(arr!=null){
			Annotation annotation = (Annotation) arr[0];
			tPresenter = TReflectionUtil.getTPresenter(annotation);
		}else{
			for (Annotation ann : modelAnnotations) 
				if(ann instanceof com.tedros.fxapi.annotation.presenter.TPresenter)
					tPresenter = (com.tedros.fxapi.annotation.presenter.TPresenter) ann;
		}
		
		if(tPresenter==null)
			throwExceptionForNullTPresenter();
		
		try {
			decorator = tPresenter.decorator().type().newInstance();
			behavior = tPresenter.behavior().type().newInstance();
			if(modelClass==null && tPresenter.modelClass()!=ITModel.class)
				modelClass = tPresenter.modelClass();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tEjbService = (TEjbService) this.modelViewClass.getAnnotation(TEjbService.class);
		tModelProcess = (TModelProcess) this.modelViewClass.getAnnotation(TModelProcess.class);
		tEntityProcess = (TEntityProcess) this.modelViewClass.getAnnotation(TEntityProcess.class);
		tReportProcess = (TReportProcess) this.modelViewClass.getAnnotation(TReportProcess.class);
		tForm = (TForm) this.modelViewClass.getAnnotation(TForm.class);
		
		//loadView();
	}
	
	@Override
	public void loadView() {
		
		decorator.setPresenter(this);
		decorator.decorate();
		
		behavior.setPresenter(this);
		if(this.modelView != null){
			behavior.removeAllListenerFromModelView();
			behavior.setModelView(modelView);
		}
		
		if(this.modelViews != null){
			behavior.removeAllListenerFromModelViewList();
			behavior.setModelViewList(modelViews);
		}
		
		behavior.load();
		
		super.loadView();
	}
	
	// private methods
	
	private void throwExceptionForNullTPresenter() {
		throw new RuntimeException("\n\nT_ERROR "
				+ "\nType: "+TErrorType.PRESENTER_INITIALIZATION
				+ "\nClass: " + getClass().getSimpleName()
				+ "\nMethod: initialize"
				+ "\n\n-The "+this.getModelViewClass() + " must be annotated with a valid view builder or with a @TPresenter annotation.\n\n");
	}
	
	// getters and setters

	public ITDecorator<TDynaPresenter<M>> getDecorator() {
		return decorator;
	}

	public ITBehavior<M, TDynaPresenter<M>> getBehavior() {
		return behavior;
	}
	
	public Class<M> getModelViewClass() {
		return modelViewClass;
	}	

	public com.tedros.fxapi.annotation.presenter.TPresenter getPresenterAnnotation() {
		return tPresenter;
	}
	
	public TModelProcess getModelProcessAnnotation() {
		return tModelProcess;
	}

	public TEntityProcess getEntityProcessAnnotation() {
		return tEntityProcess;
	}

	public TReportProcess getReportProcessAnnotation() {
		return tReportProcess;
	}
	
	public TEjbService getEjbServiceAnnotation() {
		return tEjbService;
	}

	public TForm getFormAnnotation() {
		return tForm;
	}
	
	public ObservableList<M> getModelViews() {
		return modelViews;
	}

	public M getModelView() {
		return modelView;
	}

	public void setModelView(M model) {
		this.modelView = model;
	}

	public Class<? extends ITModel> getModelClass() {
		return modelClass;
	}

	@Override
	public boolean invalidate() {
		if(behavior.invalidate()) {
			if(modelView!=null) {
				modelView.removeAllListener();
				modelView = null;
			}
			return true;
		}else
			return false;
	}

	@Override
	public String canInvalidate() {
		return behavior.canInvalidate();
	}
}
