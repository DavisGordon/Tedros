package main;

import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class EntidadeProperty extends TModelView<Entidade> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty texto;
	private SimpleDoubleProperty vlrDouble;
	private SimpleIntegerProperty vlrInteger;
	
	
	/*public EntidadeProperty(Entidade entidade) {
		this.entidade = entidade;
		texto = new SimpleStringProperty(this, "texto", entidade.getTexto());
		vlrDouble = new SimpleDoubleProperty(this, "vlrDouble", entidade.getVlrDouble());
		vlrInteger = new SimpleIntegerProperty(this, "vlrInteger", entidade.getVlrInteger());
	}*/
	
	public EntidadeProperty(Entidade model) {
		super(model);
	}

	public SimpleStringProperty getTexto() {
		return texto;
	}

	public void setTexto(SimpleStringProperty texto) {
		this.texto = texto;
	}

	public SimpleDoubleProperty getVlrDouble() {
		return vlrDouble;
	}

	public void setVlrDouble(SimpleDoubleProperty vlrDouble) {
		this.vlrDouble = vlrDouble;
	}

	public SimpleIntegerProperty getVlrInteger() {
		return vlrInteger;
	}

	public void setVlrInteger(SimpleIntegerProperty vlrInteger) {
		this.vlrInteger = vlrInteger;
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
		return texto;
	}

	

}
