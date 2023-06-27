/**
 * 
 */
package org.tedros;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.ITViewBuilder;
import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;

/**
 * @author Davis Gordon
 *
 */
public class TViewBuilder implements ITViewBuilder{


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ITView build(TModule m) {
		ITView view = null;
		TView ann = m.getClass().getAnnotation(TView.class);
		TItem[] arr = ann.items();
		if(arr.length==1) {
			TItem i = arr[0];
			view = new TDynaView(m, i.modelView());
		}else {
			TViewItem[] items = new TViewItem[] {};
			for(TItem i : arr) 
				items = ArrayUtils.add(items, new TViewItem(TDynaGroupView.class, 
						(Class<? extends TModelView>) i.modelView(), i.title(), i.groupHeaders()));
			
			view = new TGroupView<TGroupPresenter>(m, ann.title(), items);
		}
		return view;
	}

}
