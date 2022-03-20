/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/08/2014
 */
package com.tedros.settings.layout.trigger;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import com.tedros.fxapi.control.TColorPickerField;
import com.tedros.fxapi.control.TSlider;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.settings.layout.model.TMainColorModelView;
import com.tedros.settings.layout.template.TemplatePane;

import javafx.scene.paint.Color;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class MainColorTrigger extends TTrigger<Object> {

	
	private final NumberFormat numberFormat;
	
	public MainColorTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
		numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
	}
	
	@Override
	public void run(Object value) {
		
		final TFieldBox source = getSource();
		final TemplatePane t = (TemplatePane) getForm().gettAssociatedObject(TemplatePane.class.getSimpleName());
		final String fieldName = source.gettControlFieldName();
		
		List<String> list = Arrays.asList("mainCorFundo", "mainOpacidade");
		if(list.contains(fieldName)){
			Color c = getColorValue(list.get(0));
			String o = getSliderValue(list.get(1));
			t.settHeaderStyle(c, o);
		}
		
		list = Arrays.asList("mainCorTexto");
		if(list.contains(fieldName)){
			Color c = getColorValue(list.get(0));
			t.settAppNameStyle(c);
		}
		
		list = Arrays.asList("navCorFundo", "navOpacidade", "navCorTexto");
		if(list.contains(fieldName)){
			Color c = getColorValue(list.get(0));
			String o = getSliderValue(list.get(1));
			Color x = getColorValue(list.get(2));
			t.settTopMenuBarStyle(x, c, o);
		}
		
		list = Arrays.asList("navCorTexto");
		if(list.contains(fieldName)){
			Color c = getColorValue(list.get(0));
			t.settTopMenuStyle(c);
		}
		
		list = Arrays.asList( "appCorTexto", "appTamTexto");
		if(list.contains(fieldName)){
			Color c = getColorValue(list.get(0));
			String o = getSliderValue(list.get(1));
			t.settIconStyle(c, o);;
		}
		
	}


	@SuppressWarnings("unchecked")
	private Color getColorValue(String field) {
		ITModelForm<TMainColorModelView> form = (ITModelForm<TMainColorModelView>) getForm();
		return ((TColorPickerField)form.gettFieldBox(field).gettControl()).getValue();
	}
	
	@SuppressWarnings("unchecked")
	private String getSliderValue(String field) {
		ITModelForm<TMainColorModelView> form = (ITModelForm<TMainColorModelView>) getForm();
		String value = numberFormat.format(((TSlider) form.gettFieldBox(field).gettControl()).getValue()); 
		return value.replaceAll(",", ".");
	}
	
}
