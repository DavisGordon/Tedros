/**
 * 
 */
package org.tedros.fx.builder;

import java.util.Arrays;
import java.util.List;

import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.TFxKey;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.util.TModelViewUtil;
import org.tedros.server.model.ITModel;

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
