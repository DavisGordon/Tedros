/**
 * 
 */
package org.tedros.tools.module.preferences.model;

import org.tedros.common.model.TFileEntity;
import org.tedros.common.model.TMimeType;
import org.tedros.common.model.TMimeTypeImport;
import org.tedros.core.controller.TMimeTypeImportController;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.effect.TDropShadow;
import org.tedros.fx.annotation.effect.TEffect;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.reader.TReaderHtml;
import org.tedros.fx.annotation.reader.TTextReaderHtml;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.domain.THtmlConstant;
import org.tedros.fx.domain.TStyleParameter;
import org.tedros.fx.presenter.modal.behavior.TImportFileModalBehavior;
import org.tedros.fx.presenter.modal.decorator.TImportFileModalDecorator;
import org.tedros.fx.presenter.model.TImportModelView;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.start.TConstant;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TEjbService(serviceName = TMimeTypeImportController.JNDI_NAME, model=TMimeTypeImport.class)
@TPresenter(decorator = @TDecorator(type=TImportFileModalDecorator.class, viewTitle=ToolsKey.VIEW_MIMETYPE_IMP),
			behavior = @TBehavior(type=TImportFileModalBehavior.class, 
			importedEntityClass=TMimeType.class, importedModelViewClass=TMimeTypeMV.class))
public class TMimeTypeImportMV extends TImportModelView<TMimeTypeImport> {

	@TTextReaderHtml(text=ToolsKey.IMPORT_TEXT, 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text=ToolsKey.IMPORT_UP_FILE, textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty texto;
	
	@TReaderHtml
	private SimpleStringProperty rules;
	
	@TLabel(text=ToolsKey.FILE)
	@TFileField(propertyValueType=TFileModelType.ENTITY, 
	initialDirectory=TFileField.TEDROS_MODULE+TConstant.UUI,
	extensions= {TFileExtension.CSV}, moreExtensions= {"*.xls", "*.xlsx"},
	showFilePath=true, required=true)
	private TSimpleFileProperty<TFileEntity> file;
	
	public TMimeTypeImportMV(TMimeTypeImport model) {
		super(model);
	}

	@Override
	public void setId(SimpleLongProperty id) {
		
	}

	@Override
	public SimpleLongProperty getId() {
		return null;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return null;
	}

	/**
	 * @return the file
	 */
	public TSimpleFileProperty<TFileEntity> getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(TSimpleFileProperty<TFileEntity> file) {
		this.file = file;
	}

	/**
	 * @return the rules
	 */
	public SimpleStringProperty getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(SimpleStringProperty rules) {
		this.rules = rules;
	}

	/**
	 * @return the texto
	 */
	public SimpleStringProperty getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(SimpleStringProperty texto) {
		this.texto = texto;
	}

}
