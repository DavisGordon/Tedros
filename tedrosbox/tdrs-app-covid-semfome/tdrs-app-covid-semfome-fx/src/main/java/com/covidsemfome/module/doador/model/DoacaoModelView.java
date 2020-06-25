package com.covidsemfome.module.doador.model;

import java.util.Date;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.covidsemfome.model.Doacao;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

@TForm(showBreadcrumBar=true, name = "#{form.donation.name}")
@TFormReaderHtml
@TPresenter(type = TDynaPresenter.class,
behavior = @TBehavior(type = TDetailCrudViewWithListViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewWithListViewDecorator.class, viewTitle="#{form.donation.name}", listTitle="#{label.select}"))
public class DoacaoModelView extends TEntityModelView<Doacao>{
	
	private SimpleLongProperty id;
	
	@TLabel(text="#{label.tipo}")
	@TReaderHtml(codeValues={@TCodeValue(code = "Dinheiro", value = "Dinheiro"), 
			@TCodeValue(code = "Cesta basica", value = "Cesta basica"),
			@TCodeValue(code = "Comida", value = "Comida")})
	@THorizontalRadioGroup(required=true, alignment=Pos.CENTER_LEFT, spacing=4, 
	radioButtons={
			@TRadioButtonField(text = "Dinheiro", userData = "Dinheiro"),
			@TRadioButtonField(text = "Cesta basica", userData = "Cesta basica"), 
			@TRadioButtonField(text = "Comida", userData = "Comida")
			})
	@THBox(	pane=@TPane(children={"tipo","valor","data"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="tipo", priority=Priority.ALWAYS), 
						@TPriority(field="valor", priority=Priority.ALWAYS), 
   				   		@TPriority(field="data", priority=Priority.ALWAYS)}))
	private SimpleStringProperty tipo;
	
	
	@TReaderHtml
	@TLabel(text = "Valor")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="Valor", parse = true))
	private SimpleDoubleProperty valor;
	
	@TReaderHtml
	@TLabel(text="Data")
	@TDatePickerField
	private SimpleObjectProperty<Date> data;
	
	@TReaderHtml
	@TLabel(text="Observação")
	@TTextAreaField(maxLength=300, control=@TControl(prefWidth=250, prefHeight=200, parse = true))
	private SimpleStringProperty observacao;
	
	
	public DoacaoModelView(Doacao entidade) {
		super(entidade);
	}
	
	@Override
	public String toString() {
		String tipo = getTipoDescricao(); 
		if(tipo.equals(""))
			return (getValor()!=null)? getValor().getValue().toString() : "";
		else
			return (tipo+": "+getValor()!=null)? getValor().getValue().toString() : "";
	}
	
	public String getTipoDescricao(){
		/*if(TPropertyUtil.isStringValueNotBlank(getTipo())){
			if(getTipo().equals("1"))
				return "Identidade";
			if(getTipo().equals("2"))
				return "CPF";
			if(getTipo().equals("3"))
				return "CNPJ";
		}*/
		return getTipo().getValue();
	}
		
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof DoacaoModelView))
			return false;
		
		DoacaoModelView p = (DoacaoModelView) obj;
		
		if(getId()!=null && getId().getValue()!=null &&  p.getId()!=null && p.getId().getValue()!=null){
			if(!(getId().getValue().equals(Long.valueOf(0)) && p.getId().getValue().equals(Long.valueOf(0))))
				return getId().getValue().equals(p.getId().getValue());
		}	
		return false;
	/*	String str1 = getTipo()!=null && getTipo().getValue()!=null ? getTipo().getValue() : "";
		str1 += getNumero()!=null && getNumero().getValue()!=null ? getNumero().getValue() : "";
		
		String str2 = p.getTipo()!=null && p.getTipo().getValue()!=null ? p.getTipo().getValue() : "";
		str2 += p.getNumero()!=null && p.getNumero().getValue()!=null ? p.getNumero().getValue() : "";
		
		return str1.equals(str2);*/
	}


	public SimpleStringProperty getTipo() {
		return tipo;
	}


	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
	}




	public SimpleStringProperty getObservacao() {
		return observacao;
	}


	public void setObservacao(SimpleStringProperty observacao) {
		this.observacao = observacao;
	}
	
	@Override
	public SimpleStringProperty getDisplayProperty() {
		return tipo;
	}

	public final SimpleLongProperty getId() {
		return id;
	}

	public final void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the valor
	 */
	public SimpleDoubleProperty getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(SimpleDoubleProperty valor) {
		this.valor = valor;
	}

	/**
	 * @return the data
	 */
	public SimpleObjectProperty<Date> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(SimpleObjectProperty<Date> data) {
		this.data = data;
	}

}
