/**
 * 
 */
package org.somos.module.acao.campanha.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.model.ValorAjuda;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Campanha / Valor ajuda", scroll=false)
@TEjbService( model=ValorAjuda.class, serviceName = "IValorAjudaControllerRemote")
@TPresenter(decorator = @TDecorator(viewTitle="Campanha / Valor de Ajuda"))
@TSecurity(	id="SOMOS_CAMPVALORAJUDA_FORM", 
			appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Campanha / Valor de Ajuda",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ValorAjudaModelView extends TEntityModelView<ValorAjuda> {

	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Valor de Ajuda", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Valor de Ajuda em campanha", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
		
	@TReaderHtml
	@TLabel(text="Valor")
	@TTextField(maxLength=60, required = true, 
		textInputControl=@TTextInputControl(promptText="Ex: 30, 1500.50 ou 1kg Arroz", parse = true), 
		control=@TControl(tooltip="Informe numeros inteiros para valor em dinheiro ou o nome do produto para campanhas de itens. ", parse = true))
	@THBox(	pane=@TPane(children={"valor","planoPayPal"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="valor", priority=Priority.ALWAYS), 
   				   		@TPriority(field="planoPayPal", priority=Priority.ALWAYS) }))
	private SimpleStringProperty valor;
	

	@TReaderHtml
	@TLabel(text="Plano PayPal")
	@TTextField(maxLength=40,  
		textInputControl=@TTextInputControl(promptText="Ex: P-9NW33321M3871482CMJ3SFVI", parse = true), 
		control=@TControl(tooltip="Informe o id do plano/assinatura mensal criado no PayPal com este valor.", parse = true))
	private SimpleStringProperty planoPayPal;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-info", parse = true))
	@TText(text="Para as doações mensais a serem pagas via cartão de credito pelo associado "
			+ "é preciso que no PayPal sejam criados Planos/Assinaturas para cada valor desejado,"
			+ "isto é, se desejar ter na campanha as opções de valor: 30 50 100 500. Então deverá ser "
			+ "criado no PayPal um plano para cada uma destas opções e com o ID do plano criado "
			+ "atualizar o campo acima 'Plano PayPal' relacionando assim este valor com o plano criado.", 
			textAlignment=TextAlignment.JUSTIFY, 
			textStyle = TTextStyle.LARGE, 
			wrappingWidth=750)
	private SimpleStringProperty texto;
	
	public ValorAjudaModelView(ValorAjuda entity) {
		super(entity);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ValorAjudaModelView))
			return false;
		return EqualsBuilder.reflectionEquals(this.getModel(), obj != null ? ((ValorAjudaModelView)obj).getModel() : obj, false);
	}
	
	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the textoCadastro
	 */
	public SimpleStringProperty getTextoCadastro() {
		return textoCadastro;
	}

	/**
	 * @param textoCadastro the textoCadastro to set
	 */
	public void setTextoCadastro(SimpleStringProperty textoCadastro) {
		this.textoCadastro = textoCadastro;
	}

	/**
	 * @return the valor
	 */
	public SimpleStringProperty getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(SimpleStringProperty valor) {
		this.valor = valor;
	}

	/**
	 * @return the planoPayPal
	 */
	public SimpleStringProperty getPlanoPayPal() {
		return planoPayPal;
	}

	/**
	 * @param planoPayPal the planoPayPal to set
	 */
	public void setPlanoPayPal(SimpleStringProperty planoPayPal) {
		this.planoPayPal = planoPayPal;
	}

	/**
	 * @return the texto
	 */
	public SimpleStringProperty getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(SimpleStringProperty texto) {
		this.texto = texto;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return valor;
	}
}
