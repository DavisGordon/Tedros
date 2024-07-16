/**
 * 
 */
package org.tedros.fx.helper;

import java.util.List;
import java.util.function.Consumer;

import org.tedros.core.model.TModelViewUtil;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;

import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public final class TLoadListHelper {
	
	private TLoadListHelper() {}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void load(ObservableList list, String service,
			Class<? extends ITEntity> eClass, Class<? extends TModelView> mClass,
			Class<? extends TEntityProcess> pClass, TSelect sel, Consumer<Boolean> succeeded) throws Exception {
		
		TModelViewUtil util = mClass!=null && mClass!=TModelView.class
				? new TModelViewUtil(mClass, eClass)
						: null;
		load(service, eClass, mClass, pClass, sel, e->{
			if(util!=null) 
				list.add(util.convertToModelView(e));
			else
				list.add(e);
		}, succeeded);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void load(String service, Class<? extends ITEntity> eClass,
			Class<? extends TModelView> mClass, Class<? extends TEntityProcess> pClass, TSelect sel,
			Consumer<ITEntity> consume, Consumer<Boolean> callback) throws Exception {
		
		final TEntityProcess process = pClass!=TEntityProcess.class 
			? pClass.getDeclaredConstructor().newInstance()
				: new TEntityProcess(eClass, service) {};

		process.stateProperty().addListener((a,o,n)-> {
			if(n.equals(State.SUCCEEDED)){
				List<TResult<Object>> lst = (List<TResult<Object>>) process.getValue();
				if(lst!=null && lst.size()>0){
					TResult result = lst.get(0);
					if(result.getValue()!=null && result.getValue() instanceof List<?>){
						List<Object> l = (List<Object>) result.getValue();
						for(Object e : l)
							if(e instanceof ITEntity)
								consume.accept((ITEntity) e);
						if(callback!=null)
							callback.accept(true);
					}else
						if(callback!=null)
							callback.accept(false);
				}else
					if(callback!=null) 
						callback.accept(false);
			}
		});
		process.search(sel);
		process.startProcess();
	}

}
