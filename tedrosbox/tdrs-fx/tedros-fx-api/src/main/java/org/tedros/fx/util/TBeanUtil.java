/**
 * 
 */
package org.tedros.fx.util;

import java.lang.reflect.Field;
import java.util.Set;

import org.reflections.ReflectionUtils;
import org.tedros.server.model.ITModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Davis Gordon
 *
 */
public class TBeanUtil {

	/**
	 * 
	 */
	private TBeanUtil() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends ITModel> void copyChanges(T source, T target) {
		Set<Field> fields = ReflectionUtils.getAllFields(source.getClass());
		for(Field f : fields) {
			f.setAccessible(true);
			if(f.isAnnotationPresent(JsonIgnore.class))
				continue;
			try {
				Object srcVal = f.get(source);
				Object tgtVal = f.get(target);
				if(srcVal!=null && tgtVal!=null) {
					/*if(srcVal instanceof Collection) {
						Collection col = (Collection) srcVal;
						
					}else*/
					if(srcVal instanceof ITModel) {
						copyChanges((ITModel) srcVal, (ITModel) tgtVal);
					}else {
						if(!srcVal.equals(tgtVal))
							f.set(target, srcVal);
					}
				}else
					if(srcVal!=null && tgtVal==null) {
						if(srcVal instanceof ITModel) {
							copyChanges((ITModel) srcVal, (ITModel) srcVal.getClass().getDeclaredConstructor().newInstance());
						}else {
							f.set(target, srcVal);
						}
					}else 
						if(srcVal==null && tgtVal!=null) {
						f.set(target, srcVal);
					}
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

}
