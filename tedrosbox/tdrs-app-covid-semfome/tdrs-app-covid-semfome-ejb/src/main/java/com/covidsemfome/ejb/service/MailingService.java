/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import com.covidsemfome.ejb.bo.EmailBO;
import com.covidsemfome.ejb.bo.MailingBO;
import com.covidsemfome.ejb.bo.PessoaBO;
import com.covidsemfome.ejb.exception.MailingWarningException;
import com.covidsemfome.model.Mailing;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IMailingService")
public class MailingService extends TEjbService<Mailing> {
	
	@Inject
	private MailingBO bo;
	
	@Inject
	private PessoaBO pessBO;
	
	@Inject
	private EmailBO emailBO;

	@Override
	public ITGenericBO<Mailing> getBussinesObject() {
		return bo;
	}
	
	public void enviar(Mailing m) {
		
		try{
			String msg = "";
			if(StringUtils.isNotBlank(m.getEmails())){
				
				String content = m.getHtml().replaceAll("#NOME#", "");
				
				String[] arr = m.getEmails().contains(",") 
						? m.getEmails().split(",")
								: new String[]{m.getEmails()};
						
				for (String to : arr) {
					try{
						emailBO.enviar(false, to, m.getTituloEmail(), content, true);
					}catch(Exception e){
						e.printStackTrace();
						msg += to + ", ";
					}
				}
			}

			if(StringUtils.isNotBlank(m.getDestino())){
				switch (m.getDestino()) {
				case "1": // Todos
					List<Pessoa> lst = pessBO.listAll(Pessoa.class);
					for (Pessoa p : lst) 
						msg = enviar(m, msg, p);
					
					break;

				case "2": // Nao inscritos
					List<Pessoa> lst2 = pessBO.listAll(Pessoa.class);
					if(lst2.size() == m.getVoluntarios().size())
						throw new MailingWarningException("Todos os voluntários estão inscritos para a ação!");
					for (Pessoa p : lst2) {
						boolean enviar = true;
						List<Voluntario> vLst = m.getVoluntarios();
						if(vLst!=null && !vLst.isEmpty()){
							for (Voluntario v : vLst) {
								if(v.getPessoa().getId().equals(p.getId())){
									enviar = false;
									break;
								}
							}
						}
						
						if(enviar)
							msg = enviar(m, msg, p);
					}
					
					break;
					
				case "3": // somente inscritos
					List<Voluntario> vLst = m.getVoluntarios();
					if(vLst!=null && !vLst.isEmpty()){
						for (Voluntario v : vLst) {
							msg = enviar(m, msg, v.getPessoa());
						}
					}else{
						throw new MailingWarningException("Não há voluntários inscritos para a ação!");
					}
					
					break;
				}
			}
			
			if(!msg.isEmpty())
				throw new MailingWarningException("Houve um problema no envio de mailing para os seguintes destinatarios: "+msg);
		
		}catch(Exception e){
			e.printStackTrace();
			throw new MailingWarningException("Um erro impediu o envio de email para este mailing!");
		}
		
	}

	private String enviar(Mailing m, String msg, Pessoa p) {
		String content = m.getHtml().replaceAll("#NOME#", p.getNome());
		try{
			emailBO.enviar(false, p.getLoginName(), m.getTituloEmail(), content, true);
		}catch(Exception e){
			e.printStackTrace();
			msg += p.getNome() + ", ";
		}
		return msg;
	}
	
	

}
