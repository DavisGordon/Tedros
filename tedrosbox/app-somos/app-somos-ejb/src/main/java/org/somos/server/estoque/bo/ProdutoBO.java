/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.estoque.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.somos.model.Produto;
import org.somos.server.estoque.eao.ProdutoEAO;

import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ProdutoBO extends TGenericBO<Produto> {

	@Inject
	private ProdutoEAO eao;
	
	@Override
	public ProdutoEAO getEao() {
		return eao;
	}
	
	public List<Produto> pesquisar(String cod, String nome, String marca, String medida, String uniMed, String orderby, String ordertype){
		List<String> cods = null;
		if(cod!=null){
			cods = new ArrayList<>();
			String[] arr = cod.split(",");
			cods = Arrays.asList(arr);
		}
		
		return eao.pesquisar(cods, nome, marca, medida, uniMed, orderby, ordertype);
	}

}
