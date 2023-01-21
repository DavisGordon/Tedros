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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.tedros.env.domain.WebUserType;
import org.tedros.env.ejb.controller.IWebUserController;
import org.tedros.env.entity.WebUser;
import org.tedros.env.util.ResourceHandler;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TValidatorUtil;
import org.tedros.web.ResKey;
import org.tedros.web.bean.AppBean;
import org.tedros.web.bean.WebLanguageBean;
/**
 * @author Davis Gordon
 *
 */
@WebServlet(name="SignUpCustomer",
urlPatterns= {"/en/suc", "/pt/suc"})
public class SignUpCustomer extends HttpServlet {

	private static final long serialVersionUID = -3035965625139672187L;
	
	@Inject @Any
	private AppBean appBean;
	
	@Inject @Any
	private WebLanguageBean lang;
	
	@EJB
	private IWebUserController service;
	
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
					TResult<WebUser> res = service.signUp(appBean.getToken(), 
							locale, email, pass, WebUserType.CUSTOMER);
			
					if(res.getState().equals(TState.SUCCESS)){
						resp.setStatus(SC_OK);
						out.println(res.getMessage());
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
					out.println(resource.getString(ResKey.ERROR_CREATE_USER));
				}
			}
		}
		out.close();
	}

}
