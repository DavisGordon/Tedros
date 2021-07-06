/**
 * 
 */
package br.com.covidsemfome.filter;

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

import com.covidsemfome.ejb.controller.IAutUserController;
import com.covidsemfome.model.User;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

import br.com.covidsemfome.bean.CovidUserBean;

/**
 * @author Davis Gordon
 *
 */
@WebFilter(filterName = "TermoFilter",urlPatterns = {"/termo/*","/api/termo/*"})
public class TermoFilter implements Filter {
	
	@Inject @Any
	private CovidUserBean covidUserBean;
	
	@EJB
	private IAutUserController serv;
	
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
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) req;
	
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
	    	
	    	try {
				TResult<Boolean> res = serv.validar(key.trim());
				
				if(res.getResult().equals(EnumResult.SUCESS)){
					if(res.getValue()){
						TResult<User> res2 = serv.recuperar(key.trim());
						User u = res2.getValue();
						covidUserBean.getUser().setKey(key);
						covidUserBean.getUser().setId(u.getId());
						covidUserBean.getUser().setPessoa(u.getPessoa());
						
						chain.doFilter(req, resp);
					}else{
						((HttpServletResponse)resp).sendRedirect(httpRequest.getContextPath() + "/voluntario.html");
					}
				}else{
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

	}

}
