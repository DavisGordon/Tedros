package com.covidsemfome.module.acao.behavior;

import com.covidsemfome.model.Mailing;
import com.covidsemfome.module.acao.model.MailingModelView;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMainCrudViewWithListViewBehavior;

import javafx.scene.control.ListView;

public class MailingBehavior
extends TMainCrudViewWithListViewBehavior<MailingModelView, Mailing> {
	
	
	@Override
	public void load() {
		super.load();
		setDisableModelActionButtons(true);
		
	}
	@Override
	public void configColapseButton(){
		
	}
	
	@Override
	public void configNewButton() {
		
	}
	@Override
	public void configDeleteButton() {
		
	}
	
	

	protected void configCancelAction() {
		setCancelAction(new TPresenterAction<TDynaPresenter<MailingModelView>>() {

			@Override
			public boolean runBefore(TDynaPresenter<MailingModelView> presenter) {
				return true;
			}

			@Override
			public void runAfter(TDynaPresenter<MailingModelView> presenter) {
				final ListView<MailingModelView> listView = decorator.gettListView();
				listView.getSelectionModel().clearSelection();
				setDisableModelActionButtons(true);
			}
		});
	}
	
	
	/**
	 * Perform this action when a mode radio change listener is triggered.
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public void changeModeAction() {
		final TPresenterAction changeModeAction =  getChangeModeAction();
		final TDynaPresenter<MailingModelView> presenter = getPresenter();
		if(changeModeAction==null || (changeModeAction!=null && changeModeAction.runBefore(presenter))){
			if(getModelView()!=null){
				if(decorator.isShowBreadcrumBar())
					decorator.gettBreadcrumbForm().tEntryListProperty().clear();
				showForm(null);
			}
		}
		
		if(changeModeAction!=null)
			changeModeAction.runAfter(presenter);
		
	}
	
	
	
	/*protected void configListView() {
		this.decorator.gettListView()
		.getSelectionModel()
		.selectedItemProperty()
		.addListener(
				new ChangeListener<M>() {
	                public void changed(final ObservableValue<? extends M> ov, final M old_val, final M new_val) {
	                	if(isUserAuthorized(TAuthorizationType.EDIT))
	                		setViewMode(TViewMode.EDIT);
	                	else if(isUserAuthorized(TAuthorizationType.READ))
	                		setViewMode(TViewMode.READER);
	                	
	                	if(decorator.isShowBreadcrumBar())
	                		decorator.gettBreadcrumbForm().tEntryListProperty().clear();
	                	
	                	selectedItemAction(new_val);
	                	//decorator.hideListContent();
	                	setDisableModelActionButtons(false);
	            }	
	        });
		
		this.decorator.gettListView().getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				setDisableModelActionButtons(true);
			}
		});
	}*/
	
	public void remove() {
		final ListView<MailingModelView> listView = this.decorator.gettListView();
		final int selectedIndex = listView.getSelectionModel().getSelectedIndex();
		listView.getItems().remove(selectedIndex);
		listView.getSelectionModel().clearSelection();
		setDisableModelActionButtons(true);
	}
		
	
	
	public boolean processNewEntityBeforeBuildForm(MailingModelView model) {
		final ListView<MailingModelView> list = this.decorator.gettListView();
		list.getItems().add(model);
		list.selectionModelProperty().get().select(list.getItems().size()-1);
		return false;
	}
	
	public void editEntity(MailingModelView model) {
		
	}
	
	public void setDisableModelActionButtons(boolean flag) {
		if(decorator.gettCancelButton()!=null)
			decorator.gettCancelButton().setDisable(flag);
		if(decorator.gettSaveButton()!=null && isUserAuthorized(TAuthorizationType.SAVE))
			decorator.gettSaveButton().setDisable(flag);
		if(decorator.gettDeleteButton()!=null && isUserAuthorized(TAuthorizationType.DELETE))
			decorator.gettDeleteButton().setDisable(flag);
	}
	
}
