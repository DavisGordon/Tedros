/**
 * 
 */
package com.solidarity.servlet;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.solidarity.ejb.controller.IPessoaController;
import com.solidarity.model.Contato;
import com.solidarity.model.Pessoa;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.util.TEncriptUtil;
/**
 * @author Davis Gordon
 *
 */
public class VoluntarioServlet extends HttpServlet {

	private static final long serialVersionUID = -3035965625139672187L;
	
	@EJB
	private IPessoaController service;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

		String nome = request.getParameter("name");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String tel = request.getParameter("telefone");
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();

		if(StringUtils.isAnyBlank(nome, email, pass)){
			resp.setStatus(SC_BAD_REQUEST);
			out.println("Por favor informar o seu nome, email e senha.");
		}else{
	
			try {
				
				List<Contato> lst = new ArrayList<>();			
				if(email!=null){
					Contato cont1 = new Contato();
					cont1.setDescricao(email);
					cont1.setTipo("1");
					lst.add(cont1);
				}
				
				if(tel!=null){
					Contato cont2 = new Contato();
					cont2.setDescricao(tel);
					cont2.setTipo("2");
					lst.add(cont2);
				}
				
				
				Pessoa pess = new Pessoa();
				pess.setNome(nome);
				pess.setLoginName(email);
				pess.setPassword(TEncriptUtil.encript(pass));
				pess.getContatos().addAll(lst);
				pess.setTipoVoluntario("5");
				pess.setStatusVoluntario("1");
				pess.setStatus("ATIVADO");
				
				TResult<Pessoa> res = service.saveFromSite(pess);
							
				if(res.getResult().equals(EnumResult.SUCESS)){
					System.out.println("sucesso");
					resp.setStatus(SC_OK);
					out.println("Obrigado por se cadastrar agora voce tem acesso a um painel feito para te ajudar a agendar suas ações como voluntário.");
				}else if(res.getResult().equals(EnumResult.WARNING)){
					//System.out.println(res.getErrorMessage());
					resp.setStatus(SC_ACCEPTED);	
					out.println("Entre no painel com seu email e senha.");
				}else if(res.getResult().equals(EnumResult.ERROR)){
					//System.out.println(res.getErrorMessage());
					resp.setStatus(SC_INTERNAL_SERVER_ERROR);	
					out.println(res.getMessage());
				}
		
				
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(SC_INTERNAL_SERVER_ERROR);
				out.println("Não foi possivel realizar seu cadastro mas pode realiza-lo atraves do whatsup (21) 99606-1825 / Pedro Borges");
			}
		
		}
		out.close();
		//super.doPost(request, resp);
	}

}
