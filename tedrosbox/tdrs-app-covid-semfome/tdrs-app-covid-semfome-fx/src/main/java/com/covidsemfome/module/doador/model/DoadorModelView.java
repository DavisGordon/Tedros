package com.covidsemfome.module.doador.model;

import java.util.Date;

import com.covidsemfome.model.Doacao;
import com.covidsemfome.model.Doador;
import com.covidsemfome.module.doador.process.DoadorProcess;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.TIgnoreField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.form.TDetailView;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.reader.TDetailReaderHtml;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMainCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * The person model view
 * 
 * An entity model view class wraps an entity and is responsable 
 * for descript how the crud view and the fields must be build and exposed.
 * 
 * @author Davis Gordon
 * */
@TFormReaderHtml
@TForm(name = "#{form.donor.title}", showBreadcrumBar=true)
@TEntityProcess(process = DoadorProcess.class, entity=Doador.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TMainCrudViewWithListViewBehavior.class), 
			decorator = @TDecorator(type = TMainCrudViewWithListViewDecorator.class, 
									viewTitle="#{view.donor.name}", listTitle="#{label.select}"))
@TSecurity(	id="APP_COVIDSEMFOME_FORM", 
			appName = "#{app.name}", moduleName = "#{label.donor}", viewName = "#{view.donor.name}",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})

public class DoadorModelView extends TEntityModelView<Doador>{
	
	private SimpleLongProperty id;
	
	@TIgnoreField()
	@TTextReaderHtml(text="#{form.person.title}", 
					htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
					cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
					cssForHtmlBox="", cssForContentValue="")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="#{form.person.title}", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
		node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, textInputControl=@TTextInputControl(promptText="#{label.name}", parse = true), 
		control=@TControl(tooltip="#{label.name}", parse = true))
	@THBox(pane=@TPane(children={"nome","email","celular"}), spacing=10, fillHeight=true, 
	   hgrow=@THGrow(priority={@TPriority(field="nome", priority=Priority.ALWAYS), 
			   				   @TPriority(field="email", priority=Priority.ALWAYS),
			   				   @TPriority(field="celular", priority=Priority.ALWAYS) }))
	private SimpleStringProperty nome;
	
	@TReaderHtml
	@TLabel(text="Email")
	@TTextField(maxLength=80, 
	textInputControl=@TTextInputControl(promptText="E-mail", parse = true),
	required=true)
	private SimpleStringProperty email;
	
	@TReaderHtml
	@TLabel(text="Celular")
	@TMaskField(mask="(99) #9999-9999", 
	textInputControl=@TTextInputControl(promptText="Celular", parse = true),
	required=true)
	private SimpleStringProperty celular;
	
	@TReaderHtml
	@TLabel(text="#{label.birthdate}")
	@TDatePickerField(required = false)
	@THBox(pane=@TPane(children={"dataNascimento","sexo"}), spacing=10, fillHeight=true, 
	   hgrow=@THGrow(priority={@TPriority(field="dataNascimento", priority=Priority.ALWAYS), 
			   				   @TPriority(field="sexo", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Date> dataNascimento;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "F", value = "#{label.female}"), @TCodeValue(code = "M", value = "#{label.male}")})
	@TLabel(text="#{label.sex}")
	@THorizontalRadioGroup(required=true, spacing=4, 
			radioButtons={	@TRadioButtonField(text = "#{label.female}", userData = "F"), 
							@TRadioButtonField(text = "#{label.male}", userData = "M")})
	private SimpleStringProperty sexo;
	
	@TReaderHtml
	@TLabel(text="#{label.address}")
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=50, parse = true), maxLength=150, prefRowCount=4)
	@THBox(	pane=@TPane(children={"endereco","observacao"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="endereco", priority=Priority.ALWAYS), 
						@TPriority(field="observacao", priority=Priority.ALWAYS)}))
	private SimpleStringProperty endereco;
	
	@TReaderHtml
	@TLabel(text="#{label.observation}")
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=50, parse = true), maxLength=450, prefRowCount=4)
	private SimpleStringProperty observacao;
	
	@TIgnoreField()
	@TTextReaderHtml(text="#{label.donations}", 
			htmlTemplateForControlValue="<h2 style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{label.donations}", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoDetail;
	
	@TDetailReaderHtml(	label=@TLabel(text="#{label.donation}"), 
						entityClass=Doacao.class, 
						modelViewClass=DoacaoModelView.class)
	@TTabPane(tabs = {
			@TTab(text = "#{label.donation}", closable=false,
					content = @TContent(detailView=@TDetailView(field="doacoes", formTitle="#{label.donation}", 
					listTitle = "#{label.donation}", propertyType=ITObservableList.class, entityClass=Doacao.class, entityModelViewClass=DoacaoModelView.class)))
			})
	@TModelViewCollectionType(	entityClass=Doacao.class, 
								modelViewClass=DoacaoModelView.class)
	private ITObservableList<DoacaoModelView> doacoes;
	

	
	public DoadorModelView(Doador entidade) {
		super(entidade);
	}
	
	@Override
	public void removeAllListener() {
		super.removeAllListener();
		//	tl.stop();
	}
	
	public DoadorModelView() {
		super(new Doador());
	}
	
	public  DoadorModelView getNewInstance(){
		return new DoadorModelView(new Doador());
	}
	
	@Override
	public String toString() {
		return (getNome()!=null)? getNome().getValue() : "";	
	}
		
	@Override
	public int hashCode() {
		return reflectionHashCode(this, null);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof DoadorModelView))
			return false;
		
		DoadorModelView p = (DoadorModelView) obj;
		
		if(getId()!=null && getId().getValue()!=null &&  p.getId()!=null && p.getId().getValue()!=null){
			if(!(getId().getValue().equals(Long.valueOf(0)) && p.getId().getValue().equals(Long.valueOf(0))))
				return getId().getValue().equals(p.getId().getValue());
		}	
		
		if(getNome()!=null && getNome().getValue()!=null &&  p.getNome()!=null && p.getNome().getValue()!=null)
			return getNome().getValue().equals(p.getNome().getValue());
		
		return false;
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getTextoCadastro() {
		return textoCadastro;
	}

	public void setTextoCadastro(SimpleStringProperty textoCadastro) {
		this.textoCadastro = textoCadastro;
	}

	public SimpleStringProperty getNome() {
		return nome;
	}

	public void setNome(SimpleStringProperty nome) {
		this.nome = nome;
	}

	
	public SimpleObjectProperty<Date> getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(SimpleObjectProperty<Date> dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public SimpleStringProperty getSexo() {
		return sexo;
	}

	public void setSexo(SimpleStringProperty sexo) {
		this.sexo = sexo;
	}

	public SimpleStringProperty getObservacao() {
		return observacao;
	}

	public void setObservacao(SimpleStringProperty observacao) {
		this.observacao = observacao;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return getNome();
	}

	public SimpleStringProperty getTextoDetail() {
		return textoDetail;
	}

	public void setTextoDetail(SimpleStringProperty textoDetail) {
		this.textoDetail = textoDetail;
	}

	/**
	 * @return the email
	 */
	public SimpleStringProperty getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}

	/**
	 * @return the celular
	 */
	public SimpleStringProperty getCelular() {
		return celular;
	}

	/**
	 * @param celular the celular to set
	 */
	public void setCelular(SimpleStringProperty celular) {
		this.celular = celular;
	}

	/**
	 * @return the endereco
	 */
	public SimpleStringProperty getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(SimpleStringProperty endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the doacoes
	 */
	public ITObservableList<DoacaoModelView> getDoacoes() {
		return doacoes;
	}

	/**
	 * @param doacoes the doacoes to set
	 */
	public void setDoacoes(ITObservableList<DoacaoModelView> doacoes) {
		this.doacoes = doacoes;
	}


	

}
