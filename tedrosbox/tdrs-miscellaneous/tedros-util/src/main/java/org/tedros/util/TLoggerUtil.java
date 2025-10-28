package org.tedros.util;



import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tedros.function.Action;

public class TLoggerUtil {
	
	private static final String DEBUG_LOGGER = ">";
	private static final String DEBUG_PROPERTIE = "debug";
	private static final String PARSER_PROPERTIE = "parser";
	private static final String FORM_PROPERTIE = "form";
	private static final String FILE = "logging.properties";
	
	private static Logger LOGGER;
	
	private static boolean DEBUG_ENABLED;
	private static boolean FORM_ENGINE_ENABLED;
	private static boolean PARSER_DEBUG_ENABLED;
		
	private TLoggerUtil() {
		
	}
	
	static{
		Properties properties = new Properties();		
        try (FileInputStream input = new FileInputStream(TFileUtil.getTedrosFolderPath()
				+TedrosFolder.CONF_FOLDER.getFolder()+FILE)) {
            if (input != null) 
            	properties.load(input);
        } catch (IOException ex) {
            System.out.println("The file "+TFileUtil.getTedrosFolderPath()
				+TedrosFolder.CONF_FOLDER.getFolder()+FILE
				+" not found! These file can be used to set the propertie debug=true");
        }

        // define the level
        DEBUG_ENABLED = BooleanUtils.toBoolean(properties.getProperty(DEBUG_PROPERTIE));
        FORM_ENGINE_ENABLED = BooleanUtils.toBoolean(properties.getProperty(FORM_PROPERTIE));
        PARSER_DEBUG_ENABLED = BooleanUtils.toBoolean(properties.getProperty(PARSER_PROPERTIE));
        
        LOGGER = DEBUG_ENABLED 
        		? LoggerFactory.getLogger(DEBUG_LOGGER)
        				: LoggerFactory.getLogger(TLoggerUtil.class);
        
        // init
        LOGGER.info("INFO Enabled");
        LOGGER.debug("DEBUG Enabled");
        LOGGER.error("ERROR Enabled");
	}
	
	public static <T> Logger getLogger(Class<T> cls) {
		return LoggerFactory.getLogger(cls);
	}
	
	public static <T> void info(Class<T> cls, String msg) {
		LoggerFactory.getLogger(cls).info(msg);
	}
	
	public static <T> void warn(Class<T> cls, String msg) {
		LoggerFactory.getLogger(cls).warn(msg);
	}
	
	public static <T> void error(Class<T> cls, String msg, Throwable exception) {
		LoggerFactory.getLogger(cls).error(msg, exception);
	}
	
	/*
	 * public static void debug(String msg) { LOGGER.debug(msg); }
	 */
	
	public static <T> void debug(Class<T> cls, String msg) {
		LOGGER.debug("{} -> {}", String.format("%-36s", 
				TClassUtil.abbreviateClassName(cls.getName(),36)), msg);
	}
	
	public static void debug(String msg, Throwable exception) {
		LOGGER.debug(msg, exception);
	}
	
	public static <T> void error(String msg, Throwable exception) {
		LOGGER.error(msg, exception);
	}
	
	public static <T> Logger getLogger() {
		return LOGGER;
	}
	
	
	
	public static <T> void timeComplexity(Class<T> cls, String detail, Action action) {
		
		String id = UUID.randomUUID().toString().substring(0, 8);
		
		debug(cls, "Trace:"+id+"|"+ (detail!=null ? detail : ""));
		long startTime = System.nanoTime();
		action.execute();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        double durationInMillis = duration / 1_000_000.0;
        double durationInSeconds = duration / 1_000_000_000.0;
        debug(cls, "Trace:"+id+"|Execution time: "+ durationInMillis + " Millis or "+ durationInSeconds + " seconds");
	}
	
	public static <T> void splitDebugLine(Class<T> cls, char character) {
		debug(cls, getSplitLine(character));
	}
	
	public static String getSplitLine(char character) {
		return StringUtils.repeat(character, 50);
	}

	public static boolean isDebugEnabled() {
		return DEBUG_ENABLED;
	}

	public static boolean isParserDebugEnabled() {
		return DEBUG_ENABLED && PARSER_DEBUG_ENABLED;
	}
	
	public static boolean isFormEngineEnabled() {
		return DEBUG_ENABLED && FORM_ENGINE_ENABLED;
	}
	
	

}
