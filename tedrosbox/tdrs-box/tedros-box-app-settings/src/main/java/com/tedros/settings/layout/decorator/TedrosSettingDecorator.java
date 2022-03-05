package com.tedros.settings.layout.decorator;

import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.control.TLabel;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.settings.layout.model.ExampleViewModel;
import com.tedros.settings.layout.model.PainelModelView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class TedrosSettingDecorator extends TDynaViewCrudBaseDecorator<PainelModelView>
{
	
	private TDynaPresenter<ExampleViewModel> examplePresenter;
	
	private TComboBoxField<String> themes;
	
	
	@Override
	public void decorate() {
		
		examplePresenter = new TDynaPresenter<>(ExampleViewModel.class);
		TDynaView<ExampleViewModel> exampleView = new TDynaView<>(examplePresenter); 
		exampleView.tLoad();
		
		setViewTitle("#{settings.form.name}");
		buildSaveButton("#{label.button.apply}");
		
		themes = new TComboBoxField<>();
		// add the buttons at the header tool bar
		addItemInTHeaderToolBar(gettSaveButton());
		Region space = new Region();
		HBox.setHgrow(space, Priority.ALWAYS);
		TLabel l = new TLabel("Temas: ");
		super.addItemInTHeaderHorizontalLayout(space,l, themes);
				
		final ScrollPane exampleScroll = new ScrollPane();
		exampleScroll.setStyle("-fx-background-color: transparent;");
		exampleScroll.setContent(exampleView);
		exampleScroll.setFitToHeight(true);
		exampleScroll.setFitToWidth(true);
		exampleScroll.prefHeight(exampleView.getHeight());
		exampleScroll.maxHeight(Double.MAX_VALUE);
		
		getView().gettFormSpace().setPrefWidth(400);
		getView().gettFormSpace().setAlignment(Pos.TOP_LEFT);
		getView().gettFormSpace().setPadding(new Insets(0));
		
		addItemInTLeftContent(getView().gettFormSpace());
		
		addPaddingInTLeftContent(0, 0, 0, 0);
		addItemInTCenterContent(exampleScroll);
		StackPane.setMargin(exampleScroll, new Insets(40, 0, 0, 40));
		
	}
	
	public TDynaPresenter<ExampleViewModel> getExamplePresenter() {
		return examplePresenter;
	}

	/**
	 * @return the themes
	 */
	public TComboBoxField<String> getThemes() {
		return themes;
	}
}
