/**
 * 
 */
package org.tedros.core.logging;

import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * @author Davis Gordon
 *
 */
public final class TLog {


	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private TLog() {
	}
	
	public static void info(Supplier<String> supplierrMsg) {
		LOGGER.info(supplierrMsg);
	}
	
	public static void severe(Supplier<String> supplierrMsg) {
		LOGGER.severe(supplierrMsg);
	}

}
