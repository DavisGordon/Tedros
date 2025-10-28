package org.tedros.tools.module.scheme.decorator;

import org.tedros.fx.TFxKey;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.scheme.model.ExampleMV;
import org.tedros.tools.module.scheme.model.TPanelMV;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class TPanelDecorator extends TDynaViewCrudBaseDecorator<TPanelMV>
{
	
	private TDynaPresenter<ExampleMV> examplePresenter;
	
	@Override
	public void decorate() {
		
		examplePresenter = new TDynaPresenter<>(ExampleMV.class);
		TDynaView<ExampleMV> exampleView = new TDynaView<>(examplePresenter); 
		exampleView.tLoad();
		
		setViewTitle(ToolsKey.VIEW_COLORS);
		buildSaveButton(TFxKey.BUTTON_APPLY);
		
		// add the buttons at the header tool bar
		addItemInTHeaderToolBar(gettSaveButton());
		
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
	
	public TDynaPresenter<ExampleMV> getExamplePresenter() {
		return examplePresenter;
	}

}
