/**
 * 
 */
package com.solidarity.module.acao.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.solidarity.model.Mailing;
import com.tedros.util.TResourceUtil;

/**
 * @author Davis Gordon
 *
 */
public class MailingUtil {
	
	private static String siteurl;
	
	static{
		Properties prop = TResourceUtil.getPropertiesFromConfFolder("remote-config.properties");
		siteurl = prop.getProperty("siteurl");
	}

	public static String buildTemplateLink(){
		return siteurl+"/email_template.html";
	}
	
	public static String buildConteudo(final Mailing mailing){
		
		
		String pattern = "dd/MM/yyyy 'às' HH:mm";
		DateFormat df = new SimpleDateFormat(pattern);
		
		
		String lp = "<a href='"+siteurl+"/voluntario.html'>Painel</a>";
		String ls = "<a href='"+siteurl+"/index.html'>www.covidsemfome.com.br</a>";
		
		String qmv = mailing.getQtdMinVoluntarios() == null ? "" : mailing.getQtdMinVoluntarios().toString(); 
		String qxv = mailing.getQtdMaxVoluntarios() == null ? "" : mailing.getQtdMaxVoluntarios().toString(); 
		String qin = mailing.getVoluntarios() == null ? "" : String.valueOf(mailing.getVoluntarios().size()); 
		
		String str = mailing.getConteudo();
		str = str.replaceAll("#TITULOACAO#", mailing.getTitulo());
		str = str.replaceAll("#DESCRICAOACAO#", mailing.getDescricao());
		str = str.replaceAll("#STATUSACAO#", StringUtils.capitalize(mailing.getStatus().toLowerCase()));
		str = str.replaceAll("#DATAACAO#", df.format(mailing.getData()));
		str = str.replaceAll("#QTDINSCRITOS#", qin);
		str = str.replaceAll("#QTDMINVOL#", qmv);
		str = str.replaceAll("#QTDMAXVOL#", qxv);
		str = str.replaceAll("#LINKPAINEL#", lp);
		str = str.replaceAll("#LINKSITE#", ls);
		
		return str;
	}
}
