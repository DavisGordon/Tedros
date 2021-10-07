/**
 * 
 */
package com.editorweb.module.template.model;

import com.tedros.editorweb.model.Style;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailTableViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailFieldDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TDetailTableViewPresenter(
		presenter=@TPresenter(behavior=@TBehavior(type=TDetailFieldBehavior.class),
				decorator = @TDecorator(type=TDetailFieldDecorator.class, viewTitle="Style")
				),
		tableView=@TTableView(editable=true, control=@TControl(prefHeight=200, parse = true),
			columns = {@TTableColumn(cellValue="name", text = "#{label.name}", resizable=true),
					@TTableColumn(cellValue="href", text = "href", resizable=true),
					@TTableColumn(cellValue="desc", text = "#{label.description}", resizable=true)
			}))
public class StyleMV extends TEntityModelView<Style> {

	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=25, required = true, 
	node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name","href","desc"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS),
   				   		@TPriority(field="desc", priority=Priority.ALWAYS) }))
	private SimpleStringProperty name;

	@TReaderHtml
	@TLabel(text="href")
	@TTextField(maxLength=120)
	private SimpleStringProperty href;
	
	@TReaderHtml
	@TLabel(text="#{label.description}")
	@TTextField(maxLength=80)
	private SimpleStringProperty desc;
	
	@TReaderHtml
	@TAccordion(node=@TNode(id="codeAcc",parse = true),
	panes={@TTitledPane(text="#{label.code}", expanded=false, fields={"code"})})
	@TTextAreaField(control=@TControl(prefWidth=250, prefHeight=200, parse = true), wrapText=true)
	private SimpleStringProperty code;
	
	
	public StyleMV(Style entity) {
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
	 * @return the href
	 */
	public SimpleStringProperty getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public void setHref(SimpleStringProperty href) {
		this.href = href;
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

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}
}
