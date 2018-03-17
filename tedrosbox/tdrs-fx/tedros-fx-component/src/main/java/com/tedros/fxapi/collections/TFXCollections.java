package com.tedros.fxapi.collections;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.tedros.fxapi.presenter.model.TModelView;

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
		                    		if(obj instanceof TModelView)
		                    			((TModelView<?>)obj).lastHashCodeProperty().addListener(new ChangeListener<Number>() {
		                    				@Override
		                    				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
		                    					list.tBuildHashCodeProperty();
		                    				}
		                    			});
		                    	}else
		                    		obj = method.invoke(list, args);
		                    	list.tBuildHashCodeProperty();
		                    	return obj; 
		                    }
		                });
    }

	/*@Override
	public void start(Stage stage) throws Exception {
		
		ITObservableList<EntidadeEntityView> list =  TFXCollections.iTObservableList();
		
		list.tHashCodeProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				System.out.println("HashCodeProperty: "+arg2);
			}
		});;
		
		
		list.add(new EntidadeEntityView(new Entidade("nome 1", 1)));
		list.add(new EntidadeEntityView(new Entidade("nome 2", 2)));
		list.add(new EntidadeEntityView(new Entidade("nome 3", 3)));
		
		System.out.println("*************************************");
		System.out.println("HashCodeProperty: "+list.hashCode());
		System.out.println("HashCodeProperty: "+list.hashCode());
		System.out.println("*************************************");
		System.out.println("Alterando valor:");
		list.get(0).getNome().set("alterado");
		System.out.println("*************************************");
		System.out.println("HashCodeProperty: "+list.hashCode());
		System.out.println("HashCodeProperty: "+list.hashCode());
		System.out.println("*************************************");
		System.out.println("Alterando valor:");
		list.get(1).getNome().set("alterado");
		list.get(2).getNome().set("alterado");
		list.get(0).getNome().set("alterado");
		System.out.println("*************************************");
		System.out.println("HashCodeProperty: "+list.hashCode());
		System.out.println("HashCodeProperty: "+list.hashCode());
		System.out.println("*************************************");
		System.out.println("Removendo da lista:");
		list.remove(1);
		System.out.println("*************************************");
		
		
		
		ListView<EntidadeEntityView> listView = new ListView<>();
		listView.setItems((ObservableList<EntidadeEntityView>)list);
		listView.setCellFactory(new Callback<ListView<EntidadeEntityView>, ListCell<EntidadeEntityView>>() {
		    public ListCell<EntidadeEntityView> call(ListView<EntidadeEntityView> param) {
		        final ListCell<EntidadeEntityView> cell = new ListCell<EntidadeEntityView>() {
		            
		        	@Override
		            public void updateItem(final EntidadeEntityView item, boolean empty) {
		            	
		        		super.updateItem(item, empty);
		            	
		        		if (item != null) {
		        			textProperty().bind(item.getDisplayProperty());
		                	
		                	if(item.getEntity().isNew())
		                		setStyle("-fx-text-fill: green;");
		                	
		                	item.lastHashCodeProperty().addListener(new ChangeListener<Number>() {
								@Override
								public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
									setStyle("-fx-text-fill: red;");
								}
							});
		                	
		                	item.loadedProperty().addListener(new ChangeListener<Number>() {
								@Override
								public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
									setStyle("-fx-text-fill: black;");
								}
							});
		                }
		            }
		        };
		        return cell;
		    }
		});
		
		
		StackPane pane = new StackPane();
		pane.getChildren().add(listView);
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}*/
	

}

