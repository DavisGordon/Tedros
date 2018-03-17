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

import javafx.scene.paint.Color;

import com.tedros.fxapi.control.TCheckBoxField;
import com.tedros.fxapi.control.TColorPickerField;
import com.tedros.fxapi.control.TSlider;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.settings.layout.behavior.ExampleBehavior;
import com.tedros.settings.layout.model.ExampleViewModel;
import com.tedros.util.TColorUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class CustomizarPainelTrigger extends TTrigger {

	
	private final NumberFormat numberFormat;
	
	public CustomizarPainelTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
		numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
	}
	
	@Override
	public void run() {
		
		final TFieldBox source = getSource();
		final ExampleBehavior behavior = (ExampleBehavior) getForm().gettAssociatedObject(ExampleBehavior.class.getSimpleName());
		final String fieldName = source.gettControlFieldName();
		
		List<String> list = Arrays.asList("painelCorTexto", "painelTamTexto");
		if(list.contains(fieldName)){
			Color painelCorTexto = getColorValue(list.get(0));
			String painelTamTexto = getSliderValue(list.get(1));
			behavior.processPanelTextStyle(TColorUtil.toHexadecimal(painelCorTexto), painelTamTexto);
			behavior.processFieldSetBorderAndTextColor(TColorUtil.toHexadecimal(getColorValue(list.get(0))), getSliderValue("campoTamTitulo"));
		}
		
		list = Arrays.asList("painelCorFundo", "painelOpacidade");
		if(list.contains(fieldName)){
			Color painelCorFundo = getColorValue(list.get(0));
			String painelOpacidade = getSliderValue(list.get(1));
			behavior.processPanelStyle(painelCorFundo, painelOpacidade);
			behavior.processFieldSetLegendBackgroundColor(painelCorFundo);
		}
		
		list = Arrays.asList("botaoCorTexto", "botaoTamTexto", "botaoCorFundo", "botaoCorBorda", "botaoOpacidade");
		if(list.contains(fieldName)){
			Color botaoCorTexto = getColorValue(list.get(0));
			String botaoTamTexto = getSliderValue(list.get(1));
			Color botaoCorFundo = getColorValue(list.get(2));
			Color botaoCorBorda = getColorValue(list.get(3));
			String botaoOpacidade = getSliderValue(list.get(4));
			behavior.processPanelButtons(botaoCorTexto, botaoCorFundo, botaoCorBorda, botaoTamTexto, botaoOpacidade);
		}
		
		list = Arrays.asList("campoCorTexto", "campoCorFundo", "campoCorBorda", "campoTamTexto", "campoTextoNegrito",
				"campoCorTitulo", "campoTamTitulo", 
				"formCorFundo", "formOpacidade");
		if(list.contains(fieldName)){
			setFormMode(behavior);
			Color campoCorTitulo = getColorValue(list.get(5));
			String campoTamTitulo = getSliderValue(list.get(6));
			behavior.processLabelStyle(campoCorTitulo, campoTamTitulo);
			
			Color formCorFundo = getColorValue(list.get(7));
			String formOpacidade = getSliderValue(list.get(8));
			behavior.processBackgroundColor(formOpacidade, formCorFundo);
			
			Color campoCorTexto = getColorValue(list.get(0));
			Color campoCorFundo = getColorValue(list.get(1));
			Color campoCorBorda = getColorValue(list.get(2));
			String campoTamTexto = getSliderValue(list.get(3));
			boolean campoTextoNegrito = getCheckBoxValue(list.get(4));
			
			behavior.processEditInputStyle(campoTamTitulo, campoTamTexto, campoCorFundo, campoCorBorda, campoCorTitulo, campoCorTexto, campoTextoNegrito);
			behavior.processFieldSetBorderAndTextColor(TColorUtil.toHexadecimal(getColorValue("painelCorTexto")), campoTamTitulo);
		}
		
		list = Arrays.asList("readerCorTituloCampo", "readerTamTituloCampo");
		if(list.contains(fieldName)){
			setReaderMode(behavior);
			Color readerCorTituloCampo = getColorValue(list.get(0));
			String readerTamTituloCampo = getSliderValue(list.get(1));
			behavior.processLabelStyle(readerCorTituloCampo, readerTamTituloCampo);
		}
		
		list = Arrays.asList("readerCorTexto", "readerTamTexto");
		if(list.contains(fieldName)){
			setReaderMode(behavior);
			Color readerCorTexto = getColorValue(list.get(0));
			String readerTamTexto = getSliderValue(list.get(1));
			behavior.processTextStyle(readerCorTexto, readerTamTexto);
		}
		
		list = Arrays.asList("readerCorFundo", "readerOpacidade");
		if(list.contains(fieldName)){
			setReaderMode(behavior);
			Color readerCorFundo = getColorValue(list.get(0));
			String readerOpacidade = getSliderValue(list.get(1));
			behavior.processBackgroundColor(readerOpacidade, readerCorFundo);
		}
		
	}

	private void setReaderMode(final ExampleBehavior presenter) {
		if(!presenter.isReaderModeSelected()){
			presenter.setViewMode(TViewMode.READER);
			presenter.selectMode();
		}
	}
	
	private void setFormMode(final ExampleBehavior presenter) {
		if(!presenter.isEditModeSelected()){
			presenter.setViewMode(TViewMode.EDIT);
			presenter.selectMode();
		}
	}

	@SuppressWarnings("unchecked")
	private Color getColorValue(String field) {
		ITModelForm<ExampleViewModel> form = (ITModelForm<ExampleViewModel>) getForm();
		return ((TColorPickerField)form.gettFieldBox(field).gettControl()).getValue();
	}
	
	@SuppressWarnings("unchecked")
	private String getSliderValue(String field) {
		ITModelForm<ExampleViewModel> form = (ITModelForm<ExampleViewModel>) getForm();
		String value = numberFormat.format(((TSlider) form.gettFieldBox(field).gettControl()).getValue()); 
		return value.replaceAll(",", ".");
	}
	
	@SuppressWarnings("unchecked")
	private boolean getCheckBoxValue(String field) {
		ITModelForm<ExampleViewModel> form = (ITModelForm<ExampleViewModel>) getForm();
		return ((TCheckBoxField) form.gettFieldBox(field).gettControl()).isSelected();
	}

}
