/**
 * 
 */
package com.covidsemfome.module.voluntario.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.module.voluntario.process.TipoAjudaProcess;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMainCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;
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
@TForm(name = "Cadastrar tipos de ajuda", showBreadcrumBar=true)
@TEntityProcess(process = TipoAjudaProcess.class, entity=TipoAjuda.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TMainCrudViewWithListViewBehavior.class), 
			decorator = @TDecorator(type = TMainCrudViewWithListViewDecorator.class, 
									viewTitle="Tipos de ajuda voluntariado", listTitle="#{label.select}"))
@TSecurity(	id="COVSEMFOME_CADTIPOAJUDA_FORM", 
			appName = "#{app.name}", moduleName = "Gerenciar Campanha", viewName = "Cadastrar Tipos de Ajuda",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})


public class TipoAjudaModelView extends TEntityModelView<TipoAjuda> {

	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Tipo de Ajuda", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Tipo de Ajuda", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
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
		// TODO Auto-generated constructor stub
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
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

}
