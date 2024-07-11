/**
 * 
 */
package org.tedros.tools.ai.model;

import org.tedros.ai.model.Message;
import org.tedros.ai.model.Teros;
import org.tedros.core.style.TStyle;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.control.TButtonField;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TScrollPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.TToolBar;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.tools.ai.setting.TerosSetting;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

/**
 * @author Davis Gordon
 *
 */
@TSetting(TerosSetting.class)
@TForm(scroll=false, editCssId = "t-fieldbox-info")
@TPresenter(model=Teros.class,
	decorator=@TDecorator(type=TViewDecorator.class),
	behavior=@TBehavior(type=TViewBehavior.class))
public class TerosMV extends TModelView<Teros> {
	
	@TScrollPane(content = "messages", 
		node = @TNode(style = "-fx-padding: 20 0 0 0;", parse = true),
		control = @TControl(maxHeight = 400, minHeight = 400, parse = true),
		vbarPolicy = ScrollBarPolicy.ALWAYS)
	private SimpleStringProperty title;
	
	@TVBox(fillWidth=true, spacing=10)
	@TGenericType(model = Message.class, modelView=MsgMV.class)
	private ITObservableList<MsgMV> messages; 

	@TTextAreaField(prefRowCount=4, wrapText=true, 
			control= @TControl(maxHeight=70, parse = true))
	private SimpleStringProperty prompt;
	
	@TToolBar(items = { "sendBtn", "clearBtn" })
	@TButtonField(labeled = @TLabeled(parse = true, text=TFxKey.BUTTON_SEND),
			node = @TNode(style = "-fx-font-size: 0.8em;", parse = true))
	private SimpleStringProperty sendBtn;

	@TButtonField(labeled = @TLabeled(parse = true, text=TFxKey.BUTTON_CLEAN),
			node = @TNode(style = TStyle.FONT_SIZE_095em, parse = true))
	private SimpleStringProperty clearBtn;
	
	public TerosMV(Teros m) {
		super(m);
		super.formatToString("%s", title);
	}

	/**
	 * @return the messages
	 */
	public ITObservableList<MsgMV> getMessages() {
		return messages;
	}

	/**
	 * @return the prompt
	 */
	public SimpleStringProperty getPrompt() {
		return prompt;
	}

}
