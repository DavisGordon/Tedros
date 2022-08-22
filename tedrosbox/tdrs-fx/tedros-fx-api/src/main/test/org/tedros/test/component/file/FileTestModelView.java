package org.tedros.test.component.file;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.server.model.TFileModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

public class FileTestModelView extends TModelView<FileTestModel> {

	
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
	public SimpleStringProperty toStringProperty() {
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
