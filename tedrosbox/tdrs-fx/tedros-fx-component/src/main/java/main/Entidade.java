package main;

import com.tedros.ejb.base.entity.TEntity;

public class Entidade extends TEntity {

	private static final long serialVersionUID = 1L;
	private String texto;
	private Double vlrDouble;
	private Integer vlrInteger;
	
	public Entidade() {
		
	}
	
	public Entidade(String texto, Double vlrD, Integer vlrInt) {
		this.texto = texto;
		this.vlrDouble = vlrD;
		this.vlrInteger = vlrInt;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Double getVlrDouble() {
		return vlrDouble;
	}

	public void setVlrDouble(Double vlrDouble) {
		this.vlrDouble = vlrDouble;
	}

	public Integer getVlrInteger() {
		return vlrInteger;
	}

	public void setVlrInteger(Integer vlrInteger) {
		this.vlrInteger = vlrInteger;
	}
	
	

}
