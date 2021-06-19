package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.tedros.core.ITModule;
import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.context.TedrosAppManager;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.util.TReflectionUtil;


/**
 * <pre>
 * An annotation parser engine to parse all properties in an annotation to their reference object. 
 * 
 *	Example:
 *
 *	import <b style='color:green'>javafx.scene.control.TextField</b>;
 *	import <b style='color:blue'>com.tedros.fxapi.annotation.control.TTextField</b>;
 *
 *	public class TTextFieldParser extends TAnnotationParser&lt;<b style='color:blue'>TTextField</b>, <b style='color:green'>TextField</b>&gt; {
 *	
 *		private static TTextFieldParser instance;
 * 	
 *		private TTextFieldParser(){
 *		
 *		}
 *	
 *		public static  TTextFieldParser getInstance(){
 *			if(instance==null)
 *			  instance = new TTextFieldParser();
 *			return instance;	
 *		}
 *	}
 * </pre>
 * @author Davis Gordon
 * */
@SuppressWarnings("rawtypes")
public abstract class TAnnotationParser<A extends Annotation, T> implements ITAnnotationParser<A, T> {
	
	private final static String SET = "set";
	private final static String GET = "get";
	private final String[] SKIPMETHODS = {"builder","parser","parse","equals", "getClass", "wait", "hashCode", "toString", "notify", "notifyAll", "annotationType"};
	
	private TComponentDescriptor componentDescriptor;
	protected TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
	
	/**
	 * <pre>
	 * Return the {@link TComponentDescriptor} in execution.
	 * </pre>
	 * */
	public TComponentDescriptor getComponentDescriptor() {
		return componentDescriptor;
	}
	
	/**
	 * <pre>
	 * Set the {@link TComponentDescriptor} to execution.
	 * </pre>
	 * */
	public void setComponentDescriptor(TComponentDescriptor componentDescriptor) {
		this.componentDescriptor = componentDescriptor;
		
		try{
			if(this.componentDescriptor.getForm()!=null && this.componentDescriptor.getForm().gettPresenter()!=null){
				ITModule module = this.componentDescriptor.getForm().gettPresenter().getModule();
				if(module !=null && TedrosAppManager.getInstance().getModuleContext(module)!=null){
					String uuid = TedrosAppManager.getInstance().getModuleContext(module).getAppContext().getAppDescriptor().getUniversalUniqueIdentifier();
					iEngine.setCurrentUUID(uuid);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * <pre>
	 * Parse the annotation to the given object
	 * 
	 * The last parameter config which propertys can be or not parsed.
	 * 
	 *	Example:
	 *
	 *	// <b style='color:red'>this will parse all properties except "node", "control" and "pane"</b>
	 *
	 *	public void parse(TMaskField annotation, com.tedros.fxapi.control.TMaskField object, String... byPass) throws Exception {
	 *		
	 *		super.parse(annotation, object, <b style='color:red'>"node", "control", "pane"</b>);
	 *	}
	 *	
	 *	// <b style='color:red'>this will parse only the "node" and "control"</b>
	 *
	 *	public void parse(TMaskField annotation, com.tedros.fxapi.control.TMaskField object, String... byPass) throws Exception {
	 *		
	 *		super.parse(annotation, object, <b style='color:red'>"+node", "+control"</b>);
	 *	}
	 *  
	 * 
	 * </pre>
	 * */
	public void parse(final A annotation, final T object, String...byPass) throws Exception{
		
		//long startTime = System.nanoTime();
		
		Annotation defaultSetting = null;
		Map<String, Object> defaultParams = null;
		
		final Map<String, Object> runAfter = new HashMap<String, Object>(0);
			
		/*int x= 0;
		if(annotation instanceof TStringProperty)
			x= 0;*/
		
		List<String> exclusive = new ArrayList<>(0);
		if(byPass!=null && byPass.length>0){
			for(String s : byPass){
				if(s.contains("+"))
					exclusive.add(s.substring(1));
			}
		}
		
		Method[] metodos = annotation.getClass().getDeclaredMethods();
		try{
			for (Method method : metodos) {
				String key = method.getName();
								
				if(ArrayUtils.contains(SKIPMETHODS, key))
					continue;
				
				Object value = method.invoke(annotation);
				
				if(value instanceof Annotation){
					Method parseMethod = TReflectionUtil.getParseMethod((Annotation) value);
					if(parseMethod != null){
						boolean parse = (boolean) parseMethod.invoke(value);
						if(!parse)
							continue;
					}
				}else if(value instanceof Class && (Modifier.isAbstract(((Class)value).getModifiers()) || Modifier.isInterface(((Class)value).getModifiers()))){
					continue;
				}
				
				int y=0; 
				if(key.contains("node") || key.contains("labeled"))
					y=1;
				
				if(byPass!=null && byPass.length>0){
					if(exclusive.size()>0 && !exclusive.contains(key)){
						continue;
					}else if(exclusive.isEmpty() && ArrayUtils.contains(byPass, key)){
						continue;
					}
				}
				
				// �grow needs the pane first  
				if(key.contains("grow")){
					runAfter.put(key, value);
					continue;
				}
				
				if(defaultSetting==null)
					defaultSetting = getDefaultSetting(annotation);
				
				if(defaultParams==null)
					defaultParams = (defaultSetting!=null) ? TReflectionUtil.readAnnotation(defaultSetting) : null;
				
				//long startTime2 = System.nanoTime();
				run(key, annotation, defaultSetting, value, defaultParams, object);
				//long endTime2 = System.nanoTime();
				//long duration2 = (endTime2 - startTime2);
				//if(TDebugConfig.detailParseExecution)
					//System.out.println("[TAnnotationParser][Annotation: "+TReflectionUtil.getAnnotationName(annotation)+"][attribute: "+key+"][Parser duration: "+(duration2/1000000)+"ms, "+(TimeUnit.MILLISECONDS.toSeconds(duration2/1000000))+"s] ");
			}
			
			if(!runAfter.isEmpty()){
				
				if(defaultSetting==null)
					defaultSetting = getDefaultSetting(annotation);
				
				if(defaultParams==null)
					defaultParams = (defaultSetting!=null) ? TReflectionUtil.readAnnotation(defaultSetting) : null;
				
				Iterator entries = runAfter.entrySet().iterator();
				while (entries.hasNext()) {
				  Entry thisEntry = (Entry) entries.next();
				  String key = (String) thisEntry.getKey();
				  Object value = thisEntry.getValue();
				  //long startTime2 = System.nanoTime();
				  run(key, annotation, defaultSetting, value, defaultParams, object);
				  //long endTime2 = System.nanoTime();
				  //long duration2 = (endTime2 - startTime2);
				  //if(TDebugConfig.detailParseExecution)
				//	  System.out.println("[TAnnotationParser][Annotation: "+TReflectionUtil.getAnnotationName(annotation)+"][attribute: "+key+"][Parser duration: "+(duration2/1000000)+"ms, "+(TimeUnit.MILLISECONDS.toSeconds(duration2/1000000))+"s] ");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//long endTime = System.nanoTime();
		//long duration = (endTime - startTime);
		//if(TDebugConfig.detailParseExecution)
		//	System.out.println("[TAnnotationParser][Annotation: "+TReflectionUtil.getAnnotationName(annotation)+"][Parser duration: "+(duration/1000000)+"ms, "+(TimeUnit.MILLISECONDS.toSeconds(duration/1000000))+"s] ");
	}
	
	/**
	 * <pre>
	 * Execute the parser of the given annotation defined in the parser() property. 
	 * </pre>
	 * */
	@SuppressWarnings("unchecked")
	public static void callParser(final Annotation tAnnotation, final Object control, final TComponentDescriptor componentDescriptor) throws Exception {
		Method parserMethod = TReflectionUtil.getParserMethod(tAnnotation);
		if(parserMethod!=null){
			try{
				Object object = parserMethod.invoke(tAnnotation);
				Class<? extends ITAnnotationParser>[] parsers = (object instanceof Class[]) 
						? (Class<? extends ITAnnotationParser>[]) object 
								: new Class[]{(Class<? extends ITAnnotationParser>)object};
						
				for (Class<? extends ITAnnotationParser> clazz : parsers) {
					ITAnnotationParser parser = (ITAnnotationParser) clazz.newInstance();
					parser.setComponentDescriptor(componentDescriptor);
					try{
						parser.parse(tAnnotation, control);
					}catch(ClassCastException e){
						final Method method = getTargetMethod(componentDescriptor.getAnnotationPropertyInExecution(), 
								getGenericParamClass(componentDescriptor.getParserClassInExecution(),1));
						
						Object obj = method.invoke(control);
						if(obj !=null){
							parser.parse(tAnnotation, obj);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	private void run(String key, final A annotation, final Annotation defaultSetting, final Object value, final Map<String, Object> defaultParams, final T object) throws Exception{
		
		componentDescriptor.setAnnotationPropertyInExecution(key);
		componentDescriptor.setParserClassInExecution(getClass());
		
		Object selectedValue = null;
		
		Object defaultValueSetted = (defaultParams!=null) 
				? (defaultParams.get(key) instanceof Map) 
						? getAnnotation(key, defaultSetting) 
								: defaultParams.get(key) 
				: null;
				
		Object valueSetted = (value instanceof Annotation) 
				? getAnnotation(key, annotation) 
						: value;
				
		if(defaultValueSetted instanceof Annotation)
			defaultValueSetted = TTypeAnalyserParserDelegate.parse(defaultValueSetted, componentDescriptor); 
		
		if(valueSetted instanceof Annotation)
			valueSetted = TTypeAnalyserParserDelegate.parse(valueSetted, componentDescriptor);
		
		if(defaultValueSetted!=null){
			
			Object annotDefVal = annotation.annotationType().getMethod(key).getDefaultValue();
			if(annotDefVal instanceof Annotation)
				annotDefVal = TTypeAnalyserParserDelegate.parse(annotDefVal, componentDescriptor);
			
			if(annotDefVal!=null && (defaultValueSetted instanceof Double[]) ? 
					!Arrays.deepEquals((Double[])defaultValueSetted, (Double[]) annotDefVal) 
					: !defaultValueSetted.equals(annotDefVal)){
				selectedValue = defaultValueSetted;
			}
			if((annotDefVal!=null && (valueSetted instanceof Double[]) ? 
					!Arrays.deepEquals((Double[])valueSetted, (Double[])annotDefVal) 
					: !valueSetted.equals(annotDefVal)) || (annotDefVal==null && valueSetted!=null)){
				selectedValue = valueSetted;
			}
		}else{
			Object annotDefVal = annotation.annotationType().getMethod(key).getDefaultValue();
			if(annotDefVal instanceof Annotation)
				annotDefVal = TTypeAnalyserParserDelegate.parse(annotDefVal, componentDescriptor);
			if((annotDefVal!=null && (valueSetted instanceof Double[]) ? 
					!Arrays.deepEquals((Double[])valueSetted, (Double[])annotDefVal) 
					: (valueSetted!=null && !valueSetted.equals(annotDefVal))) || (annotDefVal==null && valueSetted!=null)){
				selectedValue = valueSetted;
			}
		}
				
		if(selectedValue!=null){
			
			if(selectedValue instanceof Annotation){
				/*int x=0;
				if(key.contains("labeled"))
					x=1;*/
				callParser((Annotation) selectedValue, (Object) object, componentDescriptor);
			}else{
				
				if(selectedValue instanceof String)
					selectedValue = iEngine.getString((String) selectedValue);
				
				if(key.equals("tooltip") && selectedValue instanceof String)
					selectedValue = new Tooltip((String)selectedValue);
				
				if(key.equals("textFill") && selectedValue instanceof String)
					selectedValue = Color.web((String)selectedValue);
				
				invokeTargetMethod(annotation, key, object, selectedValue);
			}
		}
	}

	@SuppressWarnings({"unchecked", "null"})
	private void invokeTargetMethod(A annotation, String key, Object targetObject, Object value) {
		/*int x = 0;
		if(key.equals("items"))
			x = 0;*/
		try {
			Class paramClass = getGenericParamClass();
			Class targetClass = paramClass == Object.class ? targetObject.getClass() : paramClass;
			Object targetValue = TTypeAnalyserParserDelegate.parse(value, componentDescriptor);
			final Method method = getTargetMethod(key, targetClass);
			try{
				if(method!=null){
					if(value instanceof Double[])
						method.invoke(targetObject, ((Double[])value)[0], ((Double[])value)[1]);
					else
						method.invoke((T)targetObject,  targetValue);
				}else if(!key.equals(componentDescriptor.getAnnotationPropertyInExecution())){
					final Method method2 = getTargetMethod(componentDescriptor.getAnnotationPropertyInExecution(), targetClass);
					if(method2!=null){
						Object obj = method2.invoke(targetObject);
						if(obj !=null){
							final Method method3 = getTargetMethod(key, targetClass);
							if(method3!=null)
								method.invoke(targetObject, TTypeAnalyserParserDelegate.parse(obj, componentDescriptor));
						}
					}
					
				}
			}catch(Exception e){
				System.out.println("Warning: Error trying parse an annotation.");
				System.out.println("annotation: "+TReflectionUtil.getAnnotationFullName(annotation)+", object: " + targetObject.getClass().getSimpleName()
									+", method: "+method.getName() + ", value: "+value.toString()+", form: "+componentDescriptor.getForm().getClass().getSimpleName());
				System.out.println("Message: "+e.getMessage());
			}
		}catch(Exception e) {
			System.out.println("Warning: Error trying parse an annotation.");
			System.out.println("annotation: "+TReflectionUtil.getAnnotationFullName(annotation)+", object: " + targetObject.getClass().getSimpleName()
								+ ", value: "+value.toString()+", form: "+componentDescriptor.getForm().getClass().getSimpleName());
			System.out.println("Message: "+e.getMessage());
		}
	}
	
	/*private static Method getTargetMethod(String key, Class clazz, Object value){
		
		
		
		Method prop = TReflectionUtil.getMethod(clazz, key, value.getClass());
		Method set = TReflectionUtil.getSetterMethod(clazz, key, value.getClass());
		if(set==null)
			set = TReflectionUtil.getGetterMethod(clazz, key, value.getClass());
		return (set!=null && prop!=null) ? set : ((set!=null) ? set : prop);
	}
	*/
	private static Method getTargetMethod(String key, Class clazz){
		
		
		Method prop = null;
		Method set = null;
		do {
			for(Method m : clazz.getDeclaredMethods()){
				if(m.getName().equals(key)){
					prop = m;
				}
				if(m.getName().equals(SET+StringUtils.capitalize(key))){
					if(key.equals("alignment") && m.getParameterTypes().length>1)
						continue;
					set = m;
					break;
				}
				
				if(m.getName().equals(GET+StringUtils.capitalize(key)))
					set = m;
			}
			clazz = clazz.getSuperclass();
		}while(clazz!=Object.class && (prop==null && set==null));
		
		return (set!=null && prop!=null) ? set : ((set!=null) ? set : prop);
	}
	
	private Annotation getDefaultSetting(A annotation) {
		List<Annotation> typeAnnotations = componentDescriptor.getModelViewAnnotationList();
		String nameToCompare = TReflectionUtil.getAnnotationName(annotation).toLowerCase()+"defaultsetting";
		for (Annotation target : typeAnnotations)
			if(TReflectionUtil.getAnnotationName(target).toLowerCase().equals(nameToCompare))
				return target;
		return null;
	}
	
	private Object getAnnotation(String key, Annotation annotation) throws Exception {
		return annotation.annotationType().getMethod(key).invoke(annotation);
	}
	
	@SuppressWarnings("unchecked")
	private static Class getGenericParamClass(Class clazz, int index) {
		return (Class) TReflectionUtil.getGenericParamClass(clazz, index);
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getGenericParamClass() {
		return getGenericParamClass(getClass(),1);
	}
	
}