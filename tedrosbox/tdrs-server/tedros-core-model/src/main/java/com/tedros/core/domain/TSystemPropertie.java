/**
 * 
 */
package com.tedros.core.domain;

/**
 * @author Davis Gordon
 *
 */
public enum TSystemPropertie {
	
	OWNER ("sys.owner"),
	TOKEN ("sys.token"),
	ORGANIZATION ("sys.org"),
	REPORT_LOGOTYPE("sys.report.logo"),
	SMTP_USER ("sys.smtp.email"),
	SMTP_PASS ("sys.smtp.pass"),
	SMTP_HOST ("sys.smtp.host"),
	SMTP_PORT ("sys.smtp.port"),
	SMTP_SOCKET_PORT ("sys.smtp.socket.port"),
	NOTIFY_INTERVAL_TIMER ("sys.notify.interval.timer");
	
	private String value;

	/**
	 * @param value
	 */
	private TSystemPropertie(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	

}
