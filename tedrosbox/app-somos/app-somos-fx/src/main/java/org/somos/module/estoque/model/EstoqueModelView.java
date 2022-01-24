package org.somos.module.estoque.model;

import java.util.Date;

import org.somos.model.Cozinha;
import org.somos.model.Entrada;
import org.somos.model.Estoque;
import org.somos.model.EstoqueItem;
import org.somos.model.Saida;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellFactory;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TFieldSet;
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
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.util.TDateUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "Estoque gerado automaticamente", showBreadcrumBar=true)
@TEjbService(serviceName = "IEstoqueControllerRemote", model=Estoque.class)
@TListViewPresenter(listViewMinWidth=400,
	paginator=@TPaginator(entityClass = Estoque.class, serviceName = "IEstoqueControllerRemote",
		show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="Ver Estoque", 
		buildNewButton=false, buildDeleteButton=false, buildCollapseButton=false
	)))
@TSecurity(	id="SOMOS_ESTOQUE_FORM", 
	appName = "#{somos.name}", moduleName = "Administrativo", viewName = "Estoque",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
			TAuthorizationType.READ, TAuthorizationType.SAVE})
public class EstoqueModelView extends TEntityModelView<Estoque> {

	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TTextReaderHtml(text="Estoque", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	/*@TText(text="Estoque", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, node=@TNode(id="t-form-title-text", parse = true))*/
	private SimpleStringProperty header;
	
	@TReaderHtml
	@TLabel(text="Local de produção:")
	@THBox(	pane=@TPane(children={"entradaRef","saidaRef","data","cozinha"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="entradaRef", priority=Priority.NEVER), 
		   		@TPriority(field="saidaRef", priority=Priority.NEVER),
		   		@TPriority(field="data", priority=Priority.ALWAYS), 
			   		@TPriority(field="cozinha", priority=Priority.ALWAYS)}))
	@TShowField(fields= {@TField(name = "nome", label="Cozinha da"), @TField(name = "telefone", label="Tel:", pattern="(99) #9999-9999")})
	private SimpleObjectProperty<Cozinha> cozinha;
	
	@TReaderHtml
	@TShowField(fields= {@TField(name = "data", label="Data"), @TField(name = "tipo", label="Tipo")})
	@TFieldSet(fields = { "entradaRef" }, legend = "Entrada", region=@TRegion(maxWidth=200, parse = true))
	private SimpleObjectProperty<Entrada> entradaRef;
	
	@TReaderHtml
	@TFieldSet(fields = { "saidaRef" }, legend = "Saida", region=@TRegion(maxWidth=300, parse = true))
	@TShowField(fields= {@TField(name = "data", label="Data"), @TField(name = "acao", label="Ação")})
	private SimpleObjectProperty<Saida> saidaRef;
	
	
	
	@TReaderHtml
	@TLabel(text="Data e Hora")
	@TShowField(fields= {@TField(pattern = TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> data;
	
	@TReaderHtml
	@TLabel(text="Observação")
	@TTextAreaField(maxLength=600, wrapText=true, control=@TControl(prefWidth=250, prefHeight=100, parse = true))
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
		loadDisplayText(entity);
	}
	
	@Override
	public void reload(Estoque model) {
		super.reload(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(Estoque model) {
		if(!model.isNew()){
			String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " )
					+ (entradaRef.getValue()!=null ? entradaRef.getValue() : "")
					+ (saidaRef.getValue()!=null ? saidaRef.getValue() : "");
			displayText.setValue(str);
		}
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

	/**
	 * @return the header
	 */
	public SimpleStringProperty getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(SimpleStringProperty header) {
		this.header = header;
	}

	/**
	 * @return the saidaRef
	 */
	public SimpleObjectProperty<Saida> getSaidaRef() {
		return saidaRef;
	}

	/**
	 * @param saidaRef the saidaRef to set
	 */
	public void setSaidaRef(SimpleObjectProperty<Saida> saidaRef) {
		this.saidaRef = saidaRef;
	}

}
