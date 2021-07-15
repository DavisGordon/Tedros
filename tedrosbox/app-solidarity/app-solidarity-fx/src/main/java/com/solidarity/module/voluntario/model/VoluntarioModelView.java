/**
 * 
 */
package com.solidarity.module.voluntario.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.solidarity.model.Acao;
import com.solidarity.model.Pessoa;
import com.solidarity.model.TipoAjuda;
import com.solidarity.model.Voluntario;
import com.solidarity.module.doacao.model.AcaoFindModelView;
import com.solidarity.module.doacao.model.PessoaFindModelView;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TMultipleSelectionModal;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TColumnReader;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTableReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Voluntário inscrito na campanha ", showBreadcrumBar=true)
@TEjbService(serviceName = "IVoluntarioControllerRemote", model=Voluntario.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = Voluntario.class, serviceName = "IVoluntarioControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Voluntário inscrito")))
@TSecurity(	id="COVSEMFOME_CADVOL_FORM", 
	appName = "#{app.name}", moduleName = "Gerenciar Campanha", viewName = "Voluntários inscritos na campanha",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})

public class VoluntarioModelView extends TEntityModelView<Voluntario> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TTextReaderHtml(text="Voluntário na Ação/Campanha", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Selecione a ação o voluntário e o tipo de ajuda", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml
	@TLabel(text="Ação/Campanha")
	@TOneSelectionModal(modelClass = Acao.class, modelViewClass = AcaoFindModelView.class, 
	width=300, height=50, required=true)
	@THBox(	pane=@TPane(children={"acao","pessoa"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="acao", priority=Priority.ALWAYS),
   				   		@TPriority(field="pessoa", priority=Priority.ALWAYS) }))
	private SimpleObjectProperty<Acao> acao;
	
	@TReaderHtml
	@TLabel(text="Pessoa")
	@TOneSelectionModal(modelClass = Pessoa.class, modelViewClass = PessoaFindModelView.class,
	width=300, height=50, required=true)
	private SimpleObjectProperty<Pessoa> pessoa;	
	
	@TLabel(text="Tipos de Ajuda")
	@TTableReaderHtml(label=@TLabel(text="Tipo de Ajuda:"), 
		column = { 	@TColumnReader(field = "descricao", name = "Descricao"), 
					@TColumnReader(field = "tipoPessoa", name = "Tipo pessoa")})
	@TMultipleSelectionModal(modelClass = TipoAjuda.class, modelViewClass = TipoAjudaFindModelView.class, width=350, required=true)
	@TModelViewCollectionType(modelClass=TipoAjuda.class, modelViewClass=TipoAjudaModelView.class, required=true)
	private ITObservableList<TipoAjudaModelView> tiposAjuda;
	
	
	public VoluntarioModelView(Voluntario entity) {
		super(entity);
		buildListener();
		if(!entity.isNew()){
			String str =  ((pessoa.getValue()!=null) 
					? pessoa.getValue().getNome() + " na "
							: "")  + ((acao.getValue()!=null) 
					? "ação do dia "+formataDataHora(acao.getValue().getData())
						: "");
			displayText.setValue(str);
		}
	}
	
	@Override
	public void reload(Voluntario model) {
		super.reload(model);
		buildListener();
		
		if(!model.isNew()){
			String str =  ((pessoa.getValue()!=null) 
					? pessoa.getValue().getNome() + " na "
							: "")  + ((acao.getValue()!=null) 
					? "ação do dia "+formataDataHora(acao.getValue().getData())
						: "");
			displayText.setValue(str);
		}
	}

	private void buildListener() {
		
		
		ChangeListener<Pessoa> pessListener = super.getListenerRepository().get("displayText");
		if(pessListener==null){
			pessListener = new ChangeListener<Pessoa>(){
		
				@Override
				public void changed(ObservableValue arg0, Pessoa arg1, Pessoa arg2) {
					String str = (arg2!=null ? arg2.getNome() : "") + ((acao.getValue()!=null) 
							? " na ação do dia "+formataDataHora(acao.getValue().getData())
							: "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText", pessListener);
		}else
			pessoa.removeListener(pessListener);
		
		pessoa.addListener(pessListener);
		
		ChangeListener<Acao> acaoListener = super.getListenerRepository().get("displayText1");
		if(acaoListener==null){
			acaoListener = new ChangeListener<Acao>(){
				@Override
				public void changed(ObservableValue arg0, Acao arg1, Acao arg2) {
					String str =  ((pessoa.getValue()!=null) 
							? pessoa.getValue().getNome() + " na "
									: "") 
							+ (arg2!=null ? "ação do dia "+formataDataHora(arg2.getData()) : "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText1", acaoListener);
		}else
			acao.removeListener(acaoListener);
		
		acao.addListener(acaoListener);
		
	}
	
	private String formataDataHora(Date data){
		String pattern = "dd/MM/yyyy 'às' HH:mm";
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(data);
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return displayText;
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
	 * @return the acao
	 */
	public SimpleObjectProperty<Acao> getAcao() {
		return acao;
	}

	/**
	 * @param acao the acao to set
	 */
	public void setAcao(SimpleObjectProperty<Acao> acao) {
		this.acao = acao;
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
	 * @return the displayText
	 */
	public SimpleStringProperty getDisplayText() {
		return displayText;
	}

	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(SimpleStringProperty displayText) {
		this.displayText = displayText;
	}

}
