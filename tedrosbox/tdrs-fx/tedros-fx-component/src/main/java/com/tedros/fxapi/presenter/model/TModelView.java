/**
 * Tedros Box
 */
package com.tedros.fxapi.presenter.model;

import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.core.model.ITModelView;
import com.tedros.core.module.TObjectRepository;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.ejb.base.model.ITFileModel;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.chart.TAreaChartField;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.collections.TFXCollections;
import com.tedros.fxapi.exception.TErrorType;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.property.TSimpleFileProperty;
import com.tedros.fxapi.util.TPropertyUtil;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;


/**
 * <p>
 * This engine can bind all javafx property between a {@link TModelView} and a 
 * {@link ITModel}, listeners are build to listen changes in the propertys and 
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
	private 	TCompatibleTypesHelper<M>	tCompatibleTypesHelper;
	
	private Map<String, Observable> propertys = new HashMap<>();
	
	private String modelViewId;
	
	/**
	 * <pre>
	 * Bind the propertys of this model view with the fields of the given model. 
	 * </pre>
	 * */
	public TModelView(final M model) {
		
		LOGGER.setLevel(Level.FINEST);
		
		LOGGER.log(Level.FINEST, "Begin wrap the model " + model.getClass().getSimpleName() + " by the "+this.getClass().getSimpleName());
		this.modelViewId = UUID.randomUUID().toString(); 
		this.model = model;
		if(this.model==null)
			return;
		
		tListenerHelper = new TListenerHelper<>(this);
		tCompatibleTypesHelper = new TCompatibleTypesHelper<>(this);
		
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
	 * Set the id value
	 * </pre>
	 * */
	public abstract void setId(SimpleLongProperty id);
	
	/**
	 * <pre>
	 * Get the id value
	 * </pre>
	 * */
	public abstract SimpleLongProperty getId();
	
	/**
	 * <pre>
	 * Get the value to display in components.
	 * </pre>
	 * */
	@Transient
	public abstract SimpleStringProperty getDisplayProperty();


	/**
	 * <pre>
	 * Show the fields values as the format param 
	 * </pre>
	 * */
	@SuppressWarnings("unchecked")
	protected void formatFieldsToDisplay(String format, ObservableValue<?>... fields) {
		
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

	/**
	 * @param format
	 * @param fields
	 */
	private void setDisplayValue(String format, ObservableValue<?>... fields) {
		String[] arr = new String[] {};
		for(ObservableValue<?> f : fields) {
			arr = ArrayUtils.add(arr, f.getValue()!=null ? f.getValue().toString() : "");
		}
		this.getDisplayProperty().setValue(String.format(format, arr));
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
	 * Return a {@link List} of {@link TDetailInfo}, parsed by {@link TDetailView} annotation
	 * in the {@link TModelView} instance and used by filters views like {@link TEntityFilterView} 
	 * to build detail views in collections of models.   
	 * 
	 * 	Example:
	 * 
	 *	public class ExampleModelView extends TModelView&lt;ExampleModel&gt;{
	 *		...
	 *		<b><i>@</i>TDetailView</b>(formTitle="Documents", listTitle = "Document",
	 *				propertyType=ITObservableList.class, entityClass=Document.class, 
	 *				entityModelViewClass=DocumentModelView.class)
	 *		<i>@</i>TModelViewType(entityClass=Document.class, modelViewClass=DocumentModelView.class)
	 *		private ITObservableList&lt;DocumentModelView&gt; documents;
	 * 		
	 * 	
	 * 		public ExampleModelView(ExampleModel model) {
	 *			<b>super(model);</b>
	 *		}
	 *		...
	 *		// getters and setters
	 * 	}
	 * 
	 * 
	 * </pre>
	 * *
	@Transient
	public List<TDetailInfo> getDetailInfoList() throws Exception {
		List<TDetailInfo> list = null;
		for(final TFieldDescriptor f : TReflectionUtil.getTDetailFormAnnotatedFields(this)){
			final TDetailInfo info = buildDetailInfo(f);
			if(info!=null){
				if(list==null) list = new ArrayList<>();
				list.add(info);
			}
		}
			
		return list;
	}*/
	
	/**
	 * <pre>
	 * Set a new model and reload the values in the model view propertys. 
	 * </pre>
	 * */
	public void reload(M model) {
		
		LOGGER.log(Level.FINEST, "Reloading model " + model.getClass().getSimpleName() + " by the "+this.getClass().getSimpleName());
		//this.removeAllListener();
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
		return (getDisplayProperty()!=null)? getDisplayProperty().getValue() : "";	
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
	@SuppressWarnings("rawtypes")
	public Observable getProperty(String fieldName) {
		return  propertys.get(fieldName);
	}
	
	/**
	 * Register a property
	 * */
	protected void registerProperty(String fieldName, Observable value){
		propertys.put(fieldName, value);
	}
	

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void loadFields() {
		
		LOGGER.log(Level.FINEST, "Starting load fields.");
		
		final List<Field> modelFields = new ArrayList<>();
		final Field[] propertyFields = this.getClass().getDeclaredFields();
		Class superClass = this.model.getClass();
		
		Class<?> target = (TReflectionUtil.isImplemented(superClass, ITEntity.class)) 
				? ITEntity.class 
						: (TReflectionUtil.isImplemented(superClass, ITModel.class)) 
						? ITModel.class
								: null;
		
		if(target != null){
			while(TReflectionUtil.isImplemented(superClass, target)){
				for(Field f : superClass.getDeclaredFields())
					modelFields.add(f);
				superClass = superClass.getSuperclass();
			}
		}
		propertys.clear();
		// percorre os campos do model view 
		for (final Field field : propertyFields) {
			
			//LOGGER.log(Level.FINEST, field.getName()+" field found.");
			
			final String propertyFieldName = field.getName();
			final Class propertyFieldType = field.getType();
			//verifica se o tipo � compativel para bind
			if(tCompatibleTypesHelper.isCompatible(propertyFieldType)){
				try{
					
					// recupera o metodo get do property
					final Method modelViewGetMethod = this.getClass().getMethod(GET+StringUtils.capitalize(propertyFieldName));
					// recupera o metodo set do property
					final Method propertySetMethod = this.getClass().getMethod(SET+StringUtils.capitalize(propertyFieldName), propertyFieldType);
					Object propertyObj = modelViewGetMethod.invoke(this);
					
					boolean buildListener = false;
					if(propertyObj==null){
						buildListener = true;
						if(TReflectionUtil.isImplemented(propertyFieldType, ITObservableList.class)){
							propertyObj = (ITObservableList) TFXCollections.iTObservableList();
							propertySetMethod.invoke(this, propertyObj);
						}else if(TReflectionUtil.isImplemented(propertyFieldType, ObservableList.class)){
							propertyObj = (ObservableList) FXCollections.observableArrayList();
							propertySetMethod.invoke(this, propertyObj);
						}else if(propertyFieldType == ObservableSet.class){
							propertyObj = (ObservableSet)FXCollections.observableSet((Set)new HashSet());
							propertySetMethod.invoke(propertyObj);
						}else if(propertyFieldType == ObservableMap.class ){
							propertyObj = (ObservableMap)FXCollections.observableMap((Map)new HashMap());
							propertySetMethod.invoke(propertyObj);
						}else if(propertyFieldType == TSimpleFileProperty.class) {
							Class entityClass = null;
							
							for(Annotation annotation : field.getDeclaredAnnotations())
								if(annotation instanceof TModelViewType ){
									TModelViewType tAnnotation = (TModelViewType) annotation;
									entityClass = tAnnotation.modelClass();
								}
							if(entityClass!=null) {
								propertyObj = propertyFieldType.getConstructor(ITFileBaseModel.class).newInstance(entityClass.newInstance());
								propertySetMethod.invoke(this, propertyObj);
							}else {
								propertyObj = propertyFieldType.newInstance();
								propertySetMethod.invoke(this, propertyObj);
							}
							
						}else{
							propertyObj = propertyFieldType.newInstance();
							propertySetMethod.invoke(this, propertyObj);
						}
					}
					
					/* //Used to stop at a specific field load at debug time
					 int x = 0;
					if(propertyFieldName.contains("rootProjectFolder"))
						x = 1;
					*/
					
					// recupera o campo equivalente no model
					Field entityField = null;
					for (final Field f : modelFields) {
						if(f.getName().equals(propertyFieldName)){
							entityField = f;
							break;
						}
					}
					if(entityField==null)
						continue;
					
					final Class entityFieldType = entityField.getType();
					// recupera o metodo set do atributo na entidade
					final Method entidadeSetMethod = TReflectionUtil.getSetterMethod(this.model.getClass(), propertyFieldName, entityFieldType);
					//final Method entidadeSetMethod = this.model.getClass().getMethod(SET+StringUtils.capitalize(propertyFieldName), entidadeFieldType);;
					
					// recupera o valor do campo na entidade
					final Method entityGetMethod = TReflectionUtil.getGetterMethod(this.model.getClass(), propertyFieldName);
					//final Method entidadeGetMethod = this.model.getClass().getMethod(GET+StringUtils.capitalize(propertyFieldName));
					// recupera o objeto da entidade
					final Object obj = entityGetMethod.invoke(this.model);
					
					if(TPropertyUtil.isCollectionObservableType(propertyFieldType)){
						
						Class modelViewClass = null; 
						Class entityClass = null;
						
						for(Annotation annotation : field.getDeclaredAnnotations())
							if(annotation instanceof TModelViewType ){
								TModelViewType tAnnotation = (TModelViewType) annotation;
								modelViewClass = tAnnotation.modelViewClass();
								entityClass = tAnnotation.modelClass();
							}
						
						// ObservableList.class
						if(TReflectionUtil.isImplemented(propertyFieldType, ObservableList.class)){ // OK
							
							final ObservableList property = (ObservableList) propertyObj; 
							
							final ListChangeListener listener = (ListChangeListener) tListenerHelper.tObjectRepository.get(propertyFieldName); 
									
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
													+ "\nFIELD: "+getClass().getSimpleName() + "."+propertyFieldName
													+ "\n\n-Use the @TModelViewType annotation at field "+getClass().getSimpleName() + "."+propertyFieldName+"\n\n");
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
													+ "\n\n-Check the configuration of the field "+propertyFieldName+" at "+getClass().getName()+".java\n\n";
											
											LOGGER.severe(error);
											LOGGER.severe(e.toString());
											
										}
									}else if(o instanceof ITFileModel){
										property.add(new TSimpleFileProperty<ITFileModel>((ITFileModel)o));
									}
								}
							}
							if(listener==null)
								tListenerHelper.buildListListener(propertyFieldType, entityGetMethod, entidadeSetMethod, entityFieldType, property, propertyFieldName);
							else
								property.addListener(listener);
							propertys.put(propertyFieldName, property);
						} 
						
						// ObservableSet.class
						if(propertyFieldType == ObservableSet.class){ // TODO: DEVE SER TESTADO
							final ObservableSet property = (ObservableSet) propertyObj; // modelViewGetMethod.invoke(this);
							
							//TODO: TESTAR LISTENER REPO - BEGIN
							/*final SetChangeListener listener = (changeListenersRepository!=null) 
									? (SetChangeListener)changeListenersRepository.get(entidadeSetMethod.getName())
											:null;*/
							
							final SetChangeListener listener = (SetChangeListener) tListenerHelper.tObjectRepository.get(propertyFieldName);
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
								tListenerHelper.buildSetListener(propertyFieldType, entityGetMethod, entidadeSetMethod, entityFieldType, property, propertyFieldName);
							else
								property.addListener(listener);
							propertys.put(propertyFieldName, property);
						}
						
						// ObservableMap.class
						if(propertyFieldType == ObservableMap.class ){ // TODO: DEVE SER TESTADO
							final ObservableMap property = (ObservableMap) propertyObj; //modelViewGetMethod.invoke(this);
							if(!buildListener){
								
								//TODO: TESTAR LISTENER REPO - BEGIN
								// final MapChangeListener listener = (MapChangeListener)changeListenersRepository.get(entidadeSetMethod.getName());
								
								final MapChangeListener listener = (MapChangeListener)	tListenerHelper.tObjectRepository.get(propertyFieldName);
								//END
								
								if(listener!=null){
									property.removeListener(listener);
									property.clear();
								}
								
								property.addListener(listener);
							}
							property.putAll((Map) obj);
							if(buildListener)
								tListenerHelper.buildMapListener(propertyFieldType, entityGetMethod, entidadeSetMethod, property, propertyFieldName);
							propertys.put(propertyFieldName, property);
						}
					}else{
					
						final Property property = (Property) propertyObj; //modelViewGetMethod.invoke(this);
						if(!tCompatibleTypesHelper.isTypesCompatible(propertyFieldType, entityFieldType)){
							
							String error = "\n\nTERROR :: FIELD "+propertyFieldName+" of type "+propertyFieldType.getSimpleName()+" in "+getClass().getSimpleName()
									+" is not compatible for the type "+entityFieldType+" on "+getModel().getClass().getSimpleName()+"\n";
							error += "TINFO :: TModelView "+ propertyFieldType.getSimpleName() +" compatible types: ";
							for(Class c : tCompatibleTypesHelper.compatibleTypes.get(property.getClass()))
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
								
								for(Annotation annotation : field.getDeclaredAnnotations())
									if(annotation instanceof TModelViewType ){
										TModelViewType tAnnotation = (TModelViewType) annotation;
										modelViewClass = tAnnotation.modelViewClass();
										entityClass = tAnnotation.modelClass();
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
											+ "\n\n-Check the configuration of the field "+propertyFieldName+" at "+getClass().getName()+".java\n\n";
									
									LOGGER.severe(error);
									LOGGER.severe(e.toString());
									
								}

							}else {
								property.setValue(obj);
							}
						}else if(!(property instanceof TSimpleFileProperty))
							property.setValue(null);
						
						// gera os ChangeListener(s)
						if(buildListener)
							tListenerHelper.buildListeners(propertyFieldType, entityFieldType, property, entidadeSetMethod, obj, propertyFieldName);
						
						propertys.put(propertyFieldName, property);
					}
					
				}catch(Throwable e){				
					LOGGER.severe(e.toString());
				}
			}else{
				
				if(propertyFieldType == TAreaChartField.class)
					continue;
				
				String types = "";
				for (Class c : tCompatibleTypesHelper.compatibleTypes.keySet()) 
					types += c.getSimpleName()+", ";
				
				LOGGER.log(Level.FINEST, propertyFieldName+" field of type "+propertyFieldType.getSimpleName()+" in "+getClass().getSimpleName()+" is not compatible for a TModelView!");
				LOGGER.log(Level.FINEST, "TModelView compatible types: "+types.substring(0,types.length()-2));
				
			}
		}
		
		for(final Field entidadeField : this.model.getClass().getSuperclass().getDeclaredFields()){
			if(entidadeField.getName().equals("id")){
				try{
					final Class entidadeFieldType = entidadeField.getType();
					// recupera o metodo set do atributo na entidade
					final Method entidadeSetMethod = this.model.getClass().getMethod("setId", entidadeFieldType);
					// recupera o valor do campo na entidade
					final Method entidadeGetMethod = this.model.getClass().getMethod("getId");
					// recupera o objeto da entidade
					final Long id = (Long) entidadeGetMethod.invoke(this.model);
					
					final Method modelViewGetMethod = this.getClass().getMethod("getId");
					
					final Method propertySetMethod = this.getClass().getMethod("setId", SimpleLongProperty.class);
					SimpleLongProperty propertyObj = (SimpleLongProperty) modelViewGetMethod.invoke(this);
					if(propertyObj==null){
						propertyObj = new SimpleLongProperty(id);
						propertySetMethod.invoke(this, propertyObj);
						tListenerHelper.buildListeners(SimpleLongProperty.class, entidadeFieldType, propertyObj, entidadeSetMethod, id, entidadeField.getName() );
					}else{
						propertyObj.setValue(id);
						tListenerHelper.buildListeners(SimpleLongProperty.class, entidadeFieldType, propertyObj, entidadeSetMethod, id, entidadeField.getName());
					}
					
					propertys.put(entidadeField.getName(), propertyObj);
					
				}catch(Exception e){
					LOGGER.severe(e.toString());
				}
				break;
			}
				
		}
	}

	@SuppressWarnings("rawtypes")
	protected boolean isClassAModel(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITModel.class);
	}
	
	@SuppressWarnings("rawtypes")
	protected boolean isClassAnEntity(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITEntity.class);
	}
	
	@SuppressWarnings("rawtypes")
	protected boolean isClassAFileBaseModel(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITFileBaseModel.class);
	}

	@SuppressWarnings("rawtypes")
	protected boolean isClassAFileModel(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITFileModel.class);
	}

	@SuppressWarnings("rawtypes")
	protected boolean isClassAFileEntity(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITFileEntity.class);
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

	@Transient
	protected void setLastHashCodeProperty(SimpleIntegerProperty lastHashCodeProperty) {
		this.lastHashCodeProperty = lastHashCodeProperty;
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
	public TObjectRepository getListenerRepository() {
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
