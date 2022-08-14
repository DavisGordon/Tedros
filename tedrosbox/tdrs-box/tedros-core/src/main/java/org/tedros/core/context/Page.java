package org.tedros.core.context;


import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public abstract class Page extends TreeItem<String> {

    protected Page(String name) {
        super(name);
    }

    public void setName(String name){
        setValue(name);
    }

    public String getName() {
        return getValue();
    }

    public String getPath() {
        if (getParent() == null) {
            return getName();
        } else {
            String parentsPath = ((Page)getParent()).getPath();
            if (parentsPath.equalsIgnoreCase("All")) {
                return getName();
            } else {
                return  parentsPath + "/" + getName();
            }
        }
    }

    public abstract Node createModule();
    
    public abstract Node getModule();

    /** 
     * find a child with a '/' deliminated path 
     * */
    @SuppressWarnings("rawtypes")
	public Page getChild(String path) {
        int firstIndex = path.indexOf('/');
        String childName = (firstIndex==-1) ? path : path.substring(0,firstIndex);
        if (childName.indexOf('#') != -1) {
            String[] parts = childName.split("#");
            childName = parts[0];
        }
        for (TreeItem child : getChildren()) {
            Page page = (Page)child;
            if(page.getName().equals(childName)) {
                if(firstIndex==-1) {
                    return page;
                } else {
                    return page.getChild(path.substring(firstIndex+1));
                }
            }
        }
        return null;
    }

    @Override public String toString() {
        return toString("");
    }

    @SuppressWarnings("rawtypes")
	private String toString(String indent) {
        String out = indent + "["+getName()+"] "+getClass().getSimpleName();
        ObservableList<TreeItem<String>> childModel = getChildren();
        if (childModel!=null) {
            for (TreeItem child:childModel) {
                out += "\n"+((Page)child).toString("    "+indent);
            }
        }
        return out;
    }

    @SuppressWarnings("rawtypes")
	public static class GoToPageEventHandler implements EventHandler {
        private String pagePath;

        public GoToPageEventHandler(String pagePath) {
            this.pagePath = pagePath;
        }

        public void handle(Event event) {
        	TedrosContext.setPagePathProperty(this.pagePath,true, false, true);
        }
    }
}