package org.somos.module.acao.model;

import org.somos.model.SiteGaleria;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TSelectImageField;
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
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

@TFormReaderHtml
@TForm(name = "Site/Galeria", showBreadcrumBar=false)
@TEjbService(serviceName = "ISiteGaleriaControllerRemote", model=SiteGaleria.class)
@TListViewPresenter(refreshListViewAfterActions=true,
		paginator=@TPaginator(entityClass = SiteGaleria.class, serviceName = "ISiteGaleriaControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Site/Galeria", buildModesRadioButton=false)))
@TSecurity(	id="SOMOS_SITEGALERIA_FORM", 
	appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Site/Galeria",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SiteGaleriaModelView extends TEntityModelView<SiteGaleria>{
	
	private SimpleLongProperty id;
	
	@TLabel(text="Titulo")
	@TTextField(required=true, maxLength=120,
	node=@TNode(requestFocus=true, parse = true))
	@THBox(pane=@TPane(children={"titulo","status","tipo","ordem"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="titulo", priority=Priority.ALWAYS), 
					@TPriority(field="status", priority=Priority.ALWAYS), 
					@TPriority(field="tipo", priority=Priority.ALWAYS)}))
	private SimpleStringProperty titulo;
	
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	@TLabel(text="Tipo")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, required=true, spacing=4,
	radioButtons = {@TRadioButtonField(text="Parceiro", userData="Parceiro"), 
					@TRadioButtonField(text="Mural", userData="Mural")
	})
	private SimpleStringProperty tipo;
	
	@TLabel(text="Ordem")
	@TNumberSpinnerField(maxValue = 999)
	private SimpleIntegerProperty ordem;
	
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, remoteOwner="somos")
	private SimpleObjectProperty<ITFileBaseModel> image;
	
	public SiteGaleriaModelView(SiteGaleria entidade) {
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
		return titulo;
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
	 * @return the tipo
	 */
	public SimpleStringProperty getTipo() {
		return tipo;
	}


	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
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
