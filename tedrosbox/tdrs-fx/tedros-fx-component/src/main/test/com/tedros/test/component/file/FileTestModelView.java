package com.tedros.test.component.file;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tedros.ejb.base.model.TFileModel;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TListFileField;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.property.TSimpleFileModelProperty;

public class FileTestModelView extends TModelView<FileTestModel> {

	
	private SimpleLongProperty id;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-form", parse = true))
	@TText(text="Teste", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty texto;
	
	
	@TLabel(text="Imagem de fundo")
	@TFileField(showImage=true)
	private TSimpleFileModelProperty<TFileModel> selectedFile;
	
	@TLabel(text="Imagens")
	@TListFileField
	private ObservableList<TSimpleFileModelProperty<TFileModel>> fileList;
	
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

	public TSimpleFileModelProperty<TFileModel> getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(TSimpleFileModelProperty<TFileModel> selectedFile) {
		this.selectedFile = selectedFile;
	}

	public ObservableList<TSimpleFileModelProperty<TFileModel>> getFileList() {
		return fileList;
	}

	public void setFileList(ObservableList<TSimpleFileModelProperty<TFileModel>> fileList) {
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
