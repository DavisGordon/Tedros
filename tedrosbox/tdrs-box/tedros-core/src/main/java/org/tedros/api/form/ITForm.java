package org.tedros.api.form;

import java.util.Map;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.core.repository.TRepository;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * Define an {@link ITForm}
 * */
@SuppressWarnings("rawtypes")
public interface ITForm {
	
	public void tInitializeForm();
	
	public void tReloadForm();
	
	public ITFieldBox gettFieldBox(String fieldName);
	
	public Map<String, ITFieldBox> gettFieldBoxMap();
	
	public void settPresenter(ITPresenter presenter);
	
	public ITPresenter gettPresenter();
	
	public void setId(String id);
	
	public String getId();
	
	public void setStyle(String style);
	
	public ObservableList<Node> getChildren();
	
	public TRepository gettObjectRepository();
	
	public void tDispose();
	
	public ReadOnlyBooleanProperty tLoadedProperty();
	
	public boolean isLoaded();
	
	public ITSetting gettSetting();
}
