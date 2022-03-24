package com.tedros.settings.layout.decorator;

import com.tedros.core.TLanguage;
import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.control.TLabel;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.settings.layout.model.BackgroundImageModelView;
import com.tedros.settings.layout.model.TMainColorModelView;
import com.tedros.settings.layout.template.TemplatePane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class MainColorDecorator extends TDynaViewCrudBaseDecorator<TMainColorModelView> {


	private TComboBoxField<String> themes;
	
	private TemplatePane template;
	
	@Override
	public void decorate() {
		
		try {
			setViewTitle(null);
			buildSaveButton("#{label.button.apply}");
			
			themes = new TComboBoxField<>();
			// add the buttons at the header tool bar
			addItemInTHeaderToolBar(gettSaveButton());
			Region space = new Region();
			HBox.setHgrow(space, Priority.ALWAYS);
			TLabel l = new TLabel(TLanguage.getInstance(null).getString("#{label.theme}: "));
			super.addItemInTHeaderHorizontalLayout(space,l, themes);
			
			
			this.template = new TemplatePane();
			super.addItemInTLeftContent(getView().gettFormSpace());
			super.addItemInTCenterContent(this.template);
			
			
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
