/**
 * 
 */
package com.editorweb.module.site.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.editorweb.model.Metadata;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.layout.TFieldSet;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailTableViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailFieldDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TDetailTableViewPresenter(
		presenter=@TPresenter(behavior=@TBehavior(type=TDetailFieldBehavior.class),
				decorator = @TDecorator(type=TDetailFieldDecorator.class, viewTitle="Metas")
				),
		tableView=@TTableView(editable=true, control=@TControl(prefHeight=200, parse = true),
			columns = {@TTableColumn(cellValue="display", text = "Meta", resizable=true)
			}))
public class MetadataMV extends TEntityModelView<Metadata> {

	private SimpleLongProperty id;
	
	@THBox(	pane=@TPane(children={"name","charSet"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS),
		   				   		@TPriority(field="charSet", priority=Priority.ALWAYS) }))
	private SimpleStringProperty display;
	
	@TLabel(text="name")
	@TTextField(maxLength=160, 
	node=@TNode(requestFocus=true, parse = true))
	@TFieldSet(fields = { "name","httpEquiv","content" }, layoutType=TLayoutType.HBOX,
	region=@TRegion(maxWidth=400, parse = true),
	legend = "#{label.attribute} name #{label.or} httpEquiv")
	private SimpleStringProperty name;
	
	@TLabel(text="#{label.or} httpEquiv")
	@TTextField(maxLength=120)
	private SimpleStringProperty httpEquiv;
	
	@TLabel(text="content")
	@TTextField(maxLength=200)
	private SimpleStringProperty content;
	
	@TLabel(text="charSet")
	@TTextField(maxLength=20)
	@TFieldSet(fields = { "charSet" }, 
	region=@TRegion(maxWidth=400, parse = true),
	legend = "#{label.attribute} charSet")
	private SimpleStringProperty charSet;
	
	public MetadataMV(Metadata entity) {
		super(entity);
		buildDisplay();
	}
	
	@Override
	public void reload(Metadata model) {
		super.reload(model);
		buildDisplay();
	}
	
	private void buildDisplay() {
		Metadata e = super.getEntity();
		String y = buildDisplayText(e.getName(), e.getHttpEquiv(), e.getContent(), e.getCharSet());
		if(display==null) {
			display = new SimpleStringProperty();
		}
		display.setValue(y);
		
		if(super.getProperty("display")==null)
			super.registerProperty("display", display);
		
		ChangeListener<String> chl1 = super.getListenerRepository().get("dName");
		if(chl1==null) {
			chl1 = (a, b, x)->{
				String h = httpEquiv.getValue();
				String c = content.getValue();
				String t = charSet.getValue();
				String s = buildDisplayText(x, h, c, t);
				display.setValue(s);
			};
			super.addListener("dName", chl1);
		}
		name.addListener(new WeakChangeListener<>(chl1));
		
		ChangeListener<String> chl2 = super.getListenerRepository().get("dHttpEq");
		if(chl2==null) {
			chl2 = (a, b, x)->{
				String n = name.getValue();
				String c = content.getValue();
				String t = charSet.getValue();
				String s = buildDisplayText(n, x, c, t);
				display.setValue(s);
			};
			super.addListener("dHttpEq", chl2);
		}
		httpEquiv.addListener(new WeakChangeListener<>(chl2));
		
		ChangeListener<String> chl3 = super.getListenerRepository().get("dCont");
		if(chl3==null) {
			chl3 = (a, b, x)->{
				String n = name.getValue();
				String h = httpEquiv.getValue();
				String t = charSet.getValue();
				String s = buildDisplayText(n, h, x, t);
				display.setValue(s);
			};
			super.addListener("dCont", chl3);
		}
		content.addListener(new WeakChangeListener<>(chl3));
		
		ChangeListener<String> chl4 = super.getListenerRepository().get("dChar");
		if(chl4==null) {
			chl4 = (a, b, x)->{
				String n = name.getValue();
				String h = httpEquiv.getValue();
				String c = content.getValue();
				String s = buildDisplayText(n, h, c, x);
				display.setValue(s);
			};
			super.addListener("dChar", chl4);
		}
		charSet.addListener(new WeakChangeListener<>(chl4));
	}

	/**
	 * @param n
	 * @param c
	 * @return
	 */
	private String buildDisplayText(String n, String h, String c, String t) {
		String s = StringUtils.isNotBlank(n) ? "name="+n+" " : "";
		s = StringUtils.isNotBlank(h) ? s + "httpEquiv="+h+" " : s;
		if(StringUtils.isNotBlank(c))
			s += "content="+c+" ";
		s = StringUtils.isNotBlank(t) ? s + "charSet="+t : s;
		return s;
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
	 * @return the content
	 */
	public SimpleStringProperty getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(SimpleStringProperty content) {
		this.content = content;
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
	 * @return the httpEquiv
	 */
	public SimpleStringProperty getHttpEquiv() {
		return httpEquiv;
	}

	/**
	 * @param httpEquiv the httpEquiv to set
	 */
	public void setHttpEquiv(SimpleStringProperty httpEquiv) {
		this.httpEquiv = httpEquiv;
	}

	/**
	 * @return the charSet
	 */
	public SimpleStringProperty getCharSet() {
		return charSet;
	}

	/**
	 * @param charSet the charSet to set
	 */
	public void setCharSet(SimpleStringProperty charSet) {
		this.charSet = charSet;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return display;
	}

	/**
	 * @return the display
	 */
	public SimpleStringProperty getDisplay() {
		return display;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(SimpleStringProperty display) {
		this.display = display;
	}

}
