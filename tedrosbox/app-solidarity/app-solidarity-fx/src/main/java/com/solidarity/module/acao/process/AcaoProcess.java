/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.module.acao.process;

import com.solidarity.model.Acao;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TEntityProcess;

/**
 * The acao CRUD Process
 *
 * @author Davis Gordon
 *
 */
public class AcaoProcess extends TEntityProcess<Acao> {

	public AcaoProcess() throws TProcessException {
		super(Acao.class, "IAcaoControllerRemote");
		
		/*for(int i=0; i<=500; i++) {
			Acao a = new Acao();
			a.setTitulo("Acao teste "+i);
			a.setData(new Date());
			a.setDescricao("precisamos da sua ajuda para a campanha a ser realizada no dia 13/08/2020 às 00:00, temos 3 inscrito(s) e precisamos de 4");
			a.setStatus("Agendada");
			super.save(a);
		}*/
		
	}

}
