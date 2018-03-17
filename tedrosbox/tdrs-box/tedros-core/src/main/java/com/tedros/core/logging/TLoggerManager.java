package com.tedros.core.logging;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolderEnum;

public class TLoggerManager {

	static private ConsoleHandler consoleHandler;
	static private FileHandler fileHandler;
    static private Formatter formatterTxt;

    static public void setup() throws IOException {
            
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.setLevel(Level.FINEST);
            
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers[0] instanceof ConsoleHandler) {
                    rootLogger.removeHandler(handlers[0]);
            }
            
            formatterTxt = new TLoggerFormatter();
            //formatterTxt = new SimpleFormatter();
            
            consoleHandler = new ConsoleHandler();
            fileHandler = new FileHandler(TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.LOG_FOLDER.getFolder() + "Logging.txt");
            
            fileHandler.setFormatter(formatterTxt);
            consoleHandler.setFormatter(formatterTxt);
            
            logger.addHandler(fileHandler);
            logger.addHandler(consoleHandler);
    }
}
