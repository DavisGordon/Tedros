/**
 * 
 */
package com.tedros.fxapi.presenter.behavior;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.control.action.TPresenterAction;

/**
 * @author Davis Gordon
 *
 */
public final class TActionHelper {

	private List<TPresenterAction> actions;
	
	public TActionHelper() {
		actions = new ArrayList<>();
	}
	
	public List<TPresenterAction> getAction(TActionType... types){
		if(types==null)
			return actions;
		List<TPresenterAction> l = new ArrayList<>();
		for(TPresenterAction a : actions) {
			if(a.getTypes()!=null) {
				for(TActionType t : types)
					if(ArrayUtils.contains(a.getTypes(), t)) {
						l.add(a);
						break;
					}
			}	
		}
		return l;
	}
	
	public void add(TPresenterAction action) {
		this.actions.add(action);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void add(ITPresenter presenter, Class<? extends TPresenterAction>... action) {
		try {
			List<TPresenterAction> l = build(presenter, action);
			if(l==null)
				return;
			actions.addAll(l);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TPresenterAction> build(ITPresenter presenter, Class<? extends TPresenterAction>... action) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if(action==null || action.length==1 && action[0]==TPresenterAction.class)
			return null;
		List<TPresenterAction> l = new ArrayList<>();
		for(Class<? extends TPresenterAction> c : action)
			l.add(build(c, presenter));
		return l;
	}
	
	@SuppressWarnings("rawtypes")
	private static TPresenterAction build(Class<? extends TPresenterAction> action, ITPresenter presenter) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		TPresenterAction p =  action.newInstance();
		p.setPresenter(presenter);
		return p;
	}
	
	
	public boolean runBefore(TActionType... types) {
		List<TPresenterAction> actions = this.getAction(types);
		if(actions==null || actions.isEmpty())
			return true;
		
		actions.sort(null);
		for(TPresenterAction a : actions)
			if(!a.runBefore())
				return false;
		return true;
	}
	
	
	public void runAfter(TActionType... types) {
		List<TPresenterAction> actions = this.getAction(types);
		if(actions==null || actions.isEmpty())
			return;
		
		actions.sort(null);
		for(TPresenterAction a : actions)
			a.runAfter();
	}
}
