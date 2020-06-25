/**
 * 
 */
package br.com.covidsemfome.filter;

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

import com.covidsemfome.ejb.service.IAutUserService;
import com.covidsemfome.model.User;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

import br.com.covidsemfome.bean.CovidUserBean;
import br.com.covidsemfome.ejb.service.ServiceLocator;

/**
 * @author Davis Gordon
 *
 */
@WebFilter(filterName = "PainelAuthFilter",urlPatterns = {"/painel/*","/api/painel/*"})
public class AutenticacaoFilter implements Filter {
	
	@Inject @Any
	private CovidUserBean covidUserBean;
	
	private static String PARAM = "c";

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
		
	    /*Enumeration<String> headerNames = httpRequest.getHeaderNames();

	    if (headerNames != null) {
	            while (headerNames.hasMoreElements()) {
	            		String n = headerNames.nextElement();
	                    System.out.println(n+": " + httpRequest.getHeader(n));
	            }
	    }*/
		String key = httpRequest.getParameter(PARAM);
		if(key==null){
			Cookie[] cks = httpRequest.getCookies();
			if(cks != null && cks.length>0){
				for (Cookie c : cks) {
					if(c.getName().equals("CSF-TOKEN"))
						key = c.getValue();
				}
			}
		}else{
			//Cookie
			String pCookieName = "CSF-TOKEN";
	        Cookie cookie = new Cookie(pCookieName, key);
	        cookie.setMaxAge(-1);
	        cookie.setHttpOnly(false);
	        cookie.setPath("/");
			((HttpServletResponse)resp).addCookie(cookie);
		}
		
	    if(key!=null && !key.trim().equals("")){
	    	
		    ServiceLocator loc = ServiceLocator.getInstance();
			
			try {
				IAutUserService serv = loc.lookup("IAutUserServiceRemote");
				TResult<Boolean> res = serv.validar(key.trim());
				
				if(res.getResult().equals(EnumResult.SUCESS)){
					if(res.getValue()){
						TResult<User> res2 = serv.recuperar(key.trim());
						User u = res2.getValue();
						covidUserBean.getUser().setKey(key);
						covidUserBean.getUser().setId(u.getId());
						covidUserBean.getUser().setPessoa(u.getPessoa());
						
						loc.close();
						
						chain.doFilter(req, resp);
					}else{
						loc.close();
						((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/voluntario.html");
					}
				}else{
					loc.close();
					((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/500.html");
				}
			}catch(Exception e){
				((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/500.html");
			}
	    }else{
	    	((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/voluntario.html");
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
