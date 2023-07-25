/**
 * 
 */
package org.tedros.chat.module.client.setting;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.tedros.chat.domain.ChatPropertie;
import org.tedros.chat.ejb.controller.IChatController;
import org.tedros.chat.ejb.controller.IChatMessageController;
import org.tedros.chat.ejb.controller.IChatUserController;
import org.tedros.chat.entity.Chat;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TImageExtension;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.fx.util.TFileBaseUtil;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.TFileModel;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.TAccessToken;
import org.tedros.util.TFileUtil;
import org.tedros.util.TedrosFolder;

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
public class ChatUtil {
	
	private TLanguage iEngine = TLanguage.getInstance();

	/**
	 * 
	 */
	public ChatUtil() {
	}
	
	@SuppressWarnings("unchecked")
	public Chat saveChat(TAccessToken token, Chat chat) throws Exception {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			IChatController serv = loc.lookup(IChatController.JNDI_NAME);
			TResult<Chat> res = serv.save(token, chat);
			return res.getValue();
		}finally {
			loc.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ChatMessage saveMessage(TAccessToken token, ChatMessage msg) throws Exception {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			IChatMessageController serv = loc.lookup(IChatMessageController.JNDI_NAME);
			TResult<ChatMessage> res = serv.save(token, msg);
			return res.getValue();
		}finally {
			loc.close();
		}
	}


	public List<ChatMessage> findMessages(TAccessToken token, Long chatId) throws Exception {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			Chat c = new Chat();
			c.setId(chatId);
			ChatMessage m = new ChatMessage();
			m.setChat(c);
			m.setDateTime(null);
			IChatMessageController serv = loc.lookup(IChatMessageController.JNDI_NAME);
			TResult<List<ChatMessage>> res = serv.findAll(token, m);
			return res.getValue();
		}finally {
			loc.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ChatUser findUser(TAccessToken token, Long id, String name) throws Exception {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			ChatUser c = new ChatUser();
			c.setUserId(id);
			c.setName(name);
			IChatUserController serv = loc.lookup(IChatUserController.JNDI_NAME);
			TResult<ChatUser> res = serv.find(token, c);
			return res.getValue();
		}finally {
			loc.close();
		}
	}
	
	public Integer getServerPort(TAccessToken token) throws Exception {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
			TResult<String> res1 = serv.getValue(token, ChatPropertie.CHAT_SERVER_PORT.getValue());
			if(res1.getState().equals(TState.SUCCESS) && StringUtils.isNotBlank(res1.getValue())) {
				return Integer.parseInt(res1.getValue());
			}else {
				return null;
			}
		}finally {
			loc.close();
		}
	}
	
	public String getServerIp(TAccessToken token) throws Exception {
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
			TResult<String> res1 = serv.getValue(token, ChatPropertie.CHAT_SERVER_IP.getValue());
			if(res1.getState().equals(TState.SUCCESS) && StringUtils.isNotBlank(res1.getValue())) {
				return res1.getValue();
			}else {
				return null;
			}
		}finally {
			loc.close();
		}
	}
	
	public StackPane buildTextPane(ChatMessage msg, boolean left, Consumer<Boolean> callback) {
		String user = msg.getFrom().getName();
		String txt = msg.getContent();
		final TFileEntity file = msg.getFile();
		Date dt =  msg.getInsertDate();
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
			t1.setWrappingWidth(300);
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
			
		}else if(file!=null){
			TText t1 = new TText(file.toString());
			t1.settTextStyle(TTextStyle.MEDIUM);
			t1.setWrappingWidth(300);
			VBox.setMargin(t1, new Insets(8));
			gp.getChildren().add(t1);
			
			Hyperlink hl = new Hyperlink(iEngine.getString(TFxKey.BUTTON_OPEN));
			hl.getStyleClass().add(TTextStyle.SMALL.getValue());
			EventHandler<ActionEvent> ev = e -> {
				try {
					TFileModel fm = TFileBaseUtil.convert(file);
					fm.setFilePath(TedrosFolder.EXPORT_FOLDER.getFullPath());
					TFileUtil.open(fm.getFile());
					if(callback!=null)
						callback.accept(true);
				} catch (IOException e1) {
					e1.printStackTrace();
					if(callback!=null)
						callback.accept(false);
				}
			};
			hl.setOnAction(ev);
			footer.getChildren().add(hl);
			
			String ext = file.getFileExtension();
			String[] exts = TImageExtension.ALL_IMAGES.getExtensionName();
			if(ArrayUtils.contains(exts, ext.toLowerCase())) {
				ImageView iv = new ImageView();
				iv.setFitWidth(300);
				iv.setPreserveRatio(true);
				if(!file.isNew()) {
					this.loadImage(iv, file);
				}else {
					this.setImage(iv, file.getByteEntity().getBytes());
				}

				VBox.setMargin(iv, new Insets(8));
				gp.getChildren().add(iv);
			}
		}
		gp.getChildren().add(footer);
		StackPane p1 = new StackPane();
		p1.setId("t-chat-msg-pane");
		p1.getChildren().add(gp);
		p1.setAlignment(left ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
		return p1;
	}
	

	/**
	 * @param imgView
	 * @param fe
	 */
	private void loadImage(final ImageView imgView, ITFileEntity fe) {
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
