package org.tedros.tools.module.scheme.decorator;

import org.tedros.core.TLanguage;
import org.tedros.fx.control.TComboBoxField;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.scheme.model.TMainColorMV;
import org.tedros.tools.module.scheme.template.TemplatePane;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class TMainColorDecorator extends TDynaViewCrudBaseDecorator<TMainColorMV> {


	private TComboBoxField<String> themes;
	
	private TemplatePane template;
	
	@Override
	public void decorate() {
		
		try {
			setViewTitle(null);
			buildSaveButton(ToolsKey.BUTTON_APPLY);
			
			themes = new TComboBoxField<>();
			// add the buttons at the header tool bar
			addItemInTHeaderToolBar(gettSaveButton());
			Region space = new Region();
			HBox.setHgrow(space, Priority.ALWAYS);
			TLabel l = new TLabel(TLanguage.getInstance(null).getString(ToolsKey.THEME+": "));
			super.addItemInTHeaderHorizontalLayout(space,l, themes);
			
			
			this.template = new TemplatePane();
			super.addItemInTLeftContent(getView().gettFormSpace());
			super.addItemInTCenterContent(this.template);
			StackPane.setMargin(template, new Insets(6));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	/**
	 * @return the themes
	 */
	public TComboBoxField<String> getThemes() {
		return themes;
	}


	/**
	 * @return the template
	 */
	public TemplatePane getTemplate() {
		return template;
	}

}
