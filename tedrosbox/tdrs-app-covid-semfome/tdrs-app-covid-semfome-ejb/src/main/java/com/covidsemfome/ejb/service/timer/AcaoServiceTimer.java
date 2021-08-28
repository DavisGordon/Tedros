/**
 * 
 */
package com.covidsemfome.ejb.service.timer;

import java.util.List;

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

import com.covidsemfome.ejb.exception.EmailBusinessException;
import com.covidsemfome.ejb.service.AcaoService;
import com.covidsemfome.model.Acao;
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

    @PostConstruct
    private void construct() {
        final TimerConfig programadas = new TimerConfig("programadas", false);
        timerService.createCalendarTimer(new ScheduleExpression().minute(0).hour(12), programadas);
        //timerService.createCalendarTimer(new ScheduleExpression().second("*").minute("*").hour("*"), checkOnTheDaughters);
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
        } 
    }
}
