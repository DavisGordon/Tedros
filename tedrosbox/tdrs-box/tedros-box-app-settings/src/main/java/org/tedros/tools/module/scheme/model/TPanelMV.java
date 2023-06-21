package org.tedros.tools.module.scheme.model;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.style.TStyleResourceValue;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCheckBoxField;
import org.tedros.fx.annotation.control.TColorPickerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TLabelDefaultSetting;
import org.tedros.fx.annotation.control.TSliderField;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TAccordion;
import org.tedros.fx.annotation.layout.TFieldSet;
import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TModelProcess;
import org.tedros.fx.annotation.property.TObservableListProperty;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.scheme.behaviour.TPanelBehavior;
import org.tedros.tools.module.scheme.decorator.TPanelDecorator;
import org.tedros.tools.module.scheme.process.TPanelProcess;
import org.tedros.tools.module.scheme.trigger.TPanelTrigger;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

@TForm(header=ToolsKey.SETTINGS_FORM_NAME, editCssId="y")
@TLabelDefaultSetting(position=TLabelPosition.TOP, textFill="#ffffff")
@TModelProcess(type=TPanelProcess.class)
@TPresenter(type = TDynaPresenter.class, modelClass = TPanel.class,
	behavior=@TBehavior(type = TPanelBehavior.class), 
	decorator=@TDecorator(type=TPanelDecorator.class))
public class TPanelMV extends TModelView<TPanel> {
	
	private SimpleStringProperty display;
	/**
	 * Grupo customizar painel
	 * */
	@TAccordion(node=@TNode(parse = true, 
			styleClass=@TObservableListProperty(addAll="t-accordion", parse=true)),
			panes={
					@TTitledPane(text=ToolsKey.SETTINGS_LAYOUT_PAINEL, fields={"painelCorTexto", "painelCorFundo", "painelTamTexto", "painelOpacidade"}),
					@TTitledPane(text=ToolsKey.SETTINGS_LAYOUT_BUTTONS, fields={"botaoCorTexto", "botaoTamTexto", "botaoCorFundo", "botaoCorBorda","botaoOpacidade"}),
					@TTitledPane(text=ToolsKey.SETTINGS_LAYOUT_FIELDTITLE, fields={"campoCorTitulo", "campoTamTitulo"}),
					@TTitledPane(text=ToolsKey.SETTINGS_LAYOUT_FIELD, fields={"campoCorTexto", "campoCorFundo", "campoCorBorda", "campoTamTexto", "campoTextoNegrito"}),
					@TTitledPane(text=ToolsKey.SETTINGS_LAYOUT_FORM, fields={"formCorFundo", "formOpacidade"}),
					@TTitledPane(text=ToolsKey.SETTINGS_LAYOUT_READER, fields={"readerCorTituloCampo", "readerCorTexto", "readerCorFundo"})
			})
	@TLabel(text=TUsualKey.TEXT, 
		position=TLabelPosition.LEFT, 
		control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> painelCorTexto;
	
	@TLabel(text=TUsualKey.FONT)
	@TSliderField(max = 2, min = 0.8,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty painelTamTexto;
	
	@TLabel(text=TUsualKey.BACKGROUND, 
			position=TLabelPosition.LEFT,
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> painelCorFundo;
	
	@TLabel(text=TUsualKey.OPACITY)
	@TSliderField(max = 1, min = 0,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty painelOpacidade;
	
	/**
	 * Grupo customizar bot�es
	 * */
	@TLabel(text=TUsualKey.TEXT, 
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> botaoCorTexto;
	
	@TLabel(text=TUsualKey.FONT)
	@TSliderField(max = 2, min = 0.8,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty botaoTamTexto;
	
	@TLabel(text=TUsualKey.COLOR,
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> botaoCorFundo;
	
	@TLabel(text=TUsualKey.BORDER, 
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> botaoCorBorda;
	
	@TLabel(text=TUsualKey.OPACITY)
	@TSliderField(max = 1, min = 0,
			majorTickUnit=0.5, blockIncrement=0.01,
					minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty botaoOpacidade;
	
	/**
	 * Grupo customizar titulo dos campos
	 * */
	@TLabel(text=TUsualKey.TEXT, 
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> campoCorTitulo;
	
	@TLabel(text=TUsualKey.FONT)
	@TSliderField(max = 2, min = 0.8,
			majorTickUnit=0.5, blockIncrement=0.01,
			minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true, snapToTicks=false)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty campoTamTitulo;
	
	/**
	 * Grupo customizar campo 
	 * */
	@TLabel(text=TUsualKey.TEXT, 
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> campoCorTexto;
	
	@TLabel(text=TUsualKey.BACKGROUND, 
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80)) 
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> campoCorFundo;
	
	@TLabel(text=TUsualKey.BORDER, 
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> campoCorBorda;
	
	@TLabel(text=TUsualKey.FONT)
	@TSliderField(max = 2, min = 0.8,
	majorTickUnit=0.5, blockIncrement=0.01,
	minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
	showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty campoTamTexto;
	
	@TCheckBoxField(labeled=@TLabeled(text=TUsualKey.BOLD, parse = true))
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleBooleanProperty campoTextoNegrito;
	
	/**
	 * Grupo customizar formulario
	 * */
	@TLabel(text=TUsualKey.COLOR, 
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> formCorFundo;
	
	@TLabel(text=TUsualKey.OPACITY)
	@TSliderField(max = 1, min = 0,
			majorTickUnit=0.5, blockIncrement=0.01,
			minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty formOpacidade;
	
	/**
	 * Grupo customizar titulo do visualizar
	 * */
	// Visualizar - Titulos
	@TLabel(text=TUsualKey.COLOR, 
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	@TFieldSet(fields = { "readerCorTituloCampo", "readerTamTituloCampo" }, 
	legend = "#{label.title}", layoutType=TLayoutType.VBOX)
	private SimpleObjectProperty<Color> readerCorTituloCampo;
	
	@TLabel(text=TUsualKey.FONT)
	@TSliderField(max = 2, min = 0.8,
			majorTickUnit=0.5, blockIncrement=0.01,
			minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty readerTamTituloCampo;
	
	// Visualizar - Texto
	@TLabel(text=TUsualKey.COLOR, 
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	@TFieldSet(fields = { "readerCorTexto", "readerTamTexto" }, 
	legend = TUsualKey.TEXT, layoutType=TLayoutType.VBOX)
	private SimpleObjectProperty<Color> readerCorTexto;
	
	@TLabel(text=TUsualKey.FONT)
	@TSliderField(max = 2, min = 0.8,
			majorTickUnit=0.5, blockIncrement=0.01,
			minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty readerTamTexto;
	
	// visualizar - Fundo
	@TLabel(text=TUsualKey.COLOR, 
			position=TLabelPosition.LEFT, 
			control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TFieldSet(fields = { "readerCorFundo", "readerOpacidade" }, 
	legend = TUsualKey.BACKGROUND, layoutType=TLayoutType.VBOX)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> readerCorFundo;
	
	@TLabel(text=TUsualKey.OPACITY)
	@TSliderField(max = 1, min = 0,
			majorTickUnit=0.5, blockIncrement=0.01,
			minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
			showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TPanelTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty readerOpacidade;
	
	
	public TPanelMV(TPanel entity) {
		super(entity);
		display.setValue("#{label.style}");
		super.formatToString("%s", display);
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

	/**
	 * @return the display
	 */
	public SimpleStringProperty getDisplay() {
		return display;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(SimpleStringProperty display) {
		this.display = display;
	}

	
}
