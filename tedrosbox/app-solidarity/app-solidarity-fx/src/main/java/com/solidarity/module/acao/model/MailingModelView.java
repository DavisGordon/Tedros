/**
 * 
 */
package com.solidarity.module.acao.model;

import java.util.Date;

import com.solidarity.model.Mailing;
import com.solidarity.model.Voluntario;
import com.solidarity.module.acao.behavior.MailingAction;
import com.solidarity.module.acao.behavior.MailingBehavior;
import com.solidarity.module.acao.decorator.MailingDecorator;
import com.solidarity.module.acao.form.EmailTemplateForm;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.TItemModel;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TValidator;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.util.TDateUtil;

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
@TForm(name = "e-Mailing", editCssId="")
@TEjbService(serviceName = "IMailingControllerRemote", model=Mailing.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = Mailing.class, serviceName = "IMailingControllerRemote",
		show=true, showSearchField=true, searchFieldName="titulo",
				orderBy = {@TOption(text = "#{label.codigo}", value = "id"), 
						@TOption(text = "#{label.titulo}", value = "titulo"), 
						@TOption(text = "#{label.data}", value = "data")}),
	presenter=@TPresenter(behavior = @TBehavior(type = MailingBehavior.class, 
		saveAction=MailingAction.class, saveAllModels=false, saveOnlyChangedModels=false), 
		decorator = @TDecorator(type = MailingDecorator.class, buildModesRadioButton=false, 
			viewTitle="e-Mailing", listTitle="#{form.campaign.name}", saveButtonText="#{button.enviar.email}")))
@TSecurity(	id="COVSEMFOME_MAIL_FORM", 
	appName = "#{app.name}", moduleName = "#{module.manage.campaign}", viewName = "e-Mailing",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.SAVE, TAuthorizationType.EDIT, 
			TAuthorizationType.READ})
public class MailingModelView extends TEntityModelView<Mailing> {
	
	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="eem",
			panes={	@TTitledPane(text="#{form.campaign.name}", 
					fields={"textoCadastro","titulo","descricao"}),
					@TTitledPane(text="#{label.vol.inscritos}", fields={"voluntarios"}),
					@TTitledPane(text="E-mail",node=@TNode(id="eem",parse = true), expanded=true, 
					fields={"tituloEmail", "destino","conteudo","tituloBoxEmail","textoChaves"})})
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{form.campaign.name}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TLabel(text="#{label.titulo.local}")
	@TShowField()
	@THBox(	pane=@TPane(children={"titulo","data","status"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="titulo", priority=Priority.ALWAYS), 
   				   		@TPriority(field="data", priority=Priority.NEVER),
   				   	@TPriority(field="status", priority=Priority.ALWAYS)}))
	private SimpleStringProperty titulo;
	
	@TShowField(fields= {@TField(label="#{label.dataHora}", pattern=TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> data;
	
	@TLabel(text="#{label.status}")
	@TShowField()
	private SimpleStringProperty status;
	
	@TLabel(text="#{label.descricao}")
	@TTextAreaField( wrapText=true, control=@TControl(prefWidth=220, prefHeight=50, parse = true))
	private SimpleStringProperty descricao;
	
	@TTableView(editable=true,
			columns = { @TTableColumn(cellValue="nome", text = "#{label.name}", prefWidth=70),
					@TTableColumn(cellValue="email", text = "E-mail", prefWidth=100),
			@TTableColumn(cellValue="tipoVoluntario", text = "#{label.tipo}", prefWidth=60),
			@TTableColumn(cellValue="statusVoluntario", text = "Status", prefWidth=60)
			})
	@TModelViewCollectionType(modelClass=Voluntario.class, modelViewClass=VoluntarioTableView.class)
	private ITObservableList<VoluntarioTableView> voluntarios;
	
	@TLabel(text="#{label.titulo}")
	@TTextField(required=true)
	private SimpleStringProperty tituloEmail;
	
	@TLabel(text="#{label.destino}")
	@TValidator(validatorClass = MailingDestinoValidator.class, associatedFieldsName={"emails"})
	@TComboBoxField(items=DestinoItensBuilder.class, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"destino","emails"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="destino", priority=Priority.NEVER), 
   				   		@TPriority(field="emails", priority=Priority.ALWAYS) }))
	private SimpleObjectProperty<TItemModel<String>> destino;

	@TLabel(text="#{label.para}")
	@TTextField(textInputControl=@TTextInputControl(promptText="#{prompt.para}", parse = true))
	private SimpleStringProperty emails;
	
	
	@TLabel(text="#{label.conteudo}")
	@THTMLEditor(required=true, control=@TControl(prefHeight=300, parse = true))
	private SimpleStringProperty conteudo;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{text.emailing.chaves}", 
	wrappingWidth=650, textAlignment=TextAlignment.LEFT, 
	textStyle = TTextStyle.CUSTOM)
	private SimpleStringProperty tituloBoxEmail;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#NOME# #TITULOACAO# #DESCRICAOACAO# #STATUSACAO# #DATAACAO# #QTDINSCRITOS# #QTDMINVOL# #QTDMAXVOL# #LINKPAINEL# #LINKSITE#", 
			wrappingWidth=650, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.CUSTOM)
	private SimpleStringProperty textoChaves;


	public MailingModelView(Mailing entity) {
		super(entity);
		tituloEmail.setValue(getModel().getTitulo());
		conteudo.setValue("Olá #NOME#,<br> precisamos da sua ajuda para a campanha a ser realizada no dia #DATAACAO#, temos #QTDINSCRITOS# inscrito(s) e precisamos de #QTDMINVOL#, você pode se inscrever pelo #LINKPAINEL# em nosso site. <br><br> "
		+getModel().getDescricao()+"<br><br>#LINKSITE#");
	
	}
	
	@Override
	public boolean isChanged() {
		return false;
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
	public SimpleObjectProperty<TItemModel<String>> getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(SimpleObjectProperty<TItemModel<String>> destino) {
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
