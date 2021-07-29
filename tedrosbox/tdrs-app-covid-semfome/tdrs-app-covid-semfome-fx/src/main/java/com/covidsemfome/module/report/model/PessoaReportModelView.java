package com.covidsemfome.module.report.model;

import com.covidsemfome.module.report.process.PessoaReportProcess;
import com.covidsemfome.report.model.PessoaModel;
import com.covidsemfome.report.model.PessoaReportModel;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TReportProcess;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.report.behavior.TDataSetReportBehavior;
import com.tedros.fxapi.presenter.report.decorator.TDataSetReportDecorator;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;


@TForm(name = "Relatório de pessoas", showBreadcrumBar=false, editCssId="")
@TReportProcess(type=PessoaReportProcess.class, model = PessoaReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			searchAction=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle="Relatório de pessoas"))
@TSecurity(	id="COVSEMFOME_PESSREP_FORM", 
			appName = "#{app.name}", moduleName = "Administrativo", viewName = "Relatório de pessoas",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class PessoaReportModelView extends TModelView<PessoaReportModel>{
	
	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="Filtros", node=@TNode(id="filtro",parse = true), expanded=true,
							fields={"nome","tipoVoluntario","listarAcoes"}),
					@TTitledPane(text="Resultado", node=@TNode(id="resultado",parse = true),
						fields={"result"})})	
	private SimpleStringProperty displayText;
	
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, textInputControl=@TTextInputControl(promptText="#{label.name}", parse = true), 
			node=@TNode(requestFocus=true, parse = true),
			control=@TControl(tooltip="#{label.name}", parse = true))
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
	
	@TReaderHtml
	//@TLabel(text="Listar proximas ações:", position=TLabelPosition.LEFT)
	@TCheckBoxField(labeled=@TLabeled(text="Exibir as proximas ações que estiver inscrito", parse = true))
	private SimpleBooleanProperty listarAcoes;
	
	@TTableView(editable=true, 
			columns = {@TTableColumn(cellValue="nome", text = "Nome", resizable=true), 
						@TTableColumn(cellValue="tipo", text = "Tipo", resizable=true), 
						@TTableColumn(cellValue="email", text = "Email", resizable=true), 
						@TTableColumn(cellValue="contatos", text = "Contatos", resizable=true)
			})
	@TModelViewCollectionType(modelClass=PessoaModel.class, modelViewClass=PessoaModelTableView.class)
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

	

	
}
