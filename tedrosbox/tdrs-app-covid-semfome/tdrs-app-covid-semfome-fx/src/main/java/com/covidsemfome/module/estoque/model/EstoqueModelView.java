package com.covidsemfome.module.estoque.model;

import java.util.Date;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Entrada;
import com.covidsemfome.model.Estoque;
import com.covidsemfome.model.EstoqueItem;
import com.covidsemfome.model.Producao;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellFactory;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TDetailReaderHtml;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.util.TDateUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Priority;

@TFormReaderHtml
@TForm(name = "Estoque")
@TEjbService(serviceName = "IEstoqueControllerRemote", model=Estoque.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = Estoque.class, serviceName = "IEstoqueControllerRemote",
		show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Estoque", 
		buildNewButton=false, buildDeleteButton=false, buildCollapseButton=false
	)))
@TSecurity(	id="COVSEMFOME_ESTOQUE_FORM", 
	appName = "#{app.name}", moduleName = "Administrativo", viewName = "Estoque",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class EstoqueModelView extends TEntityModelView<Estoque> {

	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TReaderHtml
	@TLabel(text="Entrada")
	@TShowField(fields= {@TField(name = "data", label="Data"), @TField(name = "tipo", label="Tipo")})
	@THBox(	pane=@TPane(children={"entradaRef","producaoRef","data","cozinha"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="entradaRef", priority=Priority.NEVER), 
		   		@TPriority(field="producaoRef", priority=Priority.NEVER),
		   		@TPriority(field="data", priority=Priority.NEVER), 
			   		@TPriority(field="cozinha", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Entrada> entradaRef;
	
	@TLabel(text="Producao")
	@TShowField(fields= {@TField(name = "data", label="Data"), @TField(name = "acao", label="Acao")})
	private SimpleObjectProperty<Producao> producaoRef;
	
	@TReaderHtml
	@TLabel(text="Cozinha:")
	@TShowField(fields= {@TField(name = "nome"), @TField(name = "telefone", label="Tel:", pattern="(99) #9999-9999")})
	private SimpleObjectProperty<Cozinha> cozinha;
	
	@TReaderHtml
	@TLabel(text="Data e Hora")
	@TShowField(fields= {@TField(pattern = TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> data;
	
	@TReaderHtml
	@TLabel(text="Observação")
	@TTextAreaField(maxLength=600, control=@TControl(prefWidth=250, prefHeight=100, parse = true))
	private SimpleStringProperty observacao;
	
	@TDetailReaderHtml(	label=@TLabel(text="Produtos"), 
			entityClass=EstoqueItem.class, 
			modelViewClass=EstoqueItemModelView.class)
	@TTableView(editable=true, tableMenuButtonVisible=true,
	columns = { @TTableColumn(cellValue="produto", text = "Produto", prefWidth=50, resizable=true), 
				@TTableColumn(cellValue="qtdMinima", text = "Qtd.Min", prefWidth=20, resizable=true),
				@TTableColumn(cellValue="qtdInicial", text = "Qtd. Inicial", resizable=true), 
				@TTableColumn(cellValue="qtdCalculado", text = "Qtd. Calculado", resizable=true), 
				@TTableColumn(cellValue="qtdAjuste", text = "Ajuste", resizable=true, editable=true,
						cellFactory=@TCellFactory(parse = true, callBack=@TCallbackFactory(parse=true, value=QtdAjusteCallBack.class))), 
				@TTableColumn(cellValue="vlrAjustado", text = "Total", resizable=true)})
	@TModelViewCollectionType(modelClass=EstoqueItem.class, modelViewClass=EstoqueItemModelView.class)
	private ITObservableList<EstoqueItemModelView> itens;
	
	
	public EstoqueModelView(Estoque entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
	}
	
	@Override
	public void reload(Estoque model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(Estoque model) {
		if(!model.isNew()){
			String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " )
					+ (cozinha.getValue()!=null ? " ("+cozinha.getValue()+")" : "") 
					+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
			displayText.setValue(str);
		}
	}
	
	private String formataDataHora(Date data){
		return TDateUtil.getFormatedDate(data, TDateUtil.DDMMYYYY);
	}
	
	private void buildListener() {
		
		/*ChangeListener<Produto> idListener = super.getListenerRepository().getListener("displayText");
		if(idListener==null){
			idListener = (arg0, arg1, arg2) -> {
					String str = (arg2==null ? "" : arg2.toString() ) 
							+ (cozinha.getValue()!=null ? " ("+cozinha.getValue()+")" : "") ;
					displayText.setValue(str);
				};
			
			super.addListener("displayText", idListener);
		}else
			produto.removeListener(idListener);
		
		produto.addListener(idListener);*/
		
		ChangeListener<Cozinha> tituloListener = super.getListenerRepository().getListener("displayText1");
		if(tituloListener==null){
			tituloListener = (arg0, arg1, arg2) -> {
					String str = /*(produto.getValue()==null ? "" : produto.getValue().toString() )  
							+*/ (arg2!=null ? " ("+arg2+")" : "") ;
					displayText.setValue(str);
				};
			super.addListener("displayText1", tituloListener);
		}else
			cozinha.removeListener(tituloListener);
		
		cozinha.addListener(tituloListener);
		
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


	@Override
	public SimpleStringProperty getDisplayProperty() {
		return displayText;
	}

	/**
	 * @return the entradaRef
	 */
	public SimpleObjectProperty<Entrada> getEntradaRef() {
		return entradaRef;
	}

	/**
	 * @param entradaRef the entradaRef to set
	 */
	public void setEntradaRef(SimpleObjectProperty<Entrada> entradaRef) {
		this.entradaRef = entradaRef;
	}

	/**
	 * @return the producaoRef
	 */
	public SimpleObjectProperty<Producao> getProducaoRef() {
		return producaoRef;
	}

	/**
	 * @param producaoRef the producaoRef to set
	 */
	public void setProducaoRef(SimpleObjectProperty<Producao> producaoRef) {
		this.producaoRef = producaoRef;
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

	/**
	 * @return the itens
	 */
	public ITObservableList<EstoqueItemModelView> getItens() {
		return itens;
	}

	/**
	 * @param itens the itens to set
	 */
	public void setItens(ITObservableList<EstoqueItemModelView> itens) {
		this.itens = itens;
	}

}
