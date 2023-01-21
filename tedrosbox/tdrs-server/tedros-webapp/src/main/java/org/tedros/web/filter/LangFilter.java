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

import org.apache.commons.lang3.StringUtils;
import org.tedros.ejb.controller.ICountryController;
import org.tedros.location.model.Country;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.web.bean.AppBean;
import org.tedros.web.bean.WebLanguageBean;

/**
 * @author Davis Gordon
 *
 */
@WebFilter(filterName = "LangFilter",
urlPatterns = {"/index.html"})
public class LangFilter implements Filter {
	
	private static final String TOKEN = "tdrslang";
	
	@Inject
	protected AppBean appBean;

	@Inject @Any
	private WebLanguageBean lang;
	
	@EJB
	private ICountryController serv;
	
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
		boolean redirect = false;
		String l = httpRequest.getParameter(LANG);
		if(l==null){
			Cookie[] cks = httpRequest.getCookies();
			if(cks != null && cks.length>0){
				for (Cookie c : cks) 
					if(c.getName().equals(TOKEN))
						l = c.getValue();
			}
			if(l==null) {
				redirect = true;
				try {
					/*WebServiceClient client = new WebServiceClient
							.Builder(42, "license_key")
							.host("geolite.info")
							.build();

					InetAddress ipAddress = InetAddress
							.getByName(LangFilter.getClientIp(httpRequest));

					// Do the lookup
					CountryResponse response = client.country(ipAddress);
*/
					String countryIso2 = "ES" ;//response.getCountry().getIsoCode();
					
					if(StringUtils.isNotBlank(countryIso2)) {
						Country e = new Country();
						e.setIso2Code(countryIso2);
						TResult<Country> res = serv.find(appBean.getToken(), e);
						if(res.getState().equals(TState.SUCCESS)) {
							e = res.getValue();
							l = (e.getLangCode().toLowerCase().equals("pt"))
									? "pt"
											: "en";
						}else
							l = "pt";
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					l = "pt";
				}
			}
		}else{
	    	try {
	    		String pCookieName = TOKEN;
	    		Cookie cookie = new Cookie(pCookieName, l.trim());
	    		cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
				cookie.setHttpOnly(false);
				cookie.setPath("/");
				((HttpServletResponse)resp).addCookie(cookie);
				
			}catch(Exception e){
				((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/500.html");
			}
	    }
		lang.set(l.trim());
		if(redirect)
			((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/"+l+"/");
		else
			chain.doFilter(req, resp);
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
