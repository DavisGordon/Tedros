/**
 * 
 */
package com.tedros.core.ejb.timer;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.math.NumberUtils;

import com.tedros.core.domain.DomainPropertie;
import com.tedros.core.ejb.producer.Item;
import com.tedros.core.ejb.service.TNotifyService;
import com.tedros.core.notify.model.TNotify;

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
	
	@Inject 
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
        					.minute(interval), notify);
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
    		List<TNotify> l = serv.process();
    		for(TNotify e : l)
				try {
					serv.save(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
    	}else {
    		try {
				TNotify e = serv.process((String)timer.getInfo());
				serv.save(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
        
    }
}
