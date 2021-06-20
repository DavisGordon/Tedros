package com.covidsemfome.module.report.model;

import java.util.Date;

import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.module.report.process.DoacaoReportProcess;
import com.covidsemfome.module.voluntario.model.TipoAjudaModelView;
import com.covidsemfome.report.model.DoacaoItemModel;
import com.covidsemfome.report.model.DoacaoReportModel;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellValueFactory;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
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
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.report.behavior.TDataSetReportBehavior;
import com.tedros.fxapi.presenter.report.decorator.TDataSetReportDecorator;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;


@TForm(name = "Relatório de Doações", showBreadcrumBar=false)
@TReportProcess(type=DoacaoReportProcess.class, model = DoacaoReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			searchAction=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle="Relatório de Doações"))
@TSecurity(	id="COVSEMFOME_DOACAOREP_FORM", 
			appName = "#{app.name}", moduleName = "Administrativo", viewName = "Relatório de Doações",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class DoacaoReportModelView extends TModelView<DoacaoReportModel>{
	
	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="Filtros", node=@TNode(id="filtro",parse = true), expanded=true,
							fields={"nome","texto2","tiposAjuda"}),
					@TTitledPane(text="Resultado", node=@TNode(id="resultado",parse = true),
						fields={"result"})})
	private SimpleStringProperty displayText;
	
	
	@TLabel(text="Pessoa")
	@TTextField(textInputControl=@TTextInputControl(promptText="Insira parte ou o nome completo da pessoa", parse = true))
	@THBox(	pane=@TPane(children={"nome","acaoId"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="nome", priority=Priority.ALWAYS),
   				   		@TPriority(field="acaoId", priority=Priority.ALWAYS) }))
	private SimpleStringProperty nome;
	
	@TLabel(text="Codigo da Ação")
	@TTextField(textInputControl=@TTextInputControl(promptText="Insira os codigos separados por virgula", parse = true))
	private SimpleStringProperty acaoId;
	
	@TText(text="Data ou Periodo da doação:", textAlignment=TextAlignment.LEFT, 
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
	
	@TPickListField(selectedLabel="#{label.selected}", 
			sourceLabel="Tipo Ajuda", required=false,
			optionsList=@TOptionsList(entityClass=TipoAjuda.class,
						optionModelViewClass=TipoAjudaModelView.class, serviceName = "ITipoAjudaControllerRemote"))
	@TModelViewCollectionType(modelClass=TipoAjuda.class, modelViewClass=TipoAjudaModelView.class, required=false)
	private ITObservableList<TipoAjudaModelView> tiposAjuda;
	
	
	@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="data", text = "Data", prefWidth=30, resizable=true,
						cellValueFactory=@TCellValueFactory(parse=true, value=@TCallbackFactory(parse=true, value=DoacaoDateCellCallBack.class))),
						@TTableColumn(cellValue="tipoAjuda", text = "Tipo Ajuda", prefWidth=100, resizable=true), 
						@TTableColumn(cellValue="quantidade", text = "Qtd.",prefWidth=10, resizable=true), 
						@TTableColumn(cellValue="valor", text = "Valor", prefWidth=30, resizable=true),
						@TTableColumn(cellValue="pessoa", text = "Doador", resizable=true), 
						@TTableColumn(cellValue="acao", text = "Acão", resizable=true)
			})
	@TModelViewCollectionType(modelClass=DoacaoItemModel.class, modelViewClass=DoacaoItemTableView.class)
	private ITObservableList<DoacaoItemTableView> result;
	
	
	public DoacaoReportModelView(DoacaoReportModel entidade) {
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
	 * @return the acaoId
	 */
	public SimpleStringProperty getAcaoId() {
		return acaoId;
	}

	/**
	 * @param acaoId the acaoId to set
	 */
	public void setAcaoId(SimpleStringProperty acaoId) {
		this.acaoId = acaoId;
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
	 * @return the result
	 */
	public ITObservableList<DoacaoItemTableView> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(ITObservableList<DoacaoItemTableView> result) {
		this.result = result;
	}


	
}
