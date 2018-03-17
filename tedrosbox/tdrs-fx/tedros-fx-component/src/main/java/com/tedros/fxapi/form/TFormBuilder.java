/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.annotation.form.TForm;

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
			e.printStackTrace();
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
			formClass = tForm.form();
		
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
				break;
				case 2 :
					this.form = (ITModelForm<M>) formClass.getConstructor(this.modelView.getClass()).newInstance(this.modelView);
				break;
				case 3 :
					this.form = (ITModelForm<M>) formClass.getConstructor(ITPresenter.class, ITModelView.class).newInstance(this.presenter, (ITModelView)this.modelView);
				break;
				case 4 :
					this.form = (ITModelForm<M>) formClass.getConstructor(ITPresenter.class, this.modelView.getClass()).newInstance(this.presenter, this.modelView);
				break;
				default:
					throw new InstantiationException("ERROR: THE FORM "+formClass.getName()+" must have a constructor with TModelView or "+this.modelView.getClass().getCanonicalName()+" parameter!");
			}
			
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	
		
		return this.form;
		
	}
	
	
	
}
