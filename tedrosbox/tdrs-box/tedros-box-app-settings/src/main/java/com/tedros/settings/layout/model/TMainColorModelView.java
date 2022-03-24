/**
 * 
 */
package com.tedros.settings.layout.model;

import com.tedros.core.TLanguage;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.fxapi.annotation.control.TColorPickerField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TSliderField;
import com.tedros.fxapi.annotation.control.TTrigger;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TModelProcess;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.settings.layout.behavior.MainColorBehavior;
import com.tedros.settings.layout.decorator.MainColorDecorator;
import com.tedros.settings.layout.process.MainColorProcess;
import com.tedros.settings.layout.trigger.MainColorTrigger;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "#{color.base.painel.title}", scroll=false, editCssId="x")
@TModelProcess(type=MainColorProcess.class)
@TPresenter(type=TDynaPresenter.class, modelClass=MainColor.class, 
decorator=@TDecorator(type=MainColorDecorator.class, viewTitle="#{color.base.painel.title}"), 
behavior=@TBehavior(type=MainColorBehavior.class))
public class TMainColorModelView extends TEntityModelView<MainColor> {

	private SimpleLongProperty id;
	
	@TAccordion(
			panes={
					@TTitledPane(text="#{label.main.title}", fields={"mainCorTexto", "mainCorFundo","mainOpacidade"}),
					@TTitledPane(text="#{label.nav.title}", fields={"navCorTexto", "navCorFundo","navOpacidade"}),
					@TTitledPane(text="#{label.app.title}", fields={ "appCorTexto", "appTamTexto" })
			})
	@TLabel(text="#{label.text}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=MainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> mainCorTexto;
	
	@TLabel(text="#{label.background}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=MainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> mainCorFundo;
	
	@TLabel(text="#{label.opacit}")
	@TSliderField(max = 1, min = 0,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=MainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty mainOpacidade;
	
	// nav bar
	
	@TLabel(text="#{label.text}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=MainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> navCorTexto;
	
	@TLabel(text="#{label.background}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=MainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> navCorFundo;
	
	@TLabel(text="#{label.opacit}")
	@TSliderField(max = 1, min = 0,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=MainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty navOpacidade;
	
	@TLabel(text="#{label.text}", position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=MainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> appCorTexto;
	
	@TLabel(text="#{label.font}")
	@TSliderField(max = 2, min = 0.8,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=MainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty appTamTexto;
	
	public TMainColorModelView(MainColor entity) {
		super(entity);
	}
	
	public void loadSavedValues() {
		String mainCorTexto = TStyleResourceValue.MAIN_TEXT_COLOR.defaultStyle(true);
		String mainCorFundo = TStyleResourceValue.MAIN_COLOR.defaultStyle();
		String mainOpacidade = TStyleResourceValue.MAIN_COLOR_OPACITY.defaultStyle();
		
		String navCorTexto = TStyleResourceValue.TOPBAR_TEXT_COLOR.defaultStyle();
		String navCorFundo = TStyleResourceValue.TOPBAR_COLOR.defaultStyle();
		String navOpacidade = TStyleResourceValue.TOPBAR_COLOR_OPACITY.defaultStyle();
		
		String appCorTexto = TStyleResourceValue.APP_TEXT_COLOR.defaultStyle();
		String appTamTexto = TStyleResourceValue.APP_TEXT_SIZE.defaultStyle();
		
		
		this.mainCorTexto.setValue(mainCorTexto!=null ? Color.web(mainCorTexto) : Color.WHITE);
		this.mainCorFundo.setValue(mainCorFundo!=null ? Color.web(mainCorFundo) : Color.BLACK);
		this.mainOpacidade.setValue(mainOpacidade!=null ? Double.parseDouble(mainOpacidade) : 0.35);
		
		this.navCorTexto.setValue(navCorTexto!=null ? Color.web(navCorTexto) : Color.WHITE);
		this.navCorFundo.setValue(navCorFundo!=null ? Color.web(navCorFundo) : Color.BLACK);
		this.navOpacidade.setValue(navOpacidade!=null ? Double.parseDouble(navOpacidade) : 0.35);
		
		this.appCorTexto.setValue(appCorTexto!=null ? Color.web(appCorTexto) : Color.WHITE);
		this.appTamTexto.setValue(appTamTexto!=null ? Double.parseDouble(appTamTexto) : 1.4);
		
	}

	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the mainCorTexto
	 */
	public SimpleObjectProperty<Color> getMainCorTexto() {
		return mainCorTexto;
	}

	/**
	 * @param mainCorTexto the mainCorTexto to set
	 */
	public void setMainCorTexto(SimpleObjectProperty<Color> mainCorTexto) {
		this.mainCorTexto = mainCorTexto;
	}

	/**
	 * @return the mainCorFundo
	 */
	public SimpleObjectProperty<Color> getMainCorFundo() {
		return mainCorFundo;
	}

	/**
	 * @param mainCorFundo the mainCorFundo to set
	 */
	public void setMainCorFundo(SimpleObjectProperty<Color> mainCorFundo) {
		this.mainCorFundo = mainCorFundo;
	}

	/**
	 * @return the mainOpacidade
	 */
	public SimpleDoubleProperty getMainOpacidade() {
		return mainOpacidade;
	}

	/**
	 * @param mainOpacidade the mainOpacidade to set
	 */
	public void setMainOpacidade(SimpleDoubleProperty mainOpacidade) {
		this.mainOpacidade = mainOpacidade;
	}

	/**
	 * @return the navCorTexto
	 */
	public SimpleObjectProperty<Color> getNavCorTexto() {
		return navCorTexto;
	}

	/**
	 * @param navCorTexto the navCorTexto to set
	 */
	public void setNavCorTexto(SimpleObjectProperty<Color> navCorTexto) {
		this.navCorTexto = navCorTexto;
	}

	/**
	 * @return the navCorFundo
	 */
	public SimpleObjectProperty<Color> getNavCorFundo() {
		return navCorFundo;
	}

	/**
	 * @param navCorFundo the navCorFundo to set
	 */
	public void setNavCorFundo(SimpleObjectProperty<Color> navCorFundo) {
		this.navCorFundo = navCorFundo;
	}

	/**
	 * @return the navOpacidade
	 */
	public SimpleDoubleProperty getNavOpacidade() {
		return navOpacidade;
	}

	/**
	 * @param navOpacidade the navOpacidade to set
	 */
	public void setNavOpacidade(SimpleDoubleProperty navOpacidade) {
		this.navOpacidade = navOpacidade;
	}



	@Override
	public SimpleStringProperty getDisplayProperty() {
		String s = TLanguage.getInstance(null).getString("#{label.style}");
		return new SimpleStringProperty(s);
	}

	/**
	 * @return the appCorTexto
	 */
	public SimpleObjectProperty<Color> getAppCorTexto() {
		return appCorTexto;
	}

	/**
	 * @param appCorTexto the appCorTexto to set
	 */
	public void setAppCorTexto(SimpleObjectProperty<Color> appCorTexto) {
		this.appCorTexto = appCorTexto;
	}

	/**
	 * @return the appTamTexto
	 */
	public SimpleDoubleProperty getAppTamTexto() {
		return appTamTexto;
	}

	/**
	 * @param appTamTexto the appTamTexto to set
	 */
	public void setAppTamTexto(SimpleDoubleProperty appTamTexto) {
		this.appTamTexto = appTamTexto;
	}

}
