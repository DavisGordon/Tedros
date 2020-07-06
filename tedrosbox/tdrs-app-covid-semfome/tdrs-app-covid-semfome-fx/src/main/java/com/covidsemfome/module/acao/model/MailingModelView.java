/**
 * 
 */
package com.covidsemfome.module.acao.model;

import java.util.Date;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Voluntario;
import com.covidsemfome.module.acao.behavior.AcaoVoluntarioBehavior;
import com.covidsemfome.module.acao.decorator.AcaoVoluntarioDecorator;
import com.covidsemfome.module.acao.process.AcaoProcess;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.reader.TDetailReader;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Voluntários inscritos", showBreadcrumBar=false)
@TEntityProcess(process = AcaoProcess.class, entity=Acao.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = AcaoVoluntarioBehavior.class), 
			decorator = @TDecorator(type = AcaoVoluntarioDecorator.class, 
									viewTitle="Voluntarios inscritos", listTitle="Acão"))
@TSecurity(	id="COVSEMFOME_ACAOVOL_FORM", 
			appName = "#{app.name}", moduleName = "Voluntários Inscritos", viewName = "Voluntarios inscritos",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})

public class AcaoVoluntarioModelView extends TEntityModelView<Acao> {
	
	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Acão", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Acão", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoCadastro;
	
	@TReaderHtml
	@TLabel(text="Titulo")
	@TTextField(maxLength=100, 
	textInputControl=@TTextInputControl(promptText="Titulo", parse = true),
	required=true)
	@THBox(	pane=@TPane(children={"titulo","data"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="titulo", priority=Priority.ALWAYS), 
   				   		@TPriority(field="data", priority=Priority.NEVER) }))
	private SimpleStringProperty titulo;
	
	@TReaderHtml
	@TLabel(text="Data e Hora")
	@TDatePickerField(required = false, dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> data;
	
	@TTextReaderHtml(text="Voluntários", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Voluntários", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty textoCadastro2;
	
	@TDetailReader(label=@TLabel(text="Voluntários"))
	@TTableView(editable=true,
			columns = { @TTableColumn(cellValue="nome", text = "Nome", prefWidth=100)
			})
	@TModelViewCollectionType(entityClass=Voluntario.class, modelViewClass=VoluntarioTableView.class)
	private ITObservableList<VoluntarioTableView> voluntarios;


	public AcaoVoluntarioModelView(Acao entity) {
		super(entity);
	}
	
	@Override
	public void removeAllListener() {
		super.removeAllListener();
		//	tl.stop();
	}
	
	public AcaoVoluntarioModelView() {
		super(new Acao());
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
		return titulo;
	}

	
	
	public AcaoVoluntarioModelView getNewInstance(){
		return new AcaoVoluntarioModelView(new Acao());
	}
	
	@Override
	public String toString() {
		return (getTitulo()!=null)? getTitulo().getValue() : "";	
	}
		
	@Override
	public int hashCode() {
		return reflectionHashCode(this, null);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof AcaoVoluntarioModelView))
			return false;
		
		AcaoVoluntarioModelView p = (AcaoVoluntarioModelView) obj;
		
		if(getId()!=null && getId().getValue()!=null &&  p.getId()!=null && p.getId().getValue()!=null){
			if(!(getId().getValue().equals(Long.valueOf(0)) && p.getId().getValue().equals(Long.valueOf(0))))
				return getId().getValue().equals(p.getId().getValue());
		}	
		
		if(getTitulo()!=null && getTitulo().getValue()!=null &&  p.getTitulo()!=null && p.getTitulo().getValue()!=null)
			return getTitulo().getValue().equals(p.getTitulo().getValue());
		
		return false;
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
	 * @return the textoCadastro
	 */
	public SimpleStringProperty getTextoCadastro() {
		return textoCadastro;
	}

	/**
	 * @param textoCadastro the textoCadastro to set
	 */
	public void setTextoCadastro(SimpleStringProperty textoCadastro) {
		this.textoCadastro = textoCadastro;
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
	 * @return the voluntarios
	 */
	public ITObservableList<VoluntarioTableView> getVoluntarios() {
		return voluntarios;
	}

	/**
	 * @param voluntarios the voluntarios to set
	 */
	public void setVoluntarios(ITObservableList<VoluntarioTableView> voluntarios) {
		this.voluntarios = voluntarios;
	}

	/**
	 * @return the textoCadastro2
	 */
	public SimpleStringProperty getTextoCadastro2() {
		return textoCadastro2;
	}

	/**
	 * @param textoCadastro2 the textoCadastro2 to set
	 */
	public void setTextoCadastro2(SimpleStringProperty textoCadastro2) {
		this.textoCadastro2 = textoCadastro2;
	}


}
