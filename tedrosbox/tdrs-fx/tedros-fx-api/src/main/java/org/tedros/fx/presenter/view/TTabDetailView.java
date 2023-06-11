/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 20/01/2014
 */
package org.tedros.fx.presenter.view;

import org.tedros.api.form.ITModelForm;
import org.tedros.api.presenter.view.ITView;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.TTabDetailPresenter;
import org.tedros.server.entity.ITEntity;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TTabDetailView<M extends TEntityModelView> extends Tab {
	
	private BorderPane layout;
	private Button newBtn;
	private Button removeBtn;
	private ListView<M> listView;
	private HBox menuBox;
	private ToolBar menuBar;
	
	
	public TTabDetailView(ITView masterView, final String tabName, final String listName, final ObservableList<M> models, Class<M> modelClass, Class<? extends ITEntity> entityClass, Class<? extends ITModelForm<?>> formClass) {
		setText(tabName);
		new TTabDetailPresenter<>(masterView, listName, this, models, modelClass, entityClass, formClass);		
	}
	
	public BorderPane getLayout() {
		return layout;
	}

	public void setLayout(BorderPane layout) {
		this.layout = layout;
	}

	public Button getNewBtn() {
		return newBtn;
	}

	public void setNewBtn(Button newBtn) {
		this.newBtn = newBtn;
	}

	public Button getRemoveBtn() {
		return removeBtn;
	}

	public void setRemoveBtn(Button removeBtn) {
		this.removeBtn = removeBtn;
	}

	public ListView<M> getListView() {
		return listView;
	}

	public void setListView(ListView<M> listView) {
		this.listView = listView;
	}

	public HBox getMenuBox() {
		return menuBox;
	}

	public void setMenuBox(HBox menuBox) {
		this.menuBox = menuBox;
	}

	public ToolBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(ToolBar menuBar) {
		this.menuBar = menuBar;
	}

}
