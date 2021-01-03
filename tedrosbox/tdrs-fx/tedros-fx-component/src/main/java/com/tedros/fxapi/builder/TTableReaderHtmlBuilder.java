/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package com.tedros.fxapi.builder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.web.WebView;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.model.ITModelView;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.fxapi.annotation.reader.TColumnReader;
import com.tedros.fxapi.annotation.reader.TTableReaderHtml;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TConverter;
import com.tedros.fxapi.html.THtmlTableGenerator;
import com.tedros.fxapi.html.THtmlTableGenerator.TColumn;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.reader.THtmlReader;
import com.tedros.fxapi.util.TMaskUtil;
import com.tedros.util.TStripTagUtil;


/**
 * <pre>
 * The builder used by {@link TTableReaderHtml} annotation.
 * </pre>
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TTableReaderHtmlBuilder 
extends TBuilder
implements ITReaderHtmlBuilder<TTableReaderHtml, Object> {

	private static final String DIV = "<div id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' >"+THtmlConstant.CONTENT+"</div>";
	private final TStripTagUtil tStripTagUtil;
	
	public TTableReaderHtmlBuilder(){
		tStripTagUtil = new TStripTagUtil();
	}
	
	@SuppressWarnings("unchecked")
	public THtmlReader build(final TTableReaderHtml tAnnotation,final Object fieldObject) throws Exception {
		
		final TComponentDescriptor descriptor = getComponentDescriptor();
		final ITModelView modelView = descriptor.getModelView();
		final String uuid = UUID.randomUUID().toString();
		final String fieldName = descriptor.getFieldDescriptor().getFieldName();
		
		if (fieldObject instanceof ITObservableList){
			//build listener
			ChangeListener<Number> listener = new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {					
					updateWebView(tAnnotation, fieldObject, descriptor, uuid);
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
	public THtmlReader buildTHtmlReader(final TTableReaderHtml tAnnotation, final Object fieldObject)  {
		if(tAnnotation.converter().type()!=com.tedros.fxapi.form.TConverter.class){
			Class clazz = tAnnotation.converter().type();
			TConverter converter;
			try {
				
				converter = (TConverter) clazz.getConstructor().newInstance();
				converter.setIn(fieldObject);
				return (THtmlReader) converter.getOut();
				
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		
		THtmlTableGenerator tHtmlTableGenerator = new THtmlTableGenerator();
		tHtmlTableGenerator.setTableStyle(replaceTags(tAnnotation.tableHtmlStyle()));
		tHtmlTableGenerator.setTableAttribute(replaceTags(tAnnotation.tableHtmlAttribute()));
		tHtmlTableGenerator.setRowHeaderStyle(replaceTags(tAnnotation.rowHeaderHtmlStyle()));
		tHtmlTableGenerator.setRowHeaderAttribute(replaceTags(tAnnotation.rowHeaderHtmlAttribute()));
		tHtmlTableGenerator.setRowStyle(replaceTags(tAnnotation.rowHtmlStyle()));
		tHtmlTableGenerator.setRowAttribute(replaceTags(tAnnotation.rowHtmlAttribute()));
		
		if (fieldObject instanceof SimpleObjectProperty){
			final SimpleObjectProperty<?> property = (SimpleObjectProperty) fieldObject;
			Object object = property.getValue();
			List<List<TColumn>> rows = new ArrayList<>();
			fillHeaders(tAnnotation, tHtmlTableGenerator, rows);
			try {
				fillColumns(tAnnotation, tHtmlTableGenerator, object, rows);
			} catch (NoSuchMethodException | IllegalAccessException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
			tHtmlTableGenerator.setRows(rows);
		}else
		if (fieldObject instanceof ObservableList) {
			List<List<TColumn>> rows = new ArrayList<>();
			fillHeaders(tAnnotation, tHtmlTableGenerator, rows);
			try{	
				for(Object object : (ObservableList) fieldObject )
					fillColumns(tAnnotation, tHtmlTableGenerator, object, rows);
			} catch (NoSuchMethodException | IllegalAccessException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
			tHtmlTableGenerator.setRows(rows);
		}
		
		return new THtmlReader(tHtmlTableGenerator.getHtml());
	}

	private void fillColumns(TTableReaderHtml tAnnotation,
			THtmlTableGenerator tHtmlTableGenerator, Object object, List<List<TColumn>> rows)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		List<TColumn> cols = new ArrayList<>();
		for(TColumnReader tch : tAnnotation.column()){
			String fieldName = tch.field();
			String methodName = "get"+StringUtils.capitalize(fieldName);
			Method method = object.getClass().getMethod(methodName);
			if(method!=null){
				Object obj = method.invoke(object);
				String fieldValue = getValue(tch, obj);
				THtmlReader tColHtmlReader = new THtmlReader(tch.fieldValueHtmlTemplate(), fieldValue);
				TColumn tColumn = tHtmlTableGenerator.new TColumn(tColHtmlReader.getText());
				tColumn.setColumnHeaderStyle(replaceTags(tch.columnHeaderHtmlStyle()));
				tColumn.setColumnHeaderAttribute(replaceTags(tch.columnHeaderHtmlAttribute()));
				tColumn.setColumnStyle(replaceTags(tch.columnHtmlStyle()));
				tColumn.setColumnAttribute(replaceTags(tch.columnHtmlAttribute()));
				cols.add(tColumn);
			}
		}
		rows.add(cols);
	}

	private void fillHeaders(TTableReaderHtml tAnnotation,
			THtmlTableGenerator tHtmlTableGenerator, List<List<TColumn>> rows) {
		List<TColumn> headers = new ArrayList<>();
		for(TColumnReader tch : tAnnotation.column()){
			TColumn tColumn = tHtmlTableGenerator.new TColumn(tch.name());
			tColumn.setColumnHeaderStyle(replaceTags(tch.columnHeaderHtmlStyle()));
			tColumn.setColumnHeaderAttribute(replaceTags(tch.columnHeaderHtmlAttribute()));
			tColumn.setColumnStyle(replaceTags(tch.columnHtmlStyle()));
			tColumn.setColumnAttribute(replaceTags(tch.columnHtmlAttribute()));
			headers.add(tColumn);
		}
		rows.add(headers);
	}

	private String replaceTags(String styleContent) {
		if(tStripTagUtil.isTagPresent(styleContent)){
			String[] keys = tStripTagUtil.getTags(styleContent);
			for (String key : keys) {
				TStyleResourceValue tResourceValue = TStyleResourceValue.getByName(key);
				if(tResourceValue!=null){
					String value = tResourceValue.customStyle();
					if(value!=null)
						return tStripTagUtil.replaceTag(styleContent, key, value);
				}	
			}
		}
		
		return styleContent;
	}
	
	private String getValue(TColumnReader tAnnotation, Object object) {
		Object objectValue = (object instanceof Property) 
				? ((Property)object).getValue():
					object;
		return getContent(tAnnotation, objectValue);	
	}

	private String getContent(TColumnReader tAnnotation, final Object objectValue) {
		if(objectValue==null)
			return "";
		
		if(objectValue instanceof String || objectValue instanceof Number){
			return StringUtils.isNotBlank(tAnnotation.mask()) 
					? TMaskUtil.applyMask(String.valueOf(objectValue), tAnnotation.mask()) 
							: String.valueOf(objectValue);
		}
		
		if(objectValue instanceof Date)
			return new SimpleDateFormat(tAnnotation.datePattern()).format(objectValue);
		
		if(objectValue instanceof Boolean)
			return ((Boolean)objectValue) ? tAnnotation.booleanValues().trueValue() : tAnnotation.booleanValues().falseValue();
		
		if(objectValue instanceof TModelView)
			return ((TModelView)objectValue).getDisplayProperty().getValue();
		
		return objectValue.toString();
	}

	private void updateWebView(final TTableReaderHtml tAnnotation, final Object fieldObject, final TComponentDescriptor descriptor,
			final String uuid) {
		WebView wv =  ((ITModelForm)descriptor.getForm()).gettWebView();
		if(wv!=null){
			THtmlReader tHtmlReader = buildTHtmlReader(tAnnotation, fieldObject);
			wv.getEngine().executeScript("document.getElementById('"+uuid+"').innerHTML = \""+tHtmlReader.getText()+"\"");
		}
	}
	
	
}
