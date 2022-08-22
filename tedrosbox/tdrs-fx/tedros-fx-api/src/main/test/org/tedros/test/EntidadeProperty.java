package org.tedros.test;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EntidadeProperty {

	private SimpleStringProperty texto;
	private SimpleDoubleProperty vlrDouble;
	private SimpleIntegerProperty vlrInteger;
	
	private Entidade entidade;
	
	public EntidadeProperty(Entidade entidade) {
		this.entidade = entidade;
		texto = new SimpleStringProperty(this, "texto", entidade.getTexto());
		vlrDouble = new SimpleDoubleProperty(this, "vlrDouble", entidade.getVlrDouble());
		vlrInteger = new SimpleIntegerProperty(this, "vlrInteger", entidade.getVlrInteger());
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

	public Entidade getEntidade() {
		return entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

}
