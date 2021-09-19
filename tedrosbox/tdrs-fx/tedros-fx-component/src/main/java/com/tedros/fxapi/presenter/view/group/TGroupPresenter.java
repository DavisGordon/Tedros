package com.tedros.fxapi.presenter.view.group;

import com.tedros.core.ITModule;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.presenter.ITGroupPresenter;
import com.tedros.core.presenter.view.ITGroupViewItem;
import com.tedros.core.presenter.view.ITView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;

@SuppressWarnings("rawtypes")
public class TGroupPresenter implements ITGroupPresenter<TGroupView<ITGroupPresenter>> {

	private SimpleBooleanProperty viewLoadedProperty = new SimpleBooleanProperty(false);
	
	private SimpleStringProperty tViewTitle;
	private TGroupView<ITGroupPresenter> view;
	private ObservableList<ITGroupViewItem> groupViewItemList;
	private TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
	private ITModule module;
	
	private ITGroupViewItem itemSelected;
	
	public TGroupPresenter() {
		
	}
	
	public TGroupPresenter(String title, ObservableList<ITGroupViewItem> groupViewItemList) {
		setViewTitle(iEngine.getString(title));
		setGroupViewItemList(groupViewItemList);
	}
	
	public TGroupPresenter(ITModule module, String title, ObservableList<ITGroupViewItem> groupViewItemList) {
		setModule(module);
		setViewTitle(iEngine.getString(title));
		setGroupViewItemList(groupViewItemList);
	}
	

	@Override
	public void initialize() {
		
		view.tLoad();
		
		final StackPane formSpacePane = view.gettFormSpace();
		
		view.gettContentPane().setCenter(formSpacePane);
		
		String title = (tViewTitle!=null) ? tViewTitle.getValue() : null;
		
		
		tViewTitle.bindBidirectional(view.gettGroupTitle().textProperty());
		
		if(title!=null)
			view.gettGroupTitle().setText(title);
		
		MenuBar menuBar = new MenuBar();
		final Menu menu = new Menu(iEngine.getString("#{tedros.fxapi.label.options}"));
		menuBar.getMenus().add(menu);
    	boolean addFirst = true;
    	
    	for (final ITGroupViewItem item : groupViewItemList) {
    		
    		final MenuItem button = new MenuItem(iEngine.getString(item.getButtonTitle()));
    		button.setOnAction( e -> {
    			showView(item);
    			menu.setText(iEngine.getString("#{tedros.fxapi.label.options}")+" > "+button.getText());
    		});
    		menu.getItems().add(button);
    		if(addFirst){
    			showView(item);
    			menu.setText(iEngine.getString("#{tedros.fxapi.label.options}")+" > "+button.getText());
    			addFirst = false;
    		}
		}
		final ToolBar tGroupToolbar = view.gettGroupToolbar(); 
    	tGroupToolbar.getItems().add(menuBar);
	}
	
	@Override
	public TGroupView<ITGroupPresenter> getView() {
		return this.view;
	}
	
	@Override
	public void setView(TGroupView<ITGroupPresenter> view) {
		this.view = view;
	}
	
	@Override
    public void loadView() {
    	viewLoadedProperty.setValue(true);
    }
    
    @Override
    public boolean isViewLoaded() {
    	return viewLoadedProperty.get();
    }
    
    @Override
    public BooleanProperty viewLoadedProperty() {
    	return viewLoadedProperty;
    }
	
    //
	
	@Override
	public void setViewTitle(String title) {
		initViewTitle();
		this.tViewTitle.set(title);
		
	}

	@Override
	public String getViewTitle() {
		return (this.tViewTitle==null) ? null : this.tViewTitle.getValue();
	}
	
	@Override
	public ReadOnlyStringProperty viewTitleProperty() {
		initViewTitle();
		return this.tViewTitle;
	}
	
	private void initViewTitle() {
		if(this.tViewTitle==null)
			this.tViewTitle = new SimpleStringProperty();
	}

	@Override
	public ObservableList<ITGroupViewItem> getGroupViewItemList() {
		return groupViewItemList;
	}
	
	public void setGroupViewItemList(ObservableList<ITGroupViewItem> groupViewItemList) {
		for (ITGroupViewItem itGroupViewItem : groupViewItemList) {
			itGroupViewItem.setModule(getModule());
		}
		this.groupViewItemList = groupViewItemList;
	}

	private void showView(final ITGroupViewItem item) {
		try {
			itemSelected = item;
			final StackPane formSpacePane = view.gettFormSpace();
			final ITView<?> view = item.getViewInstance(getModule());
			((Node)view).setId("t-view-group");
			formSpacePane.getChildren().clear();
			formSpacePane.getChildren().add((Node)view);
			
			if(!view.gettPresenter().isViewLoaded())
				view.tLoad();
			
		} catch (Exception exception) {
		    throw new RuntimeException(exception);
		}
	}
	
	public ITView getSelectedView() {
		return itemSelected.getViewInstance(getModule());
	}

	@Override
	public boolean invalidate() {
		if(groupViewItemList!=null){
			for (ITGroupViewItem itGroupViewItem : groupViewItemList) {
				ITView view = itGroupViewItem.getViewInstance(getModule());
				if(view!=null) {
					if(!view.gettPresenter().invalidate()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public String canInvalidate() {
		
		if(groupViewItemList!=null){
			for (ITGroupViewItem itGroupViewItem : groupViewItemList) {
				ITView view = itGroupViewItem.getViewInstance(getModule());
				if(view!=null) {
					String msg = view.gettPresenter().canInvalidate();
					if(msg!=null) {
						return msg;
					}
				}
			}
		}
		return null;
	
	}

	public ITModule getModule() {
		return module;
	}

	public void setModule(ITModule module) {
		this.module = module;
		String uuid = TedrosAppManager.getInstance().getModuleContext(module).getModuleDescriptor().getApplicationUUID();
		iEngine.setCurrentUUID(uuid);
	}

}
