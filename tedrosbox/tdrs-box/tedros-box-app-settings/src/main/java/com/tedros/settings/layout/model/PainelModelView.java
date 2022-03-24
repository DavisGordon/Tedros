package com.tedros.settings.layout.model;

import com.tedros.core.TLanguage;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TColorPickerField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelDefaultSetting;
import com.tedros.fxapi.annotation.control.TSliderField;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.TFieldSet;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TModelProcess;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.settings.layout.behavior.TedrosSettingBehavior;
import com.tedros.settings.layout.decorator.TedrosSettingDecorator;
import com.tedros.settings.layout.process.TedrosSettingProcess;
import com.tedros.settings.layout.trigger.CustomizarPainelTrigger;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

@TForm(name="#{settings.form.name}", editCssId="y")
@TLabelDefaultSetting(position=TLabelPosition.TOP, textFill="#ffffff")
@TModelProcess(type=TedrosSettingProcess.class)
@TPresenter(type = TDynaPresenter.class, modelClass = PainelModel.class,
	behavior=@TBehavior(type = TedrosSettingBehavior.class), 
	decorator=@TDecorator(type=TedrosSettingDecorator.class))
public class PainelModelView extends TModelView<PainelModel> {
	
	/**
	 * Grupo customizar painel
	 * */
	@TAccordion(
			panes={
					@TTitledPane(text="#{settings.layout.painel}", fields={"painelCorTexto", "painelCorFundo", "painelTamTexto", "painelOpacidade"}),
					@TTitledPane(text="#{settings.layout.buttons}", fields={"botaoCorTexto", "botaoTamTexto", "botaoCorFundo", "botaoCorBorda","botaoOpacidade"}),
					@TTitledPane(text="#{settings.layout.fieldtitle}", fields={"campoCorTitulo", "campoTamTitulo"}),
					@TTitledPane(text="#{settings.layout.field}", fields={"campoCorTexto", "campoCorFundo", "campoCorBorda", "campoTamTexto", "campoTextoNegrito"}),
					@TTitledPane(text="#{settings.layout.form}", fields={"formCorFundo", "formOpacidade"}),
					@TTitledPane(text="#{settings.layout.reader}", fields={"readerCorTituloCampo", "readerCorTexto", "readerCorFundo"})
			})
	@TLabel(text="#{label.text}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> painelCorTexto;
	
	@TLabel(text="#{label.font}")
	@TSliderField(max = 2, min = 0.8,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty painelTamTexto;
	
	@TLabel(text="#{label.background}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> painelCorFundo;
	
	@TLabel(text="#{label.opacit}")
	@TSliderField(max = 1, min = 0,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty painelOpacidade;
	
	/**
	 * Grupo customizar bot�es
	 * */
	@TLabel(text="#{label.text}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> botaoCorTexto;
	
	@TLabel(text="#{label.font}")
	@TSliderField(max = 2, min = 0.8,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty botaoTamTexto;
	
	@TLabel(text="#{label.color}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> botaoCorFundo;
	
	@TLabel(text="#{label.border}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> botaoCorBorda;
	
	@TLabel(text="#{label.opacit}")
	@TSliderField(max = 1, min = 0,
			majorTickUnit=0.5, blockIncrement=0.01,
					minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty botaoOpacidade;
	
	/**
	 * Grupo customizar titulo dos campos
	 * */
	@TLabel(text="#{label.text}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> campoCorTitulo;
	
	@TLabel(text="#{label.font}")
	@TSliderField(max = 2, min = 0.8,
			majorTickUnit=0.5, blockIncrement=0.01,
			minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true, snapToTicks=false)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty campoTamTitulo;
	
	/**
	 * Grupo customizar campo 
	 * */
	@TLabel(text="#{label.text}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> campoCorTexto;
	
	@TLabel(text="#{label.background}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80)) 
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> campoCorFundo;
	
	@TLabel(text="#{label.border}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> campoCorBorda;
	
	@TLabel(text="#{label.font}")
	@TSliderField(max = 2, min = 0.8,
	majorTickUnit=0.5, blockIncrement=0.01,
	minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
	showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty campoTamTexto;
	
	@TCheckBoxField(labeled=@TLabeled(text="Negrito", parse = true))
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleBooleanProperty campoTextoNegrito;
	
	/**
	 * Grupo customizar formulario
	 * */
	@TLabel(text="#{label.color}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> formCorFundo;
	
	@TLabel(text="#{label.opacit}")
	@TSliderField(max = 1, min = 0,
			majorTickUnit=0.5, blockIncrement=0.01,
			minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty formOpacidade;
	
	/**
	 * Grupo customizar titulo do visualizar
	 * */
	// Visualizar - Titulos
	@TLabel(text="#{label.color}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	@TFieldSet(fields = { "readerCorTituloCampo", "readerTamTituloCampo" }, legend = "#{label.title}", layoutType=TLayoutType.VBOX)
	private SimpleObjectProperty<Color> readerCorTituloCampo;
	
	@TLabel(text="#{label.font}")
	@TSliderField(max = 2, min = 0.8,
			majorTickUnit=0.5, blockIncrement=0.01,
			minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty readerTamTituloCampo;
	
	// Visualizar - Texto
	@TLabel(text="#{label.color}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	@TFieldSet(fields = { "readerCorTexto", "readerTamTexto" }, legend = "#{label.text}", layoutType=TLayoutType.VBOX)
	private SimpleObjectProperty<Color> readerCorTexto;
	
	@TLabel(text="#{label.font}")
	@TSliderField(max = 2, min = 0.8,
			majorTickUnit=0.5, blockIncrement=0.01,
			minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty readerTamTexto;
	
	// visualizar - Fundo
	@TLabel(text="#{label.color}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TFieldSet(fields = { "readerCorFundo", "readerOpacidade" }, legend = "#{label.background}", layoutType=TLayoutType.VBOX)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> readerCorFundo;
	
	@TLabel(text="#{label.opacit}")
	@TSliderField(max = 1, min = 0,
			majorTickUnit=0.5, blockIncrement=0.01,
			minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=CustomizarPainelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty readerOpacidade;
	
	
	public PainelModelView(PainelModel entity) {
		super(entity);
	}
	
	public void loadSavedValues() {
		
		String savedEditLabelColor = TStyleResourceValue.FORM_CONTROL_LABEL_COLOR.customStyle(true);
		String savedEditSizeLabel = TStyleResourceValue.FORM_CONTROL_LABEL_SIZE.customStyle();
		String savedEditFormColor = TStyleResourceValue.FORM_BACKGROUND_COLOR.customStyle();
		String savedEditOpacityColor = TStyleResourceValue.FORM_BACKGROUND_OPACITY.customStyle();
		
		String savedReportLabelColor = TStyleResourceValue.READER_LABEL_COLOR.customStyle();
		String savedReportSizeLabel = TStyleResourceValue.READER_LABEL_SIZE.customStyle();
		
		String savedReaderTextColor = TStyleResourceValue.READER_TEXT_COLOR.customStyle();
		String savedReaderTextSize = TStyleResourceValue.READER_TEXT_SIZE.customStyle();
		
		String savedReportFormColor = TStyleResourceValue.READER_BACKGROUND_COLOR.customStyle();
		String savedReportOpacityColor = TStyleResourceValue.READER_BACKGROUND_OPACITY.customStyle();
		
		String savedPanelTextColor = TStyleResourceValue.PANEL_TEXT_COLOR.customStyle();
		String savedPanelSizeLabel = TStyleResourceValue.PANEL_TEXT_SIZE.customStyle();
		String savedPanelColor = TStyleResourceValue.PANEL_BACKGROUND_COLOR.customStyle();
		String savedPanelOpacityColor = TStyleResourceValue.PANEL_BACKGROUND_OPACITY.customStyle();
		
		String savedButtonTextColor = TStyleResourceValue.BUTTON_TEXT_COLOR.customStyle();
		String savedButtonTextSizeLabel = TStyleResourceValue.BUTTON_TEXT_SIZE.customStyle();
		String savedButtonColor = TStyleResourceValue.BUTTON_BACKGROUND_COLOR.customStyle();
		String savedButtonBorderColor = TStyleResourceValue.BUTTON_BORDER_COLOR.customStyle();
		String savedButtonOpacityColor = TStyleResourceValue.BUTTON_BACKGROUND_OPACITY.customStyle();
		
		String savedInputTextColor = TStyleResourceValue.INPUT_TEXT_COLOR.customStyle();
		String savedInputSize = TStyleResourceValue.INPUT_TEXT_SIZE.customStyle();
		String savedInputColor = TStyleResourceValue.INPUT_COLOR.customStyle();
		String savedInputBorderColor = TStyleResourceValue.INPUT_BORDER_COLOR.customStyle();
		
		this.campoCorTitulo.setValue(savedEditLabelColor!=null ? Color.web(savedEditLabelColor) : Color.WHITE);
		this.formCorFundo.setValue(savedEditFormColor!=null ? Color.web(savedEditFormColor) : Color.BLACK);
		this.formOpacidade.setValue(savedEditOpacityColor!=null ? Double.parseDouble(savedEditOpacityColor) : 0.35);
		this.campoTamTitulo.setValue(savedEditSizeLabel!=null ? Double.parseDouble(savedEditSizeLabel) : 1.2);
		
		this.readerCorTituloCampo.setValue(savedReportLabelColor!=null ? Color.web(savedReportLabelColor) : Color.WHITE);
		this.readerCorFundo.setValue(savedReportFormColor!=null ? Color.web(savedReportFormColor) : Color.BLACK);
		this.readerCorTexto.setValue(savedReaderTextColor!=null ? Color.web(savedReaderTextColor) : Color.WHITE);
		this.readerTamTexto.setValue(savedReaderTextSize!=null ? Double.parseDouble(savedReaderTextSize) : 1.5);
		this.readerOpacidade.setValue(savedReportOpacityColor!=null ? Double.parseDouble(savedReportOpacityColor) : 0.35);
		this.readerTamTituloCampo.setValue(savedReportSizeLabel!=null ? Double.parseDouble(savedReportSizeLabel) : 1.5);
		
		this.painelCorTexto.setValue(savedPanelTextColor!=null ? Color.web(savedPanelTextColor) : Color.WHITE);
		this.painelCorFundo.setValue(savedPanelColor!=null ? Color.web(savedPanelColor) : Color.web("#003333"));
		this.painelOpacidade.setValue(savedPanelOpacityColor!=null ? Double.parseDouble(savedPanelOpacityColor) : 0.5);
		this.painelTamTexto.setValue(savedPanelSizeLabel!=null ? Double.parseDouble(savedPanelSizeLabel) : 1.2);
		
		this.botaoCorTexto.setValue(savedButtonTextColor!=null ? Color.web(savedButtonTextColor) : Color.WHITE);
		this.botaoCorFundo.setValue(savedButtonColor!=null ? Color.web(savedButtonColor) : Color.WHITE);
		this.botaoCorBorda.setValue(savedButtonBorderColor!=null ? Color.web(savedButtonBorderColor) : Color.web("#D3CDC2"));
		this.botaoOpacidade.setValue(savedButtonOpacityColor!=null ? Double.parseDouble(savedButtonOpacityColor) : 0.4);
		this.botaoTamTexto.setValue(savedButtonTextSizeLabel!=null ? Double.parseDouble(savedButtonTextSizeLabel) : 1);
		
		this.campoCorTexto.setValue(savedInputTextColor!=null ? Color.web(savedInputTextColor) : Color.BLACK);
		this.campoTamTexto.setValue(savedInputSize!=null ? Double.parseDouble(savedInputSize) : 1.2);
		this.campoCorFundo.setValue(savedInputColor!=null ? Color.web(savedInputColor) : Color.WHITESMOKE);
		this.campoCorBorda.setValue(savedInputBorderColor!=null ? Color.web(savedInputBorderColor) : Color.web("#ADB85F"));
		
	}

	@Override
	public void setId(SimpleLongProperty id) {
		// TODO Auto-generated method stub

	}

	@Override
	public SimpleLongProperty getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		String s = TLanguage.getInstance(null).getString("#{label.style}");
		return new SimpleStringProperty(s);
	}

	public SimpleObjectProperty<Color> getPainelCorTexto() {
		return painelCorTexto;
	}

	public void setPainelCorTexto(SimpleObjectProperty<Color> painelCorTexto) {
		this.painelCorTexto = painelCorTexto;
	}

	public SimpleDoubleProperty getPainelTamTexto() {
		return painelTamTexto;
	}

	public void setPainelTamTexto(SimpleDoubleProperty painelTamTexto) {
		this.painelTamTexto = painelTamTexto;
	}

	public SimpleObjectProperty<Color> getPainelCorFundo() {
		return painelCorFundo;
	}

	public void setPainelCorFundo(SimpleObjectProperty<Color> painelCorFundo) {
		this.painelCorFundo = painelCorFundo;
	}

	public SimpleDoubleProperty getPainelOpacidade() {
		return painelOpacidade;
	}

	public void setPainelOpacidade(SimpleDoubleProperty painelOpacidade) {
		this.painelOpacidade = painelOpacidade;
	}

	public SimpleObjectProperty<Color> getBotaoCorTexto() {
		return botaoCorTexto;
	}

	public void setBotaoCorTexto(SimpleObjectProperty<Color> botaoCorTexto) {
		this.botaoCorTexto = botaoCorTexto;
	}

	public SimpleDoubleProperty getBotaoTamTexto() {
		return botaoTamTexto;
	}

	public void setBotaoTamTexto(SimpleDoubleProperty botaoTamTexto) {
		this.botaoTamTexto = botaoTamTexto;
	}

	public SimpleObjectProperty<Color> getBotaoCorFundo() {
		return botaoCorFundo;
	}

	public void setBotaoCorFundo(SimpleObjectProperty<Color> botaoCorFundo) {
		this.botaoCorFundo = botaoCorFundo;
	}

	public SimpleObjectProperty<Color> getBotaoCorBorda() {
		return botaoCorBorda;
	}

	public void setBotaoCorBorda(SimpleObjectProperty<Color> botaoCorBorda) {
		this.botaoCorBorda = botaoCorBorda;
	}

	public SimpleDoubleProperty getBotaoOpacidade() {
		return botaoOpacidade;
	}

	public void setBotaoOpacidade(SimpleDoubleProperty botaoOpacidade) {
		this.botaoOpacidade = botaoOpacidade;
	}

	public SimpleObjectProperty<Color> getCampoCorTitulo() {
		return campoCorTitulo;
	}

	public void setCampoCorTitulo(SimpleObjectProperty<Color> campoCorTitulo) {
		this.campoCorTitulo = campoCorTitulo;
	}

	public SimpleDoubleProperty getCampoTamTitulo() {
		return campoTamTitulo;
	}

	public void setCampoTamTitulo(SimpleDoubleProperty campoTamTitulo) {
		this.campoTamTitulo = campoTamTitulo;
	}

	public SimpleObjectProperty<Color> getCampoCorTexto() {
		return campoCorTexto;
	}

	public void setCampoCorTexto(SimpleObjectProperty<Color> campoCorTexto) {
		this.campoCorTexto = campoCorTexto;
	}

	public SimpleObjectProperty<Color> getCampoCorFundo() {
		return campoCorFundo;
	}

	public void setCampoCorFundo(SimpleObjectProperty<Color> campoCorFundo) {
		this.campoCorFundo = campoCorFundo;
	}

	public SimpleObjectProperty<Color> getCampoCorBorda() {
		return campoCorBorda;
	}

	public void setCampoCorBorda(SimpleObjectProperty<Color> campoCorBorda) {
		this.campoCorBorda = campoCorBorda;
	}

	public SimpleDoubleProperty getCampoTamTexto() {
		return campoTamTexto;
	}

	public void setCampoTamTexto(SimpleDoubleProperty campoTamTexto) {
		this.campoTamTexto = campoTamTexto;
	}

	public SimpleBooleanProperty getCampoTextoNegrito() {
		return campoTextoNegrito;
	}

	public void setCampoTextoNegrito(SimpleBooleanProperty campoTextoNegrito) {
		this.campoTextoNegrito = campoTextoNegrito;
	}

	public SimpleObjectProperty<Color> getFormCorFundo() {
		return formCorFundo;
	}

	public void setFormCorFundo(SimpleObjectProperty<Color> formCorFundo) {
		this.formCorFundo = formCorFundo;
	}

	public SimpleDoubleProperty getFormOpacidade() {
		return formOpacidade;
	}

	public void setFormOpacidade(SimpleDoubleProperty formOpacidade) {
		this.formOpacidade = formOpacidade;
	}

	public SimpleObjectProperty<Color> getReaderCorTituloCampo() {
		return readerCorTituloCampo;
	}

	public void setReaderCorTituloCampo(
			SimpleObjectProperty<Color> readerCorTituloCampo) {
		this.readerCorTituloCampo = readerCorTituloCampo;
	}

	public SimpleDoubleProperty getReaderTamTituloCampo() {
		return readerTamTituloCampo;
	}

	public void setReaderTamTituloCampo(SimpleDoubleProperty readerTamTituloCampo) {
		this.readerTamTituloCampo = readerTamTituloCampo;
	}

	public SimpleObjectProperty<Color> getReaderCorFundo() {
		return readerCorFundo;
	}

	public void setReaderCorFundo(SimpleObjectProperty<Color> readerCorFundo) {
		this.readerCorFundo = readerCorFundo;
	}

	public SimpleDoubleProperty getReaderOpacidade() {
		return readerOpacidade;
	}

	public void setReaderOpacidade(SimpleDoubleProperty readerOpacidade) {
		this.readerOpacidade = readerOpacidade;
	}

	public SimpleObjectProperty<Color> getReaderCorTexto() {
		return readerCorTexto;
	}

	public void setReaderCorTexto(SimpleObjectProperty<Color> readerCorTexto) {
		this.readerCorTexto = readerCorTexto;
	}

	public SimpleDoubleProperty getReaderTamTexto() {
		return readerTamTexto;
	}

	public void setReaderTamTexto(SimpleDoubleProperty readerTamTexto) {
		this.readerTamTexto = readerTamTexto;
	}

	
}
