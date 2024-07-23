package org.tedros.fx.presenter.view.group;

import java.util.Optional;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.api.presenter.ITGroupPresenter;
import org.tedros.api.presenter.view.ITDynaView;
import org.tedros.api.presenter.view.ITGroupViewItem;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
    	HBox.setHgrow(tGroupToolbar, Priority.ALWAYS);
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
	
	public void loadView() {
		
	}
	
	private void showView(final ITGroupViewItem item) {
		try {
			this.viewLoadedProperty.unbind();
			final ITView<?> view = item.getViewInstance(getModule());
			this.viewLoadedProperty.bind(view.gettPresenter().viewLoadedProperty());
			
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
    public void setViewLoaded(boolean loaded) {
    	viewLoadedProperty.setValue(loaded);
    }
    
    @Override
    public boolean isViewLoaded() {
    	return viewLoadedProperty.get();
    }
    
    @Override
    public ReadOnlyBooleanProperty viewLoadedProperty() {
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
		StringBuilder msg = new StringBuilder();
		if(groupViewItemList!=null){
			groupViewItemList.stream()
			.filter(p-> {
				return p.isViewInitialized();
			}).forEach(i -> {
				ITView v = i.getViewInstance(getModule());
				String m = v.gettPresenter().canInvalidate();
				if(m!=null)
					msg.append(i.getButtonTitle())
					.append(": ")
					.append(m)
					.append("\n");
			});
		}
		return msg.toString().isEmpty() ? null : msg.toString();
	}

	public ITModule getModule() {
		return module;
	}

	public void setModule(ITModule module) {
		this.module = module;
		String uuid = TedrosAppManager.getInstance().getModuleContext(module).getModuleDescriptor().getApplicationUUID();
		iEngine.setCurrentUUID(uuid);
	}
	

	@Override
    public boolean isLoadable(Class<? extends ITModelView> modelViewClasd) {
    	return this.findItem(modelViewClasd)!=null;
    }
    
	
	@Override
	public <M extends ITModelView> void lookupAndShow(Class<M> modelViewClass) {
		if(modelViewClass == null)
			throw new IllegalArgumentException("The modelViewClass argument cannot be null"); 
		
		ITGroupViewItem item = findItem(modelViewClass);
		if(item==null)
			throw new RuntimeException("The class "+modelViewClass.getSimpleName()+" has no item add in this TGroupPresenter ");
    	MenuItem mi = this.getMenuItem(item);
		if(mi!=null) 
			mi.fire();
	}


	@SuppressWarnings("unchecked")
	@Override
	public <M extends ITModelView> void lookupAndShow(M modelView) {
		if(modelView == null)
			throw new IllegalArgumentException("The modelView argument cannot be null"); 
		
		ITGroupViewItem item = findItem(modelView.getClass());
		if(item==null)
			throw new RuntimeException("The class "+modelView.getClass().getSimpleName()+" has no item add in this TGroupPresenter ");
    	MenuItem mi = this.getMenuItem(item);
		if(mi!=null) {
			ListChangeListener<Node> chl = buildListener((v, l) -> {
				TDynaPresenter p = (TDynaPresenter) v.gettPresenter();
				p.lookupAndShow(modelView);
				mainView.gettFormSpace().getChildren().removeListener(l);
			});
			mainView.gettFormSpace().getChildren().addListener(chl);
			mi.fire();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <M extends ITModelView> void lookupAndShow(ObservableList<M> modelsView) {
		if(modelsView == null || modelsView.isEmpty())
			throw new IllegalArgumentException("The modelsView argument cannot be null or empty"); 
		
		if(modelsView.size()==1) {
			this.lookupAndShow(modelsView.get(0));
			return;
		}
		
		Class<? extends ITModelView> cls = modelsView.get(0).getClass();
		ITGroupViewItem item = findItem(cls);
		if(item==null)
			throw new RuntimeException("The class "+cls.getSimpleName()+" has no item add in this TGroupPresenter ");
    	MenuItem mi = this.getMenuItem(item);
		if(mi!=null) {
			ListChangeListener<Node> chl = buildListener((v, l) -> {
				TDynaPresenter p = (TDynaPresenter) v.gettPresenter();
				p.lookupAndShow(modelsView);
				mainView.gettFormSpace().getChildren().removeListener(l);
			});
			mainView.gettFormSpace().getChildren().addListener(chl);
			mi.fire();
		}
		
	}

	private MenuItem getMenuItem(ITGroupViewItem item) {
		final ToolBar tGroupToolbar = mainView.gettGroupToolbar(); 
    	MenuBar mb = null;
    	Optional<Node> op = tGroupToolbar.getItems().stream()
    			.filter(p->{
    				return p instanceof MenuBar;
    			}).findFirst();
    	if(op.isPresent()) {
    		mb = (MenuBar) op.get();
			for(Menu m : mb.getMenus()){
				Optional<MenuItem> mop = m.getItems().stream().filter(p->{
					return p.getText().equals(iEngine.getString(item.getButtonTitle()));
				}).findFirst();
				
				if(mop.isPresent()) {
					return mop.get();
				}
			}
    	}
		return null;
	}

	/**
	 * @param modelView
	 * @return
	 */
	private <M extends ITModelView> ListChangeListener<Node> buildListener(BiConsumer<ITView, ListChangeListener> f) {
		ListChangeListener<Node> chl = new ListChangeListener<Node>() {
			@Override
			public void onChanged(Change c) {
				if(c.next() && c.wasAdded()) {
					Node n = (Node) c.getList().get(0);
					if(n instanceof ITView) {
						ITView v = (ITView) n;
						if(!v.gettPresenter().isViewLoaded()){
							v.gettPresenter().viewLoadedProperty().addListener((a,o,b)->{
								if(b) 
									f.accept(v, this);
							});
						}else
							f.accept(v, this);
					}
				}
			}
		};
		return chl;
	}

	/**
	 * @param modelView
	 * @return
	 */
	private <M extends ITModelView> ITGroupViewItem findItem(Class<M> modelViewClass) {
		Optional<ITGroupViewItem> op = groupViewItemList.stream()
				.filter(p->{
					return p.getModelViewClass()==modelViewClass;
				}).findFirst();
		ITGroupViewItem item = op.isPresent() 
				? op.get()
						: null;
		return item;
	}


}
