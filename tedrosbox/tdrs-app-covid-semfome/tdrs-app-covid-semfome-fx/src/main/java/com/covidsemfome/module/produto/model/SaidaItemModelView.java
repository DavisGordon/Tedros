/**
 * 
 */
package com.covidsemfome.module.produto.model;

import com.covidsemfome.model.Produto;
import com.covidsemfome.model.SaidaItem;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
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
import com.tedros.fxapi.domain.TZeroValidation;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailFieldDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Adicionar insumo")
@TDetailTableViewPresenter(
		presenter=@TPresenter(behavior=@TBehavior(type=TDetailFieldBehavior.class, addAction=SaidaItemAddAction.class),
				decorator = @TDecorator(type=TDetailFieldDecorator.class, viewTitle="Itens")
				),
		tableView=@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="produto", text = "Produto", prefWidth=50, resizable=true), 
						@TTableColumn(cellValue="quantidade", text = "Qtd.", prefWidth=20, resizable=true)}))
public class SaidaItemModelView extends TEntityModelView<SaidaItem> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TReaderHtml
	@TLabel(text="Produto")
	@TOneSelectionModal(modelClass = Produto.class, modelViewClass = ProdutoFindModelView.class,
	width=300, height=50, required=true)
	@THBox(	pane=@TPane(children={"produto","quantidade"}), spacing=10, fillHeight=false,
	hgrow=@THGrow(priority={@TPriority(field="quantidade", priority=Priority.NEVER), 
		   		@TPriority(field="produto", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Produto> produto;
	
	
	@TReaderHtml
	@TLabel(text="Quantidade")
	@TNumberSpinnerField(maxValue = 1000000, minValue=0, zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	private SimpleIntegerProperty quantidade;
	
	
	
	public SaidaItemModelView(SaidaItem entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
	}
	
	
	@Override
	public void reload(SaidaItem model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}
	
	private void loadDisplayText(SaidaItem model) {
		if(!model.isNew()){
			String str = (produto.getValue()==null ? "" : "(COD: "+produto.getValue().getCodigo()+") " +
					produto.getValue().getNome());
			displayText.setValue(str);
		}
	}
	
	private void buildListener() {
		
		ChangeListener<Produto> idListener =  super.getListenerRepository().get("displayText");
		if(idListener==null){
			idListener = (arg0, arg1, produto) -> {
	
				String str = (produto==null ? "" : "(COD: "+produto.getCodigo()+") " +
						produto.getNome());
				displayText.setValue(str);
				};
			super.addListener("displayText", idListener);
		}else
			produto.removeListener(idListener);
		
		produto.addListener(idListener);
		
		
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



}
