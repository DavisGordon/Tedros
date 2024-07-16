/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package org.tedros.fx.form;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.ITPresenter;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.util.TLoggerUtil;

/**
 * A {@link ITModelForm} builder. 
 *
 * @author Davis Gordon
 */
public class TFormBuilder<M extends ITModelView<?>> {
	
	private ITModelForm<M> form;
	private M modelView;
	@SuppressWarnings("rawtypes")
	private ITPresenter presenter;
	
	@SuppressWarnings("rawtypes")
	private static TFormBuilder instance;
	
	private TFormBuilder(M modelView) {
		try{
			this.form = null;
			this.modelView = modelView;
		}catch(Exception e){
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
	}
	
	
	public static TFormBuilder<?> create(ITModelView<?> modelView){
		instance = new TFormBuilder<ITModelView<?>>(modelView);
		return instance;
	}
	
	@SuppressWarnings("rawtypes")
	public TFormBuilder<?> presenter(ITPresenter presenter){
		this.presenter = presenter;
		return this;
	}
			
	@SuppressWarnings({"unchecked", "rawtypes"})
	public ITModelForm<M> build() {
		
		Class formClass = TDefaultForm.class;
		TForm tForm = this.modelView.getClass().getAnnotation(TForm.class);
		if(tForm!=null)
			formClass = tForm.type();
		
		try {
			
			int constructorType = 0;
			Constructor[] constructors = formClass.getConstructors();
			for (Constructor constructor : constructors) {
				Class[] types = constructor.getParameterTypes();
				for (Class type : types) {
					if(type == ITModelView.class)
						constructorType = 1;
					if(type == this.modelView.getClass())
						constructorType = 2;
				}
			}
			
			if(this.presenter!=null){
				constructorType += 2;
			}
			
			switch (constructorType) {
				case 1 :
					this.form = (ITModelForm<M>) formClass.getConstructor(ITModelView.class).newInstance((ITModelView)this.modelView);
					applyStyles(tForm);
					break;
				case 2 :
					this.form = (ITModelForm<M>) formClass.getConstructor(this.modelView.getClass()).newInstance(this.modelView);
					applyStyles(tForm);
					break;
				case 3 :
					this.form = (ITModelForm<M>) formClass.getConstructor(ITPresenter.class, ITModelView.class).newInstance(this.presenter, (ITModelView)this.modelView);
					applyStyles(tForm);
					break;
				case 4 :
					this.form = (ITModelForm<M>) formClass.getConstructor(ITPresenter.class, this.modelView.getClass()).newInstance(this.presenter, this.modelView);
					applyStyles(tForm);
					break;
				default:
					throw new InstantiationException("ERROR: THE FORM "+formClass.getName()+" must have a constructor with TModelView or "+this.modelView.getClass().getCanonicalName()+" parameter!");
			}
			
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			TLoggerUtil.error(getClass(), e.getMessage(), e);
			return null;
		}
		
		return this.form;
		
	}


	/**
	 * @param tForm
	 */
	private void applyStyles(TForm tForm) {
		if(tForm==null)
			return;
		this.form.setId(StringUtils.isNotBlank(tForm.editCssId()) ? tForm.editCssId() : null);
		if(StringUtils.isNotBlank(tForm.style()))
			this.form.setStyle(tForm.style());
	}
	
	
	
}
