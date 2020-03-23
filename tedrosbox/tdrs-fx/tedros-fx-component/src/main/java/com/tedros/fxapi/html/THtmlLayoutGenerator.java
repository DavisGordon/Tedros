package com.tedros.fxapi.html;

import java.util.ArrayList;
import java.util.List;

import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TLayoutType;

public class THtmlLayoutGenerator implements ITHtmlGenerator {
	
	private TLayoutType tLayoutType;
	private List<StringBuffer> contents;
	
	private StringBuffer rowTemplate = new StringBuffer("<div style='margin-bottom:8px'>"+THtmlConstant.CONTENT+"</div>");
	private StringBuffer colTemplate = new StringBuffer("<span style='margin-right:8px; display: inline-block;'>"+THtmlConstant.CONTENT+"</span>");
	private StringBuffer flowTemplate = new StringBuffer("<div id='twrapper'>"+THtmlConstant.CONTENT+"</div>");
	
	public THtmlLayoutGenerator(TLayoutType tLayoutType) {
		if(tLayoutType==null)
			this.tLayoutType = TLayoutType.FLOWPANE;
		this.tLayoutType = tLayoutType;
		contents = new ArrayList<>();
	}
	
	public void addContent(String content){
		this.contents.add(new StringBuffer(content));
	}
	
	public String getHtml(){
		StringBuffer sbf = new StringBuffer();
		switch (tLayoutType) {
		case VBOX:
			for (StringBuffer sb : contents) 
				sbf.append(rowTemplate.toString().replace(THtmlConstant.CONTENT, sb.toString()));
			return sbf.toString();
			
		case HBOX:
			for (StringBuffer sb : contents) 
				sbf.append(colTemplate.toString().replace(THtmlConstant.CONTENT, sb.toString()));
			return sbf.toString();
			
		case FLOWPANE:
		default :
			for (StringBuffer sb : contents) 
				sbf.append("<div class='titm'>"+sb.toString()+"</div>");
			return flowTemplate.toString().replace(THtmlConstant.CONTENT, sbf.toString());		
		}
		
	}

}
