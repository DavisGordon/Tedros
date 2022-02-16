package org.somos.module.acao.model;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.somos.model.SiteDoacaoTransferencia;
import org.somos.model.SitePontoColeta;
import org.somos.model.SiteSMDoacao;
import org.somos.model.Voluntario;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDetailField;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
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
import com.tedros.fxapi.annotation.reader.TDetailReaderHtml;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "Site/Doações", showBreadcrumBar=false)
@TEjbService(serviceName = "ISiteSMDoacaoControllerRemote", model=SiteSMDoacao.class)
@TListViewPresenter(paginator=@TPaginator(entityClass = SiteSMDoacao.class, serviceName = "ISiteSMDoacaoControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Site/Doações", buildModesRadioButton=false)))
@TSecurity(	id="SOMOS_SITEDOACOES_FORM", 
	appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Site/Doações",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteSMDoacaoModelView extends TEntityModelView<SiteSMDoacao>{
	
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
	//@THTMLEditor(/*required=true*/control=@TControl(prefHeight=300, parse = true))
	@TTextAreaField(wrapText=true, control=@TControl(prefHeight=400, parse = true))
	@TTabPane(tabs = { @TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"descricao"})), text = "Conteudo"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"image"})), text = "Imagem"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"pontosColeta"})), text = "Pontos de coleta"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"transferencia"})), text = "Transferencia")
			
	})
	private SimpleStringProperty descricao;
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, remoteOwner="somos")
	private SimpleObjectProperty<ITFileBaseModel> image;
	
	@TFieldBox(node=@TNode(id="pontocol", parse = true))
	@TDetailReaderHtml(label=@TLabel(text="Pontos de coleta"), entityClass=SitePontoColeta.class, modelViewClass=SitePontoColetaDetailView.class)
	@TDetailListField(entityModelViewClass = SitePontoColetaDetailView.class, entityClass = SitePontoColeta.class)
	@TModelViewType(modelClass=SitePontoColeta.class, modelViewClass=SitePontoColetaDetailView.class)
	private ITObservableList<SitePontoColetaDetailView>  pontosColeta;
	
	@TDetailField(showButtons=true, modelClass = SiteDoacaoTransferencia.class, modelViewClass = SiteDoacaoTransferenciaDetailView.class) 
	@TModelViewType(modelClass=SiteDoacaoTransferencia.class, modelViewClass=SiteDoacaoTransferenciaDetailView.class)
	private SimpleObjectProperty<SiteDoacaoTransferenciaDetailView> transferencia;
	
	public SiteSMDoacaoModelView(SiteSMDoacao entidade) {
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


	/**
	 * @return the pontosColeta
	 */
	public ITObservableList<SitePontoColetaDetailView> getPontosColeta() {
		return pontosColeta;
	}


	/**
	 * @param pontosColeta the pontosColeta to set
	 */
	public void setPontosColeta(ITObservableList<SitePontoColetaDetailView> pontosColeta) {
		this.pontosColeta = pontosColeta;
	}


	/**
	 * @return the transferencia
	 */
	public SimpleObjectProperty<SiteDoacaoTransferenciaDetailView> getTransferencia() {
		return transferencia;
	}


	/**
	 * @param transferencia the transferencia to set
	 */
	public void setTransferencia(SimpleObjectProperty<SiteDoacaoTransferenciaDetailView> transferencia) {
		this.transferencia = transferencia;
	}


}
