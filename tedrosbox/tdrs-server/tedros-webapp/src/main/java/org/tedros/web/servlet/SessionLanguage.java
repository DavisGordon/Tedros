/**
 * 
 */
package org.tedros.web.servlet;

import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tedros.web.bean.WebLanguageBean;
/**
 * @author Davis Gordon
 *
 */
@WebServlet(name="SessionLanguage",
urlPatterns= {"/en/lang", "/pt/lang","/lang"})
public class SessionLanguage extends HttpServlet {

	private static final long serialVersionUID = -3035965625139672187L;

	private static final String TOKEN = "tdrslang";
	
	@Inject @Any
	private WebLanguageBean lang;
	
	private static String LANG = "lng";
	
	@Override
	protected void doPost(HttpServletRequest httpRequest, HttpServletResponse resp) throws ServletException, IOException {

		String l = httpRequest.getParameter(LANG);
		
		String pCookieName = TOKEN;
		Cookie cookie = new Cookie(pCookieName, l.trim());
		cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
		cookie.setHttpOnly(false);
		cookie.setPath("/");
		resp.addCookie(cookie);
		lang.set(l.trim());

		resp.setStatus(SC_OK);
	}

}
