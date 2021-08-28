/**
 * 
 */
package com.covidsemfome.ejb.bo;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.covidsemfome.ejb.exception.EmailBusinessException;
import com.covidsemfome.ejb.producer.Item;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.model.Voluntario;
import com.tedros.util.TDateUtil;
import com.tedros.util.TEmailUtil;
import com.tedros.util.TSentEmailException;
import com.tedros.util.TFileUtil;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EmailBO {
	
	@Inject
	private PessoaBO pessBO;
	
	@Inject
	@Named("host")
	private Item<String> host;
	
	@Inject
	@Named("emailUser")
	private Item<String> emailAccount;
	
	@Inject
	@Named("emailPass")
	private Item<String> passAccount;
	
	@Inject
	@Named("emailTemplatePath")
	private Item<String> templPath;
	
	private TEmailUtil util;
	
	@PostConstruct
	public void init(){
		util = TEmailUtil.getInstance("smtp.gmail.com", "587", "javax.net.ssl.SSLSocketFactory", 
				"true", "587", emailAccount.getValue(), passAccount.getValue());
	}
	
	
	public void enviar(boolean debug, String to, String subject, String content, boolean html) throws TSentEmailException{
		util.sent(debug, emailAccount.getValue(), to, subject, content, html);
	}
	
	public void enviarEmailBoasVindas(Pessoa p) throws TSentEmailException, EmailBusinessException{
		String content = gerarMsgBoasVindas(p.getNome());
		
		//Envia email de boas vindas
		util.sent(false, emailAccount.getValue(), p.getLoginName(), "Bem vindo a ONG Covid Sem Fome", content, true);
				
	}
	
	public void enviarEmailNewPass(Pessoa p, String key) throws TSentEmailException{
		
		String content = "Olá "+p.getNome()+", para definir uma nova senha clique <a href=\""+host+"defpass/defpass.html?k="+key+"\">aqui</a>";
		
		util.sent(false, emailAccount.getValue(), p.getLoginName(), "[Covid Sem Fome] Definir nova senha", content, true);
				
	}
	
	public void enviarEmailAcoesProgramadasParaDecisao(List<Acao> lst) throws TSentEmailException, EmailBusinessException{
		
		List<Pessoa> pess = pessBO.getListaEstrategicoEmail();
		if(pess==null || pess.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		Calendar c = Calendar.getInstance();
		for(Pessoa p : pess) {
			String to = pessBO.getEmail(p);
			if(to==null ||  (p.getStatus()!=null && p.getStatus().equals("DESATIVADO")))
				continue;
			
			String content = "As seguintes ações encontram-se programadas para muito em breve e aguardam uma decisão: <br>";
			for(Acao a : lst) {
				content += "Ação "+a.getTitulo()
				+ " programada para "+TDateUtil.getFormatedDate(a.getData(), TDateUtil.DDMMYYYY_HHMM)
				+ "<br>Mudar status para: ";
				if(c.after(a.getData())) {
					content += "<br><a href=\""+host+"api/csf/acao/"+a.getId()+"/prog/2/"+to+"\">Cancelada</a> ou "
							+ "<a href=\""+host+"api/csf/acao/"+a.getId()+"/prog/3/"+to+"\">Executada</a> ";
				}else {
					content += "<br><a href=\""+host+"api/csf/acao/"+a.getId()+"/prog/1/"+to+"\">Agendada</a> ou "
							+ "<a href=\""+host+"api/csf/acao/"+a.getId()+"/prog/2/"+to+"\">Cancelada</a> ";
				}
				content += "<hr>";
			}
			content += "Alterar todas para <a href=\""+host+"api/csf/acao/prog/1/"+to+"\">Agendada</a> ou "
					+ "todas para <a href=\""+host+"api/csf/acao/prog/2/"+to+"\">Cancelada</a> com exceção das "
					+ "que foram alteradas isoladamente.";
		
 
			util.sent(false, emailAccount.getValue(), to, "[Covid Sem Fome] Avaliar ações com status programadas.", content, true);
		}
	}
	
	public void enviarEmailNovoVoluntario(Pessoa p) throws TSentEmailException, EmailBusinessException{
		
		String cel = "";
		for(Contato c : p.getContatos())
			if(c.getTipo().equals("2"))
				cel = c.getDescricao();
		
		
		String content = "Um novo cadastro acaba de ser realizado pelo site. "
				+ "<br>Nome: "+p.getNome() 
				+ "<br>Email: "+p.getLoginName()
				+ "<br>Cel/Tel: "+cel;
		
		String to = pessBO.getEnderecoEstrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(false, emailAccount.getValue(), to, "[Covid Sem Fome] Novo cadastro de voluntário realizado pelo site", content, true);
				
	}
	
	public void enviarEmailParticiparAcao(Voluntario v, String termo) throws TSentEmailException, EmailBusinessException{
		
		Pessoa p = v.getPessoa();
		
		String cel = "";
		for(Contato c : p.getContatos())
			if(c.getTipo().equals("2"))
				cel = c.getDescricao();
		
		String tajs = "<ul>";
		for(TipoAjuda taj : v.getTiposAjuda())
			tajs += "<li>"+taj.getDescricao()+"</li>";
		tajs += "</ul>";
		
		
		String content = p.getNome()+" quer participar na ação "+v.getAcao().getTitulo()+" agendado para o dia "
				+ formataDataHora(v.getAcao().getData())+"."
				+ "<br>Email: "+p.getLoginName()
				+ "<br>Cel/Tel: "+cel
				+ "<br>Tipo de ajuda:"+tajs
				+ (termo!=null ? "<hr><br>Termo de adesão:<br><br>" + termo : "");
		
		String to = pessBO.getEnderecoEstrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(false, emailAccount.getValue(), to, "[Covid Sem Fome] Voluntário para ação "+v.getAcao().getTitulo(), content, true);
				
	}
	
	public void enviarEmailSairAcao(Pessoa p, Acao a) throws TSentEmailException, EmailBusinessException{
		
		String cel = "";
		for(Contato c : p.getContatos())
			if(c.getTipo().equals("2"))
				cel = c.getDescricao();
		
		String content = p.getNome()+" saiu da ação "+a.getTitulo()+" agendado para o dia "
				+ formataDataHora(a.getData())+"."
				+ "<br>Email: "+p.getLoginName()
				+ "<br>Cel/Tel: "+cel;
		
		String to = pessBO.getEnderecoEstrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(false, emailAccount.getValue(), to, "[Covid Sem Fome] Voluntário saiu da ação "+a.getTitulo(), content, true);
				
	}

	private String gerarMsgBoasVindas(String nome) throws EmailBusinessException {
		
		//File htmlTemplate = new File("C:/usr/covidsemfome/email_boasvindas.html");
		File htmlTemplate = new File(templPath.getValue());
		
		if(!htmlTemplate.isFile())
			throw new EmailBusinessException("TEMPLATE DE EMAIL NAO ENCONTRADO") ;
		
		String msg = TFileUtil.readFile(htmlTemplate);
		msg = msg.replaceAll("#NOME#", nome);
		msg = msg.replaceAll("#HOST#", host.getValue());
		
		return msg;
	}
	
	private String formataDataHora(Date data){
		String pattern = "dd/MM/yyyy 'às' HH:mm";
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(data);
	}
	
	
}
