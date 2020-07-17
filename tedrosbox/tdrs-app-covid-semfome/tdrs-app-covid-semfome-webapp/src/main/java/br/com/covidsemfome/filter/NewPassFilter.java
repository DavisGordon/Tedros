/**
 * 
 */
package br.com.covidsemfome.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.covidsemfome.ejb.controller.IPessoaController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

import br.com.covidsemfome.ejb.service.ServiceLocator;

/**
 * @author Davis Gordon
 *
 */
@WebFilter(filterName = "NewPassFilter",urlPatterns = {"/defpass/*"})
public class NewPassFilter implements Filter {
	
	private static String PARAM = "k";

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		
	    
		String key = httpRequest.getParameter(PARAM);
		
	    if(key!=null && !key.trim().equals("")){
	    	
		    ServiceLocator loc = ServiceLocator.getInstance();
			
			try {
				IPessoaController serv = loc.lookup("IPessoaControllerRemote");
				TResult<Boolean> res = serv.validateNewPassKey(key.trim());
				loc.close();
				if(res.getResult().equals(EnumResult.SUCESS)){
					chain.doFilter(req, resp);
				}else if(res.getResult().equals(EnumResult.WARNING)){
					((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/WARN1.html");
				}else{
					((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/500.html");
				}
			}catch(Exception e){
				((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/500.html");
			}
	    }else{
	    	((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/WARN1.html");
	    }

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
