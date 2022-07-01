package com.tedros.tools.module.scheme.decorator;

import com.tedros.core.TLanguage;
import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.control.TLabel;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.tools.ToolsKey;
import com.tedros.tools.module.scheme.model.TMainColorMV;
import com.tedros.tools.module.scheme.template.TemplatePane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

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
