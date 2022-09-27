/**
 * 
 */
package org.tedros.chat.module.client.behaviour;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import javax.activation.MimeType;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.module.client.decorator.TChatDecorator;
import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.chat.module.client.model.TChatModel;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TImageExtension;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.fx.util.TFileBaseUtil;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.TFileModel;
import org.tedros.util.TFileUtil;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class TChatBehaviour extends TDynaViewSimpleBaseBehavior<TChatMV, TChatModel> {

	private TChatDecorator deco;
	
	@Override
	public void load() {
		super.load();
		
		this.deco = (TChatDecorator) super.getPresenter().getDecorator();
		
		String txt = "abc abc nhu hgtf gffuu yghh fdryu koojhg uvh u u tfg hnij bo abc nhu hgtf gffuu yghh fdryu koojhg uvh u u tfg hnij b";
		
		GridPane gp = this.deco.getMsgPane();
		int i = 0;
		for(int x=0; x<=2; x++) {
			ChatMessage m = new ChatMessage();
			m.setContent(txt);
			m.setInsertDate(new Date());
			TUser u;
			if(i==0) {
				u = new TUser();
				u.setName("Fulano de tal");
			}else {
				u = TedrosContext.getLoggedUser();
			}
		m.setFrom(u);
		StackPane p1 = buildTextPane(m, x%2==0);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		gp.add(p1, i, x);
		
		i = i==0 ? 1 : 0;
		//this.deco.getMsgsVB().getChildren().add(p1);
		}
		
		try {
			TFileModel fm = new TFileModel(new File("C:\\Users\\Davis Gordon\\Documents\\Covid Sem Fome\\picpay.png"));
			ITFileEntity fe = TFileBaseUtil.convert(fm);
			ChatMessage m = new ChatMessage();
			m.setFile((TFileEntity) fe);
			m.setInsertDate(new Date());
			TUser u;
			u = TedrosContext.getLoggedUser();
			
		m.setFrom(u);
		StackPane p1 = buildTextPane(m, false);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		gp.add(p1, 1, 4);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @param txt
	 * @return
	 */
	private StackPane buildTextPane(ChatMessage msg, boolean left) {
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
			Hyperlink hl = new Hyperlink(super.iEngine.getString(TFxKey.BUTTON_COPY));
			hl.getStyleClass().add(TTextStyle.SMALL.getValue());
			hl.setUserData(txt);
			EventHandler<ActionEvent> ev = e -> {
				String text = (String) ((Node) e.getSource()).getUserData();
				final Clipboard clipboard = Clipboard.getSystemClipboard();
			    final ClipboardContent content = new ClipboardContent();
			    content.putString(text);
			    clipboard.setContent(content);
			};
			super.getListenerRepository().add(UUID.randomUUID().toString(), ev);
			hl.setOnAction(new WeakEventHandler<>(ev));
			footer.getChildren().add(hl);
			
		}else if(file!=null){
			TText t1 = new TText(file.toString());
			t1.settTextStyle(TTextStyle.MEDIUM);
			t1.setWrappingWidth(300);
			VBox.setMargin(t1, new Insets(8));
			gp.getChildren().add(t1);
			
			Hyperlink hl = new Hyperlink(super.iEngine.getString(TFxKey.BUTTON_OPEN));
			hl.getStyleClass().add(TTextStyle.SMALL.getValue());
			EventHandler<ActionEvent> ev = e -> {
				//TFileUtil..open(f)
			};
			super.getListenerRepository().add(UUID.randomUUID().toString(), ev);
			hl.setOnAction(new WeakEventHandler<>(ev));
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
	
	@Override
	public String canInvalidate() {
		// TODO Auto-generated method stub
		return null;
	}

}
