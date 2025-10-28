/**
 * 
 */
package org.tedros.fx.control;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.app.component.ITComponent;
import org.tedros.core.TLanguage;
import org.tedros.fx.converter.TConverter;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.form.TFieldBox;
import org.tedros.fx.form.TFieldBoxBuilder;
import org.tedros.fx.util.TMaskUtil;
import org.tedros.util.TDateUtil;
import org.tedros.util.TLoggerUtil;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
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
	private TField[] fields;
	private ITComponentDescriptor descriptor;
	
	private Pane pane;
	/**
	 * @param layout
	 * @param value
	 * @param fields
	 */
	@SuppressWarnings("rawtypes")
	public TShowField(TLayoutType layout, Property value, TField... fields) {
		this.layout = layout;
		this.value = value;
		this.fields = fields;
		try {
			init();
		} catch (Exception e) {
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
	}
	@SuppressWarnings("rawtypes")
	public TShowField(TLayoutType layout, Property value, ITComponentDescriptor descriptor ,TField... fields) {
		this.descriptor = descriptor;
		this.layout = layout;
		this.value = value;
		this.fields = fields;
		try {
			init();
		} catch (Exception e) {
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
	}
	/**
	 * @param value
	 * @param fields
	 */
	@SuppressWarnings("rawtypes")
	public TShowField(Property value, TField... fields) {
		this.value = value;
		this.fields = fields;
		try {
			init();
		} catch (Exception e) {
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Observable tValueProperty() {
		return value;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() throws Exception {
		
		super.setAlignment(Pos.TOP_CENTER);
		buildPane();
		if(value instanceof ListProperty) {
			((ListProperty)value).addListener((Change c) -> {
				buildPane();
				for(Object obj : c.getList()) {
					try {
						addField(obj);
					} catch (Exception e) {
						TLoggerUtil.error(getClass(), e.getMessage(), e);
					}
				}
			});
			ListProperty lst = (ListProperty) value;
			for(Object obj : lst) {
				addField(obj);
			}
		}else {
			((ObservableValue)value).addListener((a,b,n)->{
				try {
					buildPane();
					if(n!=null)
						addField(n);
				} catch (Exception e) {
					TLoggerUtil.error(getClass(), e.getMessage(), e);
				}
			});
			addField(value.getValue());
		}
		
	}

	@SuppressWarnings("static-access")
	private void addField(Object obj) throws Exception {
		if(fields!=null) {
			for(TField f : fields) {
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
	
	private TFieldBox buildFieldBox(String value, TField f) {
		TLabel l = StringUtils.isNotBlank(f.getLabel()) 
				? new TLabel(TLanguage.getInstance(null).getString(f.getLabel())) 
						: null;
		if(l!=null)
			l.setId("t-form-control-label");
		
		Node c = buildNode(TLanguage.getInstance(null).getString(value));
		TFieldBox box =  new TFieldBox(f.getName(), l, c, f.getLabelPosition());
		box.setId(null);
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
	private Object getObject(String path, Object obj) {
		Object o = null;
		String field = (path.contains(".")) 
				? StringUtils.substringBefore(path, ".")
						: path;
		String after = (path.contains(".")) 
				? StringUtils.substringAfter(path, ".")
						: "";
		Class target = obj.getClass();
		do {
			Field f;
			try {
				f = target.getDeclaredField(field);
				f.setAccessible(true);
				o = f.get(obj);
				f.setAccessible(false);
			} catch (NoSuchFieldException | SecurityException e) {
				target = target.getSuperclass();
			} catch (IllegalArgumentException | IllegalAccessException e) {
				TLoggerUtil.error(getClass(), e.getMessage(), e);
				throw new RuntimeException(e);
			} 
		}while(o==null || target == Object.class);
		
		if(o!=null && StringUtils.isNotBlank(after))
			o = getObject(after, o);
	
		return o;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getValue(Object obj, TField f) throws Exception {
		String v = "";
		
		Object t = (obj!=null && StringUtils.isNotBlank(f.getName())) 
				? getObject(f.getName(), obj)
					: obj;
		if(t!=null) {
			if(t instanceof Property) 
				t = ((Property)t).getValue();
			
			if(f.getConverter()!=TConverter.class) {
				TConverter c = f.getConverter().getDeclaredConstructor().newInstance();
				c.setComponentDescriptor(descriptor);
				c.setIn(t);
				return (String) c.getOut();
			}
		
			if(t instanceof Date) {
				if(StringUtils.isNotBlank(f.getFormat()))
					v = TDateUtil.format((Date) t, f.getFormat());
				else {
					v = TDateUtil.create(TLanguage.getLocale())
						.setDateStyle(f.getDateStyle().getValue())
						.setTimeStyle(f.getTimeStyle().getValue())
						.format((Date) t);
					}
			}else {
				v = TLanguage.getInstance().getString(t.toString());
				if(StringUtils.isNotBlank(f.getMask()))
					v = TMaskUtil.applyMask(v, f.getMask());
				else if(StringUtils.isNotBlank(f.getFormat()))
					v = String.format(f.getFormat(), t);
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
	/**
	 * 
	 */
	private void buildPane() {
	
		if(pane!=null && super.getChildren().contains(pane))
			super.getChildren().remove(pane);
		switch(layout) {
		case FLOWPANE:
			pane = new FlowPane();
			((FlowPane)pane).setPrefWrapLength(USE_COMPUTED_SIZE);
			((FlowPane)pane).setVgap(10);
			((FlowPane)pane).setHgap(10);
			break;
		case HBOX:
			pane = new HBox(10);
			break;
		case VBOX:
			pane = new VBox(10);
			break;
		
		}
		super.getChildren().add(pane);
	}
	
}
