package com.tedros.tools.module.scheme.model;

import java.util.Date;
import java.util.List;

import com.tedros.ejb.base.entity.ITEntity;

import javafx.scene.paint.Color;

public class TMainColor implements ITEntity {
	private static final long serialVersionUID = -7456496481184817180L;

	private Long id;
	
	private Color mainCorTexto;
	private Color mainCorFundo;
	private Double mainOpacidade;
	
	private Color navCorTexto;
	private Color navCorFundo;
	private Double navOpacidade;
	
	private Color appCorTexto;
	private Double appTamTexto;

	/**
	 * 
	 */
	public TMainColor() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the mainCorTexto
	 */
	public Color getMainCorTexto() {
		return mainCorTexto;
	}

	/**
	 * @param mainCorTexto the mainCorTexto to set
	 */
	public void setMainCorTexto(Color mainCorTexto) {
		this.mainCorTexto = mainCorTexto;
	}

	/**
	 * @return the mainCorFundo
	 */
	public Color getMainCorFundo() {
		return mainCorFundo;
	}

	/**
	 * @param mainCorFundo the mainCorFundo to set
	 */
	public void setMainCorFundo(Color mainCorFundo) {
		this.mainCorFundo = mainCorFundo;
	}

	/**
	 * @return the mainOpacidade
	 */
	public Double getMainOpacidade() {
		return mainOpacidade;
	}

	/**
	 * @param mainOpacidade the mainOpacidade to set
	 */
	public void setMainOpacidade(Double mainOpacidade) {
		this.mainOpacidade = mainOpacidade;
	}

	/**
	 * @return the navCorTexto
	 */
	public Color getNavCorTexto() {
		return navCorTexto;
	}

	/**
	 * @param navCorTexto the navCorTexto to set
	 */
	public void setNavCorTexto(Color navCorTexto) {
		this.navCorTexto = navCorTexto;
	}

	/**
	 * @return the navCorFundo
	 */
	public Color getNavCorFundo() {
		return navCorFundo;
	}

	/**
	 * @param navCorFundo the navCorFundo to set
	 */
	public void setNavCorFundo(Color navCorFundo) {
		this.navCorFundo = navCorFundo;
	}

	/**
	 * @return the navOpacidade
	 */
	public Double getNavOpacidade() {
		return navOpacidade;
	}

	/**
	 * @param navOpacidade the navOpacidade to set
	 */
	public void setNavOpacidade(Double navOpacidade) {
		this.navOpacidade = navOpacidade;
	}


	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getVersionNum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVersionNum(Integer versionNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getLastUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastUpdate(Date lastUpdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getInsertDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInsertDate(Date insertDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getOrderBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addOrderBy(String fieldName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOrderBy(List<String> orders) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the appCorTexto
	 */
	public Color getAppCorTexto() {
		return appCorTexto;
	}

	/**
	 * @param appCorTexto the appCorTexto to set
	 */
	public void setAppCorTexto(Color appCorTexto) {
		this.appCorTexto = appCorTexto;
	}

	/**
	 * @return the appTamTexto
	 */
	public Double getAppTamTexto() {
		return appTamTexto;
	}

	/**
	 * @param appTamTexto the appTamTexto to set
	 */
	public void setAppTamTexto(Double appTamTexto) {
		this.appTamTexto = appTamTexto;
	}

	
}
