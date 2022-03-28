package com.tedros.template.module.report.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TComboBoxField;
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
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TReportProcess;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.report.behavior.TDataSetReportBehavior;
import com.tedros.fxapi.presenter.report.decorator.TDataSetReportDecorator;
import com.tedros.template.module.produto.model.UnidadeMedidaBuilder;
import com.tedros.template.module.report.process.ProdutoReportProcess;
import com.tedros.template.report.model.ProdutoItemModel;
import com.tedros.template.report.model.ProdutoReportModel;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;


@TForm(name = "#{label.search.prod}", showBreadcrumBar=true, editCssId="")
@TReportProcess(type=ProdutoReportProcess.class, model = ProdutoReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			action=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle="#{view.rep.prod}"))
@TSecurity(	id="SOMOS_PRODUTREP_FORM", 
			appName = "#{myapp.name}", moduleName = "#{module.adm}", viewName = "#{view.rep.prod}",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class ProdutoReportModelView extends TModelView<ProdutoReportModel>{
	
	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="#{label.filters}", node=@TNode(id="filtro",parse = true), 
							expanded=true, layoutType=TLayoutType.HBOX,
							fields={"codigo", "orderBy"}),
					@TTitledPane(text="#{label.result}", node=@TNode(id="resultado",parse = true),
						fields={"result"})})	
	private SimpleStringProperty displayText;
	
	@TLabel(text="#{label.code}")
	@TTextField(node=@TNode(requestFocus=true, parse = true), 
	textInputControl=@TTextInputControl(promptText="#{text.rep.prod.code}", parse = true))
	@TVBox(	pane=@TPane(children={"codigo", "nome", "marca", "texto2"}), spacing=10, fillWidth=true)
	private SimpleStringProperty codigo;
	
	@TLabel(text="#{label.name}")
	@TTextField(textInputControl=@TTextInputControl(promptText="#{text.rep.prod.name}", parse = true))
	private SimpleStringProperty nome;
	
	@TLabel(text="#{label.trademark}")
	@TTextField(maxLength=20)
	@THBox(	pane=@TPane(children={"marca","medida","unidadeMedida"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="marca", priority=Priority.ALWAYS), 
						@TPriority(field="unidadeMedida", priority=Priority.NEVER), 
						@TPriority(field="medida", priority=Priority.NEVER)}))
	private SimpleStringProperty marca;
	
	@TLabel(text="#{label.unit.measure}")
	@TComboBoxField(items=UnidadeMedidaBuilder.class)
	private SimpleStringProperty unidadeMedida;
	
	@TReaderHtml
	@TLabel(text="#{label.measure}")
	@TTextField(maxLength=10)
	private SimpleStringProperty  medida;
	
	@TLabel(text="#{label.order.by}:")
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend = "#{label.result.order}")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="#{label.code}", userData="codigo"), 
					@TRadioButtonField(text="#{label.name}", userData="nome")
	})
	private SimpleStringProperty orderBy;
	
	@TLabel(text="#{label.order.type}:")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="#{label.order.asc}", userData="asc"), 
					@TRadioButtonField(text="#{label.order.desc}", userData="desc")
	})
	private SimpleStringProperty orderType;
	
	@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="codigo", text = "#{label.code}", prefWidth=20, resizable=true), 
					@TTableColumn(cellValue="nome", text = "#{label.name}", resizable=true), 
						@TTableColumn(cellValue="marca", text = "#{label.trademark}", resizable=true), 
						@TTableColumn(cellValue="medida", text = "#{label.measure}", resizable=true), 
						@TTableColumn(cellValue="unidadeMedida", text = "#{label.unit.measure}", resizable=true)
			})
	@TModelViewType(modelClass=ProdutoItemModel.class, modelViewClass=ProdutoItemTableView.class)
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
}
