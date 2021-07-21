package com.solidarity.module.pessoa.model;

import com.solidarity.model.Contato;
import com.solidarity.model.Pessoa;
import com.tedros.core.TInternationalizationEngine;

public final class PessoaFieldValueUtil {

	private PessoaFieldValueUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getDescricaoTipo(String value) {
		TInternationalizationEngine iE = TInternationalizationEngine.getInstance(null);
		if(value==null)
			value= "";
		switch(value){
		case "1": return iE.getString("#{label.operacional}");
		case "2": return iE.getString("#{label.estrategico}");
		case "3": return iE.getString("#{label.estrategico.email}");
		case "4": return iE.getString("#{label.doador.filant}");
		case "5": return iE.getString("#{label.cad.site}");
		case "6": return iE.getString("#{label.outro}");
		default: return "";
		}
	}

	public static String getDescricaoStatus(String value) {
		TInternationalizationEngine iE = TInternationalizationEngine.getInstance(null);

		if(value==null)
			value= "";
		switch(value){
		case "1": return iE.getString("#{label.nao.contactado}");
		case "2": return iE.getString("#{label.contactado}");
		case "3": return iE.getString("#{label.inativo}");
		case "4": return iE.getString("#{label.ativo}");
		case "5": return iE.getString("#{label.desligado}");
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
