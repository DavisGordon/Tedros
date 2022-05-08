/**
 * 
 */
package org.somos.module.acao.campanha.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.model.FormaAjuda;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextAreaField;
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
@TForm(name = "Campanha / Forma ajuda", scroll=false)
@TEjbService( model=FormaAjuda.class, serviceName = "IFormaAjudaControllerRemote")
@TPresenter(decorator = @TDecorator(viewTitle="Campanha / Forma de Ajuda"))
@TSecurity(	id="SOMOS_CAMPFORMAAJUDA_FORM", 
			appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Campanha / Forma de Ajuda",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class FormaAjudaModelView extends TEntityModelView<FormaAjuda> {

	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Forma de Ajuda", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Forma de Ajuda em campanha", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
		
	@TReaderHtml
	@TLabel(text="Tipo")
	@TTextField(maxLength=60, required = true, 
		textInputControl=@TTextInputControl(promptText="Pix, PayPal, PicPay, Deposito, Produtos...", parse = true), 
		control=@TControl(tooltip="Informe a forma de ajuda que uma campanha pode precisar", parse = true))
	@THBox(	pane=@TPane(children={"tipo","tercerizado"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="tipo", priority=Priority.ALWAYS), 
   				   		@TPriority(field="tercerizado", priority=Priority.ALWAYS) }))
	private SimpleStringProperty tipo;
	

	@TLabel(text="Tecerizado (Ex: PayPal)")
	@TReaderHtml(codeValues={@TCodeValue(code = "Sim", value = "Sim"),
			@TCodeValue(code = "Não", value = "Não")})
	@THorizontalRadioGroup(alignment=Pos.CENTER_LEFT, spacing=4, 
	radioButtons={
			@TRadioButtonField(text = "Sim", userData = "Sim"),
			@TRadioButtonField(text = "Não", userData = "Não")
			})
	private SimpleStringProperty tercerizado;
	
	@TReaderHtml
	@TLabel(text="Detalhe")
	@TTextAreaField(maxLength=2000, 
	control=@TControl(tooltip="Detalhe aqui os dados necessarios para esta forma de ajuda.", parse = true))
	private SimpleStringProperty detalhe;
	
	public FormaAjudaModelView(FormaAjuda entity) {
		super(entity);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof FormaAjudaModelView))
			return false;
		return EqualsBuilder.reflectionEquals(this.getModel(), obj != null ? ((FormaAjudaModelView)obj).getModel() : obj, false);
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

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return tipo;
	}

	/**
	 * @return the tipo
	 */
	public SimpleStringProperty getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the detalhe
	 */
	public SimpleStringProperty getDetalhe() {
		return detalhe;
	}

	/**
	 * @param detalhe the detalhe to set
	 */
	public void setDetalhe(SimpleStringProperty detalhe) {
		this.detalhe = detalhe;
	}

	/**
	 * @return the tercerizado
	 */
	public SimpleStringProperty getTercerizado() {
		return tercerizado;
	}

	/**
	 * @param tercerizado the tercerizado to set
	 */
	public void setTercerizado(SimpleStringProperty tercerizado) {
		this.tercerizado = tercerizado;
	}
	
}
