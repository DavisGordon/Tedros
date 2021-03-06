package com.solidarity.module.estoque.model;

import com.solidarity.model.Cozinha;
import com.solidarity.model.EstoqueConfig;
import com.solidarity.model.Produto;
import com.solidarity.module.cozinha.model.CozinhaModelView;
import com.solidarity.module.produto.model.ProdutoFindModelView;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TOptionsList;
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
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.domain.TZeroValidation;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

@TFormReaderHtml
@TForm(name = "#{view.estoque.inicial.title}", showBreadcrumBar=true)
@TEjbService(serviceName = "IEstoqueConfigControllerRemote", model=EstoqueConfig.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = EstoqueConfig.class, serviceName = "IEstoqueConfigControllerRemote",
			show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.estoque.inicial.title}")))
@TSecurity(	id="SOLIDARITY_ESTOQUECONF_FORM", 
	appName = "#{app.name}", moduleName = "#{module.administrativo}", viewName = "#{view.estoque.inicial.title}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class EstoqueConfigModelView extends TEntityModelView<EstoqueConfig> {

	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TTextReaderHtml(text="#{text.estoque.conf}", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="#{text.estoque.conf.header}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TReaderHtml
	@TLabel(text="#{label.produto}")
	@TOneSelectionModal(modelClass = Produto.class, modelViewClass = ProdutoFindModelView.class,
	width=300, height=50, required=true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"produto","cozinha"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="produto", priority=Priority.NEVER), 
		   		@TPriority(field="cozinha", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Produto> produto;
	
	
	@TReaderHtml
	@TLabel(text="#{label.local.prod}")
	@TComboBoxField(required=true, optionsList=@TOptionsList(entityClass=Cozinha.class, 
	optionModelViewClass=CozinhaModelView.class, serviceName = "ICozinhaControllerRemote"))
	private SimpleObjectProperty<Cozinha> cozinha;
	
	
	@TReaderHtml
	@TLabel(text="#{label.qtd.min}")
	@TNumberSpinnerField(maxValue = 1000000, minValue=0, zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	@THBox(	pane=@TPane(children={"qtdMinima","qtdInicial"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="qtdMinima", priority=Priority.NEVER), 
		   		@TPriority(field="qtdInicial", priority=Priority.NEVER)}))
	private SimpleIntegerProperty qtdMinima;
	
	
	@TReaderHtml
	@TLabel(text="#{label.qtd.inicial}")
	@TNumberSpinnerField(maxValue = 1000000, minValue=0, zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	private SimpleIntegerProperty qtdInicial;
	
	public EstoqueConfigModelView(EstoqueConfig entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
	}
	
	@Override
	public void reload(EstoqueConfig model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(EstoqueConfig model) {
		if(!model.isNew()){
			String str = (produto.getValue()==null ? "" : produto.getValue().toString() ) 
					+ (cozinha.getValue()!=null ? " ("+cozinha.getValue()+")" : "") ;
			displayText.setValue(str);
		}
	}
	
	private void buildListener() {
		
		ChangeListener<Produto> idListener = super.getListenerRepository().get("displayText");
		if(idListener==null){
			idListener = (arg0, arg1, arg2) -> {
					String str = (arg2==null ? "" : arg2.toString() ) 
							+ (cozinha.getValue()!=null ? " ("+cozinha.getValue()+")" : "") ;
					displayText.setValue(str);
				};
			
			super.addListener("displayText", idListener);
		}else
			produto.removeListener(idListener);
		
		produto.addListener(idListener);
		
		ChangeListener<Cozinha> tituloListener = super.getListenerRepository().get("displayText1");
		if(tituloListener==null){
			tituloListener = (arg0, arg1, arg2) -> {
					String str = (produto.getValue()==null ? "" : produto.getValue().toString() )  
							+ (arg2!=null ? " ("+arg2+")" : "") ;
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
	 * @return the produto
	 */
	public SimpleObjectProperty<Produto> getProduto() {
		return produto;
	}

	/**
	 * @param produto the produto to set
	 */
	public void setProduto(SimpleObjectProperty<Produto> produto) {
		this.produto = produto;
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
	 * @return the qtdMinima
	 */
	public SimpleIntegerProperty getQtdMinima() {
		return qtdMinima;
	}

	/**
	 * @param qtdMinima the qtdMinima to set
	 */
	public void setQtdMinima(SimpleIntegerProperty qtdMinima) {
		this.qtdMinima = qtdMinima;
	}

	/**
	 * @return the qtdInicial
	 */
	public SimpleIntegerProperty getQtdInicial() {
		return qtdInicial;
	}

	/**
	 * @param qtdInicial the qtdInicial to set
	 */
	public void setQtdInicial(SimpleIntegerProperty qtdInicial) {
		this.qtdInicial = qtdInicial;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return displayText;
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

}
