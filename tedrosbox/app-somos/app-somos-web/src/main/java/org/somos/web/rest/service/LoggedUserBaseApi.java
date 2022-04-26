/**
 * 
 */
package org.somos.web.rest.service;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.commons.text.WordUtils;
import org.somos.ejb.controller.IAutUserController;
import org.somos.model.Pessoa;
import org.somos.web.bean.CovidUserBean;
import org.somos.web.rest.model.RestModel;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
public class LoggedUserBaseApi extends BaseApi {


	@Inject @Any
	protected CovidUserBean covidUserBean;
	
	@EJB
	private IAutUserController autServ;
	

	@GET
	@Path("/logout")
	public RestModel<String> logout(){
		
		try {
			TResult<Boolean> res = autServ.logout(covidUserBean.getUser().getPessoa());
			
			if(res.getResult().equals(EnumResult.SUCESS)){
				System.out.println(covidUserBean.getUser().getPessoa().getNome() +" removido da sessao com sucesso");
				
				return new RestModel<String>("LOGOUT", "200", res.getMessage());
			}else{
				return new RestModel<String>("", "404", res.getResult().equals(EnumResult.WARNING) ? res.getMessage()  : error.getValue() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new RestModel<String>("", "500", error.getValue());
		}
	}

	@GET
	@Path("/user/info")
	public RestModel<Map<String, String>> getUserInfo(){
		
		try{
			Pessoa p = covidUserBean.getUser().getPessoa();
			
			Map<String, String> info = new HashMap<>();
			info.put("nome", WordUtils.capitalizeFully(p.getNome()));
			info.put("status", p.getStatusVoluntario());
			info.put("sexo", p.getSexo());
			info.put("estrategico", Boolean.toString(p.isVoluntarioEstrategico()));
			return new RestModel<>(info, "200", "OK");
		}catch (Exception e) {
			return new RestModel<>(null, "500", error.getValue());
		}
	}

	@GET
	@Path("/mg")
	public RestModel<String> getMenuGestao(){
				
		try {
			String m = " <a href=\"tdrs/prod.html\" >Editar Produtos</a>\r\n" + 
					"		  <a href=\"tdrs/estoque_inicial.html\" >Estoque / Config</a>\r\n" + 
					"		  <a href=\"tdrs/entrada.html\" >Estoque / Entrada</a>\r\n" + 
					"		  <a href=\"tdrs/saida.html\" >Estoque / Saida</a>\r\n" +
					"		  <a href=\"tdrs/estoque.html\" >Estoque / Visualizar</a>\r\n" ;
			String n = "		  <a href=\"javascript: logout()\" >Sair</a>\r\n" + 
					"		  <a href=\"javascript:void(0);\" class=\"icon\" onclick=\"shm()\">\r\n" + 
					"		    <i class=\"fa fa-bars\"></i>\r\n" + 
					"		  </a>";
			
			String mg = isAccessDenied() ? n : m+n;
			return new RestModel<>(mg, "200", "OK");
			
		}catch(Exception e){
			e.printStackTrace();
			return new RestModel<>(null, "500", error.getValue());
		}
	}
	
	protected boolean isAccessDenied() {
		int tv = Integer.valueOf(this.covidUserBean.getUser().getPessoa().getTipoVoluntario());
		return (tv<2||tv>3);
	}
}
