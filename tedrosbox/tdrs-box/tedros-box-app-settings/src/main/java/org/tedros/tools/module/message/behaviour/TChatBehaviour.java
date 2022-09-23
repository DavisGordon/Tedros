/**
 * 
 */
package org.tedros.tools.module.message.behaviour;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import javax.activation.MimeType;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.message.model.TMessage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TImageExtension;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.tools.module.message.decorator.TChatDecorator;
import org.tedros.tools.module.message.model.TChatMV;
import org.tedros.tools.module.message.model.TChatModel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
		for(int x=0; x<=8; x++) {
			TMessage m = new TMessage();
			m.setContent(txt);
			m.setInsertDate(new Date());
		StackPane p1 = buildTextPane(m, x%2==0);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		gp.add(p1, i, x);
		i = i==0 ? 1 : 0;
		//this.deco.getMsgsVB().getChildren().add(p1);
		}
		
		
	}

	/**
	 * @param txt
	 * @return
	 */
	private StackPane buildTextPane(TMessage msg, boolean left) {
		String txt = msg.getContent();
		final TFileEntity file = msg.getFile();
		Date dt =  msg.getInsertDate();
		String dtf = dt!=null 
				? DateFormat
				.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, TLanguage.getLocale())
				.format(dt)
				: "";
		
		GridPane gp = new GridPane();
		
		if(txt!=null) {
			TText t1 = new TText(txt);
			t1.settTextStyle(TTextStyle.MEDIUM);
			t1.setWrappingWidth(300);
			
			gp.add(t1, 0, 0, 2, 1);
			if(!"".equals(dtf)) {
				TText t2 = new TText(dtf);
				t2.settTextStyle(TTextStyle.SMALL);
				gp.add(t2, 0, 1);
			}
			
			
		}else if(file!=null){
			TText t1 = new TText(file.toString());
			t1.settTextStyle(TTextStyle.MEDIUM);
			t1.setWrappingWidth(300);
			gp.add(t1, 0, 0, 2, 1);
			
			Hyperlink hl = new Hyperlink(super.iEngine.getString(TFxKey.BUTTON_OPEN));
			
			EventHandler<ActionEvent> ev = e -> {
				
			};
			super.getListenerRepository().add(UUID.randomUUID().toString(), ev);
			hl.setOnAction(new WeakEventHandler<>(ev));
			
			String ext = file.getFileExtension();
			String[] exts = TImageExtension.ALL_IMAGES.getExtensionName();
			if(ArrayUtils.contains(exts, "*."+ext.toLowerCase())) {
				ImageView iv = new ImageView();
				iv.setFitWidth(300);
				iv.setPreserveRatio(true);
				if(!file.isNew()) {
					this.loadImage(iv, file);
				}else {
					this.setImage(iv, file.getByteEntity().getBytes());
				}
				gp.add(iv, 0, 1, 2, 1);
				gp.add(hl, 0, 2, 2, 1);
				
				if(!"".equals(dtf)) {
					TText t2 = new TText(dtf);
					t2.settTextStyle(TTextStyle.SMALL);
					gp.add(t2, 0, 3);
				}
			}else {
				gp.add(hl, 0, 1, 2, 1);
				if(!"".equals(dtf)) {
					TText t2 = new TText(dtf);
					t2.settTextStyle(TTextStyle.SMALL);
					gp.add(t2, 0, 2);
				}
			}
			
			
		}
		
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
