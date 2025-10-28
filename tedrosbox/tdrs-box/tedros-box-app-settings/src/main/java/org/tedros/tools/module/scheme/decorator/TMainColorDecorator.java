package org.tedros.tools.module.scheme.decorator;

import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.control.TComboBoxField;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.tools.module.scheme.model.TMainColorMV;
import org.tedros.tools.module.scheme.template.TemplatePane;

import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class TMainColorDecorator extends TDynaViewCrudBaseDecorator<TMainColorMV> {


	private TComboBoxField<String> themes;
	
	private TemplatePane template;
	
	@Override
	public void decorate() {
		
		try {
			setViewTitle(null);
			buildSaveButton(TFxKey.BUTTON_APPLY);
			
			themes = new TComboBoxField<>();
			// add the buttons at the header tool bar
			//final ITDynaView<TMainColorMV> view = getView();
			TLabel l = new TLabel(TLanguage.getInstance(null).getString(TUsualKey.THEME+": "));
			l.setPadding(new Insets(0,4,0,4));
			
			Region space = new Region();
			double w = 10;
			space.setMaxWidth(w);
			space.setMinWidth(w);
			space.setPrefWidth(w);
			
			addItemInTHeaderToolBar(l, themes, space, gettSaveButton());
			
			
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
