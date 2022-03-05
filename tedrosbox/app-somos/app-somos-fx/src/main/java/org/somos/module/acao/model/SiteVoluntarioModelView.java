package org.somos.module.acao.model;

import org.somos.model.SiteVoluntario;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TSelectImageField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

@TFormReaderHtml
@TForm(name = "Site/Voluntario", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = "ISiteVoluntarioControllerRemote", model=SiteVoluntario.class)
@TListViewPresenter(paginator=@TPaginator(entityClass = SiteVoluntario.class, serviceName = "ISiteVoluntarioControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Site/Voluntario", buildModesRadioButton=false)))
@TSecurity(	id="SOMOS_SITEVOLUNTARIO_FORM", 
	appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Site/Voluntario",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteVoluntarioModelView extends TEntityModelView<SiteVoluntario>{
	
	private SimpleLongProperty id;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "Ativado"), 
			@TCodeValue(code = "DESATIVADO", value = "Desativado")})
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	@TLabel(text="Descrição")
	@TReaderHtml
	//@THTMLEditor(/*required=true*/)
	@TTextAreaField(wrapText=true, control=@TControl(prefHeight=400, parse = true))
	@TTabPane(tabs = { @TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"descricao"})), text = "Conteudo"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"image"})), text = "Imagem")
			
	})
	private SimpleStringProperty descricao;
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, 
	remoteOwner="somos", maxFileSize=300000)
	private SimpleObjectProperty<ITFileBaseModel> image;
	
	
	public SiteVoluntarioModelView(SiteVoluntario entidade) {
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
		return status;
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
