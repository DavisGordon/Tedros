/**
 * 
 */
package org.tedros.core.context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.tedros.core.ITModule;
import org.tedros.core.model.ITModelView;
import org.tedros.server.model.ITModel;

import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public final class TedrosModuleLoader {
	
	static final String MODEL = "m";
	static final String MODELVIEW = "mv";
	static final String MODULE = "mdl";

	private static TedrosModuleLoader instance;
	
	final List<TEntry> entries = new ArrayList<>();
	
	private TedrosModuleLoader() {
	}
	
	public static TedrosModuleLoader getInstance() {
		if(instance==null)
			instance = new TedrosModuleLoader();
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<TLoader> getLoader(ITModel model) {
		List<TLoader> l = new ArrayList<>();
		entries.stream()
		.filter(e->{
			return e.get(MODEL) == model.getClass();
		})
		.forEach(e->{
			l.add(new TLoader((Class) e.get(MODEL), (Class) e.get(MODELVIEW), (Class) e.get(MODULE), model, null, null, null));
		});
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<TLoader> getLoader(List<? extends ITModel> models) {
		if(models == null || models.isEmpty())
			throw new IllegalArgumentException("The models argument cannot be null or empty"); 
		List<TLoader> l = new ArrayList<>();
		models.forEach(m->{
			List<? extends ITModel> group = models.stream()
					.filter(x->x.getClass()==m.getClass())
					.collect(Collectors.toList());
			Class<? extends ITModel> cls = group.get(0).getClass();
			entries.stream()
			.filter(e->e.get(MODEL) == cls && !l.stream().filter(p->p.getModelType()==cls).findFirst().isPresent())
			.forEach(e->l.add(new TLoader((Class) e.get(MODEL), (Class) e.get(MODELVIEW), (Class) e.get(MODULE), null, null, group, null)));
		});
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<TLoader> getLoader(ITModelView model) {
		List<TLoader> l = new ArrayList<>();
		entries.stream()
		.filter(e->{
			return e.get(MODELVIEW) == model.getClass();
		})
		.forEach(e->{
			l.add(new TLoader((Class) e.get(MODEL), (Class) e.get(MODELVIEW), (Class) e.get(MODULE), null, model, null, null));
		});
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<TLoader> getLoader(ObservableList<? extends ITModelView> modelsView) {
		if(modelsView == null || modelsView.isEmpty())
			throw new IllegalArgumentException("The modelsView argument cannot be null or empty"); 
		Class<? extends ITModelView> cls = modelsView.get(0).getClass();
		List<TLoader> l = new ArrayList<>();
		entries.stream()
		.filter(e->{
			return e.get(MODELVIEW) == cls;
		})
		.forEach(e->{
			l.add(new TLoader((Class) e.get(MODEL), (Class) e.get(MODELVIEW), (Class) e.get(MODULE), null, null, null, modelsView));
		});
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<TLoader> getLoaderByModelType(Class<? extends ITModel> model) {
		List<TLoader> l = new ArrayList<>();
		entries.stream()
		.filter(e->{
			return e.get(MODEL) == model;
		})
		.forEach(e->{
			l.add(new TLoader((Class) e.get(MODEL), (Class) e.get(MODELVIEW), (Class) e.get(MODULE), null, null, null, null));
		});
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<TLoader> getLoaderByModelViewType(Class<? extends ITModelView> model) {
		List<TLoader> l = new ArrayList<>();
		entries.stream()
		.filter(e->{
			return e.get(MODELVIEW) == model;
		})
		.forEach(e->{
			l.add(new TLoader((Class) e.get(MODEL), (Class) e.get(MODELVIEW), (Class) e.get(MODULE), null, null, null, null));
		});
		return l;
	}
	

	@SuppressWarnings("unchecked")
	public List<TLoader> getLoaderByModuleType(Class<? extends ITModule> module) {
		List<TLoader> l = new ArrayList<>();
		entries.stream()
		.filter(e->{
			return e.get(MODULE) == module;
		})
		.forEach(e->{
			l.add(new TLoader((Class) e.get(MODEL), (Class) e.get(MODELVIEW), (Class) e.get(MODULE), null, null, null, null));
		});
		return l;
	}
	
	

}
