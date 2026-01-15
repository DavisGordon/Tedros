package org.tedros.core.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A Logger formatter
 * */
class TLoggerFormatter extends Formatter{
	
	private static final String SEPARATOR = " ";
	private static final String PIPE = "|";
	
	/**
	 * Formats the log record
	 * */
	@Override
	public String format(LogRecord logRecord) {
		
		StringBuffer sbf = new StringBuffer();
		
		sbf.append(calcDate(logRecord.getMillis()) + SEPARATOR);
		sbf.append(logRecord.getLevel().getName() + SEPARATOR);
		sbf.append(logRecord.getSourceClassName() + SEPARATOR);
		sbf.append(logRecord.getSourceMethodName() + SEPARATOR);
		sbf.append(SEPARATOR + PIPE + SEPARATOR + logRecord.getMessage()+"\n");
		
		return sbf.toString();
	}
	
	private String calcDate(long millisecs) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date resultdate = new Date(millisecs);
        return dateFormat.format(resultdate);
	}
	
}
