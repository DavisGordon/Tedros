package org.somos.module.mailing.behavior;

import org.somos.model.CampanhaMailConfig;
import org.somos.module.mailing.model.CampanhaMailConfigModelView;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;

import javafx.scene.control.ListView;

public class CampanhaMailConfigBehavior
extends TMasterCrudViewBehavior<CampanhaMailConfigModelView, CampanhaMailConfig> {
	
	
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
		addAction(new TPresenterAction(TActionType.CANCEL) {
			@Override
			public boolean runBefore() {
				return true;
			}
			@Override
			public void runAfter() {
				final ListView<CampanhaMailConfigModelView> listView = decorator.gettListView();
				listView.getSelectionModel().clearSelection();
				setDisableModelActionButtons(true);
			}
		});
	}
	
	
	/**
	 * Perform this action when a mode radio change listener is triggered.
	 * */
	@Override
	public void changeModeAction() {
		if(super.actionHelper.runBefore(TActionType.CHANGE_MODE)){
			if(getModelView()!=null){
				if(decorator.isShowBreadcrumBar())
					decorator.gettBreadcrumbForm().tEntryListProperty().clear();
				showForm(null);
			}
		}
		super.actionHelper.runAfter(TActionType.CHANGE_MODE);
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
		final ListView<CampanhaMailConfigModelView> listView = this.decorator.gettListView();
		final int selectedIndex = listView.getSelectionModel().getSelectedIndex();
		listView.getItems().remove(selectedIndex);
		listView.getSelectionModel().clearSelection();
		setDisableModelActionButtons(true);
	}
		
	
	
	public boolean processNewEntityBeforeBuildForm(CampanhaMailConfigModelView model) {
		final ListView<CampanhaMailConfigModelView> list = this.decorator.gettListView();
		list.getItems().add(model);
		list.selectionModelProperty().get().select(list.getItems().size()-1);
		return false;
	}
	
	public void editEntity(CampanhaMailConfigModelView model) {
		
	}
	
	public void setDisableModelActionButtons(boolean flag) {
		if(decorator.gettCancelButton()!=null)
			decorator.gettCancelButton().setDisable(flag);
		if(decorator.gettSaveButton()!=null && isUserAuthorized(TAuthorizationType.SAVE))
			decorator.gettSaveButton().setDisable(flag);
		if(decorator.gettDeleteButton()!=null && isUserAuthorized(TAuthorizationType.DELETE))
			decorator.gettDeleteButton().setDisable(flag);
		if(decorator.gettEditModeRadio()!=null && isUserAuthorized(TAuthorizationType.EDIT))
			decorator.gettEditModeRadio().setDisable(flag);
		if(decorator.gettReadModeRadio()!=null && isUserAuthorized(TAuthorizationType.READ))
			decorator.gettReadModeRadio().setDisable(flag);
	}
	
}
