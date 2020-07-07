/**
 * 
 */
package com.covidsemfome.module.acao.model;

import java.util.Date;

import com.covidsemfome.model.Mailing;
import com.covidsemfome.model.Voluntario;
import com.covidsemfome.module.acao.behavior.MailingAction;
import com.covidsemfome.module.acao.behavior.MailingBehavior;
import com.covidsemfome.module.acao.decorator.MailingDecorator;
import com.covidsemfome.module.acao.form.EmailTemplateForm;
import com.covidsemfome.module.acao.process.MailingProcess;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TValidator;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
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
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Mailing", showBreadcrumBar=false, form=EmailTemplateForm.class)
@TEntityProcess(process = MailingProcess.class, entity=Mailing.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = MailingBehavior.class, 
									saveAction=MailingAction.class, saveAllModels=false), 
			decorator = @TDecorator(type = MailingDecorator.class, 
									viewTitle="Mailing", listTitle="Acão", saveButtonText="Enviar email"))
@TSecurity(	id="COVSEMFOME_MAIL_FORM", 
			appName = "#{app.name}", moduleName = "Mailing", viewName = "Mailing",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS})

public class MailingModelView extends TEntityModelView<Mailing> {
	
	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Acão", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Acão", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml
	@TLabel(text="Titulo/Local")
	@TTextField(node = @TNode(parse = true, disable=true))
	@THBox(	pane=@TPane(children={"titulo","data","status"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="titulo", priority=Priority.ALWAYS), 
   				   		@TPriority(field="data", priority=Priority.NEVER),
   				   	@TPriority(field="status", priority=Priority.ALWAYS)}))
	private SimpleStringProperty titulo;
	
	@TLabel(text="Data e Hora")
	@TDatePickerField( dateFormat=DateTimeFormatBuilder.class, node = @TNode(parse = true, disable=true))
	private SimpleObjectProperty<Date> data;
	
	@TLabel(text="Status")
	@THorizontalRadioGroup(required=true, alignment=Pos.CENTER_LEFT, spacing=4, 
	node = @TNode(parse = true, disable=true),
			radioButtons={
					@TRadioButtonField(text = "Agendada", userData = "Agendada"),
					@TRadioButtonField(text = "Cancelada", userData = "Cancelada"), 
					@TRadioButtonField(text = "Executada", userData = "Executada")
					})
	private SimpleStringProperty status;
	
	@TLabel(text="Descricão")
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=50, parse = true))
	private SimpleStringProperty descricao;
	
	@TAccordion(expandedPane="eem",
			panes={
					@TTitledPane(text="Voluntários inscritos", fields={"voluntarios"}),
					@TTitledPane(text="Enviar email",node=@TNode(id="eem",parse = true), expanded=true, fields={"destino","tituloEmail", "conteudo","tituloBoxEmail","textoChaves"})})
	@TTableView(editable=true,
			columns = { @TTableColumn(cellValue="nome", text = "Nome", prefWidth=100)
			})
	@TModelViewCollectionType(entityClass=Voluntario.class, modelViewClass=VoluntarioTableView.class)
	private ITObservableList<VoluntarioTableView> voluntarios;
	
	@TLabel(text="Destino:")
	@TValidator(validatorClass = MailingDestinoValidator.class, associatedFieldsName={"emails"})
	@THorizontalRadioGroup(required=true, alignment=Pos.CENTER_LEFT, spacing=4, 
			radioButtons={
					@TRadioButtonField(text = "Todos os voluntários", userData = "1"),
					@TRadioButtonField(text = "Não inscritos", userData = "2"), 
					@TRadioButtonField(text = "Somente Inscritos", userData = "3")
					})
	@THBox(	pane=@TPane(children={"destino","emails"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="destino", priority=Priority.ALWAYS), 
   				   		@TPriority(field="emails", priority=Priority.ALWAYS)}))
	private SimpleStringProperty destino;

	@TLabel(text="Para")
	@TTextField(textInputControl=@TTextInputControl(promptText="Insira os emails separados por virgula", parse = true))
	private SimpleStringProperty emails;
	
	
	@TLabel(text="Titulo")
	@TTextField(required=true)
	private SimpleStringProperty tituloEmail;
	
	@TLabel(text="Conteudo")
	@TTextAreaField(required=true, wrapText=true, control=@TControl(prefWidth=250, prefHeight=150, parse = true))
	private SimpleStringProperty conteudo;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Chaves para substituição, insira a chave desejada para inserir uma informação. ex: #NOME# será substituido pelo nome do voluntário.", 
	wrappingWidth=650,
	font=@TFont(size=12), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-label", parse = true))
	private SimpleStringProperty tituloBoxEmail;
	
	//@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="#NOME# #TITULOACAO# #DESCRICAOACAO# #STATUSACAO# #DATAACAO# #QTDINSCRITOS# #QTDMINVOL# #QTDMAXVOL# #LINKPAINEL# #LINKSITE#", 
			wrappingWidth=650,
			font=@TFont(size=12), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-label", parse = true))
	private SimpleStringProperty textoChaves;


	public MailingModelView(Mailing entity) {
		super(entity);
		tituloEmail.setValue("[Covid Sem Fome] "+getModel().getTitulo());
		conteudo.setValue("Olá #NOME#,<br> precisamos da sua ajuda para a campanha a ser realizada no dia #DATAACAO# e já temos #QTDINSCRITOS# inscrito(s) e precisamos de #QTDMINVOL#, você pode se inscrever pelo #LINKPAINEL# em nosso site. <br><br> "
		+getModel().getDescricao()+"<br><br>Equipe Covid Sem Fome<br>#LINKSITE#");
		
	}
	
	@Override
	public void removeAllListener() {
		super.removeAllListener();
		//	tl.stop();
	}
	
	public MailingModelView() {
		super(new Mailing());
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
		return titulo;
	}

	
	
	public MailingModelView getNewInstance(){
		return new MailingModelView(new Mailing());
	}
	
	@Override
	public String toString() {
		return (getTitulo()!=null)? getTitulo().getValue() : "";	
	}
		
	@Override
	public int hashCode() {
		return reflectionHashCode(this, null);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof MailingModelView))
			return false;
		
		MailingModelView p = (MailingModelView) obj;
		
		if(getId()!=null && getId().getValue()!=null &&  p.getId()!=null && p.getId().getValue()!=null){
			if(!(getId().getValue().equals(Long.valueOf(0)) && p.getId().getValue().equals(Long.valueOf(0))))
				return getId().getValue().equals(p.getId().getValue());
		}	
		
		if(getTitulo()!=null && getTitulo().getValue()!=null &&  p.getTitulo()!=null && p.getTitulo().getValue()!=null)
			return getTitulo().getValue().equals(p.getTitulo().getValue());
		
		return false;
	}

	/**
	 * @return the titulo
	 */
	public SimpleStringProperty getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(SimpleStringProperty titulo) {
		this.titulo = titulo;
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

	/**
	 * @return the voluntarios
	 */
	public ITObservableList<VoluntarioTableView> getVoluntarios() {
		return voluntarios;
	}

	/**
	 * @param voluntarios the voluntarios to set
	 */
	public void setVoluntarios(ITObservableList<VoluntarioTableView> voluntarios) {
		this.voluntarios = voluntarios;
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
	 * @return the tituloBoxEmail
	 */
	public SimpleStringProperty getTituloBoxEmail() {
		return tituloBoxEmail;
	}

	/**
	 * @param tituloBoxEmail the tituloBoxEmail to set
	 */
	public void setTituloBoxEmail(SimpleStringProperty tituloBoxEmail) {
		this.tituloBoxEmail = tituloBoxEmail;
	}

	/**
	 * @return the tituloEmail
	 */
	public SimpleStringProperty getTituloEmail() {
		return tituloEmail;
	}

	/**
	 * @param tituloEmail the tituloEmail to set
	 */
	public void setTituloEmail(SimpleStringProperty tituloEmail) {
		this.tituloEmail = tituloEmail;
	}

	/**
	 * @return the conteudo
	 */
	public SimpleStringProperty getConteudo() {
		return conteudo;
	}

	/**
	 * @param conteudo the conteudo to set
	 */
	public void setConteudo(SimpleStringProperty conteudo) {
		this.conteudo = conteudo;
	}

	/**
	 * @return the textoChaves
	 */
	public SimpleStringProperty getTextoChaves() {
		return textoChaves;
	}

	/**
	 * @param textoChaves the textoChaves to set
	 */
	public void setTextoChaves(SimpleStringProperty textoChaves) {
		this.textoChaves = textoChaves;
	}

	/**
	 * @return the destino
	 */
	public SimpleStringProperty getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(SimpleStringProperty destino) {
		this.destino = destino;
	}

	/**
	 * @return the emails
	 */
	public SimpleStringProperty getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public void setEmails(SimpleStringProperty emails) {
		this.emails = emails;
	}


}