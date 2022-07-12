/**
 * 
 */
package com.tedros.fxapi.builder;

import java.util.Arrays;
import java.util.List;

import com.tedros.core.ITModule;
import com.tedros.core.TLanguage;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.TFxKey;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.util.TModelViewUtil;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public abstract class TModelRowFactoryCallBackBuilder<M extends TModelView<?>> extends TContextMenuRowFactoryCallBackBuilder<M> {

protected abstract Class<? extends ITModule> getTargetModuleClass(Class<? extends ITModelView> modelViewClass);
	
	protected abstract Class<? extends TModelView> getTargetModelViewClass(Class<? extends ITModel> modelClass);
	
	@SuppressWarnings("unchecked")
	@Override
	List<MenuItem> getMenuItems(TableView<M> table, TableRow<M> row) {
		TLanguage iE = TLanguage.getInstance();
		MenuItem edit = new MenuItem(iE.getString(TFxKey.BUTTON_EDIT));
		edit.setOnAction(e->{
			M mv = row.getItem();
			ITModel model = mv.getModel();
			TModelViewUtil mvu = new TModelViewUtil(getTargetModelViewClass(model.getClass()), model.getClass(), model);
			ITModelView target = mvu.convertToModelView();
			TedrosAppManager.getInstance().loadInModule(getTargetModuleClass(target.getClass()), target);
		});
		return Arrays.asList(edit);
	}

}
