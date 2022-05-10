/**
 * 
 */
package org.somos.module.acao.campanha.model;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.model.Campanha;
import org.somos.model.FormaAjuda;
import org.somos.model.Periodo;
import org.somos.model.ValorAjuda;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.fxapi.annotation.TCodeValue;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
import com.tedros.fxapi.annotation.control.TSelectImageField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
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
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
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
@TForm(name = "Campanha de ajuda", scroll=false)
@TEjbService( model=Campanha.class, serviceName = "ICampanhaControllerRemote")
@TPresenter(decorator = @TDecorator(viewTitle="Campanha de ajuda"))
@TSecurity(	id="SOMOS_CAMPANHAAJUDA_FORM", 
			appName = "#{somos.name}", moduleName = "Gerenciar Campanha", viewName = "Campanha de Ajuda",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
							TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CampanhaModelView extends TEntityModelView<Campanha> {

	private SimpleLongProperty id;
	
	@TTextReaderHtml(text="Campanha de Ajuda", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Campanha de Ajuda", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty textoCadastro;
		
	@TReaderHtml
	@TLabel(text="Titulo")
	@TTextField(maxLength=120, required = true)
	@TTabPane(tabs = { 
			@TTab(closable=false, content =
				@TContent(detailForm=@TDetailForm(
						fields={"titulo","desc","meta","dataFim"})), 
				text = "Dados da campanha"),
			@TTab(closable=false, scroll=true, content =
				@TContent(detailForm=@TDetailForm(
					fields={"valores","formasAjuda"})), 
				text = "Valores, Periodo e Formas de Ajuda"),
			@TTab(closable=false, content = 
				@TContent(detailForm=@TDetailForm(fields={"image"})), 
				text = "Imagem")})
	private SimpleStringProperty titulo;
	
	@TReaderHtml
	@TLabel(text="Descrição")
	@TTextAreaField(maxLength=2000)
	private SimpleStringProperty desc;
	
	@TReaderHtml
	@TLabel(text="Meta")
	@TTextField(maxLength=30)
	@THBox(	pane=@TPane(children={"meta","angariado"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="meta", priority=Priority.ALWAYS),
   				   		@TPriority(field="angariado", priority=Priority.ALWAYS) }))
	private SimpleStringProperty meta;
	
	@TReaderHtml
	@TLabel(text="Angariado")
	@TTextField(maxLength=30)
	private SimpleStringProperty angariado;

	@TReaderHtml
	@TLabel(text="Ultimo dia")
	@TDatePickerField
	@THBox(	pane=@TPane(children={"status","dataFim"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="status", priority=Priority.ALWAYS), 
   				   		@TPriority(field="dataFim", priority=Priority.NEVER) }))
	private SimpleObjectProperty<Date> dataFim;
	
	@TLabel(text="Status")
	@TReaderHtml(codeValues={@TCodeValue(code = "ATIVADO", value = "Ativado"),
			@TCodeValue(code = "DESATIVADO", value = "Desativado")})
	@THorizontalRadioGroup(required=true, alignment=Pos.CENTER_LEFT, spacing=4, 
	radioButtons={
			@TRadioButtonField(text = "Ativado", userData = "ATIVADO"),
			@TRadioButtonField(text = "Desativado", userData = "DESATIVADO")
			})
	private SimpleStringProperty status;

	@TReaderHtml
	@TPickListField(selectedLabel="Selecionados", sourceLabel="Valores", 
	optionsList=@TOptionsList(serviceName="IValorAjudaControllerRemote", 
	entityClass = ValorAjuda.class, optionModelViewClass=ValorAjudaModelView.class))
	@TModelViewType(modelClass = ValorAjuda.class, modelViewClass=ValorAjudaModelView.class)
	@THBox(	pane=@TPane(children={"valores","periodos"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="valores", priority=Priority.ALWAYS),
   				   		@TPriority(field="periodos", priority=Priority.ALWAYS) }))
	private ITObservableList<ValorAjudaModelView> valores;
	
	@TReaderHtml
	@TPickListField(selectedLabel="Selecionados", sourceLabel="Periodos", 
	optionsList=@TOptionsList(serviceName="IPeriodoControllerRemote", 
	entityClass = Periodo.class, optionModelViewClass=PeriodoModelView.class))
	@TModelViewType(modelClass = Periodo.class, modelViewClass=PeriodoModelView.class)
	private ITObservableList<PeriodoModelView> periodos;
	
	@TReaderHtml
	@TPickListField(selectedLabel="Selecionados", 
	sourceLabel="Forma de ajuda", required=true, 
	optionsList=@TOptionsList(serviceName="IFormaAjudaControllerRemote", 
	entityClass = FormaAjuda.class, optionModelViewClass=FormaAjudaModelView.class))
	@TModelViewType(modelClass = FormaAjuda.class, modelViewClass=FormaAjudaModelView.class)
	private ITObservableList<FormaAjudaModelView> formasAjuda;

	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, 
	remoteOwner="somos", maxFileSize=300000)
	private SimpleObjectProperty<ITFileBaseModel> image;
	
	
	
	
	public CampanhaModelView(Campanha entity) {
		super(entity);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof CampanhaModelView))
			return false;
		return EqualsBuilder.reflectionEquals(this.getModel(), obj != null ? ((CampanhaModelView)obj).getModel() : obj, false);
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
	 * @return the desc
	 */
	public SimpleStringProperty getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(SimpleStringProperty desc) {
		this.desc = desc;
	}

	/**
	 * @return the meta
	 */
	public SimpleStringProperty getMeta() {
		return meta;
	}

	/**
	 * @param meta the meta to set
	 */
	public void setMeta(SimpleStringProperty meta) {
		this.meta = meta;
	}

	/**
	 * @return the angariado
	 */
	public SimpleStringProperty getAngariado() {
		return angariado;
	}

	/**
	 * @param angariado the angariado to set
	 */
	public void setAngariado(SimpleStringProperty angariado) {
		this.angariado = angariado;
	}

	/**
	 * @return the dataFim
	 */
	public SimpleObjectProperty<Date> getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(SimpleObjectProperty<Date> dataFim) {
		this.dataFim = dataFim;
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
	 * @return the periodos
	 */
	public ITObservableList<PeriodoModelView> getPeriodos() {
		return periodos;
	}

	/**
	 * @param periodos the periodos to set
	 */
	public void setPeriodos(ITObservableList<PeriodoModelView> periodos) {
		this.periodos = periodos;
	}

	/**
	 * @return the formasAjuda
	 */
	public ITObservableList<FormaAjudaModelView> getFormasAjuda() {
		return formasAjuda;
	}

	/**
	 * @param formasAjuda the formasAjuda to set
	 */
	public void setFormasAjuda(ITObservableList<FormaAjudaModelView> formasAjuda) {
		this.formasAjuda = formasAjuda;
	}

	/**
	 * @return the image
	 */
	public SimpleObjectProperty<ITFileBaseModel> getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(SimpleObjectProperty<ITFileBaseModel> image) {
		this.image = image;
	}

	/**
	 * @return the valores
	 */
	public ITObservableList<ValorAjudaModelView> getValores() {
		return valores;
	}

	/**
	 * @param valores the valores to set
	 */
	public void setValores(ITObservableList<ValorAjudaModelView> valores) {
		this.valores = valores;
	}

}
