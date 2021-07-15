/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 15/01/2014
 */
package com.solidarity.module.pessoa.model;

import com.solidarity.model.Endereco;
import com.solidarity.model.UF;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.util.TPropertyUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(showBreadcrumBar=true, name = "Editar endereço")
@TDetailListViewPresenter(presenter=@TPresenter(
behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewDecorator.class, viewTitle="Endereços")))
public class EnderecoModelView extends TEntityModelView<Endereco> {
	
	private SimpleLongProperty id;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "1", value = "#{label.house}"), 
			@TCodeValue(code = "2", value = "#{label.work}"),
			@TCodeValue(code = "3", value = "#{label.other}")})
	@TLabel(text="Tipo")
	@THorizontalRadioGroup(required=true, alignment=Pos.TOP_LEFT, spacing=4,
			radioButtons = {@TRadioButtonField(text="#{label.house}", userData="1"), 
							@TRadioButtonField(text="#{label.work}", userData="2"),
							@TRadioButtonField(text="#{label.other}", userData="3")
			})
	private SimpleStringProperty tipo;
	
	@TReaderHtml
	@TLabel(text="Tipo Logradouro")
	@TTextField(maxLength=100, required=true,
	node=@TNode(requestFocus=true, parse = true),
	textInputControl=@TTextInputControl(promptText="Rua, Avenida...", parse = true), 
	control=@TControl(prefWidth=350, parse = true))
	@THBox(	pane=@TPane(children={"tipoLogradouro","logradouro","bairro"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="tipoLogradouro", priority=Priority.ALWAYS), 
   				   		@TPriority(field="logradouro", priority=Priority.ALWAYS),
   				   		@TPriority(field="bairro", priority=Priority.ALWAYS) }))
	private SimpleStringProperty tipoLogradouro;
	
	@TReaderHtml
	@TLabel(text="Logradouro")
	@TTextField(maxLength=300, required=true, control=@TControl(prefWidth=350, parse = true))
	private SimpleStringProperty logradouro;
	
	@TReaderHtml
	@TLabel(text="Bairro")
	@TTextField(maxLength=300, required=true, control=@TControl(prefWidth=350, parse = true))
	private SimpleStringProperty bairro;
	
	@TReaderHtml
	@TLabel(text="Caixa Postal")
	@TTextField(maxLength=60, control=@TControl(minWidth=80, parse = true))
	@THBox(	pane=@TPane(children={"complemento","caixaPostal","cep"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="complemento", priority=Priority.ALWAYS), 
   				   		@TPriority(field="caixaPostal", priority=Priority.NEVER),
   				   		@TPriority(field="cep", priority=Priority.NEVER) }))
	
	private SimpleStringProperty caixaPostal;
	
	@TReaderHtml
	@TLabel(text="Cep")
	@TMaskField(mask="99999-999", required=true, control=@TControl(prefWidth=350, parse = true))
	private SimpleStringProperty cep;
	
	@TReaderHtml
	@TLabel(text="Complemento")
	@TTextField(maxLength=300, required=true, control=@TControl(prefWidth=350, parse = true))
	private SimpleStringProperty complemento;
	
	@TReaderHtml
	@TLabel(text="Cidade")
	@TTextField(required=true, maxLength=300, control=@TControl(prefWidth=350, parse = true))
	@THBox(	pane=@TPane(children={"cidade","uf"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="cidade", priority=Priority.ALWAYS), 
   				   		@TPriority(field="uf", priority=Priority.NEVER) }))
	private SimpleStringProperty cidade;
	
	@TReaderHtml
	@TLabel(text="UF")
	@TComboBoxField(firstItemTex="Selecione", required=true,
	optionsList=@TOptionsList(entityClass=UF.class, 
			optionModelViewClass=UFModelView.class, serviceName = "IUFControllerRemote"))
	private SimpleObjectProperty<UF> uf;
	
	
	public EnderecoModelView(Endereco entidade) {
		super(entidade);
	}
	
	@Override
	public String toString() {
		return getEnderecoCompleto();	
	}

	private String getEnderecoCompleto() {
		String tipo = TPropertyUtil.getValue(this.tipo);
		String tDes = "";
		if(tipo.equals("1"))
			tDes = "Residencial: ";
		if(tipo.equals("2"))
			tDes = "Comercial: ";
		
		String tLog = TPropertyUtil.getValue(tipoLogradouro)+" ";
		String logr = TPropertyUtil.getValue(logradouro)+" ";
		String compl = TPropertyUtil.getValue(complemento)+" ";
		String bair = TPropertyUtil.getValue(bairro)+" ";
		String cida = TPropertyUtil.getValue(cidade)+" ";
		//String esta = TPropertyUtil.getValue(this.uf)+" ";
		String cep = TPropertyUtil.getValue(this.cep)+" ";
		//return tDes+tLog+logr+compl+bair+cida+esta+cep;
		return tDes+tLog+logr+compl+bair+cida+cep;
	}
	
	
	public SimpleStringProperty getTipo() {
		return tipo;
	}

	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
	}

	public SimpleStringProperty getCaixaPostal() {
		return caixaPostal;
	}

	public void setCaixaPostal(SimpleStringProperty caixaPostal) {
		this.caixaPostal = caixaPostal;
	}

	public SimpleStringProperty getCep() {
		return cep;
	}

	public void setCep(SimpleStringProperty cep) {
		this.cep = cep;
	}

	public SimpleStringProperty getComplemento() {
		return complemento;
	}

	public void setComplemento(SimpleStringProperty complemento) {
		this.complemento = complemento;
	}

	public SimpleStringProperty getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(SimpleStringProperty tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	public SimpleStringProperty getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(SimpleStringProperty logradouro) {
		this.logradouro = logradouro;
	}

	public SimpleStringProperty getBairro() {
		return bairro;
	}

	public void setBairro(SimpleStringProperty bairro) {
		this.bairro = bairro;
	}

	public SimpleStringProperty getCidade() {
		return cidade;
	}

	public void setCidade(SimpleStringProperty cidade) {
		this.cidade = cidade;
	}

	/*public SimpleStringProperty getUf() {
		return uf;
	}

	public void setUf(SimpleStringProperty uf) {
		this.uf = uf;
	}*/

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return cep;
	}

	public final SimpleLongProperty getId() {
		return id;
	}

	public final void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the uf
	 */
	public SimpleObjectProperty<UF> getUf() {
		return uf;
	}

	/**
	 * @param uf the uf to set
	 */
	public void setUf(SimpleObjectProperty<UF> uf) {
		this.uf = uf;
	}

	

}
