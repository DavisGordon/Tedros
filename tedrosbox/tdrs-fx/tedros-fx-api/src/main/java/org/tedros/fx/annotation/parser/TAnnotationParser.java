package org.tedros.fx.annotation.parser;

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
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.core.ITModule;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.fx.descriptor.TComponentDescriptor;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.util.TLoggerUtil;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;


/**
 * <pre>
 * An annotation parser engine to parse all properties in an annotation to their reference object. 
 * 
 *	Example:
 *
 *	import <b style='color:green'>javafx.scene.control.TextField</b>;
 *	import <b style='color:blue'>org.tedros.fx.annotation.control.TTextField</b>;
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
	
	private final static Logger LOGGER = TLoggerUtil.getLogger(TAnnotationParser.class);
	
	private final static String SET = "set";
	private final static String GET = "get";
	
	private ITComponentDescriptor componentDescriptor;
	protected TLanguage iEngine = TLanguage.getInstance(null);
	
	/**
	 * <pre>
	 * Return the {@link TComponentDescriptor} in execution.
	 * </pre>
	 * */
	public ITComponentDescriptor getComponentDescriptor() {
		return componentDescriptor;
	}
	
	/**
	 * <pre>
	 * Set the {@link TComponentDescriptor} to execution.
	 * </pre>
	 * */
	public void setComponentDescriptor(ITComponentDescriptor componentDescriptor) {
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
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private class TInner<I>{
		I value = null;
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
	 *	public void parse(TMaskField annotation, org.tedros.fx.control.TMaskField object, String... byPass) throws Exception {
	 *		
	 *		super.parse(annotation, object, <b style='color:red'>"node", "control", "pane"</b>);
	 *	}
	 *	
	 *	// <b style='color:red'>this will parse only the "node" and "control"</b>
	 *
	 *	public void parse(TMaskField annotation, org.tedros.fx.control.TMaskField object, String... byPass) throws Exception {
	 *		
	 *		super.parse(annotation, object, <b style='color:red'>"+node", "+control"</b>);
	 *	}
	 *  
	 *  
	 * 
	 * </pre>
	 * */
	public void parse(final A annotation, final T object, String...byPass) throws Exception{
		if(TLoggerUtil.isParserDebugEnabled()) {
			TLoggerUtil.splitDebugLine(TAnnotationParser.class, '.');
			TLoggerUtil.timeComplexity(TAnnotationParser.class, "[Parse] "+annotation.annotationType().getName()+", Object: "+object.getClass().getName()+", byPass: "+Arrays.toString(byPass), 
			()->{
				try {
					exec(annotation, object, byPass);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			});
		}else
			exec(annotation, object, byPass);
	}
	
	
	private void exec(final A annotation, final T object, String...byPass) throws Exception{
			
		TInner<Annotation> defaultSetting = new TInner<>();
		TInner<Map<String, Object>> defaultParams = new TInner<>();
		
		final Map<String, Object> runAfter = new HashMap<String, Object>(0);
		
		List<String> exclusive = new ArrayList<>(0);
		if(byPass!=null && byPass.length>0){
			for(String s : byPass){
				if(s.contains("+"))
					exclusive.add(s.substring(1));
			}
		}
	
		Stream<Method> metodos = Stream.of(annotation.getClass().getDeclaredMethods());
		try{
			//metodos.parallel().forEach(method-> {
			metodos.forEach(method-> {
				String key = method.getName();
						
				if(!ArrayUtils.contains(TReflectionUtil.SKIPMETHODS, key)) {
					
					try {
						Object value = method.invoke(annotation);
						boolean skip = false;
						
						if(TLoggerUtil.isParserDebugEnabled())
							TLoggerUtil.debug(TAnnotationParser.class, key+" = "+value.toString());
						
						if(value instanceof Annotation){
							Method parseMethod = TReflectionUtil.getParseMethod((Annotation) value);
							if(parseMethod != null){
								boolean parse = (boolean) parseMethod.invoke(value);
								if(!parse)
									skip = true;;
							}
						}else if(value instanceof Class && (Modifier.isAbstract(((Class)value).getModifiers()) || Modifier.isInterface(((Class)value).getModifiers()))){
							skip = true;
						}
						
						if(!skip) {
						
							if(byPass!=null && byPass.length>0){
								if(exclusive.size()>0 && !exclusive.contains(key)){
									skip = true;
								}else if(exclusive.isEmpty() && ArrayUtils.contains(byPass, key)){
									skip = true;
								}
							}
							if(!skip) {
								// the pane.childs must be parsed
								if(key.toLowerCase().contains("grow") 
										|| key.toLowerCase().contains("margin") 
										|| key.toLowerCase().contains("alignment")){
									runAfter.put(key, value);
									skip = true;
								}
								if(!skip) {
									if(defaultSetting.value==null)
										defaultSetting.value = getDefaultSetting(annotation);
									
									if(defaultParams.value==null)
										defaultParams.value = (defaultSetting.value!=null) ? TReflectionUtil.readAnnotation(defaultSetting.value) : null;
									
									if(TLoggerUtil.isParserDebugEnabled())
										TLoggerUtil.timeComplexity(TAnnotationParser.class, 
											"run("+key+","+ annotation.annotationType().getName()+","+ 
													(defaultSetting.value!=null ? defaultSetting.value : "null")+","+value+","+
													(defaultParams.value!=null?defaultParams.value:"null")+","+object+")",
											()->{
												try {
													run(key, annotation, defaultSetting.value, value, defaultParams.value, object);
												} catch (Exception e) {
													LOGGER.error(e.getMessage(), e);
												}
											});
									else
										try {
											run(key, annotation, defaultSetting.value, value, defaultParams.value, object);
										} catch (Exception e) {
											LOGGER.error(e.getMessage(), e);
										}
								}
							}
						}else
							if(TLoggerUtil.isParserDebugEnabled())
								TLoggerUtil.debug(TAnnotationParser.class, key+" skipped");
						
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			});
			
			if(!runAfter.isEmpty()){
				
				if(defaultSetting.value==null)
					defaultSetting.value = getDefaultSetting(annotation);
				
				if(defaultParams.value==null)
					defaultParams.value = (defaultSetting.value!=null) ? TReflectionUtil.readAnnotation(defaultSetting.value) : null;
				
				Iterator entries = runAfter.entrySet().iterator();
				while (entries.hasNext()) {
				  Entry thisEntry = (Entry) entries.next();
				  String key = (String) thisEntry.getKey();
				  Object value = thisEntry.getValue();
				  if(TLoggerUtil.isParserDebugEnabled())
					  TLoggerUtil.timeComplexity(TAnnotationParser.class, 
						"run("+key+","+ annotation.annotationType().getName()+","+ 
								(defaultSetting.value!=null ? defaultSetting.value : "null")+","+value+","+
								(defaultParams.value!=null?defaultParams.value:"null")+","+object+")",
						()->{
							try {
								run(key, annotation, defaultSetting.value, value, defaultParams.value, object);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
				  else
					  try {
							run(key, annotation, defaultSetting.value, value, defaultParams.value, object);
						} catch (Exception e) {
							LOGGER.error(e.getMessage(), e);
						}
				}
			}
			
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		
	}
	
	
	private static String INFO_HEADER = "\n\t[TAnnotationParser][callParser] Field: %s, Annotation(name: %s, proxy: %s), Control: %s";
	
	/**
	 * <pre>
	 * Execute the parser of the given annotation defined in the parser() property. 
	 * </pre>
	 * */
	@SuppressWarnings("unchecked")
	public static void callParser(final Annotation tAnnotation, final Object control, final ITComponentDescriptor descriptor) throws Exception {
		
		Method parserMethod = TReflectionUtil.getParserMethod(tAnnotation);
		if(parserMethod!=null){
			StringBuilder error = null;
			try{
				Object object = parserMethod.invoke(tAnnotation);
				Class<? extends ITAnnotationParser>[] parsers = (object instanceof Class[]) 
						? (Class<? extends ITAnnotationParser>[]) object 
								: new Class[]{(Class<? extends ITAnnotationParser>)object};
				
				for (Class<? extends ITAnnotationParser> clazz : parsers) {
					
					ITAnnotationParser parser = (ITAnnotationParser) clazz.getDeclaredConstructor().newInstance();
					parser.setComponentDescriptor(descriptor);
					
					try{
					
						parser.parse(tAnnotation, control);
					
					}catch(ClassCastException e){
						
						if(error==null)
							error = new StringBuilder(String.format(INFO_HEADER, 
									descriptor.getFieldDescriptor().getFieldName(), 
									tAnnotation.annotationType().getSimpleName(), 
									tAnnotation.getClass().getSimpleName(),
									control.getClass().getSimpleName()));
						
						error.append("\n\t\tIssue found on the parser: "+ clazz.getSimpleName());
						error.append("\n\t\tException: "+ e.getMessage());
						try {
							
							Class genParamClass = getGenericParamClass(clazz,1);
							final Method method = getSourceMethod(descriptor.getAnnotationPropertyInExecution(), control.getClass());
							
							error.append("\n\t\tMethod method = getSourceMethod("+descriptor.getAnnotationPropertyInExecution()+","+genParamClass.getSimpleName()+")");
							error.append("\n\t\tWhere method is "+method.getName());
							error.append("\n\t\tExecuting method.invoke("+control.getClass().getName()+") to get the parser param!");
							Object obj = method.invoke(control);
							error.append("\n\t\tObject returned: "+obj.getClass().getSimpleName());
							if(obj !=null) {
								error.append("\n\t\t\tTrying to parser again: parser.parse("+tAnnotation.annotationType().getSimpleName()+","+obj.getClass().getSimpleName()+")");
								error.append("\n\t\t\tWhere parser is "+parser.getClass().getSimpleName());
								parser.parse(tAnnotation, obj);
								error.append("\n\t\t\tParser executed successfully!");
							}
						}catch (Exception ex) { 
							error.append("\nFail message: "+ex.getMessage());
						}
					}
				}
			}catch(Exception e){
				if(error==null)
					error = new StringBuilder();
				error.append("\nError: "+e.getMessage());
			}	
			if(error!=null)
				LOGGER.error(error.toString());
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
		
		Class paramClass = getGenericParamClass();
		Class targetClass = paramClass == Object.class ? targetObject.getClass() : paramClass;
			
		try {
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
				LOGGER.warn("Warning: Error trying parse the annotation "+ TReflectionUtil.getAnnotationFullName(annotation));
				LOGGER.warn("parser: "+this.getClass().getSimpleName()
					+", attribute: "+key
					+", object: " + targetObject.getClass().getSimpleName()
					+", target method: "+targetClass.getSimpleName()+"."+method.getName() 
					+", value: "+targetValue.toString()
					+", form: "+componentDescriptor.getForm().getClass().getSimpleName()
					+", modelView: "+componentDescriptor.getModelView().getClass().getSimpleName()
					+", field: "+componentDescriptor.getFieldDescriptor().getFieldName());
				LOGGER.error(e.getMessage());
				
			}
		}catch(Exception e) {
			LOGGER.warn("Warning: Error trying analyse a parse annotation to get the value.");
			LOGGER.warn("annotation: "+TReflectionUtil.getAnnotationFullName(annotation)
				+", parser: "+this.getClass().getSimpleName()
				+", attribute: "+key
				+", object: " + targetObject.getClass().getSimpleName()
				+", target class: "+targetClass.getSimpleName()
				+", value: "+value.toString()
				+", form: "+componentDescriptor.getForm().getClass().getSimpleName()
				+", modelView: "+componentDescriptor.getModelView().getClass().getSimpleName()
				+", field: "+componentDescriptor.getFieldDescriptor().getFieldName());
			LOGGER.error(e.getMessage());
		}
	}
	
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
	
	private static Method getSourceMethod(String key, Class clazz){
		
		Method prop = null;
		Method get = null;
		do {
			for(Method m : clazz.getDeclaredMethods()){
				if(m.getName().equals(key))
					prop = m;
				
				if(m.getName().equals(GET+StringUtils.capitalize(key)))
					get = m;
			}
			clazz = clazz.getSuperclass();
		}while(clazz!=Object.class && (prop==null && get==null));
		
		return (get!=null && prop!=null) ? get : ((get!=null) ? get : prop);
	}
	
	private Annotation getDefaultSetting(A annotation) {
		List<Annotation> typeAnnotations = componentDescriptor.getModelViewAnnotationList();
		String nameToCompare = TReflectionUtil.getAnnotationName(annotation).toLowerCase()+"defaultsetting";
		if(typeAnnotations!=null)
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