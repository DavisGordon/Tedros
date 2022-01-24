package org.somos.model;

import java.util.Date;
import java.util.List;

import com.tedros.ejb.base.entity.ITEntity;

public interface Estocavel extends ITEntity{

	/**
	 * @return the data
	 */
	Date getData();

	/**
	 * @param data the data to set
	 */
	void setData(Date data);

	/**
	 * @return the cozinha
	 */
	Cozinha getCozinha();

	/**
	 * @param cozinha the cozinha to set
	 */
	void setCozinha(Cozinha cozinha);
	
	/**
	 * @return a dynamic stock observation 
	 * */
	String getObservacaoEstoque();

	/**
	 * @return the itens
	 */
	List<? extends EstocavelItem> getItens();


}