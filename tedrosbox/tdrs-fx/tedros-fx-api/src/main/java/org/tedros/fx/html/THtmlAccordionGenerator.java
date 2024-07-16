/**
 * 
 */
package org.tedros.fx.html;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.tedros.fx.domain.THtmlConstant;
import org.tedros.fx.domain.TStyleParameter;
import org.tedros.util.TLoggerUtil;

/**
 * @author Davis Gordon
 */
public class THtmlAccordionGenerator implements ITHtmlGenerator{
	
	private final StringBuffer templateParam;
	private List<StringBuffer> sections;
	
	private String accordionColor = "#eee";
	private String contentColor = "#ffffff";
	private String accordionTextColor = "444";
	private String accordionTextSize = "1.2";
	private String contentTextColor = "#000000";
	private String contentTextSize = "1.2";
	
	
	public THtmlAccordionGenerator() {
		templateParam = new StringBuffer();
		templateParam.append("<button class='accordion'>%s</button>");
		templateParam.append("<div class='panel'>");
		templateParam.append("<p>%s</p>");
		templateParam.append("</div>");
		
		sections = new ArrayList<>();
	}
		
	public void addSection(String title, String content){
		sections.add(new StringBuffer(String.format(templateParam.toString(), title, content)));
	}
	
	public String getHtml(){
		
		URL url = this.getClass().getClassLoader().getResource("com/tedros/fxapi/html/template/accordion.tpl.html");
		File f;
		try {
			f = new File(url.toURI());
			StringBuffer sbf = new StringBuffer(FileUtils.readFileToString(f));
			StringBuffer sbfSections = new StringBuffer();
			
			for (StringBuffer sb : sections) {
				sbfSections.append(sb.toString());
			}
			
			return sbf.toString().replace(THtmlConstant.CONTENT, sbfSections.toString())
					.replace(TStyleParameter.PANEL_BACKGROUND_COLOR, accordionColor)
					.replace(TStyleParameter.READER_BACKGROUND_COLOR, contentColor)
					.replace(TStyleParameter.PANEL_TEXT_COLOR, accordionTextColor)
					.replace(TStyleParameter.PANEL_TEXT_SIZE, accordionTextSize)
					.replace(TStyleParameter.READER_TEXT_COLOR, contentTextColor)
					.replace(TStyleParameter.READER_TEXT_SIZE, contentTextSize);
			
		} catch (URISyntaxException | IOException e) {
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
		
		return "";
		
	}

	/**
	 * @return the accordionColor
	 */
	public String getAccordionColor() {
		return accordionColor;
	}

	/**
	 * @param accordionColor the accordionColor to set
	 */
	public void setAccordionColor(String accordionColor) {
		this.accordionColor = accordionColor;
	}

	/**
	 * @return the contentColor
	 */
	public String getContentColor() {
		return contentColor;
	}

	/**
	 * @param contentColor the contentColor to set
	 */
	public void setContentColor(String contentColor) {
		this.contentColor = contentColor;
	}

	/**
	 * @return the accordionTextColor
	 */
	public String getAccordionTextColor() {
		return accordionTextColor;
	}

	/**
	 * @param accordionTextColor the accordionTextColor to set
	 */
	public void setAccordionTextColor(String accordionTextColor) {
		this.accordionTextColor = accordionTextColor;
	}

	/**
	 * @return the accordionTextSize
	 */
	public String getAccordionTextSize() {
		return accordionTextSize;
	}

	/**
	 * @param accordionTextSize the accordionTextSize to set
	 */
	public void setAccordionTextSize(String accordionTextSize) {
		this.accordionTextSize = accordionTextSize;
	}

	/**
	 * @return the contentTextColor
	 */
	public String getContentTextColor() {
		return contentTextColor;
	}

	/**
	 * @param contentTextColor the contentTextColor to set
	 */
	public void setContentTextColor(String contentTextColor) {
		this.contentTextColor = contentTextColor;
	}

	/**
	 * @return the contentTextSize
	 */
	public String getContentTextSize() {
		return contentTextSize;
	}

	/**
	 * @param contentTextSize the contentTextSize to set
	 */
	public void setContentTextSize(String contentTextSize) {
		this.contentTextSize = contentTextSize;
	}
	
	
}
