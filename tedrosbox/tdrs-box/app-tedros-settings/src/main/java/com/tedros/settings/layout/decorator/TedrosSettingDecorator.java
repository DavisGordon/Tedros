package com.tedros.settings.layout.decorator;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.settings.layout.model.ExampleViewModel;
import com.tedros.settings.layout.model.PainelModelView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;

public class TedrosSettingDecorator extends TDynaViewCrudBaseDecorator<PainelModelView>
{
	
	private TDynaPresenter<ExampleViewModel> examplePresenter;
	
	@Override
	public void decorate() {
		
		examplePresenter = new TDynaPresenter<>(ExampleViewModel.class);
		TDynaView<ExampleViewModel> exampleView = new TDynaView<>(examplePresenter); 
		exampleView.tLoad();
		
		setViewTitle("#{settings.form.name}");
		buildSaveButton(TInternationalizationEngine.getInstance(null).getString("#{label.button.save}"));
		
		// add the buttons at the header tool bar
		addItemInTHeaderToolBar(gettSaveButton());
				
		final ScrollPane exampleScroll = ScrollPaneBuilder.create()
				.style("-fx-background-color: transparent;")
				.content(exampleView)
				.fitToHeight(true)
				.fitToWidth(true)
				.prefHeight(exampleView.getHeight())
				.maxHeight(Double.MAX_VALUE)
				.build();
		
		getView().gettFormSpace().setPrefWidth(400);
		getView().gettFormSpace().setAlignment(Pos.TOP_LEFT);
		getView().gettFormSpace().setPadding(new Insets(0));
		
		addItemInTLeftContent(getView().gettFormSpace());
		
		addPaddingInTLeftContent(0, 0, 0, 0);
		addItemInTCenterContent(exampleScroll);
		
		
	}
	
	public TDynaPresenter<ExampleViewModel> getExamplePresenter() {
		return examplePresenter;
	}
}
