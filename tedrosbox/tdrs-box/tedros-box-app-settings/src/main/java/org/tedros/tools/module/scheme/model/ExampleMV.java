package org.tedros.tools.module.scheme.model;



import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.TBooleanValues;
import org.tedros.fx.annotation.control.TCheckBoxField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TLabelDefaultSetting;
import org.tedros.fx.annotation.control.TLongField;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTextInputControl;
import org.tedros.fx.annotation.layout.TFieldSet;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.reader.TReader;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.scene.control.TSize;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.domain.TValidateNumber;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.scheme.behaviour.ExampleBehavior;
import org.tedros.tools.module.scheme.decorator.ExampleDecorator;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;


@TLabelDefaultSetting(wrapText=false, position = TLabelPosition.TOP, 
	node=@TNode(id="t-form-control-label", parse = true))
@TPresenter(
		behavior=@TBehavior(type = ExampleBehavior.class), 
		decorator=@TDecorator(type=ExampleDecorator.class, 
			viewTitle=ToolsKey.VIEW_EXAMPLE_PANEL), type = TDynaPresenter.class, modelClass = Example.class)
public class ExampleMV extends TEntityModelView<Example> {
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id=TFieldBox.INFO, parse = true))
	@TText(text=ToolsKey.TEXT_EXAMPLE_TITLE, textAlignment=TextAlignment.LEFT, 
	textStyle = TTextStyle.LARGE)
	private SimpleStringProperty text;
	
	@TReader
	@TLabel(text=TUsualKey.CODE)
	@TLongField(validate=TValidateNumber.GREATHER_THAN_ZERO, maxLength=3,	
	control=@TControl(maxSize=@TSize(width=100, height=30), parse = true), 
	textInputControl=@TTextInputControl(editable=false, parse = true))
	@THBox(	pane=@TPane(children={"id","longField"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="id", priority=Priority.ALWAYS), 
   				   		@TPriority(field="longField", priority=Priority.ALWAYS) }))
	private SimpleLongProperty id;
	
	@TReader
	@TLabel(text=TUsualKey.AMOUNT, font=@TFont(size=56), control=@TControl(prefWidth=500, parse = true))
	@TLongField(validate=TValidateNumber.GREATHER_THAN_ZERO, maxLength=3,	
	control=@TControl(maxSize=@TSize(width=100, height=30), parse = true))
	private SimpleLongProperty longField;
	
	@TReader
	@TLabel(text=TUsualKey.NAME, control=@TControl(prefWidth=500, parse = true))
	@TTextField(textInputControl=@TTextInputControl(promptText=TUsualKey.NAME, parse = true), 
	control=@TControl(tooltip=TUsualKey.NAME, parse = true), required=true, maxLength=20)
	@TFieldSet(fields = { "stringField", "textField", "booleanField" }, legend = TUsualKey.MAIN_TITLE)
	@TFieldBox(node=@TNode(id="stringField-fieldSet", parse=true))
	private SimpleStringProperty stringField;
	
	@TReader(wrappingWidth=600)
	@TLabel(text=TUsualKey.DESCRIPTION, control=@TControl(prefWidth=500, parse = true))
	@TTextAreaField(textInputControl=@TTextInputControl(promptText=TUsualKey.DESCRIPTION, parse = true), 
	control=@TControl(tooltip=TUsualKey.DESCRIPTION, parse = true), prefColumnCount=25, prefRowCount=2, 
	required=true, wrapText=true)
	private SimpleStringProperty textField;

	@TReader(label=@TLabel(text=TUsualKey.ENABLE), 
			booleanValues=@TBooleanValues(
					falseValue=TUsualKey.NO, 
					trueValue=TUsualKey.YES))
	@TLabel(text=TUsualKey.ENABLE)
	@TCheckBoxField(labeled=@TLabeled(text=TUsualKey.YES, parse = true), required=true)
	private SimpleBooleanProperty booleanField;
	
	public ExampleMV(){
		super(new Example());
	}
	
	public ExampleMV(Example entidade) {
		super(entidade);
	}
	
	@Override
	public String toString() {			
		
		return (getStringField()!=null)? getStringField().getValue() : "";
		
	}
		
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof ExampleMV))
			return false;
		
		ExampleMV p = (ExampleMV) obj;
		
		
		
		if(getLongField()!=null && getLongField().getValue()!=null &&  p.getLongField()!=null && p.getLongField().getValue()!=null){
			if(!(getLongField().getValue().equals(Long.valueOf(0)) && p.getLongField().getValue().equals(Long.valueOf(0))))
				return getLongField().getValue().equals(p.getLongField().getValue());
		}	
		
		if(getStringField()!=null && getStringField().getValue()!=null &&  p.getStringField()!=null && p.getStringField().getValue()!=null)
			return getStringField().getValue().equals(p.getStringField().getValue());
		
		return false;
	}

	public SimpleLongProperty getLongField() {
		return longField;
	}

	public void setLongField(SimpleLongProperty longField) {
		this.longField = longField;
	}



	public SimpleStringProperty getStringField() {
		return stringField;
	}

	public void setStringField(SimpleStringProperty stringField) {
		this.stringField = stringField;
	}

	public SimpleStringProperty getTextField() {
		return textField;
	}

	public void setTextField(SimpleStringProperty textField) {
		this.textField = textField;
	}

	public SimpleBooleanProperty getBooleanField() {
		return booleanField;
	}

	public void setBooleanField(SimpleBooleanProperty booleanField) {
		this.booleanField = booleanField;
	}

	public SimpleStringProperty getText() {
		return text;
	}

	public void setText(SimpleStringProperty text) {
		this.text = text;
	}


}
