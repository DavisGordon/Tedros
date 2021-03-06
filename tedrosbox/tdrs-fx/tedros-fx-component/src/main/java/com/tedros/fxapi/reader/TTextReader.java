/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 06/04/2014
 */
package com.tedros.fxapi.reader;

import java.util.Arrays;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ContextMenuBuilder;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import com.tedros.core.context.TedrosContext;
import com.tedros.fxapi.util.TMaskUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public abstract class TTextReader<V> extends Text {

	private SimpleStringProperty maskProperty;
	private ContextMenu contextMenu;
	private EventHandler<MouseEvent> mouseClickEventHandler;
	
	
	public void setMask(String mask) {
		maskProperty().setValue(mask);
	}
	
	public String getMask() {
		return maskProperty!=null ? maskProperty.getValue() : null;
	}
	
	public SimpleStringProperty maskProperty() {
		buildMaskProperty();
		return maskProperty;
	}
	
	public abstract V getValue();
	
	public void setShowActionsToolTip(boolean show){
		if(show)
			buildContextMenu();
		else
			removeActionsToolTip();
	}
	
	private void removeActionsToolTip(){
		this.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickEventHandler);
	}
	
	private void buildMaskProperty() {
		if(maskProperty==null)
			maskProperty = new SimpleStringProperty();
		maskProperty.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				final V value =  getValue();
				if(value!=null && !(value instanceof Date || value instanceof Boolean) && arg2!=null){
					setText(TMaskUtil.applyMask(String.valueOf(value), arg2));
				}
			}
		});
	}
	
	private void buildContextMenu() {
		final MenuItem copyItem = buildCopyMenuITem();
		contextMenu = ContextMenuBuilder.create().items(Arrays.asList(copyItem)).build();
		contextMenu.setAutoFix(true);
		contextMenu.setAutoHide(true);
		mouseClickEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY)
					contextMenu.show(TedrosContext.getStage(), e.getScreenX() + 1, e.getScreenY() + 1);
			}
		};
		this.setOnMouseClicked(mouseClickEventHandler);
		
		Image img = new Image(getClass().getResourceAsStream("cursor-tedros.gif"));
		ImageCursor actionsCursor = new ImageCursor(img);
		this.setCursor(actionsCursor);
	}

	private MenuItem buildCopyMenuITem() {
		final MenuItem copyItem = MenuItemBuilder.create()
				.text("Copiar")
				.onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						if(getText()!=null){
							final Clipboard clipboard = Clipboard.getSystemClipboard();
						    final ClipboardContent content = new ClipboardContent();
						    content.putString(getText());
						    clipboard.setContent(content);
						}
					}
				})
				.build();
		return copyItem;
	}
	
}



