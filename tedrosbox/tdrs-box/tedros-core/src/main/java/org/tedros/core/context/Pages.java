package org.tedros.core.context;

import org.tedros.core.TCoreKeys;
import org.tedros.core.TLanguage;


public class Pages{
	
    private AllPagesPage root;
    private CategoryPage modules;
    	
	public Pages(){
        root = new AllPagesPage();
        modules = new CategoryPage(TLanguage.getInstance(null).getString(TCoreKeys.MENU_ROOT), new Page[0]);
        root.getChildren().add(modules);
        
    }
    	
	public void parseModules(){
		InternalViewHelper.loadApplication(modules);
    }	

    public Page getPage(String name){
        Page page = root.getChild(name);
        return page;
    }
    
    public Page getModules(){
        return modules;
    }

    
    public Page getRoot(){
        return root;
    }   
}
