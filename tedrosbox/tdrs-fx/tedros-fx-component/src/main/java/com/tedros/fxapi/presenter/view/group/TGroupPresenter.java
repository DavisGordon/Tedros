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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    	
    	boolean addFirst = true;
    	for (final ITGroupViewItem item : groupViewItemList) {
    		
    		final Button button = new Button(iEngine.getString(item.getButtonTitle()));
    		button.setId("t-button");
    		button.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent event) {
    				showView(item);
    			}
    		});
    		
    		view.gettGroupToolbar().getItems().add(button);
    		
    		if(addFirst){
    			showView(item);
    			addFirst = false;
    		}
		}
    	
    	final ToolBar tGroupToolbar = view.gettGroupToolbar(); 
    	
    	if(tGroupToolbar.getItems().size()>0)
    		tGroupToolbar.getItems().get(tGroupToolbar.getItems().size()-1).setId("t-last-button");
		
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
			final StackPane formSpacePane = view.gettFormSpace();
			final ITView<?> view = item.getViewInstance(getModule());
			//view.settModule(this.getView().gettModule());
			formSpacePane.getChildren().clear();
			formSpacePane.getChildren().add((Node)view);
			
			if(!view.gettPresenter().isViewLoaded())
				view.tLoad();
			
		} catch (Exception exception) {
		    throw new RuntimeException(exception);
		}
	}

	@Override
	public void stop() {
		if(groupViewItemList!=null){
			for (ITGroupViewItem itGroupViewItem : groupViewItemList) {
				ITView view = itGroupViewItem.getViewInstance(getModule());
				if(view!=null)
					view.gettPresenter().stop();
			}
		}
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
