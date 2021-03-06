/**
 * 
 */
package com.solidarity.module.acao.model;

import java.util.Date;

import com.solidarity.model.Acao;
import com.solidarity.module.acao.process.AcaoProcess;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.util.TDateUtil;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "#{form.campaign.name}", showBreadcrumBar=false)
@TEntityProcess(process = AcaoProcess.class, entity=Acao.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = Acao.class, serviceName = "IAcaoControllerRemote",
		show=true, showSearchField=true, searchFieldName="titulo",
		orderBy = {@TOption(text = "#{label.codigo}", value = "id"), 
					@TOption(text = "#{label.titulo}", value = "titulo"), 
					@TOption(text = "#{label.data}", value = "data")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{form.campaign.name}")))
@TSecurity(	id="SOLIDARITY_SITEACAO_FORM", 
	appName = "#{app.name}", moduleName = "#{module.manage.campaign}", viewName = "#{form.campaign.name}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class AcaoModelView extends TEntityModelView<Acao> {
	
	@TReaderHtml
	@TLabel(text="#{label.codigo}:", position=TLabelPosition.LEFT)
	@TShowField()
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText = new SimpleStringProperty();
	
	@TTextReaderHtml(text="#{form.campaign.name}", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{form.campaign.name}", textAlignment=TextAlignment.LEFT, textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml
	@TLabel(text="#{label.titulo.local}")
	@TTextField(maxLength=100, node=@TNode(requestFocus=true, parse = true),
	textInputControl=@TTextInputControl(promptText="#{prompt.titulo.local}", parse = true),
	required=true)
	private SimpleStringProperty titulo;
	
	@TReaderHtml
	@TLabel(text="#{label.dataHora}")
	@TDatePickerField(required = true, dateFormat=DateTimeFormatBuilder.class)
	@THBox(	pane=@TPane(children={"data","status"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="data", priority=Priority.NEVER), 
   				   		@TPriority(field="status", priority=Priority.ALWAYS) }))
	private SimpleObjectProperty<Date> data;
	
	@TLabel(text="#{label.status}")
	@TReaderHtml(codeValues={@TCodeValue(code = "Agendada", value = "#{label.agendada}"), 
			@TCodeValue(code = "Cancelada", value = "#{label.cancelada}"),
			@TCodeValue(code = "Executada", value = "#{label.executada}")})
	@THorizontalRadioGroup(required=true, alignment=Pos.CENTER_LEFT, spacing=4, 
	radioButtons={
			@TRadioButtonField(text = "#{label.agendada}", userData = "Agendada"),
			@TRadioButtonField(text = "#{label.cancelada}", userData = "Cancelada"), 
			@TRadioButtonField(text = "#{label.executada}", userData = "Executada")
			})
	private SimpleStringProperty status;
	
	@TReaderHtml
	@TLabel(text="#{label.qtd.min.vol}")
	@TNumberSpinnerField(maxValue = 150)
	@THBox(	pane=@TPane(children={"qtdMinVoluntarios","qtdMaxVoluntarios"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="qtdMinVoluntarios", priority=Priority.NEVER), 
   				   		@TPriority(field="qtdMaxVoluntarios", priority=Priority.NEVER) }))
	private SimpleIntegerProperty qtdMinVoluntarios;
	
	@TReaderHtml
	@TLabel(text="#{label.qtd.max.vol}")
	@TNumberSpinnerField(maxValue = 150)
	private SimpleIntegerProperty qtdMaxVoluntarios;
	
	@TReaderHtml
	@TLabel(text = "#{label.valor.previsto}")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="#{label.valor.previsto}", parse = true))
	@THBox(	pane=@TPane(children={"vlrPrevisto","vlrArrecadado","vlrExecutado"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="vlrPrevisto", priority=Priority.NEVER), 
   				   		@TPriority(field="vlrArrecadado", priority=Priority.NEVER),
   				   		@TPriority(field="vlrExecutado", priority=Priority.NEVER) }))
	private SimpleDoubleProperty vlrPrevisto;
	
	@TReaderHtml
	@TLabel(text = "#{label.valor.arrecadado}")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="#{label.valor.arrecadado}", parse = true))
	private SimpleDoubleProperty vlrArrecadado;
	
	@TReaderHtml
	@TLabel(text = "#{label.valor.executado}")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="#{label.valor.executado}", parse = true))
	private SimpleDoubleProperty vlrExecutado;
	
	@TReaderHtml
	@TLabel(text="#{label.descricao}")
	@TTextAreaField(required=true, maxLength=2000, wrapText=true,
	control=@TControl(prefWidth=250, prefHeight=150, parse = true))
	private SimpleStringProperty descricao;
	
	@TReaderHtml
	@TLabel(text="#{label.observacao}")
	@TTextAreaField(maxLength=600, wrapText=true,
	control=@TControl(prefWidth=250, prefHeight=100, parse = true))
	private SimpleStringProperty observacao;
	
	

	public AcaoModelView(Acao entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
	}
	
	@Override
	public void reload(Acao model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(Acao model) {
		if(!model.isNew()){
			String em = " "+ TInternationalizationEngine.getInstance(null).getString("#{label.em}")+" ";
			String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
					+ (titulo.getValue()!=null ? titulo.getValue() : "") 
					+ (data.getValue()!=null ?  em + formataDataHora(data.getValue()) : "");
			displayText.setValue(str);
		}
	}
	
	private void buildListener() {
		String em = " "+ TInternationalizationEngine.getInstance(null).getString("#{label.em}")+" ";
		ChangeListener<Number> idListener = super.getListenerRepository().get("displayText");
		if(idListener==null){
			idListener = (arg0, arg1, arg2) -> {
					String str = (arg2==null ? "" : "(ID: "+arg2.toString()+") " ) 
							+ (titulo.getValue()!=null ? titulo.getValue() : "") 
							+ (data.getValue()!=null ?  em + formataDataHora(data.getValue()) : "");
					displayText.setValue(str);
				};
			
			super.addListener("displayText", idListener);
		}else
			id.removeListener(idListener);
		
		id.addListener(idListener);
		
		ChangeListener<String> tituloListener = super.getListenerRepository().get("displayText1");
		if(tituloListener==null){
			tituloListener = (arg0, arg1, arg2) -> {
					String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
							+ (arg2!=null ? arg2 : "") 
							+ (data.getValue()!=null ?  em + formataDataHora(data.getValue()) : "");
					displayText.setValue(str);
				};
			super.addListener("displayText1", tituloListener);
		}else
			titulo.removeListener(tituloListener);
		
		titulo.addListener(tituloListener);
		
		ChangeListener<Date> dataListener = super.getListenerRepository().get("displayText2");
		if(dataListener==null){
			dataListener = (arg0, arg1, arg2) -> {
					String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
							+ (titulo.getValue()!=null ? titulo.getValue() : "") 
							+ (arg2!=null ?  em + formataDataHora(arg2) : "");
					displayText.setValue(str);
				};
				
			super.addListener("displayText2", dataListener);
		}else
			data.removeListener(dataListener);
		
		data.addListener(dataListener);
		
	}
	
	private String formataDataHora(Date data){
		return TDateUtil.getFormatedDate(data, TDateUtil.DDMMYYYY);
	}
	
	@Override
	public void removeAllListener() {
		super.removeAllListener();
	}
	
	public AcaoModelView() {
		super(new Acao());
	}

	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
		
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return displayText;
	}

	
	
	public AcaoModelView getNewInstance(){
		return new AcaoModelView(new Acao());
	}
	
	@Override
	public String toString() {
		return (getTitulo()!=null)? getTitulo().getValue() : "";	
	}

	/**
	 * @return the titulo
	 */
	public SimpleStringProperty getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(SimpleStringProperty titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the descricao
	 */
	public SimpleStringProperty getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(SimpleStringProperty descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the data
	 */
	public SimpleObjectProperty<Date> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(SimpleObjectProperty<Date> data) {
		this.data = data;
	}

	/**
	 * @return the status
	 */
	public SimpleStringProperty getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}

	/**
	 * @return the observacao
	 */
	public SimpleStringProperty getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(SimpleStringProperty observacao) {
		this.observacao = observacao;
	}

	/**
	 * @return the qtdMinVoluntarios
	 */
	public SimpleIntegerProperty getQtdMinVoluntarios() {
		return qtdMinVoluntarios;
	}

	/**
	 * @param qtdMinVoluntarios the qtdMinVoluntarios to set
	 */
	public void setQtdMinVoluntarios(SimpleIntegerProperty qtdMinVoluntarios) {
		this.qtdMinVoluntarios = qtdMinVoluntarios;
	}

	/**
	 * @return the qtdMaxVoluntarios
	 */
	public SimpleIntegerProperty getQtdMaxVoluntarios() {
		return qtdMaxVoluntarios;
	}

	/**
	 * @param qtdMaxVoluntarios the qtdMaxVoluntarios to set
	 */
	public void setQtdMaxVoluntarios(SimpleIntegerProperty qtdMaxVoluntarios) {
		this.qtdMaxVoluntarios = qtdMaxVoluntarios;
	}

	/**
	 * @return the vlrPrevisto
	 */
	public SimpleDoubleProperty getVlrPrevisto() {
		return vlrPrevisto;
	}

	/**
	 * @param vlrPrevisto the vlrPrevisto to set
	 */
	public void setVlrPrevisto(SimpleDoubleProperty vlrPrevisto) {
		this.vlrPrevisto = vlrPrevisto;
	}

	/**
	 * @return the vlrArrecadado
	 */
	public SimpleDoubleProperty getVlrArrecadado() {
		return vlrArrecadado;
	}

	/**
	 * @param vlrArrecadado the vlrArrecadado to set
	 */
	public void setVlrArrecadado(SimpleDoubleProperty vlrArrecadado) {
		this.vlrArrecadado = vlrArrecadado;
	}

	/**
	 * @return the vlrExecutado
	 */
	public SimpleDoubleProperty getVlrExecutado() {
		return vlrExecutado;
	}

	/**
	 * @param vlrExecutado the vlrExecutado to set
	 */
	public void setVlrExecutado(SimpleDoubleProperty vlrExecutado) {
		this.vlrExecutado = vlrExecutado;
	}

	/**
	 * @return the textoCadastro
	 */
	public SimpleStringProperty getTextoCadastro() {
		return textoCadastro;
	}

	/**
	 * @param textoCadastro the textoCadastro to set
	 */
	public void setTextoCadastro(SimpleStringProperty textoCadastro) {
		this.textoCadastro = textoCadastro;
	}

	/**
	 * @return the displayText
	 */
	public SimpleStringProperty getDisplayText() {
		return displayText;
	}

	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(SimpleStringProperty displayText) {
		this.displayText = displayText;
	}


}
