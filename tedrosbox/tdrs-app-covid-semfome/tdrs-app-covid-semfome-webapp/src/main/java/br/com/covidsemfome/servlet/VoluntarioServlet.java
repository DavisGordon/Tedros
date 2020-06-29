/**
 * 
 */
package br.com.covidsemfome.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.covidsemfome.ejb.service.IPessoaService;
import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;
import com.tedros.util.TEncriptUtil;

import br.com.covidsemfome.ejb.service.ServiceLocator;

/**
 * @author Davis Gordon
 *
 */
public class VoluntarioServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3035965625139672187L;
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

		String nome = request.getParameter("name");
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String tel = request.getParameter("telefone");
		String voluntario = request.getParameter("voluntario");
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();

		if(StringUtils.isAnyBlank(nome, email, pass)){
			resp.setStatus(resp.SC_BAD_REQUEST);
			out.println("Por favor informar o seu nome, email, senha e o local que pretende se voluntariar.");
		}else{
		
			ServiceLocator loc = ServiceLocator.getInstance();
	
			try {
				IPessoaService service = (IPessoaService) loc.lookup("IPessoaServiceRemote");
				
				List<Contato> lst = new ArrayList();			
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
				pess.setTipoVoluntario(voluntario);
				pess.setStatusVoluntario("1");
				
				TResult<Pessoa> res = service.saveFromSite(pess);
							
				if(res.getResult().equals(EnumResult.SUCESS)){
					System.out.println("sucesso");
					resp.setStatus(HttpServletResponse.SC_OK);
					out.println("Obrigado por se cadastrar agora voce tem acesso a um painel feito para te ajudar a agendar suas ações como voluntário.");
				}else{
					//System.out.println(res.getErrorMessage());
					resp.setStatus(HttpServletResponse.SC_ACCEPTED);	
					out.println("Entre no painel com seu email e senha.");
				}
		
				
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				out.println("Não foi possivel realizar seu cadastro mas pode realiza-lo atraves do whatsup (21) 99606-1825 / Pedro Borges");
			}finally{
				loc.close();
			}
		
		}
		out.close();
		//super.doPost(request, resp);
	}

}
