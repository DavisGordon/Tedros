package com.tedros.settings.layout.model;



import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.fxapi.annotation.TBooleanValues;
import com.tedros.fxapi.annotation.control.TCheckBoxField;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelDefaultSetting;
import com.tedros.fxapi.annotation.control.TLongField;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.layout.TFieldSet;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TReader;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.control.TLabeled;
import com.tedros.fxapi.annotation.scene.control.TSize;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TZeroValidation;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.settings.layout.behavior.ExampleBehavior;
import com.tedros.settings.layout.decorator.ExampleDecorator;
import com.tedros.settings.layout.form.EntityExampleForm;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;


@TLabelDefaultSetting(wrapText=false, position = TLabelPosition.TOP, node=@TNode(id="t-form-control-label", parse = true))
@TPresenter(behavior=@TBehavior(type = ExampleBehavior.class), decorator=@TDecorator(type=ExampleDecorator.class, viewTitle="#{label.paineltitle}"), type = TDynaPresenter.class, modelClass = EntityExampleForm.class)
public class ExampleViewModel extends TEntityModelView<EntityExampleForm> {
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Example form", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty text;
	
	@TReader
	@TLabel(text="Code:")
	@TLongField(zeroValidation=TZeroValidation.GREATHER_THAN_ZERO, maxLength=3,	control=@TControl(maxSize=@TSize(width=100, height=30), parse = true), textInputControl=@TTextInputControl(editable=false, parse = true))
	@THBox(	pane=@TPane(children={"id","longField"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="id", priority=Priority.ALWAYS), 
   				   		@TPriority(field="longField", priority=Priority.ALWAYS) }))
	private SimpleLongProperty id;
	
	@TReader
	@TLabel(text="Age:", font=@TFont(size=56), control=@TControl(prefWidth=500, parse = true))
	@TLongField(zeroValidation=TZeroValidation.GREATHER_THAN_ZERO, maxLength=3,	control=@TControl(maxSize=@TSize(width=100, height=30), parse = true))
	private SimpleLongProperty longField;
	
	@TReader
	@TLabel(text="Name:", control=@TControl(prefWidth=500, parse = true))
	@TTextField(textInputControl=@TTextInputControl(promptText="Name", parse = true), control=@TControl(tooltip="Name", parse = true), required=true, maxLength=20)
	@TFieldSet(fields = { "stringField", "textField", "booleanField" }, legend = "FieldSet")
	@TFieldBox(node=@TNode(id="stringField-fieldSet", parse=true))
	private SimpleStringProperty stringField;
	
	@TReader(wrappingWidth=600)
	@TLabel(text="About:", control=@TControl(prefWidth=500, parse = true))
	@TTextAreaField(textInputControl=@TTextInputControl(promptText="About", parse = true), 
	control=@TControl(tooltip="Digite um texto", parse = true), prefColumnCount=25, prefRowCount=2, 
	required=true, wrapText=true)
	private SimpleStringProperty textField;

	@TReader(label=@TLabel(text="Check"), booleanValues=@TBooleanValues(falseValue="No", trueValue="Yes"))
	@TLabel(text="Check")
	@TCheckBoxField(labeled=@TLabeled(text="Yes", parse = true), control=@TControl(tooltip="Check", parse = true), required=true)
	private SimpleBooleanProperty booleanField;
	
	public ExampleViewModel(){
		super(new EntityExampleForm());
	}
	
	public ExampleViewModel(EntityExampleForm entidade) {
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
		
		if(obj == null || !(obj instanceof ExampleViewModel))
			return false;
		
		ExampleViewModel p = (ExampleViewModel) obj;
		
		
		
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

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return null;
	}

	public final SimpleLongProperty getId() {
		return id;
	}

	public final void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getText() {
		return text;
	}

	public void setText(SimpleStringProperty text) {
		this.text = text;
	}


}
