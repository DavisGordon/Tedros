/**
 * 
 */
package org.tedros.tools.module.scheme.model;

import java.io.File;
import java.io.IOException;

import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.style.TStyleResourceValue;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TColorPickerField;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TSliderField;
import org.tedros.fx.annotation.control.TTextField;
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
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.server.model.TFileModel;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.scheme.behaviour.TMainColorBehavior;
import org.tedros.tools.module.scheme.decorator.TMainColorDecorator;
import org.tedros.tools.module.scheme.process.TMainColorProcess;
import org.tedros.tools.module.scheme.trigger.TMainColorTrigger;
import org.tedros.util.TLoggerUtil;

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
@TPresenter(type=TDynaPresenter.class, model=TMainColor.class, 
decorator=@TDecorator(type=TMainColorDecorator.class, viewTitle=ToolsKey.VIEW_THEMES), 
behavior=@TBehavior(type=TMainColorBehavior.class))
public class TMainColorMV extends TEntityModelView<TMainColor> {

	private static final String DEFAULT_SYSTEM_NAME = "Tedros";

	private static final int DEFAULT_IDENTANTION = 55;

	private static final double DEFAULT_MAIN_OPACIT = 0.35;

	private static final double DEFAULT_NAV_OPACIT = 0.35;

	private static final double DEFAULT_TEXT_SIZE = 1.4;

	private static final int DEFAULT_ICON_SIZE = 57;

	private SimpleStringProperty display;
	
	@TAccordion(node=@TNode(parse = true, 
		styleClass=@TObservableListProperty(addAll="t-accordion", parse=true)),
		panes={
			@TTitledPane(text=TUsualKey.MAIN_TITLE, fields={"mainCorTexto", "mainCorFundo","mainOpacidade", 
					"brand", "indentation", "fileLogo"}),
			@TTitledPane(text=TUsualKey.NAV_TITLE, fields={"navCorTexto", "navCorFundo","navOpacidade"}),
			@TTitledPane(text=TUsualKey.APP_TITLE, fields={ "appCorTexto", "appTamTexto", "appIconSize"})
	})
	@TLabel(text=TUsualKey.TEXT, position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> mainCorTexto;
	
	@TLabel(text=TUsualKey.BACKGROUND, position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> mainCorFundo;
	
	@TLabel(text=TUsualKey.OPACITY)
	@TSliderField(max = 1, min = 0,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty mainOpacidade;
	
	@TLabel(text=TUsualKey.COMPANY)
	@TTextField(maxLength=100)
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleStringProperty brand;
	
	@TLabel(text="Indentation")
	@TSliderField(max = 100, min = 0,
		majorTickUnit=50, blockIncrement=5,
		minorTickCount=5, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty indentation;
	
	@TLabel(text=TUsualKey.LOGO)
	@TFileField(propertyValueType=TFileModelType.MODEL, 
	extensions= {TFileExtension.ALL_IMAGES}, 
	showFilePath=false, showImage=false, 
	maxImageHeight = 60, minImageHeight = 30, maxFileSize = 256000)
	@TGenericType(model=TFileModel.class)
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private TSimpleFileProperty<TFileModel> fileLogo;
	
	// nav bar
	
	@TLabel(text=TUsualKey.TEXT, position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> navCorTexto;
	
	@TLabel(text=TUsualKey.BACKGROUND, position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> navCorFundo;
	
	@TLabel(text=TUsualKey.OPACITY)
	@TSliderField(max = 1, min = 0,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty navOpacidade;
	
	@TLabel(text=TUsualKey.TEXT, position=TLabelPosition.LEFT, control=@TControl(parse = true, prefWidth=80))
	@TColorPickerField
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleObjectProperty<Color> appCorTexto;
	
	@TLabel(text=TUsualKey.FONT)
	@TSliderField(max = 2, min = 0.8,
		majorTickUnit=0.5, blockIncrement=0.01,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT)
	private SimpleDoubleProperty appTamTexto;
	
	@TLabel(text="Icon size")
	@TSliderField(max = 114, min = DEFAULT_ICON_SIZE,
		majorTickUnit=57, blockIncrement=1,
		minorTickCount=1, control=@TControl(parse = true, prefWidth=100), 
		showTickMarks=true, showTickLabels=true)
	@TTrigger(type=TMainColorTrigger.class, mode=TViewMode.EDIT, runAfterFormBuild = true)
	private SimpleDoubleProperty appIconSize;
	
	public TMainColorMV(TMainColor entity) {
		super(entity);
		this.display.setValue("#{label.style}");
		super.formatToString("%s", display);
	}
	
	public void loadSavedValues() {
		String mainCorTexto = TStyleResourceValue.MAIN_TEXT_COLOR.defaultStyle(true);
		String mainCorFundo = TStyleResourceValue.MAIN_COLOR.defaultStyle();
		String mainOpacidade = TStyleResourceValue.MAIN_COLOR_OPACITY.defaultStyle();
		
		String brand = TStyleResourceValue.BRAND.headerStyle(true);
		String indentantion = TStyleResourceValue.INDENTANTION.headerStyle();
		String path = TStyleResourceValue.LOGO.headerStyle();
		
		String navCorTexto = TStyleResourceValue.TOPBAR_TEXT_COLOR.defaultStyle();
		String navCorFundo = TStyleResourceValue.TOPBAR_COLOR.defaultStyle();
		String navOpacidade = TStyleResourceValue.TOPBAR_COLOR_OPACITY.defaultStyle();
		
		String appCorTexto = TStyleResourceValue.APP_TEXT_COLOR.defaultStyle();
		String appTamTexto = TStyleResourceValue.APP_TEXT_SIZE.defaultStyle();
		String appIconSize = TStyleResourceValue.APP_ICON_SIZE.defaultStyle();
		
		this.brand.setValue(brand!=null? brand : DEFAULT_SYSTEM_NAME);
		this.indentation.setValue(indentantion!=null ? Double.parseDouble(indentantion) : DEFAULT_IDENTANTION);
		
		if(path!=null) {
			try {
				TFileModel fileModel = new TFileModel(new File(path));
				this.fileLogo.setValue(fileModel);
			} catch (IOException e) {
				TLoggerUtil.error(getClass(), e.getMessage(), e);
			}
		}
		this.mainCorTexto.setValue(mainCorTexto!=null ? Color.web(mainCorTexto) : Color.WHITE);
		this.mainCorFundo.setValue(mainCorFundo!=null ? Color.web(mainCorFundo) : Color.BLACK);
		this.mainOpacidade.setValue(mainOpacidade!=null ? Double.parseDouble(mainOpacidade) : DEFAULT_MAIN_OPACIT);
		
		
		
		this.navCorTexto.setValue(navCorTexto!=null ? Color.web(navCorTexto) : Color.WHITE);
		this.navCorFundo.setValue(navCorFundo!=null ? Color.web(navCorFundo) : Color.BLACK);
		this.navOpacidade.setValue(navOpacidade!=null ? Double.parseDouble(navOpacidade) : DEFAULT_NAV_OPACIT);
		
		this.appCorTexto.setValue(appCorTexto!=null ? Color.web(appCorTexto) : Color.WHITE);
		this.appTamTexto.setValue(appTamTexto!=null ? Double.parseDouble(appTamTexto) : DEFAULT_TEXT_SIZE);
		this.appIconSize.setValue(appIconSize!=null ? Double.parseDouble(appIconSize) : DEFAULT_ICON_SIZE);
		
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

	public SimpleDoubleProperty getIndentation() {
		return indentation;
	}

	public void setIndentation(SimpleDoubleProperty indentation) {
		this.indentation = indentation;
	}

	public SimpleStringProperty getBrand() {
		return brand;
	}

	public void setBrand(SimpleStringProperty brand) {
		this.brand = brand;
	}

	public TSimpleFileProperty<TFileModel> getFileLogo() {
		return fileLogo;
	}

	public void setFileLogo(TSimpleFileProperty<TFileModel> fileLogo) {
		this.fileLogo = fileLogo;
	}

	public SimpleDoubleProperty getAppIconSize() {
		return appIconSize;
	}

	public void setAppIconSize(SimpleDoubleProperty appIconSize) {
		this.appIconSize = appIconSize;
	}

}
