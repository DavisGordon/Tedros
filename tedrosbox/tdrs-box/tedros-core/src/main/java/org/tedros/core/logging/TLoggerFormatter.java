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
	public String format(LogRecord record) {
		
		StringBuffer sbf = new StringBuffer();
		
		sbf.append(calcDate(record.getMillis()) + SEPARATOR);
		sbf.append(record.getLevel().getName() + SEPARATOR);
		sbf.append(record.getSourceClassName() + SEPARATOR);
		sbf.append(record.getSourceMethodName() + SEPARATOR);
		sbf.append(SEPARATOR + PIPE + SEPARATOR + record.getMessage()+"\n");
		
		return sbf.toString();
	}
	
	private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
	}
	
}
