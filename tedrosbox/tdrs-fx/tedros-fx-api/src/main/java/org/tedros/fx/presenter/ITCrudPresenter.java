package org.tedros.fx.presenter;

import org.tedros.fx.form.ITModelForm;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Toggle;

@SuppressWarnings("rawtypes")
public interface ITCrudPresenter<M extends TEntityModelView> {
	
	public void setForm(ITModelForm<M> form);
	
	public void clearForm();
	
	public M getModel();
	 
	public void setModel(M model);

	public void disableResultGrid(boolean disabled);
	
	public void disableModeButton(boolean disabled);
	
	public void showModeButton(boolean show);
	
	public void disableSaveButton(boolean disabled);
	
	public void showSaveButton(boolean show);
	
	public void disableNewButton(boolean disabled);
	
	public void showNewButton(boolean show);
	
	public void disableRemoveButton(boolean disabled);
	
	public void showRemoveButton(boolean show);
	
	public BooleanProperty resultGridDisabledProperty();
	
	public void disableAllCrudButton(boolean disabled);
	
	public void showAllCrudButton(boolean show);

	public BooleanProperty saveButtonDisabledProperty();

	public BooleanProperty saveButtonVisibleProperty();

	public BooleanProperty newButtonDisabledProperty();

	public BooleanProperty newButtonVisibleProperty();

	public BooleanProperty removeButtonDisabledProperty();

	public BooleanProperty removeButtonVisibleProperty();

	public BooleanProperty modeButtonDisableProperty();

	public BooleanProperty modeButtonVisibleProperty();
	
	public ReadOnlyObjectProperty<Toggle> modeSelectedToggleProperty();
	
	public void newAction();
	
	public void saveAction();
	
	public void deleteAction();
	
	public void selectedItemAction(M new_val);
	
	public void changeModeAction();
	
	
	public void setEditMode();
	
	public void setReaderMode();
	
	public boolean isReaderModeSelected();
	
	public boolean isEditModeSelected();
	
}
