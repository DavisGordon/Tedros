/**
 * 
 */
package org.tedros.web.filter;

import java.io.IOException;
import java.util.Locale;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tedros.env.ejb.controller.IWebSessionController;
import org.tedros.env.entity.WebSession;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.web.bean.AppBean;
import org.tedros.web.bean.WebLanguageBean;
import org.tedros.web.bean.WebSessionBean;

/**
 * @author Davis Gordon
 *
 */
@WebFilter(filterName = "AuthFilter",
urlPatterns = {"/en/cstmr/index.html*", "/pt/cstmr/index.html*"})
public class AuthFilter implements Filter {
	
	private static final String TOKEN = "tdrstoken";

	@Inject
	protected AppBean appBean;

	@Inject @Any
	private WebLanguageBean lang;
	
	@Inject @Any
	private WebSessionBean session;
	
	@EJB
	private IWebSessionController serv;
	
	private static String KEY = "c";

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) req;
	
		String key = httpRequest.getParameter(KEY);
		if(key!=null && !key.trim().equals("")){
			Locale locale = lang.get();
	    	try {
				TResult<Boolean> res = serv.isActive(appBean.getToken(), locale, key.trim());
				
				if(res.getState().equals(TState.SUCCESS)){
					if(res.getValue()){
						String pCookieName = TOKEN;
				        Cookie cookie = new Cookie(pCookieName, key);
				        cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
				        cookie.setHttpOnly(false);
				        cookie.setPath("/");
						((HttpServletResponse)resp).addCookie(cookie);
				    	
						WebSession e = new WebSession();
						e.setKey(key.trim());
						TResult<WebSession> res2 = serv.find(appBean.getToken(), e);
						e = res2.getValue();
						session.get().setKey(key);
						session.get().setId(e.getId());
						session.get().setUser(e.getUser());
						
						chain.doFilter(req, resp);
					}
				}else{
					((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/500.html");
				}
			}catch(Exception e){
				((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/500.html");
			}
	    }else
	    	chain.doFilter(req, resp);

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
