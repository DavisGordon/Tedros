/**
 * 
 */
package com.covidsemfome.ejb.bo;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Contato;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.TipoAjuda;
import com.covidsemfome.model.Voluntario;
import com.tedros.util.TEmailUtil;
import com.tedros.util.TFileUtil;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EmailBO {
	
	@Inject
	private PessoaBO pessBO;
	
	private String host = "http://localhost:8080/tdrs-app-covid-semfome-webapp/";
	private String emailAccount = "tedrosbox@gmail.com";
	private String passAccount = "$tdrs#221978@";
	
	private TEmailUtil util;
	
	@PostConstruct
	public void init(){
		util = TEmailUtil.getInstance("smtp.gmail.com", "587", "javax.net.ssl.SSLSocketFactory", 
				"true", "587", emailAccount, passAccount);
	}
	
	
	public void enviar(boolean debug, String to, String subject, String content, boolean html){
		util.sent(debug, emailAccount, to, subject, content, html);
	}
	
	public void enviarEmailBoasVindas(Pessoa p){
		String content = gerarMsgBoasVindas(p.getNome());
		
		//Envia email de boas vindas
		util.sent(true, emailAccount, p.getLoginName(), "Bem vindo a ONG Covid Sem Fome", content, true);
				
	}
	
	public void enviarEmailNovoVoluntario(Pessoa p){
		
		String cel = "";
		for(Contato c : p.getContatos())
			if(c.getTipo().equals("2"))
				cel = c.getDescricao();
		
		
		String content = "Um novo cadastro acaba de ser realizado pelo site. "
				+ "<br>Nome: "+p.getNome() 
				+ "<br>Email: "+p.getLoginName()
				+ "<br>Cel/Tel: "+cel;
		
		String to = pessBO.estrategicoEmail();
		
		util.sent(true, emailAccount, to, "[Covid Sem Fome] Novo cadastro de voluntário realizado pelo site", content, true);
				
	}
	
	public void enviarEmailParticiparAcao(Voluntario v){
		
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
				+ "<hr>Tipo de ajuda:"+tajs;
		
		String to = pessBO.estrategicoEmail();
		
		util.sent(true, emailAccount, to, "[Covid Sem Fome] Voluntário para ação "+v.getAcao().getTitulo(), content, true);
				
	}
	
	public void enviarEmailSairAcao(Pessoa p, Acao a){
		
		String cel = "";
		for(Contato c : p.getContatos())
			if(c.getTipo().equals("2"))
				cel = c.getDescricao();
		
		String content = p.getNome()+" saiu da ação "+a.getTitulo()+" agendado para o dia "
				+ formataDataHora(a.getData())+"."
				+ "<br>Email: "+p.getLoginName()
				+ "<br>Cel/Tel: "+cel;
		
		String to = pessBO.estrategicoEmail();
		
		util.sent(true, emailAccount, to, "[Covid Sem Fome] Voluntário saiu da ação "+a.getTitulo(), content, true);
				
	}

	private String gerarMsgBoasVindas(String nome) {
		
		File htmlTemplate = new File("C:/usr/covidsemfome/email_boasvindas.html");
		
		if(!htmlTemplate.isFile())
			throw new RuntimeException("TEMPLATE DE EMAIL NAO ENCONTRADO") ;
		
		String msg = TFileUtil.readFile(htmlTemplate);
		msg = msg.replaceAll("#NOME#", nome);
		msg = msg.replaceAll("#HOST#", host);
		
		return msg;
	}
	
	private String formataDataHora(Date data){
		String pattern = "dd/MM/yyyy 'às' HH:mm";
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(data);
	}
	
	
}
