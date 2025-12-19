package org.tedros.mail;

import org.tedros.server.util.TSMTPUtil;
import org.tedros.server.util.TSentEmailException;

public class SendEmailPoc {
	
	public static void main(String[] args) {
		try {
			
			String host = System.getenv("SMTP_HOST");
			String port = System.getenv("SMTP_PORT");
			String user = System.getenv("SMTP_USER");
			String pass = System.getenv("SMTP_PASS");
			
			
			TSMTPUtil.getInstance(host, port, "javax.net.ssl.SSLSocketFactory", "true", port, user, pass)
			.send(true, user, "@gmail.com", "subject", "content", true);
			
		} catch (TSentEmailException e) {
			e.printStackTrace();
		}
	}

}
