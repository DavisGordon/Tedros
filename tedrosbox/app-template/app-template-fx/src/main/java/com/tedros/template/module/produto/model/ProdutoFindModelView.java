package com.tedros.template.module.produto.model;

import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLongField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TSelectionModalPresenter;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.template.model.Produto;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Priority;

/**
 * 
 * @author Davis Gordon
 * */
@TForm(name = "Produto")
@TSelectionModalPresenter(
		paginator=@TPaginator(entityClass = Produto.class, modelViewClass=ProdutoFindModelView.class, 
			serviceName = "IProdutoControllerRemote"),
		tableView=@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="codigo", text = "Codigo", prefWidth=20, resizable=true), 
						@TTableColumn(cellValue="nome", text = "Nome", resizable=true)}), 
		allowsMultipleSelections = false)
public class ProdutoFindModelView extends TEntityModelView<Produto>{
	
	@TLabel(text="Codigo:", position=TLabelPosition.LEFT)
	@TLongField(maxLength=6 )
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	

	@TLabel(text="#{label.code}")
	@TTextField(maxLength=20, required = true, node=@TNode(requestFocus=true, parse = true),
	textInputControl=@TTextInputControl(promptText="#{label.product.code}", parse = true), 
				control=@TControl(tooltip="#{label.ref.code}", parse = true))
	@THBox(	pane=@TPane(children={"codigo","nome"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="codigo", priority=Priority.NEVER), 
						@TPriority(field="nome", priority=Priority.ALWAYS)}))
	private SimpleStringProperty codigo;
	
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, textInputControl=@TTextInputControl(promptText="#{label.name}", parse = true), 
	node=@TNode(requestFocus=true, parse = true),
	control=@TControl(tooltip="#{label.name}", parse = true))
	private SimpleStringProperty nome;
	
	
	
	public ProdutoFindModelView(Produto entidade) {
		super(entidade);
		buildListener();
		loadDisplayText(entidade);
	}
	
	
	@Override
	public void reload(Produto model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}
	
	private void loadDisplayText(Produto model) {
		if(!model.isNew()){
			String str = (codigo.getValue()==null ? "" : "(COD: "+codigo.getValue()+") " ) 
					+ (nome.getValue()!=null ? nome.getValue() : "");
			displayText.setValue(str);
		}
	}
	
	private void buildListener() {
		
		ChangeListener<String> idListener =  super.getListenerRepository().get("displayText");
		if(idListener==null){
			idListener = (arg0, arg1, arg2) -> {
					String str = (arg2==null ? "" : "(COD: "+arg2.toString()+") " ) 
							+ (nome.getValue()!=null ? nome.getValue() : "");
					displayText.setValue(str);
				};
			super.addListener("displayText", idListener);
		}else
			codigo.removeListener(idListener);
		
		codigo.addListener(idListener);
		
		ChangeListener<String> tituloListener = super.getListenerRepository().get("displayText1");
		if(tituloListener==null){
			tituloListener = (arg0, arg1, arg2)-> {
					String str = (codigo.getValue()==null ? "" : "(COD: "+codigo.getValue().toString()+") " ) 
							+ (arg2!=null ? arg2 : "");
					displayText.setValue(str);
				};
			super.addListener("displayText1", tituloListener);
		}else
			nome.removeListener(tituloListener);
		
		nome.addListener(tituloListener);
	}
	
	@Override
	public void removeAllListener() {
		super.removeAllListener();
	}
	
	@Override
	public String toString() {
		return (getNome()!=null)? getNome().getValue() : "";	
	}
		
	

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	
	public SimpleStringProperty getNome() {
		return nome;
	}

	public void setNome(SimpleStringProperty nome) {
		this.nome = nome;
	}


	@Override
	public SimpleStringProperty getDisplayProperty() {
		return displayText;
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
