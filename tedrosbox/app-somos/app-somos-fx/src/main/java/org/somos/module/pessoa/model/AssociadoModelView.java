/**
 * 
 */
package org.somos.module.pessoa.model;

import org.somos.model.AjudaCampanha;
import org.somos.model.Associado;
import org.somos.model.Pessoa;
import org.somos.module.pessoa.table.AjudaCampanhaTableView;
import org.somos.module.pessoa.table.CampanhaCellCallBack;
import org.somos.module.pessoa.table.DateCallback;
import org.somos.module.pessoa.table.FormaAjudaCellCallBack;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellFactory;
import com.tedros.fxapi.annotation.control.TCellValueFactory;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "Dados da forma de ajuda nas campanhas", showBreadcrumBar=true)
@TEjbService(serviceName = "IAssociadoControllerRemote", model=Associado.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Associado em campanha", 
	buildDeleteButton=false, buildNewButton=false, buildSaveButton=false, buildModesRadioButton=false)))
@TSecurity(	id="SOMOS_ASSOCIADOS_FORM", 
	appName = "#{somos.name}", moduleName = "Administrativo", viewName = "Associados em campanha",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT})
public class AssociadoModelView extends TEntityModelView<Associado> {

	private SimpleLongProperty id;
	
	@TShowField(fields= {@TField(name = "nome", label="Associado:")
	, @TField(name = "todosContatos", label="Contato:")})
	private SimpleObjectProperty<Pessoa> pessoa;
	
	@TTableView(editable=true, tableMenuButtonVisible=true,
			columns = { @TTableColumn(cellValue="campanha", text = "Campanha", prefWidth=50, resizable=true,
					cellValueFactory=@TCellValueFactory(parse=true, value=@TCallbackFactory(parse=true, value=CampanhaCellCallBack.class))), 
						@TTableColumn(cellValue="valor", text = "Valor",  resizable=true),
						@TTableColumn(cellValue="periodo", text = "Periodo", resizable=true), 
						@TTableColumn(cellValue="formaAjuda", text = "Forma Ajuda", prefWidth=20, resizable=true,
								cellValueFactory=@TCellValueFactory(parse=true, value=@TCallbackFactory(parse=true, value=FormaAjudaCellCallBack.class))), 
						@TTableColumn(cellValue="dataProcessado", text = "Enviado em", resizable=true,
								cellFactory=@TCellFactory(parse = true, callBack=@TCallbackFactory(parse=true, value=DateCallback.class))), 
						@TTableColumn(cellValue="dataProximo", text = "Proximo envio", resizable=true,
								cellFactory=@TCellFactory(parse = true, callBack=@TCallbackFactory(parse=true, value=DateCallback.class)))})
	@TModelViewType(modelClass=AjudaCampanha.class, modelViewClass=AjudaCampanhaTableView.class)	
	private ITObservableList<AjudaCampanhaTableView> ajudaCampanhas;
	
	public AssociadoModelView(Associado entity) {
		super(entity);
	}

	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the pessoa
	 */
	public SimpleObjectProperty<Pessoa> getPessoa() {
		return pessoa;
	}

	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(SimpleObjectProperty<Pessoa> pessoa) {
		this.pessoa = pessoa;
	}

	/**
	 * @return the ajudaCampanhas
	 */
	public ITObservableList<AjudaCampanhaTableView> getAjudaCampanhas() {
		return ajudaCampanhas;
	}

	/**
	 * @param ajudaCampanhas the ajudaCampanhas to set
	 */
	public void setAjudaCampanhas(ITObservableList<AjudaCampanhaTableView> ajudaCampanhas) {
		this.ajudaCampanhas = ajudaCampanhas;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return new SimpleStringProperty(getModel().getPessoa().getNome());
	}

}
