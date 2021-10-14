/**
 * 
 */
package com.editorweb.module.site.model;

import com.editorweb.module.template.model.ComponentTypeOptionBuilder;
import com.tedros.common.model.TFileEntity;
import com.tedros.editorweb.domain.ComponentType;
import com.tedros.editorweb.model.ComponentTemplate;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellFactory;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.presenter.TSelectionModalPresenter;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TSelectionModalPresenter(listViewMaxWidth=150, listViewMinWidth=150,
		paginator=@TPaginator(entityClass = ComponentTemplate.class, modelViewClass=ComponentTemplateFindMV.class, 
			serviceName = "ITComponentTemplateControllerRemote"),
		tableView=@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="name", text = "#{label.name}", minWidth=100, maxWidth=250, resizable=true), 
						@TTableColumn(cellValue="type", text = "#{label.type}", minWidth=100, maxWidth=250, resizable=true), 
						@TTableColumn(cellValue="imgExample", text = "#{label.imageExample}", resizable=true, maxWidth=305,
								cellFactory=@TCellFactory(parse = true, callBack=@TCallbackFactory(parse=true, value=CompTempImageCallback.class)))}), 
		allowsMultipleSelections = false)
public class ComponentTemplateFindMV extends TEntityModelView<ComponentTemplate> {

	private SimpleLongProperty id;
	
	@TLabel(text="#{label.name}")
	@TTextField(node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty name;
	
	@TReaderHtml
	@TLabel(text="#{label.type}")
	@TComboBoxField(firstItemTex="#{label.select}", required=true, 
	items=ComponentTypeOptionBuilder.class)
	private SimpleObjectProperty<ComponentType> type;
	
	private TSimpleFileEntityProperty<TFileEntity> imgExample;
	
	public ComponentTemplateFindMV(ComponentTemplate entity) {
		super(entity);
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
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
	public TSimpleFileEntityProperty<TFileEntity> getImgExample() {
		return imgExample;
	}

	/**
	 * @param imgExample the imgExample to set
	 */
	public void setImgExample(TSimpleFileEntityProperty<TFileEntity> imgExample) {
		this.imgExample = imgExample;
	}
	
}
