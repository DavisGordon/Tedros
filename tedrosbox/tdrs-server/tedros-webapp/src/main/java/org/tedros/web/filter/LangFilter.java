/**
 * 
 */
package org.tedros.web.filter;

import java.io.IOException;

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

import org.tedros.web.bean.WebLanguageBean;

/**
 * @author Davis Gordon
 *
 */
@WebFilter(filterName = "LangFilter",
urlPatterns = {"/en/*", "/pt/*", "/api/*"})
public class LangFilter implements Filter {
	
	private static final String TOKEN = "tdrslang";
	
	@Inject @Any
	private WebLanguageBean lang;
	
	private static String LANG = "lng";

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
		String uri = httpRequest.getRequestURI();
		
		String l = httpRequest.getParameter(LANG);
		String f = null;
		if(uri.contains("/en/")) f = "en";
		if(uri.contains("/pt/")) f = "pt";
		Cookie cookie = null;
		if(lang.get()==null) {
			cookie = getCookie(httpRequest);
			String v = null;
			if(l!=null) 
				v = l;
			else if(f!=null)
				v = f;
			
			if(cookie==null && v!=null)
				cookie = createCookie(v);
			else if(v!=null)
				cookie.setValue(v);
			if(cookie!=null) {
				lang.set(cookie.getValue());
				((HttpServletResponse)resp).addCookie(cookie);
			}
		}
		
		chain.doFilter(req, resp);
	}

	private Cookie getCookie(HttpServletRequest httpRequest) {
		Cookie[] cks = httpRequest.getCookies();
		if(cks != null && cks.length>0){
			for (Cookie c : cks) 
				if(c.getName().equals(TOKEN))
					return c;
		}
		
		return null;
	}

	private Cookie createCookie(String l) {
		String pCookieName = TOKEN;
		Cookie cookie = new Cookie(pCookieName, l.trim());
		cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
		cookie.setHttpOnly(false);
		cookie.setPath("/");
		
		return cookie;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	
	private static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return !"".equals(remoteAddr)
        		? remoteAddr
        				: request.getRemoteAddr();
    }

}
