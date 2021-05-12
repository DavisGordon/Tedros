/**
 * 
 */
package com.covidsemfome.module.termoAdesao.model;

import com.covidsemfome.model.TermoAdesao;
import com.covidsemfome.module.termoAdesao.process.TermoAdesaoProcess;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "Termo de adesão", showBreadcrumBar=false)
@TEntityProcess(process = TermoAdesaoProcess.class, entity=TermoAdesao.class)
@TPresenter(decorator = @TDecorator(viewTitle="Termo de adesão"), 
		    behavior=@TBehavior(saveAction=TermoAdesaoSaveAction.class))
@TSecurity(	id="COVSEMFOME_TERMADES_FORM", 
	appName = "#{app.name}", moduleName = "Administrativo", viewName = "Termo de adesão",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class TermoAdesaoModelView extends TEntityModelView<TermoAdesao> {

	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="Titulo")
	@TTextField(maxLength=100, 
	textInputControl=@TTextInputControl(promptText="Insira um titulo", parse = true),
	required=true)
	@THBox(	pane=@TPane(children={"titulo","status"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="titulo", priority=Priority.ALWAYS), 
   				   		@TPriority(field="status", priority=Priority.ALWAYS) }))
	
	private SimpleStringProperty titulo;
	
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "Ativado"), 
			@TCodeValue(code = "DESATIVADO", value = "Desativado")
	})
	@TLabel(text="Status")
	@THorizontalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4, required=true,
	radioButtons = {@TRadioButtonField(text="Ativado", userData="ATIVADO"), 
					@TRadioButtonField(text="Desativado", userData="DESATIVADO")
	})
	private SimpleStringProperty status;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form",
			style="-fx-background-color: #FFFFFF", parse = true))
	@TText(text="#NOME# #NACIONALIDADE# #ESTADOCIVIL# #CPF# #PROFISSAO# "
			+ "#IDENTIDADE# #RUA# #BAIRRO# #CEP# #CIDADE# #UF# #TIPOSAJUDA# #DATAACAO# #HORAACAO#", 
			wrappingWidth=650,
			font=@TFont(size=12), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-label", parse = true))
	private SimpleStringProperty textoChaves;
	
	@THTMLEditor()
	private SimpleStringProperty conteudo;
	
	public TermoAdesaoModelView(TermoAdesao entity) {
		super(entity);
	}

	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleLongProperty getId() {
		return this.id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return titulo;
	}

	/**
	 * @return the titulo
	 */
	public SimpleStringProperty getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(SimpleStringProperty titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the conteudo
	 */
	public SimpleStringProperty getConteudo() {
		return conteudo;
	}

	/**
	 * @param conteudo the conteudo to set
	 */
	public void setConteudo(SimpleStringProperty conteudo) {
		this.conteudo = conteudo;
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
	 * @return the textoChaves
	 */
	public SimpleStringProperty getTextoChaves() {
		return textoChaves;
	}

	/**
	 * @param textoChaves the textoChaves to set
	 */
	public void setTextoChaves(SimpleStringProperty textoChaves) {
		this.textoChaves = textoChaves;
	}

}
