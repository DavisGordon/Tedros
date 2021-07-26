/**
 * 
 */
package com.covidsemfome.module.produto.model;

import java.util.Date;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Saida;
import com.covidsemfome.model.SaidaItem;
import com.covidsemfome.module.cozinha.model.CozinhaModelView;
import com.covidsemfome.module.doacao.model.AcaoFindModelView;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
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
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.util.TDateUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Saida de produtos do estoque", showBreadcrumBar=true, editCssId="")
@TEjbService(serviceName = "ISaidaControllerRemote", model=Saida.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = Saida.class, serviceName = "ISaidaControllerRemote",
			show=true, 
			orderBy = {	@TOption(text = "Data", value = "data"), 
						@TOption(text = "Tipo", value = "tipo")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Saida de Produtos")))
@TSecurity(	id="COVSEMFOME_SAIDAPROD_FORM", 
	appName = "#{app.name}", moduleName = "Administrativo", viewName = "Saida de Produtos",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SaidaModelView extends TEntityModelView<Saida> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText = new SimpleStringProperty();
	
	@TAccordion(expandedPane="main", node=@TNode(id="estocavelAcc",parse = true),
			panes={
					@TTitledPane(text="Principal", node=@TNode(id="main",parse = true), expanded=true,
							fields={"textoCadastro", "observacao"}),
					@TTitledPane(text="Detalhe", node=@TNode(id="detail",parse = true),
						fields={"itens"})})
	@TTextReaderHtml(text="Saida de Produtos", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form",parse = true))
	@TText(text="Informar os produtos que sairam do estoque", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	
	@TReaderHtml
	@TLabel(text="Observação")
	@TTextAreaField(maxLength=600, control=@TControl(prefWidth=250, prefHeight=100, parse = true))
	@THBox(	pane=@TPane(children={"acao", "observacao"}), spacing=10, fillHeight=false,
	hgrow=@THGrow(priority={@TPriority(field="acao", priority=Priority.NEVER), 
   				   		@TPriority(field="observacao", priority=Priority.ALWAYS) }))
	private SimpleStringProperty observacao;
	
	@TReaderHtml
	@TLabel(text="Ação/Campanha")
	@TOneSelectionModal(modelClass = Acao.class, modelViewClass = AcaoFindModelView.class, 
	width=300, height=50, required=true)
	@TVBox(	pane=@TPane(children={"acao", "data"}), spacing=10, 
	vgrow=@TVGrow(priority={@TPriority(field="acao", priority=Priority.NEVER), 
   				   		@TPriority(field="data", priority=Priority.ALWAYS) }))
	private SimpleObjectProperty<Acao> acao;
	
	@TReaderHtml
	@TLabel(text="Data")
	@TDatePickerField(required = true, dateFormat=DateTimeFormatBuilder.class)
	@THBox(	pane=@TPane(children={"data", "cozinha"}), spacing=10, fillHeight=false,
	hgrow=@THGrow(priority={@TPriority(field="data", priority=Priority.NEVER), 
   				   		@TPriority(field="cozinha", priority=Priority.ALWAYS) }))
	private SimpleObjectProperty<Date> data;
	
	@TReaderHtml
	@TLabel(text="Cozinha:")
	@TComboBoxField(required=true, optionsList=@TOptionsList(entityClass=Cozinha.class, 
	optionModelViewClass=CozinhaModelView.class, serviceName = "ICozinhaControllerRemote"))
	private SimpleObjectProperty<Cozinha> cozinha;
	
	
	@TDetailReaderHtml(	label=@TLabel(text="Itens"), 
			entityClass=SaidaItem.class, 
			modelViewClass=SaidaItemModelView.class)
	@TDetailListField(entityModelViewClass = SaidaItemModelView.class, entityClass = SaidaItem.class, required=true)
	@TModelViewCollectionType(modelClass=SaidaItem.class, modelViewClass=SaidaItemModelView.class)
	private ITObservableList<SaidaItemModelView> itens;
	
	
	public SaidaModelView(Saida entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
	}
	
	@Override
	public void reload(Saida model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(Saida model) {
		if(!model.isNew()){
			String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
					+ (acao.getValue()!=null ? acao.getValue() : "") 
					+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
			displayText.setValue(str);
		}
	}

	private void buildListener() {
		
		ChangeListener<Number> idListener = super.getListenerRepository().get("displayText");
		if(idListener==null){
			idListener = (arg0, arg1, arg2) -> {
					String str = (arg2==null ? "" : "(ID: "+arg2.toString()+") " ) 
							+ (acao.getValue()!=null ? acao.getValue() : "") 
							+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
					displayText.setValue(str);
				};
			
			super.addListener("displayText", idListener);
		}else
			id.removeListener(idListener);
		
		id.addListener(idListener);
		
		ChangeListener<Acao> tituloListener = super.getListenerRepository().get("displayText1");
		if(tituloListener==null){
			tituloListener = (arg0, arg1, arg2) -> {
					String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
							+ (arg2!=null ? arg2 : "") 
							+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
					displayText.setValue(str);
				};
			super.addListener("displayText1", tituloListener);
		}else
			acao.removeListener(tituloListener);
		
		acao.addListener(tituloListener);
		
		ChangeListener<Date> dataListener = super.getListenerRepository().get("displayText2");
		if(dataListener==null){
			dataListener = (arg0, arg1, arg2) -> {
					String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
							+ (acao.getValue()!=null ? acao.getValue() : "") 
							+ (arg2!=null ? " em "+formataDataHora(arg2) : "");
					displayText.setValue(str);
				};
				
			super.addListener("displayText2", dataListener);
		}else
			data.removeListener(dataListener);
		
		data.addListener(dataListener);
		
	}
	
	private String formataDataHora(Date data){
		return TDateUtil.getFormatedDate(data, TDateUtil.DDMMYYYY);
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


	@Override
	public SimpleStringProperty getDisplayProperty() {
		return displayText;
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
	 * @return the cozinha
	 */
	public SimpleObjectProperty<Cozinha> getCozinha() {
		return cozinha;
	}

	/**
	 * @param cozinha the cozinha to set
	 */
	public void setCozinha(SimpleObjectProperty<Cozinha> cozinha) {
		this.cozinha = cozinha;
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

	/**
	 * @return the itens
	 */
	public ITObservableList<SaidaItemModelView> getItens() {
		return itens;
	}

	/**
	 * @param itens the itens to set
	 */
	public void setItens(ITObservableList<SaidaItemModelView> itens) {
		this.itens = itens;
	}


}
