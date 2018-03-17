package com.tedros.core.presenter.view;

import com.tedros.core.presenter.ITGroupPresenter;

import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;

@SuppressWarnings("rawtypes")
public interface ITGroupView<P extends ITGroupPresenter> extends ITView<P> {

	Label gettGroupTitle();

	void settGroupTitle(Label tGroupTitle);

	ToolBar gettGroupToolbar();

	void settGroupToolbar(ToolBar tGroupToolbar);

}