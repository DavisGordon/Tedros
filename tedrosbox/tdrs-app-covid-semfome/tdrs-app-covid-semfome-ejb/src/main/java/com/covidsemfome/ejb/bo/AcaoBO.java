/**
 * 
 */
package com.covidsemfome.ejb.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.AcaoEAO;
import com.covidsemfome.model.Acao;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AcaoBO extends TGenericBO<Acao> {
	
	@Inject
	private AcaoEAO eao;

	@Override
	public ITGenericEAO<Acao> getEao() {
		// TODO Auto-generated method stub
		return eao;
	}

	public List<Acao> listAcoesDoDiaAnteriorEmDiante(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date data = cal.getTime();
		return eao.listAcoes(data);
	}
}
