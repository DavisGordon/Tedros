/**
 * 
 */
package org.somos.model;

/**
 * @author Davis Gordon
 *
 */
final class SiteHelper {
	public static String cleanHtml(String s) {
		if(s!=null) {
			s = s.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
			s = s.replace("</body></html>", "");
			s = s.replace("Segoe UI", "ms-r");
			
		}
		return s;
	}
	
}
