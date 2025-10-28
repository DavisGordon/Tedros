package org.tedros.fx.form;

import java.util.Objects;
import java.util.UUID;

import org.tedros.api.form.ITFieldBox;
import org.tedros.fx.control.ITField;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.util.TLoggerUtil;

import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * <pre>
 * A field box is a layout componet to arrange the control and his label.
 * 
 * Example:
 * Label label = (Label) fieldBox.gettLabel();
 * TTextField textField = (TTextField) fieldBox.gettControl();   
 * </pre>
 * 
 * @author Davis Gordon
 * */
public class TFieldBox extends StackPane implements ITField, ITFieldBox {

	final static String DEFAULT = "t-fieldbox";
	final static String MESSAGE = "t-fieldbox-message";
	final static String INFO = "t-fieldbox-info";
	final static String TITLE = "t-fieldbox-title";
	final static String FIRST = "t-fieldbox-hsplit-first";
	final static String MIDDLE = "t-fieldbox-hsplit-middle";
	final static String LAST = "t-fieldbox-hsplit-last";
	
	private Node label;
	private Node control;
	private String controlFieldName;
	private UUID uuid = UUID.randomUUID();
	
	/**
	 * Initialize the field box
	 * */
	public TFieldBox(String controlFieldName, Node label, Node control, TLabelPosition position) {
		this.controlFieldName = controlFieldName;
		this.label = label;
		this.control = control;
		setId(DEFAULT);
		if(this.label==null && control==null)
			return;
		
		if(this.label==null){
			getChildren().add(control);
			return;
		}
		
		if(position ==  null)
			position = TLabelPosition.TOP;
		
		if(position == TLabelPosition.TOP || position == TLabelPosition.DEFAULT){
			try{
				VBox box = new VBox(3);
				//VBox.setVgrow(this.control, Priority.ALWAYS);
				box.setAlignment(Pos.TOP_LEFT);
				box.getChildren().addAll(this.label, this.control);
				getChildren().add(box);
			}catch(NullPointerException e){
				TLoggerUtil.error(getClass(), e.getMessage(), e);
			}
		}
		
		if(position == TLabelPosition.BOTTOM){
			VBox box = new VBox(3);
			//VBox.setVgrow(this.control, Priority.ALWAYS);
			box.setAlignment(Pos.TOP_LEFT);
			box.getChildren().addAll(this.control, this.label);
			getChildren().add(box);
		}
		
		if(position == TLabelPosition.RIGHT){
			HBox box = new HBox(8);
			HBox.setHgrow(this.control, Priority.ALWAYS);
			box.setAlignment(Pos.TOP_LEFT);
			box.getChildren().addAll(this.control, this.label);
			getChildren().add(box);
		}
		
		if(position == TLabelPosition.LEFT){
			HBox box = new HBox(8);
			HBox.setHgrow(this.control, Priority.ALWAYS);
			box.setAlignment(Pos.TOP_LEFT);
			box.getChildren().addAll(this.label, this.control);
			getChildren().add(box);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITFieldBox#tValueProperty()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Observable tValueProperty() {
		return super.getChildren();
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITFieldBox#gettLabel()
	 */
	@Override
	public Node gettLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITFieldBox#gettControl()
	 */
	@Override
	public Node gettControl() {
		return control;
	}
		
	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITFieldBox#gettControlFieldName()
	 */
	@Override
	public String gettControlFieldName() {
		return controlFieldName;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.ITFieldBox#settFieldStyle(java.lang.String)
	 */
	@Override
	public void settFieldStyle(String style) {
		if(control!=null){
			if(control instanceof ITField){
				((ITField)control).settFieldStyle(style);
			}
		}
	}
	
	
	/*
	 * @Override public boolean equals(Object obj) { if(obj==null || !(obj
	 * instanceof TFieldBox)) return false; return
	 * this.uuid.equals(((TFieldBox)obj).uuid); //return
	 * EqualsBuilder.reflectionEquals(this, obj, true); }
	 */
	
	/*
	 * @Override public int hashCode() { return
	 * HashCodeBuilder.reflectionHashCode(this, true); }
	 */
	
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder("TFieldBox[");
		if(controlFieldName!=null)
			str.append("FIELD: "+controlFieldName+", ");
		if(label!=null)
			str.append("LABEL: "+label.toString()+", ");
		if(control!=null)
			str.append("CONTROL: "+control.toString()+", ");
		str.append("];");
		return str.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(controlFieldName, uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TFieldBox))
			return false;
		TFieldBox other = (TFieldBox) obj;
		return Objects.equals(controlFieldName, other.controlFieldName) && Objects.equals(uuid, other.uuid);
	}
	
}
