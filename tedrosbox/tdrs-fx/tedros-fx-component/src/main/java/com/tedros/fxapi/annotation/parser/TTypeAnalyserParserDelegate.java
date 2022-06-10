package com.tedros.fxapi.annotation.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.tedros.fxapi.annotation.TCursor;
import com.tedros.fxapi.annotation.TDepthTest;
import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.annotation.listener.TInvalidationListener;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.builder.ITEventHandlerBuilder;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.ITNodeBuilder;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.domain.TBlendMode;
import com.tedros.fxapi.domain.TPoint3D;
import com.tedros.fxapi.form.TConverter;
import com.tedros.fxapi.form.TFontBuilder;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.util.Callback;

public class TTypeAnalyserParserDelegate {
	
	@SuppressWarnings("rawtypes")
	public final static Object parse(Object obj, TComponentDescriptor descriptor) throws Exception{
		
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
			ITNodeBuilder builder = (ITNodeBuilder) ((Class)obj).newInstance();
			return builder.build();
		}
		
		if(obj instanceof Class && TReflectionUtil.isImplemented((Class)obj, ITEventHandlerBuilder.class)){
			if(((Class)obj)==ITEventHandlerBuilder.class)
				return null;
			ITEventHandlerBuilder builder = (ITEventHandlerBuilder) ((Class)obj).newInstance();
			builder.setComponentDescriptor(descriptor);
			return builder.build();
		}
		
		if(obj instanceof Class && TReflectionUtil.isImplemented((Class)obj, ITGenericBuilder.class)){
			if(((Class)obj)==ITGenericBuilder.class)
				return null;
			ITGenericBuilder builder = (ITGenericBuilder) ((Class)obj).newInstance();
			builder.setComponentDescriptor(descriptor);
			return builder.build();
		}
		
		if(obj instanceof Class && TReflectionUtil.isTypeOf((Class)obj, TChangeListener.class)){
			if(((Class)obj)==TChangeListener.class)
				return null;
			TChangeListener listener = (TChangeListener) ((Class)obj).newInstance();
			listener.setComponentDescriptor(descriptor);
			return listener;
		}
		
		if(obj instanceof Class && TReflectionUtil.isTypeOf((Class)obj, TInvalidationListener.class)){
			if(((Class)obj)==TInvalidationListener.class)
				return null;
			TInvalidationListener listener = (TInvalidationListener) ((Class)obj).newInstance();
			listener.setComponentDescriptor(descriptor);
			return listener;
		}
		
		if(obj instanceof Class && TReflectionUtil.isImplemented((Class)obj, Callback.class)){
			if(((Class)obj)==Callback.class)
				return null;
			Callback callback = (Callback) ((Class)obj).newInstance();
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
				Object parserInstance = clazz.newInstance();
				return ((ITBaseParser)parserInstance).parse(annotation);
			}
		}
		
		Method converterMethod = TReflectionUtil.getConverterMethod(annotation);
		if(converterMethod!=null){
		
			final Object obj = converterMethod.invoke(annotation);
			Class clazz = (Class) obj;
			if(TReflectionUtil.isTypeOf(clazz, TConverter.class)){
				TConverter converter = (TConverter) clazz.newInstance();
				converter.setIn(annotation);
				return converter.getOut();
			}
		}
		
		return null;
	}

}
