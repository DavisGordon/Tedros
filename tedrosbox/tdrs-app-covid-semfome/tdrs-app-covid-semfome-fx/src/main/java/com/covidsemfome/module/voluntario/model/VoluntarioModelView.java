/**
 * 
 */
package com.covidsemfome.module.voluntario.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.model.Voluntario;
import com.covidsemfome.module.acao.model.AcaoModelView;
import com.covidsemfome.module.pessoa.model.PessoaModelView;
import com.covidsemfome.module.voluntario.process.LoadAcaoOptionListProcess;
import com.covidsemfome.module.voluntario.process.LoadPessoaOptionListProcess;
import com.covidsemfome.module.voluntario.process.LoadTipoAjudaOptionListProcess;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.form.TForm;
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
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TOptionProcessType;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
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
	
	@TTextReaderHtml(text="Ação/Campanha e pessoa", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Ação/Campanha e pessoa", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml
	@TLabel(text="Ação/Campanha")
	@TComboBoxField(firstItemTex="Selecione uma ação...", required=true, 
			control=@TControl(parse = true, minWidth=400),
			optionsList=@TOptionsList(optionsProcessClass = LoadAcaoOptionListProcess.class, 
			entityClass=Acao.class, optionModelViewClass=AcaoModelView.class, optionProcessType=TOptionProcessType.LIST_ALL ))
	private SimpleObjectProperty<Acao> acao;
	
	@TReaderHtml
	@TLabel(text="Pessoa")
	@TComboBoxField(firstItemTex="Selecione uma Pessoa...", required=true, 
			control=@TControl(parse = true, minWidth=400),
			optionsList=@TOptionsList(optionsProcessClass = LoadPessoaOptionListProcess.class, 
			entityClass=Pessoa.class, optionModelViewClass=PessoaModelView.class, optionProcessType=TOptionProcessType.LIST_ALL ))
	private SimpleObjectProperty<Pessoa> pessoa;
	
	
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Tipo de Ajuda", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
		node=@TNode(id="t-form-title-text", parse = true), mode=TViewMode.EDIT)
	private SimpleStringProperty tipoAjudaText;
	
	
	@TTableReaderHtml(label=@TLabel(text="Tipo de Ajuda:"), 
		column = { 	@TColumnReader(field = "descricao", name = "Descricao"), 
					@TColumnReader(field = "tipoPessoa", name = "Tipo pessoa")})
	@TPickListField(selectedLabel="#{label.selected}", 
			sourceLabel="Tipo Ajuda", required=true,
			optionsList=@TOptionsList(entityClass=TipoAjuda.class,
						optionModelViewClass=TipoAjudaModelView.class, 
						optionsProcessClass=LoadTipoAjudaOptionListProcess.class))
	@TModelViewCollectionType(modelClass=TipoAjuda.class, modelViewClass=TipoAjudaModelView.class, required=true)
	private ITObservableList<TipoAjudaModelView> tiposAjuda;
	
	
	public VoluntarioModelView(Voluntario entity) {
		super(entity);
		buildListener();
		if(!entity.isNew()){
			String str =  ((pessoa.getValue()!=null) 
					? pessoa.getValue().getNome() + " na "
							: "")  + ((acao.getValue()!=null) 
					? "acão do dia "+formataDataHora(acao.getValue().getData())
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
					? "acão do dia "+formataDataHora(acao.getValue().getData())
						: "");
			displayText.setValue(str);
		}
	}

	private void buildListener() {
		
		
		ChangeListener<Pessoa> pessListener = super.getListenerRepository().getListener("displayText");
		if(pessListener==null){
			pessListener = new ChangeListener<Pessoa>(){
		
				@Override
				public void changed(ObservableValue arg0, Pessoa arg1, Pessoa arg2) {
					String str =  arg2.getNome() + ((acao.getValue()!=null) 
							? " na acão do dia "+formataDataHora(acao.getValue().getData())
							: "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText", pessListener);
		}else
			pessoa.removeListener(pessListener);
		
		pessoa.addListener(pessListener);
		
		ChangeListener<Acao> acaoListener = super.getListenerRepository().getListener("displayText1");
		if(acaoListener==null){
			acaoListener = new ChangeListener<Acao>(){
				@Override
				public void changed(ObservableValue arg0, Acao arg1, Acao arg2) {
					String str =  ((pessoa.getValue()!=null) 
							? pessoa.getValue().getNome() + " na "
									: "") 
							+ "acão do dia "+formataDataHora(arg2.getData());
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

	/**
	 * @return the tipoAjudaText
	 */
	public SimpleStringProperty getTipoAjudaText() {
		return tipoAjudaText;
	}

	/**
	 * @param tipoAjudaText the tipoAjudaText to set
	 */
	public void setTipoAjudaText(SimpleStringProperty tipoAjudaText) {
		this.tipoAjudaText = tipoAjudaText;
	}

}
