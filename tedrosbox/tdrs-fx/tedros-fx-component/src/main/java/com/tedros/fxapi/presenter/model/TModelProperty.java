/**
 * Tedros Box
 */
package com.tedros.fxapi.presenter.model;

import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.sun.javafx.scene.control.WeakListChangeListener;
import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.ejb.base.model.ITFileModel;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.form.TDetailView;
import com.tedros.fxapi.chart.TAreaChartField;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.collections.TFXCollections;
import com.tedros.fxapi.collections.TSimpleObservableList;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.exception.TErrorType;
import com.tedros.fxapi.exception.TException;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;
import com.tedros.fxapi.property.TSimpleFileModelProperty;
import com.tedros.fxapi.util.TPropertyUtil;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.collections.WeakMapChangeListener;
import javafx.collections.WeakSetChangeListener;
import javafx.scene.paint.Color;


/**
 * <p>
 * This engine can bind all javafx property between a {@link TModelProperty} and a 
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
public abstract class TModelProperty<M extends ITModel> implements ITModelView<M> {
	
	private ObjectProperty<M> modelProperty;
	private int lastHashCode;
	private SimpleIntegerProperty lastHashCodeProperty;
	private SimpleLongProperty loadedProperty;
	@SuppressWarnings("rawtypes")
	private Map<Class, List<Class>> compatibleTypes;
	private boolean changed = false;
	private Map<String, Object> changeListenersRepository;
	
	/**
	 * <pre>
	 * Bind the propertys of this model view with the fields of the given model. 
	 * </pre>
	 * */
	public TModelProperty(final M model) {
		
		if(model==null)
			throw new IllegalArgumentException("The model cannot be null");
		
		this.modelProperty = new SimpleObjectProperty<>(model);
		
		loadTypesCompatibility();
		loadFields();
		buildLastHashCode();
		lastHashCodeProperty.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {
				changed = true;
			}
		});
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
	 * Get the model.
	 * </pre>
	 * */
	@Transient
	public M getModel(){
		return this.modelProperty.getValue();
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
	 *		<i>@</i>TModelViewCollectionType(entityClass=Document.class, modelViewClass=DocumentModelView.class)
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
		this.modelProperty.set(model);
		loadFields();
		buildLastHashCode();
		loadedProperty().setValue(Calendar.getInstance().getTimeInMillis());
		changed = false;
	}
	
	@Override
	public int hashCode() {
		return reflectionHashCode(this, null);
	}
	/**
	 * <pre>
	 * To be called by hashCode methods.
	 * 
	 *	Example:
	 *	public class ExampleModelView extends TModelView&lt;ExampleModel&gt;{
	 *		...
	 *		<i>@</i>Override
	 *		public int hashCode() {
	 *			return reflectionHashCode(this, null);
	 *		}
	 *	}
	 * 
	 * </pre>
	 * */
	public int reflectionHashCode(Object obj , Collection<String> excludeFields){
		List<String> exclude = Arrays.asList("lastHashCode", "lastHashCodeProperty", "form", "loadedProperty", "compatibleTypes", "detailsObservableList");
		if(excludeFields!=null && excludeFields.size()>0)
			exclude.addAll(excludeFields);
		return HashCodeBuilder.reflectionHashCode(obj, exclude);
	}
	/**
	 * <pre>
	 * Return the last hashCode to check changes 
	 * </pre>
	 * */
	@Transient
	public int lastHashCode() {
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
		
		if(obj == null || !(obj instanceof TModelProperty))
			return false;
		
		TModelProperty p = (TModelProperty) obj;
		
		if(getId()!=null && getId().getValue()!=null &&  p.getId()!=null && p.getId().getValue()!=null){
			if(!(getId().getValue().equals(Long.valueOf(0)) && p.getId().getValue().equals(Long.valueOf(0))))
				return getId().getValue().equals(p.getId().getValue());
		}	
		
		if(getDisplayProperty()!=null && getDisplayProperty().getValue()!=null &&  p.getDisplayProperty()!=null && p.getDisplayProperty().getValue()!=null)
			return getDisplayProperty().getValue().equals(p.getDisplayProperty().getValue());
		
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public ObservableValue getProperty(String fieldName) {
		
		ObservableValue property = null;
		
		Method m;
		try {
			m = this.getClass().getMethod("get"+StringUtils.capitalize(fieldName));
			property = (ObservableValue) m.invoke(this);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}	
		
		return property;
	}
	
	
	/**
	 * <pre>
	 * </pre>
	 * */
	@Transient
	private TDetailInfo buildDetailInfo(final TFieldDescriptor tFieldDescriptor) throws Exception {
		final String fieldName = tFieldDescriptor.getFieldName();
		final Method modelViewGetMethod = getClass().getMethod("get"+StringUtils.capitalize(fieldName));
		for (final Annotation annotation : (List<Annotation>) tFieldDescriptor.getAnnotations()) {
			if (annotation instanceof TDetailView) {
				TDetailView tAnnotation = (TDetailView) annotation; 
				final Observable attrProperty = (Observable) modelViewGetMethod.invoke(this);
				return new TDetailInfo(tAnnotation.formTitle(), tAnnotation.listTitle(), attrProperty, tAnnotation.entityModelViewClass(), tAnnotation.entityClass(), tAnnotation.formClass());
				/*TODO: VALIDAR E ARRANCAR
				if(tAnnotation.propertyType() == ObservableList.class ){
					final ObservableList<?> attrProperty = (ObservableList<?>) modelViewGetMethod.invoke(this);
					return new TDetailInfo(tAnnotation.formTitle(), tAnnotation.listTitle(), attrProperty, tAnnotation.entityModelViewClass(), tAnnotation.entityClass(), tAnnotation.formClass());
				}
				if(tAnnotation.propertyType() == ITObservableList.class ){
					final ITObservableList<?> attrProperty = (ITObservableList<?>) modelViewGetMethod.invoke(this);
					return new TDetailInfo(tAnnotation.formTitle(), tAnnotation.listTitle(), attrProperty, tAnnotation.entityModelViewClass(), tAnnotation.entityClass(), tAnnotation.formClass());
				}
				*/
			}
		}
		return null;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void loadFields() {
		
		M model = modelProperty.get();
		
		final List<Field> modelFields = new ArrayList<>();
		final Field[] propertyFields = this.getClass().getDeclaredFields();
		Class superClass = model.getClass();
		
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
		
		// percorre os campos do model view 
		for (final Field field : propertyFields) {
			
			final String propertyFieldName = field.getName();
			final Class propertyFieldType = field.getType();
			//verifica se o tipo � compativel para bind
			if(isCompatible(propertyFieldType)){
				try{
					
					// recupera o metodo get do property
					final Method modelViewGetMethod = this.getClass().getMethod("get"+StringUtils.capitalize(propertyFieldName));
					// recupera o metodo set do property
					final Method propertySetMethod = this.getClass().getMethod("set"+StringUtils.capitalize(propertyFieldName), propertyFieldType);
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
						}else{
							propertyObj = propertyFieldType.newInstance();
							propertySetMethod.invoke(this, propertyObj);
						}
					}
					
					/*int x = 0;
					if(propertyFieldName.contains("Cor"))
						x = 1;*/
					
					// recupera o campo equivalente na entidade
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
					final Method entidadeSetMethod = TReflectionUtil.getSetterMethod(model.getClass(), propertyFieldName, entityFieldType);
					//final Method entidadeSetMethod = model.getClass().getMethod("set"+StringUtils.capitalize(propertyFieldName), entidadeFieldType);;
					
					// recupera o valor do campo na entidade
					final Method entityGetMethod = TReflectionUtil.getGetterMethod(model.getClass(), propertyFieldName);
					//final Method entidadeGetMethod = model.getClass().getMethod("get"+StringUtils.capitalize(propertyFieldName));
					// recupera o objeto da entidade
					final Object obj = entityGetMethod.invoke(model);
					
					if(TPropertyUtil.isCollectionObservableType(propertyFieldType)){
						
						Class modelViewClass = null; 
						Class entityClass = null;
						
						for(Annotation annotation : field.getDeclaredAnnotations())
							if(annotation instanceof TModelViewCollectionType ){
								TModelViewCollectionType tAnnotation = (TModelViewCollectionType) annotation;
								modelViewClass = tAnnotation.modelViewClass();
								entityClass = tAnnotation.modelClass();
							}
						
						// ObservableList.class
						//if(propertyFieldType == ObservableList.class){ // OK
						if(TReflectionUtil.isImplemented(propertyFieldType, ObservableList.class)){ // OK
							final ObservableList property = (ObservableList) propertyObj; //modelViewGetMethod.invoke(this);
							final ListChangeListener listener = (changeListenersRepository!=null) 
									? (ListChangeListener) changeListenersRepository.get(entidadeSetMethod.getName())
											: null;
							if(listener!=null){
								property.removeListener(listener);
								property.clear();
							}
							if(obj!=null){
								for(Object o : (Collection) obj){
									//if(o instanceof TModelView){
									if(o instanceof ITEntity){
										if(modelViewClass==null || entityClass==null)
											throw new Exception("\n\nT_ERROR "
													+ "\nType: "+TErrorType.COLLECTION_CONFIGURATION
													+ "\nFIELD: "+getClass().getSimpleName() + "."+propertyFieldName
													+ "\n\n-Use the @TModelViewCollectionType annotation at field "+getClass().getSimpleName() + "."+propertyFieldName+"\n\n");
										try{
											property.add(modelViewClass.getConstructor(entityClass).newInstance(o));
										}catch(IllegalArgumentException e){
											String error = "\n\nT_ERROR\nType: "+TErrorType.BUILD
													+ "\nModel View class: "+modelViewClass.getName()+".class"
													+ "\nModel View constructor argument type: "+entityClass.getName()+".class"
													+ "\nEntity to bind: "+o.getClass().getName()+".class"
													+ "\n\n-Check the configuration of the field "+propertyFieldName+" at "+getClass().getName()+".java\n\n";
											System.out.println(error);
											e.printStackTrace();
										}
									}else if(o instanceof ITFileModel){
										property.add(new TSimpleFileModelProperty<ITFileModel>((ITFileModel)o));
									}
								}
							}
							if(listener==null)
								buildListListener(propertyFieldType, entityGetMethod, entidadeSetMethod, entityFieldType, property);
							else
								property.addListener(listener);
						} 
						
						// ObservableSet.class
						if(propertyFieldType == ObservableSet.class){ // TODO: DEVE SER TESTADO
							final ObservableSet property = (ObservableSet) propertyObj; // modelViewGetMethod.invoke(this);
							final SetChangeListener listener = (changeListenersRepository!=null) 
									? (SetChangeListener)changeListenersRepository.get(entidadeSetMethod.getName())
											:null;
							if(listener!=null){
								property.removeListener(listener);
								property.clear();
							}
							if(obj!=null)
								for(Object o : (Collection) obj)
									property.add(modelViewClass.getConstructor(entityClass).newInstance(o));
							if(listener==null)
								buildSetListener(propertyFieldType, entityGetMethod, entidadeSetMethod, entityFieldType, property);
							else
								property.addListener(listener);
						}
						
						// ObservableMap.class
						if(propertyFieldType == ObservableMap.class ){ // TODO: DEVE SER TESTADO
							final ObservableMap property = (ObservableMap) propertyObj; //modelViewGetMethod.invoke(this);
							if(!buildListener){
								final MapChangeListener listener = (MapChangeListener)changeListenersRepository.get(entidadeSetMethod.getName());
								property.removeListener(listener);
								property.clear();
								property.addListener(listener);
							}
							property.putAll((Map) obj);
							if(buildListener)
								buildMapListener(propertyFieldType, entityGetMethod, entidadeSetMethod, property);
						}
					}else{
					
						final Property property = (Property) propertyObj; //modelViewGetMethod.invoke(this);
						if(!isTypesCompatible(propertyFieldType, entityFieldType)){
							
							String error = "\n\nTERROR :: FIELD "+propertyFieldName+" of type "+propertyFieldType.getSimpleName()+" in "+getClass().getSimpleName()
									+" is not compatible for the type "+entityFieldType+" on "+getModel().getClass().getSimpleName()+"\n";
							error += "TINFO :: TModelView "+ propertyFieldType.getSimpleName() +" compatible types: ";
							for(Class c : compatibleTypes.get(property.getClass()))
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
							}else
								property.setValue(obj);
						}
							
						
						// gera os ChangeListener(s)
						if(buildListener)
							buildListeners(propertyFieldType, entityFieldType, property, entidadeSetMethod, obj);
					}
					
				}catch(Throwable e){				
					e.printStackTrace();
				}
			}else{
				
				if(propertyFieldType == TAreaChartField.class)
					continue;
				
				String types = "";
				for (Class c : compatibleTypes.keySet()) types += c.getSimpleName()+", ";
				System.out.println("TWARNING :: FIELD "+propertyFieldName+" of type "+propertyFieldType.getSimpleName()+" in "+getClass().getSimpleName()+" is not compatible for a TModelView!");
				System.out.println("TINFO :: TModelView compatible types: "+types.substring(0,types.length()-2)+"\n");
			}
		}
		
		for(final Field entidadeField : model.getClass().getSuperclass().getDeclaredFields()){
			if(entidadeField.getName().equals("id")){
				try{
					final Class entidadeFieldType = entidadeField.getType();
					// recupera o metodo set do atributo na entidade
					final Method entidadeSetMethod = model.getClass().getMethod("setId", entidadeFieldType);
					// recupera o valor do campo na entidade
					final Method entidadeGetMethod = model.getClass().getMethod("getId");
					// recupera o objeto da entidade
					final Long id = (Long) entidadeGetMethod.invoke(model);
					
					final Method modelViewGetMethod = this.getClass().getMethod("getId");
					
					final Method propertySetMethod = this.getClass().getMethod("setId", SimpleLongProperty.class);
					SimpleLongProperty propertyObj = (SimpleLongProperty) modelViewGetMethod.invoke(this);
					if(propertyObj==null){
						propertyObj = new SimpleLongProperty(id);
						propertySetMethod.invoke(this, propertyObj);
						buildListeners(SimpleLongProperty.class, entidadeFieldType, propertyObj, entidadeSetMethod, id);
					}else{
						propertyObj.setValue(id);
						buildListeners(SimpleLongProperty.class, entidadeFieldType, propertyObj, entidadeSetMethod, id);
					}
					
					/*if(getId()==null){
						this.setId(new SimpleLongProperty(id));
						buildListeners(SimpleLongProperty.class, entidadeFieldType, this.getId(), entidadeSetMethod);
					}else{
						if(changeListenersRepository.containsKey("id"))
							getId().removeListener((ChangeListener)changeListenersRepository.get("id"));
						getId().setValue(id);
						if(changeListenersRepository.containsKey("id"))
							getId().addListener((ChangeListener)changeListenersRepository.get("id"));
					}*/
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
				
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean isTypesCompatible(final Class propertyFieldType, Class typeToVerify) {
		
		boolean compatible = compatibleTypes.get(propertyFieldType).contains(typeToVerify); 
		
		if(!compatible && propertyFieldType == TSimpleFileEntityProperty.class)
			compatible = isClassAnFileEntity(typeToVerify);
		
		if(!compatible && propertyFieldType == TSimpleFileModelProperty.class)
			compatible = isClassAnFileModel(typeToVerify);
		
		if(!compatible && isClassAnEntity(typeToVerify))
			compatible = compatibleTypes.get(propertyFieldType).contains(TEntity.class);
		
		return compatible; 
	}

	@SuppressWarnings("rawtypes")
	private boolean isCompatible(final Class propertyFieldType) {
		return compatibleTypes.containsKey(propertyFieldType);
	}

	/**
	 * Gera os ChangeListener(s) de acordo com a compatibilidade entre os tipos do TViewModel com os do ITEntity (entidade)
	 * */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private void buildListeners(final Class propertyFieldType, final Class entidadeFieldType, final Property property, final Method entidadeSetMethod, final Object entityFieldValue) {
		
		//TSimpleFileEntityProperty
		if(propertyFieldType == TSimpleFileEntityProperty.class){
			if(isClassAnFileEntity(entidadeFieldType)){
				
				InvalidationListener invalidationListener = new InvalidationListener() {
					@Override
					public void invalidated(Observable arg0) {
						try {
							final M model = modelProperty.get();
							final ITFileEntity fileEntity = (ITFileEntity) ((TSimpleFileEntityProperty)property).getValue();
							if(fileEntity.getByteEntity().getBytes()==null && fileEntity.getFileName()==null)
								entidadeSetMethod.invoke(model, (ITFileEntity) null);
							else
								entidadeSetMethod.invoke(model, fileEntity);
							buildLastHashCode();
						} catch (IllegalAccessException	| IllegalArgumentException	| InvocationTargetException e) {
							e.printStackTrace();
						}
						
					}
				};
				
				setInvalidationListener(property, entidadeSetMethod, invalidationListener);
				
				return;
			}
		}
		
		//TSimpleFileModelProperty
		if(propertyFieldType == TSimpleFileModelProperty.class){
			if(isClassAnFileModel(entidadeFieldType)){
				InvalidationListener invalidationListener = new InvalidationListener() {
					@Override
					public void invalidated(Observable arg0) {
						try {
							final M model = modelProperty.get();
							entidadeSetMethod.invoke(model, (ITFileModel) ((TSimpleFileModelProperty)property).getValue());
							buildLastHashCode();
						} catch (IllegalAccessException	| IllegalArgumentException	| InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				};
				
				setInvalidationListener(property, entidadeSetMethod, invalidationListener);
				
				return;
			}
		}
		
		
		// ObjectProperty
		if(ObjectProperty.class.isAssignableFrom(propertyFieldType)){
			// ITEntity
			if(isClassAnEntity(entidadeFieldType)){
				
				ChangeListener<Object> changeListener = new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
						try {
							final M model = modelProperty.get();
							entidadeSetMethod.invoke(model, (arg2 instanceof TModelProperty) ? ((TModelProperty)arg2).getModel() : arg2);
							buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
				
			}else
			// Date
			if(entidadeFieldType == Date.class){
				
				ChangeListener<Date> changeListener = new ChangeListener<Date>() {
					@Override
					public void changed(ObservableValue<? extends Date> arg0, Date arg1, Date arg2) {
						try {
							final M model = modelProperty.get();
							entidadeSetMethod.invoke(model, arg2);
							buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
				
			}else
			
			// BigDecimal
			if(entidadeFieldType == BigDecimal.class){
				
				ChangeListener<BigDecimal> changeListener = new ChangeListener<BigDecimal>() {
					@Override
					public void changed(ObservableValue<? extends BigDecimal> arg0, BigDecimal arg1, BigDecimal arg2) {
						try {
							final M model = modelProperty.get();
							entidadeSetMethod.invoke(model, arg2);
							buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
				
			}else
			
			// BigInteger
			if(entidadeFieldType == BigInteger.class){
				
				ChangeListener<BigInteger> changeListener = new ChangeListener<BigInteger>() {
					@Override
					public void changed(ObservableValue<? extends BigInteger> arg0, BigInteger arg1, BigInteger arg2) {
						try {
							final M model = modelProperty.get();
							entidadeSetMethod.invoke(model, arg2);
							buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
				
			}else
			// byte
			if(entidadeFieldType == byte[].class){
				
				ChangeListener<byte[]> changeListener = new ChangeListener<byte[]>() {
					@Override
					public void changed(ObservableValue<? extends byte[]> arg0, byte[] arg1, byte[] arg2) {
						try {
							final M model = modelProperty.get();
							entidadeSetMethod.invoke(model, arg2);
							buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
			
			}else{
				ChangeListener<Object> changeListener = new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
						try {
							final M model = modelProperty.get();
							entidadeSetMethod.invoke(model, arg2);
							buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
			}
			
			return;
		}
		
		// StringProperty
		if(StringProperty.class.isAssignableFrom(propertyFieldType)){
			ChangeListener changeListener = new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					try {
						final M model = modelProperty.get();
						
						if(arg2==null || entidadeFieldType == String.class)
							entidadeSetMethod.invoke(model, arg2);
						if(entidadeFieldType == Character.class)
							entidadeSetMethod.invoke(model, Character.valueOf(arg2.charAt(0)));
						if(entidadeFieldType == Long.class)
							entidadeSetMethod.invoke(model, Long.valueOf(arg2));
						if(entidadeFieldType == Integer.class)
							entidadeSetMethod.invoke(model, Integer.valueOf(arg2));
						if(entidadeFieldType == Double.class)
							entidadeSetMethod.invoke(model, Double.valueOf(arg2));
						if(entidadeFieldType == BigDecimal.class)
							entidadeSetMethod.invoke(model, BigDecimal.valueOf(Double.valueOf(arg2)));
						if(entidadeFieldType == BigInteger.class)
							entidadeSetMethod.invoke(model, BigInteger.valueOf(Integer.valueOf(arg2)));
						buildLastHashCode();
					}catch(NumberFormatException e){
						// TODO: TRATAR ERRO QUANDO UMA STRING N�O PUDER SER CONVERTIDA PARA NUMBER
						e.printStackTrace();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
			
			return;
		}
		
		// DoubleProperty
		if(DoubleProperty.class.isAssignableFrom(propertyFieldType)){
			
			ChangeListener changeListener = new ChangeListener<Double>() {
				@Override
				public void changed(ObservableValue<? extends Double> arg0, Double arg1, Double arg2) {
					try {
						final M model = modelProperty.get();
						
						if(arg2==null || entidadeFieldType == Double.class)
							entidadeSetMethod.invoke(model, arg2);
						
						if(entidadeFieldType == BigDecimal.class)
							entidadeSetMethod.invoke(model, BigDecimal.valueOf(arg2));
						buildLastHashCode();
					}catch(NumberFormatException e){
						// TODO: TRATAR ERRO QUANDO UMA STRING N�O PUDER SER CONVERTIDA PARA NUMBER
						e.printStackTrace();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
			
			return;
		}
				
		// BooleanProperty
		if(BooleanProperty.class.isAssignableFrom(propertyFieldType)){
			
			ChangeListener changeListener = new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					try {
						final M model = modelProperty.get();
						if(arg2==null)
							arg2 = Boolean.FALSE;
						if(entidadeSetMethod.getParameterTypes()[0] == String.class){
							entidadeSetMethod.invoke(model, (arg2) ? "T" : "F");
						}else{
							entidadeSetMethod.invoke(model, arg2);
						}
						
						buildLastHashCode();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
			
			return;
		}
		
		// IntegerProperty
		if(IntegerProperty.class.isAssignableFrom(propertyFieldType)){
			
			ChangeListener changeListener = new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
					try {
						final M model = modelProperty.get();
						entidadeSetMethod.invoke(model, arg2);
						buildLastHashCode();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
			
			return;
		}
		
		// LongProperty
		if(LongProperty.class.isAssignableFrom(propertyFieldType)){
			
			ChangeListener<Long> changeListener = new ChangeListener<Long>() {
				@Override
				public void changed(ObservableValue<? extends Long> arg0, Long arg1, Long arg2) {
					try {
						final M model = modelProperty.get();
						entidadeSetMethod.invoke(model, arg2);
						buildLastHashCode();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
			
			return;
		}
		
		// FloatProperty
		if(FloatProperty.class.isAssignableFrom(propertyFieldType)){
			
			ChangeListener<Float> changeListener = new ChangeListener<Float>() {
				@Override
				public void changed(ObservableValue<? extends Float> arg0, Float arg1, Float arg2) {
					try {
						final M model = modelProperty.get();
						entidadeSetMethod.invoke(model, arg2);
						buildLastHashCode();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, entidadeSetMethod, changeListener, entityFieldValue);
			
			return;
		}
		
	}

	@SuppressWarnings("rawtypes")
	private void setInvalidationListener(final Property property,
			final Method entidadeSetMethod,
			InvalidationListener invalidationListener) {
		WeakInvalidationListener weakInvalidationListener = new WeakInvalidationListener(invalidationListener);
		property.addListener(weakInvalidationListener);
		this.changeListenersRepository.put(entidadeSetMethod.getName(), weakInvalidationListener);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setChangeListener(final Property property, final Method entidadeSetMethod, ChangeListener changeListener, Object entityFieldValue) {
		//WeakChangeListener weakChangeListener = new WeakChangeListener<>(changeListener);
		//property.addListener(weakChangeListener);
		
		/*if(entityFieldValue!=null)
			property.setValue(entityFieldValue);*/
		
		property.addListener(changeListener);
		initChangeListenerRepository();
		this.changeListenersRepository.put(entidadeSetMethod.getName(), changeListener);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private void buildMapListener(final Class propertyFieldType, final Method getMethod, final Method setMethod, final ObservableMap property){
		// ObservableMap
		if(ObservableMap.class.isAssignableFrom(propertyFieldType)){
				
			final MapChangeListener changeListener = new MapChangeListener(){
				@Override
				public void onChanged(javafx.collections.MapChangeListener.Change c) {
					try {
						final M model = modelProperty.get();
						final Object obj = getMethod.invoke(model);
						if(obj==null)
							setMethod.invoke(model, c.getMap());
						else{
							((Collection) obj).clear();
							((Collection) obj).addAll(c.getMap().values());
						}
						buildLastHashCode();
					} catch (IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			};
			
			WeakMapChangeListener weakMapChangeListener = new WeakMapChangeListener<>(changeListener);
			property.addListener(weakMapChangeListener);
			
			initChangeListenerRepository();
			changeListenersRepository.put(setMethod.getName(), weakMapChangeListener);
			
			return;
		}
	}

	private void initChangeListenerRepository() {
		if(changeListenersRepository == null)
			changeListenersRepository = new HashMap<>();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private void buildSetListener(final Class propertyFieldType, final Method getMethod, final Method setMethod, final Class entidadeFieldType, final ObservableSet property){
		// ObservableSet
		//if(propertyFieldType == ObservableSet.class){
		if(ObservableSet.class.isAssignableFrom(propertyFieldType)){
			
			final SetChangeListener changeListener = new SetChangeListener(){
				@Override
				public void onChanged(javafx.collections.SetChangeListener.Change c) {
					try {
						final M model = modelProperty.get();
						final Object obj = getMethod.invoke(model);
						if(obj==null){
							setMethod.invoke(model, entidadeFieldType==Set.class ? new HashSet<>(c.getSet()) : c.getSet());
						}else{
							((Collection) obj).clear();
							((Collection) obj).addAll(c.getSet());
						}
						buildLastHashCode();
					} catch (IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			};
			WeakSetChangeListener weakSetChangeListener = new WeakSetChangeListener<>(changeListener);
			property.addListener(weakSetChangeListener);
			
			initChangeListenerRepository();
			changeListenersRepository.put(setMethod.getName(), weakSetChangeListener);
			
			return;
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private void buildListListener(final Class propertyFieldType, final Method getMethod, final Method setMethod, final Class entidadeFieldType, final ObservableList property){
		// 	ObservableList
		//if(propertyFieldType == ObservableList.class){
		//if(TReflectionUtil.isImplemented(propertyFieldType, ObservableList.class)){
		if(ObservableList.class.isAssignableFrom(propertyFieldType)){
				
			final ListChangeListener changeListener = new ListChangeListener(){
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change c) {
					try {
						final M model = modelProperty.get();
						final Object obj = getMethod.invoke(model);
						if(obj==null){
							if(entidadeFieldType==Set.class){
								Set col = new HashSet<>();
								for(Object o : c.getList()){
									if(o instanceof TModelProperty)
										col.add(((TModelProperty)o).getModel());
									else
										col.add(o);
								}
								setMethod.invoke(model, col);
							}else{
								List col = new ArrayList();
								for(Object o : c.getList()){
									if(o instanceof TModelProperty)
										col.add(((TModelProperty)o).getModel());
									else
										col.add(o);
								}
								setMethod.invoke(model, col);
							}
							
						}else{
							ListChangeListener l = this;
							property.removeListener(l);
							Collection col = (Collection) obj;
							col.clear();
							for(Object o : c.getList()){
								if(o instanceof TModelProperty)
									col.add(((TModelProperty)o).getModel());
								else
									col.add(o);
							}
							property.addListener(l);
						}
						buildLastHashCode();
					} catch (IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			};
			
			//if(TReflectionUtil.isImplemented(propertyFieldType, ITObservableList.class)){
			if(ITObservableList.class.isAssignableFrom(propertyFieldType)){
				((ITObservableList)property).tHashCodeProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
						buildLastHashCode();
					}
				});;
			}
			
			WeakListChangeListener weakListChangeListener = new WeakListChangeListener<>(changeListener);
			property.addListener(weakListChangeListener);
			
			initChangeListenerRepository();
			changeListenersRepository.put(setMethod.getName(), weakListChangeListener);
			
			return;
		}	
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private void loadTypesCompatibility(){
		
		compatibleTypes = new HashMap<>();
		compatibleTypes.put(SimpleObjectProperty.class, (List) Arrays.asList(ITModel.class, TEntity.class, ITFileEntity.class, ITFileModel.class, ITEntity.class, Color.class, Object.class, Date.class, byte[].class, BigDecimal.class, BigInteger.class));
		compatibleTypes.put(SimpleStringProperty.class, (List) Arrays.asList(String.class, Character.class, Long.class, Integer.class, Double.class, BigDecimal.class, BigInteger.class));
		compatibleTypes.put(SimpleDoubleProperty.class, (List) Arrays.asList(Double.class, BigDecimal.class));
		compatibleTypes.put(SimpleBooleanProperty.class, (List) Arrays.asList(Boolean.class, String.class));
		compatibleTypes.put(SimpleIntegerProperty.class, (List) Arrays.asList(Integer.class));
		compatibleTypes.put(SimpleLongProperty.class, (List) Arrays.asList(Long.class));
		compatibleTypes.put(SimpleFloatProperty.class, (List) Arrays.asList(Float.class));
		compatibleTypes.put(TSimpleFileEntityProperty.class, (List) Arrays.asList(ITFileEntity.class));
		compatibleTypes.put(TSimpleFileModelProperty.class, (List) Arrays.asList(ITFileModel.class));
		
		compatibleTypes.put(ITObservableList.class, (List) Arrays.asList(List.class, ArrayList.class));
		compatibleTypes.put(TSimpleObservableList.class, (List) Arrays.asList(List.class, ArrayList.class));
		compatibleTypes.put(ObservableList.class, (List) Arrays.asList(List.class, ArrayList.class));		
		compatibleTypes.put(ObservableSet.class, (List) Arrays.asList(Set.class, HashSet.class));
		compatibleTypes.put(ObservableMap.class, (List) Arrays.asList(Map.class, HashMap.class));
		
		compatibleTypes.put(SimpleListProperty.class, (List) Arrays.asList(List.class, ArrayList.class));		
		compatibleTypes.put(SimpleSetProperty.class, (List) Arrays.asList(Set.class, HashSet.class));
		compatibleTypes.put(SimpleMapProperty.class, (List) Arrays.asList(Map.class, HashMap.class));
		
	}

	@SuppressWarnings("rawtypes")
	private boolean isClassAnEntity(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITEntity.class);
	}

	@SuppressWarnings("rawtypes")
	private boolean isClassAnFileModel(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITFileModel.class);
	}

	@SuppressWarnings("rawtypes")
	private boolean isClassAnFileEntity(Class clazz) {
		return TReflectionUtil.isImplemented(clazz, ITFileEntity.class);
	}

	@Transient
	private void buildLastHashCode() {
		setLastHashCode(hashCode());
		lastHashCodeProperty().setValue(lastHashCode());
		final M model = modelProperty.get();
		model.toString();
	}

	@Transient
	private void setLastHashCode(int lastHashCode) {
		this.lastHashCode = lastHashCode;
	}

	@Transient
	private void setLastHashCodeProperty(SimpleIntegerProperty lastHashCodeProperty) {
		this.lastHashCodeProperty = lastHashCodeProperty;
	}
	
	/**
	 * <pre>
	 * </pre>
	 * */
	public class TDetailInfo{
		final private String formTitle;
		final private String listTitle;
		final private Observable property;
		@SuppressWarnings("rawtypes")
		final private Class<? extends TEntityModelView> modelViewClass;
		final private Class<? extends ITEntity> entityClass;
		@SuppressWarnings("rawtypes")
		final private Class<? extends ITModelForm> formClass;
		
		@SuppressWarnings("rawtypes")
		public TDetailInfo(String formTitle, String listTitle, Observable property, Class<? extends TEntityModelView> modelViewClass, Class<? extends ITEntity> entityClass, Class<? extends ITModelForm> formClass) {
			this.formTitle = formTitle;
			this.listTitle = listTitle; 
			this.property = property;
			this.modelViewClass = modelViewClass;
			this.entityClass = entityClass;
			this.formClass = formClass; 
		}
		
		public String getFormTitle() {
			return formTitle;
		}
		
		public String getListTitle() {
			return listTitle;
		}

		public Observable getProperty() {
			return property;
		}

		@SuppressWarnings("rawtypes")
		public Class<? extends TEntityModelView> getModelViewClass() {
			return modelViewClass;
		}

		public Class<? extends ITEntity> getEntityClass() {
			return entityClass;
		}
		
		@SuppressWarnings("rawtypes")
		public Class<? extends ITModelForm> getFormClass() {
			return formClass;
		}
	}
	
	
		
}
