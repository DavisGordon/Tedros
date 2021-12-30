package com.covidsemfome.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import com.covidsemfome.model.Produto;
import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface IProdutoController extends ITSecureEjbController<Produto>{
	public TResult<List<Produto>> pesquisar(TAccessToken token, String cod, String nome, String marca, 
			String medida, String uniMed, String orderby, String ordertype);
		
}
