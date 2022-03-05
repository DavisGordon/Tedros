package org.somos.module.acao.model;

import org.somos.model.SiteEquipe;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TSelectImageField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

@TFormReaderHtml
@TForm(name = "Home/Equipe", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = "ISiteEquipeControllerRemote", model=SiteEquipe.class)
@TListViewPresenter(refreshListViewAfterActions=true,
		paginator=@TPaginator(entityClass = SiteEquipe.class, serviceName = "ISiteEquipeControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Home/Equipe", buildModesRadioButton=false)))
@TSecurity(	id="SOMOS_SITEEQUIPE_FORM", 
	appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Site/Home/Equipe",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteEquipeModelView extends TEntityModelView<SiteEquipe>{
	
	private SimpleLongProperty id;
	
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	@THBox(pane=@TPane(children={"status","ordem"}))
	private SimpleStringProperty status;
	
	@TLabel(text="Ordem")
	@TNumberSpinnerField(maxValue = 100)
	private SimpleIntegerProperty ordem;
	
	@TLabel(text="Nome")
	@TTextField(required=true, maxLength=60,
	node=@TNode(requestFocus=true, parse = true))
	@TTabPane(tabs = { 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"nome", "funcao", "descricao"})), text = "Conteudo"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"image"})), text = "Imagem")
			
	})
	private SimpleStringProperty nome;
	
	@TLabel(text="Função")
	@TTextField(required=true, maxLength=120)
	private SimpleStringProperty funcao;
	
	@TLabel(text="Descrição")
	@TTextAreaField(wrapText=true, control=@TControl(prefHeight=250, parse = true))
	private SimpleStringProperty descricao;
	

	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, 
	remoteOwner="somos", maxFileSize=300000)
	private SimpleObjectProperty<ITFileBaseModel> image;
	
	public SiteEquipeModelView(SiteEquipe entidade) {
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
	 * @return the funcao
	 */
	public SimpleStringProperty getFuncao() {
		return funcao;
	}


	/**
	 * @param funcao the funcao to set
	 */
	public void setFuncao(SimpleStringProperty funcao) {
		this.funcao = funcao;
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
	 * @return the image
	 */
	public SimpleObjectProperty<ITFileBaseModel> getImage() {
		return image;
	}


	/**
	 * @param image the image to set
	 */
	public void setImage(SimpleObjectProperty<ITFileBaseModel> image) {
		this.image = image;
	}


	


}
