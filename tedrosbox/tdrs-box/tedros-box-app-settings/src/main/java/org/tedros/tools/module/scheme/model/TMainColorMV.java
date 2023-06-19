/**
 * 
 */
package org.tedros.tools.module.scheme.model;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.style.TStyleResourceValue;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TColorPickerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TSliderField;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TAccordion;
import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TModelProcess;
import org.tedros.fx.annotation.property.TObservableListProperty;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.scheme.behaviour.TMainColorBehavior;
import org.tedros.tools.module.scheme.decorator.TMainColorDecorator;
import org.tedros.tools.module.scheme.process.TMainColorProcess;
import org.tedros.tools.module.scheme.trigger.TMainColorTrigger;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = ToolsKey.VIEW_THEMES, scroll=false, editCssId="x")
@TModelProcess(type=TMainColorProcess.class)
@TPresenter(type=TDynaPresenter.class, modelClass=TMainColor.class, 
decorator=@TDecorator(type=TMainColorDecorator.class, viewTitle=ToolsKey.VIEW_THEMES), 
behavior=@TBehavior(type=TMainColorBehavior.class))
public class TMainColorMV extends TEntityModelView<TMainColor> {

	private SimpleStringProperty display;
	
	@TAccordion(node=@TNode(parse = true, 
			styleClass=@TObservableListProperty(addAll="t-accordion", parse=true)),
			panes={
					@TTitledPane(text=TUsualKey.MAIN_TITLE, fields={"mainCorTexto", "mainCorFundo","mainOpacidade"}),
					@TTitledPane(text=TUsualKey.NAV_TITLE, fields={"navCorTexto", "navCorFundo","navOpacidade"}),
					@TTitledPane(text=TUsualKey.APP_TITLE, fields={ "appCorTexto", "appTamTexto"})
			})
	@TLabel(text=TUsualKey.TEXT, position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> mainCorTexto;
	
	@TLabel(text=TUsualKey.BACKGROUND, position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> mainCorFundo;
	
	@TLabel(text=TUsualKey.OPACITY)
	@TSliderField(max = 1, min = 0,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty mainOpacidade;
	
	// nav bar
	
	@TLabel(text=TUsualKey.TEXT, position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> navCorTexto;
	
	@TLabel(text=TUsualKey.BACKGROUND, position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> navCorFundo;
	
	@TLabel(text=TUsualKey.OPACITY)
	@TSliderField(max = 1, min = 0,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty navOpacidade;
	
	@TLabel(text=TUsualKey.TEXT, position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(triggerClass=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> appCorTexto;
	
	@TLabel(text=TUsualKey.FONT)
	@TSliderField(max = 2, min = 0.8,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(triggerClass=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty appTamTexto;
	
	public TMainColorMV(TMainColor entity) {
		super(entity);
		this.display.setValue("#{label.style}");
		super.formatToString("%s", display);
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

	/**
	 * @return the display
	 */
	public SimpleStringProperty getDisplay() {
		return display;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(SimpleStringProperty display) {
		this.display = display;
	}

}
