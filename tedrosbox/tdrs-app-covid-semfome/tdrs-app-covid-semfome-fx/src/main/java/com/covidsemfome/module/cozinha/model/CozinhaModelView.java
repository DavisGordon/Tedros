/**
 * 
 */
package com.covidsemfome.module.cozinha.model;

import com.covidsemfome.model.Cozinha;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TMaskField;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Cozinha")
@TEjbService(serviceName = "ICozinhaControllerRemote", model=Cozinha.class)
@TPresenter(decorator = @TDecorator(viewTitle="Cozinha"))
@TSecurity(	id="COVSEMFOME_CADCOZ_FORM", 
	appName = "#{app.name}", moduleName = "Administrativo", viewName = "Cozinha",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CozinhaModelView extends TEntityModelView<Cozinha> {

	private SimpleLongProperty id;

	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, textInputControl=@TTextInputControl(promptText="#{label.name}", parse = true), 
				control=@TControl(tooltip="#{label.name}", parse = true))
	@THBox(	pane=@TPane(children={"nome","telefone"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="nome", priority=Priority.ALWAYS), 
						@TPriority(field="telefone", priority=Priority.ALWAYS)}))
	private SimpleStringProperty nome;
	
	@TReaderHtml
	@TLabel(text="Endereço")
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=50, parse = true), 
	wrapText=true, maxLength=200, prefRowCount=4)
	private SimpleStringProperty endereco;
	
	@TReaderHtml
	@TLabel(text="Telefone")
	@TMaskField(mask="(99) 999999999", 
	textInputControl=@TTextInputControl(promptText="Telefone, celuar...", parse = true),
	required=true)
	private SimpleStringProperty telefone;
	
	public CozinhaModelView(Cozinha model) {
		super(model);
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
		return nome;
	}

	/**
	 * @return the nome
	 */
	public SimpleStringProperty getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(SimpleStringProperty nome) {
		this.nome = nome;
	}

	/**
	 * @return the endereco
	 */
	public SimpleStringProperty getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(SimpleStringProperty endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the telefone
	 */
	public SimpleStringProperty getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(SimpleStringProperty telefone) {
		this.telefone = telefone;
	}

}
