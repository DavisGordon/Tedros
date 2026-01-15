package org.tedros.core.logging;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.tedros.util.TFileUtil;
import org.tedros.util.TedrosFolder;

/**
 * The Logger manager
 * */
public class TLoggerManager {

	private static ConsoleHandler consoleHandler;
	private static FileHandler fileHandler;
	private static Formatter formatterTxt;
	
	private TLoggerManager() {
		
	}

    public static void setup()  {
            
            try {
				Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
				logger.setLevel(Level.FINEST);
				
				Logger rootLogger = Logger.getLogger("");
				Handler[] handlers = rootLogger.getHandlers();
				if (handlers[0] instanceof ConsoleHandler) {
				        rootLogger.removeHandler(handlers[0]);
				}
				
				formatterTxt = new TLoggerFormatter();
				
				consoleHandler = new ConsoleHandler();
				//fileHandler = new FileHandler(TFileUtil.getTedrosFolderPath()+TedrosFolder.LOG_FOLDER.getFolder() + "system.log");
				
				//fileHandler.setFormatter(formatterTxt);
				consoleHandler.setFormatter(formatterTxt);
				
				//logger.addHandler(fileHandler);
				logger.addHandler(consoleHandler);
			} catch (SecurityException e) {
				e.printStackTrace();
			}
    }
}
