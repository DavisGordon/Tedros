package com.covidsemfome.module.doacao.model;

import java.util.Date;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Doacao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.module.voluntario.model.TipoAjudaModelView;
import com.covidsemfome.module.voluntario.process.LoadTipoAjudaOptionListProcess;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
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
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TOptionProcessType;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "Doação", showBreadcrumBar=false)
@TEjbService(serviceName = "IDoacaoControllerRemote", model=Doacao.class)
@TListViewPresenter(listViewMinWidth=380, listViewMaxWidth=380,
	paginator=@TPaginator(entityClass = Doacao.class, serviceName = "IDoacaoControllerRemote", show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Doação")))
@TSecurity(	id="COVSEMFOME_DOACAO_FORM", 
	appName = "#{app.name}", moduleName = "Gerenciar Campanha", viewName = "Doação",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class DoacaoModelView extends TEntityModelView<Doacao>{
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TTextReaderHtml(text="Doação", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Pessoa e tipo ajuda", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml
	@TLabel(text="Pessoa")
	@TOneSelectionModal(modelClass = Pessoa.class, modelViewClass = PessoaFindModelView.class,
	width=300, height=50)
	@THBox(	pane=@TPane(children={"pessoa","tipoAjuda"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="pessoa", priority=Priority.ALWAYS),
   				   		@TPriority(field="tipoAjuda", priority=Priority.ALWAYS) }))
	private SimpleObjectProperty<Pessoa> pessoa;
	
	
	@TReaderHtml
	@TLabel(text="Tipo ajuda")
	@TComboBoxField(firstItemTex="Selecione a forma de doação...", required=true, 
			control=@TControl(parse = true, minWidth=270),
			optionsList=@TOptionsList(optionsProcessClass = LoadTipoAjudaOptionListProcess.class, 
			entityClass=TipoAjuda.class, optionModelViewClass=TipoAjudaModelView.class, optionProcessType=TOptionProcessType.LIST_ALL ))
	private SimpleObjectProperty<TipoAjuda> tipoAjuda;
	
	@TReaderHtml
	@TLabel(text="Ação")
	@TOneSelectionModal(modelClass = Acao.class, modelViewClass = AcaoFindModelView.class)
	private SimpleObjectProperty<Acao> acao;
	
	@TReaderHtml
	@TLabel(text="Quantidade")
	@TNumberSpinnerField(maxValue = 1000000)
	@THBox(	pane=@TPane(children={"quantidade","valor","data"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="quantidade", priority=Priority.ALWAYS), 
   				   		@TPriority(field="valor", priority=Priority.ALWAYS),
   				   		@TPriority(field="data", priority=Priority.ALWAYS) }))
	private SimpleIntegerProperty quantidade;
	
	@TReaderHtml
	@TLabel(text = "Valor")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="Valor", parse = true))
	private SimpleDoubleProperty valor;
	
	
	@TReaderHtml
	@TLabel(text="Data")
	@TDatePickerField
	private SimpleObjectProperty<Date> data;
	
	@TReaderHtml
	@TLabel(text="Observação")
	@TTextAreaField(maxLength=300, control=@TControl(prefWidth=250, prefHeight=200, parse = true))
	private SimpleStringProperty observacao;
	
	
	public DoacaoModelView(Doacao entidade) {
		super(entidade);
		buildListener();
		buldDisplayText(entidade);
	}
	
	@Override
	public void reload(Doacao model) {
		super.reload(model);
		buildListener();
		buldDisplayText(model);
	}
	
	private void buldDisplayText(Doacao entidade) {
		if(!entidade.isNew()){
			String str =  ((pessoa.getValue()!=null) 
					? pessoa.getValue().getNome() 
							: "")  + ((tipoAjuda.getValue()!=null) 
					? " ("+tipoAjuda.getValue().getDescricao()+")"
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
					String str = (arg2!=null ? arg2.getNome() : "") + ((tipoAjuda.getValue()!=null) 
							? " ("+tipoAjuda.getValue().getDescricao()+")"
							: "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText", pessListener);
		}else
			pessoa.removeListener(pessListener);
		
		pessoa.addListener(pessListener);
		
		ChangeListener<TipoAjuda> taListener = super.getListenerRepository().get("displayText1");
		if(taListener==null){
			taListener = new ChangeListener<TipoAjuda>(){
				@Override
				public void changed(ObservableValue arg0, TipoAjuda arg1, TipoAjuda arg2) {
					String str =  ((pessoa.getValue()!=null) 
							? pessoa.getValue().getNome() 
									: "") 
							+ " ("+arg2.getDescricao()+")";
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText1", taListener);
		}else
			tipoAjuda.removeListener(taListener);
		
		tipoAjuda.addListener(taListener);
	}
	
	@Override
	public String toString() {
		String tipo = ""; 
		if(tipo.equals(""))
			return (getValor()!=null)? getValor().getValue().toString() : "";
		else
			return (tipo+": "+getValor()!=null)? getValor().getValue().toString() : "";
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
		return this.displayText;
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
	 * @return the tipoAjuda
	 */
	public SimpleObjectProperty<TipoAjuda> getTipoAjuda() {
		return tipoAjuda;
	}

	/**
	 * @param tipoAjuda the tipoAjuda to set
	 */
	public void setTipoAjuda(SimpleObjectProperty<TipoAjuda> tipoAjuda) {
		this.tipoAjuda = tipoAjuda;
	}

	/**
	 * @return the quantidade
	 */
	public SimpleIntegerProperty getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(SimpleIntegerProperty quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the valor
	 */
	public SimpleDoubleProperty getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(SimpleDoubleProperty valor) {
		this.valor = valor;
	}

	/**
	 * @return the data
	 */
	public SimpleObjectProperty<Date> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(SimpleObjectProperty<Date> data) {
		this.data = data;
	}

	/**
	 * @return the observacao
	 */
	public SimpleStringProperty getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(SimpleStringProperty observacao) {
		this.observacao = observacao;
	}


}
