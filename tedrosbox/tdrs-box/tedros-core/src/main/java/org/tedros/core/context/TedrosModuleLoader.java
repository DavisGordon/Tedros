/**
 * 
 */
package org.tedros.core.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.tedros.core.ITModule;
import org.tedros.core.annotation.TView;
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
	
	//@SuppressWarnings("unchecked")
	private TedrosModuleLoader() {
		/*Collection<TEntry> lst = CollectionUtils.synchronizedCollection(entries);
		TReflections.getInstance().getClassesAnnotatedWith(TView.class)
		.parallelStream()
		.forEach(c -> {
			TView l = c.getAnnotation(TView.class);
			Arrays.asList(l.items())
			.stream()
			.forEach(v->{
				TEntry e = TEntryBuilder.create()
				.addEntry(MODEL, v.model())
				.addEntry(MODELVIEW, v.modelView())
				.addEntry(MODULE, c)
				.build();
				synchronized(lst){
					lst.add(e);
				}
			});
		});*/
		
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
		Class<? extends ITModel> cls = models.get(0).getClass();
		List<TLoader> l = new ArrayList<>();
		entries.stream()
		.filter(e->{
			return e.get(MODEL) == cls;
		})
		.forEach(e->{
			l.add(new TLoader((Class) e.get(MODEL), (Class) e.get(MODELVIEW), (Class) e.get(MODULE), null, null, models, null));
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
