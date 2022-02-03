/**
 * 
 */
package org.somos.module.pessoa.model;

import org.somos.model.PessoaTermoAdesao;
import org.somos.model.TipoAjuda;
import org.somos.module.tipoAjuda.model.TipoAjudaModelView;

import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TColumnReader;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTableReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TOptionProcessType;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(showBreadcrumBar=false, name = "Termo de adesão preenchido", editCssId="")
@TDetailListViewPresenter(presenter=@TPresenter(
		behavior = @TBehavior(type = TDetailCrudViewBehavior.class,
		action = {PessoaTermoAdesaoNewAction.class, PessoaTermoAdesaoPrintAction.class}), 
		decorator = @TDecorator(type = TDetailCrudViewDecorator.class, viewTitle="Termo de adesão", 
		buildPrintButton=true, printButtonText="Exportar PDF")))
public class PessoaTermoAdesaoModelView extends TEntityModelView<PessoaTermoAdesao> {

	private SimpleLongProperty id;
	
	@TTabPane(tabs = { @TTab(closable=false, content = @TContent(detailForm=@TDetailForm(orientation=Orientation.HORIZONTAL, fields={"tiposAjuda","status", "versionNum"})), text = "Tipos de ajuda"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"conteudo"})), text = "Documento")
	})
	@TLabel(text="Tipos de Ajuda")
	@TTableReaderHtml(label=@TLabel(text="Tipo de Ajuda:"), 
		column = { 	@TColumnReader(field = "descricao", name = "Descricao"), 
					@TColumnReader(field = "tipoPessoa", name = "Tipo pessoa")})
	@TPickListField(selectedLabel="#{label.selected}", 
		sourceLabel="Opções", required=true,
		optionsList=@TOptionsList(entityClass=TipoAjuda.class,
					optionModelViewClass=TipoAjudaModelView.class,
					optionProcessType=TOptionProcessType.LIST_ALL,
					serviceName = "ITipoAjudaControllerRemote"))
	@TModelViewType(modelClass=TipoAjuda.class, modelViewClass=TipoAjudaModelView.class, required=true)
	private ITObservableList<TipoAjudaModelView> tiposAjuda;
	
	@TLabel(text="Status")
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "Ativado"), 
			@TCodeValue(code = "DESATIVADO", value = "Desativado")
	})
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4, required=true,
		node=@TNode(requestFocus=true, parse = true),
		radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
						@TRadioButtonField(text="Desativado", userData="DESATIVADO")
		})
	private SimpleStringProperty status;
	
	@TReaderHtml
	@TLabel(text="Versão")
	@TShowField()
	private SimpleIntegerProperty versionNum;
	
	
	
		
	@TReaderHtml
	@THTMLEditor(control=@TControl(prefHeight=350, parse = true))
	private SimpleStringProperty conteudo;
	
	
	
	public PessoaTermoAdesaoModelView(PessoaTermoAdesao entity) {
		super(entity);
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
	 * @return the tiposAjuda
	 */
	public ITObservableList<TipoAjudaModelView> getTiposAjuda() {
		return tiposAjuda;
	}

	/**
	 * @param tiposAjuda the tiposAjuda to set
	 */
	public void setTiposAjuda(ITObservableList<TipoAjudaModelView> tiposAjuda) {
		this.tiposAjuda = tiposAjuda;
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
	 * @return the versionNum
	 */
	public SimpleIntegerProperty getVersionNum() {
		return versionNum;
	}

	/**
	 * @param versionNum the versionNum to set
	 */
	public void setVersionNum(SimpleIntegerProperty versionNum) {
		this.versionNum = versionNum;
	}

}
