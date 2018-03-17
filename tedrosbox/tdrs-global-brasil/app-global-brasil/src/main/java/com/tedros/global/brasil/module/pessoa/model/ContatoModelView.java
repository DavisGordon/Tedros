/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 16/01/2014
 */
package com.tedros.global.brasil.module.pessoa.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.util.TPropertyUtil;
import com.tedros.global.brasil.model.Contato;
import com.tedros.global.brasil.module.pessoa.trigger.TTipoContatoTrigger;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TForm(showBreadcrumBar=true, name = "Editar contato")
@TFormReaderHtml
@TPresenter(type = TDynaPresenter.class,
behavior = @TBehavior(type = TDetailCrudViewWithListViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewWithListViewDecorator.class, viewTitle="Contatos", listTitle="Selecione"))
/*@TReaderDefaultSetting(showActionsToolTip=true, 
labelDefaultSettings=@TLabelDefaultSetting(node=@TNode(style="-fx-text-fill:yellow; -fx-font-size: 1.4em;", parse = true), font=@TFont(family="Euphemia", weight=FontWeight.BOLD)))*/
public class ContatoModelView extends TEntityModelView<Contato> {
	
	private SimpleLongProperty id;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "1", value = "#{label.email}"), 
			@TCodeValue(code = "2", value = "#{label.celnumber}"),
			@TCodeValue(code = "3", value = "#{label.housenumber}"),
			@TCodeValue(code = "4", value = "#{label.worknumber}")})
	@TLabel(text = "Tipo")
	@TVerticalRadioGroup(required=true, alignment=Pos.TOP_LEFT, spacing=4, 
			radioButtons = {@TRadioButtonField(text="#{label.email}", userData="1"), 
							@TRadioButtonField(text="#{label.celnumber}", userData="2"),
							@TRadioButtonField(text="#{label.housenumber}", userData="3"),
							@TRadioButtonField(text="#{label.worknumber}", userData="4")
			})
	@TTrigger(runAfterFormBuild = true, targetFieldName="descricao", 
	triggerClass=TTipoContatoTrigger.class, mode=TViewMode.EDIT)
	private SimpleStringProperty tipo;
	
	@TReaderHtml
	@TLabel(text="DESCRIÇÃO")
	@TMaskField(mask="##################################################", 
	textInputControl=@TTextInputControl(promptText="E-mail, telefone, celuar...", parse = true),
	required=true)
	private SimpleStringProperty descricao;
	
	public ContatoModelView(Contato entidade) {
		super(entidade);
	}
	
	public String getTipoDescricao(){
		String id = TPropertyUtil.getValue(tipo);
		String desc = "";
		if(id.equals("1"))
			desc = "E-mail";
		if(id.equals("2"))
			desc = "Celular";
		if(id.equals("3"))
			desc = "Tel. Residencial";
		if(id.equals("4"))
			desc = "Tel. Comercial";
		
		return desc;
	}
	
	@Override
	public String toString() {
		return TPropertyUtil.getValue(descricao);	
	}
	
	public String getContatoCompleto(){
		return getTipoDescricao()+" "+TPropertyUtil.getValue(descricao);
	}
		
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof ContatoModelView))
			return false;
		
		ContatoModelView p = (ContatoModelView) obj;
		
		if(getId()!=null && getId().getValue()!=null &&  p.getId()!=null && p.getId().getValue()!=null)
			if(!(getId().getValue().equals(Long.valueOf(0)) && p.getId().getValue().equals(Long.valueOf(0))))
				return getId().getValue().equals(p.getId().getValue());
		
		return getContatoCompleto().equals(p.getContatoCompleto());
	}

	
	public SimpleStringProperty getTipo() {
		return tipo;
	}


	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
	}


	public SimpleStringProperty getDescricao() {
		return descricao;
	}


	public void setDescricao(SimpleStringProperty descricao) {
		this.descricao = descricao;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return descricao;
	}

	public final SimpleLongProperty getId() {
		return id;
	}

	public final void setId(SimpleLongProperty id) {
		this.id = id;
	}

}
