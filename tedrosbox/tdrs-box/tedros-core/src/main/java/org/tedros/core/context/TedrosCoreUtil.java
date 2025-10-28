package org.tedros.core.context;

/**
 * Tedros core utility
 * */
public final class TedrosCoreUtil {

	/**
	 * Return true if the class or the super class implements the interface
	 * */
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
		if(interfaces!=null){
			for(Class c : interfaces){
				if(c == interfaceType)
					return true;
				else{
					return interfaceWalk(c.getInterfaces(), interfaceType);
				}
			}
		}
		return false;
	}
	
}
