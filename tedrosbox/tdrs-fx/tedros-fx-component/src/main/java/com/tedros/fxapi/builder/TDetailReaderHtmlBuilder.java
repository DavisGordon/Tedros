/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.web.WebView;

import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.fxapi.annotation.reader.TDetailReader;
import com.tedros.fxapi.annotation.reader.TDetailReaderHtml;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TConverter;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.form.TReaderFormBuilder;
import com.tedros.fxapi.html.THtmlLayoutGenerator;
import com.tedros.fxapi.reader.THtmlReader;
import com.tedros.fxapi.util.TModelViewUtil;


/**
 * <pre>
 * The builder used by {@link TDetailReader} annotation.
 * </pre>
 * @see TDetailReader
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TDetailReaderHtmlBuilder 
extends TBuilder
implements ITReaderHtmlBuilder<TDetailReaderHtml, Object> {

	private static final String DIV = "<div id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' >"+THtmlConstant.CONTENT+"</div>";
	
	private static TDetailReaderHtmlBuilder instance;
	
	private TDetailReaderHtmlBuilder(){
		
	}
	
	public static TDetailReaderHtmlBuilder getInstance(){
		if(instance==null)
			instance = new TDetailReaderHtmlBuilder();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public THtmlReader build(final TDetailReaderHtml tAnnotation, final Object fieldObject) throws Exception {
		
		final TComponentDescriptor descriptor = getComponentDescriptor();
		final ITModelView modelView = descriptor.getModelView();
		final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		final String fieldName = descriptor.getFieldDescriptor().getFieldName();
		
		System.out.println("########################");
		System.out.println(uuid);
		System.out.println(fieldName);

	
		
		if (fieldObject instanceof ITObservableList){
			//build listener
			ChangeListener<Number> listener = new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {					
					updateWebView(tAnnotation, fieldObject, descriptor, uuid);
					/*WebView wv =  ((ITModelForm)descriptor.getForm()).gettWebView();
					if(wv!=null){
						
						String html = (String) ((ITModelForm)descriptor.getForm()).gettWebView().getEngine().executeScript("document.documentElement.outerHTML");
						
						System.out.println(html);
						System.out.println("**************************************");
						System.out.println(uuid);
						System.out.println(fieldName);
						System.out.println(arg2);
						
						THtmlReader tHtmlReader = buildTHtmlReader(tAnnotation, fieldObject);
						wv.getEngine().executeScript("document.getElementById('"+uuid+"').innerHTML = \""+tHtmlReader.getText()+"\"");
					}*/
				}
			};			
			//add listener to the property
			((ITObservableList)fieldObject).tHashCodeProperty().addListener(listener);
			// add listener to the model view repository
			modelView.addListener(fieldName, listener);
			
		}else
		if (fieldObject instanceof Property) {
			//build listener
			ChangeListener<Object> listener = new ChangeListener<Object>() {
				@Override
				public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {					
					updateWebView(tAnnotation, fieldObject, descriptor, uuid);
					/*WebView wv =  ((ITModelForm)descriptor.getForm()).gettWebView();
					if(wv!=null){
						
						String html = (String) ((ITModelForm)descriptor.getForm()).gettWebView().getEngine().executeScript("document.documentElement.outerHTML");
						
						System.out.println(html);
						System.out.println("**************************************");
						System.out.println(uuid);
						System.out.println(fieldName);
						System.out.println(arg2);
						
						THtmlReader tHtmlReader = buildTHtmlReader(tAnnotation, fieldObject);
						wv.getEngine().executeScript("document.getElementById('"+uuid+"').innerHTML = \""+tHtmlReader.getText()+"\"");
					}*/
				}
			};
			//add listener to the property
			((Property)fieldObject).addListener(listener);
			// add listener to the model view repository
			modelView.addListener(fieldName, listener);
			
		}
		
		return new THtmlReader(DIV.replace(THtmlConstant.ID, uuid).replace(THtmlConstant.NAME, fieldName), 
				buildTHtmlReader(tAnnotation, fieldObject).getText());
	}

	@SuppressWarnings("unchecked")
	public THtmlReader buildTHtmlReader(final TDetailReaderHtml tAnnotation,
			final Object attrProperty) {
		if(tAnnotation.converter().type()!=com.tedros.fxapi.form.TConverter.class){
			Class clazz = tAnnotation.converter().type();
			TConverter converter;
			try {
				converter = (TConverter) clazz.getConstructor().newInstance();
				converter.setIn(attrProperty);
				THtmlReader tHtmlReader = (THtmlReader) converter.getOut();
				return tHtmlReader;
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		
		THtmlReader tHtmlReader = null;
		
		if (attrProperty instanceof Property){
			Object object = ((Property) attrProperty).getValue();
			tHtmlReader = new THtmlReader(buidHtml(tAnnotation, object));
		}else		
		if (attrProperty instanceof ObservableList) {
			THtmlLayoutGenerator layoutGenerator = new THtmlLayoutGenerator(tAnnotation.modelLayout());
			for (Object object : (ObservableList) attrProperty) {
				String str = buidHtml(tAnnotation, object);
				layoutGenerator.addContent(str);
			}
			tHtmlReader = new THtmlReader(layoutGenerator.getHtml());
		}
		
		return tHtmlReader;
	}

	@SuppressWarnings("unchecked")
	private String buidHtml(TDetailReaderHtml tAnnotation, Object object)
	{
		
		if(object instanceof ITModelView){
			return buildDetailForm(tAnnotation, (ITModelView<?>) object);
			
		}else if (object instanceof TEntity){
			return buildDetailForm(tAnnotation, new TModelViewUtil(tAnnotation.modelViewClass(), tAnnotation.entityClass(), (ITEntity) object).convertToModelView());
			
		}
		
		return null;
	}
	
	private static final String buildDetailForm(TDetailReaderHtml tDetailReader, ITModelView<?> modelView)
	{
		ITModelForm detailForm = TReaderFormBuilder.create(modelView, TDefaultForm.class).build();
		
		THtmlLayoutGenerator layoutGenerator = new THtmlLayoutGenerator(tDetailReader.fieldsLayout());
		
		for(Object object : detailForm.gettFormItens()){
			if(object instanceof THtmlReader)
				layoutGenerator.addContent(((THtmlReader)object).getText());
		}
		detailForm.getChildren().clear();
		
		return layoutGenerator.getHtml();
	}
	
	private void updateWebView(final TDetailReaderHtml tAnnotation, final Object fieldObject, final TComponentDescriptor descriptor,
			final String uuid) {
		WebView wv =  ((ITModelForm)descriptor.getForm()).gettWebView();
		if(wv!=null){
			THtmlReader tHtmlReader = buildTHtmlReader(tAnnotation, fieldObject);
			try{
				wv.getEngine().executeScript("document.getElementById('"+uuid+"').innerHTML = \""+tHtmlReader.getText()+"\"");
			}catch(Exception e){
				
			}
		}
	}
	
	
	
}
