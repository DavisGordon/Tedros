/**
 * 
 */
package org.somos.module.produto.model;

import org.somos.model.EntradaItem;
import org.somos.model.Produto;

import com.tedros.fxapi.annotation.TObservableValue;
import com.tedros.fxapi.annotation.control.TBigDecimalField;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailTableViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.property.TReadOnlyBooleanProperty;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.domain.TZeroValidation;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailFieldDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleDoubleProperty;
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
@TForm(name = "Adicionar produtos")
@TDetailTableViewPresenter(
		presenter=@TPresenter(behavior=@TBehavior(type=TDetailFieldBehavior.class, action=EntradaItemAddAction.class),
				decorator = @TDecorator(type=TDetailFieldDecorator.class, viewTitle="Itens")
				),
		tableView=@TTableView(editable=true, control=@TControl(prefHeight=180, parse=true),
			columns = { @TTableColumn(cellValue="produto", text = "Produto", prefWidth=50, resizable=true), 
						@TTableColumn(cellValue="quantidade", text = "Qtd.", prefWidth=20, resizable=true),
						@TTableColumn(cellValue="valorUnitario", text = "Vlr. Unitario", resizable=true), 
						@TTableColumn(cellValue="unidadeMedida", text = "Unidade", resizable=true)}))
public class EntradaItemModelView extends TEntityModelView<EntradaItem> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TLabel(text="Pesquisar pelo codigo")
	@TTextField(maxLength=20, node=@TNode(requestFocus=true,
	focusedProperty=@TReadOnlyBooleanProperty(observableValue=@TObservableValue(
			addListener=CarregarProdutoListener.class), parse = true), parse = true),
	textInputControl=@TTextInputControl(promptText="Codigo do produto", parse = true), 
				control=@TControl(tooltip="Insira o codigo do produto a ser carregado", parse = true))
	@THBox(	pane=@TPane(children={"codigo","produto"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="codigo", priority=Priority.NEVER), 
						@TPriority(field="produto", priority=Priority.ALWAYS)}))
	private SimpleStringProperty codigo;
	
	@TReaderHtml
	@TLabel(text="Produto")
	@TOneSelectionModal(modelClass = Produto.class, modelViewClass = ProdutoFindModelView.class,
	width=300, height=50, required=true)
	@TTrigger(triggerClass = ProdutoSelecionadoTrigger.class, targetFieldName="unidadeMedida")
	private SimpleObjectProperty<Produto> produto;
	
	
	@TReaderHtml
	@TLabel(text="Quantidade")
	@TNumberSpinnerField(maxValue = 1000000, minValue=0, zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	@THBox(	pane=@TPane(children={"quantidade","unidadeMedida","valorUnitario"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="quantidade", priority=Priority.NEVER), 
		   		@TPriority(field="unidadeMedida", priority=Priority.NEVER), 
		   		@TPriority(field="valorUnitario", priority=Priority.NEVER)}))
	private SimpleIntegerProperty quantidade;
	
	@TReaderHtml
	@TLabel(text="Unidade de medida")
	@TComboBoxField(items=UnidadeMedidaBuilder.class, required=true)
	private SimpleStringProperty unidadeMedida;
	
	@TReaderHtml
	@TLabel(text = "Valor")
	@TBigDecimalField(textInputControl=@TTextInputControl(promptText="Valor", parse = true))
	private SimpleDoubleProperty valorUnitario;
	
	public EntradaItemModelView(EntradaItem entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
	}
	
	
	@Override
	public void reload(EntradaItem model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}
	
	private void loadDisplayText(EntradaItem model) {
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


	/**
	 * @return the valorUnitario
	 */
	public SimpleDoubleProperty getValorUnitario() {
		return valorUnitario;
	}


	/**
	 * @param valorUnitario the valorUnitario to set
	 */
	public void setValorUnitario(SimpleDoubleProperty valorUnitario) {
		this.valorUnitario = valorUnitario;
	}


	/**
	 * @return the unidadeMedida
	 */
	public SimpleStringProperty getUnidadeMedida() {
		return unidadeMedida;
	}


	/**
	 * @param unidadeMedida the unidadeMedida to set
	 */
	public void setUnidadeMedida(SimpleStringProperty unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}


	/**
	 * @return the codigo
	 */
	public SimpleStringProperty getCodigo() {
		return codigo;
	}


	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(SimpleStringProperty codigo) {
		this.codigo = codigo;
	}

}
