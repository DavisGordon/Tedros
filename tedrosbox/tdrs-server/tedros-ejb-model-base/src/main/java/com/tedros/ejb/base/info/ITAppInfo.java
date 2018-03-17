package com.tedros.ejb.base.info;

import java.util.Properties;

/**
 * Interface com os dados de identificação do modulo.
 * */
public interface ITAppInfo {

	/**
	 * Carrega o properties module-info.properties
	 * */
	public void readInfoProperties(Properties info);
	/**
	 * Retorna o nome do modulo
	 * */
	public String getModuleName();
	/**
	 * Retorna o codigo partner id fornecido pela Tedros.
	 * */
	public String getPartnerId();
	/**
	 * Retorna o partnerAlias
	 * */	
	public String getPartnerAlias();
	/**
	 * Retorna uma curta descrição do modulo.
	 * */
	public String getModuleShortDescription();
	/**
	 * Retorna o schema do modulo 
	 * */
	public String getModuleSchema();
	
}
