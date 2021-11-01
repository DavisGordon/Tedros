package com.covidsemfome.module.report.model;

import java.util.Date;

import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.module.report.process.VoluntarioReportProcess;
import com.covidsemfome.module.tipoAjuda.model.TipoAjudaFindModelView;
import com.covidsemfome.module.tipoAjuda.model.TipoAjudaModelView;
import com.covidsemfome.report.model.VoluntarioItemModel;
import com.covidsemfome.report.model.VoluntarioReportModel;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellValueFactory;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TMultipleSelectionModal;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.TFieldSet;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TReportProcess;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.report.behavior.TDataSetReportBehavior;
import com.tedros.fxapi.presenter.report.decorator.TDataSetReportDecorator;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;


@TForm(name = "Pesquisar por voluntários inscritos nas ações", 
showBreadcrumBar=true, editCssId="")
@TReportProcess(type=VoluntarioReportProcess.class, model = VoluntarioReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			action=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle="Relatório de Voluntários inscritos"))
@TSecurity(	id="COVSEMFOME_VOLUNREP_FORM", 
			appName = "#{app.name}", moduleName = "Administrativo", viewName = "Relatório de Voluntários inscritos",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class VoluntariosReportModelView extends TModelView<VoluntarioReportModel>{
	
	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="Filtros", node=@TNode(id="filtro",parse = true), expanded=true,
							layoutType=TLayoutType.HBOX,
							fields={"ids", "tiposAjuda"}),
					@TTitledPane(text="Resultado", node=@TNode(id="resultado",parse = true),
						fields={"result"})})	
	private SimpleStringProperty displayText;
	
	@TLabel(text="Codigo da Ação")
	@TTextField(node=@TNode(requestFocus=true, parse = true), 
	textInputControl=@TTextInputControl(promptText="Insira os codigos da ação separados por virgula", parse = true))
	@TVBox(	pane=@TPane(children={"ids", "titulo", "status", "nome", "texto2"}), spacing=10, fillWidth=true)
	private SimpleStringProperty ids;
	
	@TLabel(text="Titulo / Local")
	@TTextField(textInputControl=@TTextInputControl(promptText="Insira parte ou o titulo completo da ação", parse = true))
	private SimpleStringProperty titulo;
	
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.CENTER_LEFT, spacing=4, 
			radioButtons={
					@TRadioButtonField(text = "Nenhum", userData = ""),
					@TRadioButtonField(text = "Agendada", userData = "Agendada"),
					@TRadioButtonField(text = "Cancelada", userData = "Cancelada"), 
					@TRadioButtonField(text = "Executada", userData = "Executada")
					})
	private SimpleStringProperty status;
	
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, textInputControl=@TTextInputControl(promptText="#{label.name}", parse = true), 
			control=@TControl(tooltip="#{label.name}", parse = true))
	private SimpleStringProperty nome;
	
	@TText(text="Data ou Periodo da ação:", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.MEDIUM)
	@THBox(	pane=@TPane(children={"texto2","dataInicio","dataFim"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="texto2", priority=Priority.NEVER),
			@TPriority(field="dataInicio", priority=Priority.NEVER),
   				   		@TPriority(field="dataFim", priority=Priority.NEVER) }))
	private SimpleStringProperty texto2;
	
	@TLabel(text="De")
	@TDatePickerField
	private SimpleObjectProperty<Date> dataInicio;
	
	@TLabel(text="Ate")
	@TDatePickerField
	private SimpleObjectProperty<Date> dataFim;
	
	@TLabel(text="Tipos de Ajuda")
	@TMultipleSelectionModal(modelClass = TipoAjuda.class, modelViewClass = TipoAjudaFindModelView.class, width=350)
	@TVBox(	pane=@TPane(children={"tiposAjuda", "orderBy"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="tiposAjuda", priority=Priority.ALWAYS),
   				   		@TPriority(field="orderBy", priority=Priority.NEVER) }))
	@TModelViewCollectionType(modelClass=TipoAjuda.class, modelViewClass=TipoAjudaModelView.class)
	private ITObservableList<TipoAjudaModelView> tiposAjuda;
	
	@TLabel(text="Ordenar por:")
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend = "Ordenação dos resultados")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Codigo/Ação", userData="a.id"), 
		@TRadioButtonField(text="Data/Ação", userData="a.data"), 
		@TRadioButtonField(text="Ação", userData="a.titulo"), 
		@TRadioButtonField(text="Nome", userData="p.nome")
	})
	private SimpleStringProperty orderBy;
	
	@TLabel(text="Ordenar de forma:")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Crescente", userData="asc"), 
					@TRadioButtonField(text="Decrescente", userData="desc")
	})
	private SimpleStringProperty orderType;
	
	@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="id", text = "Codigo", prefWidth=20, resizable=true), 
					@TTableColumn(cellValue="data", text = "Data/Hora", prefWidth=40, resizable=true,
						cellValueFactory=@TCellValueFactory(parse=true, value=@TCallbackFactory(parse=true, value=VoluntarioDateCellCallBack.class))),
						@TTableColumn(cellValue="titulo", text = "Titulo / Local", resizable=true), 
						@TTableColumn(cellValue="nome", text = "Nome", resizable=true), 
						@TTableColumn(cellValue="tiposAjuda", text = "Tipo Ajuda", resizable=true), 
						@TTableColumn(cellValue="email", text = "Email", resizable=true), 
						@TTableColumn(cellValue="contatos", text = "Contatos", resizable=true)
			})
	@TModelViewCollectionType(modelClass=VoluntarioItemModel.class, modelViewClass=VoluntarioItemTableView.class)
	private ITObservableList<VoluntarioItemTableView> result;
	
	
	public VoluntariosReportModelView(VoluntarioReportModel entidade) {
		super(entidade);
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
		return this.displayText;
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
	 * @return the ids
	 */
	public SimpleStringProperty getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(SimpleStringProperty ids) {
		this.ids = ids;
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
	 * @return the texto2
	 */
	public SimpleStringProperty getTexto2() {
		return texto2;
	}

	/**
	 * @param texto2 the texto2 to set
	 */
	public void setTexto2(SimpleStringProperty texto2) {
		this.texto2 = texto2;
	}

	/**
	 * @return the dataInicio
	 */
	public SimpleObjectProperty<Date> getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(SimpleObjectProperty<Date> dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public SimpleObjectProperty<Date> getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(SimpleObjectProperty<Date> dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the result
	 */
	public ITObservableList<VoluntarioItemTableView> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(ITObservableList<VoluntarioItemTableView> result) {
		this.result = result;
	}


	/**
	 * @return the orderBy
	 */
	public SimpleStringProperty getOrderBy() {
		return orderBy;
	}


	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(SimpleStringProperty orderBy) {
		this.orderBy = orderBy;
	}


	/**
	 * @return the orderType
	 */
	public SimpleStringProperty getOrderType() {
		return orderType;
	}


	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(SimpleStringProperty orderType) {
		this.orderType = orderType;
	}


	/**
	 * @return the nome
	 */
	public SimpleStringProperty getNome() {
		return nome;
	}


	/**
	 * @param nome the nome to set
	 */
	public void setNome(SimpleStringProperty nome) {
		this.nome = nome;
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


	
}
