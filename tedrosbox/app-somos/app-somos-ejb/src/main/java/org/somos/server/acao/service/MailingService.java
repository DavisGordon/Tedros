/**
 * 
 */
package org.somos.server.acao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.somos.model.Mailing;
import org.somos.model.Pessoa;
import org.somos.model.Voluntario;
import org.somos.server.acao.bo.EmailBO;
import org.somos.server.acao.bo.MailingBO;
import org.somos.server.exception.MailingWarningException;
import org.somos.server.pessoa.bo.PessoaBO;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IMailingService")
@TransactionAttribute(value = TransactionAttributeType.NEVER)
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
					}catch(Exception | TSentEmailException e){
						e.printStackTrace();
						msg += "".equals(msg) ? to : ", "+to;
					}
				}
			}
			String dest = m.getDestino() == null ? "" : m.getDestino().getKey();
			if(StringUtils.isNotBlank(dest)){
				
				List<Pessoa> lstp;
				List<Pessoa> lstd;
				
				switch (dest) {
				case "1": // Todos
					 lstp = pessBO.listAll(Pessoa.class);
					for (Pessoa p : lstp) 
						msg = enviar(m, msg, p);
					
					break;

				case "2": // Nao inscritos
					lstp = pessBO.listAll(Pessoa.class);
					if(lstp.size() == m.getVoluntarios().size())
						throw new MailingWarningException("Todos os voluntários estão inscritos para a ação!");
					for (Pessoa p : lstp) {
						boolean enviar = true;
						Set<Voluntario> vLst = m.getVoluntarios();
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
					Set<Voluntario> vLst = m.getVoluntarios();
					if(vLst!=null && !vLst.isEmpty()){
						for (Voluntario v : vLst) {
							msg = enviar(m, msg, v.getPessoa());
						}
					}else{
						throw new MailingWarningException("Não há voluntários inscritos para a ação!");
					}
					
					break;
					
				case "4": // operacionais
					lstp = pessBO.listAll(Pessoa.class);
					lstd = new ArrayList<>();
					for (Pessoa p : lstp){
						if(p.getTipoVoluntario()!=null && p.getTipoVoluntario().equals("1"))
							lstd.add(p);
					}
					if(lstd.isEmpty())
						throw new MailingWarningException("Não há voluntários operacionais cadastrados!");
					
					for (Pessoa p : lstd) 		
						msg = enviar(m, msg, p);
					
					break;
				
				case "5": // estrategicos
					lstp = pessBO.listAll(Pessoa.class);
					lstd = new ArrayList<>();
					for (Pessoa p : lstp) {
						if(p.getTipoVoluntario()!=null && (p.getTipoVoluntario().equals("2") || p.getTipoVoluntario().equals("3")))
							lstd.add(p);
					}
					if(lstd.isEmpty())
						throw new MailingWarningException("Não há voluntários estratėgicos cadastrados!");
					
					for (Pessoa p : lstd) 		
						msg = enviar(m, msg, p);
					
					break;
					
				case "6": // doadores
					lstp = pessBO.listAll(Pessoa.class);
					lstd = new ArrayList<>();
					for (Pessoa p : lstp) {
						if(p.getTipoVoluntario()!=null && p.getTipoVoluntario().equals("4"))
							lstd.add(p);
					}
					if(lstd.isEmpty())
						throw new MailingWarningException("Não há doadores cadastrados!");
					
					for (Pessoa p : lstd) 		
						msg = enviar(m, msg, p);
					
					break;
				
				case "7": // cadastrado site e outros
					lstp = pessBO.listAll(Pessoa.class);
					lstd = new ArrayList<>();
					for (Pessoa p : lstp) {
						if(p.getTipoVoluntario()!=null && (p.getTipoVoluntario().equals("5") || p.getTipoVoluntario().equals("6")))
							lstd.add(p);
					}
					if(lstd.isEmpty())
						throw new MailingWarningException("Não há pessoas "+ m.getDestino().getValue()+" cadastrados!");
					
					for (Pessoa p : lstd) 		
						msg = enviar(m, msg, p);
					
					break;
				}
			}
			
			if(!msg.isEmpty())
				throw new MailingWarningException("Houve um problema no envio de email para os seguintes destinatarios: "+msg);
		
		}catch(MailingWarningException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new MailingWarningException("Um erro impediu o envio de email para este mailing!");
		}
		
	}

	private String enviar(Mailing m, String msg, Pessoa p) {
		String content = m.getHtml().replaceAll("#NOME#", p.getNome());
		try{
			emailBO.enviar(false, p.getLoginName(), m.getTituloEmail(), content, true);
		}catch(Exception | TSentEmailException e){
			e.printStackTrace();
			msg += "".equals(msg) ? p.getNome() : ", "+p.getNome();
		}
		return msg;
	}
	
	

}
