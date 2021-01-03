package com.covidsemfome.module.doacao.model;

import com.covidsemfome.model.Pessoa;
import com.covidsemfome.module.pessoa.model.PessoaFieldValueUtil;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLongField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TSelectionModalPresenter;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;

/**
 * 
 * @author Davis Gordon
 * */
@TForm(name = "#{form.person.title}", showBreadcrumBar=true)
@TSelectionModalPresenter(
		paginator=@TPaginator(entityClass = Pessoa.class, modelViewClass=PessoaFindModelView.class, 
			serviceName = "IPessoaControllerRemote"),
		tableView=@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="id", text = "Codigo", prefWidth=20, resizable=true), 
						@TTableColumn(cellValue="nome", text = "Nome", resizable=true), 
						@TTableColumn(cellValue="status", text = "Status", resizable=true)}), 
		allowsMultipleSelections = false)
public class PessoaFindModelView extends TEntityModelView<Pessoa>{
	
	@TLabel(text="Codigo:", position=TLabelPosition.LEFT)
	@TLongField(maxLength=6 )
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText;
	
	
	@TLabel(text="Status", position=TLabelPosition.LEFT)
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	/**
	 * A text input description for the person name and a horizontal box with name, last name and nick name
	 * */
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, textInputControl=@TTextInputControl(promptText="#{label.name}", parse = true), 
				control=@TControl(tooltip="#{label.name}", parse = true))
	private SimpleStringProperty nome;
	
	
	private SimpleStringProperty tipoVoluntario;
	
	
	
	public PessoaFindModelView(Pessoa entidade) {
		super(entidade);
		buildListener();
		loadDisplayText(entidade);
	
	}
	
	
	@Override
	public void reload(Pessoa model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}
	
	/**
	 * @param model
	 */
	private void loadDisplayText(Pessoa model) {
		if(!model.isNew()){
			String str = (nome.getValue()!=null ? nome.getValue() : "") 
					+ (tipoVoluntario.getValue()!=null ? " ("+PessoaFieldValueUtil.getDescricaoTipo(tipoVoluntario.getValue())+")" : "");
			displayText.setValue(str);
		}
	}

	

	private void buildListener() {
		
		ChangeListener<String> nomeListener = super.getListenerRepository().get("displayText1");
		if(nomeListener==null){
			nomeListener = new ChangeListener<String>(){
				@Override
				public void changed(ObservableValue arg0, String arg1, String arg2) {
					String str = (arg2!=null ? arg2 : "") 
							+ (tipoVoluntario.getValue()!=null ? " ("+PessoaFieldValueUtil.getDescricaoTipo(tipoVoluntario.getValue())+")" : "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText1", nomeListener);
		}else
			nome.removeListener(nomeListener);
		
		nome.addListener(nomeListener);
		
		ChangeListener<String> tipoListener = super.getListenerRepository().get("displayText2");
		if(tipoListener==null){
			tipoListener = new ChangeListener<String>(){
				@Override
				public void changed(ObservableValue arg0, String arg1, String arg2) {
					String str = (nome.getValue()!=null ? nome.getValue() : "") 
							+ (arg2!=null ? " ("+PessoaFieldValueUtil.getDescricaoTipo(arg2)+")" : "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText2", tipoListener);
		}else
			tipoVoluntario.removeListener(tipoListener);
		
		tipoVoluntario.addListener(tipoListener);
		
	}
	
	@Override
	public void removeAllListener() {
		super.removeAllListener();
	}
	
	public PessoaFindModelView() {
		super(new Pessoa());
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
	 * @return the tipoVoluntario
	 */
	public SimpleStringProperty getTipoVoluntario() {
		return tipoVoluntario;
	}

	/**
	 * @param tipoVoluntario the tipoVoluntario to set
	 */
	public void setTipoVoluntario(SimpleStringProperty tipoVoluntario) {
		this.tipoVoluntario = tipoVoluntario;
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
