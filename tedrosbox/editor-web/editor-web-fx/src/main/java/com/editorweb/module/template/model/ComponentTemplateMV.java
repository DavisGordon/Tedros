/**
 * 
 */
package com.editorweb.module.template.model;

import com.tedros.common.model.TFileEntity;
import com.tedros.editorweb.domain.ComponentType;
import com.tedros.editorweb.model.ComponentTemplate;
import com.tedros.editorweb.model.CssClass;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TDetailListViewPresenter(presenter=@TPresenter(
behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewDecorator.class, buildModesRadioButton=false, viewTitle="#{label.components}")))
public class ComponentTemplateMV extends TEntityModelView<ComponentTemplate> {

	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, 
	node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name","type"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS),
   				   		@TPriority(field="type", priority=Priority.ALWAYS) }))
	private SimpleStringProperty name;
	
	@TReaderHtml
	@TLabel(text="#{label.type}")
	@TComboBoxField(firstItemTex="#{label.select}", required=true, 
	items=ComponentTypeOptionBuilder.class)
	private SimpleObjectProperty<ComponentType> type;
	
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=200, parse = true), wrapText=true)
	@TTabPane(tabs = { 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"code"})), text = "#{label.code}"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"imgExample"})), text = "#{label.imageExample}"), 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"cssClassList"})), text = "#{view.cssclass}")
	})
	private SimpleStringProperty code;
	
	@TLabel(text="Imagem")
	@TFieldBox(node=@TNode(id="image", parse=true))
	@TFileField(showImage=true, propertyValueType=TFileModelType.ENTITY,
	preLoadFileBytes=true, extensions= {TFileExtension.JPG, TFileExtension.PNG},
	showFilePath=true)
	private TSimpleFileProperty<TFileEntity> imgExample;
	
	@TPickListField(selectedLabel="#{label.selected}", 
			sourceLabel="#{view.cssclass}", 
			optionsList=@TOptionsList(entityClass=CssClass.class,
						optionModelViewClass=CssClassMV.class, serviceName = "ITCssClassControllerRemote"))
	@TModelViewType(modelClass=CssClass.class, modelViewClass=CssClassMV.class)
	private ITObservableList<CssClassMV> cssClassList;

	
	public ComponentTemplateMV(ComponentTemplate entity) {
		super(entity);
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
	 * @return the name
	 */
	public SimpleStringProperty getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}


	/**
	 * @return the code
	 */
	public SimpleStringProperty getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}


	/**
	 * @return the type
	 */
	public SimpleObjectProperty<ComponentType> getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(SimpleObjectProperty<ComponentType> type) {
		this.type = type;
	}


	/**
	 * @return the imgExample
	 */
	public TSimpleFileProperty<TFileEntity> getImgExample() {
		return imgExample;
	}


	/**
	 * @param imgExample the imgExample to set
	 */
	public void setImgExample(TSimpleFileProperty<TFileEntity> imgExample) {
		this.imgExample = imgExample;
	}


	/**
	 * @return the cssClassList
	 */
	public ITObservableList<CssClassMV> getCssClassList() {
		return cssClassList;
	}


	/**
	 * @param cssClassList the cssClassList to set
	 */
	public void setCssClassList(ITObservableList<CssClassMV> cssClassList) {
		this.cssClassList = cssClassList;
	}


	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

}
