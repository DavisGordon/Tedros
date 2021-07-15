package com.solidarity.module.pessoa.model;

import com.solidarity.model.Contato;
import com.solidarity.model.Pessoa;

public final class PessoaFieldValueUtil {

	private PessoaFieldValueUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getDescricaoTipo(String value) {
		if(value==null)
			value= "";
		switch(value){
		case "1": return "Operacional";
		case "2": return "Estratégico";
		case "3": return "Estratégico (Receber emails)";
		case "4": return "Doador/Filatrópico";
		case "5": return "Cadastro/Site";
		case "6": return "Outro";
		default: return "";
		}
	}

	public static String getDescricaoStatus(String value) {
		if(value==null)
			value= "";
		switch(value){
		case "1": return "Aguardando";
		case "2": return "Contactado";
		case "3": return "Voluntário";
		case "4": return "Voluntário Ativo";
		case "5": return "Voluntário problematico";
		case "6": return "Desligado";
		default: return "";
		}
	}
	
	public static String getEmails(Pessoa p){
		String login = p.getLoginName() == null ? "" : p.getLoginName();
		String email = login;
		
		if(p.getContatos()!=null)
			for(Contato c : p.getContatos()){
				if(!c.getTipo().equals("1") 
						|| login.toLowerCase().trim().equals(c.getDescricao().toLowerCase().trim()))
					continue;
				
				email += (!email.isEmpty()) ? "," + c.getDescricao() : c.getDescricao();
			}
		return email;
	}
}
