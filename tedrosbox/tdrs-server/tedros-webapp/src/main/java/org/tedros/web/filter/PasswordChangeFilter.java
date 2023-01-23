/**
 * 
 */
package org.tedros.web.filter;

import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.tedros.env.ejb.controller.IWebUserController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.web.bean.AppBean;

/**
 * @author Davis Gordon
 *
 */
@WebFilter(filterName = "PasswordChangeFilter",
urlPatterns = {"/pt/changepass.html", "/en/changepass.html" })
public class PasswordChangeFilter implements Filter {
	
	private static String KEY = "k";
	private static String LANG = "l";
	
	@Inject
	private AppBean appBean;
	
	@EJB
	private IWebUserController serv;

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
	    
		String key = httpRequest.getParameter(KEY);
		String lang = httpRequest.getParameter(LANG);
		
	    if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(lang)){
	    	try {
				TResult<Boolean> res = serv.isValidationCodeEnabled(appBean.getToken(), key.trim());
				if(res.getState().equals(TState.SUCCESS)){
					if(res.getValue())
						chain.doFilter(req, resp);
					else
						((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/" + lang +"/WARN1.html");
				}else{
					((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/" + lang + "/500.html");
				}
			}catch(Exception e){
				((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/" + lang + "/500.html");
			}
	    }else{
	    	((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/" + lang + "/WARN1.html");
	    }

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
