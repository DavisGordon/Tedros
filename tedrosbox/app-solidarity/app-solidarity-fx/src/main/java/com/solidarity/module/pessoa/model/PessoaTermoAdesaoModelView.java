/**
 * 
 */
package com.solidarity.module.pessoa.model;

import com.solidarity.model.PessoaTermoAdesao;
import com.solidarity.model.TipoAjuda;
import com.solidarity.module.voluntario.model.TipoAjudaModelView;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TColumnReader;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTableReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.TOptionProcessType;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(showBreadcrumBar=true, name = "#{label.termo.adesao}")
@TDetailListViewPresenter(presenter=@TPresenter(
		behavior = @TBehavior(type = TDetailCrudViewBehavior.class,
		newAction=PessoaTermoAdesaoNewAction.class, printAction=PessoaTermoAdesaoPrintAction.class), 
		decorator = @TDecorator(type = TDetailCrudViewDecorator.class, viewTitle="#{view.termos.adesao}", 
		buildPrintButton=true, printButtonText="#{button.exportar.pdf}")))
public class PessoaTermoAdesaoModelView extends TEntityModelView<PessoaTermoAdesao> {

	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="main", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="#{label.detalhes}", node=@TNode(id="main",parse = true), expanded=true,
							fields={"textoCadastro", "status", "tiposAjuda"}),
					@TTitledPane(text="#{label.termo.adesao}", node=@TNode(id="detail",parse = true),
						fields={"conteudo"})})
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{label.detalhes.termo}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TLabel(text="Status")
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "#{label.ativado}"), 
			@TCodeValue(code = "DESATIVADO", value = "#{label.desativado}")
	})
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4, required=true,
		node=@TNode(requestFocus=true, parse = true),
		radioButtons = {@TRadioButtonField(text="#{label.ativado}", userData="ATIVADO"), 
						@TRadioButtonField(text="#{label.desativado}", userData="DESATIVADO")
		})
	@THBox(pane=@TPane(	children={"status", "versionNum"}), spacing=10, fillHeight=true, 
	hgrow=@THGrow(priority={@TPriority(field="status", priority=Priority.ALWAYS),
							@TPriority(field="versionNum", priority=Priority.ALWAYS)}))
	private SimpleStringProperty status;
	
	@TReaderHtml
	@TLabel(text="#{label.versao}")
	@TShowField()
	private SimpleIntegerProperty versionNum;
	
	
	@TLabel(text="#{label.tipos.ajuda}")
	@TTableReaderHtml(label=@TLabel(text="#{label.tipos.ajuda}"), 
		column = { 	@TColumnReader(field = "descricao", name = "#{label.descricao}"), 
					@TColumnReader(field = "tipoPessoa", name = "#{label.tipo}")})
	@TPickListField(selectedLabel="#{label.selected}", 
		sourceLabel="#{label.opcoes}", required=true,
		optionsList=@TOptionsList(entityClass=TipoAjuda.class,
					optionModelViewClass=TipoAjudaModelView.class,
					/*exampleEntityBuilder=TipoAjudaExampleBuilder.class,*/
					optionProcessType=TOptionProcessType.LIST_ALL,
					serviceName = "ITipoAjudaControllerRemote"))
	@TModelViewCollectionType(modelClass=TipoAjuda.class, modelViewClass=TipoAjudaModelView.class, required=true)
	private ITObservableList<TipoAjudaModelView> tiposAjuda;
		
	@THTMLEditor
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
