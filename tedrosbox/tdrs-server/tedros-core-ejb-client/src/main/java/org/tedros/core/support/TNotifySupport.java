package org.tedros.core.support;

import java.util.Date;

import org.tedros.core.notify.model.TState;

import jakarta.ejb.Remote;

@Remote
public interface TNotifySupport {
	
	/**
	 * Gets the notification state.
	 * 
	 * @param refCode
	 * @return the notification state
	 * @throws Exception
	 */
	TState getState(String refCode)  throws Exception;

	/**
	 * Cancel a notification by its reference code.
	 * 
	 * @param refCode
	 * @throws Exception
	 */
	void cancel(String refCode) throws Exception;
	
	/**
	 * Sends a notification and return the referral code.
	 * 
	 * @param emailTo
	 * @param subject
	 * @param content
	 * @return the reference code.
	 * @throws Exception
	 */
	String send(String emailTo, String subject, String content) throws Exception;
	
	/**
	 * Adds a notification to the queue and return the referral code.
	 * 
	 * @param emailTo
	 * @param subject
	 * @param content
	 * @return the reference code.
	 * @throws Exception
	 */
	String toQueue(String emailTo, String subject, String content) throws Exception;
	 
	/**
	 * Schedule a notification and return the referral code.
	 * 
	 * @param emailTo
	 * @param subject
	 * @param content
	 * @param dateTime
	 * @return the reference code.
	 * @throws Exception
	 */
	String toSchedule(String emailTo, String subject, String content, Date dateTime) throws Exception;

	
}
