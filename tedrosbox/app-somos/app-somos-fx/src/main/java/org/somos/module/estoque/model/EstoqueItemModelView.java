/**
 * 
 */
package org.somos.module.estoque.model;

import org.somos.model.EstoqueItem;
import org.somos.model.Produto;

import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
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
@TForm(name = "Itens")
public class EstoqueItemModelView extends TEntityModelView<EstoqueItem> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	@TReaderHtml
	@TLabel(text="Produto")
	private SimpleObjectProperty<Produto> produto;
	
	
	@TReaderHtml
	@TLabel(text="Qtd. Min")
	@THBox(	pane=@TPane(children={"qtdMinima","qtdInicial","qtdCalculado", "qtdAjuste", "vlrAjustado"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="qtdMinima", priority=Priority.ALWAYS), 
		   		@TPriority(field="qtdInicial", priority=Priority.ALWAYS), 
		   		@TPriority(field="qtdCalculado", priority=Priority.ALWAYS), 
		   		@TPriority(field="qtdAjuste", priority=Priority.ALWAYS), 
		   		@TPriority(field="vlrAjustado", priority=Priority.ALWAYS)}))
	private SimpleIntegerProperty qtdMinima;
	
	@TReaderHtml
	@TLabel(text="Qtd. Inicial")
	private SimpleIntegerProperty qtdInicial;
	
	@TReaderHtml
	@TLabel(text="Qtd. Calculado")
	private SimpleIntegerProperty qtdCalculado;
	
	@TReaderHtml
	@TLabel(text="Qtd. Ajuste")
	private SimpleIntegerProperty qtdAjuste;
	
	@TReaderHtml
	@TLabel(text="Total")
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
		
		ChangeListener<Number> idListener = super.getListenerRepository().get("vlrajuste2");
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
