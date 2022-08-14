/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 18/08/2014
 */
package org.tedros.tools.module.scheme.trigger;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import org.tedros.fx.control.TColorPickerField;
import org.tedros.fx.control.TSlider;
import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.ITModelForm;
import org.tedros.fx.form.TFieldBox;
import org.tedros.tools.module.scheme.model.TMainColorMV;
import org.tedros.tools.module.scheme.template.TemplatePane;

import javafx.scene.paint.Color;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TMainColorTrigger extends TTrigger<Object> {

	
	private final NumberFormat numberFormat;
	
	public TMainColorTrigger(TFieldBox source, TFieldBox target) {
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
		ITModelForm<TMainColorMV> form = (ITModelForm<TMainColorMV>) getForm();
		return ((TColorPickerField)form.gettFieldBox(field).gettControl()).getValue();
	}
	
	@SuppressWarnings("unchecked")
	private String getSliderValue(String field) {
		ITModelForm<TMainColorMV> form = (ITModelForm<TMainColorMV>) getForm();
		String value = numberFormat.format(((TSlider) form.gettFieldBox(field).gettControl()).getValue()); 
		return value.replaceAll(",", ".");
	}
	
}
