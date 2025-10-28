package org.tedros.core.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tedros.util.TLoggerUtil;

/**
 * InternalViewHelper
 *
 */
class InternalViewHelper {
	
	public static void loadApplication(CategoryPage rootPage) {    
    	findAllApplications(rootPage);
    }
    
    private static CategoryPage getCategoryPageForPath(String path, CategoryPage dirPage, Map<String,CategoryPage> categoryPageMap) {
        CategoryPage categoryPage = categoryPageMap.get(path);
        if (categoryPage == null) {
            int lastSlash = path.lastIndexOf('/');
            if (lastSlash == -1) {
                // found root level category so create
                categoryPage = new CategoryPage(path);
                dirPage.getChildren().add(categoryPage);
            } else {
                // get parent
                CategoryPage parentCategoryPage = getCategoryPageForPath(path.substring(0,lastSlash),dirPage, categoryPageMap);
                // create new sub-category
                categoryPage = new CategoryPage(path.substring(lastSlash+1,path.length()));
                parentCategoryPage.getChildren().add(categoryPage);
            }
            categoryPageMap.put(path,categoryPage);
        }
        return categoryPage;
    }

    private static void findAllApplications(CategoryPage dirPage) {
    	
    	List<TModuleContext> contexts = TedrosAppManager.getInstance().getModuleContexts();
    	Collections.sort(contexts);
    	
        Map<String, CategoryPage> categoryPageMap = new HashMap<String, CategoryPage>();
        for (TModuleContext  context : contexts) {
        	String oneSampleUrl = context.getAppContext()
        			.getAppDescriptor().getName()+"/"
        			+context.getModuleDescriptor().getMenu()+"/"
        			+context.getModuleDescriptor().getModuleName();
        	try{
	            // create module page
	            InternalViewPage internalViewPage = new InternalViewPage(context);
	            // add to parent category;
	            // don't forget to put "/" at the end
	            String[] pathParts = oneSampleUrl.split("/");
	            if(pathParts.length==1)
	            	continue;
	            String parentPath = context.getAppContext().getAppDescriptor().getName()+"/"+context.getModuleDescriptor().getMenu();
	            CategoryPage parentCategoryPage = getCategoryPageForPath(parentPath, dirPage, categoryPageMap);
	            parentCategoryPage.getChildren().add(internalViewPage);
        	}catch (Exception e) {
        		TLoggerUtil.error(InternalViewHelper.class, e.getMessage(), e);
			}
        }
    }
}
