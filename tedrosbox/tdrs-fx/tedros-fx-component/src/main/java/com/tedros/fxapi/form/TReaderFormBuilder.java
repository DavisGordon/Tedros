/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import java.lang.reflect.InvocationTargetException;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.annotation.form.TForm;

/**
 * The {@link ITModelForm} builder for reader mode. 
 *
 * @author Davis Gordon
 */
public class TReaderFormBuilder<M extends ITModelView<?>> {
	
	private ITModelForm<M> form;
	private M modelView;
	
	@SuppressWarnings("rawtypes")
	private Class<? extends ITModelForm> formClass;
	
	private TReaderFormBuilder(M modelView) {
		this.form = null;
		this.modelView = modelView;
	}
	
	@SuppressWarnings("rawtypes")
	private TReaderFormBuilder(M modelView, Class<? extends ITModelForm> modelViewFormClass) {
		this.form = null;
		this.modelView = modelView;
		this.formClass = modelViewFormClass;
	}
	
	
	public static TReaderFormBuilder<?> create(ITModelView<?> modelView){
		return new TReaderFormBuilder<ITModelView<?>>(modelView);
	}
	
	@SuppressWarnings("rawtypes")
	public static TReaderFormBuilder<?> create(ITModelView<?> modelView, Class<? extends ITModelForm> modelViewFormClass){
		return new TReaderFormBuilder<ITModelView<?>>(modelView, modelViewFormClass);
	}
			
	
	public ITModelForm<M> build() {
		
		TForm tForm = this.modelView.getClass().getAnnotation(TForm.class);
		if(tForm!=null)
			this.formClass = tForm.form();
		
		if(this.formClass==null)
			this.formClass = TDefaultForm.class;
		
		try {
			generate();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		
		return this.form;
		
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private void generate() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		if(this.formClass == TDefaultForm.class || this.formClass == TDefaultDetailForm.class)
			this.form = (ITModelForm<M>) this.formClass.getConstructor(ITModelView.class, Boolean.class).newInstance((ITModelView)this.modelView, true);
		else
			this.form = (ITModelForm<M>) this.formClass.getConstructor(this.modelView.getClass(), Boolean.class).newInstance(this.modelView, true);
		
	}
	
	
	
}
