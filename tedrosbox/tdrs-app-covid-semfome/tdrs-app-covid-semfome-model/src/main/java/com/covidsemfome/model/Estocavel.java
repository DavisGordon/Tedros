package com.covidsemfome.model;

import java.util.Date;
import java.util.List;

public interface Estocavel {

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
	 * @return the itens
	 */
	List<? extends EstocavelItem> getItens();


}