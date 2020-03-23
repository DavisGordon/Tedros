package com.tedros.fxapi.html;

import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TLayoutType;

public class THtmlFieldSetGenerator implements ITHtmlGenerator{
	
	private StringBuffer legend;
	private static String LEGEND = THtmlConstant.LABEL;
	private static String CONTENT = THtmlConstant.CONTENT;
	private THtmlLayoutGenerator tHtmlLayoutGenerator;
	
	private StringBuffer fieldSetTemplate = new StringBuffer("<fieldset><legend>"+LEGEND+"</legend>"+CONTENT+"</fieldset>");
	
	public THtmlFieldSetGenerator(TLayoutType tLayoutType) {
		this.tHtmlLayoutGenerator = new THtmlLayoutGenerator(tLayoutType);
		this.legend = new StringBuffer("");
	}
	
	public THtmlFieldSetGenerator(String legend, TLayoutType tLayoutType) {
		this.tHtmlLayoutGenerator = new THtmlLayoutGenerator(tLayoutType);
		this.legend = new StringBuffer(legend);
	}
	
	public void setLegend(String legend){
		this.legend.append(legend);
	}
	
	public void addContent(String content){
		this.tHtmlLayoutGenerator.addContent(content);
	}
	
	public String getHtml(){
		return fieldSetTemplate.toString()
				.replace(LEGEND, legend.toString())
				.replace(CONTENT, tHtmlLayoutGenerator.getHtml());
	}

}
