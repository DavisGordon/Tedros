package org.tedros.fx.presenter.dynamic;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.ITDynaPresenter;
import org.tedros.api.presenter.behavior.ITBehavior;
import org.tedros.api.presenter.decorator.ITDecorator;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.api.presenter.view.TViewState;
import org.tedros.core.ITModule;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.parser.TStackPaneParser;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.process.TEntityProcess;
import org.tedros.fx.annotation.process.TModelProcess;
import org.tedros.fx.annotation.process.TReportProcess;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.exception.TErrorType;
import org.tedros.fx.presenter.TPresenter;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.server.model.ITModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

/**
 * Responsible to hold and control the objects to build and invalidate the view.
 * 
 *  @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public class TDynaPresenter<M extends ITModelView> extends TPresenter<TDynaView> 
implements ITDynaPresenter<M> {
	
	private ITDecorator<TDynaPresenter<M>> 		decorator;
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
	
	private org.tedros.fx.annotation.presenter.TPresenter tPresenter;
	
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
			for (Annotation ann : modelAnnotations) {
				if(ann instanceof org.tedros.fx.annotation.presenter.TPresenter) {
					tPresenter = (org.tedros.fx.annotation.presenter.TPresenter) ann;
					break;
				}else {
					Annotation presenter = TReflectionUtil.getTPresenter(ann);
					if(presenter!=null) {
						tPresenter = (org.tedros.fx.annotation.presenter.TPresenter) presenter;
						break;
					}
				}
			}
		}
		
		if(tPresenter==null)
			throwExceptionForNullTPresenter();
		
		try {
			decorator = tPresenter.decorator().type().newInstance();
			behavior = tPresenter.behavior().type().newInstance();
			if(modelClass==null && tPresenter.model()!=ITModel.class)
				modelClass = tPresenter.model();
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
		
		if(!super.getView().gettState().equals(TViewState.CREATED))
			return;
		
		super.getView().settState(TViewState.LOADING);
		
		decorator.setPresenter(this);
		decorator.decorate();
		
		ITComponentDescriptor descriptor = new TComponentDescriptor(null, null, null);
		TStackPaneParser parser = new TStackPaneParser();
		parser.setComponentDescriptor(descriptor);
		try {
			parser.parse(tPresenter.decorator(), (StackPane) getView(), "+node", "+region", "+pane");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.dynamic.ITDynaPresenter#getDecorator()
	 */
	@Override
	public ITDecorator<TDynaPresenter<M>> getDecorator() {
		return decorator;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.dynamic.ITDynaPresenter#getBehavior()
	 */
	@Override
	public ITBehavior<M, TDynaPresenter<M>> getBehavior() {
		return behavior;
	}
	
	public Class<M> getModelViewClass() {
		return modelViewClass;
	}	

	public org.tedros.fx.annotation.presenter.TPresenter getPresenterAnnotation() {
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

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.dynamic.ITDynaPresenter#getModelClass()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.dynamic.ITDynaPresenter#canInvalidate()
	 */
	@Override
	public String canInvalidate() {
		return behavior.canInvalidate();
	}
	
	@Override
	public boolean isLoadable(Class<? extends ITModelView> modelViewClass) {
		return this.modelViewClass == modelViewClass;
	}

	@Override
	public <T extends ITModelView> void lookupAndShow(Class<T> modelViewClass) {
		if(modelViewClass == null)
			throw new IllegalArgumentException("The modelViewClass argument cannot be null"); 
		else if(modelViewClass != this.modelViewClass)
			throw new IllegalArgumentException("The argument "+modelViewClass.getSimpleName()
			+" is not acceptable, expected "+this.modelViewClass.getSimpleName()); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ITModelView> void lookupAndShow(T modelView) {
		if(modelView == null)
			throw new IllegalArgumentException("The modelView argument cannot be null"); 
		
		if(getView().gettState().equals(TViewState.READY)) {
			this.behavior.setViewMode(TViewMode.EDIT);
			this.behavior.loadModelView((M) modelView);
		}else {
			this.modelView = (M) modelView;
			ChangeListener<TViewState> bchl = new ChangeListener<TViewState>(){
				@Override
				public void changed(ObservableValue<? extends TViewState> a, TViewState o, TViewState n) {
					if(n!=null && n.equals(TViewState.READY) 
							&& (getBehavior().getModelView()==null || 
									getBehavior().getModelView()!= modelView)) {
						getBehavior().setViewMode(TViewMode.EDIT);
						getBehavior().loadModelView( (M) modelView);
					}
					if(n!=null && n.equals(TViewState.READY))
						getView().tStateProperty().removeListener(this);
				}
			};
			getView().tStateProperty().addListener(bchl);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ITModelView> void lookupAndShow(ObservableList<T> modelsView) {
		if(modelsView == null || modelsView.isEmpty())
			throw new IllegalArgumentException("The modelsView argument cannot be null or empty"); 
		
		if(getView().gettState().equals(TViewState.READY)) {
			this.behavior.loadModelViewList((ObservableList<M>) modelsView);
		}else {
			this.modelViews = (ObservableList<M>) modelsView;
			ChangeListener<TViewState> bchl = new ChangeListener<TViewState>(){
				@Override
				public void changed(ObservableValue<? extends TViewState> a, TViewState o, TViewState n) {
					if(n!=null && n.equals(TViewState.READY) ) {
						getBehavior().loadModelViewList((ObservableList<M>) modelsView);
						getView().tStateProperty().removeListener(this);
					}
				}
			};
			getView().tStateProperty().addListener(bchl);
		}
	}
}
