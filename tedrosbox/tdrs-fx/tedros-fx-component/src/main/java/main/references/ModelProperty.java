package main.references;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.tedros.core.module.TObjectRepository;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class ModelProperty {
	
	private ObjectProperty<Model> model;

	private IntegerProperty number;
	
	private StringProperty name;
	
	private ObjectProperty<Child> child;
	
	public ModelProperty(Model m) {
		
		this.model = new SimpleObjectProperty<>(m);
		
		number = new SimpleIntegerProperty();
		name = new SimpleStringProperty();
		child = new SimpleObjectProperty<>();
				
		ChangeListener<String> listener1 = buildListener("name");
		name.addListener(listener1);
		
		ChangeListener<String> listener2 = buildListener("name");
		name.addListener(listener2);
		
		KeyFrame kf = new KeyFrame(Duration.millis(3000), 
                new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	name.setValue(UUID.randomUUID().toString());
            }
        });
		
		Timeline tl = TimelineBuilder.create()
			.keyFrames(kf)
	        .cycleCount(Animation.INDEFINITE)
	        .build();
		
		tl.play();
	
	}
	
	private Map<String, Set<String>> keys = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	private String buildKeyForField(String fieldName){
		
		String key = fieldName;
		
		Set<String> list = (Set<String>) (keys.containsKey(key)
				? keys.get(key)
						: buildList(fieldName));
		
		if(list.contains(key))
			key = fieldName + UUID.randomUUID(); 
				
		list.add(key);
		
		return key;
	}

	private Set<String> buildList(String fieldName) {
		Set<String> list = new HashSet<>();
		keys.put(fieldName, list);
		return list;
	}
	
	private TObjectRepository repository = new TObjectRepository();
	
	private <T> ChangeListener<T> buildListener(final String fieldName){
		String key = buildKeyForField(fieldName);
		ChangeListener<T> listener = new ChangeListener<T>() {
			@Override
			public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {
				
				Method m = TReflectionUtil.getSetterMethod(Model.class, fieldName, arg2.getClass());
				try {
					m.invoke(model.getValue(), arg2);
					System.out.println("**> "+ ModelProperty.this.toString() +"  Listener for method "+fieldName+" still alive. New Value = "+arg2);
					
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		};
		
		repository.add(key, listener);
		
		return listener;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeAllListener(){
		for(String fieldName : keys.keySet()){
			for(String key : keys.get(fieldName)){
				 Method m = TReflectionUtil.getGetterMethod(this.getClass(), fieldName);
				 try {
					Property property = (Property) m.invoke(this);
					
					Object obj = repository.remove(key);
					
					if(obj instanceof ChangeListener)
						property.removeListener((ChangeListener) obj);
					else 
						if(obj instanceof InvalidationListener)
							property.removeListener((InvalidationListener) obj);
					else					
						if(obj instanceof ListChangeListener && property instanceof ListProperty)
							((ListProperty)property).removeListener((ListChangeListener) obj);
					else
						if(obj instanceof SetChangeListener && property instanceof SetProperty)
							((SetProperty)property).removeListener((SetChangeListener) obj);
					else
						if(obj instanceof MapChangeListener && property instanceof MapProperty)
							((MapProperty)property).removeListener((MapChangeListener) obj);
					
					
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		keys.clear();
	}
	
	
	public IntegerProperty getNumber() {
		return number;
	}

	public void setNumber(IntegerProperty number) {
		this.number = number;
	}

	public StringProperty getName() {
		return name;
	}

	public void setName(StringProperty name) {
		this.name = name;
	}

	public ObjectProperty<Child> getChild() {
		return child;
	}

	public void setChild(ObjectProperty<Child> child) {
		this.child = child;
	}
	
	
	
}
