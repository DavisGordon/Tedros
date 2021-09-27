/**
 * 
 */
package com.covidsemfome.module.tipoAjuda.model;

import com.covidsemfome.model.TipoAjuda;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.presenter.TSelectionModalPresenter;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

/**
 * @author Davis Gordon
 *
 */
@TSelectionModalPresenter(
		paginator=@TPaginator(entityClass = TipoAjuda.class, modelViewClass=TipoAjudaModelView.class, 
			serviceName = "ITipoAjudaControllerRemote"),
		tableView=@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="descricao", text = "Descrição", prefWidth=20, resizable=true), 
						@TTableColumn(cellValue="tipoPessoa", text = "Tipo Pessoa", resizable=true), 
						@TTableColumn(cellValue="status", text = "Status", resizable=true)}), 
		allowsMultipleSelections = true)
public class TipoAjudaFindModelView extends TEntityModelView<TipoAjuda> {

	private SimpleLongProperty id;
	
	@TLabel(text="Descrição")
	@TTextField(maxLength=60, required = true, node=@TNode(requestFocus=true, parse = true),
	textInputControl=@TTextInputControl(promptText="Descrição", parse = true), 
				control=@TControl(tooltip="#{label.name}", parse = true))
	private SimpleStringProperty descricao;
	
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	@TLabel(text="Tipo Pessoa")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Pessoa Fisica", userData="PF"), 
					@TRadioButtonField(text="Pessoa Juridica", userData="PJ")
	})
	private SimpleStringProperty tipoPessoa;
	
	public TipoAjudaFindModelView(TipoAjuda entity) {
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
	 * @return the tipoPessoa
	 */
	public SimpleStringProperty getTipoPessoa() {
		return tipoPessoa;
	}

	/**
	 * @param tipoPessoa the tipoPessoa to set
	 */
	public void setTipoPessoa(SimpleStringProperty tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return descricao;
	}
	
}
