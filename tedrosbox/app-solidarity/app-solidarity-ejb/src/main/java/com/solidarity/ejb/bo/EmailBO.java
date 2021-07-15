/**
 * 
 */
package com.solidarity.ejb.bo;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.solidarity.ejb.exception.EmailBusinessException;
import com.solidarity.ejb.producer.Item;
import com.solidarity.model.Acao;
import com.solidarity.model.Contato;
import com.solidarity.model.Pessoa;
import com.solidarity.model.TipoAjuda;
import com.solidarity.model.Voluntario;
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
	
	public void enviarEmailNovoVoluntario(Pessoa p) throws TSentEmailException, EmailBusinessException{
		
		String cel = "";
		for(Contato c : p.getContatos())
			if(c.getTipo().equals("2"))
				cel = c.getDescricao();
		
		
		String content = "Um novo cadastro acaba de ser realizado pelo site. "
				+ "<br>Nome: "+p.getNome() 
				+ "<br>Email: "+p.getLoginName()
				+ "<br>Cel/Tel: "+cel;
		
		String to = pessBO.estrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(true, emailAccount.getValue(), to, "[Covid Sem Fome] Novo cadastro de voluntário realizado pelo site", content, true);
				
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
		
		String to = pessBO.estrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(true, emailAccount.getValue(), to, "[Covid Sem Fome] Voluntário para ação "+v.getAcao().getTitulo(), content, true);
				
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
		
		String to = pessBO.estrategicoEmail();
		if(to.isEmpty())
			throw new EmailBusinessException("Não foi identificado nenhum voluntário estrategico para envio de email.");
		
		util.sent(false, emailAccount.getValue(), to, "[Covid Sem Fome] Voluntário saiu da ação "+a.getTitulo(), content, true);
				
	}

	private String gerarMsgBoasVindas(String nome) throws EmailBusinessException {
		
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
