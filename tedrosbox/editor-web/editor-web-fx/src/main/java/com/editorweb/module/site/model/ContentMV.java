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
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.image.TImageView;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.annotation.scene.web.TWebEngine;
import com.tedros.fxapi.annotation.scene.web.TWebView;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TLabelPosition;
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

/**
 * @author Davis Gordon
 *
 */
@TDetailListViewPresenter(presenter=@TPresenter(
behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewDecorator.class, buildModesRadioButton=false, viewTitle="#{label.contents}")))
public class ContentMV extends TEntityModelView<Content> {

	
	
	@THBox(	pane=@TPane(children={"selected", "desc"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="desc", priority=Priority.ALWAYS),
					@TPriority(field="selected", priority=Priority.NEVER)
			}))
	private SimpleLongProperty id;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-info", parse = true))
	@TLabel(text="Component Selecionado:", position=TLabelPosition.TOP)
	@TShowField
	@TVBox(	pane=@TPane(children={"selected", "template"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="selected", priority=Priority.NEVER),
			@TPriority(field="template", priority=Priority.ALWAYS)
	}))
	private SimpleStringProperty selected;
	
	@TAccordion(expandedPane="tmplt", node=@TNode(id="contaacc",parse = true), region=@TRegion(maxWidth=250, parse = true),
			panes={
					@TTitledPane(text="Template", node=@TNode(id="tmplt",parse = true), 
							expanded=true, fields={"template", "templateImg"}),
					@TTitledPane(text="#{label.main.data}", fields={"title", "preOrdering", "styleAttr"}),
					@TTitledPane(text="#{view.cssclass}", fields={"cssClassList"})
				})
	@TLabel(text="Template")
	@TTrigger(triggerClass = CompTempTrigger.class, targetFieldName="cssClassList", runAfterFormBuild=true)
	@TOneSelectionModal(modelClass = ComponentTemplate.class, modelViewClass = ComponentTemplateFindMV.class,
			width=200, height=50, modalHeight=510)
	private SimpleObjectProperty<ComponentTemplate> template;
	
	@TImageView(fitWidth=200, preserveRatio=true)
	private TSimpleFileEntityProperty<TFileEntity> templateImg;
	
	@TLabel(text="#{label.title}")
	@TTextField(maxLength=120, required = true, 
	node=@TNode(requestFocus=true, parse = true))
	//@TTrigger(triggerClass = CompTempStringTrigger.class)
	private SimpleStringProperty title;
	
	@TLabel(text="#{label.ordering}")
	@TNumberSpinnerField(maxValue = 250)
	private SimpleIntegerProperty preOrdering;
	
	@TLabel(text="#{label.styleattr}")
	@TTextAreaField(wrapText=true)
	private SimpleStringProperty styleAttr;
	
	@TPickListField(selectedLabel="#{label.selected}", 
			sourceLabel="#{view.cssclass}", width=110)
	@TModelViewCollectionType(modelClass=CssClass.class, modelViewClass=CssClassMV.class)
	private ITObservableList<CssClassMV> cssClassList;
	
	@TTabPane(tabs = { @TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"webview"})), text = "View"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"desc"})), text = "#{label.content}"),
			@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"code"})), text = "#{label.code}")
			})
	@THTMLEditor(control=@TControl(prefHeight=300, parse = true))
	private SimpleStringProperty desc;
	
	@TWebView(prefHeight=400, 
			engine=@TWebEngine(//load="http://localhost:8081/editor-web-webapp/story/edit.html",
			load="file:C:/Users/Davis Gordon/git/Tedros/tedrosbox/editor-web/editor-web-webapp/src/main/webapp/story/edit.html",
					componentConfig=WebEngineConfig.class))
	private SimpleStringProperty webview;

	@TLabel(text="#{label.code}")
	@TTextAreaField(wrapText=true)
	private SimpleStringProperty code;
	
	public ContentMV(Content entity) {
		super(entity);
		super.registerProperty("templateImg", templateImg);
		super.registerProperty("webview", webview);
		super.registerProperty("selected", selected);
	}
	
	@Override
	public void reload(Content model) {
		super.reload(model);
		super.registerProperty("templateImg", templateImg);
		super.registerProperty("webview", webview);
		super.registerProperty("selected", selected);
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

	/**
	 * @return the webview
	 */
	public SimpleStringProperty getWebview() {
		return webview;
	}

	/**
	 * @param webview the webview to set
	 */
	public void setWebview(SimpleStringProperty webview) {
		this.webview = webview;
	}

	/**
	 * @return the selected
	 */
	public SimpleStringProperty getSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(SimpleStringProperty selected) {
		this.selected = selected;
	}

}
