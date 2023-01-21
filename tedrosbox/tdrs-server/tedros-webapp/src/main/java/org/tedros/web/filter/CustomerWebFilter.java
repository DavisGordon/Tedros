/**
 * 
 */
package org.tedros.web.filter;

import java.io.IOException;

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

import org.tedros.env.domain.WebUserType;
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
@WebFilter(filterName = "CustomerWebFilter",
urlPatterns = {"/cstmr/*", "/api/cstmr/*"})
public class CustomerWebFilter implements Filter {
	
	private static final String TOKEN = "tdrstoken";

	@Inject
	protected AppBean appBean;

	@Inject @Any
	private WebLanguageBean lang;
	
	@Inject @Any
	private WebSessionBean session;
	
	@EJB
	private IWebSessionController serv;
	
	private static String PARAM = "c";

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
	
		String key = httpRequest.getParameter(PARAM);
		if(key==null){
			Cookie[] cks = httpRequest.getCookies();
			if(cks != null && cks.length>0){
				for (Cookie c : cks) {
					if(c.getName().equals(TOKEN))
						key = c.getValue();
				}
			}
		}else{
			//Cookie
			String pCookieName = TOKEN;
	        Cookie cookie = new Cookie(pCookieName, key);
	        cookie.setMaxAge(-1);
	        cookie.setHttpOnly(false);
	        cookie.setPath("/cstmr");
			((HttpServletResponse)resp).addCookie(cookie);
		}
		
	    if(key!=null && !key.trim().equals("")){
	    	
	    	try {
				TResult<Boolean> res = serv.isActive(appBean.getToken(), lang.get(), key.trim());
				
				if(res.getState().equals(TState.SUCCESS)){
					if(res.getValue()){
						WebSession e = new WebSession();
						e.setKey(key.trim());
						TResult<WebSession> res2 = serv.find(appBean.getToken(), e);
						e = res2.getValue();
						
						if(e.getUser().getType().equals(WebUserType.CUSTOMER)) {
							session.get().setKey(key);
							session.get().setId(e.getId());
							session.get().setUser(e.getUser());
							chain.doFilter(req, resp);
						}else 
							setResponse(resp, httpRequest, 401, "../WARN1.html");
					}else
						setResponse(resp, httpRequest, 401, "../WARN1.html");
				}else
					setResponse(resp, httpRequest, 500, "../500.html");
				
			}catch(Exception e){
				e.printStackTrace();
				setResponse(resp, httpRequest, 500, "../500.html");
			}
	    }else{
	    	setResponse(resp, httpRequest, 401, "../WARN1.html");
	    }

	}

	/**
	 * @param resp
	 * @param httpRequest
	 * @throws IOException
	 */
	private void setResponse(ServletResponse resp, HttpServletRequest httpRequest, int status, String targetPage) throws IOException {
		if(httpRequest.getRequestURL().toString().contains("api/cstmr"))
			((HttpServletResponse)resp).setStatus(status);
		else
			((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + targetPage);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
