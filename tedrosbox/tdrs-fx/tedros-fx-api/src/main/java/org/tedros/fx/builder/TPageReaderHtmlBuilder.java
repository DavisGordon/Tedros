/**
 * 
 */
package org.tedros.fx.builder;

import org.tedros.core.style.TStyleResourceValue;
import org.tedros.fx.annotation.reader.TFormReaderHtml;
import org.tedros.fx.html.THtmlPageGenerator;
import org.tedros.fx.reader.THtmlReader;
import org.tedros.util.TStripTagUtil;

/**
 * @author Davis Gordon
 *
 */
public class TPageReaderHtmlBuilder extends TBuilder implements
		ITReaderHtmlBuilder<TFormReaderHtml, StringBuffer> {
	
	private final TStripTagUtil tStripTagUtil;
	
	public TPageReaderHtmlBuilder(){
		tStripTagUtil = new TStripTagUtil();
	}

	@Override
	public THtmlReader build(TFormReaderHtml tAnnotation, StringBuffer sbf)
			throws Exception {
		
		THtmlPageGenerator pageGenerator = new THtmlPageGenerator();
		pageGenerator.setTitle(tAnnotation.title());
		pageGenerator.setBodyTemplateAttribute(replaceTags(tAnnotation.bodyTemplateAttribute()));
		pageGenerator.setBodyTemplateStyle(replaceTags(tAnnotation.bodyTemplateStyle()));
		pageGenerator.setJavascriptOnTop(replaceTags(tAnnotation.javascriptOnTop()));
		pageGenerator.setJavascriptOnBottom(replaceTags(tAnnotation.javascriptOnBottom()));
		pageGenerator.setStyle(replaceTags(tAnnotation.style()));
		pageGenerator.addContent(sbf.toString());
		pageGenerator.setDetailBorderColor(TStyleResourceValue.PANEL_BACKGROUND_COLOR.customStyle());
		
		return new THtmlReader(pageGenerator.getHtml());
	}

	private String replaceTags(String styleContent) {
		if(tStripTagUtil.isTagPresent(styleContent)){
			String[] keys = tStripTagUtil.getTags(styleContent);
			for (String key : keys) {
				TStyleResourceValue tResourceValue = TStyleResourceValue.getByName(key);
				if(tResourceValue!=null){
					String value = tResourceValue.customStyle();
					if(value!=null)
						return tStripTagUtil.replaceTag(styleContent, key, value);
				}	
			}
		}
		
		return styleContent;
	}
	
}
