/**
 * 
 */
package org.tedros.core.ejb.timer;

import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.NoSuchObjectLocalException;
import jakarta.ejb.ScheduleExpression;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timeout;
import jakarta.ejb.Timer;
import jakarta.ejb.TimerConfig;
import jakarta.ejb.TimerService;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.commons.lang3.math.NumberUtils;
import org.tedros.core.cdi.producer.Item;
import org.tedros.core.domain.DomainPropertie;
import org.tedros.core.ejb.service.TNotifyService;
import org.tedros.core.notify.model.TNotify;
import org.tedros.server.exception.TBusinessException;

/**
 * @author Davis Gordon
 *
 */
@Startup
@Singleton
@Lock(LockType.READ) // allows timers to execute in parallel
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TNotifyTimer {
	
	private final static String DEFAULT = "DEFAULT_TIMER";
	
	private Timer defaultTimer;
	
	@Inject
	@Named(DomainPropertie.NOTIFY_INTERVAL_TIMER)
	private Item<String> initialInterval;

	@Resource
    private TimerService timerService;
	
	@EJB
	private TNotifyService serv;
		
    @PostConstruct
    private void construct() {
    	String interval = initialInterval.get();
        start(interval);
    }

    public void stop() {
    	if(defaultTimer!=null) {
    		defaultTimer.cancel();
    		timerService.getAllTimers().remove(defaultTimer);
    		defaultTimer = null;
    	}
    }
    
	/**
	 * @param interval
	 */
	public void start(String interval) {
		stop();
        if(interval!=null && NumberUtils.isCreatable(interval)) {
        	final TimerConfig notify = new TimerConfig(DEFAULT, true);
        	defaultTimer = timerService
        			.createCalendarTimer(new ScheduleExpression()
        					.minute("*/"+interval).hour("*"), notify);
        }
		
	}
    
    public void schedule(TNotify e) {
    	timerService.createTimer(e.getScheduleTime(), e.getRefCode());
    }
    
    public void cancel(TNotify e) {
    	timerService.getAllTimers().stream().filter(t->{
    		try {
    			return t.getInfo().equals(e.getRefCode());
    		}catch(NoSuchObjectLocalException ex) {
    			return false;
    		}
    	}).forEach(t->{
    		try {
    			t.cancel();
    		}catch(NoSuchObjectLocalException ex) {
    			ex.printStackTrace();
    		}
    	});;
    }

    @Timeout
    public void timeout(Timer timer) {
    	if(DEFAULT.equals(timer.getInfo())) {
    		List<TNotify> l = process();
    		for(TNotify e : l)
				try {
					serv.save(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
    	}else {
    		try {
				TNotify e = process((String)timer.getInfo());
				serv.save(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}        
    }
    
    private List<TNotify> process()  {
		List<TNotify> l = serv.listToProcess();
		if(l!=null && !l.isEmpty())
			for(TNotify e : l) 
				serv.queue(e);
		return l;
	}
	
	private TNotify process(String refCode) throws Exception {
		if(refCode!=null) {
			TNotify ex = new TNotify();
			ex.setRefCode(refCode);
			ex = serv.find(ex);
			if(ex!=null) {
				serv.queue(ex);
				return ex;
			}else
				throw new TBusinessException("#{tedros.fxapi.message.no.data.found}");
		}else
			throw new IllegalArgumentException("The argument cannot be null");
	}
    
    
}
