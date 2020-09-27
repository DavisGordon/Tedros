/**
 * 
 */
package com.covidsemfome.module.produto.model;

import com.covidsemfome.model.Produto;
import com.covidsemfome.model.ProdutoImport;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.effect.TDropShadow;
import com.tedros.fxapi.annotation.effect.TEffect;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.modal.behavior.TImportFileModalBehavior;
import com.tedros.fxapi.presenter.modal.decorator.TImportFileModalDecorator;
import com.tedros.fxapi.presenter.model.TImportModelView;
import com.tedros.fxapi.property.TSimpleFileEntityProperty;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TEjbService(serviceName = "IProdutoImportControllerRemote", model=ProdutoImport.class)
@TPresenter(decorator = @TDecorator(type=TImportFileModalDecorator.class, viewTitle="Importar produtos"),
			behavior = @TBehavior(type=TImportFileModalBehavior.class, 
			importedEntityClass=Produto.class, importedModelViewClass=ProdutoModelView.class))
public class ProdutoImportModelView extends TImportModelView<ProdutoImport> {

	@TTextReaderHtml(text="Especificação de arquivo para importação", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", effect=@TEffect(dropShadow=@TDropShadow, parse=true), parse = true))
	@TText(text="Importar arquivo para cadastro de produtos", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
	node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty texto;
	
	@TReaderHtml
	private SimpleStringProperty rules;
	
	@TLabel(text="Arquivo")
	@TFileField(extensions= {TFileExtension.ALL_MICROSOFT_EXCEL, TFileExtension.CSV}, required=true)
	private TSimpleFileEntityProperty file;
	
	public ProdutoImportModelView(ProdutoImport model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setId(SimpleLongProperty id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SimpleLongProperty getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the file
	 */
	public TSimpleFileEntityProperty getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(TSimpleFileEntityProperty file) {
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
