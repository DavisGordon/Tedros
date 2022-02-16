package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.SiteParceria;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

@Remote
public interface ISiteParceriaController extends ITSecureEjbController<SiteParceria>{

	TResult<String> enviarEmail(TAccessToken token, String empresa, String nome, String contato, String tipoAjuda,
			String descricao, String endereco);
}
