package com.tedros.test.component.file;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.ejb.base.model.TFileModel;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

public class FileTestModelView extends TModelView<FileTestModel> {

	
	private SimpleLongProperty id;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Teste", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, node=@TNode(id="t-form-title-text", parse = true), 
	textStyle = TTextStyle.MEDIUM)
	private SimpleStringProperty texto;
	
	
	@TLabel(text="Imagem de fundo")
	@TFileField(showImage=true)
	private TSimpleFileProperty<TFileModel> selectedFile;
	
	@TLabel(text="Imagens")
	@TFileField
	private ObservableList<TSimpleFileProperty<TFileModel>> fileList;
	
	public FileTestModelView(FileTestModel model) {
		super(model);
	}

	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return null;
	}

	public TSimpleFileProperty<TFileModel> getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(TSimpleFileProperty<TFileModel> selectedFile) {
		this.selectedFile = selectedFile;
	}

	public ObservableList<TSimpleFileProperty<TFileModel>> getFileList() {
		return fileList;
	}

	public void setFileList(ObservableList<TSimpleFileProperty<TFileModel>> fileList) {
		this.fileList = fileList;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	public SimpleStringProperty getTexto() {
		return texto;
	}

	public void setTexto(SimpleStringProperty texto) {
		this.texto = texto;
	}

}
