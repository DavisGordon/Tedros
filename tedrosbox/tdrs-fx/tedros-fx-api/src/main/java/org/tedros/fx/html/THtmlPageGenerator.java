package org.tedros.fx.html;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.domain.THtmlConstant;
import org.tedros.fx.domain.TStyleParameter;

public class THtmlPageGenerator implements ITHtmlGenerator {
	
	private List<StringBuffer> contents;

	private static final StringBuffer HTMLTEMPLATE = new StringBuffer("<html>"+THtmlConstant.CONTENT+"</html>");
	private static final StringBuffer BODYTEMPLATE = new StringBuffer("<body style='"+THtmlConstant.STYLE+"' "+THtmlConstant.ATTRIBUTE+" >"+THtmlConstant.CONTENT+"</body>");
	private static final StringBuffer HEADTEMPLATE = new StringBuffer("<head><title>"+THtmlConstant.LABEL+"</title><meta charset='utf-8' /><meta name='viewport' content='width=device-width'>"+THtmlConstant.CONTENT+"</head>");
	private static final StringBuffer STYLETEMPLATE = new StringBuffer("<style type='text/css'>"
																		+ " "+THtmlConstant.STYLE+" "
																		+ " #twrapper{ "
																		+ " 			-moz-column-count: 3; "
																		+ " 			-moz-column-gap: 1em; "
																	    + "				-webkit-column-count: 3; "
																	    + " 			-webkit-column-gap: 1em; "
																	    + " 			column-count: 3; "
																	    + " 			column-gap: 1em; "
																	    + " 			padding: 8px; "
																	    + " 			border-top:1px solid "+TStyleParameter.PANEL_BACKGROUND_COLOR+"; "
																	    + " } "
																	    + " .titm{ "
																	    + " 		display:inline-block; "
																	    + " 		width:100%; "
																	    + " 		margin-right:1em; "
																	    + " 		border-right:1px solid "+TStyleParameter.PANEL_BACKGROUND_COLOR+"; "
															    		+ " } "
															    		+ " .titm:nth-child(3n+1){ "
																	    + " 		clear:left; "
																	    + " }"
																		+ "</style>");
	private static final StringBuffer JAVASCRIPTTEMPLATE = new StringBuffer("<script type='text/javascript'>"+THtmlConstant.CONTENT+"</script>");
	
	private String title;
	private String style;
	private String bodyTemplateStyle;
	private String bodyTemplateAttribute;
	private String javascriptOnTop;
	private String javascriptOnBottom;
	private String detailBorderColor;
	
	private String getValue(String content){
		return StringUtils.defaultIfBlank(content, "");
	}
	
	public THtmlPageGenerator() {
		contents = new ArrayList<>();
	}
	
	public void addContent(String content){
		this.contents.add(new StringBuffer(content));
	}
	
	public String getHtml(){
		
		StringBuffer sbf = new StringBuffer();
		for (StringBuffer sb : contents) 
			sbf.append(sb.toString());
		
		StringBuffer jsTop = new StringBuffer(JAVASCRIPTTEMPLATE.toString().replace(THtmlConstant.CONTENT, getValue(javascriptOnTop)));
		StringBuffer jsBottom = new StringBuffer(JAVASCRIPTTEMPLATE.toString().replace(THtmlConstant.CONTENT,getValue(javascriptOnBottom)));
		StringBuffer stylePage = new StringBuffer(STYLETEMPLATE.toString().replace(THtmlConstant.STYLE, getValue(style)).replace(TStyleParameter.PANEL_BACKGROUND_COLOR, detailBorderColor));
		StringBuffer body = new StringBuffer(BODYTEMPLATE.toString()
				.replace(THtmlConstant.STYLE, getValue(bodyTemplateStyle))
				.replace(THtmlConstant.ATTRIBUTE, getValue(bodyTemplateAttribute))
				.replace(THtmlConstant.CONTENT, getValue(sbf.toString())));
		
		StringBuffer head = new StringBuffer(HEADTEMPLATE.toString().replace(THtmlConstant.LABEL, getValue(title))
				.replace(THtmlConstant.CONTENT, stylePage.toString() + jsTop.toString()));
		
		
		return HTMLTEMPLATE.toString().replace(THtmlConstant.CONTENT, head.toString() + body.toString() + jsBottom.toString());
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the bodyTemplateStyle
	 */
	public String getBodyTemplateStyle() {
		return bodyTemplateStyle;
	}

	/**
	 * @param bodyTemplateStyle the bodyTemplateStyle to set
	 */
	public void setBodyTemplateStyle(String bodyTemplateStyle) {
		this.bodyTemplateStyle = bodyTemplateStyle;
	}

	/**
	 * @return the bodyTemplateAttribute
	 */
	public String getBodyTemplateAttribute() {
		return bodyTemplateAttribute;
	}

	/**
	 * @param bodyTemplateAttribute the bodyTemplateAttribute to set
	 */
	public void setBodyTemplateAttribute(String bodyTemplateAttribute) {
		this.bodyTemplateAttribute = bodyTemplateAttribute;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return the javascriptOnTop
	 */
	public String getJavascriptOnTop() {
		return javascriptOnTop;
	}

	/**
	 * @param javascriptOnTop the javascriptOnTop to set
	 */
	public void setJavascriptOnTop(String javascriptOnTop) {
		this.javascriptOnTop = javascriptOnTop;
	}

	/**
	 * @return the javascriptOnBottom
	 */
	public String getJavascriptOnBottom() {
		return javascriptOnBottom;
	}

	/**
	 * @param javascriptOnBottom the javascriptOnBottom to set
	 */
	public void setJavascriptOnBottom(String javascriptOnBottom) {
		this.javascriptOnBottom = javascriptOnBottom;
	}

	/**
	 * @return the detailBorderColor
	 */
	public String getDetailBorderColor() {
		return detailBorderColor;
	}

	/**
	 * @param detailBorderColor the detailBorderColor to set
	 */
	public void setDetailBorderColor(String detailBorderColor) {
		this.detailBorderColor = detailBorderColor;
	}

}
