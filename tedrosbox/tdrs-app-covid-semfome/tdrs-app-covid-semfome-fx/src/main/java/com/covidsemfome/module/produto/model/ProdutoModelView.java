/**
 * 
 */
package com.covidsemfome.module.produto.model;

import com.covidsemfome.model.Produto;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
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
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

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
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Produtos", buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=ProdutoImportModelView.class)))
@TSecurity(	id="COVSEMFOME_CADPROD_FORM", 
	appName = "#{app.name}", moduleName = "Administrativo", viewName = "Produtos",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ProdutoModelView extends TEntityModelView<Produto> {

	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText = new SimpleStringProperty();

	@TTextReaderHtml(text="Produto", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Dados do Produto", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty header;
	
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
		buildListener();
		loadDisplayText(entity);
	}
	
	@Override
	public void reload(Produto model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}
	
	private void loadDisplayText(Produto model) {
		if(!model.isNew()){
			String str = (codigo.getValue()==null ? "" : "(COD: "+codigo.getValue()+") " ) 
					+ (nome.getValue()!=null ? nome.getValue() : "");
			displayText.setValue(str);
		}
	}
	
	private void buildListener() {
		
		ChangeListener<String> idListener =  super.getListenerRepository().get("displayText");
		if(idListener==null){
			idListener = (arg0, arg1, arg2) -> {
					String str = (arg2==null ? "" : "(COD: "+arg2.toString()+") " ) 
							+ (nome.getValue()!=null ? nome.getValue() : "");
					displayText.setValue(str);
				};
			super.addListener("displayText", idListener);
		}else
			codigo.removeListener(idListener);
		
		codigo.addListener(idListener);
		
		ChangeListener<String> tituloListener = super.getListenerRepository().get("displayText1");
		if(tituloListener==null){
			tituloListener = (arg0, arg1, arg2)-> {
					String str = (codigo.getValue()==null ? "" : "(COD: "+codigo.getValue().toString()+") " ) 
							+ (arg2!=null ? arg2 : "");
					displayText.setValue(str);
				};
			super.addListener("displayText1", tituloListener);
		}else
			nome.removeListener(tituloListener);
		
		nome.addListener(tituloListener);
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
	 * @return the header
	 */
	public SimpleStringProperty getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(SimpleStringProperty header) {
		this.header = header;
	}

}
