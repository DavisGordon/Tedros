package org.tedros.fx.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.TIgnoreField;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.builder.ITControlBuilder;
import org.tedros.fx.builder.ITFileBuilder;
import org.tedros.fx.builder.ITLayoutBuilder;
import org.tedros.fx.builder.ITReaderBuilder;
import org.tedros.fx.builder.ITReaderHtmlBuilder;
import org.tedros.fx.builder.ITViewBuilder;
import org.tedros.fx.descriptor.TFieldDescriptor;
import org.tedros.fx.form.TConverter;
import org.tedros.fx.model.TModelView;

public final class TReflectionUtil {

//	public static final String ANNOTATION_ROOT_PACKAGE = "org.tedros.fx.annotation";
	public static final String ANNOTATION_EFFECT_PACKAGE = "org.tedros.fx.annotation.effect";
	
	
	public static boolean isIgnoreField(final ITFieldDescriptor tFieldDescriptor){
		for (Annotation annotation : tFieldDescriptor.getAnnotations())
			if(annotation instanceof TIgnoreField)
				return true;
		return false;
	}
	
	public static boolean isTypeOf(Class<?> from, Class<?> type){
		while(from != type){
			if(from ==null || from == Object.class)
				return false;
			from = from.getSuperclass();
		}
		return true;
	}
	
	public static boolean isImplemented(Class<?> from, Class<?>... interfaceType){
		int x = interfaceType.length; 
		for (Class<?> class1 : interfaceType) {
			if(isImplemented(from, class1))
				x--;
		}
		return x!=interfaceType.length;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isImplemented(Class<?> from, Class<?> interfaceType){
		if(from == interfaceType)
			return true;
		while(from != Object.class){		
			if(from==null)
				return false;
			Class[] interfaces = from.getInterfaces();
			if(interfaceWalk(interfaces, interfaceType))
				return true;
			from = from.getSuperclass();
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	private static boolean interfaceWalk(Class[] interfaces, Class<?> interfaceType) {
		boolean flag = false;
		if(interfaces!=null){
			for(Class c : interfaces){
				if(c == interfaceType)
					return true;
				else{
					flag = interfaceWalk(c.getInterfaces(), interfaceType);
					if(flag)
						break;
				}
			}
		}
		return flag;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getMethod(Class clazz, String fieldName, Class fieldType){
		Method method = null;
		try {
			method = clazz.getMethod(fieldName, fieldType);
		} catch (NoSuchMethodException e) {
			if(clazz != Object.class)
				method = getMethod(clazz.getSuperclass(), fieldName, fieldType);
		}
		return method;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getSetterMethod(Class clazz, String fieldName, Class fieldType){
		Method method = null;
		try {
			method = clazz.getMethod("set"+StringUtils.capitalize(fieldName), fieldType);
		} catch (NoSuchMethodException e) {
			if(clazz != Object.class)
				method = getSetterMethod(clazz.getSuperclass(), fieldName, fieldType);
		}
		return method;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getGetterMethod(Class clazz, String fieldName, Class fieldType){
		Method method = null;
		try {
			method = clazz.getMethod("get"+StringUtils.capitalize(fieldName), fieldType);
		} catch (NoSuchMethodException e) {
			if(clazz != Object.class)
				method = getGetterMethod(clazz.getSuperclass(), fieldName, fieldType);
		}
		return method;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getGetterMethod(Class clazz, String fieldName){
		Method method = null;
		try {
			method = clazz.getMethod("get"+StringUtils.capitalize(fieldName));
		} catch (NoSuchMethodException e) {
			if(clazz != Object.class)
				method = getGetterMethod(clazz.getSuperclass(), fieldName);
		}
		return method;
	}
	
	
	public static Map<String, Object> readAnnotation(Annotation annotation){
		
		Method[] metodos = annotation.getClass().getDeclaredMethods();
		final Map<String, Object> values = new HashMap<String, Object>();
		try{
			for (Method method : metodos) {
				String name = method.getName();
				if(name.equals("equals") || name.equals("getClass") || name.equals("wait")  || name.equals("hashCode")  
						|| name.equals("toString") || name.equals("notify") || name.equals("notifyAll") || name.equals("annotationType"))
					continue;
				
				Object obj = method.invoke(annotation);
				
				if(obj instanceof Annotation)
					values.put(name, readAnnotation((Annotation)obj));
				else if(obj instanceof Object[]){
					int x = 1;
					final Map<String, Object> vls = new HashMap<String, Object>(((Object[])obj).length);
					for(Object o : (Object[])obj){
						if(o instanceof Annotation)
							vls.put(name+x++, readAnnotation((Annotation)o));
						else{
							vls.put(name+x++, o);
						}
					}
					values.put(name, vls);
				}else
					values.put(name, obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return values;
		
	}
	
	public static Method getConverterMethod(final Annotation annotation) {
		try {
			return annotation.annotationType().getMethod("converter");
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}
	
	public static TPresenter getTPresenter(final Annotation annotation) {
		try {
			Method method = annotation.annotationType().getMethod("presenter");
			if(method!=null){
				return (TPresenter) method.invoke(annotation);
			}
		} catch (NoSuchMethodException e) {
			return null;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static TConverter getConverter(final Annotation annotation) {
		
		try {
			Method method = getConverterMethod(annotation);
			if(method!=null){
				org.tedros.fx.annotation.control.TConverter tConverter =  (org.tedros.fx.annotation.control.TConverter) method.invoke(annotation);
				if(tConverter.parse()){
					Class<? extends TConverter> clazz = (Class) tConverter.type();
					return (TConverter) clazz.getConstructor().newInstance();
				}
				
			}
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/*
	public static Object getIntanciaParametroGenerico(Class<? extends Object> classe, int indiceParametro) {
		Object instancia = null;
		try {
			instancia = getGenericParamClass(classe, indiceParametro).newInstance();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return instancia;
	}*/
	
	public static List<Annotation> getEffectAnnotations(List<Annotation> list){
		List<Annotation> ret = new ArrayList<>(0);
		for (Annotation annotation : list) {
			if( getAnnotationFullName(annotation).contains(ANNOTATION_EFFECT_PACKAGE)){
				ret.add(annotation);
			}
		}
		return ret;
	}
	
	/*public static TNode getNodeAnnotation(List<Annotation> list){
		for (Annotation annotation : list) {
			if(annotation instanceof TNode){
				return (TNode) annotation;
			}
		}
		return null;
	}
	
	public static TControl getControlAnnotation(List<Annotation> list){
		for (Annotation annotation : list) {
			if(annotation instanceof TControl){
				return (TControl) annotation;
			}
		}
		return null;
	}*/
	
	public static Object[] getViewBuilder(List<Annotation> list) {
		return getBuilder(list, ITViewBuilder.class);
	}
	
	public static Object[] getReaderBuilder(List<Annotation> list) {
		return getBuilder(list, ITReaderBuilder.class);
	}
	
	public static Object[] getReaderHtmlBuilder(List<Annotation> list) {
		return getBuilder(list, ITReaderHtmlBuilder.class);
	}
	
	public static Object[] getControlBuilder(List<Annotation> list) {
		return getBuilder(list, ITControlBuilder.class, ITFileBuilder.class);
	}
	
	public static Object[] getLayoutBuilder(List<Annotation> list) {
		return getBuilder(list, ITLayoutBuilder.class);
	}
	
	@SuppressWarnings({"rawtypes"})
	private static Object[] getBuilder(List<Annotation> list, Class... builderInterface) {
		for (Annotation annotation : list) {
			final Method method = getAnnotationBuilderMethod(annotation);
			if(method != null){
				try{
					Class clazz = (Class) method.invoke(annotation);
					if(isImplemented(clazz, builderInterface)){
						try{
							return new Object[]{annotation, clazz.newInstance()};
						}catch( InstantiationException e){
							//System.err.println("ERROR: The class "+clazz.getSimpleName()+" must implement the 'public static "+clazz.getSimpleName()+" getInstance()'");
							e.printStackTrace();
						}
						
					}
				}catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private static Method getAnnotationBuilderMethod(Annotation annotation) {
		try{
			return annotation.getClass().getMethod("builder");
		}catch(NoSuchMethodException e){ 
			return null;
		}
		
	}
	
	public static Method getMethod(final Annotation annotation, String methodName) {
		try {
			return annotation.annotationType().getMethod(methodName);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Object obj, Method m){
		if(m!=null){
			try {
				return (T) m.invoke(obj);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static <T> T getValue(Annotation annotation, String methodName){
		return getValue(annotation, getMethod(annotation, methodName));
		
	}
	
	public static Method getParserMethod(final Annotation annotation) {
		try {
			return annotation.annotationType().getMethod("parser");
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	public static Method getParseMethod(final Annotation annotation) {
		try {
			return annotation.annotationType().getMethod("parse");
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public static String getAnnotationFullName(Annotation annotation) {
		try{
			if(Proxy.isProxyClass(annotation.getClass()))
				return StringUtils.substringBetween(annotation.toString(), "@", "(");
			else
				return annotation.getClass().getPackage().getName();
		}catch(NullPointerException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public static String getAnnotationName(Annotation annotation) {
		return getAnnotationName(getAnnotationFullName(annotation));
	}
	
	public static String getAnnotationName(String fullName) {
		return StringUtils.substringAfterLast(fullName, ".");
	}
	
	public static String getAnnotationPackage(String fullName) {
		return StringUtils.substringBeforeLast(fullName, ".");
	}

	/**
	 * Retorna a classe de um par�metro gen�rico de uma determinada classe
	 * 
	 * @param clazz
	 * @param param
	 * @return
	 */
	public static Class<?> getGenericParamClass(Class<? extends Object> clazz, int param) {
		return ((Class<?>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[param]);
	}
	
	
	@SuppressWarnings("rawtypes")
	public static List<ITFieldDescriptor> getFieldDescriptorList(ITModelView model){
		List<ITFieldDescriptor> fieldsList = new ArrayList<>();
		List<String> k = new ArrayList<>();
		Class target = ITModelView.class;
		Class superClass = model.getClass();
		
		while(TReflectionUtil.isImplemented(superClass, target)){
			if(!superClass.equals(TModelView.class)) {
				Arrays.asList(superClass.getDeclaredFields()).stream()
				.forEach(f->{
					if(!k.contains(f.getName()))
						try {
							fieldsList.add(new TFieldDescriptor(f));
							k.add(f.getName());
						} catch (Throwable e) {
							e.printStackTrace();
						}
				});
			}
			superClass = superClass.getSuperclass();
		}
		return fieldsList;
	}
	
	
	
}
