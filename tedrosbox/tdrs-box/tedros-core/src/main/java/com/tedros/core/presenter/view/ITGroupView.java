package com.tedros.core.presenter.view;

import com.tedros.core.presenter.ITGroupPresenter;

import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;

/**
 * Group view
 * */
@SuppressWarnings("rawtypes")
public interface ITGroupView<P extends ITGroupPresenter> extends ITView<P> {
	
	/**
	 * Returns the Label with the group view title
	 * 
	 *  @return {@link Label}
	 * */
	Label gettGroupTitle();
	
	/**
	 * Sets the Label with the group view title
	 * 
	 *  @param tGroupTitleLabel
	 * */
	void settGroupTitle(Label tGroupTitleLabel);

	/**
	 * Returns the ToolBar
	 * 
	 *  @return {@link ToolBar}
	 * */
	ToolBar gettGroupToolbar();

	/**
	 * Sets the ToolBar 
	 * 
	 * @param tGroupToolbar
	 * */
	void settGroupToolbar(ToolBar tGroupToolbar);

}