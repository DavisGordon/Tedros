/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.covidsemfome.module.doacao.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.covidsemfome.ejb.controller.IDoacaoController;
import com.covidsemfome.model.Doacao;
import com.tedros.ejb.base.result.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TFilterProcess;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TDoacaoFilterProcess extends TFilterProcess {

	public TDoacaoFilterProcess() throws TProcessException {
		super(Doacao.class, "TDoadorControllerRemote", true);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public List<TResult<Object>> doFilter(Map<String, Object> objects) {
		
		String nome = (objects.containsKey("nome")) ? (String) objects.get("nome") :  null;
		String tipo = (objects.containsKey("tipoPessoa")) ? (String) objects.get("tipoPessoa") :  null;;
		String tipoDocumento = (objects.containsKey("tipo")) ? (String) objects.get("tipo") :  null;;
		String numero = (objects.containsKey("numero")) ? (String) objects.get("numero") :  null;;
		Date dataNascimento = (objects.containsKey("dataNascimento")) ? (Date) objects.get("dataNascimento") :  null;;
		
		List<TResult<Object>> resultados = new ArrayList<>();
		//TResult result = ((IDoacaoController)getService()).pesquisar(nome, dataInicio, dataFim, acaoId, tipoAjuda)
		//resultados.add(result);
		return resultados;
	}

}
