/**
 * 
 */
package com.covidsemfome.module.empresaParceira.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.covidsemfome.model.Mailing;
import com.covidsemfome.parceiro.model.SiteConteudo;
import com.tedros.util.TResourceUtil;

/**
 * @author Davis Gordon
 *
 */
public class SiteConteudoUtil {
	
	private static String siteurl;
	
	static{
		//Properties prop = TResourceUtil.getPropertiesFromConfFolder("remote-config.properties");
		siteurl = "http://localhost:8081/tdrs-app-covid-semfome-webapp"; //prop.getProperty("siteurl");
	}

	public static String buildTemplateLink(){
		return siteurl+"/parceiro/spotview.html";
	}
	
	public static String buildConteudo(final SiteConteudo mailing){
		return null;
	}
}
