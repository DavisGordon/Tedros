/**
 * 
 */
package org.tedros.web.servlet;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.tedros.env.domain.WebUserType;
import org.tedros.env.ejb.controller.IWebSessionController;
import org.tedros.env.ejb.controller.IWebUserController;
import org.tedros.env.entity.WebSession;
import org.tedros.env.util.ResourceHandler;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TValidatorUtil;
import org.tedros.web.ResKey;
import org.tedros.web.bean.AppBean;
import org.tedros.web.bean.WebLanguageBean;
import org.tedros.web.bean.WebSessionBean;
/**
 * @author Davis Gordon
 *
 */
@WebServlet(name="SignInCustomer",
urlPatterns= {"/en/sic", "/pt/sic"})
public class SignInCustomer extends HttpServlet {

	private static final long serialVersionUID = -3035965625139672187L;
	
	private static final String TOKEN = "tdrstoken";

	@Inject
	protected AppBean appBean;

	@Inject @Any
	private WebLanguageBean lang;
	
	@Inject @Any
	private WebSessionBean session;
	
	@EJB
	private IWebSessionController serv;
	

	@EJB
	private IWebUserController uServ;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		
		Locale locale = lang.get();
		ResourceHandler resource = ResourceHandler.fromWar(ResKey.RESOURCE_NAME, locale);
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();

		if(StringUtils.isAnyBlank(email, pass)){
			resp.setStatus(SC_BAD_REQUEST);
			out.println(resource.getString(ResKey.MSG_EMAIL_PASSWORD_REQUIRED));
		}else{
			if(!TValidatorUtil.isEmailAddress(email)) {
				resp.setStatus(SC_BAD_REQUEST);
				out.println(resource.getString(ResKey.MSG_INVALID_EMAIL));
			}else{
				try {
					TResult<WebSession> res = uServ.signIn(appBean.getToken(), lang.get(), 
							email, pass, WebUserType.CUSTOMER);
					
					if(res.getState().equals(TState.SUCCESS)){
						WebSession e = res.getValue();
						String pCookieName = TOKEN;
				        Cookie cookie = new Cookie(pCookieName, e.getKey());
				        cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
				        cookie.setHttpOnly(false);
				        cookie.setPath("/");
						((HttpServletResponse)resp).addCookie(cookie);
				    	
						session.get().setKey(e.getKey());
						session.get().setId(e.getId());
						session.get().setUser(e.getUser());
						resp.setStatus(SC_OK);
					
					}else 
					if(res.getState().equals(TState.ERROR)){
						resp.setStatus(SC_INTERNAL_SERVER_ERROR);	
						out.println(res.getMessage());
					}else {
						resp.setStatus(SC_ACCEPTED);
						out.println(res.getMessage());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					resp.sendError(SC_INTERNAL_SERVER_ERROR);
				}
			}
		}
		out.close();
	}

}
