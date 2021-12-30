/**
 * 
 */
package com.covidsemfome.server.acao.timer;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
import com.covidsemfome.server.acao.service.AcaoService;
import com.covidsemfome.server.acao.service.VoluntarioService;
import com.covidsemfome.server.exception.EmailBusinessException;
import com.covidsemfome.server.pessoa.service.PessoaService;
import com.tedros.util.TSentEmailException;

/**
 * @author Davis Gordon
 *
 */
@Singleton
@Lock(LockType.READ) // allows timers to execute in parallel
@Startup
public class AcaoServiceTimer {

	@Resource
    private TimerService timerService;
	
	@Inject 
	private AcaoService serv;
	
	@Inject 
	private VoluntarioService vServ;
	
	@Inject 
	private PessoaService pServ;

    @PostConstruct
    private void construct() {
        final TimerConfig programadas = new TimerConfig("programadas", false);
        final TimerConfig volAtivos = new TimerConfig("voluntariosAtivos", false);
        timerService.createCalendarTimer(new ScheduleExpression().minute(0).hour(12), programadas);
        timerService.createCalendarTimer(new ScheduleExpression().dayOfWeek("Fri").minute(0).hour(22), volAtivos);

    }

    @Timeout
    public void timeout(Timer timer) {
        if ("programadas".equals(timer.getInfo())) {
            List<Acao> lst = serv.listAcoesProgramadasParaDecisao();
            if(lst!=null && !lst.isEmpty()) {
            	try {
					serv.enviarEmailProgramadasParaDecisao(lst);
				} catch (TSentEmailException | EmailBusinessException e) {
					e.printStackTrace();
				}
            }
        }else if("voluntariosAtivos".equals(timer.getInfo())) {
        	Calendar today = Calendar.getInstance();
        	Calendar startDate = Calendar.getInstance();
        	startDate.add(Calendar.MONTH, -2);
        	List<Acao> l = serv.pesquisar(null, null, startDate.getTime(), today.getTime(), null, null, null);
        
        	Map<Pessoa, Integer> t = new HashMap<>();
        	int total =  0;
        	for(Acao a : l) {
        		if(a.getStatus().equals("Agendada") || a.getStatus().equals("Executada")) {
        			total++;
	        		Set<Voluntario> vs = a.getVoluntarios();
	        		if(vs!=null && !vs.isEmpty()) {
	        			for(Voluntario v : vs) {
	        				Pessoa p = v.getPessoa();
	        				if(t.containsKey(p)) {
	        					Integer i = t.get(p);
	        					i++;
	        					t.put(p, i);
	        				}else
	        					t.put(p, 1);
	        			}
	        		}
        		}
        	}
        	
        	for(Pessoa p : t.keySet()) {
        		Integer c = t.get(p);
        		double perc = (c*100)/total;
        		if(perc>=40)
        			p.setStatusVoluntario("4");
        		else
        			p.setStatusVoluntario("3");
        		
        		try {
					pServ.save(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
        		
        	}
        
        }
    }
}
