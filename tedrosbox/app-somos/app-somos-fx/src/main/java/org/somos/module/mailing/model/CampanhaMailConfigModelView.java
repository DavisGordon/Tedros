/**
 * 
 */
package org.somos.module.mailing.model;

import org.somos.model.Campanha;
import org.somos.model.CampanhaMailConfig;
import org.somos.model.FormaAjuda;
import org.somos.module.acao.campanha.model.CampanhaFindModelView;
import org.somos.module.acao.campanha.model.FormaAjudaModelView;
import org.somos.module.mailing.form.CampanhaEmailTemplateForm;

import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.TText.TTextStyle;
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
@TForm(name = "Configurar email a ser enviado", showBreadcrumBar=false, form=CampanhaEmailTemplateForm.class)
@TEjbService(serviceName = "ICampanhaMailConfigControllerRemote", model=CampanhaMailConfig.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = CampanhaMailConfig.class, serviceName = "ICampanhaMailConfigControllerRemote",
		show=true, showSearchField=true, searchFieldName="titulo",
		orderBy = {@TOption(text = "Codigo", value = "id"), 
					@TOption(text = "Titulo", value = "titulo")}),
	presenter=@TPresenter(behavior = @TBehavior(saveAllModels=false, saveOnlyChangedModels=false), 
		decorator = @TDecorator(viewTitle="Campanha Mailing Config", listTitle="Configuração", readerModeTitle="Ver email")))
/*@TSecurity(	id="SOMOS_CAMPANHA_MAIL_CONFIG_FORM", 
	appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Campanha Mail Config",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.SAVE, TAuthorizationType.NEW,
			TAuthorizationType.DELETE, TAuthorizationType.EDIT, TAuthorizationType.READ})*/
public class CampanhaMailConfigModelView extends TEntityModelView<CampanhaMailConfig> {
	
	private SimpleLongProperty id;
	@TText(text="Este será o email a ser enviado automaticamente aos associados. "
			+ "Se nenhuma campanha for selecionada este template de email será usado para "
			+ "todos os associados que escolheram a mesma forma de ajuda aqui especificada.", 
	textAlignment=TextAlignment.LEFT, wrappingWidth=750,
	textStyle = TTextStyle.MEDIUM)
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-hsplit-last", parse = true))
	private SimpleStringProperty textoCadastro;
	
	@TLabel(text="Titulo do email")
	@TTextField(maxLength=25, node=@TNode(requestFocus=true, parse = true),
	textInputControl=@TTextInputControl(promptText="Insira um titulo", parse = true),
	required=true)
	private SimpleStringProperty titulo;
	
	@TLabel(text="Conteudo")
	@TTextAreaField(required=true, wrapText=true, control=@TControl(prefHeight=150, parse = true))
	@THBox(	pane=@TPane(children={"formaAjuda", "conteudo"}), spacing=10, 
	hgrow=@THGrow(priority={@TPriority(field="formaAjuda", priority=Priority.NEVER), 
   				   		@TPriority(field="conteudo", priority=Priority.ALWAYS) }))
	private SimpleStringProperty conteudo;
	
	@TLabel(text="Forma de ajuda")
	@TComboBoxField(firstItemTex="Selecione", required=true,
	optionsList=@TOptionsList(entityClass=FormaAjuda.class, 
			optionModelViewClass=FormaAjudaModelView.class, serviceName = "IFormaAjudaControllerRemote"))
	@TVBox(	pane=@TPane(children={"formaAjuda", "campanha"}), spacing=10, 
	vgrow=@TVGrow(priority={@TPriority(field="formaAjuda", priority=Priority.NEVER), 
   				   		@TPriority(field="campanha", priority=Priority.ALWAYS) }))
	private SimpleObjectProperty<FormaAjuda> formaAjuda;

	@TLabel(text="Campanha")
	@TOneSelectionModal(modelClass = Campanha.class, modelViewClass = CampanhaFindModelView.class, 
	width=300, height=50)
	private SimpleObjectProperty<Campanha> campanha;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Chaves para substituição, insira a chave desejada para inserir uma informação. ex: #NOME# será substituido pelo nome do voluntário.", 
	wrappingWidth=650, textAlignment=TextAlignment.LEFT, 
	textStyle = TTextStyle.CUSTOM)
	private SimpleStringProperty tituloBoxEmail;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#NOME# #TITULOCAMPANHA# #LINKCAMPANHA# #LINKSITE#", 
			wrappingWidth=650, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.CUSTOM)
	private SimpleStringProperty textoChaves;


	public CampanhaMailConfigModelView(CampanhaMailConfig entity) {
		super(entity);
		if(entity.getConteudo()==null)
			conteudo.setValue("Olá #NOME#,<br> obrigado por nos ajudar nesta campanha "
					+ "segue os dados para efetuar a ajuda: <br><br> ");
		
	}
	
	public CampanhaMailConfigModelView() {
		super(new CampanhaMailConfig());
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
		return this.titulo;
	}

	
	
	public CampanhaMailConfigModelView getNewInstance(){
		return new CampanhaMailConfigModelView(new CampanhaMailConfig());
	}
	
	@Override
	public String toString() {
		return (getTitulo()!=null)? getTitulo().getValue() : "";	
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
	 * @return the campanha
	 */
	public SimpleObjectProperty<Campanha> getCampanha() {
		return campanha;
	}

	/**
	 * @param campanha the campanha to set
	 */
	public void setCampanha(SimpleObjectProperty<Campanha> campanha) {
		this.campanha = campanha;
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
	 * @return the formaAjuda
	 */
	public SimpleObjectProperty<FormaAjuda> getFormaAjuda() {
		return formaAjuda;
	}

	/**
	 * @param formaAjuda the formaAjuda to set
	 */
	public void setFormaAjuda(SimpleObjectProperty<FormaAjuda> formaAjuda) {
		this.formaAjuda = formaAjuda;
	}
		
	

}
