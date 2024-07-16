/**
 * 
 */
package org.tedros.core.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.ITModule;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The module context.
 * 
 * @author Davis Gordon
 */
public final class TModuleContext implements Comparable<TModuleContext>{
	
	private TAppContext appContext;
	private ITModule module;
	private List<TViewContext> viewContexts;
	private TEntry<Object> tEntry;
	private final TModuleDescriptor descriptor;
	
	private TViewContext currentViewContext;
	
	public TModuleContext(TAppContext appContext, TModuleDescriptor descriptor){
		this.appContext = appContext;
		this.tEntry = new TEntry<>();
		this.descriptor = descriptor;
		this.viewContexts =  new ArrayList<>();
	}
	
	public TModuleDescriptor getModuleDescriptor() {
		return descriptor;
	}
	
	public void buildModule() throws Exception{
		module = descriptor.getType().getDeclaredConstructor().newInstance();
	}
	
	public ITModule getModule(){
		return module;
	}
	
	@SuppressWarnings("rawtypes")
	public void addOpenedView(ITView view){
		final TViewContext ctx = new TViewContext(this, view);
		this.currentViewContext = ctx;
		this.viewContexts.add(ctx);
	}
	
	public boolean stopModule(){
		for (TViewContext tViewContext : viewContexts) {
			if(!tViewContext.stop())
				return false;
		}
		viewContexts.clear();
		currentViewContext = null;
		module = null;
		return true;
	}
	
	public void starModule(){
		getModule().tStart();
	}
	
	public Object getEntry(String key){
		return (StringUtils.isNotBlank(key) && tEntry!=null) 
				? tEntry.getEntry(key)
						: null;
	}
	
	public Object setEntry(String key, Object value){
		if(StringUtils.isNotBlank(key) && value!=null && tEntry!=null) 
			tEntry.addEntry(key, value);
		return null;
	}
	

	public ImageView getIcon() {
		String path = getModuleDescriptor().getIcon();
    	return getIconImageView(path);
	}
	
	public ImageView getMenuIcon() {
		String path = getModuleDescriptor().getMenuIcon();
    	return getIconImageView(path);
	}

	/**
	 * @param path
	 * @return
	 */
	private ImageView getIconImageView(String path) {
		ImageView icon = null;
		if(path!=null){
    		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(path))
    		{
    			icon =  new ImageView(new Image(is));
    		} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return icon;
	}
	
	
	@Override
	public int compareTo(TModuleContext o) {
		String thisStr = descriptor.getApplicationName() + " " + descriptor.getMenu() + " " + descriptor.getModuleName();
		String objStr = o.getModuleDescriptor().getApplicationName() + " " + o.getModuleDescriptor().getMenu() + " " + o.getModuleDescriptor().getModuleName();
		return thisStr.compareTo(objStr);
	}

	public TAppContext getAppContext() {
		return appContext;
	}

	public TViewContext getCurrentViewContext() {
		return currentViewContext;
	}

	public String canStop() {
		for (TViewContext tViewContext : viewContexts) {
			String msg = tViewContext.canStop();
			if(msg!=null)
				return msg;
		}
		return null;
	}

}
