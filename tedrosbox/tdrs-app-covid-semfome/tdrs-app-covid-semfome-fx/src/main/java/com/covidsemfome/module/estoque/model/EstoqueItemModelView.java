/**
 * 
 */
package com.covidsemfome.module.estoque.model;

import com.covidsemfome.model.EstoqueItem;
import com.covidsemfome.model.Produto;
import com.tedros.fxapi.annotation.control.TCellFactory;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailTableViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailFieldDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Priority;
import javafx.util.converter.IntegerStringConverter;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Itens")
@TDetailTableViewPresenter(
		presenter=@TPresenter(behavior=@TBehavior(type=TDetailFieldBehavior.class),
				decorator = @TDecorator(type=TDetailFieldDecorator.class, viewTitle="Itens")
				),
		tableView=@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="produto", text = "Produto", prefWidth=50, resizable=true), 
						@TTableColumn(cellValue="qtdMinima", text = "Qtd.Min", prefWidth=20, resizable=true),
						@TTableColumn(cellValue="qtdInicial", text = "Qtd. Inicial", resizable=true), 
						@TTableColumn(cellValue="qtdCalculado", text = "Qtd. Calculado", resizable=true), 
						@TTableColumn(cellValue="qtdAjuste", text = "Ajuste", resizable=true,
								cellFactory=@TCellFactory(parse = true, tableCell=TextFieldTableCell.class, stringConverter=IntegerStringConverter.class),
								onEditCommit=EstoqueItemVlrAjusteEHBuilder.class), 
						@TTableColumn(cellValue="vlrAjustado", text = "Total", resizable=true)}))
public class EstoqueItemModelView extends TEntityModelView<EstoqueItem> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TReaderHtml
	@TLabel(text="Produto")
	@TShowField
	private SimpleObjectProperty<Produto> produto;
	
	
	@TReaderHtml
	@TLabel(text="Qtd. Min")
	@TShowField
	@THBox(	pane=@TPane(children={"qtdMinima","qtdInicial","qtdCalculado", "qtdAjuste", "vlrAjustado"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="qtdMinima", priority=Priority.ALWAYS), 
		   		@TPriority(field="qtdInicial", priority=Priority.ALWAYS), 
		   		@TPriority(field="qtdCalculado", priority=Priority.ALWAYS), 
		   		@TPriority(field="qtdAjuste", priority=Priority.ALWAYS), 
		   		@TPriority(field="vlrAjustado", priority=Priority.ALWAYS)}))
	private SimpleIntegerProperty qtdMinima;
	
	@TReaderHtml
	@TLabel(text="Qtd. Inicial")
	@TShowField
	private SimpleIntegerProperty qtdInicial;
	
	@TReaderHtml
	@TLabel(text="Qtd. Calculado")
	@TShowField
	private SimpleIntegerProperty qtdCalculado;
	
	@TReaderHtml
	@TLabel(text="Qtd. Ajuste")
	@TNumberSpinnerField(maxValue = 1000000)
	private SimpleIntegerProperty qtdAjuste;
	
	@TReaderHtml
	@TLabel(text="Total")
	@TShowField
	private SimpleIntegerProperty vlrAjustado = new SimpleIntegerProperty();
	
	
	
	public EstoqueItemModelView(EstoqueItem entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
		super.registerProperty("vlrAjustado", vlrAjustado);
	}
	
	
	@Override
	public void reload(EstoqueItem model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}
	
	private void loadDisplayText(EstoqueItem model) {
		if(!model.isNew()){
			String str = (produto.getValue()==null ? "" : "(COD: "+produto.getValue().getCodigo()+") " +
					produto.getValue().getNome());
			displayText.setValue(str);
		}
		
		
		vlrAjustado.setValue(model.getVlrAjustado());
		
	}
		
	
	private void buildListener() {
		
		ChangeListener<Number> idListener = super.getListenerRepository().getListener("vlrajuste2");
		if(idListener==null){
			idListener = (arg0, arg1, vlr) -> {
				vlrAjustado.setValue(super.getModel().getVlrAjustado());
				};
			super.addListener("vlrajuste2", idListener);
		}else
			qtdAjuste.removeListener(idListener);
		
		qtdAjuste.addListener(idListener);
		
		
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return displayText;
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


	/**
	 * @return the qtdCalculado
	 */
	public SimpleIntegerProperty getQtdCalculado() {
		return qtdCalculado;
	}


	/**
	 * @param qtdCalculado the qtdCalculado to set
	 */
	public void setQtdCalculado(SimpleIntegerProperty qtdCalculado) {
		this.qtdCalculado = qtdCalculado;
	}


	/**
	 * @return the qtdAjuste
	 */
	public SimpleIntegerProperty getQtdAjuste() {
		return qtdAjuste;
	}


	/**
	 * @param qtdAjuste the qtdAjuste to set
	 */
	public void setQtdAjuste(SimpleIntegerProperty qtdAjuste) {
		this.qtdAjuste = qtdAjuste;
	}


	/**
	 * @return the vlrAjustado
	 */
	public SimpleIntegerProperty getVlrAjustado() {
		return vlrAjustado;
	}


	/**
	 * @param vlrAjustado the vlrAjustado to set
	 */
	public void setVlrAjustado(SimpleIntegerProperty vlrAjustado) {
		this.vlrAjustado = vlrAjustado;
	}



}
