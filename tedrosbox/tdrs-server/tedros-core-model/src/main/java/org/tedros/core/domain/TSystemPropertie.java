/**
 * 
 */
package org.tedros.core.domain;

/**
 * @author Davis Gordon
 *
 */
public enum TSystemPropertie {
	
	DEFAULT_COUNTRY_ISO2("sys.country.iso2","Defines a default country iso2 code to format currency and date"),
	TOTAL_PAGE_HISTORY ("sys.page.history","Defines the number of views opened in the history of pages"),
	OWNER ("sys.owner","Defines the name of the system owner"),
	TOKEN ("sys.token","Set Tedros license key"),
	ORGANIZATION ("sys.org","Defines the company trademark to display in reports"),
	REPORT_LOGOTYPE("sys.report.logo","Sets the trademark logo to be displayed in reports"),
	SMTP_USER ("sys.smtp.email","Define SMTP user email"),
	SMTP_PASS ("sys.smtp.pass","Set SMTP user password"),
	SMTP_HOST ("sys.smtp.host","Define SMTP host server"),
	SMTP_PORT ("sys.smtp.port","Defines SMTP server port"),
	SMTP_SOCKET_PORT ("sys.smtp.socket.port","Defines SMTP server socket port"),
	NOTIFY_INTERVAL_TIMER ("sys.notify.interval","Defines the interval time in minutes for sending e-mails queued by the Notify module"),
	OPENAI_KEY("sys.openai.key","Define the OpenAi Api key"),
	AI_ENABLED("sys.ai.enabled","Enable Teros artificial intelligence. Set true or false");
	
	private String value;
	private String description;

	/**
	 * @param value
	 */
	private TSystemPropertie(String value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	

}
