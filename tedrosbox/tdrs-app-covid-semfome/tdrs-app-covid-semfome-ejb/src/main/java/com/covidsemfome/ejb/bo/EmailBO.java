/**
 * 
 */
package com.covidsemfome.ejb.bo;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

import com.covidsemfome.model.Pessoa;
import com.tedros.util.TEmailUtil;
import com.tedros.util.TFileUtil;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EmailBO {
	
	private String host = "http://localhost:8080/tdrs-app-covid-semfome-webapp/";
	private String emailAccount = "tedrosbox@gmail.com";
	private String passAccount = "$tdrs#221978@";
	
	private TEmailUtil util;
	
	@PostConstruct
	public void init(){
		util = TEmailUtil.getInstance("smtp.gmail.com", "587", "javax.net.ssl.SSLSocketFactory", 
				"true", "587", emailAccount, passAccount);
	}
	
	
	public void enviarEmailBoasVindas(Pessoa p){
		String content = gerarMsgBoasVindas(p.getNome());
		
		//Envia email de boas vindas
		util.sent(true, emailAccount, p.getLoginName(), "Bem vindo a ONG Covid Sem Fome", content, true);
				
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
	
	
	
}
