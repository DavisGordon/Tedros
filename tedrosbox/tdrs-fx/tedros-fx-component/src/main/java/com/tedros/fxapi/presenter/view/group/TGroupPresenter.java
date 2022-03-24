package com.tedros.fxapi.presenter.view.group;

import org.apache.commons.lang3.ArrayUtils;

import com.tedros.core.ITModule;
import com.tedros.core.TLanguage;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.presenter.ITGroupPresenter;
import com.tedros.core.presenter.view.ITGroupViewItem;
import com.tedros.core.presenter.view.ITView;
import com.tedros.fxapi.presenter.dynamic.view.ITDynaView;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

@SuppressWarnings("rawtypes")
public class TGroupPresenter implements ITGroupPresenter<TGroupView<ITGroupPresenter>> {

	private SimpleBooleanProperty viewLoadedProperty = new SimpleBooleanProperty(false);
	
	private SimpleStringProperty tViewTitle;
	private TGroupView<ITGroupPresenter> mainView;
	private ObservableList<ITGroupViewItem> groupViewItemList;
	private TLanguage iEngine = TLanguage.getInstance(null);
	private ITModule module;
	
	private ITGroupViewItem itemSelected;
	private HBox headerPaneJoined;
	private Node[] headerItensJoined;
	
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
		
		mainView.tLoad();
		
		final StackPane formSpacePane = mainView.gettFormSpace();
		
		mainView.gettContentPane().setCenter(formSpacePane);
		
		String title = (tViewTitle!=null) ? tViewTitle.getValue() : null;
		
		
		tViewTitle.bindBidirectional(mainView.gettGroupTitle().textProperty());
		
		if(title!=null)
			mainView.gettGroupTitle().setText(title);
		
		MenuBar menuBar = new MenuBar();
		final Menu menu = new Menu(iEngine.getString("#{tedros.fxapi.label.options}"));
		menuBar.getMenus().add(menu);
    	boolean addFirst = true;
    	final ToolBar tGroupToolbar = mainView.gettGroupToolbar(); 
    	tGroupToolbar.getItems().add(menuBar);
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
		
	}
	
	private void showView(final ITGroupViewItem item) {
		try {
			final ITView<?> view = item.getViewInstance(getModule());
			final StackPane formSpacePane = this.mainView.gettFormSpace();
			if(this.headerItensJoined!=null && this.headerPaneJoined!=null && !formSpacePane.getChildren().isEmpty()) {
				this.mainView.gettGroupToolbar().getItems().removeAll(headerItensJoined);
				final ITDynaView currView = (ITDynaView) formSpacePane.getChildren().get(0);
				currView.gettHeaderVerticalLayout().getChildren().add(0, this.headerPaneJoined);
				currView.gettHeaderHorizontalLayout().getChildren().addAll(headerItensJoined);
				this.headerItensJoined = null;
				this.headerPaneJoined = null;
			}
			
			((Node)view).setId("t-view-group");
			formSpacePane.getChildren().clear();
			formSpacePane.getChildren().add((Node)view);
			
			if(!view.gettPresenter().isViewLoaded())
				view.tLoad();
			
			if(item.isJoinHeader()) {
				final ITDynaView dynaView = (ITDynaView) view;
				headerItensJoined = new Node[0];
				for(Node n : dynaView.gettHeaderHorizontalLayout().getChildren()) {
					if(n.getId()!=null && 
							(n.getId().equals("t-view-title-box") || n.getId().equals("t-group-view-title-box")))
						continue;
					headerItensJoined = ArrayUtils.add(headerItensJoined, n);
				}
				dynaView.gettHeaderHorizontalLayout().getChildren().removeAll(headerItensJoined);
				this.headerPaneJoined = (HBox) dynaView.gettHeaderHorizontalLayout();
				dynaView.gettHeaderVerticalLayout().getChildren().remove(headerPaneJoined);
				this.mainView.gettGroupToolbar().getItems().addAll(headerItensJoined);
			}
			
			itemSelected = item;
			
		} catch (Exception exception) {
		    throw new RuntimeException(exception);
		}
	}
	
	@Override
	public TGroupView<ITGroupPresenter> getView() {
		return this.mainView;
	}
	
	@Override
	public void setView(TGroupView<ITGroupPresenter> view) {
		this.mainView = view;
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
		this.headerItensJoined = null;
		this.headerPaneJoined = null;
		this.itemSelected = null;
		this.groupViewItemList = null;
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
