/**
 * 
 */
package com.covidsemfome.module.acao.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.covidsemfome.model.Acao;
import com.covidsemfome.module.acao.process.AcaoProcess;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelPosition;
import com.tedros.fxapi.annotation.control.TLongField;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TEntityCrudViewWithListView;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMainCrudViewWithListViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMainCrudViewWithListViewDecorator;
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

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Ação / Campanha", showBreadcrumBar=false)
@TEntityProcess(process = AcaoProcess.class, entity=Acao.class)
@TEntityCrudViewWithListView(listViewMinWidth=350,
paginator=@TPaginator(entityClass = Acao.class, serviceName = "IAcaoControllerRemote",
			show=true, showSearchField=true, searchFieldName="titulo",
			orderBy = {@TOption(text = "Codigo", value = "id"), 
					@TOption(text = "Titulo", value = "titulo"), 
					@TOption(text = "Data", value = "data")}),
presenter=@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TMainCrudViewWithListViewBehavior.class), 
			decorator = @TDecorator(type = TMainCrudViewWithListViewDecorator.class, 
									viewTitle="Ação / Campanha", listTitle="#{label.select}")))
@TSecurity(	id="COVSEMFOME_ACAO_FORM", 
			appName = "#{app.name}", moduleName = "Gerenciar Campanha", viewName = "Ação / Campanha",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class AcaoModelView extends TEntityModelView<Acao> {
	
	@TReaderHtml
	@TLabel(text="Codigo:", position=TLabelPosition.LEFT)
	@TLongField(maxLength=6,node=@TNode(parse = true, disable=true) )
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText = new SimpleStringProperty();
	
	@TTextReaderHtml(text="Ação / Campanha", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Ação / Campanha", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml
	@TLabel(text="Titulo/Local")
	@TTextField(maxLength=100, 
	textInputControl=@TTextInputControl(promptText="Insira um titulo ou o local", parse = true),
	required=true)
	private SimpleStringProperty titulo;
	
	@TReaderHtml
	@TLabel(text="Data e Hora")
	@TDatePickerField(required = true, dateFormat=DateTimeFormatBuilder.class)
	@THBox(	pane=@TPane(children={"data","status"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="data", priority=Priority.ALWAYS), 
   				   		@TPriority(field="status", priority=Priority.ALWAYS) }))
	private SimpleObjectProperty<Date> data;
	
	@TLabel(text="Status")
	@TReaderHtml(codeValues={@TCodeValue(code = "Agendada", value = "Agendada"), 
			@TCodeValue(code = "Cancelada", value = "Cancelada"),
			@TCodeValue(code = "Executada", value = "Executada")})
	@THorizontalRadioGroup(required=true, alignment=Pos.CENTER_LEFT, spacing=4, 
	radioButtons={
			@TRadioButtonField(text = "Agendada", userData = "Agendada"),
			@TRadioButtonField(text = "Cancelada", userData = "Cancelada"), 
			@TRadioButtonField(text = "Executada", userData = "Executada")
			})
	private SimpleStringProperty status;
	
	@TReaderHtml
	@TLabel(text="Qtd. Minima de voluntários")
	@TNumberSpinnerField(maxValue = 150)
	@THBox(	pane=@TPane(children={"qtdMinVoluntarios","qtdMaxVoluntarios"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="qtdMinVoluntarios", priority=Priority.ALWAYS), 
   				   		@TPriority(field="qtdMaxVoluntarios", priority=Priority.ALWAYS) }))
	private SimpleIntegerProperty qtdMinVoluntarios;
	
	@TReaderHtml
	@TLabel(text="Qtd. Maxima de voluntários")
	@TNumberSpinnerField(maxValue = 150)
	private SimpleIntegerProperty qtdMaxVoluntarios;
	
	@TReaderHtml
	@TLabel(text = "Valor previsto")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="Valor previsto", parse = true))
	@THBox(	pane=@TPane(children={"vlrPrevisto","vlrArrecadado","vlrExecutado"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="vlrPrevisto", priority=Priority.ALWAYS), 
   				   		@TPriority(field="vlrArrecadado", priority=Priority.ALWAYS),
   				   		@TPriority(field="vlrExecutado", priority=Priority.ALWAYS) }))
	private SimpleDoubleProperty vlrPrevisto;
	
	@TReaderHtml
	@TLabel(text = "Valor arrecadado")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="Valor arrecadado", parse = true))
	private SimpleDoubleProperty vlrArrecadado;
	
	@TReaderHtml
	@TLabel(text = "Valor executado")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="Valor executado", parse = true))
	private SimpleDoubleProperty vlrExecutado;
	
	@TReaderHtml
	@TLabel(text="Descricão")
	@TTextAreaField(required=true, maxLength=2000, control=@TControl(prefWidth=250, prefHeight=150, parse = true))
	private SimpleStringProperty descricao;
	
	@TReaderHtml
	@TLabel(text="Observação")
	@TTextAreaField(maxLength=600, control=@TControl(prefWidth=250, prefHeight=100, parse = true))
	private SimpleStringProperty observacao;
	
	

	public AcaoModelView(Acao entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
	}
	
	@Override
	public void reload(Acao model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(Acao model) {
		if(!model.isNew()){
			String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
					+ (titulo.getValue()!=null ? titulo.getValue() : "") 
					+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
			displayText.setValue(str);
		}
	}
	
	private void buildListener() {
		
		ChangeListener idListener =  super.getListenerRepository().getListener("displayText");
		if(idListener==null){
			idListener = new ChangeListener<Long>(){
		
				public void changed(ObservableValue arg0, Long arg1, Long arg2) {
					String str = (arg2==null ? "" : "(ID: "+arg2.toString()+") " ) 
							+ (titulo.getValue()!=null ? titulo.getValue() : "") 
							+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
					displayText.setValue(str);
				}
			};
			super.addListener("displayText", idListener);
		}else
			id.removeListener(idListener);
		
		id.addListener(idListener);
		
		ChangeListener<String> tituloListener = super.getListenerRepository().getListener("displayText1");
		if(tituloListener==null){
			tituloListener = new ChangeListener<String>(){
				@Override
				public void changed(ObservableValue arg0, String arg1, String arg2) {
					String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
							+ (arg2!=null ? arg2 : "") 
							+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText1", tituloListener);
		}else
			titulo.removeListener(tituloListener);
		
		titulo.addListener(tituloListener);
		
		ChangeListener<Date> dataListener = super.getListenerRepository().getListener("displayText2");
		if(dataListener==null){
			dataListener = new ChangeListener<Date>(){
				@Override
				public void changed(ObservableValue arg0, Date arg1, Date arg2) {
					String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
							+ (titulo.getValue()!=null ? titulo.getValue() : "") 
							+ (arg2!=null ? " em "+formataDataHora(arg2) : "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText2", dataListener);
		}else
			data.removeListener(dataListener);
		
		data.addListener(dataListener);
		
	}
	
	private String formataDataHora(Date data){
		String pattern = "dd/MM/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(data);
	}
	
	@Override
	public void removeAllListener() {
		super.removeAllListener();
	}
	
	public AcaoModelView() {
		super(new Acao());
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
		return displayText;
	}

	
	
	public AcaoModelView getNewInstance(){
		return new AcaoModelView(new Acao());
	}
	
	@Override
	public String toString() {
		return (getTitulo()!=null)? getTitulo().getValue() : "";	
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
	 * @return the qtdMinVoluntarios
	 */
	public SimpleIntegerProperty getQtdMinVoluntarios() {
		return qtdMinVoluntarios;
	}

	/**
	 * @param qtdMinVoluntarios the qtdMinVoluntarios to set
	 */
	public void setQtdMinVoluntarios(SimpleIntegerProperty qtdMinVoluntarios) {
		this.qtdMinVoluntarios = qtdMinVoluntarios;
	}

	/**
	 * @return the qtdMaxVoluntarios
	 */
	public SimpleIntegerProperty getQtdMaxVoluntarios() {
		return qtdMaxVoluntarios;
	}

	/**
	 * @param qtdMaxVoluntarios the qtdMaxVoluntarios to set
	 */
	public void setQtdMaxVoluntarios(SimpleIntegerProperty qtdMaxVoluntarios) {
		this.qtdMaxVoluntarios = qtdMaxVoluntarios;
	}

	/**
	 * @return the vlrPrevisto
	 */
	public SimpleDoubleProperty getVlrPrevisto() {
		return vlrPrevisto;
	}

	/**
	 * @param vlrPrevisto the vlrPrevisto to set
	 */
	public void setVlrPrevisto(SimpleDoubleProperty vlrPrevisto) {
		this.vlrPrevisto = vlrPrevisto;
	}

	/**
	 * @return the vlrArrecadado
	 */
	public SimpleDoubleProperty getVlrArrecadado() {
		return vlrArrecadado;
	}

	/**
	 * @param vlrArrecadado the vlrArrecadado to set
	 */
	public void setVlrArrecadado(SimpleDoubleProperty vlrArrecadado) {
		this.vlrArrecadado = vlrArrecadado;
	}

	/**
	 * @return the vlrExecutado
	 */
	public SimpleDoubleProperty getVlrExecutado() {
		return vlrExecutado;
	}

	/**
	 * @param vlrExecutado the vlrExecutado to set
	 */
	public void setVlrExecutado(SimpleDoubleProperty vlrExecutado) {
		this.vlrExecutado = vlrExecutado;
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


}
