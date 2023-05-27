/**
 * Tedros Box
 */
package org.tedros.fx.presenter.model;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.core.TLanguage;
import org.tedros.core.model.ITModelView;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.exception.TErrorType;
import org.tedros.fx.exception.TException;
import org.tedros.fx.presenter.model.TFormatter.Item;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.fx.util.TPropertyUtil;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.model.ITFileModel;
import org.tedros.server.model.ITModel;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;


/**
 * <p>
 * This engine can bind all javafx property between a {@link TModelView} and a 
 * {@link ITModel}, listeners are build to listen changes in the properties and 
 * set the new values in the {@link ITModel}.
 * </p>
 * <h3>Sample Usage</h3>
 * <pre>
 * <code>
 * // The model
 * public class ExampleModel implements ITModel {
 * 
 *    private Long id;
 *    ...
 *    private String <b style='color:red'>name</b>;
 *    private BigDecimal <b style='color:green'>value</b>;
 *    private Date <b style='color:blue'>date</b>;
 *    ...		
 *   // getters and setters
 * }
 * 
 * // The TModelView
 * public class ExampleModelView extends TModelView&lt;ExampleModel&gt;{
 *  
 *    private SimpleLongProperty id;
 *    ...
 *    private SimpleStringProperty <b style='color:red'>name</b>;
 *    private SimpleObjectProperty&lt;BigDecimal&gt; <b style='color:green'>value</b>;
 *    private SimpleObjectProperty&lt;Date&gt; <b style='color:blue'>date</b>;
 *    
 *    public ExampleModelView(ExampleModel model) {
 *      <b>super(model);</b>
 *    }
 *    ...
 *    // getters and setters
 *  }
 * </code>
 * @author Davis Gordon
 */
public abstract class TModelView<M extends ITModel> implements ITModelView<M> {
	
	private static final Logger 			LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	protected	final static String 		SET = "set";
	protected	final static String 		GET = "get";
	
	protected 	M 							model;
	
	private 	int 						lastHashCode;
	private 	SimpleIntegerProperty		lastHashCodeProperty;
	private 	SimpleLongProperty 			loadedProperty;
	private 	boolean 					changed = false;
	
	private 	TListenerHelper<M> 			tListenerHelper;
	
	private 	Map<String, Observable> 	properties = new HashMap<>();
	
	private 	String						modelViewId;
	
	private 	SimpleStringProperty		display;
	
	/**
	 * <pre>
	 * Bind the properties of this model view with the fields of the given model. 
	 * </pre>
	 * */
	public TModelView(final M model) {
		
		LOGGER.setLevel(Level.FINEST);
		LOGGER.log(Level.FINEST, "Begin wrap the model " + model.getClass().getSimpleName() + " by the "+this.getClass().getSimpleName());
		
		this.display = new SimpleStringProperty();
		this.modelViewId = UUID.randomUUID().toString(); 
		this.model = model;
		if(this.model==null)
			return;
		
		tListenerHelper = new TListenerHelper<>(this);
		
		loadFields();
		
		buildLastHashCode();
		buildLastHashCodeListener();
	}
	
	public String getModelViewId() {
		return modelViewId;
	}

	public void setModelViewId(String modelViewId) {
		this.modelViewId = modelViewId;
	}

	/**
	 * <pre>
	 * Get the value to display in components.
	 * </pre>
	 * */
	@Transient
	public SimpleStringProperty toStringProperty() {
		return display;
	}

	/**
	 * <pre>
	 * Format the toString value
	 * </pre>
	 * @see java.util.Formatter
	 * */
	@SuppressWarnings("unchecked")
	public void formatToString(String format, ObservableValue<?>... fields) {
		
		setDisplayValue(format, fields);
		
		int i = 0;
		for(ObservableValue<?> ob : fields) {
			String lid = "tDisplayFieldIdx_"+(i++);
			ChangeListener<Object> chl = (ChangeListener<Object>) getListenerRepository().get(lid);
			if(chl==null) {
				chl = (a,o,n) -> {
					setDisplayValue(format, fields);
				};
				addListener(lid, chl);
			}else
				ob.removeListener(chl);
			
			ob.addListener(chl);
		}
		
	}
	

	@SuppressWarnings("unchecked")
	public void formatToString(TFormatter formatter) {
		int x = 0;
		for(Item i : formatter.items) {
			String lid = "tDisplayFieldIdx_"+(x++);
			ChangeListener<Object> chl = (ChangeListener<Object>) getListenerRepository().get(lid);
			if(chl==null) {
				chl = (a,o,n) -> {
					toStringProperty().setValue(TLanguage.getInstance().getString(formatter.toString()));
				};
				addListener(lid, chl);
			}else
				i.ob.removeListener(chl);
			
			i.ob.addListener(chl);
		}
		toStringProperty().setValue(TLanguage.getInstance().getString(formatter.toString()));
	}
	

	/**
	 * @param format
	 * @param fields
	 */
	private void setDisplayValue(String format, ObservableValue<?>... fields) {
		Object[] arr = new Object[] {};
		for(ObservableValue<?> f : fields) {
			Object o = f.getValue();
			if(o instanceof Date) {
				Calendar cal = Calendar.getInstance();
				cal.setTime((Date) o);
				arr = ArrayUtils.add(arr, cal);
			}else
				arr = ArrayUtils.add(arr, o!=null ? o : "");
		}
		toStringProperty().setValue(TLanguage.getInstance().getString(String.format(format, arr)));
	}
	
	/**
	 * <pre>
	 * Get the model.
	 * </pre>
	 * */
	@Transient
	public M getModel(){
		return this.model;
	}
	
	/**
	 * <pre>
	 * Set a new model and reload the values in the model view properties. 
	 * </pre>
	 * */
	public void reload(M model) {
		
		LOGGER.log(Level.FINEST, "Reloading model " + model.getClass().getSimpleName() + " by the "+this.getClass().getSimpleName());
		this.model = model;
		loadFields();
		buildLastHashCode();
		loadedProperty().setValue(Calendar.getInstance().getTimeInMillis());
		buildLastHashCodeListener();
		changed = false;
	}

	private void buildLastHashCodeListener() {
		ChangeListener<Number> listener =  getListenerRepository().get("lastHashCodeProperty");
		if(listener==null){
			listener = new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0,
						Number arg1, Number arg2) {
					changed = true;
				}
			};
			addListener("lastHashCodeProperty", listener);
		}else
			lastHashCodeProperty.removeListener(listener);
		
		lastHashCodeProperty.addListener(listener);
	}
	
	@Override
	public int hashCode() {
		return reflectionHashCode(new String[]{});
	}
	/**
	 * <pre>
	 * To be called by hashCode methods. 
	 * The hashCode are built over the ITModel object instead this TModelView object
	 * the reason is because we need to know when the model is changed and we do this
	 * observing the hashcode value. 
	 * 
	 * 
	 *	Example:
	 *	public class ExampleModelView extends TModelView&lt;ExampleModel&gt;{
	 *		...
	 *		<i>@</i>Override
	 *		public int hashCode() {
	 *			return reflectionHashCode("someFieldToBeExcluded");
	 *		}
	 *	}
	 * 
	 * </pre>
	 * @param excludeFields
	 * */
	@Transient
	public int reflectionHashCode(String... excludeFields){
		if(excludeFields!=null && excludeFields.length>0){
			return HashCodeBuilder.reflectionHashCode(model, excludeFields);
		}else
			return HashCodeBuilder.reflectionHashCode(model);
	}
	  
	/**
	 * <pre>
	 * Return the last hashCode to check changes 
	 * </pre>
	 * */
	@Transient
	public int getLastHashCode() {
		return lastHashCode;
	}
	/**
	 * <pre>
	 * Return true if the model are changed.
	 * </pre>
	 * */
	@Transient
	public boolean isChanged(){
		return changed;
	}
	
	/**
	 * <pre>
	 * The property for the lastHashCode value.
	 * </pre>
	 * */
	@Transient
	public SimpleIntegerProperty lastHashCodeProperty() {
		if(lastHashCodeProperty==null)
			lastHashCodeProperty = new SimpleIntegerProperty();
		return lastHashCodeProperty;
	}
	
	/**
	 * <pre>
	 * A property to check when the model view are reloaded.
	 * </pre>
	 * */
	@Transient
	public SimpleLongProperty loadedProperty() {
		if(loadedProperty==null)
			loadedProperty = new SimpleLongProperty();
		return loadedProperty;
	}	
	
	/**
	 * <pre>
	 * Return the value of the getDisplayProperty method.
	 * </pre>
	 * */
	@Override
	public String toString() {
		return toStringProperty().getValue()!=null ? toStringProperty().getValue() : "";	
	}
	
	/**
	 * <pre>
	 * Perform a equals in getId and getDisplayProperty;
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof TModelView))
			return false;
		
		TModelView p = (TModelView) obj;
		
		if(getModelViewId()!=null && p.getModelViewId()!=null){
			return getModelViewId().equals(p.getModelViewId());
		}	
		
		return false;
	}
	
	/**
	 * Return the {@link ObservableValue} created to the field name. 
	 * */
	@SuppressWarnings("unchecked")
	public <T extends Observable> T getProperty(String fieldName) {
		return (T) properties.get(fieldName);
	}
	
	/**
	 * Register a property
	 * */
	protected void registerProperty(String fieldName, Observable value){
		properties.put(fieldName, value);
	}
	

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void loadFields() {
		
		LOGGER.log(Level.FINEST, "Starting load fields.");
		
		final Map<String, Field> modelFields = new HashMap<>();
		Class superClass = this.model.getClass();
		
		Class<?> target = (TReflectionUtil.isImplemented(superClass, ITEntity.class)) 
				? ITEntity.class 
						: (TReflectionUtil.isImplemented(superClass, ITModel.class)) 
						? ITModel.class
								: null;
		
		if(target != null){
			while(TReflectionUtil.isImplemented(superClass, target)){
				for(Field f : superClass.getDeclaredFields())
					modelFields.put(f.getName(), f);
				superClass = superClass.getSuperclass();
			}
		}
		
		Map<String, TPropertyHelper> propertyMap = new HashMap<>();
		target = ITModelView.class;
		superClass = this.getClass();
		
		while(TReflectionUtil.isImplemented(superClass, target)){
			if(!superClass.equals(TModelView.class)) {
				Arrays.asList(superClass.getDeclaredFields()).stream()
				.forEach(f->{
					if(!propertyMap.containsKey(f.getName()))
						try {
							propertyMap.put(f.getName(), new TPropertyHelper(f, this));
						} catch (Throwable e) {
							e.printStackTrace();
						}
				});
			}
			superClass = superClass.getSuperclass();
		}
		
		properties.clear();
		this.registerProperty("toStringProperty", display);
		// percorre os campos do model view 
		propertyMap.values()
		//.parallelStream()
		.forEach(h -> {
			if(h.isElegible()){
				try{
					//Used to stop at a specific field load at debug time
					/* int x = 0;
					if(h.name.contains("id"))
						x = 1;
					*/
					// recupera o campo equivalente no model
					Field entityField = modelFields.get(h.name);
					
					if(entityField!=null) {
						
						final Class entityFieldType = entityField.getType();
						// recupera o metodo set do atributo na entidade
						final Method entidadeSetMethod = TReflectionUtil.getSetterMethod(this.model.getClass(), h.name, entityFieldType);
						//final Method entidadeSetMethod = this.model.getClass().getMethod(SET+StringUtils.capitalize(h.name), entidadeFieldType);;
						
						// recupera o valor do campo na entidade
						final Method entityGetMethod = TReflectionUtil.getGetterMethod(this.model.getClass(), h.name);
						//final Method entidadeGetMethod = this.model.getClass().getMethod(GET+StringUtils.capitalize(h.name));
						// recupera o objeto da entidade
						final Object obj = entityGetMethod.invoke(this.model);
						
						if(TPropertyUtil.isCollectionObservableType(h.type)){
							
							Class modelViewClass = null; 
							Class entityClass = null;
							if(h.genericType!=null){
								modelViewClass = h.genericType.modelViewClass();
								entityClass = h.genericType.modelClass();
							}
							
							// ObservableList.class
							if(TReflectionUtil.isImplemented(h.type, ObservableList.class)){ // OK
								
								final ObservableList property = (ObservableList) h.value; 
								
								final ListChangeListener listener = (ListChangeListener) tListenerHelper.tObjectRepository.get(h.name); 
										
								if(listener!=null){
									property.removeListener(listener);
									property.clear();
								}
								if(obj!=null){
									for(Object o : (Collection) obj){
										//if(o instanceof TModelView){
										if(o instanceof ITModel){
											if(modelViewClass==null && entityClass==null)
												throw new Exception("\n\nT_ERROR "
														+ "\nType: "+TErrorType.COLLECTION_CONFIGURATION
														+ "\nFIELD: "+getClass().getSimpleName() + "."+h.name
														+ "\n\n-Use the @TModelViewType annotation at field "+getClass().getSimpleName() + "."+h.name+"\n\n");
											try{
												if(modelViewClass!=TModelView.class && entityClass!=null)
													property.add(modelViewClass.getConstructor(entityClass).newInstance(o));
												else
													property.add(o);
											}catch(IllegalArgumentException e){
												String error = "\n\nT_ERROR\nType: "+TErrorType.BUILD
														+ "\nModel View class: "+modelViewClass.getName()+".class"
														+ "\nModel View constructor argument type: "+entityClass.getName()+".class"
														+ "\nModel to bind: "+o.getClass().getName()+".class"
														+ "\n\n-Check the configuration of the field "+h.name+" at "+getClass().getName()+".java\n\n";
												
												LOGGER.severe(error);
												LOGGER.severe(e.toString());
												
											}
										}else if(o instanceof ITFileModel){
											property.add(new TSimpleFileProperty<ITFileModel>((ITFileModel)o));
										}
									}
								}
								if(listener==null)
									tListenerHelper.buildListListener(h.type, entityGetMethod, entidadeSetMethod, entityFieldType, property, h.name);
								else
									property.addListener(listener);
								properties.put(h.name, property);
							} 
							
							// ObservableSet.class
							if(h.type == ObservableSet.class){ // TODO: DEVE SER TESTADO
								final ObservableSet property = (ObservableSet) h.value; // modelViewGetMethod.invoke(this);
								
								//TODO: TESTAR LISTENER REPO - BEGIN
								/*final SetChangeListener listener = (changeListenersRepository!=null) 
										? (SetChangeListener)changeListenersRepository.get(entidadeSetMethod.getName())
												:null;*/
								
								final SetChangeListener listener = (SetChangeListener) tListenerHelper.tObjectRepository.get(h.name);
								//END
										
								if(listener!=null){
									property.removeListener(listener);
									property.clear();
								}
								if(obj!=null)
									for(Object o : (Collection) obj)
										if(modelViewClass!=TModelView.class && entityClass!=null)
											property.add(modelViewClass.getConstructor(entityClass).newInstance(o));
										else
											property.add(o);
								if(listener==null)
									tListenerHelper.buildSetListener(h.type, entityGetMethod, entidadeSetMethod, entityFieldType, property, h.name);
								else
									property.addListener(listener);
								properties.put(h.name, property);
							}
							
							// ObservableMap.class
							if(h.type == ObservableMap.class ){ // TODO: DEVE SER TESTADO
								final ObservableMap property = (ObservableMap) h.value; //modelViewGetMethod.invoke(this);
								if(!h.buildListener){
									
									//TODO: TESTAR LISTENER REPO - BEGIN
									// final MapChangeListener listener = (MapChangeListener)changeListenersRepository.get(entidadeSetMethod.getName());
									
									final MapChangeListener listener = (MapChangeListener)	tListenerHelper.tObjectRepository.get(h.name);
									//END
									
									if(listener!=null){
										property.removeListener(listener);
										property.clear();
									}
									
									property.addListener(listener);
								}
								property.putAll((Map) obj);
								if(h.buildListener)
									tListenerHelper.buildMapListener(h.type, entityGetMethod, entidadeSetMethod, property, h.name);
								properties.put(h.name, property);
							}
						}else{
						
							final Property property = (Property) h.value; //modelViewGetMethod.invoke(this);
							if(!TCompatibleTypesHelper.isTypesCompatible(h.type, entityFieldType)){
								
								String error = "\n\nTERROR :: FIELD "+h.name+" of type "+h.type.getSimpleName()+" in "+getClass().getSimpleName()
										+" is not compatible for the type "+entityFieldType+" on "+getModel().getClass().getSimpleName()+"\n";
								error += "TINFO :: TModelView "+ h.type.getSimpleName() +" compatible types: ";
								for(Class c : TCompatibleTypesHelper.compatibleTypes.get(property.getClass()))
									error += c.getSimpleName()+", ";
								
								throw new TException(error.substring(0, error.length()-2)+"\n");
							}
							
							// seta o valor do property com o valor da entidade
							if(obj!=null){
								if(obj instanceof String && property instanceof BooleanProperty){
									String v = (String) obj;
									String literal = v.equals("1") || v.toUpperCase().trim().equals("T") ||  v.toUpperCase().trim().equals("TRUE")  
													? "true" : "false";
									property.setValue(new Boolean(literal));
								}else if(obj instanceof ITModel) {
									Class modelViewClass = null; 
									Class entityClass = null;
									
									if(h.genericType!=null){
										modelViewClass = h.genericType.modelViewClass();
										entityClass = h.genericType.modelClass();
									}
									try{
										if(modelViewClass!=TModelView.class && entityClass!=null)
											property.setValue(modelViewClass.getConstructor(entityClass).newInstance(obj));
										else
											property.setValue(obj);
									}catch(IllegalArgumentException e){
										String error = "\n\nT_ERROR\nType: "+TErrorType.BUILD
												+ "\nModel View class: "+modelViewClass.getName()+".class"
												+ "\nModel View constructor argument type: "+entityClass.getName()+".class"
												+ "\nModel to bind: "+obj.getClass().getName()+".class"
												+ "\n\n-Check the configuration of the field "+h.name+" at "+getClass().getName()+".java\n\n";
										
										LOGGER.severe(error);
										LOGGER.severe(e.toString());
										
									}
	
								}else {
									property.setValue(obj);
								}
							}else if(!(property instanceof TSimpleFileProperty))
								property.setValue(null);
							
							// gera os ChangeListener(s)
							if(h.buildListener)
								tListenerHelper.buildListeners(h.type, entityFieldType, property, entidadeSetMethod, obj, h.name);
							
							properties.put(h.name, property);
						}
					
					}
					
				}catch(Throwable e){				
					LOGGER.severe(e.toString());
				}
			}else{
				String types = "";
				for (Class c : TCompatibleTypesHelper.compatibleTypes.keySet()) 
					types += c.getSimpleName()+", ";
				
				LOGGER.log(Level.FINEST, h.name+" field of type "+h.type.getSimpleName()+" in "+getClass().getSimpleName()+" is not compatible for a TModelView!");
				LOGGER.log(Level.FINEST, "TModelView compatible types: "+types.substring(0,types.length()-2));
			}
		});
		
	}


	@Transient
	protected void buildLastHashCode() {
		setLastHashCode(hashCode());
		lastHashCodeProperty().setValue(getLastHashCode());
		this.model.toString();
	}

	@Transient
	protected void setLastHashCode(int lastHashCode) {
		this.lastHashCode = lastHashCode;
	}

	@Override
	public void removeAllListener() {
		tListenerHelper.removeAllListener();
	}

	@Override
	public <T> T removeListener(String listenerId) {
		return tListenerHelper.removeListener(listenerId);
	}

	@Override
	public Map<String, Set<String>> getListenerKeys() {
		return tListenerHelper.getListenerKeys();
	}

	@Override
	public TRepository getListenerRepository() {
		return tListenerHelper.getListenerRepository();
	}
	
	/**
	 * @param fieldName
	 * @param invalidationListener
	 */
	public void addListener(final String fieldName, InvalidationListener invalidationListener) {
		tListenerHelper.addListener(fieldName, invalidationListener);
	}
	
	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, ChangeListener changeListener) {
		tListenerHelper.addListener(fieldName, changeListener);
	}
	
	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, final MapChangeListener changeListener) {
		tListenerHelper.addListener(fieldName, changeListener);
	}
	
	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, final SetChangeListener changeListener) {
		tListenerHelper.addListener(fieldName, changeListener);
	}

	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, final ListChangeListener changeListener) {
		tListenerHelper.addListener(fieldName, changeListener);
	}
		
}
