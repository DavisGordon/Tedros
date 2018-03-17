/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/08/2014
 */
package com.tedros.fxapi.presenter;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author davis.dun
 *
 */
public interface ITSimplePresenter {
	
	public void setTopContent(Node node);
	
	public void setBottomContent(Node node);
	
	public void addFormSpaceContent(Node node);
	
	public void clearFormSpaceContent();
	
	public void setLeftContent(Node node);
	
	public void setRightContent(Node node);
	
	public void disableSaveBtn(boolean disabled);
	
	public void showSaveBtn(boolean show);
	
	public void disableMenuToolBar(boolean disabled);
	
	public void showMenuToolBar(boolean show);

	public BooleanProperty saveBtnDisabledProperty();

	public BooleanProperty saveBtnVisibleProperty();

	public BooleanProperty menuToolBarDisabledProperty();

	public BooleanProperty menuToolBarVisibleProperty();
	
	public void saveAction();
	
	public void setTitleView(String titleView);
	
	public String getTitleView();
	
	public void setSaveBtnText(String saveText);
	
	public String getSaveBtnText();

}
