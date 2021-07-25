/**
 * 
 */
package com.solidarity.module.produto.model;

import java.util.Date;

import com.solidarity.model.Cozinha;
import com.solidarity.model.Entrada;
import com.solidarity.model.EntradaItem;
import com.solidarity.model.Pessoa;
import com.solidarity.module.cozinha.model.CozinhaModelView;
import com.solidarity.module.pessoa.model.PessoaFindModelView;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.control.TValidator;
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
@TForm(name = "#{form.entrada.prod}", showBreadcrumBar=true, editCssId="")
@TEjbService(serviceName = "IEntradaControllerRemote", model=Entrada.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = Entrada.class, serviceName = "IEntradaControllerRemote",
			show=true, 
			orderBy = {	@TOption(text = "#{label.data}", value = "data"), 
						@TOption(text = "#{label.tipo}", value = "tipo")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.entrada.prod}")))
@TSecurity(	id="SOLIDARITY_ENTRADAPROD_FORM", 
	appName = "#{app.name}", moduleName = "#{module.administrativo}", viewName = "#{view.entrada.prod}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class EntradaModelView extends TEntityModelView<Entrada> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText = new SimpleStringProperty();
	
	@TAccordion(expandedPane="main", node=@TNode(id="estocavelAcc",parse = true),
			panes={
					@TTitledPane(text="#{label.principal}", node=@TNode(id="main",parse = true), expanded=true,
							fields={"textoCadastro", "tipo"}),
					@TTitledPane(text="#{label.detalhe}", node=@TNode(id="detail",parse = true),
						fields={"itens"})})
	@TTextReaderHtml(text="#{text.entrada.prod}", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form",parse = true))
	@TText(text="#{text.entrada.prod}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
	
	@TLabel(text="#{label.tipo}")
	@TReaderHtml(codeValues={@TCodeValue(code = "Compras", value = "#{label.compras}"), 
			@TCodeValue(code = "Doação", value = "#{label.donation}")})
	@THorizontalRadioGroup(required=true, alignment=Pos.CENTER_LEFT, spacing=4, 
	radioButtons={
			@TRadioButtonField(text = "#{label.compras}", userData = "Compras"),
			@TRadioButtonField(text = "#{label.donation}", userData = "Doação")
			})
	@TTrigger(triggerClass = EntradaTipoTrigger.class, targetFieldName = "doador", runAfterFormBuild=true)
	@THBox(	pane=@TPane(children={"data","tipo", "doador"}), spacing=10, fillHeight=false,
	hgrow=@THGrow(priority={@TPriority(field="data", priority=Priority.NEVER), 
		   		@TPriority(field="tipo", priority=Priority.NEVER), 
   				   		@TPriority(field="doador", priority=Priority.ALWAYS) }))
	private SimpleStringProperty tipo;
	
	@TReaderHtml
	@TLabel(text="#{label.donor}")
	@TFieldBox(node=@TNode(parse = true))
	@TOneSelectionModal(modelClass = Pessoa.class, modelViewClass = PessoaFindModelView.class,
	width=300, height=50)
	@TValidator(validatorClass = EntradaDoadorValidator.class, associatedFieldsName="tipo")
	private SimpleObjectProperty<Pessoa> doador;
	
	@TReaderHtml
	@TLabel(text="#{label.dataHora}")
	@TDatePickerField(required = true, node=@TNode(requestFocus=true, parse = true), dateFormat=DateTimeFormatBuilder.class)
	@TVBox(	pane=@TPane(children={"data", "cozinha"}), spacing=10, fillWidth=false,
	vgrow=@TVGrow(priority={@TPriority(field="data", priority=Priority.NEVER), 
   				   		@TPriority(field="cozinha", priority=Priority.ALWAYS) }))
	private SimpleObjectProperty<Date> data;
	
	@TReaderHtml
	@TLabel(text="#{label.local.prod}")
	@TComboBoxField(required=true, optionsList=@TOptionsList(entityClass=Cozinha.class, 
	optionModelViewClass=CozinhaModelView.class, serviceName = "ICozinhaControllerRemote"))
	private SimpleObjectProperty<Cozinha> cozinha;
	
	@TDetailReaderHtml(	label=@TLabel(text="#{label.produtos}"), 
			entityClass=EntradaItem.class, 
			modelViewClass=EntradaItemModelView.class)
	@TDetailListField(entityModelViewClass = EntradaItemModelView.class, entityClass = EntradaItem.class, required=true)
	@TModelViewCollectionType(modelClass=EntradaItem.class, modelViewClass=EntradaItemModelView.class)
	private ITObservableList<EntradaItemModelView> itens;
	
	
	public EntradaModelView(Entrada entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
	}
	
	@Override
	public void reload(Entrada model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(Entrada model) {
		if(!model.isNew()){
			String em = " "+ TInternationalizationEngine.getInstance(null).getString("#{label.em}")+" ";
			String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
					+ (tipo.getValue()!=null ? tipo.getValue() : "") 
					+ (data.getValue()!=null ? em + formataDataHora(data.getValue()) : "");
			displayText.setValue(str);
		}
	}

	private void buildListener() {
		String em = " "+ TInternationalizationEngine.getInstance(null).getString("#{label.em}")+" ";
		ChangeListener<Number> idListener = super.getListenerRepository().get("displayText");
		if(idListener==null){
			idListener = (arg0, arg1, arg2) -> {
					String str = (arg2==null ? "" : "(ID: "+arg2.toString()+") " ) 
							+ (tipo.getValue()!=null ? tipo.getValue() : "") 
							+ (data.getValue()!=null ?  em + formataDataHora(data.getValue()) : "");
					displayText.setValue(str);
				};
			
			super.addListener("displayText", idListener);
		}else
			id.removeListener(idListener);
		
		id.addListener(idListener);
		
		ChangeListener<String> tituloListener = super.getListenerRepository().get("displayText1");
		if(tituloListener==null){
			tituloListener = (arg0, arg1, arg2) -> {
					String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
							+ (arg2!=null ? arg2 : "") 
							+ (data.getValue()!=null ? em + formataDataHora(data.getValue()) : "");
					displayText.setValue(str);
				};
			super.addListener("displayText1", tituloListener);
		}else
			tipo.removeListener(tituloListener);
		
		tipo.addListener(tituloListener);
		
		ChangeListener<Date> dataListener = super.getListenerRepository().get("displayText2");
		if(dataListener==null){
			dataListener = (arg0, arg1, arg2) -> {
					String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
							+ (tipo.getValue()!=null ? tipo.getValue() : "") 
							+ (arg2!=null ?  em + formataDataHora(arg2) : "");
					displayText.setValue(str);
				};
				
			super.addListener("displayText2", dataListener);
		}else
			data.removeListener(dataListener);
		
		data.addListener(dataListener);
		
	}
	
	private String formataDataHora(Date data){
		return TDateUtil.getFormatedDate(data, TDateUtil.DDMMYYYY_HHMM);
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
	 * @return the doador
	 */
	public SimpleObjectProperty<Pessoa> getDoador() {
		return doador;
	}


	/**
	 * @param doador the doador to set
	 */
	public void setDoador(SimpleObjectProperty<Pessoa> doador) {
		this.doador = doador;
	}


	/**
	 * @return the itens
	 */
	public ITObservableList<EntradaItemModelView> getItens() {
		return itens;
	}


	/**
	 * @param itens the itens to set
	 */
	public void setItens(ITObservableList<EntradaItemModelView> itens) {
		this.itens = itens;
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


}
