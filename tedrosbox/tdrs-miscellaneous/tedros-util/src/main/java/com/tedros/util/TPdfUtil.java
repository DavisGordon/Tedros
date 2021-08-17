/**
 * 
 */
package com.tedros.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

/**
 * @author Davis Gordon
 *
 */
public final class TPdfUtil {

	/**
	 * 
	 */
	private TPdfUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static void convert(String html, String output) throws FileNotFoundException, IOException  {
		org.w3c.dom.Document doc = html5ParseDocument(html);
		 generate(doc, output); 
	}

	/**
	 * @param output
	 * @param doc
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void generate(org.w3c.dom.Document doc, String output) throws IOException, FileNotFoundException {
		try (OutputStream os = new FileOutputStream(output)){
			 PdfRendererBuilder builder = new PdfRendererBuilder();
	         builder.useFastMode();
	         builder.withW3cDocument(doc, null);
	         builder.toStream(os);
	         builder.run();
	        }
	}
	
	private static org.w3c.dom.Document html5ParseDocument(String html) 
	{
		org.jsoup.nodes.Document doc;
		doc = Jsoup.parse(html);	
		
		// Should reuse W3CDom instance if converting multiple documents.
		return new W3CDom().fromJsoup(doc);
	}

}
