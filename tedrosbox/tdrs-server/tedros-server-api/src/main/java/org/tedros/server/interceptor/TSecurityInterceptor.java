/**
 * 
 */
package org.tedros.server.interceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TActionPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TMethodPolicie;
import org.tedros.server.security.TMethodSecurity;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

/**
 * @author Davis Gordon
 *
 */
@Interceptor
@org.tedros.server.security.TSecurityInterceptor
public class TSecurityInterceptor implements Serializable {

	private static final long serialVersionUID = 5509327184619426296L;

	@AroundInvoke
    public Object methodEntry(InvocationContext ctx) throws Exception {
		Object target = ctx.getTarget();
        if(target instanceof ITSecurity ) {
        	TAccessToken token = null;
        	Object[] arr = ctx.getParameters();
            if(arr!=null)
            	for(Object o : arr)
            		if(o instanceof TAccessToken) {
            			token = (TAccessToken) o;
            			break;
            		}
            
            if(token!=null) {
            	String beanName = target.getClass().getSimpleName();
            	String methodName = ctx.getMethod().getName();
	            ITSecurity sec = (ITSecurity) target;
	        	ITSecurityController controller = sec.getSecurityController();
	        	
	        	if(!controller.isAccessGranted(token)) {
	        		throwUserNotLogged(beanName, methodName);
	        	}{
	            	TBeanSecurity ann = target.getClass().getAnnotation(TBeanSecurity.class);
	            	
	            	if(ann!=null && ann.value().length>0) {
	            		
	            		TBeanPolicie[] beanPolicies = ann.value();
	            		TMethodSecurity ann1 = ctx.getMethod().getAnnotation(TMethodSecurity.class);
	            		
	            		boolean accessAllowed = false;
	            		List<String> secIds = new ArrayList<>();
	            		String polMsg = "";
	            		
	            		for(TBeanPolicie bp : beanPolicies ) {
			            	TAccessPolicie[] ap = bp.policie();
			            	String id = bp.id();
			            	if(!"".equals(id.trim())) {
			            		secIds.add(id);
				            	String[] l = ArrayUtils.toStringArray(ap);
				            	if(controller.isPolicieAllowed(token, id,  l)) {
				            		accessAllowed = true;
				            		break;
				            	}else
				            		polMsg +=  "".equals(polMsg) 
				            		? "SecurityID: "+id+", policie: "+ArrayUtils.toString(l)
				            		: "; SecurityID: "+id+", policie: "+ArrayUtils.toString(l);
			            	}
	            		}

	            		if(!accessAllowed) {
	            			throwNotAllowed(beanName, methodName, polMsg);
	            			
	            		}else if(ann1!=null && ann1.value().length>0){
	            			
		            		TMethodPolicie[] methodPolicies = ann1.value();
	            			
	            			boolean actionAllowed = false;
	            			polMsg = new String();
            				for(TMethodPolicie mp : methodPolicies ) {
    			            	TActionPolicie[] ap = mp.policie();
    			            	String id = mp.id();
    			            	if(!"".equals(id.trim())) {
    			            		String[] l = ArrayUtils.toStringArray(ap);
    				            	if(controller.isPolicieAllowed(token, id, l))
    				            		actionAllowed = true;
    				            	else
    				            		polMsg +=  "".equals(polMsg) 
    				            			? "SecurityID: "+id+", policie: "+ArrayUtils.toString(l)
    				            				: "; SecurityID: "+id+", policie: "+ArrayUtils.toString(l);
    			            	}else {
    			            		String[] l = ArrayUtils.toStringArray(ap);
    			            		for(String secId : secIds) {
        				            	if(controller.isPolicieAllowed(token, secId, l)) {
        				            		actionAllowed = true;
        				            		break;
        				            	}else
        				            		polMsg +=  "".equals(polMsg) 
        				            			? "SecurityID: "+secId+", policie: "+ArrayUtils.toString(l)
        				            				: "; SecurityID: "+secId+", policie: "+ArrayUtils.toString(l);
    			            		}
    			            	}
    	            		}
            				
            				if(!actionAllowed)
            					throwNotAllowed(beanName, methodName, polMsg);
	            		}
	            	}
	        	}
            }
        }else {
        	throw new IllegalStateException("The bean "+target.getClass().getSimpleName()
        			+" are annotated with @TBeanSecurity but it must implement the"
        			+ " ITSecurity interface.");
        }
        
        
		return ctx.proceed();
    }

	private void throwUserNotLogged(String beanName, String methodName) {
		String msg = "This operation can only be performed by a logged in user.";
		System.out.println("[TBeanSecurity] Bean: "+beanName+", method: "+methodName);
		System.out.println(msg);
		throw new IllegalStateException(msg);
	}

	/**
	 * @param m
	 */
	private void throwNotAllowed(String beanName, String methodName, String policie ) {
		String msg = "The logged in user is not allowed to perform this operation "
				+ "he must have one of these authorization policies: "+policie;
		System.out.println("[TBeanSecurity] Bean: "+beanName+", method: "+methodName);
		System.out.println(msg);
		throw new IllegalStateException(msg);
	}
	
}
