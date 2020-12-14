package com.covidsemfome.module.report.model;

import java.util.Date;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.module.cozinha.model.CozinhaModelView;
import com.covidsemfome.module.cozinha.process.CozinhaOptionProcess;
import com.covidsemfome.module.report.process.EstoqueReportProcess;
import com.covidsemfome.report.model.EstoqueModel;
import com.covidsemfome.report.model.EstoqueReportModel;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
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
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TLabelPosition;
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


@TForm(name = "Relatório de Estoque", showBreadcrumBar=false)
@TReportProcess(type=EstoqueReportProcess.class, model = EstoqueReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			cleanAction=CleanAction.class, searchAction=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle="Relatório de Estoque"))
@TSecurity(	id="COVSEMFOME_ESTOQREP_FORM", 
			appName = "#{app.name}", moduleName = "Administrativo", viewName = "Relatório de Estoque",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class EstoqueReportModelView extends TModelView<EstoqueReportModel>{
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="Filtros", node=@TNode(id="filtro",parse = true), expanded=true,
							fields={"cozinha","ids","origem","texto2","dataInicio","dataFim"}),
					@TTitledPane(text="Resultado", node=@TNode(id="resultado",parse = true),
						fields={"texto3","result"})})	
	@THBox(	pane=@TPane(children={"cozinha","ids", "origem"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="cozinha", priority=Priority.ALWAYS),
   				   		@TPriority(field="ids", priority=Priority.SOMETIMES),
   				   		@TPriority(field="origem", priority=Priority.SOMETIMES) }))
	@TLabel(text="Cozinha:")
	@TComboBoxField(optionsList=@TOptionsList(entityClass=Cozinha.class, 
	optionModelViewClass=CozinhaModelView.class, optionsProcessClass=CozinhaOptionProcess.class))
	private SimpleObjectProperty<Cozinha> cozinha;
	
	@TLabel(text="Codigo do estoque")
	@TTextField(textInputControl=@TTextInputControl(promptText="Insira os codigos separados por virgula", parse = true))
	private SimpleStringProperty ids;
	
	@TLabel(text="Estradas", position=TLabelPosition.LEFT)
	@THorizontalRadioGroup(alignment=Pos.CENTER_LEFT, spacing=4, 
	radioButtons={
			@TRadioButtonField(text = "Todas", userData = ""),
			@TRadioButtonField(text = "Entradas", userData = "E"),
			@TRadioButtonField(text = "Saidas", userData = "S")
			})
	private SimpleStringProperty origem;
			
	//@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Data ou Periodo:", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
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
	
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Resultado", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
			node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty texto3;
	
	@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="id", text = "Codigo", prefWidth=20, resizable=true), 
					@TTableColumn(cellValue="dataHora", text = "Data/Hora", prefWidth=40, resizable=true),
						@TTableColumn(cellValue="origem", text = "Gerado por", resizable=true), 
						@TTableColumn(cellValue="cozinha", text = "Cozinha",prefWidth=10, resizable=true)
			})
	@TModelViewCollectionType(modelClass=EstoqueModel.class, modelViewClass=EstoqueTableView.class)
	private ITObservableList<EstoqueTableView> result;
	
	
	public EstoqueReportModelView(EstoqueReportModel entidade) {
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
	 * @return the cozinha
	 */
	public SimpleObjectProperty<Cozinha> getCozinha() {
		return cozinha;
	}



	/**
	 * @param cozinha the cozinha to set
	 */
	public void setCozinha(SimpleObjectProperty<Cozinha> cozinha) {
		this.cozinha = cozinha;
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
	 * @return the texto3
	 */
	public SimpleStringProperty getTexto3() {
		return texto3;
	}



	/**
	 * @param texto3 the texto3 to set
	 */
	public void setTexto3(SimpleStringProperty texto3) {
		this.texto3 = texto3;
	}



	/**
	 * @return the result
	 */
	public ITObservableList<EstoqueTableView> getResult() {
		return result;
	}



	/**
	 * @param result the result to set
	 */
	public void setResult(ITObservableList<EstoqueTableView> result) {
		this.result = result;
	}



	/**
	 * @return the origem
	 */
	public SimpleStringProperty getOrigem() {
		return origem;
	}



	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(SimpleStringProperty origem) {
		this.origem = origem;
	}


	
}
