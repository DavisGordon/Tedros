/**
 * 
 */
package org.tedros.chat.module.client.decorator;

import org.tedros.api.presenter.view.ITDynaView;
import org.tedros.chat.module.client.model.ChatMV;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.control.TButton;
import org.tedros.fx.presenter.decorator.TListViewHelper;
import org.tedros.fx.presenter.entity.decorator.TMasterCrudViewDecorator;

/**
 * @author Davis Gordon
 *
 */
public class ChatDecorator extends TMasterCrudViewDecorator<ChatMV> {

	private TButton hidePopOverButton;
	
	@Override
	public void decorate() {
		super.decorate();
		super.getView().gettProgressIndicator().setMediumLogo();
	}
	
	@Override
	public void showScreenSaver() {
	}
	
	@Override
	protected void configAllButtons() {
		super.configAllButtons();
		hidePopOverButton = new TButton(iEngine.getString(TFxKey.BUTTON_CLOSE));
		addItemInTHeaderToolBar(hidePopOverButton);
	}
	
	protected void configListView() {
		String title = tPresenter!=null ? tPresenter.decorator().listTitle() : null;
		// get the list view settings
		TListViewPresenter tAnnotation = getPresenter().getModelViewClass().getAnnotation(TListViewPresenter.class);
		if(tAnnotation!=null)
			helper = new TListViewHelper<>(title, tAnnotation.listViewMaxWidth(), tAnnotation.listViewMinWidth(), tAnnotation.paginator());
		else
			helper = new TListViewHelper<>(title, 250, 250, null);
		helper.gettListViewLayout().getChildren().remove(0);
		helper.gettListView().setMaxHeight(120);
		// add the list view box at the top 
		addItemInTTopContent(helper.gettListViewPane());
	}
	
	@Override
	public boolean isListContentVisible() {
    	final ITDynaView<ChatMV> view = getView();
		return view.gettTopContent().getChildren().contains(helper.gettListViewPane());
    }
    
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#hideListContent()
	 */
	@Override
	public void hideListContent() {
    	final ITDynaView<ChatMV> view = getView();
		view.gettTopContent().getChildren().remove(helper.gettListViewPane());
	}
	
	/* (non-Javadoc)
	 * @see org.tedros.fx.presenter.entity.decorator.ITListViewDecorator#showListContent()
	 */
	@Override
	public void showListContent() {
		final ITDynaView<ChatMV> view = getView();
		if(!view.gettTopContent().getChildren().contains(helper.gettListViewPane()))
			addItemInTTopContent(helper.gettListViewPane());
		
	}

	/**
	 * @return the hidePopOverButton
	 */
	public TButton getHidePopOverButton() {
		return hidePopOverButton;
	}

}
