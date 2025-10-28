package org.tedros.fx.collections;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.tedros.fx.model.TModelView;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;

public class TFXCollections {
	
	public static <E> ITObservableList<E> iTObservableList(){
		final ITObservableList<E> list = getProxy();
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	private static <E> ITObservableList<E> getProxy() {
		
		final ITObservableList<E> list = new TSimpleObservableList<E>();
		
		return (ITObservableList<E>) Proxy.newProxyInstance
          (list.getClass().getClassLoader(),
                new Class[] { ITObservableList.class},
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method,  Object[] args) throws Throwable {
                    	Object obj = null;
                    	final String name = method.getName(); 
                    	if(name.length()==3 && name.equals("get")){
                    		obj = method.invoke(list, args);
                    		if(obj instanceof TModelView) {
                    			ChangeListener<Number> l = (a, o, n) -> {
                    				Platform.runLater(()->{
                    					list.tBuildHashCodeProperty();
                    				});
                    			};
                    			TModelView<?>  mv = (TModelView<?>) obj;
                    			mv.addListener("lastHashCodeProperty", l);
                    			mv.lastHashCodeProperty().addListener(new WeakChangeListener<>(l));
                    		}
                    	}else
                    		obj = method.invoke(list, args);
                    	//list.tBuildHashCodeProperty();
                    	Platform.runLater(()->{
        					list.tBuildHashCodeProperty();
        				});
                    	return obj; 
                    }
                });
    }

}

