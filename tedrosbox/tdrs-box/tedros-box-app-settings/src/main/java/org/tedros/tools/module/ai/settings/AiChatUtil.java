/**
 * 
 */
package org.tedros.tools.module.ai.settings;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.tedros.core.TLanguage;
import org.tedros.core.ai.model.TAiChatCompletion;
import org.tedros.core.ai.model.TAiChatMessage;
import org.tedros.core.ai.model.completion.chat.TChatRole;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TAiChatCompletionController;
import org.tedros.core.controller.TAiChatMessageController;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.TAccessToken;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class AiChatUtil {
	
	private TLanguage iEngine = TLanguage.getInstance();
	
	@SuppressWarnings("unchecked")
	public TAiChatCompletion saveChat(TAccessToken token, TAiChatCompletion chat) throws Exception {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TAiChatCompletionController serv = loc.lookup(TAiChatCompletionController.JNDI_NAME);
			TResult<TAiChatCompletion> res = serv.save(token, chat);
			return res.getValue();
		}finally {
			loc.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public TAiChatMessage saveMessage(TAccessToken token, TAiChatMessage msg) throws Exception {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TAiChatMessageController serv = loc.lookup(TAiChatMessageController.JNDI_NAME);
			TResult<TAiChatMessage> res = serv.save(token, msg);
			return res.getValue();
		}finally {
			loc.close();
		}
	}
	

	@SuppressWarnings("rawtypes")
	public boolean deleteMessage(TAccessToken token, TAiChatMessage msg) throws Exception {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TAiChatMessageController serv = loc.lookup(TAiChatMessageController.JNDI_NAME);
			TResult res = serv.remove(token, msg);
			return res.getState().equals(TState.SUCCESS);
		}finally {
			loc.close();
		}
	}


	public List<TAiChatMessage> findMessages(TAccessToken token, Long chatId) throws Exception {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TAiChatCompletion c = new TAiChatCompletion();
			c.setId(chatId);
			TAiChatMessage m = new TAiChatMessage();
			m.setChat(c);
			
			TAiChatMessageController serv = loc.lookup(TAiChatMessageController.JNDI_NAME);
			TResult<List<TAiChatMessage>> res = serv.findAll(token, m);
			return res.getValue();
		}finally {
			loc.close();
		}
	}
	
	
	public StackPane buildTextPane(TAiChatMessage msg) {
		String user = msg.getRole().equals(TChatRole.ASSISTANT) 
				? "Teros"
						: TedrosContext.getLoggedUser().getName();
		String txt = msg.getContent();
		Date dt =  msg.getInsertDate();
		return buildMsgPane(user, txt, dt);
	}

	public StackPane buildMsgPane(String user, String txt, Date dt) {
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
			t1.setWrappingWidth(800);
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
	

	/**
	 * @param imgView
	 * @param fe
	 */
	public void loadImage(final ImageView imgView, ITFileEntity fe) {
		SimpleObjectProperty<byte[]> bp = new SimpleObjectProperty<>();
		bp.addListener(new ChangeListener<byte[]>() {
			@Override
			public void changed(ObservableValue<? extends byte[]> arg0, byte[] arg1,
					byte[] n) {
				try {
					fe.getByteEntity().setBytes(n);
					setImage(imgView, n);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				bp.removeListener(this);
			}
		});
		loadBytes(bp, fe);
	}
	
	private void loadBytes(SimpleObjectProperty<byte[]> bp, ITFileEntity m) {
		try {
			TBytesLoader.loadBytesFromTFileEntity(m.getByteEntity().getId(), bp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param imgView
	 * @param bytes
	 * @throws IOException
	 */
	private void setImage(final ImageView imgView, byte[] bytes) {
		try(InputStream is = new ByteArrayInputStream(bytes)){
			Image img = new Image(is);
			imgView.setImage(img);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
