package org.somos.module.report.model;

import java.util.Date;

import org.somos.module.report.process.PessoaReportProcess;
import org.somos.report.model.PessoaModel;
import org.somos.report.model.PessoaReportModel;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellValueFactory;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
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
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.report.behavior.TDataSetReportBehavior;
import com.tedros.fxapi.presenter.report.decorator.TDataSetReportDecorator;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;


@TForm(name = "Pesquisar por pessoas cadastradas", showBreadcrumBar=true, editCssId="")
@TReportProcess(type=PessoaReportProcess.class, model = PessoaReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			action=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle="Relatório de pessoas"))
@TSecurity(	id="SOMOS_PESSREP_FORM", 
			appName = "#{somos.name}", moduleName = "Administrativo", viewName = "Relatório de pessoas",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class PessoaReportModelView extends TModelView<PessoaReportModel>{
	
	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="Filtros", node=@TNode(id="filtro",parse = true), 
							expanded=true, layoutType=TLayoutType.HBOX,
							fields={"nome","detalhado"}),
					@TTitledPane(text="Resultado", node=@TNode(id="resultado",parse = true),
						fields={"result"})})	
	private SimpleStringProperty displayText;
	
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, textInputControl=@TTextInputControl(promptText="#{label.name}", parse = true), 
			node=@TNode(requestFocus=true, parse = true),
			control=@TControl(tooltip="#{label.name}", parse = true))
	@TVBox(	pane=@TPane(children={"nome", "tipoVoluntario", "texto2"}), spacing=10, fillWidth=true)
	private SimpleStringProperty nome;
	
	@TLabel(text="Tipo voluntário")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Operacional", userData="1"), 
					@TRadioButtonField(text="Estratégico", userData="2"),
					@TRadioButtonField(text="Estratégico (Receber emails)", userData="3"),
					@TRadioButtonField(text="Doador/Filatrópico", userData="4"),
					@TRadioButtonField(text="Cadastro/Site", userData="5"),
					@TRadioButtonField(text="Outro", userData="6")
	})
	@THBox(	pane=@TPane(children={"tipoVoluntario","statusVoluntario"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="tipoVoluntario", priority=Priority.ALWAYS),
   				   		@TPriority(field="statusVoluntario", priority=Priority.ALWAYS) }))
	private SimpleStringProperty tipoVoluntario;
	
	@TLabel(text="Situação")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Aguardando", userData="1"), 
					@TRadioButtonField(text="Contactado", userData="2"),
					@TRadioButtonField(text="Voluntário", userData="3"),
					@TRadioButtonField(text="Voluntário Ativo", userData="4"),
					@TRadioButtonField(text="Voluntário problematico", userData="5"),
					@TRadioButtonField(text="Desligado ", userData="6")
	})
	private SimpleStringProperty statusVoluntario;
	
	@TText(text="Data ou Periodo de cadastro:", textAlignment=TextAlignment.LEFT, textStyle = TTextStyle.MEDIUM)
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
	
	@TCheckBoxField(labeled=@TLabeled(text="Relatório detalhado", parse = true))
	@TVBox(	pane=@TPane(children={"detalhado", "listarAcoes", "orderBy"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="detalhado", priority=Priority.NEVER),
						@TPriority(field="listarAcoes", priority=Priority.NEVER),
   				   		@TPriority(field="orderBy", priority=Priority.ALWAYS) }))
	private SimpleBooleanProperty detalhado;
	
	@TCheckBoxField(labeled=@TLabeled(text="Exibir as proximas ações que estiver inscrito", parse = true))
	private SimpleBooleanProperty listarAcoes;
	
	@TLabel(text="Ordenar por:")
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=400, parse = true),
		legend = "Ordenação dos resultados")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Nome", userData="nome"), 
					@TRadioButtonField(text="Data Cadastro", userData="insertDate")
	})
	private SimpleStringProperty orderBy;
	
	@TLabel(text="Ordenar de forma:")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Crescente", userData="asc"), 
					@TRadioButtonField(text="Decrescente", userData="desc")
	})
	private SimpleStringProperty orderType;
	
	@TTableView(editable=true, 
			columns = {@TTableColumn(cellValue="nome", text = "Nome", resizable=true), 
						@TTableColumn(cellValue="tipo", text = "Tipo", resizable=true), 
					@TTableColumn(cellValue="dataCadastro", text = "Data Cadastro ", prefWidth=40, resizable=true,
						cellValueFactory=@TCellValueFactory(parse=true, value=@TCallbackFactory(parse=true, value=PessoaDateCellCallBack.class))),
						@TTableColumn(cellValue="email", text = "Email", resizable=true), 
						@TTableColumn(cellValue="contatos", text = "Contatos", resizable=true)
			})
	@TModelViewType(modelClass=PessoaModel.class, modelViewClass=PessoaModelTableView.class)
	private ITObservableList<PessoaModelTableView> result;
	
	
	public PessoaReportModelView(PessoaReportModel entidade) {
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
	 * @return the tipoVoluntario
	 */
	public SimpleStringProperty getTipoVoluntario() {
		return tipoVoluntario;
	}


	/**
	 * @param tipoVoluntario the tipoVoluntario to set
	 */
	public void setTipoVoluntario(SimpleStringProperty tipoVoluntario) {
		this.tipoVoluntario = tipoVoluntario;
	}


	/**
	 * @return the statusVoluntario
	 */
	public SimpleStringProperty getStatusVoluntario() {
		return statusVoluntario;
	}


	/**
	 * @param statusVoluntario the statusVoluntario to set
	 */
	public void setStatusVoluntario(SimpleStringProperty statusVoluntario) {
		this.statusVoluntario = statusVoluntario;
	}


	/**
	 * @return the result
	 */
	public ITObservableList<PessoaModelTableView> getResult() {
		return result;
	}


	/**
	 * @param result the result to set
	 */
	public void setResult(ITObservableList<PessoaModelTableView> result) {
		this.result = result;
	}


	/**
	 * @return the listarAcoes
	 */
	public SimpleBooleanProperty getListarAcoes() {
		return listarAcoes;
	}


	/**
	 * @param listarAcoes the listarAcoes to set
	 */
	public void setListarAcoes(SimpleBooleanProperty listarAcoes) {
		this.listarAcoes = listarAcoes;
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
	 * @return the detalhado
	 */
	public SimpleBooleanProperty getDetalhado() {
		return detalhado;
	}


	/**
	 * @param detalhado the detalhado to set
	 */
	public void setDetalhado(SimpleBooleanProperty detalhado) {
		this.detalhado = detalhado;
	}

	

	
}
