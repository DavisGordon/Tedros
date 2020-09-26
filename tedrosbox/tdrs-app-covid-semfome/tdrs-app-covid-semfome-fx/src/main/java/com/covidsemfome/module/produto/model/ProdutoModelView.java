/**
 * 
 */
package com.covidsemfome.module.produto.model;

import com.covidsemfome.model.Produto;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Produto")
@TEjbService(serviceName = "IProdutoControllerRemote", model=Produto.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = Produto.class, serviceName = "IProdutoControllerRemote",
			show=true, showSearchField=true, searchFieldName="nome",
			orderBy = {	@TOption(text = "Codigo", value = "codigo"), 
						@TOption(text = "Nome", value = "nome")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Produto", buildImportButton=true),
	behavior=@TBehavior(importFileModelViewClass=ProdutoImportModelView.class)))
@TSecurity(	id="COVSEMFOME_CADPROD_FORM", 
	appName = "#{app.name}", moduleName = "Administrativo", viewName = "Produto",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ProdutoModelView extends TEntityModelView<Produto> {

	
	private SimpleLongProperty id;

	@TReaderHtml
	@TLabel(text="Codigo")
	@TTextField(maxLength=20, required = true, 
	textInputControl=@TTextInputControl(promptText="Codigo do produto", parse = true), 
				control=@TControl(tooltip="Codigo de referencia", parse = true))
	@THBox(	pane=@TPane(children={"codigo","nome"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="codigo", priority=Priority.NEVER), 
						@TPriority(field="nome", priority=Priority.ALWAYS)}))
	private SimpleStringProperty codigo;
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, textInputControl=@TTextInputControl(promptText="#{label.name}", parse = true), 
				control=@TControl(tooltip="#{label.name}", parse = true))
	private SimpleStringProperty nome;
	
	@TReaderHtml
	@TLabel(text="Marca")
	@TTextField(maxLength=20,
	textInputControl=@TTextInputControl(promptText="Marca do produto", parse = true), 
				control=@TControl(tooltip="Insira a marca", parse = true))
	@THBox(	pane=@TPane(children={"marca","medida","unidadeMedida"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="marca", priority=Priority.ALWAYS), 
						@TPriority(field="unidadeMedida", priority=Priority.NEVER), 
						@TPriority(field="medida", priority=Priority.NEVER)}))
	private SimpleStringProperty marca;
	
	@TReaderHtml
	@TLabel(text="Descrição")
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=50, parse = true), 
	wrapText=true, maxLength=120, prefRowCount=4)
	private SimpleStringProperty descricao;
	
	@TReaderHtml
	@TLabel(text="Unidade de medida")
	@TComboBoxField(items=UnidadeMedidaBuilder.class)
	private SimpleStringProperty unidadeMedida;
	
	@TReaderHtml
	@TLabel(text="Medida")
	@TTextField(maxLength=10, 
			textInputControl=@TTextInputControl(promptText="Medida", parse = true), 
						control=@TControl(tooltip="Insira a medida", parse = true))
	private SimpleStringProperty  medida;
	
	public ProdutoModelView(Produto entity) {
		super(entity);
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
		return nome;
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

}
