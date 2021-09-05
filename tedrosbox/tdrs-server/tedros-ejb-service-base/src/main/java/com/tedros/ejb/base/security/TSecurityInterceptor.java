/**
 * 
 */
package com.tedros.ejb.base.security;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.tedros.ejb.base.controller.ITSecurityController;

/**
 * @author Davis Gordon
 *
 */
@Interceptor
@TRemoteSecurity
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
	            ITSecurity sec = (ITSecurity) target;
	        	ITSecurityController controller = sec.getSecurityController();
	        	
	        	if(!controller.isAccessGranted(token)) {
	        		System.out.println("Metodo interceptado: "+ctx.getMethod().getName());
	        		System.out.println("Permissão negada");
	        		throw new IllegalStateException("Pemission denied!");
	        	}
            }
        }else {
        	throw new IllegalStateException("The bean "+target.getClass().getSimpleName()
        			+" are annotated with @TRemoteSecurity and need to implement the"
        			+ " ITSecurity interface.");
        }
        
        
		return ctx.proceed();
    }
	
}
