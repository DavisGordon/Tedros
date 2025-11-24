/**
 * 
 */
package org.tedros.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public final class TLoggerUtil {
	
	private final Logger logger;	
	
	public static final String WRAPPER_IN = "\n\n============================== START ========================\n";
	
	public static final String START = "===> ";
	
	public static final String WRAPPER_OUT = "\n============================== END ==========================\n";
	
	private TLoggerUtil(Logger logger) {
		this.logger = logger;
	}
	
	@SuppressWarnings("rawtypes")
	public static TLoggerUtil create(Class clazz) {
		return new TLoggerUtil(LoggerFactory.getLogger(clazz)); 
	}
	
	public void info(String msg) {
		logger.info(WRAPPER_IN + START + msg + WRAPPER_OUT);
	}
	
	public void info(String msg, Object...params) {
		logger.info(WRAPPER_IN + START + msg + WRAPPER_OUT, params);
	}
	
	public void warn(String msg) {
		logger.warn(WRAPPER_IN + START + msg + WRAPPER_OUT);
	}
	
	public void warn(String msg, Object...params) {
		logger.warn(WRAPPER_IN + START + msg + WRAPPER_OUT, params);
	}
	
	public void debug(String msg) {
		logger.debug(WRAPPER_IN + START + msg + WRAPPER_OUT);
	}
	
	public void debug(String msg, Object...params) {
		logger.debug(WRAPPER_IN + START + msg + WRAPPER_OUT, params);
	}
	
	public void error(String msg) {
		logger.error(WRAPPER_IN + START + msg + WRAPPER_OUT);
	}
	
	public void error(String msg, Object...params) {
		logger.error(WRAPPER_IN + START + msg + WRAPPER_OUT, params);
	}
	
	public void error(String msg, Throwable throwable) {
		logger.error(WRAPPER_IN + START + msg + WRAPPER_OUT, throwable);
	}

}
