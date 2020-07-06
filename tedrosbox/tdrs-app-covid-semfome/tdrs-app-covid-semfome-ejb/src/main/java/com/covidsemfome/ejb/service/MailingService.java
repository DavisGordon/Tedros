/**
 * 
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import com.covidsemfome.ejb.bo.EmailBO;
import com.covidsemfome.ejb.bo.MailingBO;
import com.covidsemfome.ejb.bo.PessoaBO;
import com.covidsemfome.model.Mailing;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.ejb.base.service.TResult;
import com.tedros.ejb.base.service.TResult.EnumResult;

/**
 * @author Davis Gordon
 *
 */
@Stateless(name="IMailingService")
public class MailingService extends TEjbService<Mailing> implements IMailingService{
	
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
	
	@Override
	public TResult<Mailing> save(Mailing m) {
		
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
						return new TResult<>(EnumResult.WARNING, "Todos os voluntários estão inscritos para a ação!", m, true);
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
						return new TResult<>(EnumResult.WARNING, "Não há voluntários inscritos para a ação!", m, true);
					}
					
					break;
				}
			}
			
			if(msg.isEmpty())
				return new TResult<>(EnumResult.SUCESS, "Emails enviado com sucesso!", m, true);
			else
				return new TResult<>(EnumResult.WARNING, "Houve um problema no envio de mailing para os seguintes destinatarios: "+msg, m, true);
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, "Um erro impediu o envio de email para este mailing!", m, true);
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
	
	/*@Override
	public TResult<List<Mailing>> listAll(Class<? extends ITEntity> entidade) {
		try{
			List<Mailing> lst1 = new ArrayList<>();
			List<Acao> lst = acaoBO.listAll(Acao.class);
			for (Acao acao : lst) {
				lst1.add((Mailing) acao);
			}
			return new TResult<>(EnumResult.SUCESS, lst1);
			
		}catch(Exception e){
			e.printStackTrace();
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
		
	}*/

}
