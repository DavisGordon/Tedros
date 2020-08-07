package com.tedros.fxapi.presenter.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.tedros.core.module.TListenerRepository;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.ITFileModel;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;
import com.tedros.fxapi.property.TSimpleFileModelProperty;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import main.references.Model;

class TListenerHelper<M extends ITModel> {
	
	private 	TModelView<M> 				tModelView;
	protected 	Map<String, Set<String>> 	listenerKeys 		= new HashMap<>();
	protected 	TListenerRepository 		tListenerRepository = new TListenerRepository();
	
	/**
	 * Constructor
	 * */
	protected TListenerHelper(TModelView<M> tModelView) {
		this.tModelView = tModelView;
		tListenerRepository = new TListenerRepository();
	}
	
	/**
	 * Build a unique key to the field name.    
	 * */
	private String buildKeyForField(String fieldName){
		
		String key = fieldName;
		
		final Set<String> list = (Set<String>) (listenerKeys.containsKey(key)
				? listenerKeys.get(key)
						: buildList(fieldName));
		
		if(list.contains(key))
			key = fieldName + "_" + UUID.randomUUID(); 
				
		list.add(key);
		
		return key;
	}

	/**
	 * Return an empty {@link Set} of String generated to the field name. 
	 * */
	private Set<String> buildList(String fieldName) {
		Set<String> list = new HashSet<>();
		listenerKeys.put(fieldName, list);
		return list;
	}
	
	
	protected <T> ChangeListener<T> buildListener(final String fieldName){
		String key = buildKeyForField(fieldName);
		ChangeListener<T> listener = new ChangeListener<T>() {
			@Override
			public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {
				
				Method m = TReflectionUtil.getSetterMethod(Model.class, fieldName, arg2.getClass());
				try {
					m.invoke(tModelView.model, arg2);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		};
		
		tListenerRepository.addListener(key, listener);
		
		return listener;
	}
	
	protected <T> T removeListener(String listenerId){
		return this.tListenerRepository.removeListener(listenerId);
	}
		
	protected Map<String, Set<String>> getListenerKeys() {
		return listenerKeys;
	}
	
	protected TListenerRepository getListenerRepository() {
		return tListenerRepository;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void removeAllListener(){
		for(String fieldName : listenerKeys.keySet()){
			
			/*TODO: REMOVER
			 * int x=0;
			if(fieldName.equals("documentos"))
				x=1;*/
			
			for(String key : listenerKeys.get(fieldName)){
				Method m = TReflectionUtil.getGetterMethod(this.tModelView.getClass(), fieldName);
				if(m == null)
					continue;
				try {
					Object fieldPropertyObj = m.invoke(this.tModelView);
					Object listenerObj = tListenerRepository.removeListener(key);
					
					if(fieldPropertyObj instanceof Property && listenerObj instanceof ChangeListener)
						((Property) fieldPropertyObj).removeListener((ChangeListener) listenerObj);
					else 
						if(fieldPropertyObj instanceof Property && listenerObj instanceof InvalidationListener)
							((Property) fieldPropertyObj).removeListener((InvalidationListener) listenerObj);
					else					
						if(listenerObj instanceof ListChangeListener && (fieldPropertyObj instanceof ListProperty || fieldPropertyObj instanceof ITObservableList)){
							if(fieldPropertyObj instanceof ListProperty)
								((ListProperty)fieldPropertyObj).removeListener((ListChangeListener) listenerObj);
							else
								((ITObservableList)fieldPropertyObj).removeListener((ListChangeListener) listenerObj);
						}	
					else
						if(listenerObj instanceof SetChangeListener && fieldPropertyObj instanceof SetProperty)
							((SetProperty)fieldPropertyObj).removeListener((SetChangeListener) listenerObj);
					else
						if(listenerObj instanceof MapChangeListener && fieldPropertyObj instanceof MapProperty)
							((MapProperty)fieldPropertyObj).removeListener((MapChangeListener) listenerObj);
					
					
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		listenerKeys.clear();
	}
	
	/**
	 * Gera os ChangeListener(s) de acordo com a compatibilidade entre os tipos do TViewModel com os do ITEntity (entidade)
	 * */
	@SuppressWarnings({"rawtypes"})
	protected void buildListeners(final Class propertyFieldType, final Class entidadeFieldType, final Property property, final Method entidadeSetMethod, final Object entityFieldValue, final String fieldName) {
		
		//TSimpleFileEntityProperty
		if(propertyFieldType == TSimpleFileEntityProperty.class){
			if(tModelView.isClassAFileEntity(entidadeFieldType)){
				
				InvalidationListener invalidationListener = new InvalidationListener() {
					@Override
					public void invalidated(Observable arg0) {
						try {
							final ITFileEntity fileEntity = (ITFileEntity) ((TSimpleFileEntityProperty)property).getValue();
							if(fileEntity.getByteEntity().getBytes()==null && fileEntity.getFileName()==null)
								entidadeSetMethod.invoke(tModelView.model, (ITFileEntity) null);
							else
								entidadeSetMethod.invoke(tModelView.model, fileEntity);
							tModelView.buildLastHashCode();
						} catch (IllegalAccessException	| IllegalArgumentException	| InvocationTargetException e) {
							e.printStackTrace();
						}
						
					}
				};
				
				addListener(fieldName, invalidationListener);	
				
				return;
			}
		}
		
		//TSimpleFileModelProperty
		if(propertyFieldType == TSimpleFileModelProperty.class){
			if(tModelView.isClassAFileModel(entidadeFieldType)){
				InvalidationListener invalidationListener = new InvalidationListener() {
					@Override
					public void invalidated(Observable arg0) {
						try {
							entidadeSetMethod.invoke(tModelView.model, (ITFileModel) ((TSimpleFileModelProperty)property).getValue());
							tModelView.buildLastHashCode();
						} catch (IllegalAccessException	| IllegalArgumentException	| InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				};
				
				addListener(fieldName, invalidationListener);
				
				return;
			}
		}
		
		
		// ObjectProperty
		if(ObjectProperty.class.isAssignableFrom(propertyFieldType)){
			// ITEntity
			if(tModelView.isClassAModel(entidadeFieldType)){
				
				ChangeListener<Object> changeListener = new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
						try {
							entidadeSetMethod.invoke(tModelView.model, (arg2 instanceof TModelView) ? ((TModelView)arg2).getModel() : arg2);
							tModelView.buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, fieldName, changeListener, entityFieldValue);
				
			}else
			// Date
			if(entidadeFieldType == Date.class){
				
				ChangeListener<Date> changeListener = new ChangeListener<Date>() {
					@Override
					public void changed(ObservableValue<? extends Date> arg0, Date arg1, Date arg2) {
						try {
							entidadeSetMethod.invoke(tModelView.model, arg2);
							tModelView.buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, fieldName, changeListener, entityFieldValue);
				
			}else
			
			// BigDecimal
			if(entidadeFieldType == BigDecimal.class){
				
				ChangeListener<BigDecimal> changeListener = new ChangeListener<BigDecimal>() {
					@Override
					public void changed(ObservableValue<? extends BigDecimal> arg0, BigDecimal arg1, BigDecimal arg2) {
						try {
							entidadeSetMethod.invoke(tModelView.model, arg2);
							tModelView.buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, fieldName, changeListener, entityFieldValue);
				
			}else
			
			// BigInteger
			if(entidadeFieldType == BigInteger.class){
				
				ChangeListener<BigInteger> changeListener = new ChangeListener<BigInteger>() {
					@Override
					public void changed(ObservableValue<? extends BigInteger> arg0, BigInteger arg1, BigInteger arg2) {
						try {
							entidadeSetMethod.invoke(tModelView.model, arg2);
							tModelView.buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, fieldName, changeListener, entityFieldValue);
				
			}else
			// byte
			if(entidadeFieldType == byte[].class){
				
				ChangeListener<byte[]> changeListener = new ChangeListener<byte[]>() {
					@Override
					public void changed(ObservableValue<? extends byte[]> arg0, byte[] arg1, byte[] arg2) {
						try {
							entidadeSetMethod.invoke(tModelView.model, arg2);
							tModelView.buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, fieldName, changeListener, entityFieldValue);
			
			}else{
				ChangeListener<Object> changeListener = new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
						try {
							entidadeSetMethod.invoke(tModelView.model, arg2);
							tModelView.buildLastHashCode();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				
				setChangeListener(property, fieldName, changeListener, entityFieldValue);
			}
			
			return;
		}
		
		// StringProperty
		if(StringProperty.class.isAssignableFrom(propertyFieldType)){
			ChangeListener changeListener = new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					try {
						if(arg2==null || entidadeFieldType == String.class){
							entidadeSetMethod.invoke(tModelView.model, arg2);
						}else if(entidadeFieldType == Character.class){
							entidadeSetMethod.invoke(tModelView.model, Character.valueOf(arg2.charAt(0)));
						}else if(entidadeFieldType == Long.class){
							entidadeSetMethod.invoke(tModelView.model, Long.valueOf(arg2));
						}else if(entidadeFieldType == Integer.class){
							entidadeSetMethod.invoke(tModelView.model, Integer.valueOf(arg2));
						}else if(entidadeFieldType == Double.class){
							entidadeSetMethod.invoke(tModelView.model, Double.valueOf(arg2));
						}else if(entidadeFieldType == BigDecimal.class){
							entidadeSetMethod.invoke(tModelView.model, BigDecimal.valueOf(Double.valueOf(arg2)));
						}else if(entidadeFieldType == BigInteger.class){
							entidadeSetMethod.invoke(tModelView.model, BigInteger.valueOf(Integer.valueOf(arg2)));
						}
						
						// Used to test with the listener was removed
						//System.out.println(tModelView.getClass().getSimpleName()+" "+entidadeSetMethod.getName()+ " " + arg2);
						
						tModelView.buildLastHashCode();
						
					}catch(NumberFormatException e){
						// TODO: TRATAR ERRO QUANDO UMA STRING N�O PUDER SER CONVERTIDA PARA NUMBER
						e.printStackTrace();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, fieldName, changeListener, entityFieldValue);
			
			return;
		}
		
		// DoubleProperty
		if(DoubleProperty.class.isAssignableFrom(propertyFieldType)){
			
			ChangeListener changeListener = new ChangeListener<Double>() {
				@Override
				public void changed(ObservableValue<? extends Double> arg0, Double arg1, Double arg2) {
					try {
						
						if(arg2==null || entidadeFieldType == Double.class)
							entidadeSetMethod.invoke(tModelView.model, arg2);
						
						if(entidadeFieldType == BigDecimal.class)
							entidadeSetMethod.invoke(tModelView.model, BigDecimal.valueOf(arg2));
						tModelView.buildLastHashCode();
					}catch(NumberFormatException e){
						// TODO: TRATAR ERRO QUANDO UMA STRING N�O PUDER SER CONVERTIDA PARA NUMBER
						e.printStackTrace();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, fieldName, changeListener, entityFieldValue);
			
			return;
		}
				
		// BooleanProperty
		if(BooleanProperty.class.isAssignableFrom(propertyFieldType)){
			
			ChangeListener changeListener = new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					try {
						if(arg2==null)
							arg2 = Boolean.FALSE;
						if(entidadeSetMethod.getParameterTypes()[0] == String.class){
							entidadeSetMethod.invoke(tModelView.model, (arg2) ? "T" : "F");
						}else{
							entidadeSetMethod.invoke(tModelView.model, arg2);
						}
						
						tModelView.buildLastHashCode();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, fieldName, changeListener, entityFieldValue);
			
			return;
		}
		
		// IntegerProperty
		if(IntegerProperty.class.isAssignableFrom(propertyFieldType)){
			
			ChangeListener changeListener = new ChangeListener<Integer>() {
				@Override
				public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
					try {
						entidadeSetMethod.invoke(tModelView.model, arg2);
						tModelView.buildLastHashCode();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, fieldName, changeListener, entityFieldValue);
			
			return;
		}
		
		// LongProperty
		if(LongProperty.class.isAssignableFrom(propertyFieldType)){
			
			ChangeListener<Long> changeListener = new ChangeListener<Long>() {
				@Override
				public void changed(ObservableValue<? extends Long> arg0, Long arg1, Long arg2) {
					try {
						entidadeSetMethod.invoke(tModelView.model, arg2);
						tModelView.buildLastHashCode();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, fieldName, changeListener, entityFieldValue);
			
			return;
		}
		
		// FloatProperty
		if(FloatProperty.class.isAssignableFrom(propertyFieldType)){
			
			ChangeListener<Float> changeListener = new ChangeListener<Float>() {
				@Override
				public void changed(ObservableValue<? extends Float> arg0, Float arg1, Float arg2) {
					try {
						entidadeSetMethod.invoke(tModelView.model, arg2);
						tModelView.buildLastHashCode();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO: TRATAR ERRO QUANDO A REFLEX�O NO CAMPO FALHAR
						e.printStackTrace();
					}
				}
			};
			
			setChangeListener(property, fieldName, changeListener, entityFieldValue);
			
			return;
		}		
	}

	
	@SuppressWarnings({"unchecked", "rawtypes"})
	protected void buildMapListener(final Class propertyFieldType, final Method getMethod, final Method setMethod, final ObservableMap property, final String fieldName){
		// ObservableMap
		if(ObservableMap.class.isAssignableFrom(propertyFieldType)){
				
			final MapChangeListener changeListener = new MapChangeListener(){
				@Override
				public void onChanged(javafx.collections.MapChangeListener.Change c) {
					try {
						final Object obj = getMethod.invoke(tModelView.model);
						if(obj==null)
							setMethod.invoke(tModelView.model, c.getMap());
						else{
							((Collection) obj).clear();
							((Collection) obj).addAll(c.getMap().values());
						}
						tModelView.buildLastHashCode();
					} catch (IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			};
			
			property.addListener(changeListener);
			addListener(fieldName, changeListener);
			
			return;
		}
	}


	@SuppressWarnings({"unchecked", "rawtypes"})
	protected void buildSetListener(final Class propertyFieldType, final Method getMethod, final Method setMethod, final Class entidadeFieldType, final ObservableSet property, final String fieldName){
		// ObservableSet
		//if(propertyFieldType == ObservableSet.class){
		if(ObservableSet.class.isAssignableFrom(propertyFieldType)){
			
			final SetChangeListener changeListener = new SetChangeListener(){
				@Override
				public void onChanged(javafx.collections.SetChangeListener.Change c) {
					try {
						final Object obj = getMethod.invoke(tModelView.model);
						if(obj==null){
							setMethod.invoke(tModelView.model, entidadeFieldType==Set.class ? new HashSet<>(c.getSet()) : c.getSet());
						}else{
							((Collection) obj).clear();
							((Collection) obj).addAll(c.getSet());
						}
						tModelView.buildLastHashCode();
					} catch (IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			};
			
			property.addListener(changeListener);
			addListener(fieldName, changeListener);
			
			return;
		}
	}

	

	@SuppressWarnings({"unchecked", "rawtypes"})
	protected void buildListListener(final Class propertyFieldType, final Method getMethod, final Method setMethod, final Class entidadeFieldType, final ObservableList property, final String fieldName){
		// 	ObservableList
		//if(propertyFieldType == ObservableList.class){
		//if(TReflectionUtil.isImplemented(propertyFieldType, ObservableList.class)){
		if(ObservableList.class.isAssignableFrom(propertyFieldType)){
				
			final ListChangeListener changeListener = new ListChangeListener(){
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change c) {
					try {
						final Object obj = getMethod.invoke(tModelView.model);
						if(obj==null){
							if(entidadeFieldType==Set.class){
								Set col = new HashSet<>();
								for(Object o : c.getList()){
									if(o instanceof TModelView)
										col.add(((TModelView)o).getModel());
									else
										col.add(o);
								}
								setMethod.invoke(tModelView.model, col);
							}else{
								List col = new ArrayList();
								for(Object o : c.getList()){
									if(o instanceof TModelView)
										col.add(((TModelView)o).getModel());
									else
										col.add(o);
								}
								setMethod.invoke(tModelView.model, col);
							}
							
						}else{
							ListChangeListener l = this;
							property.removeListener(l);
							Collection col = (Collection) obj;
							col.clear();
							for(Object o : c.getList()){
								if(o instanceof TModelView)
									col.add(((TModelView)o).getModel());
								else
									col.add(o);
							}
							property.addListener(l);
						}
						tModelView.buildLastHashCode();
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
						tModelView.buildLastHashCode();
					}
				});;
			}

			property.addListener(changeListener);
			addListener(fieldName, changeListener);
			
			return;
		}	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setChangeListener(final Property property, final String fieldName, ChangeListener changeListener, Object entityFieldValue) {
		property.addListener(changeListener);
		addListener(fieldName, changeListener);
	}
	
	/**
	 * @param fieldName
	 * @param invalidationListener
	 */
	protected void addListener(final String fieldName, InvalidationListener invalidationListener) {
		tListenerRepository.addListener(buildKeyForField(fieldName), invalidationListener);
	}
	
	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	protected void addListener(final String fieldName, ChangeListener changeListener) {
		tListenerRepository.addListener(this.buildKeyForField(fieldName), changeListener);
	}
	
	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	protected void addListener(final String fieldName, final MapChangeListener changeListener) {
		tListenerRepository.addListener(this.buildKeyForField(fieldName), changeListener);
	}
	
	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	protected void addListener(final String fieldName, final SetChangeListener changeListener) {
		tListenerRepository.addListener(this.buildKeyForField(fieldName), changeListener);
	}

	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	protected void addListener(final String fieldName, final ListChangeListener changeListener) {
		tListenerRepository.addListener(this.buildKeyForField(fieldName), changeListener);
	}

}
