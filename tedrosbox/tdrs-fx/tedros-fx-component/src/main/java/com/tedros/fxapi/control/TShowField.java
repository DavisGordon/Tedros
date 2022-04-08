/**
 * 
 */
package com.tedros.fxapi.control;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.tedros.app.component.ITComponent;
import com.tedros.core.TLanguage;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.form.TFieldBoxBuilder;
import com.tedros.fxapi.util.TMaskUtil;
import com.tedros.fxapi.util.TReflectionUtil;
import com.tedros.util.TDateUtil;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Read and show a field value.
 * 
 * @author Davis Gordon
 *
 */
public class TShowField extends StackPane implements ITField, ITComponent{

	private String t_componentId;
	private TLayoutType layout = TLayoutType.HBOX;
	@SuppressWarnings("rawtypes")
	private Property value;
	private TShowFieldValue[] fields;
	private TComponentDescriptor descriptor;
	
	private Pane pane;
	/**
	 * @param layout
	 * @param value
	 * @param fields
	 */
	@SuppressWarnings("rawtypes")
	public TShowField(TLayoutType layout, Property value, TShowFieldValue... fields) {
		this.layout = layout;
		this.value = value;
		this.fields = fields;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("rawtypes")
	public TShowField(TLayoutType layout, Property value, TComponentDescriptor descriptor ,TShowFieldValue... fields) {
		this.descriptor = descriptor;
		this.layout = layout;
		this.value = value;
		this.fields = fields;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param value
	 * @param fields
	 */
	@SuppressWarnings("rawtypes")
	public TShowField(Property value, TShowFieldValue... fields) {
		this.value = value;
		this.fields = fields;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Observable tValueProperty() {
		return value;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() throws Exception {
		
		pane = layout.getValue().newInstance();
		super.setAlignment(Pos.TOP_CENTER);
		super.getChildren().add(pane);
		if(value instanceof ListProperty) {
			((ObservableList)value).addListener(new ListChangeListener() {
				@Override
				public void onChanged(Change c) {
					pane.getChildren().clear();
					for(Object obj : c.getList()) {
						try {
							addField(obj);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			ListProperty lst = (ListProperty) value;
			for(Object obj : lst) {
				addField(obj);
			}
		}else {
			((ObservableValue)value).addListener(new ChangeListener() {

				@Override
				public void changed(ObservableValue arg0, Object arg1, Object arg2) {
					try {
						pane.getChildren().clear();
						if(arg2!=null)
							addField(arg2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			});;
			addField(value.getValue());
		}
		
	}

	@SuppressWarnings("static-access")
	private void addField(Object obj) throws Exception {
		if(fields!=null) {
			for(TShowFieldValue f : fields) {
				String v = getValue(obj, f);
				TFieldBox fb = this.buildFieldBox(v, f);
				pane.getChildren().add(fb);
				switch(layout) {
				case FLOWPANE:
					((FlowPane)pane).setMargin(fb, new Insets(0, 10, 0, 0));
					break;
				case HBOX:
					((HBox)pane).setMargin(fb, new Insets(0, 10, 0, 0));
					break;
				case VBOX:
					((VBox)pane).setMargin(fb, new Insets(0, 0, 10, 0));
					break;
				
				}
			}
		}else {
			String v = getValue(obj);
			Node c = buildNode(TLanguage.getInstance(null).getString(v));
			pane.getChildren().add(c);
		}
	}
	
	private TFieldBox buildFieldBox(String value, TShowFieldValue f) {
		TLabel l = StringUtils.isNotBlank(f.getLabel()) 
				? new TLabel(TLanguage.getInstance(null).getString(f.getLabel())) 
						: null;
		if(l!=null)
			l.setId("t-form-control-label");
		
		Node c = buildNode(TLanguage.getInstance(null).getString(value));
		TFieldBox box =  new TFieldBox(f.getName(), l, c, f.getLabelPosition());
		if(descriptor!=null)
			TFieldBoxBuilder.parse(descriptor, box);
		return box;
		
	}

	private Node buildNode(String value) {
		TLabel c = new TLabel(value);
		c.setId("t-form-label-field-value");
		return c;
	}

	@SuppressWarnings("rawtypes")
	private String getValue(Object obj) {
		if(obj!=null && obj instanceof Property) {
			obj = ((Property)obj).getValue();
		}
		return obj!=null ? obj.toString() : "";
	}
	
	@SuppressWarnings("rawtypes")
	private String getValue(Object obj, TShowFieldValue f) throws Exception {
		String v = "";
		Object t = (obj!=null && StringUtils.isNotBlank(f.getName())) 
				? TReflectionUtil.getGetterMethod(obj.getClass(), f.getName()).invoke(obj)
					: obj;
		if(t!=null) {
			if(t instanceof Property) 
				t = ((Property)t).getValue();
		
			if(t instanceof Date) {
				v = (StringUtils.isNotBlank(f.getPattern()))
						? TDateUtil.getFormatedDate((Date) t, f.getPattern())
						: TDateUtil.getFormatedDate((Date) t, TDateUtil.DDMMYYYY);
			}else {
				v = t.toString();
				if(StringUtils.isNotBlank(f.getPattern()))
					v = TMaskUtil.applyMask(v, f.getPattern());
			}
		}
		return v;
	}

	@Override
	public void settFieldStyle(String style) {
		setStyle(style);
	}
	
	@Override
	public String gettComponentId() {
		return t_componentId;
	}
	
	@Override
	public void settComponentId(String id) {
		t_componentId = id;
	}
	
}
