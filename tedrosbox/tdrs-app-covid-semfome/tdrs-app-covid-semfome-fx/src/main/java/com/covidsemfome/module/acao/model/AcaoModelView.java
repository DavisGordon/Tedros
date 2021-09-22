/**
 * 
 */
package com.covidsemfome.module.acao.model;

import java.util.Date;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.model.Voluntario;
import com.covidsemfome.module.acao.process.AcaoProcess;
import com.covidsemfome.module.voluntario.model.TipoAjudaFindModelView;
import com.covidsemfome.module.voluntario.model.TipoAjudaModelView;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TMultipleSelectionModal;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.reader.TColumnReader;
import com.tedros.fxapi.annotation.reader.TDetailReaderHtml;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTableReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.util.TDateUtil;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Ação a ser exibida no site", showBreadcrumBar=false)
@TEntityProcess(process = AcaoProcess.class, entity=Acao.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = Acao.class, serviceName = "IAcaoControllerRemote",
		show=true, showSearchField=true, searchFieldName="titulo",
		orderBy = {@TOption(text = "Codigo", value = "id"), 
					@TOption(text = "Titulo", value = "titulo"), 
					@TOption(text = "Data", value = "data")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Ação / Campanha")))
@TSecurity(	id="COVSEMFOME_SITEACAO_FORM", 
	appName = "#{app.name}", moduleName = "Gerenciar Campanha", viewName = "Ação / Campanha",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class AcaoModelView extends TEntityModelView<Acao> {
	
	@TReaderHtml
	@TLabel(text="Codigo:", position=TLabelPosition.LEFT)
	@TShowField
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-hsplit-first", parse = true))
	@THBox(	pane=@TPane(children={"id","textoCadastro"}), spacing=4, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="id", priority=Priority.NEVER), 
   				   		@TPriority(field="textoCadastro", priority=Priority.ALWAYS) }))
	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Ação / Campanha", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TText(text="As ações com status 'Agendada' serão exibidas no site e no painel do voluntário, "
			+ "as 'Programadas' não serão exibidas, porem, quando estiver faltando 7 dias para a "
			+ "execução desta um e-mail solicitando a alteração do status da ação será enviado para "
			+ "os Voluntários Operacionais que possuem permissão para receber e-mails.", 
	textAlignment=TextAlignment.LEFT, wrappingWidth=750,
	textStyle = TTextStyle.MEDIUM)
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-hsplit-last", parse = true))
	private SimpleStringProperty textoCadastro;
	
	/*@TAccordion(expandedPane="dados", node=@TNode(id="acaoacc",parse = true),
			panes={
					@TTitledPane(text="Dados da Ação", node=@TNode(id="dados",parse = true), 
							expanded=true, layoutType=TLayoutType.HBOX,
							fields={"titulo", "tiposAjuda"}),
					@TTitledPane(text="Voluntários inscritos", node=@TNode(id="voluntarios",parse = true),
						fields={"voluntarios"})})
	*/
	@TTabPane(tabs = { @TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"titulo", "tiposAjuda"}, orientation=Orientation.HORIZONTAL)), text = "Detalhes"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"voluntarios"})), text = "Voluntários inscritos")
	})
	private SimpleStringProperty displayText = new SimpleStringProperty();
	
	
	@TReaderHtml
	@TLabel(text="Titulo/Local")
	@TTextField(maxLength=100, node=@TNode(requestFocus=true, parse = true),
	textInputControl=@TTextInputControl(promptText="Insira um titulo ou o local", parse = true),
	required=true)
	@TVBox(pane=@TPane(children={"titulo", "data","status","qtdMinVoluntarios","vlrPrevisto"}), spacing=10)
	private SimpleStringProperty titulo;
	
	@TReaderHtml
	@TLabel(text="Data e Hora")
	@TDatePickerField(required = true, dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> data;
	
	@TLabel(text="Status")
	@TReaderHtml(codeValues={@TCodeValue(code = "Programada", value = "Programada"),
			@TCodeValue(code = "Agendada", value = "Agendada"), 
			@TCodeValue(code = "Cancelada", value = "Cancelada"),
			@TCodeValue(code = "Executada", value = "Executada")})
	@THorizontalRadioGroup(required=true, alignment=Pos.CENTER_LEFT, spacing=4, 
	radioButtons={
			@TRadioButtonField(text = "Programada", userData = "Programada"),
			@TRadioButtonField(text = "Agendada", userData = "Agendada"),
			@TRadioButtonField(text = "Cancelada", userData = "Cancelada"), 
			@TRadioButtonField(text = "Executada", userData = "Executada")
			})
	private SimpleStringProperty status;
	
	@TReaderHtml
	@TLabel(text="Qtd. Minima de voluntários")
	@TNumberSpinnerField(maxValue = 150)
	@THBox(	pane=@TPane(children={"qtdMinVoluntarios","qtdMaxVoluntarios"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="qtdMinVoluntarios", priority=Priority.NEVER), 
   				   		@TPriority(field="qtdMaxVoluntarios", priority=Priority.NEVER) }))
	private SimpleIntegerProperty qtdMinVoluntarios;
	
	@TReaderHtml
	@TLabel(text="Qtd. Maxima de voluntários")
	@TNumberSpinnerField(maxValue = 150)
	private SimpleIntegerProperty qtdMaxVoluntarios;
	
	@TReaderHtml
	@TLabel(text = "Valor previsto")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="Valor previsto", parse = true))
	@THBox(	pane=@TPane(children={"vlrPrevisto","vlrArrecadado","vlrExecutado"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="vlrPrevisto", priority=Priority.NEVER), 
   				   		@TPriority(field="vlrArrecadado", priority=Priority.NEVER),
   				   		@TPriority(field="vlrExecutado", priority=Priority.NEVER) }))
	private SimpleDoubleProperty vlrPrevisto;
	
	@TReaderHtml
	@TLabel(text = "Valor arrecadado")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="Valor arrecadado", parse = true))
	private SimpleDoubleProperty vlrArrecadado;
	
	@TReaderHtml
	@TLabel(text = "Valor executado")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="Valor executado", parse = true))
	private SimpleDoubleProperty vlrExecutado;
	
	@TLabel(text="Tipos de Ajuda")
	@TTableReaderHtml(label=@TLabel(text="Tipo de Ajuda:"), 
		column = { 	@TColumnReader(field = "descricao", name = "Descricao"), 
					@TColumnReader(field = "tipoPessoa", name = "Tipo pessoa")})
	@TMultipleSelectionModal(modelClass = TipoAjuda.class, modelViewClass = TipoAjudaFindModelView.class, 
	width=350, required=false)
	@TVBox(	pane=@TPane(children={"tiposAjuda", "descricao"}), spacing=10)
	@TModelViewCollectionType(modelClass=TipoAjuda.class, modelViewClass=TipoAjudaModelView.class, required=false)
	private ITObservableList<TipoAjudaModelView> tiposAjuda;
	
	@TReaderHtml
	@TLabel(text="Descricão")
	@TTextAreaField(required=true, maxLength=2000, wrapText=true,
	control=@TControl(maxWidth=300,prefHeight=100, parse = true))
	@THBox(	pane=@TPane(children={"descricao","observacao"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="descricao", priority=Priority.ALWAYS), 
   				   		@TPriority(field="observacao", priority=Priority.NEVER) }))
	private SimpleStringProperty descricao;
	
	@TReaderHtml
	@TLabel(text="Observação")
	@TTextAreaField(maxLength=600, wrapText=true,
	control=@TControl(prefWidth=250, prefHeight=100, parse = true))
	private SimpleStringProperty observacao;
	
	@TFieldBox(node=@TNode(id="volinscr", parse = true))
	@TDetailReaderHtml(label=@TLabel(text="Voluntários"), entityClass=Voluntario.class, modelViewClass=VoluntarioDetailView.class)
	@TDetailListField(entityModelViewClass = VoluntarioDetailView.class, entityClass = Voluntario.class)
	@TModelViewCollectionType(modelClass=Voluntario.class, modelViewClass=VoluntarioDetailView.class)
	private ITObservableList<VoluntarioDetailView> voluntarios;

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
			String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
					+ (titulo.getValue()!=null ? titulo.getValue() : "") 
					+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
			displayText.setValue(str);
		}
	}
	
	private void buildListener() {
		
		ChangeListener<Number> idListener = super.getListenerRepository().get("displayText");
		if(idListener==null){
			idListener = (arg0, arg1, arg2) -> {
					String str = (arg2==null ? "" : "(ID: "+arg2.toString()+") " ) 
							+ (titulo.getValue()!=null ? titulo.getValue() : "") 
							+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
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
							+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
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
							+ (arg2!=null ? " em "+formataDataHora(arg2) : "");
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

	/**
	 * @return the tiposAjuda
	 */
	public ITObservableList<TipoAjudaModelView> getTiposAjuda() {
		return tiposAjuda;
	}

	/**
	 * @param tiposAjuda the tiposAjuda to set
	 */
	public void setTiposAjuda(ITObservableList<TipoAjudaModelView> tiposAjuda) {
		this.tiposAjuda = tiposAjuda;
	}

	/**
	 * @return the voluntarios
	 */
	public ITObservableList<VoluntarioDetailView> getVoluntarios() {
		return voluntarios;
	}

	/**
	 * @param voluntarios the voluntarios to set
	 */
	public void setVoluntarios(ITObservableList<VoluntarioDetailView> voluntarios) {
		this.voluntarios = voluntarios;
	}



}
