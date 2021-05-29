/**
 * 
 */
package com.covidsemfome.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.covidsemfome.model.Documento;
import com.covidsemfome.model.Endereco;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.TipoAjuda;

/**
 * @author Davis Gordon
 *
 */
public final class TermoAdesaoHelper {

	private TermoAdesaoHelper() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @param p
	 * @param lst
	 * @param dataHoraAcao
	 * @param termo
	 * @return
	 */
	public static String replaceTokensTermo(Pessoa p, Set<TipoAjuda> lst, Date dataHoraAcao, String termo) {
		/*
		 * #NOME# #NACIONALIDADE# #ESTADOCIVIL# #CPF# #PROFISSAO# "
		+ "#IDENTIDADE# #RUA# #BAIRRO# #CEP# #CIDADE# #UF# #TIPOSAJUDA# #DATAACAO# #HORAACAO#"
		 * */
		if(p.getNome()!=null)
			termo = termo.replaceAll("#NOME#", p.getNome());
		if(p.getProfissao()!=null)
			termo = termo.replaceAll("#PROFISSAO#", p.getProfissao());
		if(p.getEstadoCivil()!=null)
			termo = termo.replaceAll("#ESTADOCIVIL#", p.getEstadoCivil());
		if(p.getDocumentos()!=null)
			for(Documento d : p.getDocumentos()) {
				if(d.getTipo().equals("1")) {
					termo = termo.replaceAll("#NACIONALIDADE#", d.getNacionalidade());
					termo = termo.replaceAll("#IDENTIDADE#", d.getNumero());
				}
				if(d.getTipo().equals("2"))
					termo = termo.replaceAll("#CPF#", d.getNumero());
			}
		
		if(lst!=null) {
			String tpas = "";
			for(TipoAjuda t : lst)
				tpas += t.getDescricao() + ", ";
			termo = termo.replaceAll("#TIPOSAJUDA#", tpas);
		}
		
		if(dataHoraAcao!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			termo = termo.replaceAll("#DATAACAO#", sdf.format(dataHoraAcao));
			sdf = new SimpleDateFormat("HH:mm");
			termo = termo.replaceAll("#HORAACAO#", sdf.format(dataHoraAcao));
		}
		
		if(p.getEnderecos()!=null)
			for(Endereco e : p.getEnderecos()) {
				if(e.getTipo().equals("1")) {
					termo = termo.replaceAll("#TIPOLOGRADOURO#", e.getTipoLogradouro());
					termo = termo.replaceAll("#LOGRADOURO#", e.getLogradouro());
					termo = termo.replaceAll("#COMPLEMENTO#", e.getComplemento());
					termo = termo.replaceAll("#BAIRRO#", e.getBairro());
					termo = termo.replaceAll("#CIDADE#", e.getCidade());
					termo = termo.replaceAll("#UF#", e.getUf().getSigla());
					termo = termo.replaceAll("#CEP#", e.getCep());
				}
			}
		return termo;
	}
	
}
