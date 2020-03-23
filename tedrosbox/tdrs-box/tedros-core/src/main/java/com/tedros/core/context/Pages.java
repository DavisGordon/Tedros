package com.tedros.core.context;

import com.tedros.core.TInternationalizationEngine;


public class Pages{
	
    private AllPagesPage root;
    private CategoryPage modules;
    	
	public Pages(){
        root = new AllPagesPage();
        modules = new CategoryPage(TInternationalizationEngine.getInstance(null).getString("#{tedros.modules}"), new Page[0]);
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
