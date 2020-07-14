/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.covidsemfome.module.doador.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TFilterProcess;
import com.covidsemfome.ejb.service.IDoadorService;
import com.covidsemfome.model.Doador;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TDoadorFilterProcess extends TFilterProcess {

	public TDoadorFilterProcess() throws TProcessException {
		super(Doador.class, "TDoadorServiceRemote", true);
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
		TResult result = ((IDoadorService)getService()).pesquisar(nome, dataNascimento);
		resultados.add(result);
		return resultados;
	}

}
