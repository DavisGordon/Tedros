package com.covidsemfome.module.report.model;

import java.util.Date;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.module.report.process.DoacaoReportProcess;
import com.covidsemfome.module.voluntario.model.TipoAjudaModelView;
import com.covidsemfome.module.voluntario.process.LoadTipoAjudaOptionListProcess;
import com.covidsemfome.report.model.DoacaoItemModel;
import com.covidsemfome.report.model.DoacaoReportModel;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TObservableValue;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
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
import com.tedros.fxapi.annotation.property.TObjectProperty;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
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


@TForm(name = "Relatório de Doações", showBreadcrumBar=false)
@TReportProcess(type=DoacaoReportProcess.class, model = DoacaoReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			cleanAction=CleanAction.class, searchAction=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle="Relatório de Doações"))
@TSecurity(	id="COVSEMFOME_DOACAOREP_FORM", 
			appName = "#{app.name}", moduleName = "Relatórios", viewName = "Relatório de Doações",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class DoacaoReportModelView extends TModelView<DoacaoReportModel>{
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="Filtros", node=@TNode(id="filtro",parse = true), expanded=true,
							fields={"texto1","nome","acaoId","texto2","dataInicio","dataFim","tiposAjuda"}),
					@TTitledPane(text="Resultado", node=@TNode(id="resultado",parse = true),
						fields={"texto3","result"})})
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Pessoa e Ação", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty texto1;
	
	@TLabel(text="Pessoa")
	@TTextField(textInputControl=@TTextInputControl(promptText="Insira parte ou o nome completo da pessoa", parse = true))
	@THBox(	pane=@TPane(children={"nome","acaoId"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="nome", priority=Priority.ALWAYS),
   				   		@TPriority(field="acaoId", priority=Priority.ALWAYS) }))
	private SimpleStringProperty nome;
	
	@TLabel(text="Codigo da Ação")
	@TTextField(textInputControl=@TTextInputControl(promptText="Insira os codigos separados por virgula", parse = true))
	private SimpleStringProperty acaoId;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Data ou Periodo da doação:", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
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
	
	@TPickListField(selectedLabel="#{label.selected}", 
			sourceLabel="Tipo Ajuda", required=false,
			optionsList=@TOptionsList(entityClass=TipoAjuda.class,
						optionModelViewClass=TipoAjudaModelView.class, 
						optionsProcessClass=LoadTipoAjudaOptionListProcess.class))
	@TModelViewCollectionType(modelClass=TipoAjuda.class, modelViewClass=TipoAjudaModelView.class, required=false)
	private ITObservableList<TipoAjudaModelView> tiposAjuda;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Resultado", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
			node=@TNode(id="t-form-title-text", parse = true))
			private SimpleStringProperty texto3;
	
	@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="data", text = "Data", prefWidth=100),
						@TTableColumn(cellValue="tipoAjuda", text = "Tipo Ajuda", prefWidth=70, resizable=true), 
						@TTableColumn(cellValue="quantidade", text = "Quantidade", prefWidth=70, resizable=true), 
						@TTableColumn(cellValue="valor", text = "Valor", prefWidth=70, resizable=true),
						@TTableColumn(cellValue="pessoa", text = "Doador", resizable=true), 
						@TTableColumn(cellValue="acao", text = "Acão", resizable=true)
			})
	@TModelViewCollectionType(modelClass=DoacaoItemModel.class, modelViewClass=DoacaoItemTableView.class)
	private ITObservableList<DoacaoItemTableView> result;
	
	
	public DoacaoReportModelView(DoacaoReportModel entidade) {
		super(entidade);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof DoacaoReportModelView))
			return false;
		
		DoacaoReportModelView p = (DoacaoReportModelView) obj;
		
		if(getId()!=null && getId().getValue()!=null &&  p.getId()!=null && p.getId().getValue()!=null){
			if(!(getId().getValue().equals(Long.valueOf(0)) && p.getId().getValue().equals(Long.valueOf(0))))
				return getId().getValue().equals(p.getId().getValue());
		}	
		return false;
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
	 * @return the texto1
	 */
	public SimpleStringProperty getTexto1() {
		return texto1;
	}

	/**
	 * @param texto1 the texto1 to set
	 */
	public void setTexto1(SimpleStringProperty texto1) {
		this.texto1 = texto1;
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

	
}
