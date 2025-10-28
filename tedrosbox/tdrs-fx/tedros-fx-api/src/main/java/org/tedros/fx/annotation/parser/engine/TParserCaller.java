/**
 * 
 */
package org.tedros.fx.annotation.parser.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.parser.ITAnnotationParser;
import org.tedros.fx.util.TReflectionUtil;
import org.tedros.util.TLoggerUtil;

/**
 * The parser caller
 */
public class TParserCaller {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(TParserCaller.class);
	private static String INFO_HEADER = "\n\t[%s][callParser] Field: %s, Annotation(name: %s, proxy: %s), Control: %s";
	/**
	 * <pre>
	 * Execute the parser of the given annotation defined in the parser() property. 
	 * </pre>
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void call(final Annotation tAnnotation, final Object control, final ITComponentDescriptor descriptor, String calledBy) throws Exception {
		
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
							error = new StringBuilder(String.format(INFO_HEADER, calledBy,
									descriptor.getFieldDescriptor().getFieldName(), 
									tAnnotation.annotationType().getSimpleName(), 
									tAnnotation.getClass().getSimpleName(),
									control.getClass().getSimpleName()));
						
						error.append("\n\t\tIssue found on the parser: "+ clazz.getSimpleName());
						error.append("\n\t\tException: "+ e.getMessage());
						try {
							
							//Class genParamClass = TReflectionUtil.getGenericParamClass(clazz,1);
							final Method method = getSourceMethod(descriptor.getAnnotationPropertyInExecution(), control.getClass());
							if(method!=null) {
								error.append("\n\t\tMethod method = getSourceMethod("+descriptor.getAnnotationPropertyInExecution()+","+control.getClass().getSimpleName()+")");
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
							}else{
								error.append("\n\t\tMethod method = getSourceMethod("+descriptor.getAnnotationPropertyInExecution()+","+control.getClass().getSimpleName()+")");
								error.append("\n\t\tCan't find the get method to the property");
							}
						}catch (Exception ex) { 
							error.append("\n\t\tFail message: "+ex.getMessage());
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
	
	@SuppressWarnings("rawtypes")
	private static Method getSourceMethod(String key, Class clazz){
		
		Method prop = null;
		Method get = null;
		do {
			for(Method m : clazz.getDeclaredMethods()){
				if(m.getName().equals(key))
					prop = m;
				
				if(m.getName().equals(TReflectionUtil.GET+StringUtils.capitalize(key)))
					get = m;
			}
			clazz = clazz.getSuperclass();
		}while(clazz!=Object.class && (prop==null && get==null));
		
		return (get!=null && prop!=null) ? get : ((get!=null) ? get : prop);
	}

}
