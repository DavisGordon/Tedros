package com.covidsemfome.module.report.model;

import com.covidsemfome.module.produto.model.UnidadeMedidaBuilder;
import com.covidsemfome.module.report.process.ProdutoReportProcess;
import com.covidsemfome.report.model.ProdutoItemModel;
import com.covidsemfome.report.model.ProdutoReportModel;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
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
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.report.behavior.TDataSetReportBehavior;
import com.tedros.fxapi.presenter.report.decorator.TDataSetReportDecorator;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;


@TForm(name = "Pesquisar produtos", showBreadcrumBar=true, editCssId="")
@TReportProcess(type=ProdutoReportProcess.class, model = ProdutoReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			searchAction=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle="Relatório de produtos"))
@TSecurity(	id="COVSEMFOME_PRODUTREP_FORM", 
			appName = "#{app.name}", moduleName = "Administrativo", viewName = "Relatório de Produtos",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class ProdutoReportModelView extends TModelView<ProdutoReportModel>{
	
	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="Filtros", node=@TNode(id="filtro",parse = true), expanded=true,
							fields={"nome","marca"}),
					@TTitledPane(text="Resultado", node=@TNode(id="resultado",parse = true),
						fields={"result"})})	
	private SimpleStringProperty displayText;
	
	@TLabel(text="Nome")
	@TTextField(node=@TNode(requestFocus=true, parse = true),
	textInputControl=@TTextInputControl(promptText="Insira parte ou o nome completo do produto", parse = true))
	@THBox(	pane=@TPane(children={"nome","codigo"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="nome", priority=Priority.ALWAYS),
   				   		@TPriority(field="codigo", priority=Priority.SOMETIMES) }))
	private SimpleStringProperty nome;
	
	@TLabel(text="Codigo")
	@TTextField(textInputControl=@TTextInputControl(promptText="Insira os codigos dos produtos separados por virgula", parse = true))
	private SimpleStringProperty codigo;
	
	@TLabel(text="Marca")
	@TTextField(maxLength=20,
	textInputControl=@TTextInputControl(promptText="Marca do produto", parse = true), 
				control=@TControl(tooltip="Insira a marca", parse = true))
	@THBox(	pane=@TPane(children={"marca","medida","unidadeMedida"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="marca", priority=Priority.ALWAYS), 
						@TPriority(field="unidadeMedida", priority=Priority.NEVER), 
						@TPriority(field="medida", priority=Priority.NEVER)}))
	private SimpleStringProperty marca;
	
	@TLabel(text="Unidade de medida")
	@TComboBoxField(items=UnidadeMedidaBuilder.class)
	private SimpleStringProperty unidadeMedida;
	
	@TReaderHtml
	@TLabel(text="Medida")
	@TTextField(maxLength=10, 
			textInputControl=@TTextInputControl(promptText="Medida", parse = true), 
						control=@TControl(tooltip="Insira a medida", parse = true))
	private SimpleStringProperty  medida;
	
	@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="codigo", text = "Codigo", prefWidth=20, resizable=true), 
					@TTableColumn(cellValue="nome", text = "Nome", resizable=true), 
						@TTableColumn(cellValue="marca", text = "Marca", resizable=true), 
						@TTableColumn(cellValue="medida", text = "Medida", resizable=true), 
						@TTableColumn(cellValue="unidadeMedida", text = "Unidade Medida", resizable=true)
			})
	@TModelViewCollectionType(modelClass=ProdutoItemModel.class, modelViewClass=ProdutoItemTableView.class)
	private ITObservableList<ProdutoItemTableView> result;
	
	public ProdutoReportModelView(ProdutoReportModel entidade) {
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
	 * @return the codigo
	 */
	public SimpleStringProperty getCodigo() {
		return codigo;
	}


	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(SimpleStringProperty codigo) {
		this.codigo = codigo;
	}


	/**
	 * @return the marca
	 */
	public SimpleStringProperty getMarca() {
		return marca;
	}


	/**
	 * @param marca the marca to set
	 */
	public void setMarca(SimpleStringProperty marca) {
		this.marca = marca;
	}


	/**
	 * @return the unidadeMedida
	 */
	public SimpleStringProperty getUnidadeMedida() {
		return unidadeMedida;
	}


	/**
	 * @param unidadeMedida the unidadeMedida to set
	 */
	public void setUnidadeMedida(SimpleStringProperty unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}


	/**
	 * @return the medida
	 */
	public SimpleStringProperty getMedida() {
		return medida;
	}


	/**
	 * @param medida the medida to set
	 */
	public void setMedida(SimpleStringProperty medida) {
		this.medida = medida;
	}


	/**
	 * @return the result
	 */
	public ITObservableList<ProdutoItemTableView> getResult() {
		return result;
	}


	/**
	 * @param result the result to set
	 */
	public void setResult(ITObservableList<ProdutoItemTableView> result) {
		this.result = result;
	}
}
