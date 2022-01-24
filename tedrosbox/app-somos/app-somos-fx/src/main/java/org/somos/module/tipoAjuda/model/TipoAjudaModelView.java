/**
 * 
 */
package org.somos.module.tipoAjuda.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.model.TipoAjuda;
import org.somos.module.tipoAjuda.process.TipoAjudaProcess;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
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
@TForm(name = "Tipos de ajuda")
@TEntityProcess(process = TipoAjudaProcess.class, entity=TipoAjuda.class)
@TPresenter(decorator = @TDecorator(viewTitle="Tipos de ajuda voluntariado"))
@TSecurity(	id="SOMOS_CADTIPOAJUDA_FORM", 
			appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Tipos de Ajuda",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class TipoAjudaModelView extends TEntityModelView<TipoAjuda> {

	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Tipo de Ajuda", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Tipo de Ajuda", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
		
	@TReaderHtml
	@TLabel(text="Descrição")
	@TTextField(maxLength=60, required = true, textInputControl=@TTextInputControl(promptText="Descrição", parse = true), 
				control=@TControl(tooltip="#{label.name}", parse = true))
	@THBox(	pane=@TPane(children={"descricao","status","tipoPessoa"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="descricao", priority=Priority.ALWAYS), 
						@TPriority(field="status", priority=Priority.ALWAYS), 
   				   		@TPriority(field="tipoPessoa", priority=Priority.ALWAYS)}))
	private SimpleStringProperty descricao;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "Ativado"), 
			@TCodeValue(code = "DESATIVADO", value = "Desativado")})
	@TLabel(text="Status")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "PF", value = "Pessoa Fisica"), 
			@TCodeValue(code = "PJ", value = "Pessoa Juridica")})
	@TLabel(text="Tipo Pessoa")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Pessoa Fisica", userData="PF"), 
					@TRadioButtonField(text="Pessoa Juridica", userData="PJ")
	})
	private SimpleStringProperty tipoPessoa;
	
	public TipoAjudaModelView(TipoAjuda entity) {
		super(entity);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TipoAjudaModelView))
			return false;
		return EqualsBuilder.reflectionEquals(this.getModel(), obj != null ? ((TipoAjudaModelView)obj).getModel() : obj, false);
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
	 * @return the status
	 */
	public SimpleStringProperty getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}

	/**
	 * @return the tipoPessoa
	 */
	public SimpleStringProperty getTipoPessoa() {
		return tipoPessoa;
	}

	/**
	 * @param tipoPessoa the tipoPessoa to set
	 */
	public void setTipoPessoa(SimpleStringProperty tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return descricao;
	}
	
}
