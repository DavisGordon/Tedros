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

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.tedros.env.domain.WebUserType;
import org.tedros.env.ejb.controller.IWebUserController;
import org.tedros.env.entity.WebUser;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TEncriptUtil;
import org.tedros.util.TValidatorUtil;
import org.tedros.web.bean.AppBean;
/**
 * @author Davis Gordon
 *
 */
public class CustomerServlet extends HttpServlet {

	private static final long serialVersionUID = -3035965625139672187L;
	
	@Inject @Any
	private AppBean appBean;
	
	@EJB
	private IWebUserController service;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();

		if(StringUtils.isAnyBlank(email, pass)){
			resp.setStatus(SC_BAD_REQUEST);
			out.println("Por favor informar um email e senha.");
		}else{
			if(!TValidatorUtil.isEmailAddress(email)) {
				resp.setStatus(SC_BAD_REQUEST);
				out.println("Email invalido.");
			}else{
				try {
					WebUser wu = new WebUser();
					
					wu.setUsername(email);
					TResult<WebUser> res = service.find(appBean.getToken(), wu);

					if(res.getState().equals(TState.SUCCESS) && res.getValue()!=null){
						resp.setStatus(SC_ACCEPTED);	
						out.println("O email informado já encontra-se cadastrado!");
					}else  if(res.getState().equals(TState.ERROR)){
						//System.out.println(res.getErrorMessage());
						resp.setStatus(SC_INTERNAL_SERVER_ERROR);	
					}else{
			
						wu.setPassword(TEncriptUtil.encript(pass));
						wu.setType(WebUserType.CUSTOMER);
						
						res = service.save(appBean.getToken(), wu);
									
						if(res.getState().equals(TState.SUCCESS)){
							System.out.println("sucesso");
							resp.setStatus(SC_OK);
							out.println("Usúario criado com sucesso!");
						}else if(res.getState().equals(TState.ERROR)){
							//System.out.println(res.getErrorMessage());
							resp.setStatus(SC_INTERNAL_SERVER_ERROR);	
							out.println(res.getMessage());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					resp.sendError(SC_INTERNAL_SERVER_ERROR);
					out.println("Não foi possivel realizar seu cadastro, tente novamente mais tarde!");
				}
			}
		}
		out.close();
	}

}
