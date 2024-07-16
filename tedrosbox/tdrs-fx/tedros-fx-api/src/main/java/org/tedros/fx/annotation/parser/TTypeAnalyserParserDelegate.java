package org.tedros.fx.annotation.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.fx.annotation.TCursor;
import org.tedros.fx.annotation.TDepthTest;
import org.tedros.fx.annotation.listener.TChangeListener;
import org.tedros.fx.annotation.listener.TInvalidationListener;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.builder.ITEventHandlerBuilder;
import org.tedros.fx.builder.ITGenericBuilder;
import org.tedros.fx.builder.ITNodeBuilder;
import org.tedros.fx.converter.TConverter;
import org.tedros.fx.domain.TBlendMode;
import org.tedros.fx.domain.TPoint3D;
import org.tedros.fx.form.TFontBuilder;
import org.tedros.fx.util.TReflectionUtil;

import javafx.util.Callback;

public class TTypeAnalyserParserDelegate {
	
	@SuppressWarnings("rawtypes")
	public final static Object parse(Object obj, ITComponentDescriptor descriptor) throws Exception{
		
		if(obj instanceof Annotation){
			return delegate((Annotation)obj);
		}
		
		if(obj instanceof Class && (Modifier.isAbstract(((Class)obj).getModifiers()) || Modifier.isInterface(((Class)obj).getModifiers()))){
			return null;
		}
				
		if(obj instanceof TCursor){
			return ((TCursor)obj).getValue();
		}
		
		if(obj instanceof TBlendMode){
			return ((TBlendMode)obj).getValue();
		}
		
		if(obj instanceof TDepthTest){
			return ((TDepthTest)obj).getValue();
		}
		
		if(obj instanceof TPoint3D){
			return ((TPoint3D)obj).getValue();
		}
				
		if(obj instanceof Class && TReflectionUtil.isImplemented((Class)obj, ITNodeBuilder.class)){
			if( ((Class)obj)==ITNodeBuilder.class)
				return null;
			ITNodeBuilder builder = (ITNodeBuilder) ((Class)obj).getDeclaredConstructor().newInstance();
			return builder.build();
		}
		
		if(obj instanceof Class && TReflectionUtil.isImplemented((Class)obj, ITEventHandlerBuilder.class)){
			if(((Class)obj)==ITEventHandlerBuilder.class)
				return null;
			ITEventHandlerBuilder builder = (ITEventHandlerBuilder) ((Class)obj).getDeclaredConstructor().newInstance();
			builder.setComponentDescriptor(descriptor);
			return builder.build();
		}
		
		if(obj instanceof Class && TReflectionUtil.isImplemented((Class)obj, ITGenericBuilder.class)){
			if(((Class)obj)==ITGenericBuilder.class)
				return null;
			ITGenericBuilder builder = (ITGenericBuilder) ((Class)obj).getDeclaredConstructor().newInstance();
			builder.setComponentDescriptor(descriptor);
			return builder.build();
		}
		
		if(obj instanceof Class && TReflectionUtil.isTypeOf((Class)obj, TChangeListener.class)){
			if(((Class)obj)==TChangeListener.class)
				return null;
			TChangeListener listener = (TChangeListener) ((Class)obj).getDeclaredConstructor().newInstance();
			listener.setComponentDescriptor(descriptor);
			return listener;
		}
		
		if(obj instanceof Class && TReflectionUtil.isTypeOf((Class)obj, TInvalidationListener.class)){
			if(((Class)obj)==TInvalidationListener.class)
				return null;
			TInvalidationListener listener = (TInvalidationListener) ((Class)obj).getDeclaredConstructor().newInstance();
			listener.setComponentDescriptor(descriptor);
			return listener;
		}
		
		if(obj instanceof Class && TReflectionUtil.isImplemented((Class)obj, Callback.class)){
			if(((Class)obj)==Callback.class)
				return null;
			Callback callback = (Callback) ((Class)obj).getDeclaredConstructor().newInstance();
			return callback;
		}
		
		return obj;
		
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	private static Object delegate(Annotation annotation) throws Exception{
		
		if(annotation instanceof TFont){
			return TFontBuilder.build((TFont)annotation);
		}
		
		Method parserMethod = TReflectionUtil.getParserMethod(annotation);
		if(parserMethod!=null){
			
			final Object obj = parserMethod.invoke(annotation);
			
			if(obj instanceof Class[])
				return annotation;
				
			Class clazz = (Class) obj;
			if(TReflectionUtil.isImplemented(clazz, ITBaseParser.class)){
				Object parserInstance = clazz.getDeclaredConstructor().newInstance();
				return ((ITBaseParser)parserInstance).parse(annotation);
			}
		}
		
		Method converterMethod = TReflectionUtil.getConverterMethod(annotation);
		if(converterMethod!=null){
		
			final Object obj = converterMethod.invoke(annotation);
			Class clazz = (Class) obj;
			if(TReflectionUtil.isTypeOf(clazz, TConverter.class)){
				TConverter converter = (TConverter) clazz.getDeclaredConstructor().newInstance();
				converter.setIn(annotation);
				return converter.getOut();
			}
		}
		
		return null;
	}

}
