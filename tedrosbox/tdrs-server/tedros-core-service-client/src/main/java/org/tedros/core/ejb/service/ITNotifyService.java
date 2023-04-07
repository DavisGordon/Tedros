/**
 * 
 */
package org.tedros.core.ejb.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.tedros.core.notify.model.TNotify;
import org.tedros.server.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@Remote
@Local
public interface ITNotifyService extends ITEjbService<TNotify> {

	void process(TNotify e);
	
	TNotify process(String refCode) throws Exception;
	
	List<TNotify> process() ;
}
