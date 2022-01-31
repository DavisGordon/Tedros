/**
 * 
 */
package org.somos.web.filter;

import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.somos.ejb.controller.IPessoaController;
import org.somos.web.bean.AppBean;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
@WebFilter(filterName = "NewPassFilter",urlPatterns = {"/defpass/*"})
public class NewPassFilter implements Filter {
	
	private static String PARAM = "k";
	
	@Inject
	private AppBean appBean;
	
	@EJB
	private IPessoaController serv;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

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
	    				
			try {
				TResult<Boolean> res = serv.validateNewPassKey(appBean.getToken(), key.trim());
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

	}

}
