/**
 * 
 */
package org.tedros.chat.module.client.decorator;

import org.tedros.api.presenter.view.ITDynaView;
import org.tedros.chat.entity.ChatUser;
import org.tedros.chat.module.client.model.TChatMV;
import org.tedros.core.control.TProgressIndicator;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewSimpleBaseDecorator;
import org.tedros.fx.presenter.paginator.TPaginator;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class TChatDecorator extends TDynaViewSimpleBaseDecorator<TChatMV> {

	private ListView<ChatUser> usrLV;
    private TProgressIndicator tListViewProgressIndicator;
	private TPaginator paginator;
	
	@Override
	public void decorate() {
		StackPane tListViewPane = new StackPane();
		tListViewPane.getStyleClass().add("t-panel-background-color");
		tListViewPane.setAlignment(Pos.CENTER);
		
		VBox lvb = new VBox(5);
		tListViewPane.getChildren().add(lvb);
		tListViewProgressIndicator = new TProgressIndicator(tListViewPane);
		tListViewProgressIndicator.setSmallLogo();
		
		this.paginator = new TPaginator(true, true);
		this.paginator.setSearchFieldName("name");
		this.usrLV = new ListView<>();
		lvb.getChildren().addAll(usrLV, paginator);
		
		super.addItemInTLeftContent(tListViewPane);
		this.usrLV.getStyleClass().add("t-panel-background-color");
		
		this.showScreenSaver();
		
	}
	
	@SuppressWarnings("rawtypes")
	private void cleanCenterContent() {
		final ITDynaView view = getView();
		view.gettCenterContent().getChildren().clear();
	}
	
	public void showChatPane(ChatPane pane) {
		this.cleanCenterContent();
		super.addItemInTCenterContent(pane);
	}
	
	@Override
	public void showScreenSaver() {
		this.cleanCenterContent();
		super.addItemInTCenterContent(super.getScreenSaverPane());
	}

	/**
	 * @return the usrLV
	 */
	public ListView<ChatUser> getUsrLV() {
		return usrLV;
	}

	/**
	 * @return the paginator
	 */
	public TPaginator getPaginator() {
		return paginator;
	}

	/**
	 * @return the tListViewProgressIndicator
	 */
	public TProgressIndicator gettListViewProgressIndicator() {
		return tListViewProgressIndicator;
	}

}
