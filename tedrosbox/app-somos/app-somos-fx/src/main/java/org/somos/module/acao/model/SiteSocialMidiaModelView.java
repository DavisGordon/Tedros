package org.somos.module.acao.model;

import org.somos.model.SiteMidiaSocial;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "Header/Siga nos", showBreadcrumBar=false)
@TEjbService(serviceName = "ISiteMidiaSocialControllerRemote", model=SiteMidiaSocial.class)
@TListViewPresenter(paginator=@TPaginator(entityClass = SiteMidiaSocial.class, serviceName = "ISiteMidiaSocialControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Header/Siga nos", buildModesRadioButton=false)))
@TSecurity(	id="SOMOS_SITESIGANOS_FORM", 
	appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Site/Header/Siga nos",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteSocialMidiaModelView extends TEntityModelView<SiteMidiaSocial>{
	
	private SimpleLongProperty id;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Item a ser exibido no rodapé das paginas", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TLabel(text="Tipo")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="Facebook", userData="fa-facebook-f"), 
					@TRadioButtonField(text="Instagram", userData="fa-instagram"), 
					@TRadioButtonField(text="Youtube", userData="fa-youtube"),
					@TRadioButtonField(text="LinkedIn", userData="fa-linkedin-in")
	})private SimpleStringProperty nome;
	
	@TLabel(text="Link")
	@TTextField(maxLength=500, required=true)
	@THBox(	pane=@TPane(children={"link","status","ordem"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="link", priority=Priority.ALWAYS), 
						@TPriority(field="status", priority=Priority.ALWAYS), 
						@TPriority(field="ordem", priority=Priority.NEVER)}))
	private SimpleStringProperty link;
	
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	@TLabel(text="Ordem")
	@TNumberSpinnerField(maxValue = 100)
	private SimpleIntegerProperty ordem;
	
	
	public SiteSocialMidiaModelView(SiteMidiaSocial entidade) {
		super(entidade);
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
		return nome;
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
	 * @return the nome
	 */
	public SimpleStringProperty getNome() {
		return nome;
	}


	/**
	 * @param nome the nome to set
	 */
	public void setNome(SimpleStringProperty nome) {
		this.nome = nome;
	}


	/**
	 * @return the link
	 */
	public SimpleStringProperty getLink() {
		return link;
	}


	/**
	 * @param link the link to set
	 */
	public void setLink(SimpleStringProperty link) {
		this.link = link;
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
	 * @return the ordem
	 */
	public SimpleIntegerProperty getOrdem() {
		return ordem;
	}


	/**
	 * @param ordem the ordem to set
	 */
	public void setOrdem(SimpleIntegerProperty ordem) {
		this.ordem = ordem;
	}


}
