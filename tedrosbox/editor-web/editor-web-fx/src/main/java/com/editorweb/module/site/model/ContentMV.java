/**
 * 
 */
package com.editorweb.module.site.model;

import com.editorweb.module.template.model.CssClassMV;
import com.tedros.common.model.TFileEntity;
import com.tedros.editorweb.model.ComponentTemplate;
import com.tedros.editorweb.model.Content;
import com.tedros.editorweb.model.CssClass;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TPickListField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTrigger;
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
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.image.TImage;
import com.tedros.fxapi.annotation.scene.image.TImageView;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;

import javafx.beans.property.SimpleIntegerProperty;
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
@TDetailListViewPresenter(presenter=@TPresenter(
behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewDecorator.class, buildModesRadioButton=false, viewTitle="#{label.contents}")))
public class ContentMV extends TEntityModelView<Content> {

	@TTabPane(tabs = { 
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"text1", "template", "templateImg"})), text = "Template"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"title", "desc"})), text = "#{label.main.data}"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"styleAttr", "code"})), text = "#{label.code}"), 
			/*@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"imgExample"})), text = "#{label.imageExample}"), 
			*/@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"cssClassList"})), text = "#{view.cssclass}")
	})
	private SimpleLongProperty id;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-info", parse = true))
	@TText(text="#{text.template}", 
	wrappingWidth=650, textAlignment=TextAlignment.LEFT, 
	textStyle = TTextStyle.CUSTOM)
	private SimpleStringProperty text1;
	
	@TLabel(text="Template")
	@TTrigger(triggerClass = CompTempTrigger.class, runAfterFormBuild=true)
	@TOneSelectionModal(modelClass = ComponentTemplate.class, modelViewClass = ComponentTemplateFindMV.class,
			width=300, height=50)
	private SimpleObjectProperty<ComponentTemplate> template;
	
	@TImageView
	private TSimpleFileEntityProperty<TFileEntity> templateImg;
	
	@TLabel(text="#{label.title}")
	@TTextField(maxLength=120, required = true, 
	node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"title", "preOrdering"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS)}))
	private SimpleStringProperty title;
	
	@TLabel(text="#{label.ordering}")
	@TNumberSpinnerField(maxValue = 250)
	private SimpleIntegerProperty preOrdering;
	
	@THTMLEditor(control=@TControl(prefHeight=400, parse = true))
	private SimpleStringProperty desc;
	
	@TLabel(text="#{label.styleattr}")
	@TTextField(maxLength=160)
	@THBox(	pane=@TPane(children={"styleAttr", "classAttr"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="styleAttr", priority=Priority.ALWAYS),
   				   		@TPriority(field="classAttr", priority=Priority.ALWAYS) }))
	private SimpleStringProperty styleAttr;
	
	@TLabel(text="#{label.classattr}")
	@TTextField(maxLength=160)
	private SimpleStringProperty classAttr;
	
	@TLabel(text="#{label.title}")
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=200, parse = true), wrapText=true)
	private SimpleStringProperty code;
	
	@TPickListField(selectedLabel="#{label.selected}", 
			sourceLabel="#{view.cssclass}", 
			optionsList=@TOptionsList(entityClass=CssClass.class,
						optionModelViewClass=CssClassMV.class, serviceName = "ITCssClassControllerRemote"))
	@TModelViewCollectionType(modelClass=CssClass.class, modelViewClass=CssClassMV.class)
	private ITObservableList<CssClassMV> cssClassList;

	public ContentMV(Content entity) {
		super(entity);
		super.registerProperty("templateImg", templateImg);
	}
	
	@Override
	public void reload(Content model) {
		super.reload(model);
		super.registerProperty("templateImg", templateImg);
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
	 * @return the title
	 */
	public SimpleStringProperty getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(SimpleStringProperty title) {
		this.title = title;
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
		return title;
	}

	/**
	 * @return the template
	 */
	public SimpleObjectProperty<ComponentTemplate> getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(SimpleObjectProperty<ComponentTemplate> template) {
		this.template = template;
	}

	/**
	 * @return the preOrdering
	 */
	public SimpleIntegerProperty getPreOrdering() {
		return preOrdering;
	}

	/**
	 * @param preOrdering the preOrdering to set
	 */
	public void setPreOrdering(SimpleIntegerProperty preOrdering) {
		this.preOrdering = preOrdering;
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
	 * @return the styleAttr
	 */
	public SimpleStringProperty getStyleAttr() {
		return styleAttr;
	}

	/**
	 * @param styleAttr the styleAttr to set
	 */
	public void setStyleAttr(SimpleStringProperty styleAttr) {
		this.styleAttr = styleAttr;
	}

	/**
	 * @return the classAttr
	 */
	public SimpleStringProperty getClassAttr() {
		return classAttr;
	}

	/**
	 * @param classAttr the classAttr to set
	 */
	public void setClassAttr(SimpleStringProperty classAttr) {
		this.classAttr = classAttr;
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
	 * @return the text1
	 */
	public SimpleStringProperty getText1() {
		return text1;
	}

	/**
	 * @param text1 the text1 to set
	 */
	public void setText1(SimpleStringProperty text1) {
		this.text1 = text1;
	}

	/**
	 * @return the templateImg
	 */
	public TSimpleFileEntityProperty<TFileEntity> getTemplateImg() {
		return templateImg;
	}

	/**
	 * @param templateImg the templateImg to set
	 */
	public void setTemplateImg(TSimpleFileEntityProperty<TFileEntity> templateImg) {
		this.templateImg = templateImg;
	}

}
