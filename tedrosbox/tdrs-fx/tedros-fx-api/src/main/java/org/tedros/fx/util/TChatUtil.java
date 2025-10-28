/**
 * 
 */
package org.tedros.fx.util;

import java.text.DateFormat;
import java.util.Date;

import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TText;
import org.tedros.fx.control.TText.TTextStyle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class TChatUtil {

	/**
	 * 
	 */
	private TChatUtil() {
	}
	
	public static StackPane buildTextPane(String user, String txt, Double txtWrapWidth, Date dt) {

		TLanguage iEngine = TLanguage.getInstance();
		
		String dtf = dt!=null 
				? DateFormat
				.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.DEFAULT, TLanguage.getLocale())
				.format(dt)
				: "";
		
		VBox gp = new VBox(8);
		HBox header = new HBox(4);
		header.setId("t-chat-msg-header");
		gp.getChildren().add(header);
		if(user!=null) {
			TText t2 = new TText(user);
			t2.settTextStyle(TTextStyle.MEDIUM);
			header.getChildren().add(t2);
		}
		HBox footer = new HBox(10);
		footer.setAlignment(Pos.CENTER_LEFT);
		footer.setId("t-chat-msg-footer");
		if(!"".equals(dtf)) {
			TText t2 = new TText(dtf);
			t2.settTextStyle(TTextStyle.SMALL);
			footer.getChildren().add(t2);
		}
		if(txt!=null) {
			TText t1 = new TText(txt);
			t1.settTextStyle(TTextStyle.MEDIUM);
			t1.setWrappingWidth(txtWrapWidth);
			t1.setId("msgTxt");
			VBox.setMargin(t1, new Insets(8));
			gp.getChildren().add(t1);
			Hyperlink hl = new Hyperlink(iEngine.getString(TFxKey.BUTTON_COPY));
			hl.getStyleClass().add(TTextStyle.SMALL.getValue());
			hl.setUserData(txt);
			EventHandler<ActionEvent> ev = e -> {
				String text = (String) ((Node) e.getSource()).getUserData();
				final Clipboard clipboard = Clipboard.getSystemClipboard();
			    final ClipboardContent content = new ClipboardContent();
			    content.putString(text);
			    clipboard.setContent(content);
			};
			hl.setOnAction(ev);
			footer.getChildren().add(hl);
			
		}
		gp.getChildren().add(footer);
		StackPane p1 = new StackPane();
		p1.setId("t-chat-msg-pane");
		p1.getChildren().add(gp);
		//p1.setAlignment(left ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
		return p1;
	}

}
